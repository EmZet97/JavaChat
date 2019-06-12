package RoomPackage;

import Globals.GlobalVariables;
import Messages.MessageManager;
import Messages.MessageManagerBuilder;

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
        //Budowanie obiektu message manager
        this.messageManager = new MessageManagerBuilder(id, GlobalVariables.userID).build();

        if(roomType == RoomType.Group){
            room = new Room_Group(name, messageManager,id);
        }
        else{
            room = new Room_Group(name, messageManager,id);
        }

        return room;
    }
}
