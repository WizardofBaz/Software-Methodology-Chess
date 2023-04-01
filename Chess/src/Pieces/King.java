package Pieces;

import Chess.*;


/**
 * Class with constructors for the Rook Piece and methods relating to it
 * @author Osama Syed
 * @author Arbaz Pathan
 */

public class King extends Piece {
	
	/**
	 * Constructor for King
	 *  
	 * @param color the color of the king
	 * @param initialPosition the initial position of the king
	 * 
	 */

	public King(String color, String initialPosition) {
		
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
	 * A method that converts the first letter
	 * of the color of the piece to a string.
	 *
	 *@return String
	 */
	@Override
	public String toString() {
		return this.color.substring(0, 1) + "K";

	}
	
	/**
	 * This is a boolean method and is used to make sure that all of the 
	 * moves using the king is valid.
	 *  
	 * 
	 * @param board the playing board
	 * @param oldPos the old position of the king
	 * @param newPos the new position of the king
	 * @param white boolean, true if it is white's turn
	 * 
	 * @return true or false
	 */

	@Override
	public boolean moveValid(Piece[][] board, String oldPos, String newPos, Boolean white) {

		int oldR = getRow(oldPos);
		int oldC = getCol(oldPos);
		int newR = getRow(newPos);
		int newC = getCol(newPos);

		if ((Math.abs(oldR - newR) == 1 && (Math.abs(oldC - newC) == 0))
				|| (Math.abs(oldR - newR) == 0 && (Math.abs(oldC - newC) == 1))
				|| (Math.abs(oldR - newR) == 1 && (Math.abs(oldC - newC) == 1))

		) {
			return true;
		}

		
		
		
		/**
		 * This section of the king class handles castling.
		 *   
		 */		
		
		
		// Moving White King Right 2 spaces
		if (white && (newC - oldC) == 2 && (Math.abs(oldR - newR) == 0)) {

			Piece king = this;
			Piece rook = board[oldR][oldC + 3];

			// Making sure that there are no pieces in between king and rook
			if (board[oldR][oldC + 1] == null && board[oldR][oldC + 2] == null) {
				// Making sure that king nor the spaces it will pass over and land onto can be
				// attack
				if (!Piece.kingChecked(board, "e1", Chess.blackPieces, white)
						&& !Piece.kingChecked(board, "f1", Chess.blackPieces, white)
						&& !Piece.kingChecked(board, "g1", Chess.blackPieces, white)) {

					// Making sure that king and rook both have not moved
					if (king.count == 0 && rook.count == 0) {

						Piece.move(board, "h1", "f1");
						Piece.updateHashmaps(board, Chess.whitePieces, Chess.blackPieces);

						return true;

					}
				}
			}
			// Moving White King Left 2 spaces
		} else if (white && (newC - oldC) == -2 && (Math.abs(oldR - newR) == 0)) {

			Piece king = this;
			Piece rook = board[oldR][oldC - 4];

			// Making sure that there are no pieces in between king and rook
			if (board[oldR][oldC - 1] == null && board[oldR][oldC - 2] == null && board[oldR][oldC - 3] == null) {

				// Making sure that king nor the spaces it will pass over and land onto can be
				// attack
				if (!Piece.kingChecked(board, "e1", Chess.blackPieces, white)
						&& !Piece.kingChecked(board, "d1", Chess.blackPieces, white)
						&& !Piece.kingChecked(board, "c1", Chess.blackPieces, white)
						&& !Piece.kingChecked(board, "b1", Chess.blackPieces, white)) {

					if (king.count == 0 && rook.count == 0) {

						Piece.move(board, "a1", "d1");
						Piece.updateHashmaps(board, Chess.whitePieces, Chess.blackPieces);

						return true;

					}

				}

			}

			// Moving Black King Right 2 spaces
		} else if (!white && (newC - oldC) == 2 && (Math.abs(oldR - newR) == 0)) {
			Piece king = this;
			Piece rook = board[oldR][oldC + 3];

			// Making sure that there are no pieces in between king and rook
			if (board[oldR][oldC + 1] == null && board[oldR][oldC + 2] == null) {

				// Making sure that king nor the spaces it will pass over and land onto can be
				// attack
				if (!Piece.kingChecked(board, "e8", Chess.whitePieces, white)
						&& !Piece.kingChecked(board, "f8", Chess.whitePieces, white)
						&& !Piece.kingChecked(board, "g8", Chess.whitePieces, white)) {

					if (king.count == 0 && rook.count == 0) {

						Piece.move(board, "h8", "f8");
						Piece.updateHashmaps(board, Chess.whitePieces, Chess.blackPieces);

						return true;

					}

				}

			}
			// Moving Black King Left 2 spaces
		} else if (!white && (newC - oldC) == -2 && (Math.abs(oldR - newR) == 0)) {
			Piece king = this;
			Piece rook = board[oldR][oldC - 4];

			if (board[oldR][oldC - 1] == null && board[oldR][oldC - 2] == null && board[oldR][oldC - 3] == null) {

				// Making sure that king nor the spaces it will pass over and land onto can be
				// attack
				if (!Piece.kingChecked(board, "e8", Chess.whitePieces, white)
						&& !Piece.kingChecked(board, "d8", Chess.whitePieces, white)
						&& !Piece.kingChecked(board, "c8", Chess.whitePieces, white)
						&& !Piece.kingChecked(board, "b8", Chess.whitePieces, white)) {

					if (king.count == 0 && rook.count == 0) {

						Piece.move(board, "a8", "d8");
						Piece.updateHashmaps(board, Chess.whitePieces, Chess.blackPieces);

						return true;

					}

				}

			}

		}

		return false;
	}

}
