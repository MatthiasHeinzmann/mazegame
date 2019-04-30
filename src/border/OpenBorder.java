package border;

import java.util.Scanner;

public class OpenBorder implements Border {
	public boolean isUnlocked() {
		return true;
	}
	
	public boolean unlock(Scanner input) {
		return true;
	}
	
	public Border clone() {
		return new OpenBorder();
	}
	
	public String toString() {
		return "open border";
	}
}
