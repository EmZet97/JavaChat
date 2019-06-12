package Rooms;

import Globals.GlobalVariables;
import Messages.MessageManager;
import SQL.SQLConnector;
import Users.User;

import java.util.List;

public class Room_PTP implements Room {

    private String name = "Global";
    private MessageManager messageManager = null;
    private Integer roomID = 1;
    private List<User> users;
    private RoomType roomType = RoomType.forTwo;
    public Room_PTP(String name, MessageManager messageManager, Integer roomID){

    }


    public void LoadUsers() {

    }


    public void RefreshRoom() {

    }


    public RoomType getRoomType() {
        return roomType;
    }


    public List<User> GetUsers() {
        return null;
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
