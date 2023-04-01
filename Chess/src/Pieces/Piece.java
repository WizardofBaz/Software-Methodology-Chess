package Pieces;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Piece defines an abstract class that is implemented by different kinds of
 * chess pieces. This abstract class defines 14 methods that are vital to the
 * chess game. .
 * 
 * @author Osama Syed
 * @author Arbaz Pathan
 * 
 */

public abstract class Piece {

	/**
	 * String color keeps track of the color of the piece.
	 * 
	 */

	public String color;

	/**
	 * String current position keeps track of the position of the piece.
	 * 
	 */

	public String currentPosition;

	/**
	 * Integer that keeps track of number of moves.
	 * 
	 */
	public int count = 0;

	/**
	 *
	 * Boolean that keeps track if enPassant is available for a piece. It is only
	 * set true in the Pawn class if a player moves their Pawn 2 spaces on its first
	 * turn. It is later set to false after the opponent has made a move.
	 */
	public boolean epAvail = false;

	/**
	 * Method to check if the move done is valid.
	 * 
	 * @param board  the current playing board
	 * @param oldPos of the piece attempting to move
	 * @param newPos of the piece attempting to move
	 * @param white  if the piece is white or not
	 * 
	 * @return returns a boolean, whether or not the move made was valid
	 */

	public abstract boolean moveValid(Piece[][] board, String oldPos, String newPos, Boolean white);

	/**
	 * String method to convert to a String.
	 */

	public abstract String toString();

	/**
	 * A method that returns the color of a current piece.
	 * 
	 * @return a string corresponding to the color of the piece.
	 */

	public String getColor() {
		return color;
	};

	/**
	 * A method that returns the column of a piece given its position.
	 * 
	 * @param position current position
	 * @return an string corresponding to number of moves.
	 */

	public static int getCol(String position) {
		return position.charAt(0) - 97;

	};

	/**
	 * A method that returns the what row a piece is at given its position.
	 * 
	 * @param position current position.
	 * @return an integer corresponding to number of moves.
	 */

	public static int getRow(String position) {
		return Character.getNumericValue(position.charAt(1)) * (-1) + 8;
	};

	/**
	 * A boolean method that returns if a piece is attacking its own color. True if
	 * it is, and the move is illegal, false otherwise.
	 * 
	 * @param board  the current board
	 * @param oldPos the old position of the piece attempting to move
	 * @param newPos the new position of the piece attempting to move
	 * 
	 * @return an boolean if the piece is attacking its own color or not.
	 */

	public static boolean attackingOwnPiece(Piece[][] board, String oldPos, String newPos) {
		int oldR = getRow(oldPos);
		int oldC = getCol(oldPos);
		int newR = getRow(newPos);
		int newC = getCol(newPos);

		if (board[newR][newC] != null) {
			String newColor = board[newR][newC].color;
			String oldColor = board[oldR][oldC].color;

			if (newColor.equals(oldColor)) {
				return true;
			}

		}

		return false;
	}

	/**
	 * A boolean method that returns if a player is attempting to move its own piece
	 * or the other players piece, which would not be allowed.
	 * 
	 * @param whiteTurn true if it is white's turn false otherwise.
	 * 
	 * @return true if the piece is attacking its own piece.
	 */

	public boolean movingOwnPiece(Boolean whiteTurn) {
		if (this.color.equals("white") && whiteTurn)
			return true;
		if (this.color.equals("black") && !whiteTurn)
			return true;

		return false;
	};

	/**
	 * A method to actually move a players piece.
	 * 
	 * @param board  the current board
	 * @param oldPos the old position of a piece attempting to move
	 * @param newPos position the new position of a piece attempting to move
	 * 
	 */

	public static void move(Piece[][] board, String oldPos, String newPos) {

		int oldR = getRow(oldPos);
		int oldC = getCol(oldPos);
		int newR = getRow(newPos);
		int newC = getCol(newPos);

		Piece moving = board[oldR][oldC];

		moving.currentPosition = newPos;
		moving.count++;
		board[newR][newC] = moving;
		board[oldR][oldC] = null;

	};

	/**
	 * Method that updates both of the hashmap of piece locations based on the
	 * board.
	 * 
	 * @param board       the current playing board
	 * @param whitePieces all of the white pieces remaining
	 * @param blackPieces all of the black pieces remaining
	 */

