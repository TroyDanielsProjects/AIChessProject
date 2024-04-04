

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Player {

	protected ArrayList<Piece> myPieces;
	protected int runningScore = 39;
	protected Board board;
	protected String name;
	protected Piece king;
	protected boolean isWhite;
	
	
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
	
	public boolean playerMove(Piece piece, int[] nextLoc) {
		int[] prevLoc = piece.location;
		return playerMove(prevLoc,nextLoc);
	}
	
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
	
	public boolean isInCheck() {
		return board.isSquareBeingAttacked(this.isWhite,this.king.location);
	}
	
	public abstract void turn();
	
	public boolean checkmate() {
		ArrayList<int[][]> makeableMoves = this.makeableMoves();
		if (makeableMoves.size()==0) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
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
