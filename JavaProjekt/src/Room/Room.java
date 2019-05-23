package Room;

import Messages.MessageManager;
import Messages.MessageManagerInterface;

import java.util.List;

public class Room {
    public RoomType type;
    public String name;
    public Integer roomId;
    public List<Integer> usersIDs;
    public List<String> usersNames;
    private MessageManager messageManager;

    public Room(RoomType roomtype, String name, Integer roomId){
        this.type = roomtype;
        this.name = name;
        this.roomId = roomId;
    }
    public void AddUser(Integer userID, String userName){
        this.usersIDs.add(userID);
        this.usersNames.add(userName);
    }
    public void RefreshRoom(){
        //SQLResult res = SQLConnector.GetMessages(GlobalVariables.activeRoomID, lastMessageID);

        //for(int i = 0 ; i < res.resultList.size(); i++){
            //messages+= res.resultList.get(i).get(0) + ": " + res.resultList.get(i).get(1) + "\n";
            //lastMessageID = Integer.parseInt(res.resultList.get(i).get(2));
        //}
    }
}
