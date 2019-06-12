package Messages;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageManagerBuilderTest {
    @Test
    public void ManagerRoomId() throws Exception{
        Integer id = 123;
        MessageManagerBuilder mmb = new MessageManagerBuilder(id, 1);

        Assert.assertEquals(mmb.getRoomID(), id);
    }

    @Test
    public void ManagerOwnerId() throws Exception{
        Integer id = 123;
        MessageManagerBuilder mmb = new MessageManagerBuilder(1, id);

        Assert.assertEquals(mmb.getUserID(), id);
    }

    @Test
    public void ManagerRefreshRate() throws Exception{
        Integer rr = 125;
        MessageManagerBuilder mmb = new MessageManagerBuilder(1, 1);
        mmb.SetRefreshRate(rr);

        Assert.assertEquals(mmb.getRefteshRate(), rr);
    }

    @Test
    public void ManagerDefaultRefreshRate() throws Exception{
        Integer rr = 250; //default
        MessageManagerBuilder mmb = new MessageManagerBuilder(1, 1);

        Assert.assertEquals(mmb.getRefteshRate(), rr);
    }

}