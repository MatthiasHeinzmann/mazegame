package room;

public class House {
	public static class Door {
		private boolean isLocked;
		
		public Door(boolean isLocked) {
			this.isLocked = isLocked;
		}
		
		public boolean isLocked() {
			return isLocked;
		}

		public void setLocked(boolean isLocked) {
			this.isLocked = isLocked;
		}
	}
	
	private Door entry;
	private String name;
	
	public House(boolean isLocked, String name) {
		this.entry = new Door(isLocked);
		this.name = name;
	}
	
	public Door getEntry() {
		return entry;
	}

	public void setEntry(Door entry) {
		this.entry = entry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "house with name " + name + " and " + entry.isLocked() + " door."; 
	}

	public static void main(String[] args) {
		Door myOpenDoor = new Door(false);
		House myClosedHouse = new House(true, "Matthias");
		
		Door myHouseDoor = myClosedHouse.getEntry();
		myHouseDoor = myOpenDoor;
		
		myClosedHouse.setEntry(myHouseDoor);
		System.out.println(myClosedHouse);	
		
		myOpenDoor.setLocked(true);
		System.out.println(myClosedHouse);	
		
	}
}
