import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 */
public class CircuitTracer {

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args){
		new CircuitTracer(args); //create this with args
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {
		System.out.println("Usage: java CircuitTracer [-s stack storage | -q queueu storage] [-c run program in console mode | -g GUI mode] [filename]");
		// any command line args
		// See https://en.wikipedia.org/wiki/Usage_message for format and content guidance
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 * @throws FileNotFoundException
	 */
	public CircuitTracer(String[] args) {
		if (args.length != 3) {
			printUsage();
			return; //exit the constructor immediately
		}
		String storageType = args[0];
		String outputType = args[1];
		String fileName = args[2];
		if (!(storageType.equals("-s") || storageType.equals("-q")) || (!(outputType.equals("-c") || outputType.equals("-g")))){ //checks if valid arugemnts
			printUsage();
			return;
		}

		Storage<TraceState> stateStore = null;
		if (storageType.equals("-s")) {
			stateStore = new Storage<>(Storage.DataStructure.stack);
		}
		if (storageType.equals("-q")) {
			stateStore = new Storage<>(Storage.DataStructure.queue);
		}

		ArrayList<TraceState> bestPaths = new ArrayList<>();
		CircuitBoard circuitBoard = null;
		
		try{
			circuitBoard = new CircuitBoard(fileName);
		} catch (FileNotFoundException e) {
			System.out.print(e.toString());
			return;
		} catch (InvalidFileFormatException e) {
			System.out.print(e.toString());
		}

		Point adjacentStartPoint = circuitBoard.getStartingPoint(); 
		TraceState initialState;

		try{
			if(circuitBoard.isOpen((int) adjacentStartPoint.getX() - 1, (int) adjacentStartPoint.getY())); { //checks if above start is open
				initialState = new TraceState(circuitBoard, (int) adjacentStartPoint.getX() - 1,(int) adjacentStartPoint.getY());
				stateStore.store(initialState);
			} 
		} catch(IndexOutOfBoundsException e){} //if either excpetion caught, then point is out of bounds or occupied
		  catch(OccupiedPositionException e){}

		try {
			if (circuitBoard.isOpen((int) adjacentStartPoint.getX(), (int) adjacentStartPoint.getY() - 1)) { //checks if to the left of start is open
				initialState = new TraceState(circuitBoard, (int) adjacentStartPoint.getX(), (int) adjacentStartPoint.getY() - 1);
				stateStore.store(initialState);
			}	 
		} catch(IndexOutOfBoundsException e){}
		  catch(OccupiedPositionException e){}

		try {
			if (circuitBoard.isOpen((int) adjacentStartPoint.getX() + 1, (int) adjacentStartPoint.getY())) { //checks if below start is open
				initialState = new TraceState(circuitBoard, (int) adjacentStartPoint.getX() + 1, (int) adjacentStartPoint.getY());
				stateStore.store(initialState);
			}
		} catch(IndexOutOfBoundsException e){}
		  catch(OccupiedPositionException e){}

		try {
			if (circuitBoard.isOpen((int) adjacentStartPoint.getX(), (int) adjacentStartPoint.getY() + 1)) { //checks if to the right of start is open
				initialState = new TraceState(circuitBoard, (int) adjacentStartPoint.getX(), (int) adjacentStartPoint.getY() + 1);
				stateStore.store(initialState);
			}
		} catch(IndexOutOfBoundsException e){}
		  catch(OccupiedPositionException e){}

		while (!stateStore.isEmpty()) {
			TraceState currentState = stateStore.retrieve();
			if(currentState.isComplete()) {
				if(bestPaths.isEmpty() || currentState.pathLength() == bestPaths.get(0).pathLength()) { //checks if best paths should be updated if solution is found
					bestPaths.add(currentState);
				} else if (currentState.pathLength() < bestPaths.get(0).pathLength()) {
					bestPaths.clear();
					bestPaths.add(currentState);
				}
			} else {
				try{
					if(circuitBoard.isOpen(currentState.getRow() - 1, currentState.getCol())); { //checks if above start is open
						initialState = new TraceState(currentState, currentState.getRow() - 1, currentState.getCol());
						stateStore.store(initialState);
					} 
				} catch(IndexOutOfBoundsException e){} //if either excpetion caught, then point is out of bounds or occupied
				  catch(OccupiedPositionException e){}

				try{
					if(circuitBoard.isOpen(currentState.getRow(), currentState.getCol() - 1)); {
						initialState = new TraceState(currentState, currentState.getRow(), currentState.getCol() - 1);
						stateStore.store(initialState);
					} 
				} catch(IndexOutOfBoundsException e){}
				  catch(OccupiedPositionException e){}

				try{
					if(circuitBoard.isOpen(currentState.getRow() + 1, currentState.getCol())); {
						initialState = new TraceState(currentState, currentState.getRow() + 1, currentState.getCol());
						stateStore.store(initialState);
					} 
				} catch(IndexOutOfBoundsException e){}
				  catch(OccupiedPositionException e){}	
				
				try{
					if(circuitBoard.isOpen(currentState.getRow(), currentState.getCol() + 1)); { 
						initialState = new TraceState(currentState, currentState.getRow(), currentState.getCol() + 1);
						stateStore.store(initialState);
					} 
				} catch(IndexOutOfBoundsException e){}
				  catch(OccupiedPositionException e){}
			}
		}
		if(outputType.equals("-c")) {
			for(int i = 0; i < bestPaths.size(); i++) {
				System.out.println(bestPaths.get(i).toString());
			}
		} else {
			System.out.print("GUI coming soon to version 2.0");
		}
	}
	
} // class CircuitTracer
