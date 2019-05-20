package Room;

import java.util.List;

public class Room {
    public RoomType type;
    public String name;
    public List<Integer> usersIDs;
    public List<String> usersNames;

    public Room(RoomType roomtype, String name){
        this.type = roomtype;
        this.name = name;
    }
    public void AddUser(Integer userID, String userName){
        this.usersIDs.add(userID);
        this.usersNames.add(userName);
    }
}
