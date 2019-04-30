package riddle;

public class SecretFactory implements RiddleFactory {
	public Riddle build() {
		return new Secret();
	}

}
