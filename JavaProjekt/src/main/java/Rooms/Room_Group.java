package Rooms;

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


    public void RefreshRoom() {
        LoadUsers();
    }


    public List<User> GetUsers() {
        return users;
    }


    public String GetName() {
        return name;
    }


    public Integer GetID() {
        return roomID;
    }


    public void SendMessage(String text) {
        SQLConnector.SendMessage(roomID, GlobalVariables.userID, text);
    }


    public MessageManager getMessageManager() {
        return messageManager;
    }
}
