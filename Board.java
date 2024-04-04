

import java.util.ArrayList;

public class Board {
	
	private Piece[][] board = new Piece[8][8];
	private King whiteKing =  new King(true, new int[] {0,3},this.board,this);
	private King blackKing =  new King(false, new int[] {7,3},this.board,this);
	private ArrayList<Piece> whitePieces = new ArrayList<Piece>();
	private ArrayList<Piece> blackPieces = new ArrayList<Piece>();
	
	public Board() {
		King whiteKing = this.whiteKing;
		King blackKing = this.blackKing;
		board[0][0] = new Rook(true, new int[] {0,0},this.whiteKing, this.board);
		board[0][1] = new Knight(true, new int[] {0,1},this.board);
		board[0][2] = new Bishop(true, new int[] {0,2},this.board);
		board[0][3] = whiteKing;
		board[0][5] = new Bishop(true, new int[] {0,5},this.board);
		board[0][6] = new Knight(true, new int[] {0,6},this.board);
		board[0][4] = new Queen(true, new int[] {0,4},this.board);
		board[0][7] = new Rook(true, new int[] {0,7},this.whiteKing,this.board);
		for (int i = 0; i<8;i++) {
			board[1][i]= new Pawn(true, new int[] {1,i},this.board);
		}
		board[7][0] = new Rook(false, new int[] {7,0},this.blackKing,this.board);
		board[7][1] = new Knight(false, new int[] {7,1},this.board);
		board[7][2] = new Bishop(false, new int[] {7,2},this.board);
		board[7][3] = blackKing;
		board[7][5] = new Bishop(false, new int[] {7,5},this.board);
		board[7][6] = new Knight(false, new int[] {7,6},this.board);
		board[7][4] = new Queen(false, new int[] {7,4},this.board);
		board[7][7] = new Rook(false, new int[] {7,7},this.blackKing,this.board);
		for (int i = 0; i<8;i++) {
			board[6][i]= new Pawn(false, new int[] {6,i},this.board);
		}
		
		for (int i = 0; i<2; i++) {
			for (int j = 0; j<8; j++) {
				this.whitePieces.add(board[i][j]);
			}
		}
		
		for (int i = 6; i<8; i++) {
			for (int j = 0; j<8; j++) {
				this.blackPieces.add(board[i][j]);
			}
		}
	}
	
	public King getKing(boolean isWhite) {
		if (isWhite) {
			return this.whiteKing;
		}
		else {
			return this.blackKing;
		}
	}
	
	public ArrayList<Piece> getBlackPieces(){
		return this.blackPieces;
	}
	
	public ArrayList<Piece> getWhitePieces(){
		return this.whitePieces;
	}
	
	public Piece getPiece(int[] location) {
		return board[location[0]][location[1]];
	}
	
	public void castle(int[] prevLoc, int[] nextLoc) {
		if (prevLoc[1]==3 && (prevLoc[0] == 0 || prevLoc[0] ==7) && nextLoc[1]==1) {
			board[prevLoc[0]][prevLoc[1]].move(nextLoc);
			board[prevLoc[0]][0].move(new int[] {prevLoc[0],2});
			board[nextLoc[0]][nextLoc[1]] = board[prevLoc[0]][prevLoc[1]];
			board[prevLoc[0]][prevLoc[1]] = null;
			board[nextLoc[0]][2] = board[prevLoc[0]][0];
			board[prevLoc[0]][0] = null;
		}
		else {
			board[prevLoc[0]][prevLoc[1]].move(nextLoc);
			board[prevLoc[0]][7].move(new int[] {prevLoc[0],4});
			board[nextLoc[0]][nextLoc[1]] = board[prevLoc[0]][prevLoc[1]];
			board[prevLoc[0]][prevLoc[1]] = null;
			board[nextLoc[0]][4] = board[prevLoc[0]][7];
			board[prevLoc[0]][7] = null;
		}
	}
	
	public void movePiece(int[] prevLoc, int[] nextLoc) {
		if (board[nextLoc[0]][nextLoc[1]]!= null && board[nextLoc[0]][nextLoc[1]].isWhite) {
			for (int i = 0; i<this.whitePieces.size();i++) {
				if (this.whitePieces.get(i).location[0]==nextLoc[0] && this.whitePieces.get(i).location[1]==nextLoc[1]) {
					this.whitePieces.remove(i);
				}
			}
		}
		else if (board[nextLoc[0]][nextLoc[1]]!= null && !board[nextLoc[0]][nextLoc[1]].isWhite) {
			for (int i = 0; i<this.blackPieces.size();i++) {
				if (this.blackPieces.get(i).location[0]==nextLoc[0] && this.blackPieces.get(i).location[1]==nextLoc[1]) {
					this.blackPieces.remove(i);
				}
			}
		}
		board[prevLoc[0]][prevLoc[1]].move(nextLoc);
		board[nextLoc[0]][nextLoc[1]] = board[prevLoc[0]][prevLoc[1]];
		board[prevLoc[0]][prevLoc[1]] = null;
	}
	// only for program use. Do Not use for player input
	public void unregisteredMovePiece(int[] prevLoc, int[] nextLoc) {
		board[prevLoc[0]][prevLoc[1]].unregisteredMove(nextLoc);
		board[nextLoc[0]][nextLoc[1]] = board[prevLoc[0]][prevLoc[1]];
		board[prevLoc[0]][prevLoc[1]] = null;
	}
	
