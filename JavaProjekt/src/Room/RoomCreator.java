package Room;

import Globals.GlobalVariables;
import Messages.MessageManager;

public class RoomCreator {
    private String name;
    private Integer id;
    private MessageManager messageManager;

    public RoomCreator(){

    }

    public Room Create(RoomType roomType, String name, Integer id){
        Room room;
        this.name = name;
        this.id = id;
        this.messageManager = new MessageManager(id, GlobalVariables.userID);

        if(roomType == RoomType.Group){
            room = new GroupRoom(name, messageManager,id);
        }
        else{
            room = new GroupRoom(name, messageManager,id);
        }

        return room;
    }
}
