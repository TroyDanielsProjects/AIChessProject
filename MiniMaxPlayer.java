

import java.util.ArrayList;
import java.util.ArrayList;

public class MiniMaxPlayer extends Player {
	
	public Player otherPlayer;
	
	
	
	public MiniMaxPlayer(ArrayList<Piece> myPieces, Board board, String name, boolean isWhite, Player otherPlayer) {
		
		super.myPieces = myPieces;
		super.board = board;
		super.name = name;
		super.king = board.getKing(isWhite);
		super.isWhite = isWhite;
		this.otherPlayer = otherPlayer;
	}
	
	public void turn() {
		MiniMaxReturn v = maxValue(0,-40,40);
		System.out.println("the value is "+v.value);
		boolean moveMade =makeMove(v.move[0], v.move[1]);
//		if (!moveMade) {
//			System.out.println("Error, no move made");
//			System.out.println("the saved move is "+this.nextMove[0][0]+this.nextMove[0][1]+this.nextMove[1][0]+this.nextMove[1][1]);
//		}
	}
	
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
