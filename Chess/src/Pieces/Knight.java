package Pieces;

	/**
	 *  Class with constructors for the Knight Piece and methods relating to it
	 *  @author Osama Syed
	 * @author Arbaz Pathan
	 */

public class Knight extends Piece{
	
	/**
	 * Constructor for Knight
	 *  
	 * @param color the color of the knight
	 * @param initialPosition the initial position of the knight
	 * 
	 */
	
	public Knight(String color, String initialPosition) {
		
		/**
		 * The position of the current king
		 *
		 */
		this.currentPosition = initialPosition;
		
		/**
		 * The color of the current king
		 *
		 */
		
		this.color = color;
	}
	
	/**
	 * Overriding toString method, so that the string shows color and type of piece 
	 *
	 *@return String
	 */

	@Override
	public String toString() {
		return this.color.substring(0, 1)+"N";
	}
	
	/**
	 * This is a boolean method and is used to make sure that all of the 
	 * moves using the knight is valid.
	 *  
	 * 
	 * @param board the playing board
	 * @param oldPos the old position of the knight
	 * @param newPos the new position of the knight
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
		
		int rowChange = newR-oldR;
		int colChange = newC-oldC;
		
		
		if(rowChange==2) {
			if(colChange==1 || colChange==-1) return true;					
		}
		
		if(rowChange==-2) {
			if(colChange==1 || colChange==-1) return true;
		}
		
		if(rowChange == 1) {
			if(colChange==2 || colChange==-2) return true;

		}
		if(rowChange ==-1) {
			if(colChange==2 || colChange==-2) return true;

		}
		
		
		return false;
	}

	
}
