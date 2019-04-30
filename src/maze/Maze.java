package maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import border.Border;
import border.Door;
import border.OpenBorder;
import mazeutil.Direction;
import observer.RiddleSubject;
import riddle.Riddle;
import riddle.RiddleFactory;
import room.Room;
import threads.RoomSetter;
import threads.BorderSetter;


public class Maze {
	// constructor
	public Maze(Map<String, RiddleFactory> factoryMap) {
		rooms = new ArrayList<Room>();
		this.subjectMap = new HashMap<String, RiddleSubject>();
		for (String key : factoryMap.keySet()) 
			this.subjectMap.put(key, new RiddleSubject(factoryMap.get(key).build()));
	}
	
	// memento
	public class Memento {
		Memento() {
			// create subject map		
			memSubjectMap = cloneSubjectMap();
			
			// clone rooms 
			memRooms = new ArrayList<Room>();
			for (Room room : rooms) {
				Room memRoom = room.clone();
				memRoom.setSubject(memSubjectMap.get("Room"));
				memRooms.add(memRoom);
				
				if (room == currentRoom)
					memCurrentRoom = memRoom;
			}
			
			// set neighbouring rooms and borders
			RoomSetter thread1 = new RoomSetter(rooms, memRooms);
			BorderSetter thread2 = new BorderSetter(rooms, memRooms, memSubjectMap);
			thread1.start();
			thread2.start();
			try {
				thread1.join();
				thread2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			memRooms = thread1.getMemRooms();
			memRooms = thread2.getMemRooms();
			
			// notify the observers of all subjects
			for (String key : memSubjectMap.keySet())
				memSubjectMap.get(key).notifyObserver();
		}
		
		// private functions
		private void getState() {
			currentRoom = memCurrentRoom;
			rooms = memRooms;
			for (String key : memSubjectMap.keySet()) 
				subjectMap.replace(key, memSubjectMap.get(key));
		}
		
		private Map<String, RiddleSubject> cloneSubjectMap() {
			// initialize boolean map which checks if keys in subjectMap have already been considered
			Map<String, Boolean> isConsidered = new HashMap<String, Boolean>();
			for (String key : subjectMap.keySet()) 
				isConsidered.put(key, new Boolean(false));
		
			// create subject map
			Map<String, RiddleSubject> clonedSubjectMap = new HashMap<String, RiddleSubject>();
			for (String key : subjectMap.keySet()) 
				if (!isConsidered.get(key).booleanValue()) {
					RiddleSubject subject = subjectMap.get(key).clone(); // clone subject
					
					clonedSubjectMap.put(key, subject);
					isConsidered.replace(key, true);
					
					// check if subject should be shared and share it if necessary
					for (String otherKey : clonedSubjectMap.keySet())
						if (key.compareTo(otherKey) != 0 &&
								subjectMap.get(key).getRiddle() == subjectMap.get(otherKey).getRiddle()) {
							clonedSubjectMap.put(otherKey, subject);
							isConsidered.replace(key, true);
						}
				}
			
			return clonedSubjectMap;
		}

		// members
		private Room memCurrentRoom;
		private List<Room> memRooms;
		private Map<String, RiddleSubject> memSubjectMap;
	}
	
	public Maze.Memento getMemento() {
		return new Memento();
	}
	
	public final void setMemento(Maze.Memento memento) {
		memento.getState();
	}
	
	// add maze elements
	public boolean addRoom(int number) {
		Room room = Room.findRoom(rooms, number);
		if (room != null)
			return false;
		
		if (rooms.isEmpty()) {
			room = new Room(number, true, subjectMap.get("Room"), subjectMap.get("Wall"));
			currentRoom = room;
		} else
			room = new Room(number, subjectMap.get("Room"), subjectMap.get("Wall"));
		rooms.add(room);
		
		return true;
	}
	
	public boolean addDoor(int number1, Direction direction1, int number2, Direction direction2) {	
		// check if both rooms exists 
		Room room1 = Room.findRoom(rooms, number1);
		Room room2 = Room.findRoom(rooms, number2);
		if (room1 == null || room2 == null)
			return false;

		setBorder(room1, direction1, room2, direction2, new Door(subjectMap.get("Door")));	
		return true;
	}
	
	public boolean addOpenBorder(int number1, Direction direction1, int number2, Direction direction2) {
		Room room1 = Room.findRoom(rooms, number1);
		Room room2 = Room.findRoom(rooms, number2);
		if (room1 == null || room2 == null)
			return false;
		
		setBorder(room1, direction1, room2, direction2, new OpenBorder());
		return true;
	}
	
	// getters and setters
	public Room getCurrentRoom() {
		return currentRoom;
	}
	
	public void setRiddle(String key, Riddle riddle) {
		if (subjectMap.containsKey(key))
			subjectMap.get(key).setRiddle(riddle);
	}
	
	// other public methods
	/**
	 * determines if maze is solved. if a riddle is solved it is replaced with an unsolved one.
	 * 
	 * @return true if all riddles are solved or if maze is completely uncovered, false otherwise.
	 */
		
	public boolean isSolved() {
		// set the first solved riddle to an unsolved one
		Riddle solvedRiddle = null;
		Riddle unsolvedRiddle = null;
		for (String key : subjectMap.keySet()) {
			Riddle currentRiddle = subjectMap.get(key).getRiddle();
			if (currentRiddle.isSolved() && unsolvedRiddle != null) {
				currentRiddle = unsolvedRiddle; 
				continue;
			} else if (!currentRiddle.isSolved() && solvedRiddle != null) {
				solvedRiddle = currentRiddle; 
				continue;
			}
		} 
		
		return areAllRiddlesSolved() || areAllRoomsUncovered();
	}

	protected boolean areAllRoomsUncovered() {
		boolean allRoomsUncovered = true;
		for (Room room : rooms)
			if (!room.isUncovered()) {
				allRoomsUncovered = false;
				continue;
			}
		return allRoomsUncovered;
	}

	private boolean areAllRiddlesSolved() {
		boolean allRiddlesSolved = true;
		for (String key : subjectMap.keySet()) 
			if (!subjectMap.get(key).getRiddle().isSolved()) {
				allRiddlesSolved = false;
				continue;
			}
		return allRiddlesSolved;
	}

	public boolean enter(Direction direction, Scanner input) {
		currentRoom = currentRoom.enter(direction, input);
		return currentRoom != null;
	}
		
	// print stuff
	public void printShort() {
		for (Room room : rooms) 
			room.printShort();	
	}
	
	public void print() {
		for (Room room : rooms) 
			room.print();
	}
	
	// private methods
	private static void setBorder(Room room1, Direction direction1, Room room2, Direction direction2, Border border) {
		room1.setNeighbouringBorder(direction1, border);
		room1.setNeighbouringRoom(direction1, room2);
		room2.setNeighbouringBorder(direction2, border);
		room2.setNeighbouringRoom(direction2, room1);
	}
	
	// members
	private Room currentRoom;
	private List<Room> rooms;
	private Map<String, RiddleSubject> subjectMap;
}
