

import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {
	
	public HumanPlayer(ArrayList<Piece> myPieces, Board board, String name, boolean isWhite) {
		super.myPieces = myPieces;
		super.board = board;
		super.name = name;
		super.king = board.getKing(isWhite);
		super.isWhite = isWhite;
	}
	
	
	public void turn() {
		System.out.println(board);
		Scanner scanner = new Scanner(System.in);
		int[] prevLoc = new int[] {0,0};
		int[] nextLoc = new int[] {7,6};
		boolean isIncorrect = false;
		do {
			if (isIncorrect) {
				System.out.println("Incorrect move/input");
			}
			System.out.println("Enter a new move ("+this.name+"): ");
			String input = scanner.nextLine();
			if (input.equalsIgnoreCase("moves")) {
				System.out.println(toString());
			}
			else {
				try {
					int prev1 = 7-(input.substring(0, 1).getBytes()[0]-97);
					int prev0 = Integer.parseInt(input.substring(1, 2))-1;
					int next1 = 7-(input.substring(2, 3).getBytes()[0]-97);
					int next0 = Integer.parseInt(input.substring(3, 4))-1;
					prevLoc[0] = prev0;
					prevLoc[1] = prev1;
					nextLoc[0] = next0;
					nextLoc[1] = next1;
					if (prev0<0 || prev0>7 || prev1<0 || prev1>7 || next0<0 || next0>7 || next1<0 || next1>7) {
						prevLoc = new int[] {0,0};
						nextLoc = new int[] {7,6};
					}
				}
				catch(Exception e) {
					
				}
			}
			
			isIncorrect = true;
		}
		while (!playerMove(prevLoc,nextLoc));
	}
	
}
