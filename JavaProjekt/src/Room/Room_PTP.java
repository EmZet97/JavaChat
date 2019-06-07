package Room;

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

    public Room_PTP(String name, MessageManager messageManager, Integer roomID){

    }

    @Override
    public void LoadUsers() {

    }

    @Override
    public void RefreshRoom() {

    }

    @Override
    public List<User> GetUsers() {
        return null;
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
    public void SendMessage(String text) {
        SQLConnector.SendMessage(roomID, GlobalVariables.userID, text);
    }

    @Override
    public MessageManager getMessageManager() {
        return messageManager;
    }
}
