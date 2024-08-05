package Players;
import java.util.ArrayList;
import Pieces.Piece;
import java.util.ArrayList;
import Game.Board;

/*
 * Represents the Alpha-Beta player
 */
public class MiniMaxPlayer extends Player {
	
	public Player otherPlayer;
	
	
	/*
	 * constructor to make Alpha-Beta Player
	 */
	public MiniMaxPlayer(ArrayList<Piece> myPieces, Board board, String name, boolean isWhite, Player otherPlayer) {
		
		super.myPieces = myPieces;
		super.board = board;
		super.name = name;
		super.king = board.getKing(isWhite);
		super.isWhite = isWhite;
		this.otherPlayer = otherPlayer;
	}
	
	/*
	 * find the best move using the Alpha-beta search algorithm and make that move
	 */
	public void turn() {
		MiniMaxReturn v = maxValue(3,-40,40);
		System.out.println("the value is "+v.value);
		makeMove(v.move[0], v.move[1]);
	}
	
	/*
	 * sorts the possible moves by the player by putting attacking moves up top.
	 * This is done because chances are attacking moves are preferable to other moves
	 * because of pruning of the search tree, should speed up runtime.
	 */
	public void sortMoves(ArrayList<int[][]> moves) {
		int swap = 0;
		for (int i = 0; i< moves.size();i++) {
			if (this.board.getPiece(moves.get(i)[1]) !=null && this.board.getPiece(moves.get(i)[1]).isWhite != this.isWhite) {
				int[][] tempMove = moves.get(swap);
				moves.set(swap, moves.get(i));
				moves.set(i, tempMove);
			}
		}
	}
	
	/*
	 * Alpha-beta search algorithm. Finds the best move based on the features selected in the tree created with 
	 * depth passed in.
	 */
	public MiniMaxReturn maxValue(int depth,double alpha,double beta) {
		MiniMaxReturn objectToReturn = new MiniMaxReturn();
		ArrayList<int[][]> moves = this.makeableMoves();
		this.sortMoves(moves);
		if (checkmate()) {
			objectToReturn.value = -40;
			return objectToReturn;
		}
		else if (otherPlayer.checkmate()) {
			objectToReturn.value = 40;
			return objectToReturn;
		}
		else if (depth == 5) {
//			System.out.println("hit depth "+depth+" on max :" +board.peiceComparisonValueFunction(isWhite));
			objectToReturn.value = board.peiceComparisonValueFunction(isWhite);
			return objectToReturn;
		}
		else {
			double v = -40;
			for (int i =0; i<moves.size();i++) {
				int[] prevloc = moves.get(i)[0];
				int[] nextloc = moves.get(i)[1];
				Piece possTakenPiece = null;
				if (board.getBoard()[nextloc[0]][nextloc[1]] != null) {
					possTakenPiece = board.getPiece(nextloc);
					this.otherPlayer.removePiece(possTakenPiece);
				}
				board.unregisteredMovePiece(prevloc, nextloc);
//				System.out.println("max made move" +board);
				MiniMaxReturn minVal = minValue(depth+1,alpha,beta);
				if (minVal.value>v) {
					v=minVal.value;
					objectToReturn.value = minVal.value;
					objectToReturn.move = moves.get(i);
					alpha= Math.max(alpha, v);
				}
				board.unregisteredMovePiece(nextloc,prevloc);
				if (possTakenPiece != null) {
					this.otherPlayer.myPieces.add(possTakenPiece);
				}
				board.insertPiece(possTakenPiece, nextloc);
				if (v>=beta) {
//					System.out.println("max prune " +beta);
					return objectToReturn;
				}
			}
			if (depth==0 && objectToReturn.move == null) {
				objectToReturn.move = moves.get(0);
			}
			return objectToReturn;
		}
	}
	
	/*
	 * Used by maxValue to try and mimic the best moves for the opposing player based on the features selected.
	 * So that the best move can be found by maxValue
	 */
	public MiniMaxReturn minValue(int depth,double alpha,double beta) {
		MiniMaxReturn objectToReturn = new MiniMaxReturn();
		if (checkmate()) {
			objectToReturn.value = 40;
			return objectToReturn;
		}
		else if (otherPlayer.checkmate()) {
			objectToReturn.value = -40;
			return objectToReturn;
		}
		else if (depth == 5) {
//			System.out.println("hit depth "+depth+" on min :" +board.peiceComparisonValueFunction(isWhite));
			objectToReturn.value = board.peiceComparisonValueFunction(isWhite);
			return objectToReturn;
		}
		else {
			double v = 40;
			ArrayList<int[][]> moves = this.otherPlayer.makeableMoves();
			this.sortMoves(moves);
			for (int i =0; i<moves.size();i++) {
				int[] prevloc = moves.get(i)[0];
				int[] nextloc = moves.get(i)[1];
				Piece possTakenPiece = null;
				if (board.getBoard()[nextloc[0]][nextloc[1]] != null) {
					possTakenPiece = board.getPiece(nextloc);
					this.removePiece(possTakenPiece);
				}
				board.unregisteredMovePiece(prevloc, nextloc);
//				System.out.println("min made move" +board);
				MiniMaxReturn maxVal = maxValue(depth+1,alpha,beta);
				if (maxVal.value<v) {
					v=maxVal.value;
					objectToReturn.value = maxVal.value;
					objectToReturn.move = moves.get(i);
					beta = Math.min(beta, v);
				}
				board.unregisteredMovePiece(nextloc,prevloc);
				if (possTakenPiece != null) {
					this.myPieces.add(possTakenPiece);
				}
				board.insertPiece(possTakenPiece, nextloc);
				if (v<=alpha) {
//					System.out.println("min prune " +alpha);
					return objectToReturn;
				}
			}
			return objectToReturn;
		}
	}
	
	

}
