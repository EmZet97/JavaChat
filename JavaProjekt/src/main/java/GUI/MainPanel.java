package GUI;

import Globals.GlobalVariables;
import Messages.Message;
import Rooms.Room;
import Rooms.RoomCreator;
import Rooms.RoomType;
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
    private int period = 250;
    private ScheduledExecutorService executor;

    private Room activeRoom = null;

    public MainPanel() {
        setTitle("Main Panel");
        setSize(600,500);
        this.add(mPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        SendMessageButton.addActionListener(new ActionListener() {

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
            text+= messages.get(i).getIndex() + ". " + messages.get(i).getUserName() + ": " + messages.get(i).getText() + "\n";
        }

        MessagesTextArea.setText(text);
        MessagesTextArea.setCaretPosition(MessagesTextArea.getDocument().getLength());
    }
}
