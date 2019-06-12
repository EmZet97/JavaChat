package Rooms;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomCreatorTest {
    @Test
    public void RoomCreatorType() throws Exception{
        RoomCreator rc = new RoomCreator();
        Room room = rc.Create(RoomType.Group, "test name", 1);

        Assert.assertEquals(room.getRoomType(), RoomType.Group);
    }

    @Test
    public void RoomCreatorName() throws Exception{
        String name = "%test@#*&";
        RoomCreator rc = new RoomCreator();
        Room room = rc.Create(RoomType.Group, name, 1);

        Assert.assertEquals(room.GetName(), name);
    }

    @Test
    public void RoomCreatorID() throws Exception{
        Integer id = 123;
        RoomCreator rc = new RoomCreator();
        Room room = rc.Create(RoomType.Group, "test", id);

        Assert.assertEquals(room.GetID(), id);
    }
}