	public static void updateHashmaps(Piece[][] board, HashMap<String, String> whitePieces,
			HashMap<String, String> blackPieces) {

		// This function edits the actual hash maps
		whitePieces.clear();
		blackPieces.clear();

		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {

				if (board[r][c] != null) {
					String position = board[r][c].currentPosition;
					String type = board[r][c].toString();
					if (board[r][c].color.equals("black")) {

						blackPieces.put(position, type);
					} else {
						whitePieces.put(position, type);
					}
				}
			}
		}

	}

	/**
	 * Method that checks whether or not the kings in check
	 * 
	 * @param board           the current playing board
	 * @param kingPosition    the position of the king
	 * @param attackingPieces that are left that are the opposite color of the king
	 * @param whiteTurn       tracking which players turn it is
	 * @return returns true if king is checked.
	 */

	public static boolean kingChecked(Piece[][] board, String kingPosition, HashMap<String, String> attackingPieces,
			boolean whiteTurn) {
		// Set of pieces that can attack
		Set<String> attackingKeys = attackingPieces.keySet();
		for (String attackingPosition : attackingKeys) {
			int r = getRow(attackingPosition);
			int c = getCol(attackingPosition);
			Piece currentPiece = board[r][c];
			if (currentPiece.moveValid(board, attackingPosition, kingPosition, whiteTurn)) {

				return true;

			}
		}

		return false;
	}

	/**
	 * Method that test whether or not the White King is in Checkmate, it does this
	 * by calculating the number of moves that a players piece can make to save its
	 * king.
	 * 
	 * @param board         the current playing board
	 * @param piecePosition the position of the piece which is being checked to see
	 *                      if it can save the king
	 * @param wkPosition    of the White King
	 * @param bkPosition    of the Black King
	 * @param blackPieces   all the black pieces remaining
	 * @param whiteTurn     tracking which players turn it is
	 * @return returns the number of moves the piece can make to save the king.
	 */

	public static int whiteCheckMate(Piece[][] board, String piecePosition, String wkPosition, String bkPosition,
			HashMap<String, String> blackPieces, Boolean whiteTurn) {
		int numberOfSavingMoves = 0;
		for (char a = 'a'; a < 'i'; a++) {
			for (int i = 1; i <= 8; i++) {
				String possibleMove = "" + a + i;

				int oldR2 = Piece.getRow(piecePosition);
				int oldC2 = Piece.getCol(piecePosition);

				Piece possiblePiece = board[oldR2][oldC2];

				if (!Piece.attackingOwnPiece(board, piecePosition, possibleMove)) {
					if (possiblePiece.moveValid(board, piecePosition, possibleMove, !whiteTurn)) {

						Piece[][] boardCopy2 = Arrays.stream(board).map(Piece[]::clone).toArray(Piece[][]::new);

						// perform move on copied board
						Piece.move(boardCopy2, piecePosition, possibleMove);

						// change position of king trackers
						if (possiblePiece.toString().charAt(1) == 'K') {
							if (!whiteTurn) {
								wkPosition = possibleMove;
							} else {
								bkPosition = possibleMove;

							}
						}

						// test if king is still in check
						if (!Piece.kingChecked(boardCopy2, wkPosition, blackPieces, whiteTurn)) {

							numberOfSavingMoves++;
						}

						// revert changes to king trackers and currentPiece position
						possiblePiece.currentPosition = piecePosition;
						possiblePiece.count--;
						if (possiblePiece.toString().charAt(1) == 'K') {
							if (!whiteTurn) {
								wkPosition = piecePosition;
							} else {
								bkPosition = piecePosition;
							}
						}

					}

				}

			}
		}

		return numberOfSavingMoves;

	}

	/**
	 * Method that test whether or not the Black King is in Checkmate, it does this
	 * by calculating the number of moves that a players piece can make to save its
	 * king.
	 * 
	 * @param board         the current playing board
	 * @param piecePosition the position of the piece which is being checked to see
	 *                      if it can save the king
	 * @param wkPosition    of the White King
	 * @param bkPosition    of the Black King
	 * @param whitePieces   all the white pieces remaining
	 * @param whiteTurn     tracking which players turn it is
	 * @return returns the number of moves the piece can make to save the king.
	 */

	public static int blackCheckMate(Piece[][] board, String piecePosition, String wkPosition, String bkPosition,
			HashMap<String, String> whitePieces, Boolean whiteTurn) {
		int numberOfSavingMoves = 0;
		for (char a = 'a'; a < 'i'; a++) {
			for (int i = 1; i <= 8; i++) {
				String possibleMove = "" + a + i;

				int oldR2 = Piece.getRow(piecePosition);
				int oldC2 = Piece.getCol(piecePosition);

				Piece possiblePiece = board[oldR2][oldC2];

				if (!Piece.attackingOwnPiece(board, piecePosition, possibleMove)) {
					if (possiblePiece.moveValid(board, piecePosition, possibleMove, !whiteTurn)) {

						Piece[][] boardCopy2 = Arrays.stream(board).map(Piece[]::clone).toArray(Piece[][]::new);

						// perform move on copied board
						Piece.move(boardCopy2, piecePosition, possibleMove);

						// change position of king trackers
						if (possiblePiece.toString().charAt(1) == 'K') {
							if (!whiteTurn) {
								wkPosition = possibleMove;
							} else {
								bkPosition = possibleMove;

							}
						}

						// test if king is still in check

						if (!Piece.kingChecked(boardCopy2, bkPosition, whitePieces, whiteTurn)) {

//							System.out.println(bkPosition);
							numberOfSavingMoves++;

						}

						// revert changes to king trackers and currentPiece position
						possiblePiece.currentPosition = piecePosition;
						possiblePiece.count--;
						if (possiblePiece.toString().charAt(1) == 'K') {
							if (!whiteTurn) {
								wkPosition = piecePosition;
							} else {
								bkPosition = piecePosition;
							}
						}

					}

				}

			}
		}

		return numberOfSavingMoves;
	}

	/**
	 * Method that checks if the pawn has made it to the opposite side thus making
	 * it into a new piece.
	 * 
	 * @param board        the current playing board
	 * @param currentPiece the current pawn
	 * @param nextMove     the next implemented by the user
	 * @param whiteTurn    tracking which players turn it is
	 */

	public static void pawnPromotion(Piece[][] board, Piece currentPiece, String nextMove, Boolean whiteTurn) {

		int entryLength = nextMove.split(" ").length;
		String newPos = nextMove.split(" ")[1];
		int newR = Piece.getRow(newPos);
		int newC = Piece.getCol(newPos);

		if (entryLength == 2) {

			if (whiteTurn) {
				board[newR][newC] = new Queen("white", currentPiece.currentPosition);

			} else {
				board[newR][newC] = new Queen("black", currentPiece.currentPosition);

			}

		}

		if (entryLength == 3) {

			String type = nextMove.split(" ")[2];

			if (type.length() == 1) {
				if (type.equals("Q")) {
					if (whiteTurn) {
						board[newR][newC] = new Queen("white", currentPiece.currentPosition);

					} else {
						board[newR][newC] = new Queen("black", currentPiece.currentPosition);

					}
				} else if (type.equals("N")) {
					if (whiteTurn) {
						board[newR][newC] = new Knight("white", currentPiece.currentPosition);

					} else {
						board[newR][newC] = new Knight("black", currentPiece.currentPosition);

					}
				} else if (type.equals("R")) {
					if (whiteTurn) {
						board[newR][newC] = new Rook("white", currentPiece.currentPosition);

					} else {
						board[newR][newC] = new Rook("black", currentPiece.currentPosition);

					}
				} else if (type.equals("B")) {
					if (whiteTurn) {
						board[newR][newC] = new Bishop("white", currentPiece.currentPosition);

					} else {
						board[newR][newC] = new Bishop("black", currentPiece.currentPosition);

					}
				}
			} else {
				if (whiteTurn) {
					board[newR][newC] = new Queen("white", currentPiece.currentPosition);

				} else {
					board[newR][newC] = new Queen("black", currentPiece.currentPosition);

				}
			}

		}

		if (entryLength == 4) {
			String type = nextMove.split(" ")[2];
			if (type.equals("Q")) {
				if (whiteTurn) {
					board[newR][newC] = new Queen("white", currentPiece.currentPosition);

				} else {
					board[newR][newC] = new Queen("black", currentPiece.currentPosition);

				}
			} else if (type.equals("N")) {
				if (whiteTurn) {
					board[newR][newC] = new Knight("white", currentPiece.currentPosition);

				} else {
					board[newR][newC] = new Knight("black", currentPiece.currentPosition);

				}
			} else if (type.equals("R")) {
				if (whiteTurn) {
					board[newR][newC] = new Rook("white", currentPiece.currentPosition);

				} else {
					board[newR][newC] = new Rook("black", currentPiece.currentPosition);

				}
			} else if (type.equals("B")) {
				if (whiteTurn) {
					board[newR][newC] = new Bishop("white", currentPiece.currentPosition);

				} else {
					board[newR][newC] = new Bishop("black", currentPiece.currentPosition);

				}
			}

		}

	}

}
