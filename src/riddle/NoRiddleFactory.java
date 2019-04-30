package riddle;

public class NoRiddleFactory implements RiddleFactory {
	public Riddle build() {
		return new NoRiddle();
	}

}
