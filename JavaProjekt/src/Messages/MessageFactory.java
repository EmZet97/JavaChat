package Messages;

public class MessageFactory {

    public Message getClone(Message sample) {
        return sample.cloneMessage();
    }

}