	public Piece[][] getBoard(){
		return this.board;
	}
	
	
	public void insertPiece(Piece piece, int[] loc) {
		this.board[loc[0]][loc[1]] = piece;
	}
	
	
	public boolean isSquareBeingAttacked(boolean isWhite, int[] location) {
		if (isWhite) {
			for (int i =0; i<blackPieces.size();i++) {
				if (blackPieces.get(i).legalAttack(location)) {
					// debugging
					//System.out.println(blackPieces.get(i) + ": "+blackPieces.get(i).location[0]+blackPieces.get(i).location[1]+","+location[0]+location[0]);
					return true;
				}
			}
			return false;
		}
		else {
			for (int i =0; i<whitePieces.size();i++) {
				if (whitePieces.get(i).legalAttack(location)) {
//					System.out.println(whitePieces.get(i) + ": "+whitePieces.get(i).location[0]+whitePieces.get(i).location[1]);
					return true;
				}
			}
			return false;
		}
	}
	
	
	public double peiceComparisonValueFunction(boolean isWhite) {
		double value = 0;
		
		if (isWhite) {
			for (int i = 0; i < this.whitePieces.size(); i++) {
				value+= this.whitePieces.get(i).value;
				if (this.whitePieces.get(i).location[0] == 0 || this.whitePieces.get(i).location[0] == 7 || this.whitePieces.get(i).location[1] == 0 || this.whitePieces.get(i).location[1] == 7) {
					value+= 0.01;
				}
				else if (this.whitePieces.get(i).location[0] == 1 || this.whitePieces.get(i).location[0] == 6 || this.whitePieces.get(i).location[1] == 1 || this.whitePieces.get(i).location[1] == 6) {
					value+= 0.02;
				}
				else if (this.whitePieces.get(i).location[0] == 2 || this.whitePieces.get(i).location[0] == 5 || this.whitePieces.get(i).location[1] == 2 || this.whitePieces.get(i).location[1] == 5) {
					value+= 0.03;
				}
				else {
					value+= 0.05;
				}
				if (this.whitePieces.get(i).value == 1 && this.whitePieces.get(i).location[0] == 7) {
					value+=8;
				}
			}
			for (int i = 0; i < this.blackPieces.size(); i++) {
				value-= this.blackPieces.get(i).value;
				if (this.blackPieces.get(i).location[0] == 0 || this.blackPieces.get(i).location[0] == 7 || this.blackPieces.get(i).location[1] == 0 || this.blackPieces.get(i).location[1] == 7) {
					value-= 0.01;
				}
				else if (this.blackPieces.get(i).location[0] == 1 || this.blackPieces.get(i).location[0] == 6 || this.blackPieces.get(i).location[1] == 1 || this.blackPieces.get(i).location[1] == 6) {
					value-= 0.02;
				}
				else if (this.blackPieces.get(i).location[0] == 2 || this.blackPieces.get(i).location[0] == 5 || this.blackPieces.get(i).location[1] == 2 || this.blackPieces.get(i).location[1] == 5) {
					value-= 0.03;
				}
				else {
					value-= 0.05;
				}
				if (this.blackPieces.get(i).value == 1 && this.blackPieces.get(i).location[0] == 0) {
					value-=8;
				}
			}
		}
		else {
			for (int i = 0; i < this.whitePieces.size(); i++) {
				value-= this.whitePieces.get(i).value;
				if (this.whitePieces.get(i).location[0] == 0 || this.whitePieces.get(i).location[0] == 7 || this.whitePieces.get(i).location[1] == 0 || this.whitePieces.get(i).location[1] == 7) {
					value-= 0.01;
				}
				else if (this.whitePieces.get(i).location[0] == 1 || this.whitePieces.get(i).location[0] == 6 || this.whitePieces.get(i).location[1] == 1 || this.whitePieces.get(i).location[1] == 6) {
					value-= 0.02;
				}
				else if (this.whitePieces.get(i).location[0] == 2 || this.whitePieces.get(i).location[0] == 5 || this.whitePieces.get(i).location[1] == 2 || this.whitePieces.get(i).location[1] == 5) {
					value-= 0.03;
				}
				else {
					value-= 0.05;
				}
				if (this.whitePieces.get(i).value == 1 && this.whitePieces.get(i).location[0] == 7) {
					value-=8;
				}
			}
			for (int i = 0; i < this.blackPieces.size(); i++) {
				value+= this.blackPieces.get(i).value;
				if (this.blackPieces.get(i).location[0] == 0 || this.blackPieces.get(i).location[0] == 7 || this.blackPieces.get(i).location[1] == 0 || this.blackPieces.get(i).location[1] == 7) {
					value+= 0.01;
				}
				else if (this.blackPieces.get(i).location[0] == 1 || this.blackPieces.get(i).location[0] == 6 || this.blackPieces.get(i).location[1] == 1 || this.blackPieces.get(i).location[1] == 6) {
					value+= 0.02;
				}
				else if (this.blackPieces.get(i).location[0] == 2 || this.blackPieces.get(i).location[0] == 5 || this.blackPieces.get(i).location[1] == 2 || this.blackPieces.get(i).location[1] == 5) {
					value+= 0.03;
				}
				else {
					value+= 0.05;
				}
				if (this.blackPieces.get(i).value == 1 && this.blackPieces.get(i).location[0] == 0) {
					value+=8;
				}
			}
		}
		
		return value;
	}
	
	public String toString() {
		String stringToReturn= "";
		stringToReturn+="\n\n";
		for (int i = 7; i>=0; i--) {
			stringToReturn+=i+1+"   | ";
			for (int j = 7; j>=0; j--) {
				if (board[i][j]!=null) {
					stringToReturn+= board[i][j]+ " | ";
				}
				else {
					stringToReturn+="     | ";
				}
			}
			stringToReturn+="\n\n";
		}
		stringToReturn+="     ";
		for (int i = 0; i<8; i++) {
			stringToReturn+="  "+(char)(i+97)+"    ";
		}
		return stringToReturn;
	}

}
