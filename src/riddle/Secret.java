package riddle;

import java.util.Random;
import java.util.Scanner;

public class Secret implements Riddle {
	// constructors
	public Secret() {
		this.word = chooseSecret();
		this.knownWord = new StringBuffer(word.replaceAll(".", "-"));
	}
	
	private Secret(Secret anotherSecret) {
		this.word = anotherSecret.word;
		this.knownWord = new StringBuffer(anotherSecret.knownWord);
		this.numberOfTrials = anotherSecret.numberOfTrials;
	}
	
	// public methods
	public Secret clone() {
		return new Secret(this);
	}
	
	public boolean tryToSolve(Scanner input) {
		boolean solved = false;
		
		if (numberOfTrials > 0) {
			System.out.println("You are prompted to find part of the secret.");
			System.out.println("current known word: " + getKnownSecret());
			System.out.println("current number of trials left: " + numberOfTrials);
			System.out.print("character to choose: ");
			char newChar = input.nextLine().charAt(0);
			
			if (isUnkownSecret(newChar)) {
				System.out.println("right choice...");
				updateKnownSecret(newChar);
				numberOfTrials = NUMBER_OF_TRIALS;
				solved = true;
			}
			else {
				System.out.println("wrong choice...");
				numberOfTrials--;	
			}
			
			System.out.println("updated known word: " + getKnownSecret());
			System.out.println("udpated number of trials left: " + numberOfTrials + '\n');
		}
		
		return solved;
	}
	
	public boolean isSolved() {
		return knownWord.toString().compareTo(word) == 0;
	}
	
	// private methods
	private String getKnownSecret() {
		return knownWord.toString();
	}
	
	private boolean isUnkownSecret(char newChar) {
		String newCharString = Character.toString(newChar);
		return knownWord.indexOf(newCharString) < 0 && word.indexOf(newCharString) >= 0;
	}
	
	private void updateKnownSecret(char newChar) {
		char currentChar;
		for (int i = 0; i < word.length(); i++) {
			currentChar = word.charAt(i);
			if (newChar == currentChar)
				knownWord.setCharAt(i, newChar);
		}
	}
	
	private static String chooseSecret() {
		Random rand = new Random();
		return words[rand.nextInt(words.length)];
	}
	
	// members
	private final String word;
	private StringBuffer knownWord;
	private int numberOfTrials = NUMBER_OF_TRIALS;;
	
	private static final int NUMBER_OF_TRIALS = 5;
	private static final String[] words = new String[] 
			{"familie", "haarwurzel", "gebirge", "mensch", "maddalena", "heilung"};
}
