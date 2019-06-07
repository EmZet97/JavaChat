package Messages;

import SQL.SQLConnector;
import SQL.SQLResult;
import SQL.SQL_Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageManager implements MessageManagerInterface {

    private Integer lastMessageId = 0;
    private List<Message> messages;
    private Integer roomID;
    private Integer userID;
    private Message message_pattern;
    private Integer refreshRate;

    public MessageManager(Integer roomID, Integer userID, Integer refreshRate) {
        this.roomID = roomID;
        this.userID = userID;
        this.message_pattern = new Message(roomID, 0);
        this.refreshRate = refreshRate;
        messages = new ArrayList<Message>();
        MessageListener();
    }

    private void MessageListener(){

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                CheckChanges();
            }
        }, 0, refreshRate, TimeUnit.MILLISECONDS);
        //exec.shutdown();

    }

    @Override
    public int GetLastMassageId() {
        return this.lastMessageId;
    }

    @Override
    public List<Message> GetMessages() {
        return this.messages;
    }

    @Override
    public boolean SendMessage(String message) {
        return SQLConnector.SendMessage(this.roomID, this.userID, message);
    }

    @Override
    public void CheckChanges() {
        SQLResult res = SQLConnector.GetMessages(this.roomID, this.lastMessageId);
        if(res.status == SQL_Status.QueryPass) {
            List<List<String>> newMesseges = res.resultList;

            if (newMesseges.size() > 0) {
                //Rozszerzanie listy wiadomosci
                for (int i = 0; i < newMesseges.size(); i++) {
                    String userName = newMesseges.get(i).get(1);
                    String text = newMesseges.get(i).get(3);
                    Message n_mes = this.message_pattern.cloneMessage();
                    n_mes.setUserName(userName);
                    n_mes.setText(text);
                    messages.add(n_mes);
                }
                //Ustawienie indeksu ostatniej wiadomosci
                int last_index_row = newMesseges.size() - 1;
                if (this.lastMessageId != Integer.parseInt(newMesseges.get(last_index_row).get(2))) {
                    this.lastMessageId = Integer.parseInt(newMesseges.get(last_index_row).get(2));
                }
            }
        }
    }
}
