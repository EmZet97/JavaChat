package Messages;

import SQL.SQLConnector;
import java.util.List;

public class MessageManagerBuilder {

    private Integer roomID;
    private Integer userID;
    private Integer refteshRate = 250;

    public MessageManagerBuilder(Integer roomID, Integer userID) {
        this.roomID = roomID;
        this.userID = userID;
    }

    public MessageManager build() {
        return new MessageManager(this.roomID, this.userID, this.refteshRate);
    }

    public void SetRefreshRate(Integer time){
        this.refteshRate = time;
    }

    public Integer getRefteshRate() {
        return refteshRate;
    }

    public Integer getUserID() {
        return userID;
    }

    public Integer getRoomID() {
        return roomID;
    }
}