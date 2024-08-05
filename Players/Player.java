package Players;

import java.util.ArrayList;
import java.util.Scanner;
import Game.Board;
import Pieces.Piece;
import Pieces.Queen;

/*
 * Abstract class for every player to inherit. Gives basic functionality for turns and moves
 */
public abstract class Player {

	protected ArrayList<Piece> myPieces;
	protected int runningScore = 39;
	protected Board board;
	protected String name;
	protected Piece king;
	protected boolean isWhite;
	
	
	/*
	 * provides most of the functionality for a move. takes the location of the piece to move and where to move it and 
	 * returns if the piece has actually moved in the game. The game will not move the piece if move is illegal
	 * Do not use in actual game - helper function for playerMove
	 */
	public boolean makeMove(int[] prevLoc, int[] nextLoc) {
		boolean hasMoved = false;
		Piece possTakenPiece = board.getPiece(nextLoc);
		if (board.getPiece(prevLoc)== null) {
			hasMoved = false;
		}
		else if (board.getPiece(prevLoc).legalMove(prevLoc,nextLoc) && this.isWhite == board.getPiece(prevLoc).isWhite) {
			if (board.getPiece(prevLoc).value==0 && prevLoc[1]==3 && (prevLoc[0] == 0 || prevLoc[0] ==7) && (nextLoc[1]==1 || nextLoc[1]==5)) {
				board.castle(prevLoc, nextLoc);
				hasMoved = true;
			}
			else {
				board.movePiece(prevLoc,nextLoc);
				hasMoved = true;
				if (isInCheck()) {
					board.movePiece(nextLoc,prevLoc);
					board.insertPiece(possTakenPiece, nextLoc);
					hasMoved = false;
				}
			}
		}
		return hasMoved;
	}
	
	/*
	 * Make a move for a player. Returns if the move was legal and registered by the game.
	 */
	public boolean playerMove(Piece piece, int[] nextLoc) {
		int[] prevLoc = piece.location;
		return playerMove(prevLoc,nextLoc);
	}
	
	/*
	 * Make a move for a player with the intention of undoing the move later on. (move that doesn't actually change the state of the game)
	 * used for Alpha-beta player
	 */
	public boolean makeUnregisteredMove(int[] prevLoc, int[] nextLoc) {
		boolean hasMoved = false;
		if (board.getPiece(prevLoc)== null) {
			hasMoved = false;
		}
		else if (board.getPiece(prevLoc).legalMove(prevLoc,nextLoc) && this.isWhite == board.getPiece(prevLoc).isWhite) {
			Piece possTakenPiece = board.getPiece(nextLoc);
			board.unregisteredMovePiece(prevLoc,nextLoc);
			hasMoved = true;
			if (isInCheck()) {
				board.unregisteredMovePiece(nextLoc,prevLoc);
				board.insertPiece(possTakenPiece, nextLoc);
				hasMoved = false;
			}
		}
		return hasMoved;
	}
	
	/*
	 * Make a move for a player. Returns if the move was legal and registered by the game.
	 * also will replace a pawn with a queen if it reaches the end.
	 */
	public boolean playerMove(int[] prevLoc, int[] nextLoc) {
		boolean hasMoved = makeMove(prevLoc,nextLoc);
		
		if (hasMoved && board.getPiece(nextLoc).value==1 && board.getPiece(nextLoc).isWhite && nextLoc[0]==7) {
			replaceEndPawn(board.getPiece(nextLoc));
		}
		else if (hasMoved && board.getPiece(nextLoc).value==1 && !board.getPiece(nextLoc).isWhite && nextLoc[0]==0) {
			replaceEndPawn(board.getPiece(nextLoc));
		}
		return hasMoved;
	}
	
	/*
	 * Replaces a end pawn to a queen
	 */
	public void replaceEndPawn(Piece piece) {
		int locationToReplace=0;
		for (int i =0; i< this.myPieces.size();i++) {
			if (this.myPieces.get(i).location.equals(piece.location)) {
				locationToReplace = i;
			}
		}
		Piece newQueen = new Queen(isWhite,piece.location,board.getBoard());
		this.myPieces.set(locationToReplace, newQueen);
		this.board.insertPiece(newQueen, newQueen.location);
	}
	
	public boolean makeMove(Piece piece, int[] nextLoc) {
		int[] prevLoc = piece.location;
		return makeMove(prevLoc,nextLoc);
	}
	
	/*
	 * determines if the player is in check
	 */
	public boolean isInCheck() {
		return board.isSquareBeingAttacked(this.isWhite,this.king.location);
	}
	
	/*
	 * implements all the functionality of the players turn
	 */
	public abstract void turn();
	
	/*
	 * determines if the player is in checkmate
	 */
	public boolean checkmate() {
		ArrayList<int[][]> makeableMoves = this.makeableMoves();
		if (makeableMoves.size()==0) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/*
	 * finds all of the makable moves for the player
	 */
	public ArrayList<int[][]> makeableMoves(){
		ArrayList<int[][]> possibleMoves;
		ArrayList<int[][]> makeableMoves = new ArrayList<int[][]>();
		for (int i = 0; i< this.myPieces.size() ;i++) {
			Piece currPiece = this.myPieces.get(i);
			int[] prevLoc =  currPiece.location;
			possibleMoves = currPiece.possibleMoveLocations();
			for (int j = 0; j< possibleMoves.size() ;j++) {
				Piece possTakenPiece = board.getPiece(possibleMoves.get(j)[1]);
				if (makeUnregisteredMove(prevLoc,possibleMoves.get(j)[1])) {
					if (!isInCheck()) {
						makeableMoves.add(possibleMoves.get(j));
					}
					board.unregisteredMovePiece(possibleMoves.get(j)[1], prevLoc);
					board.insertPiece(possTakenPiece, possibleMoves.get(j)[1]);
				}
			}
		}
		return makeableMoves;
	}
	
	
	public void removePiece(Piece piece) {
		for (int i =0; i<this.myPieces.size();i++) {
			if (this.myPieces.get(i).location[0]==piece.location[0] && this.myPieces.get(i).location[1]==piece.location[1]) {
				this.myPieces.remove(i);
			}
		}
	}
	
	
	public String toString() {
		String stringToReturn = "";
		ArrayList<int[][]> moves = makeableMoves();
		for (int i = 0; i<moves.size();i++) {
			char prev1 = (char)((moves.get(i)[0][1]*-1)+104);
			int prev0 = moves.get(i)[0][0]+1;
			char next1 = (char)((moves.get(i)[1][1]*-1)+104);
			int next0 = moves.get(i)[1][0]+1;
			stringToReturn += "move "+prev1+","+prev0+" to "+next1+","+next0+"\n";
		}
		return stringToReturn;
	}
	
}
