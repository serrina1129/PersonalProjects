package Ghost2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GamePlay {
	public static void main(String[] args) throws IOException {
		//read file words into array called dict
		String[] dict = new String [279496];	
		File words = new File("/Users/serrinabrown/Desktop/words.txt");
		Scanner in = new Scanner(words);
		for(int i = 0; i < dict.length; i++)
			dict[i] = in.nextLine();
		//All user input will be held in variable answer
 		Scanner answer = new Scanner(System.in);
 		//Gets player count
 		System.out.println("Enter the number of players: ");
		int players = answer.nextInt();
		//gets the names of the players, creates the player using the player constructor, and stores them in a list
		System.out.println("Enter the names of the players: ");
		ArrayList<Player> Players = new ArrayList<Player> ();
		for(int i = 0; i < players; i++) {
			String name = answer.next();
			Players.add(new Player(name));
		}
		//the total string of letters 
		String phrase = "";
		// the letter input by a player during their turn
		String letter = "";
		boolean gameOver = false;
		//while loop allows players to keep playing rounds till the game is over
		while(!gameOver) {
			boolean roundOver = false;
			//while loop allows players to keep taking turns till the round is over
			while(!roundOver) {
				//allows players to take turns until someone loses
				for(int i = 0; i < Players.size(); i++) {
					System.out.println(Players.get(i) + ", it's your turn. The letters are " + phrase + ". Enter a letter or enter * to challenge.");
					letter = answer.next();
					//challenges
					if(letter.equals("*")) {
						roundOver = true;
						boolean valid = false;
						for(int s = 0; s < dict.length; s++) {
							int wordLen = dict[s].length();
							// checks the dictionary for words 4 or more letters that are 1 letter longer than the phrase and starts with the same letters as the phrase
							if((wordLen > 3 && wordLen == phrase.length()+1) && (dict[s].substring(0, wordLen-1).equals(phrase))) {
								Players.get(i).loseRound();
								System.out.println( dict[s] + " begins with those letters. " + Players.get(i) + " loses!");
								valid = true;
								//resets the phrase for the next round
								phrase = "";
								//if the losing player gets GHOST they are removed from the game
								if(Players.get(i).isEliminated()) {
									System.out.println(Players.get(i) + " is eliminated!");
									Players.remove(i);
								}
							}	
						}
						if(!valid) {
							//makes it so if the first player wins the challenge it would print that the last player loses.
							if(i==0) i = Players.size();
							Players.get(i-1).loseRound();
							System.out.println("No word begins with those letters. " + Players.get(i-1) + " loses!");
							//resets the phrase for the next round
							phrase = "";
							//if the losing player gets GHOST they are removed from the game
							if(Players.get(i-1).isEliminated()) {
								System.out.println(Players.get(i-1) + " is eliminated!");
								Players.remove(i-1);
							}
						}
					}else{
						phrase += letter;
						//if the current player creates a word they lose
						for(int s = 0; s < dict.length; s++) {
							//checks words 4 letters or longer
							if(dict[s].length() > 3 && (phrase).equals(dict[s])) {
								Players.get(i).loseRound();
								System.out.println(phrase + " is a word. " + Players.get(i) + " loses!");
								//resets the phrase for the next round
								phrase = "";
								//if the losing player gets GHOST they are removed from the game
								if(Players.get(i).isEliminated()) {
									System.out.println(Players.get(i) + " is eliminated!");
									Players.remove(i);
								}
								roundOver = true;
							}
						}
					}
				}
				//if there is only one player left, they are the winner and the game is over
				if(Players.size()==1) {
					System.out.println(Players.get(0) + " is Victorious!");
					System.exit(0);
				}
			}
		}
	}
}
