import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	//constants you may find useful
	private int ROWS; //initialized in constructor
	private int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException{
		Scanner fileScan = null;
		String fileRow;
		boolean containsStart = false;
		boolean containsEnd = false;

		try {
			fileScan = new Scanner(new File(filename));

             //attempts to grab the first row of numbers
			fileRow = fileScan.nextLine();
			Scanner rowColScanner = new Scanner(fileRow);
            ROWS = rowColScanner.nextInt();
            COLS = rowColScanner.nextInt();

            if(rowColScanner.hasNextInt()){ //checks if their is any more numbers in the row and column initalizer.
				rowColScanner.close();
				throw new InputMismatchException("Invalid format for initalizing rows and columns.");
            }
			rowColScanner.close();
        } catch(InputMismatchException e) {
			throw new InvalidFileFormatException("invalid input type for row and column initialization");
        }

		board = new char[ROWS][COLS];
		for(int i = 0; i < ROWS; i++) {
			fileRow = fileScan.nextLine(); //scans each row to then be put into the char array
			fileRow = fileRow.replaceAll("\\s", "");
			if (fileRow.length() != COLS) {
				throw new InvalidFileFormatException("Rows and columns do not match inputted values");
			}
			for(int j = 0; j < COLS; j++) {
				try{
				board[i][j] = fileRow.charAt(j);
				} catch (StringIndexOutOfBoundsException e) {
					throw new InvalidFileFormatException("Rows and columns do not match inputted values");
				}
				if(board[i][j] != OPEN && board[i][j] != CLOSED && board[i][j] != START && board[i][j] != END) {
					throw new InvalidFileFormatException("Invalid value at (" + i + 1 + "," + j + 1 + ")");
				}
				if(board[i][j] == START && containsStart == false) {
					startingPoint = new Point(i, j);
					containsStart = true;
				} else if (containsStart == true && board[i][j] == START) {
					throw new InvalidFileFormatException("Duplicate starting positions in file");
				}
				if(board[i][j] == END && containsEnd == false) {
					endingPoint = new Point(i, j);
					containsEnd = true;
				} else if (containsEnd == true && board[i][j] == END) {
					throw new InvalidFileFormatException("Duplicate ending positions in file");
				}
			}
		}
		if(fileScan.hasNextLine()) {
			throw new InvalidFileFormatException("Number of rows does not match inputted value");
		}
		if(containsStart == false) {
			throw new InvalidFileFormatException("No starting point was found in the file");
		}
		if(containsEnd == false) {
			throw new InvalidFileFormatException("No ending point was found in the file");
		}
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
