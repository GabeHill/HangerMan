package logic;

import java.util.ArrayList;

import lib.ConsoleIO;

public class Game {
	private static ArrayList<Character> alreadyGuessed = new ArrayList<Character>();
	private static String s;
	private static final String answer = s;
	private static char guess;
	private static int guessesLeft = 6;
	private static String[] man;

	private static void config() {
		char[] array = s.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if (array[i] != ' ') {
				array[i] = '_';
			}
		}
		s = array.toString();
	}

	private static boolean goodGuess() {
		for (char c : answer.toCharArray()) {
			if (c == guess) {
				return true;
			}
		}
		return false;
	}

	private static void guess() {
		System.out.println(s);
		boolean fail = true;
		do {
			guess = ConsoleIO.promptForChar("Enter your guess in lowercase:", 'a', 'z');
			for (Character character : alreadyGuessed) {
				if (guess != character) {
					fail = false;
					alreadyGuessed.add(guess);
				} else {
					System.out.println("You already guessed that.");
				}
			}
		} while (fail);

	}

	private static void printMan() {
		if (!goodGuess()) {
			switch (guessesLeft) {
			case 6:
				// addHead();
				break;
			case 5:
				// addBody();
				break;
			case 4:
				// addArmL();
				break;
			case 3:
				// addArmR();
				break;
			case 2:
				// addLegL();
				break;
			case 1:
				// addLegR();
				break;
			default:
				break;
			}
			guessesLeft--;
			for (String s : man) {
				System.out.println(s);
			}
		}
	}

	public static void run() {
		setup();
		config();
		boolean condition = false;
		do {
			guess();
			printMan();
			if (s.equals(answer)) {
				condition = true;
			}
		} while (!condition);

	}

	private static void setup() {
		System.out.println("Player 2 look away.");
		s = ConsoleIO.promptForInput("Player 1 enter your answer:", false);
	}
}
