package Rooms;

import Messages.MessageManager;
import Users.User;

import java.util.List;

public interface Room {

    public void LoadUsers();
    public void RefreshRoom();
    public List<User> GetUsers();
    public String GetName();
    public Integer GetID();
    public void SendMessage(String text);
    public MessageManager getMessageManager() ;
    public RoomType getRoomType();
}
