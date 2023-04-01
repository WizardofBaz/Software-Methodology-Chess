package Pieces;

/**
 * Class with constructors for the Bishop Piece and methods relating to it
 * @author Osama Syed
 * @author Arbaz Pathan
 */

public class Bishop extends Piece {
	
	/**
	 * Constructor for Bishop
	 * 
	 * 
	 * @param color color of the bishop
	 * @param initialPosition the current position of the bishop
	 */

	public Bishop(String color, String initialPosition) {
		/**
		 * The position of the current bishop
		 *
		 */
		this.currentPosition = initialPosition;
		
		/**
		 * The color of the current bishop
		 *
		 */
		
		this.color = color;
	}
	

	@Override
	/**
	 * Overriding toString method, so that the string shows color and type of piece 
	 * 
	 *@return String
	 *
	 */
	public String toString() {
		return this.color.substring(0, 1) + "B";

	}
	
	/**
	 * This method checks to make sure that the move 
	 * that is being attempted by the user is correct
	 * for the current piece. 
	 * 
	 * @param board the current board
	 * @param oldPos the old position of the bishop
	 * @param newPos the new position of the bishop
	 * @param white boolean, true if it is white's turn
	 * 
	 * @return true if move is valid, false otherwise.
	 */

	@Override
	public boolean moveValid(Piece[][] board, String oldPos, String newPos, Boolean white) {		
		int oldR = getRow(oldPos);
		int oldC = getCol(oldPos);
		int newR = getRow(newPos);
		int newC = getCol(newPos);

		int rowChange = newR - oldR;
		int colChange = newC - oldC;

		// moving top right

		if (Math.abs(rowChange) == Math.abs(colChange)) {
			if (rowChange > 0 && colChange > 0) {
				for (int i = 1; i < Math.abs(rowChange); i++) {
					if (board[oldR + i][oldC + i] != null)
						return false;
				}
			}

			// moving top left
			if (rowChange > 0 && colChange < 0) {
				for (int i = 1; i < Math.abs(rowChange); i++) {

					if (board[oldR + i][oldC - i] != null)
						return false;
				}
			}

			// moving bottom right
			if (rowChange < 0 && colChange > 0) {
				for (int i = 1; i < Math.abs(rowChange); i++) {

					if (board[oldR - i][oldC + i] != null)
						return false;
				}
			}

			// moving bottom left
			if (rowChange < 0 && colChange < 0) {
				for (int i = 1; i < Math.abs(rowChange); i++) {

					if (board[oldR - i][oldC - i] != null)
						return false;
				}
			}

			return true;
		}

		return false;
	}

}
