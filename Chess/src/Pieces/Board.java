package Pieces;

import java.util.HashMap;


/**
 * Class containing methods which setup the board
 * 
 * @author Osama Syed
 * @author Arbaz Pathan
 *
 */

public class Board {
	
	
	/**
	 * static method is used place white pieces on the 2D Piece array
	 *  
	 * 
	 * @param emptyBoard the 8x8 2D array (empty)
	 * 
	 * @return returns a hashmap of whitePieces left on the board
	 */
	
	
	public static HashMap<String,String> setWhitePieces(Piece [][] emptyBoard) {

		
		emptyBoard[7][0] = new Rook("white","a1");
		emptyBoard[7][1] = new Knight("white","b1");
		emptyBoard[7][2] = new Bishop("white","c1");				
		emptyBoard[7][3] = new Queen("white","d1");				
		emptyBoard[7][4] = new King("white","e1");	
		emptyBoard[7][5] = new Bishop("white","f1");				
		emptyBoard[7][6] = new Knight("white","g1");
		emptyBoard[7][7] = new Rook("white","h1");
		
		//Setting white Pawn Pieces	
		for(int i = 0; i<8;i++) {
			emptyBoard[6][i] = new Pawn("white",Character.toString(97+i)+2);
			
		}
		
		/**
		 * This loop is used to set up the white pawns
		 *   
		 */
		
		
		HashMap<String,String> whitePieces = new HashMap<String,String>();
		for(int r = 0; r<8;r++) {
			for(int c = 0;c<8;c++ ) {
				
				if(emptyBoard[r][c]!=null && emptyBoard[r][c].color.equals("white")) {
					String position = emptyBoard[r][c].currentPosition;
					String type = emptyBoard[r][c].toString();
					whitePieces.put(position, type);
				}
			}
		}
		
		return whitePieces;

	}
	
	
	/**
	 * static method is used place white pieces on the 2D Piece array
	 *  
	 * 
	 * @param emptyBoard the 8x8 2D arraw (empty)
	 * 
	 * @return returns a hashmap of whitePieces left on the board
	 */
	
	public static HashMap<String,String> setBlackPieces(Piece[][] emptyBoard) {
		emptyBoard[0][0] = new Rook("black","a8");
		emptyBoard[0][1] = new Knight("black","b8");
		emptyBoard[0][2] = new Bishop("black","c8");				
		emptyBoard[0][3] = new Queen("black","d8");				
		emptyBoard[0][4] = new King("black","e8");	
		emptyBoard[0][5] = new Bishop("black","f8");				
		emptyBoard[0][6] = new Knight("black","g8");
		emptyBoard[0][7] = new Rook("black","h8");
		
		
		/**
		 * This loop is used to set up the white pawns
		 *   
		 */
		
		for(int i = 0; i<8;i++) {
			emptyBoard[1][i] = new Pawn("black",Character.toString(97+i)+7);
			
		}
		


		
		HashMap<String,String> blackPieces = new HashMap<String,String>();
		for(int r = 0; r<8;r++) {
			for(int c = 0;c<8;c++ ) {
				
				if(emptyBoard[r][c]!=null && emptyBoard[r][c].color.equals("black")) {
					String position = emptyBoard[r][c].currentPosition;
					String type = emptyBoard[r][c].toString();
					blackPieces.put(position, type);
				}	
			}
		}
		
		return blackPieces;
	}
	
	/**
	 * This method is used to print out the board.
	 * We use this after every move is implemented.
	 *  
	 * 
	 * @param currentBoard the current status of the board.
	 * 
	 */
	
	public static void printBoard(Piece[][] currentBoard) {
		for(int i = 0 ; i<8; i++) {
			for(int j = 0; j<8; j++) {
				if(currentBoard[i][j]!=null) {
				System.out.print(currentBoard[i][j]+" ");
				}
				else if(((i+j)%2)==0) {
					System.out.print("## ");
				}
				else {
					System.out.print("   ");
				}
			}
			System.out.print(8-i);
			System.out.println();
		}
		System.out.println(" a  b  c  d  e  f  g  h \n");
	}
	
}

