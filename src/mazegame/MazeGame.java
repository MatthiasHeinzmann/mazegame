package mazegame;

import maze.*;
import mazebuilder.*;
import riddle.*;
import mazeutil.Direction;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MazeGame {
	public static Maze constructMaze(MazeBuilder builder, int numberOfRooms, double percentageDoor) {
		// build maze
		builder.buildMaze();
		
		// build rooms
		for (int i = 0; i < numberOfRooms; i++)dfdfd
			builder.buildRoom(i);
	
		// build doors and open borders
		//Random random = new Math.();
		for (int i = 0; i < numberOfRooms; i++) 
			for (int j = i + 1; j < numberOfRooms; j++) {
				double randomNumber = Math.random();
				if (j-i == 1 && i%2 == 0) 
					if (randomNumber > percentageDoor)
						builder.buildOpenBorder(i, Direction.EAST, j, Direction.WEST);
					else
						builder.buildDoor(i, Direction.EAST, j, Direction.WEST);
				else if (j-i == 2)
					if (randomNumber > percentageDoor)
						builder.buildOpenBorder(i, Direction.SOUTH, j, Direction.NORTH);
					else
						builder.buildDoor(i, Direction.SOUTH, j, Direction.NORTH);
				else
					continue;
			}
	
		return builder.getResult();
	}
	
	public static void main(String[] args) {
		// construct maze where riddles are constructed through factories
		RiddleFactory roomFactory = new NumberFactory();
		RiddleFactory doorFactory = new SecretFactory();
		RiddleFactory wallFactory = new NoRiddleFactory();
		
		Map<String, RiddleFactory> factoryMap = new HashMap<String, RiddleFactory>();
		factoryMap.put("Room", roomFactory);
		factoryMap.put("Door", doorFactory);
		factoryMap.put("Wall", wallFactory);
		
		MazeBuilder builder = new MazeBuilder(factoryMap);
		Maze maze = constructMaze(builder, 4, 0.6);
		
		// optional: set new riddle subjects explicitly
		Riddle riddle = (new NumberFactory()).build();
		maze.setRiddle("Room", riddle);
		maze.setRiddle("Door", riddle);
		maze.setRiddle("Wall", riddle);
		
		// get memento
		Maze.Memento memento = maze.getMemento();
		
		// uncover the maze
		Scanner input = new Scanner(System.in);
		while (!maze.isSolved()) {
			System.out.println("uncovered maze so far: ");
			maze.printShort();
			System.out.println("\ncurrent room: ");
			maze.getCurrentRoom().print();
			
		    System.out.print("\ngive an instruction: " );
		    String text = input.nextLine();
		    switch (text) {
			    case "go east": {
			    	if (maze.enter(Direction.EAST, input))
			    		continue;
			    	else {
			    		System.out.println("You lost!");
			    		break;
			    	}
			    }
			    
			    case "go west": {
			    	if (maze.enter(Direction.WEST, input))
			    		continue;
			    	else {
			    		System.out.println("You lost!");
			    		break;
			    	}
			    }
			    
			    case "go north": {
			    	if (maze.enter(Direction.NORTH, input))
			    		continue;
			    	else {
			    		System.out.println("You lost!");
			    		break;
			    	}
			    }
			    
			    case "go south": {
			    	if (maze.enter(Direction.SOUTH, input)) 
			    		continue;
			    	else {
			    		System.out.println("You lost!");
			    		break;
			    	}
			    }
			    
			    case "memento": {
			    	Maze.Memento altMemento = maze.getMemento();
			    	maze.setMemento(memento);
			    	memento = altMemento;
			    	continue;
			    }
			    
			    case "quit": break;
			    default: continue;
		    }
			
		    // introducing some white lines
		    System.out.println("\n");
		}
		
		input.close();	
		System.out.println("" + "Congratulations, you did succeed!");
	}
}
