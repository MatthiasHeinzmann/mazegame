package riddle;

import java.util.Random;
import java.util.Scanner;

public class Number implements Riddle {
	// constructors
	public Number() {
		Random rand = new Random();
		this.result = rand.nextInt((int) Math.pow(10, EXPONENT));
		this.summand1 = rand.nextInt(result);
		this.summand2 = result - summand1;
		this.knownSummand1 = new StringBuffer(Integer.toString(summand1).replaceAll(".", "-"));
		this.knownSummand2 = new StringBuffer(Integer.toString(summand2).replaceAll(".", "-"));
	}
	
	// constructors
	private Number(Number number) {
		this.result = number.result;
		this.summand1 = number.summand1;
		this.summand2 = number.summand2;
		this.knownSummand1 = new StringBuffer(number.knownSummand1.toString());
		this.knownSummand2 = new StringBuffer(number.knownSummand2.toString());
	}
	
	// public methods
	public Number clone() {
		return new Number(this);
	}

	public boolean tryToSolve(Scanner input) {
		boolean solved = false;
		
		if (numberOfTrials > 0) {
			System.out.println("You are prompted to find part of the arithmetics.");
			System.out.println("current arithmetics: " + knownSummand1 + " + " + knownSummand2 + " = " + result);
			System.out.println("current number of trials left: " + numberOfTrials);
			System.out.print("digit to choose: ");
			char digit = input.nextLine().charAt(0);
			
			if (isUnknownDigit(digit, knownSummand1, Integer.toString(summand1))) {
				System.out.println("right choice...");
				numberOfTrials = NUMBER_OF_TRIALS;
				solved = true;
			} else if (isUnknownDigit(digit, knownSummand2, Integer.toString(summand2))) {
				System.out.println("right choice...");
				numberOfTrials = NUMBER_OF_TRIALS;
				solved = true;
			} else {
				System.out.println("wrong choice...");
				numberOfTrials--;	
			}
			
			System.out.println("updated arithmetics: " + knownSummand1 + " + " + knownSummand2 + " = " + result);
			System.out.println("udpated number of trials left: " + numberOfTrials + '\n');
		}
		
		return solved;
	}

	public boolean isSolved() {
		return knownSummand1.toString().compareTo(Integer.toString(summand1)) == 0
			&& knownSummand2.toString().compareTo(Integer.toString(summand2)) == 0;
	}
	
	// private functions
	private static boolean isUnknownDigit(char digit, StringBuffer knownSummand, CharSequence summand) {
		for (int i = 0; i < summand.length(); i++) 
			if (summand.charAt(i) == digit && knownSummand.charAt(i) != digit) {
				knownSummand.setCharAt(i, digit);
				return true;
			}
		
		return false;
	}
	
	// members
	private int result, summand1, summand2;
	private int numberOfTrials = NUMBER_OF_TRIALS;
	private StringBuffer knownSummand1, knownSummand2;
	
	private static final int EXPONENT = 5;
	private static final int NUMBER_OF_TRIALS = 4;
	
	public static void main(String[] args) {
		Number number = new Number();
		Scanner input = new Scanner(System.in);
		while (!number.isSolved())
			number.tryToSolve(input);		
	}
	
	
}
