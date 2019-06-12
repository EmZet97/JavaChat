package Messages;


public class Message implements MessageInterface {

    private String text;
    private String userName;
    private String date;
    private Integer roomID;
    private Integer index = 0;


    public Message(String userName) {
        this.userName = userName;
    }

    public Message(Integer roomID, Integer index) {
        this.roomID = roomID;
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }

    public Integer getIndex() {
        return index;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Message cloneMessage() {

        Message messageObject = null;
        try {
            messageObject = (Message) super.clone();
            this.index+=1;
        }
        catch (CloneNotSupportedException err) {
            err.printStackTrace();
            //todo exception
        }

        return messageObject;
    }
}
