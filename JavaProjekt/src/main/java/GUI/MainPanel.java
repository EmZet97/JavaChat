package GUI;

import Globals.GlobalVariables;
import Messages.Message;
import RoomPackage.Room;
import RoomPackage.RoomCreator;
import RoomPackage.RoomType;
import SQL.SQLConnector;
import SQL.SQLResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainPanel extends JFrame {
    private JTextArea InputTextArea;
    private JButton SendMessageButton;
    private JTextArea MessagesTextArea;
    private JPanel RoomsPanel;
    private JPanel mPanel;
    private JPanel RoomJoinPanel;
    private JFormattedTextField RoomJoinTextArea;
    private JButton RoomJoinButton;
    private JLabel RoomJoinLabel;
    private JLabel InfoLabel;
    private int period = 250;
    private ScheduledExecutorService executor;

    private Room activeRoom = null;
    private int status_of_new_room = -1;

    public MainPanel() {
        setTitle("Main Panel");
        setSize(600,500);
        add(mPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        SendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(InputTextArea.getText() != ""){
                    //System.out.println(GlobalVariables.activeRoomID.toString());
                    //System.out.println(GlobalVariables.userID.toString());
                    //System.out.println(InputTextArea.getText());
                    //boolean sended = SQLConnector.SendMessage(GlobalVariables.activeRoomID, GlobalVariables.userID, InputTextArea.getText());
                    if(activeRoom!=null){
                        activeRoom.SendMessage(InputTextArea.getText());
                    }
                    InputTextArea.setText("");
                }
            }
        });

        CreateRooms();

        InputTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == '\n'){
                    //System.out.println("Enter Clicked");
                    if(activeRoom!=null){
                        String txt = InputTextArea.getText().replace("\n", "");
                        activeRoom.SendMessage(txt);
                    }
                    InputTextArea.setText("");
                }
            }
        });

        RoomJoinTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() != '\n') {
                    CheckData();
                }
                else {
                    RoomJoinButton.doClick();
                    RoomJoinTextArea.setText("");
                }
            }
        });

        RoomJoinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if (status_of_new_room == 0){
                        SQLConnector.AddNewRoomMember(SQLConnector.GetRoomID(RoomJoinTextArea.getText()), GlobalVariables.userID);
                        RoomsPanel.removeAll();
                        CreateRooms();
                        RoomJoinTextArea.setText("");
                        InfoLabel.setText("You joined to room!");
                    }
                    else if (status_of_new_room == 1){
                        SQLConnector.AddNewRoom(RoomJoinTextArea.getText(), GlobalVariables.userID);
                        SQLConnector.AddNewRoomMember(SQLConnector.GetRoomID(RoomJoinTextArea.getText()), GlobalVariables.userID);
                        RoomsPanel.removeAll();
                        CreateRooms();
                        RoomJoinTextArea.setText("");
                        InfoLabel.setText("You created new room!");
                    }
                    else{
                        InfoLabel.setText("Incorrect data");
                    }
            }
        });
    }

    private void CreateRooms(){
        SQLResult res = SQLConnector.GetUserRooms(GlobalVariables.userID);
        RoomsPanel.setLayout(new GridBagLayout());

        //Stworzenie fabryki obiektow RoomPackage
        RoomCreator roomCreator = new RoomCreator();
        for(int i = 0 ; i<res.resultList.size(); i++){
            //RoomPackage
            final Room newroom = roomCreator.Create(RoomType.Group,res.resultList.get(i).get(1), Integer.parseInt(res.resultList.get(i).get(0)));

            JButton newbtn = new JButton();
            newbtn.setVisible(true);
            newbtn.setText(newroom.GetName());
            //MessagesTextArea.setText(MessagesTextArea.getText() + "\n" + res.resultList.get(i).get(1));
            newbtn.setSize(200,50);
            newbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //System.out.println(newroom.name);
                    //newroom.SendMessage(InputTextArea.getText());
                    activeRoom = newroom;
                    //System.out.println("Active room: " + activeRoom.GetName());
                    //System.out.println(activeRoom.getMessageManager().GetMessages().size());
                    if(executor!= null)
                        executor.shutdown();
                    executor = Executors.newSingleThreadScheduledExecutor();
                    executor.scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            ShowMessages(activeRoom.getMessageManager().GetMessages());
                        }
                    }, 0, period, TimeUnit.MILLISECONDS);
                    //exec.shutdown();

                }
            });
            RoomsPanel.add(newbtn);
        }
    }

    private void ShowMessages(List<Message> messages){
        MessagesTextArea.setText("");
        String text = "";
        for(int i = 0 ; i<messages.size(); i++){
            text += messages.get(i).getIndex() + ". " + messages.get(i).getUserName() + ": " + messages.get(i).getText() + "\n";
        }

        MessagesTextArea.setText(text);
        MessagesTextArea.setCaretPosition(MessagesTextArea.getDocument().getLength());
    }

    private void CheckData(){
        //najlepiej zeby to byla klasa i by u gory mozna bylo poczekac na zakonczenie tego watku bo to moze rodzic probelmy, np da sie zrobic drugi taki samy pokoj jak sie jest szynkich i watek nie zdazy sie zakonczyc
        //takze wystraczy klasa i meotda np join DO ZROBIENIA
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(RoomJoinTextArea.getText().isEmpty()) { //sprawdz pozniej spacje!
                    status_of_new_room = -1;
                    InfoLabel.setText("Join or create room");
                }
                else if(RoomJoinTextArea.getText().length() > 16){
                    status_of_new_room = -1;
                    InfoLabel.setText("Name of room is too long ");
                }
                else if(SQLConnector.CheckIfRoomExist(RoomJoinTextArea.getText())) {
                    boolean user_has_that_room = false;
                    SQLResult res = SQLConnector.GetRoomMembersIDs(SQLConnector.GetRoomID(RoomJoinTextArea.getText()));
                    for(int i = 0 ; i < res.resultList.size(); i++)
                        if(res.resultList.get(i).get(0).equals(GlobalVariables.userID.toString()))
                            user_has_that_room = true;

                    if(user_has_that_room) {
                        status_of_new_room = -1;
                        InfoLabel.setText("You already has that room!");
                    }
                    else{
                        status_of_new_room = 0;
                        InfoLabel.setText("Join to existing room");
                    }
                }
                else if(!SQLConnector.CheckIfRoomExist(RoomJoinTextArea.getText())){
                    status_of_new_room = 1;
                    InfoLabel.setText("Create a new room");
                }

            }
        }).start();
    }

}
