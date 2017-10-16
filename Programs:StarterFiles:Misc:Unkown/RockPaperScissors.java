import java.util.Scanner;
import java.util.Random;

/**
 * PP 4.13: Rock, Paper, Scissors Game.
 *
 * @author Marshall Eddy
 */
public class RockPaperScissors
{
	public enum Player {COMPUTER, YOU, TIE};

	public static void main(String[]args)
	{
		Scanner scan = new Scanner(System.in);
		int wins = 0, losses = 0, ties = 0;
		String yesNo = "y";
		while (yesNo.charAt(0) == 'y'){

			Gesture computerGesture = null, yourGesture = null;
			Player winner = null;



			// computer chooses
			Random rand = new Random();
			int choice = rand.nextInt(Gesture.values().length);


			switch (choice)
			{
			case 0: 
				computerGesture = Gesture.ROCK;
				break;
			case 1:
				computerGesture = Gesture.PAPER;
				break;
			case 2:
				computerGesture = Gesture.SCISSORS;
				break;
			}

			System.out.println("Select your gesture: ");

			for(Gesture currentGesture : Gesture.values())
			{
				System.out.println(currentGesture.ordinal() + " for " + currentGesture.name());
			}
			// player chooses
			choice = scan.nextInt();
			boolean valid = true;
			switch (choice)
			{
			case 0:
				yourGesture = Gesture.ROCK;
				break;
			case 1:
				yourGesture = Gesture.PAPER;
				break;
			case 2:
				yourGesture = Gesture.SCISSORS;
				break;
			default:
				System.out.println("Invalid Gesture!");
				valid = false;
			}

			// clear the scan buffer
			scan.nextLine();

			if(valid){


				System.out.println("I chose " + computerGesture + ".");
				System.out.println("You chose " + yourGesture + ".");

				// Logic of the game (who wins/loses)
				if (computerGesture == Gesture.ROCK)// Computer chose Rock
				{
					if (yourGesture == Gesture.ROCK)
					{
						winner = Player.TIE;
					}
					else if (yourGesture == Gesture.PAPER)
					{
						winner = Player.YOU;
					}	
					else if (yourGesture == Gesture.SCISSORS)
					{
						winner = Player.COMPUTER;
					}
				}
				if (computerGesture == Gesture.PAPER)// Computer chose Paper
				{
					if (yourGesture == Gesture.ROCK)
					{
						winner = Player.COMPUTER;
					}
					else if (yourGesture == Gesture.PAPER)
					{	
						winner = Player.TIE;
					}
					else if (yourGesture == Gesture.SCISSORS)
					{
						winner = Player.YOU;
					}
				}
				if (computerGesture == Gesture.SCISSORS)// Computer chose Scissors
				{
					if (yourGesture == Gesture.ROCK)
					{
						winner = Player.YOU;
					}
					else if (yourGesture == Gesture.PAPER)
					{
						winner = Player.COMPUTER;
					}
					else if (yourGesture == Gesture.SCISSORS)
					{
						winner = Player.TIE;
					}
				}	



				switch(winner)	// Who wins/tied
				{
				case TIE:
					ties++;
					System.out.println("We tied!");
					break;
				case YOU: 
					wins++;
					System.out.println("You win!");
					break;
				case COMPUTER:
					losses++;
					System.out.println("I win!");
					break;
				}

				System.out.println(" ");

			}
			System.out.println("Play again (y/n)? ");
			yesNo = scan.nextLine();



			System.out.println(" ");

			// Table of how many times the user wins/loses/tied
			System.out.println("You won: " + wins + " times.");
			System.out.println("You lost: " + losses + " times.");
			System.out.println("We tied: " + ties + " times.");
			// TODO: Ask if they want to play again and loop back to the
			// top if they do. Print a table of final results before
			// exiting if they decide they are done playing.

		}
	}
}
