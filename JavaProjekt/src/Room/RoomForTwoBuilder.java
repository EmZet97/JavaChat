package Room;

public class RoomForTwoBuilder implements RoomBuilder {
    @Override
    public Room CreateRoom(Integer roomId) {
        Room newRoom = new Room(RoomType.forTwo, "new_room");

        return newRoom;
    }
}
