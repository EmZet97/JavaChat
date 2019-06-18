package RoomPackage;

import Globals.GlobalVariables;
import Messages.MessageManager;
import SQL.SQLConnector;
import SQL.SQLResult;
import Users.User;

import java.util.List;

public class Room_Group implements Room {
    private String name = "Global";
    private MessageManager messageManager;
    private Integer roomID = 1;
    private List<User> users;
    private RoomType roomType = RoomType.Group;


    public Room_Group(String name, MessageManager messageManager, Integer roomID){
        this.name = name;
        this.messageManager = messageManager;
        this.roomID = roomID;
    }

    @Override
    public void LoadUsers() {
        SQLResult res = SQLConnector.GetRoomMembers(this.roomID);
        for (int i=0; i<res.resultList.size(); i++){
            String name_n = res.resultList.get(i).get(1);
            Integer id_n = Integer.parseInt(res.resultList.get(i).get(0));
            if(CheckIfExist(id_n)) {
                User u = new User(name_n, id_n);
                users.add(u);
            }
        }
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    //Sprawdzanie czy user istnieje w pokoju
    private boolean CheckIfExist(Integer id){
        for (User u:users) {
            if(u.getId()==id)
                return true;
        }
        return false;
    }

    @Override
    public void RefreshRoom() {
        LoadUsers();
    }

    @Override
    public List<User> GetUsers() {
        return users;
    }

    @Override
    public String GetName() {
        return name;
    }

    @Override
    public Integer GetID() {
        return roomID;
    }

    @Override
    public void SendMessage(final String text) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SQLConnector.SendMessage(roomID, GlobalVariables.userID, text);
            }});
        t.start();
    }

    @Override
    public MessageManager getMessageManager() {
        return messageManager;
    }
}
