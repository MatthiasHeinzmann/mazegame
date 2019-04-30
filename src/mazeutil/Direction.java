package mazeutil;

public enum Direction {
	NORTH(0), EAST(1), SOUTH(2), WEST(3);
	private final int value;
	
	private Direction(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static Direction getDirection(int value) {
		for (Direction direction : Direction.values())
			if (direction.getValue() == value)
				return direction;
		
		return null;
	}
}
