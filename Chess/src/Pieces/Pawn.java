package Pieces;

import Chess.*;

/**
 * Class with constructors for the Rook Piece and methods relating to it
 * @author Osama Syed
 * @author Arbaz Pathan
 *
 */

public class Pawn extends Piece {

	/**
	 * Constructor for Pawn
	 * 
	 * @param color           the color of the pawn
	 * @param initialPosition the initial position of the pawn
	 * 
	 */

	public Pawn(String color, String initialPosition) {
		this.currentPosition = initialPosition;
		this.color = color;
	}

	/**
	 * This is a boolean method and is used to make sure that all of the moves using
	 * the pawn is valid.
	 * 
	 * 
	 * @param board     the playing board
	 * @param oldPos    the old position of the pawn
	 * @param newPos    the new position of the pawn
	 * @param whiteTurn boolean, true if it is white's turn
	 * 
	 * @return true or false
	 */

	@Override
	public boolean moveValid(Piece[][] board, String oldPos, String newPos, Boolean whiteTurn) {
		int oldR = getRow(oldPos);
		int oldC = getCol(oldPos);
		int newR = getRow(newPos);
		int newC = getCol(newPos);

		boolean attacking = oldC != newC;

		// Checking to see move is valid when not attacking

		if (whiteTurn && !attacking) {

			// making sure move is only one or two spaces
			if (oldR - newR != 1 && oldR - newR != 2)
				return false;
			// making sure Pawn only moves two spaces on its first turn
			if (oldR - newR == 2 && count != 0)
				return false;

			// making sure there is no piece in way of non attacking move
			if (board[newR][newC] != null)
				return false;
			if (oldR - newR == 2) {
				if (board[newR + 1][newC] != null)
					return false;
			}

		} else if (!whiteTurn && !attacking) {

			// making sure move is only one or two spaces

			if (newR - oldR != 1 && newR - oldR != 2)
				return false;
			// making sure Pawn only moves two spaces on its first turn
			if (newR - oldR == 2 && count != 0)
				return false;

			// making sure there is no piece in way of non attacking move
			if (board[newR][newC] != null)
				return false;
			if (newR - oldR == 2) {
				if (board[newR - 1][newC] != null)
					return false;
			}

		}
		
		
		
		

		// implementing enPassant 

		if (Chess.lastPieceMoved != null) {
			if (Chess.lastPieceMoved.epAvail) {
				if (whiteTurn && attacking) {
					if (board[oldR][newC] == Chess.lastPieceMoved) {
						if ((newR - oldR) == -1 && (Math.abs(oldC - newC) == 1)) {

							board[oldR][newC] = null;
							return true;

						}

					}

				} else if (!whiteTurn && attacking) {
					if (board[oldR][newC] == Chess.lastPieceMoved) {
						if ((newR - oldR) == 1 && (Math.abs(oldC - newC) == 1)) {
							board[oldR][newC] = null;
							return true;

						}
					}
				}

			}
		}
		
		
		
		
		

		// Checking to see if move is valid when attacking
		if (whiteTurn && attacking) {
			if (Math.abs(oldC - newC) != 1 || oldR - newR != 1 || board[newR][newC] == null)
				return false;

		} else if (!whiteTurn && attacking) {
			if (Math.abs(oldC - newC) != 1 || newR - oldR != 1 || board[newR][newC] == null)
				return false;
		}

		// after all the checks if it is a two space move trigger the epAvail to true
		if (Math.abs(newR - oldR) == 2) {
			this.epAvail = true;
		}

		return true;
	}

	/**
	 * Overriding toString method, so that the string shows color and type of piece
	 *
	 * @return string representing piece
	 */

	@Override
	public String toString() {
		return this.color.substring(0, 1) + "p";
	}

}
