package GUI;

import Globals.GlobalVariables;
import Room.Room;
import Room.RoomType;
import SQL.SQLConnector;
import SQL.SQLResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JFrame {
    private JTextArea InputTextArea;
    private JButton SendMessageButton;
    private JTextArea MessagesTextArea;
    private JPanel RoomsPanel;
    private JPanel mPanel;

    public MainPanel() {
        setTitle("Main Panel");
        setSize(600,500);
        add(mPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        SendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(InputTextArea.getText() != ""){
                    boolean sended = SQLConnector.SendMessage(GlobalVariables.activeRoomID, GlobalVariables.userID, InputTextArea.getText());
                }
            }
        });

        CreateRooms();
    }

    private void CreateRooms(){
        SQLResult res = SQLConnector.GetUserRooms(GlobalVariables.userID);
        RoomsPanel.setLayout(new GridBagLayout());
        for(int i = 0 ; i<res.resultList.size(); i++){
            Room newroom = new Room(RoomType.Group,res.resultList.get(i).get(1), Integer.parseInt(res.resultList.get(i).get(0)));
            JButton newbtn = new JButton();
            newbtn.setVisible(true);
            newbtn.setText(res.resultList.get(i).get(1));
            MessagesTextArea.setText(MessagesTextArea.getText() + "\n" + res.resultList.get(i).get(1));
            newbtn.setSize(200,50);
            newbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(newroom.name);

                }
            });
            RoomsPanel.add(newbtn);
        }
    }
}
