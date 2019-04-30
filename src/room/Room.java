package room;

import java.util.List;
import java.util.Scanner;
import border.*;
import mazeutil.Direction;
import observer.RiddleObserver;
import observer.RiddleSubject;

public class Room extends RiddleObserver implements Cloneable {
	// constructors
	public Room(int number, RiddleSubject roomSubject, RiddleSubject wallSubject) {
		super(roomSubject);
		this.number = number;
		this.uncovered = false;
		this.neighbouringRooms = new Room[4];
		this.neighbouringBorders = new Border[] {new Wall(wallSubject),
				new Wall(wallSubject), new Wall(wallSubject), new Wall(wallSubject)};
	}
	
	public Room(int number, boolean uncovered) {
		this.number = number;
		this.uncovered = uncovered;
		this.neighbouringRooms = new Room[4];
		this.neighbouringBorders = new Border[4];
	}
	
	public Room(int number, boolean uncovered, RiddleSubject roomSubject, RiddleSubject wallSubject) {
		super(roomSubject);
		this.number = number;
		this.uncovered = uncovered;
		this.neighbouringRooms = new Room[4];
		this.neighbouringBorders = new Border[] {new Wall(wallSubject),
				new Wall(wallSubject), new Wall(wallSubject), new Wall(wallSubject)};
	}
	
	// getters
	public Room getNeighbouringRoom(Direction direction) {
		return neighbouringRooms[direction.getValue()];
	}
	
	public Border getNeighbouringBorder(Direction direction) {
		return neighbouringBorders[direction.getValue()];
	}
	
	public int getNumber() {
		return number;
	}
	
	public boolean isUncovered() {
		return uncovered;
	}
	
	// setters
	public void setNeighbouringRoom(Direction direction, Room room) {
		neighbouringRooms[direction.getValue()] = room;
	}
	
	public void setNeighbouringRooms(List<Room> rooms, Room room) {
		here: for (Direction direction : Direction.values()) 
			if (room.getNeighbouringRoom(direction) != null) 
				for (Room availableRoom : rooms) 
					if (availableRoom.getNumber() == room.getNeighbouringRoom(direction).getNumber()) {
						setNeighbouringRoom(direction, availableRoom);
						continue here;
					}
	}
	
	public void setNeighbouringBorder(Direction direction, Border border) {
		neighbouringBorders[direction.getValue()] = border;
	}
	
	public void setUncovered() {
		uncovered = true;
	}
			
	// other public methods
	public Room enter(Direction direction, Scanner input) {
		if (getNeighbouringBorder(direction).isUnlocked() || 
				getNeighbouringBorder(direction).unlock(input)) {
			Room room = getNeighbouringRoom(direction);
			if (!room.isUncovered())
				super.tryToSolve(input);
			room.setUncovered();
			
			return room;
		}
	
		return this;
	}
	
	/**
	 * creates a new Room for which the members "number" and "uncovered" coincide with "this" Room.<p>
	 * does not set any subject.
	 * 
	 * @return cloned "this" Room.
	 */
	public Room clone() {
		return new Room(number, uncovered);
	}
	
	public static Room findRoom(List<Room> rooms, int number) {
		for (Room room : rooms) 
			if (room.getNumber() == number) 
				return room;
		
		return null;
	}
	
	public void printShort() {
		if (uncovered) 
			System.out.println("room of " + super.getClass() + " and number " + number
					+ " with riddle of " + super.getRiddleClass());
	}
	
	public void print() {
		if (uncovered) {	
			printShort();
			if (neighbouringBorders != null) 
				for (Direction direction : Direction.values())
					System.out.println("\t" + direction.toString() +  ": " 
							+ neighbouringBorders[direction.getValue()].toString());
		}
	}

	// members
	private int number;
	private boolean uncovered;
	private Room[] neighbouringRooms;
	private Border[] neighbouringBorders;
}
