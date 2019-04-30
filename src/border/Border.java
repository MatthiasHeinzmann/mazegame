package border;

import java.util.Scanner;

public interface Border extends Cloneable {
	public boolean unlock(Scanner input);
	public boolean isUnlocked();
	public String toString();
	public Border clone();
}
