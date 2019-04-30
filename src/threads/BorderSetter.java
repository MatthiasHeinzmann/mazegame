package threads;

import java.util.List;
import java.util.Map;
import border.Border;
import border.Door;
import border.Wall;
import mazeutil.Direction;
import observer.RiddleObserver;
import observer.RiddleSubject;
import room.Room;

public class BorderSetter extends Thread {
	// constructor
	public BorderSetter(List<Room> rooms, List<Room> memRooms, Map<String, RiddleSubject> memSubjectMap) {
		this.rooms = rooms;
		this.memRooms = memRooms;
		this.memSubjectMap = memSubjectMap;
	}

	// public methods
	public void run() {
		setBorders();
	}
	
	public List<Room> getMemRooms() {
		return memRooms;
	}
	
	// private methods
	private void setBorders() { 
		boolean isBorderConsidered[][] = new boolean[rooms.size()][4];
		for (Room memRoom : memRooms) {
			Room room = Room.findRoom(rooms, memRoom.getNumber());
			
			// set all neighbouring borders of room
			for (Direction direction : Direction.values()) {
				if (!isBorderConsidered[memRooms.indexOf(memRoom)][direction.getValue()]) {
					// create border by cloning
					Border border = room.getNeighbouringBorder(direction).clone();
					if (border instanceof RiddleObserver) {
						if (border instanceof Door)
							((RiddleObserver) border).setSubject(memSubjectMap.get("Door"));
						else if (border instanceof Wall)
							((RiddleObserver) border).setSubject(memSubjectMap.get("Wall"));
					}	
					
					// set border
					memRoom.setNeighbouringBorder(direction, border);
					isBorderConsidered[memRooms.indexOf(memRoom)][direction.getValue()] = true;
					
					// set border of opposing room
					Room oppositeMemRoom = memRoom.getNeighbouringRoom(direction);
					if (oppositeMemRoom != null) {
						Direction oppositeDirection = 
								Direction.getDirection((direction.getValue() + 2) % 4);
						oppositeMemRoom.setNeighbouringBorder(oppositeDirection, border);
						isBorderConsidered[memRooms.indexOf(oppositeMemRoom)]
								[oppositeDirection.getValue()] = true;
					}
				}
			}
		}
	}
	
	// members
	private List<Room> rooms;
	private List<Room> memRooms;
	private Map<String, RiddleSubject> memSubjectMap;
}
