package Messages;

import java.util.List;

public class MessageManager implements MessageManagerInterface {

    public MessageManager(Integer roomId, Integer userId){

    }

    @Override
    public boolean SendMessage(String message) {
        return false;
    }

    @Override
    public int GetLastMassageId() {
        return 0;
    }

    @Override
    public List<Message> GetMessages() {
        return null;
    }

    @Override
    public void CheckChanges() {

    }


}
