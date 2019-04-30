package riddle;

public class NumberFactory implements RiddleFactory {
	public Riddle build() {
		return new Number();
	}

}
