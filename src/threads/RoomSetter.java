package threads;

import java.util.List;
import room.Room;

public class RoomSetter extends Thread {
	// constructor
	public RoomSetter(List<Room> rooms, List<Room> memRooms) {
		this.rooms = rooms;
		this.memRooms = memRooms;
	}
	
	// public methods
	public void run() {
		for (Room memRoom : memRooms) 
			memRoom.setNeighbouringRooms(memRooms, Room.findRoom(rooms, memRoom.getNumber()));
	}
	
	public List<Room> getMemRooms() {
		return memRooms;
	}
	
	// members
	List<Room> rooms;
	List<Room> memRooms;
}
