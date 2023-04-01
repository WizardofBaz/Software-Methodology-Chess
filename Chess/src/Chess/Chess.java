package Chess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import Pieces.*;

/**
 * 
 * Class that runs the game, uses constructors and methods from the Board, and abstract Piece class.
 * 
 * @author Osama Syed
 * @author Arbaz Pathan
 * 
 */



public class Chess extends Board {
	

	/**
	 * A hashmap that contains all of the
	 * White pieces with key being the position
	 * and what type of piece it is.
	 *
	 */
	
	public static HashMap<String, String> whitePieces = new HashMap<String,String>();
	
	
	/**
	 * A hashmap that contains all of the
	 * black pieces with key being the position
	 * and what type of piece it is.
	 *
	 */
	
	public static HashMap<String, String> blackPieces = new HashMap<String,String>();
	
	/**
	 * Keeps track of the last piece that was moved
	 * 
	 */
	public static Piece lastPieceMoved;


	
	public static void main(String[] args) {
		// Board.boardmaker();
		

		Scanner sc;
		
		Piece[][] board = new Piece[8][8];
				
		
		/**
		 * Our boolean fields which will
		 * help us keep track of key elements
		 * while the user is playing our game.
		 */
		
		boolean gameOver = false;
		boolean whiteTurn = true;
		boolean printBoard = true;
		boolean check = false;
		boolean draw = false;

		whitePieces = setWhitePieces(board);
		blackPieces = setBlackPieces(board);

		// Tracking where the king is at all times
		String bkPosition = "e8";
		String wkPosition = "e1";
		
		
		/**
		 * This is where the main chunk of the
		 * game is implemented. Inside this loop
		 * all of the moves and rules will be made. 
		 */
		
		String nextMove = "";
		
		sc = new Scanner(System.in);
		
		
		while (!gameOver) {


			if (printBoard) {
				printBoard(board);

			}

			// ask user for input


			if (check)
				System.out.println("Check");

			if (whiteTurn) {

				System.out.print("White's move: ");

			} else {

				System.out.print("Black's move: ");
			}

			nextMove = sc.nextLine();
			
			if(nextMove.isEmpty()) {
				System.out.println("Illegal move, try again");
				printBoard = false;
				continue;
			}
				
				

			

			
			String r = "resign";
			if (nextMove.contentEquals(r) && whiteTurn) {
				System.out.println("\nBlack Wins!");
				gameOver = true;
				break;
			}

			if (nextMove.contentEquals(r) && !whiteTurn) {
				System.out.println("\nWhite Wins!");
				gameOver = true;
				break;
			}

			
			

			
			String d = "draw";
			String dd = "draw?";
			
			if(draw && nextMove.contains(d)){
				System.out.println("\ndraw");
				gameOver = true;
				//System.out.println("over");
				break;
			} else if(draw) {
				draw = false;
			}
			
			if(nextMove.contains(dd) && whiteTurn) {
				whiteTurn = false;
				draw = true;
				whiteTurn = true;
			}
			
			if(nextMove.contains(dd) && !whiteTurn) {
				whiteTurn = true;
				draw = true;
				whiteTurn = false;
			}
	
			
			String oldPos = nextMove.split(" ")[0];
			String newPos = nextMove.split(" ")[1];
			
			// get array indices for current piece
			int oldR = Piece.getRow(oldPos);
			int oldC = Piece.getCol(oldPos);


			Piece currentPiece = board[oldR][oldC];

			// String for position that current piece is to be moved
			
			/**
			 * A boolean field that will make sure that the 
			 * move the user tried to do is successful. Will 
			 * make sure the user is not attacking their own 
			 * piece, or the user will not move the other player's 
			 * piece. Also check to see the user does not move into
			 * check.
			 */

			boolean moveSuccessful = false;

			if (currentPiece != null) {

				// Make sure that player is moving their own piece
				if (currentPiece.movingOwnPiece(whiteTurn)) {

					// Make sure that player is not attacking their own piece
					if (!Piece.attackingOwnPiece(board, oldPos, newPos)) {
						
						// Make sure that piece is moving properly
						if (currentPiece.moveValid(board, oldPos, newPos, whiteTurn)) {

							// -----Create a copy of the board-----------
							Piece[][] boardCopy = Arrays.stream(board).map(Piece[]::clone).toArray(Piece[][]::new);

							// perform move on copied board
							Piece.move(boardCopy, oldPos, newPos);

							// change position of king trackers
							if (currentPiece.toString().charAt(1) == 'K') {
								if (whiteTurn) {
									wkPosition = newPos;
								} else {
									bkPosition = newPos;
								}
							}

							// on copied board -test if players move resulted in check of own king 
							Piece.updateHashmaps(boardCopy, whitePieces, blackPieces);
														
							if (whiteTurn) {
								if (!Piece.kingChecked(boardCopy, wkPosition, blackPieces, whiteTurn)) {
									moveSuccessful = true;
									check = false;
								}
								;

							} else {
								if (!Piece.kingChecked(boardCopy, bkPosition, whitePieces, whiteTurn)) {
									moveSuccessful = true;
									check = false;
								}
							}

							// revert changes to king trackers and currentPiece position
							currentPiece.currentPosition = oldPos;
							currentPiece.count--;
							if (currentPiece.toString().charAt(1) == 'K') {
								if (whiteTurn) {
									wkPosition = oldPos;
								} else {
									bkPosition = oldPos;
								}
							}
							// ------DONE WITH COPIED BOARD---------

							
							//if move was successful in copied board then finalize changes on board
							if (moveSuccessful) {
								Piece.move(board, oldPos, newPos);

								// Checking if pawn was moved
								if (currentPiece.toString().charAt(1) == 'p') {

									if (whiteTurn) {
										if (currentPiece.currentPosition.charAt(1) == '8') {

											Piece.pawnPromotion(board, currentPiece, nextMove, whiteTurn);

										}

									} else {

										if (currentPiece.currentPosition.charAt(1) == '1') {
											Piece.pawnPromotion(board, currentPiece, nextMove, whiteTurn);

										}

									}

								}
								
								if(lastPieceMoved!=null) {
									lastPieceMoved.epAvail = false;
								}
								lastPieceMoved = currentPiece;

								Piece.updateHashmaps(board, whitePieces, blackPieces);


								if (currentPiece.toString().charAt(1) == 'K') {
									if (whiteTurn) {
										wkPosition = newPos;
									} else {
										bkPosition = newPos;
									}
								}

			/**
   		     * This section of the code makes sure that
   		     * the king is not in check. This will check
   		     * to see this after every move. Inside this section
   		     * there is also the check to make sure that one
   		     * of the players is not in checkmate which would
   		     * result in the other player being defeated.
			 */

								if (whiteTurn) {
									// Testing if black King is in check
									if (Piece.kingChecked(boardCopy, bkPosition, whitePieces, whiteTurn)) {
										check = true;

										// Check if opponent can make a move to get out of check

										Set<String> remainingBlackPieces;
										remainingBlackPieces = blackPieces.keySet();

										// Iterate through each of the opponents pieces
										int numberOfSavingMoves = 0;
										for (String piecePosition : remainingBlackPieces) {

											// For each piece check try moving it on a copied board
											numberOfSavingMoves += Piece.blackCheckMate(board, piecePosition,
													wkPosition, bkPosition, whitePieces, whiteTurn);

										}
										if (numberOfSavingMoves == 0) {
											printBoard(board);
											System.out.println("Checkmate White Wins");
											gameOver = true;
										}

									}
								} else {
									// Testing if White King is in check
									if (Piece.kingChecked(boardCopy, wkPosition, blackPieces, whiteTurn)) {
										check = true;

										// Check if opponent can make a move to get out of check

										Set<String> remainingWhitePieces;
										remainingWhitePieces = whitePieces.keySet();

										// Iterate through each of the opponents pieces
										int numberOfSavingMoves = 0;
										for (String piecePosition : remainingWhitePieces) {

											// For each piece check try moving it on a copied board
											numberOfSavingMoves += Piece.whiteCheckMate(board, piecePosition,
													wkPosition, bkPosition, blackPieces, whiteTurn);

										}

										if (numberOfSavingMoves == 0) {
											printBoard(board);
											System.out.println("Checkmate Black Wins");
											gameOver = true;
										}
									}
								}

							}

						}

					}

				}

			}

			/**
			 * This if statement will be implemented
			 * once the move the user tried to do is 
			 * actually successful. If the move fails this
			 * if statement then they will be prompted that the
			 * move is illegal and they must do a different 
			 * move.
			 */		
			
			if (moveSuccessful) {

				whiteTurn = !whiteTurn;
				printBoard = true;
				System.out.println();

			} else {
				printBoard = false;
				
				System.out.println("Illegal move,try again");
				continue;
				}
			
			;
			

		}
		
		/**
		 * If the boolean field "gameOver"
		 * is true then this if statement
		 * is implemented, thus ending the game
		 */
		
		if (gameOver == true) {
			sc.close();
			System.exit(0);
		}

	}
}
