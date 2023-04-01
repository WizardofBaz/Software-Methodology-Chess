package Pieces;

/**
 * Class with constructors for the Rook Piece and methods relating to it 
 * 
 * @author Osama Syed
 * @author Arbaz Pathan
 *
 */


public class Rook extends Piece {
	
	/**
	 * Constructor for Rook
	 *  
	 * @param color the color of the rook
	 * @param initialPosition the initial position of the rook
	 * 
	 */
	
	public Rook(String color, String initialPosition) {
		this.currentPosition = initialPosition;
		this.color = color;
	}
	
	/**
	 * Overriding toString method, so that the string shows color and type of piece 
	 *
	 *@return String
	 */

	@Override
	public String toString() {
		return this.color.substring(0, 1)+"R";

	}
	
	/**
	 * This is a boolean method and is used to make sure that all of the 
	 * moves using the rook is valid.
	 *  
	 * 
	 * @param board the playing board
	 * @param oldPos the old position of the rook
	 * @param newPos the new position of the rook
	 * @param white boolean, true if it is white's turn
	 * 
	 * @return true or false
	 */

	@Override
	public boolean moveValid(Piece[][] board,String oldPos, String newPos, Boolean white) {
		int oldR = getRow(oldPos);
		int oldC = getCol(oldPos);
		int newR = getRow(newPos);
		int newC = getCol(newPos);

		
		//Moving left right
		if (oldR == newR
			&& newC!=oldC
			&& (newC<8 && newC>=0)) {
			
			//anything in the way moving right?
			if(oldC < newC) {
				for(int i = oldC+1; i < newC; i++) {	
					if (board[oldR][i] != null)	return false;
					
				}
			}
			
			//anything in the way moving left?
			if(oldC > newC) {
				for(int i = oldC-1; i > newC; i--) {
					if(board[oldR][i] != null) return false;
				}
			}
			
			
			
			//Legal move
			return true;
		}
		
		
		//Moving up down
		if(oldC==newC
			&& newR!=oldR
			&& oldR<8 && newR>=0) {
			
			//anything in way moving up?
			if(oldR < newR) {
				for(int i = oldR+1; i < newR; i++) {	
					if (board[i][oldC] != null)	return false;
					
				}
			}
			
			//anything in the way moving down?
			if(oldR > newR) {
				for(int i = oldR-1; i > newR; i--) {
					if(board[i][oldC] != null) return false;
				}
			}
			
			//Legal Move 
			return true;
		}
		
		return false;
	}




}
