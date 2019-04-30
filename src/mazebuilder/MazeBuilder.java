package mazebuilder;

import java.util.Map;
import maze.Maze;
import mazeutil.Direction;
import riddle.RiddleFactory;

public class MazeBuilder {
	public MazeBuilder(Map<String, RiddleFactory> factoryMap) {
		this.factoryMap = factoryMap;
	}
	
	public void buildMaze() {
		maze = new Maze(factoryMap);
	}
	
	public boolean buildRoom(int number) {
		if (maze.addRoom(number))
			return true;
		else
			return false;
	}
	
	public boolean buildOpenBorder(int number1, Direction direction1, int number2, Direction direction2) {
		if (maze.addOpenBorder(number1, direction1, number2, direction2))
			return true;
		else 
			return false;
	}
	
	public boolean buildDoor(int number1, Direction direction1, int number2, Direction direction2) {
		if (maze.addDoor(number1, direction1, number2, direction2))
			return true;
		else 
			return false;
	}
	
	public Maze getResult() {
		return maze;
	}
	
	// members
	private Maze maze;
	private Map<String, RiddleFactory> factoryMap;
}
