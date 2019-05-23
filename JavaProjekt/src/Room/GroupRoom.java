package Room;

import Globals.GlobalVariables;
import Messages.Message;
import Messages.MessageManager;
import SQL.SQLConnector;
import SQL.SQLResult;
import Users.User;

import java.util.List;

public class GroupRoom implements Room {
    private String name = "Global";
    private MessageManager messageManager = null;
    private Integer roomID = 1;
    private List<User> users;


    public GroupRoom(String name, MessageManager messageManager, Integer roomID){
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
    public void SendMessage(String text) {
        SQLConnector.SendMessage(roomID, GlobalVariables.userID, text);
    }
}
