package logic;

import lib.ConsoleIO;

public class Game {
	private static char[] guesses;
	private static int guessNum = 0;
	private static String s;
	private static String answer;
	private static char guess;
	private static int guessesLeft = 5;
	private static String[] man;
	private static boolean quit = false, answered = false;

	private static void config() {
		answer = s;
		char[] array = s.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if ((array[i] != ' ') || (!(array[i] <= 57) && !(array[i] >= 48))) {
				array[i] = '_';
			}
		}
		s = new String(array);
	}

	private static boolean goodGuess() {
		char[] a = answer.toCharArray();
		for (char guesse : guesses) {
			for (char c : a) {
				if (c != guesse) {
					if (((c == guess) || (Character.toLowerCase(c) == guess))) {
						// guesses[guessNum + 1] = c;
						return true;
					}
				}
			}
		}
		return false;
	}

	private static void printMan(boolean tried) {
		boolean loop = true;
		boolean done = false;
		do {
			if (!goodGuess() || done) {
				char[] c = null;
				switch (guessesLeft) {
				case 5:

					c = man[2].toCharArray();
					c[3] = 'o';
					man[2] = c.toString();

					break;
				case 4:
					c = man[3].toCharArray();
					c[3] = '|';
					man[3] = c.toString();
					break;
				case 3:
					c = man[3].toCharArray();
					c[2] = '/';
					man[3] = c.toString();
					break;
				case 2:
					c = man[3].toCharArray();
					c[4] = '\\';
					man[3] = c.toString();
					break;
				case 1:
					c = man[4].toCharArray();
					c[2] = '/';
					man[4] = c.toString();
					break;
				case 0:
					c = man[4].toCharArray();
					c[4] = '\\';
					man[4] = c.toString();
					break;
				default:
					break;
				}

				switch (guessesLeft) {
				case 5:
					man[2] = "";
					for (char ch : c) {
						man[2] += ch;
					}
					break;

				case 4:
				case 3:
				case 2:
					man[3] = "";
					for (char ch : c) {
						man[3] += ch;
					}
					break;
				case 1:
				case 0:
					man[4] = "";
					for (char ch : c) {
						man[4] += ch;
					}
					break;
				default:
					break;
				}

				guessesLeft--;

			} else if (tried) {
				loop = false;
				done = true;
			}
		} while (!loop);
	}

	private static void replaceChars() {
		char[] anser = answer.toCharArray();
		char[] ss = s.toCharArray();
		for (int i = 0; i < anser.length; i++) {
			if ((anser[i] == guess) || (Character.toLowerCase(anser[i]) == guess)) {
				ss[i] = guess;
			}
		}
		s = new String(ss);
	}

	public static void run() {
		boolean play = true;
		do {
			setup();
			config();
			boolean condition = false;
			do {

				turn();

				if (!quit) {
					// printMan(false);
					if ((guessesLeft < 0)) {
						System.out.println("You lose! the answer was:\n" + answer);
						condition = true;
					} else if (s.equals(answer)) {
						System.out.println("You win!");
						condition = true;
					}
				} else if (answered) {
					System.out.println("You win!");
					condition = true;
					printMan(true);
				}
				for (String s : man) {
					System.out.println(s);
				}
			} while (!condition);
			if (!ConsoleIO.promptForBool("Would you like to play again? y/n", "y", "n")) {
				System.out.println("Thanks for playing.");
				play = false;
			}

		} while (play);

	}

	private static void setup() {
		boolean f = true;
		answered = false;
		quit = false;
		guessesLeft = 5;
		guessNum = 0;
		guesses = new char[27];

		for (int i = 0; i < guesses.length; i++) {
			guesses[i] = '@';
		}
		man = new String[] { "   _____", "   |   |", "       |", "       |", "       |", "     __|_ " };
		System.out.println("Player 2 look away.");
		do {
			s = ConsoleIO.promptForInput("Player 1 enter your answer:", false);
			f = s == null;
			if (f) {
				System.out.println("It has to be a string. Not null.");
			}
		} while (f);
	}

	private static void turn() {

		// TODO not let muslitple guess of same char
		System.out.println(s);
		System.out.println(guessesLeft + " guesses left");
		boolean fail = true;

		switch (ConsoleIO.promptForMenuSelection(new String[] { "Solve", "Guess", "Surrender" }, false)) {

		case 1:
			String ges = ConsoleIO.promptForInput("What is the answer?", false);
			if (ges.equals(answer)) {
				s = ges;
				answered = true;
				quit = true;
			} else {
				System.out.println("Thats not it!");
				guessesLeft = 0;
				fail = true;
			}

			break;
		case 2:
			do {
				guess = ConsoleIO.promptForChar("Enter your guess in lowercase:", 'a', 'z');

				if (goodGuess()) {

					replaceChars();

					fail = false;
					guessNum++;
					guesses[guessNum] = guess;

				} else {
					boolean f = true;
					for (char guesse : guesses) {

						if (guess == guesse) {
							break;
						} else {
							f = false;
						}
					}
					if (f) {
						System.out.println("You already guessed that.");

					} else {
						System.out.println("Wrong.");
						fail = false;
						guessNum++;
						guesses[guessNum] = guess;

					}
				}
			} while (fail);
			break;
		default:
			quit = true;
			break;
		}
		printMan(false);
	}
}
