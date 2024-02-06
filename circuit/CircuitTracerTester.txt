import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A unit test class for CircuitTracer (and CircuitBoard)
 * 
 * @author mvail
 */
public class CircuitTracerTester {
	// possible results expected in tests
	private static enum Result {
		FileNotFound, InvalidFileFormat, NoException, UnexpectedException, MatchingContents, ValidOutput, InvalidOutput,
		Fail
	};

	// maximum seconds willing to wait for CircuitTracer to complete a search
	private static final int TIMEOUT = 10;

	// command line arguments for CircuitTracer
	private static final String STACK = "-s";
	private static final String QUEUE = "-q";
	private static final String CONSOLE = "-c";
	private static final String GUI = "-g";
	private static final String INVALID_OPTION = "-z";

	// bogus file name
	private static final String NO_SUCH = "noSuchFile";

	// tracking number of tests and test results
	private final int EXPECTED_TOTAL_TESTS = 86;
	private int totalTests;
	private int passes = 0;
	private int failures = 0;
	private int totalRun = 0;

	/**
	 * Initialize and run tests
	 * 
	 * @param args not used
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// to avoid every method being static
		CircuitTracerTester tester = new CircuitTracerTester(args);
		tester.runTests();
	}

	/**
	 * tester constructor
	 * 
	 * @param args command line args
	 * @throws FileNotFoundException
	 */
	public CircuitTracerTester(String[] args) throws FileNotFoundException {
		totalTests = 0;
		makeFiles();
	}

	/**
	 * Print test results in a consistent format
	 * 
	 * @param testDesc description of the test
	 * @param result   indicates if the test passed or failed
	 */
	private void printTest(String testDesc, boolean result) {
		totalRun++;
		if (result) {
			passes++;
		} else {
			failures++;
		}
		System.out.printf("%-46s\t%s\n\n", testDesc, (result ? "   PASS" : "***FAIL***"));
	}

	/** Print a final summary */
	private void printFinalSummary() {
		String verdict = String.format("\nTotal Tests Run: %d of %d,  Passed: %d (%.1f%%),  Failed: %d\n", totalRun,
				totalTests, passes, passes * 100.0 / totalTests, failures);
		String line = "";
		for (int i = 0; i < verdict.length(); i++) {
			line += "-";
		}
		System.out.println(line);
		System.out.println(verdict);
		if (totalTests != EXPECTED_TOTAL_TESTS) {
			System.out.printf("Expected %d total tests, but evaluated %d.\n", EXPECTED_TOTAL_TESTS, totalTests);
		}
	}

	// XXX runTests()
	// see the blue box on the right of the scroll bar? the triple-X tag aids in
	// navigating long files
	/**
	 * Run tests to confirm required functionality from list constructors and
	 * methods
	 */
	private void runTests() {

		// simply here to ensure that CircuitTracer is compiled
		// when CircuitTracerTester is compiled
		try {
			String[] someArgs = { "-s", "-c", VALID_0_FILENAME };
			CircuitTracer neverUsed = new CircuitTracer(someArgs);
			neverUsed.toString(); // don't care, just calling something
		} catch (Exception e) {
			// don't care
		}

		System.out.println("*******************************");
		System.out.println("CircuitBoard Constructor Tests");
		System.out.println("*******************************\n");
		try {
			// invalid file name
			printTest("CircuitBoard(\"" + NO_SUCH + "\")", testCircuitBoard(NO_SUCH, null, Result.FileNotFound));
			// valid file formats
			printTest("CircuitBoard(\"" + VALID_1_FILENAME + "\")",
					testCircuitBoard(VALID_1_FILENAME, VALID_1, Result.MatchingContents));
			printTest("CircuitBoard(\"" + VALID_2_FILENAME + "\")",
					testCircuitBoard(VALID_2_FILENAME, VALID_2, Result.MatchingContents));
			printTest("CircuitBoard(\"" + VALID_3_FILENAME + "\")",
					testCircuitBoard(VALID_3_FILENAME, VALID_3, Result.MatchingContents));
			printTest("CircuitBoard(\"" + VALID_4_FILENAME + "\")",
					testCircuitBoard(VALID_4_FILENAME, VALID_4, Result.MatchingContents));
			printTest("CircuitBoard(\"" + VALID_5_FILENAME + "\")",
					testCircuitBoard(VALID_5_FILENAME, VALID_5, Result.MatchingContents));
			printTest("CircuitBoard(\"" + VALID_6_FILENAME + "\")",
					testCircuitBoard(VALID_6_FILENAME, VALID_6, Result.MatchingContents));
			printTest("CircuitBoard(\"" + VALID_7_FILENAME + "\")",
					testCircuitBoard(VALID_7_FILENAME, VALID_7, Result.MatchingContents));
			printTest("CircuitBoard(\"" + VALID_8_FILENAME + "\")",
					testCircuitBoard(VALID_8_FILENAME, VALID_8, Result.MatchingContents));
			printTest("CircuitBoard(\"" + VALID_9_FILENAME + "\")",
					testCircuitBoard(VALID_9_FILENAME, VALID_9, Result.MatchingContents));
			printTest("CircuitBoard(\"" + VALID_10_FILENAME + "\")",
					testCircuitBoard(VALID_10_FILENAME, VALID_10, Result.MatchingContents));
			// invalid file formats
			printTest("CircuitBoard(\"" + INVALID_1_FILENAME + "\")",
					testCircuitBoard(INVALID_1_FILENAME, INVALID_1, Result.InvalidFileFormat));
			printTest("CircuitBoard(\"" + INVALID_2_FILENAME + "\")",
					testCircuitBoard(INVALID_2_FILENAME, INVALID_2, Result.InvalidFileFormat));
			printTest("CircuitBoard(\"" + INVALID_3_FILENAME + "\")",
					testCircuitBoard(INVALID_3_FILENAME, INVALID_3, Result.InvalidFileFormat));
			printTest("CircuitBoard(\"" + INVALID_4_FILENAME + "\")",
					testCircuitBoard(INVALID_4_FILENAME, INVALID_4, Result.InvalidFileFormat));
			printTest("CircuitBoard(\"" + INVALID_5_FILENAME + "\")",
					testCircuitBoard(INVALID_5_FILENAME, INVALID_5, Result.InvalidFileFormat));
			printTest("CircuitBoard(\"" + INVALID_6_FILENAME + "\")",
					testCircuitBoard(INVALID_6_FILENAME, INVALID_6, Result.InvalidFileFormat));
			printTest("CircuitBoard(\"" + INVALID_7_FILENAME + "\")",
					testCircuitBoard(INVALID_7_FILENAME, INVALID_7, Result.InvalidFileFormat));
			printTest("CircuitBoard(\"" + INVALID_8_FILENAME + "\")",
					testCircuitBoard(INVALID_8_FILENAME, INVALID_8, Result.InvalidFileFormat));
			printTest("CircuitBoard(\"" + INVALID_9_FILENAME + "\")",
					testCircuitBoard(INVALID_9_FILENAME, INVALID_9, Result.InvalidFileFormat));
			printTest("CircuitBoard(\"" + INVALID_10_FILENAME + "\")",
					testCircuitBoard(INVALID_10_FILENAME, INVALID_10, Result.InvalidFileFormat));
			printTest("CircuitBoard(\"" + INVALID_11_FILENAME + "\")",
					testCircuitBoard(INVALID_11_FILENAME, INVALID_11, Result.InvalidFileFormat));
			printTest("CircuitBoard(\"" + INVALID_12_FILENAME + "\")",
					testCircuitBoard(INVALID_12_FILENAME, INVALID_12, Result.InvalidFileFormat));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE CIRCUITBOARD TESTS\n");
			e.printStackTrace();
		}

		System.out.println("************************************");
		System.out.println("CircuitTracer Valid Input File Tests");
		System.out.println("************************************\n");
		try {
			printTest("java CircuitTracer -s -c " + VALID_1_FILENAME,
					testCircuitTracerValidFileCmdLine(STACK, CONSOLE, VALID_1_FILENAME, VALID_1_SOLUTIONS));
			printTest("java CircuitTracer -q -c " + VALID_1_FILENAME,
					testCircuitTracerValidFileCmdLine(QUEUE, CONSOLE, VALID_1_FILENAME, VALID_1_SOLUTIONS));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + VALID_1_FILENAME + "\"})",
					testCircuitTracerValidFile(STACK, CONSOLE, VALID_1_FILENAME, VALID_1_SOLUTIONS));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + VALID_1_FILENAME + "\"})",
					testCircuitTracerValidFile(QUEUE, CONSOLE, VALID_1_FILENAME, VALID_1_SOLUTIONS));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + VALID_2_FILENAME + "\"})",
					testCircuitTracerValidFile(STACK, CONSOLE, VALID_2_FILENAME, VALID_2_SOLUTIONS));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + VALID_2_FILENAME + "\"})",
					testCircuitTracerValidFile(QUEUE, CONSOLE, VALID_2_FILENAME, VALID_2_SOLUTIONS));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + VALID_3_FILENAME + "\"})",
					testCircuitTracerValidFile(STACK, CONSOLE, VALID_3_FILENAME, VALID_3_SOLUTIONS));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + VALID_3_FILENAME + "\"})",
					testCircuitTracerValidFile(QUEUE, CONSOLE, VALID_3_FILENAME, VALID_3_SOLUTIONS));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + VALID_4_FILENAME + "\"})",
					testCircuitTracerValidFile(STACK, CONSOLE, VALID_4_FILENAME, VALID_4_SOLUTIONS));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + VALID_4_FILENAME + "\"})",
					testCircuitTracerValidFile(QUEUE, CONSOLE, VALID_4_FILENAME, VALID_4_SOLUTIONS));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + VALID_5_FILENAME + "\"})",
					testCircuitTracerValidFile(STACK, CONSOLE, VALID_5_FILENAME, VALID_5_SOLUTIONS));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + VALID_5_FILENAME + "\"})",
					testCircuitTracerValidFile(QUEUE, CONSOLE, VALID_5_FILENAME, VALID_5_SOLUTIONS));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + VALID_6_FILENAME + "\"})",
					testCircuitTracerValidFile(STACK, CONSOLE, VALID_6_FILENAME, VALID_6_SOLUTIONS));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + VALID_6_FILENAME + "\"})",
					testCircuitTracerValidFile(QUEUE, CONSOLE, VALID_6_FILENAME, VALID_6_SOLUTIONS));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + VALID_7_FILENAME + "\"})",
					testCircuitTracerValidFile(STACK, CONSOLE, VALID_7_FILENAME, VALID_7_SOLUTIONS));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + VALID_7_FILENAME + "\"})",
					testCircuitTracerValidFile(QUEUE, CONSOLE, VALID_7_FILENAME, VALID_7_SOLUTIONS));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + VALID_8_FILENAME + "\"})",
					testCircuitTracerValidFile(STACK, CONSOLE, VALID_8_FILENAME, VALID_8_SOLUTIONS));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + VALID_8_FILENAME + "\"})",
					testCircuitTracerValidFile(QUEUE, CONSOLE, VALID_8_FILENAME, VALID_8_SOLUTIONS));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + VALID_9_FILENAME + "\"})",
					testCircuitTracerValidFile(STACK, CONSOLE, VALID_9_FILENAME, VALID_9_SOLUTIONS));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + VALID_9_FILENAME + "\"})",
					testCircuitTracerValidFile(QUEUE, CONSOLE, VALID_9_FILENAME, VALID_9_SOLUTIONS));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + VALID_10_FILENAME + "\"})",
					testCircuitTracerValidFile(STACK, CONSOLE, VALID_10_FILENAME, VALID_10_SOLUTIONS));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + VALID_10_FILENAME + "\"})",
					testCircuitTracerValidFile(QUEUE, CONSOLE, VALID_10_FILENAME, VALID_10_SOLUTIONS));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE CIRCUITTRACER TESTS ON VALID INPUT FILES\n");
			e.printStackTrace();
		}

		System.out.println("**************************************");
		System.out.println("CircuitTracer Invalid Input File Tests");
		System.out.println("**************************************\n");
		try {
			// invalid file formats
			printTest("java CircuitTracer -s -c " + INVALID_1_FILENAME,
					testCircuitTracerInvalidFileCmdLine(STACK, CONSOLE, INVALID_1_FILENAME));
			printTest("java CircuitTracer -q -c " + INVALID_1_FILENAME,
					testCircuitTracerInvalidFileCmdLine(QUEUE, CONSOLE, INVALID_1_FILENAME));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + INVALID_1_FILENAME + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, INVALID_1_FILENAME));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + INVALID_1_FILENAME + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, INVALID_1_FILENAME));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + INVALID_2_FILENAME + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, INVALID_2_FILENAME));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + INVALID_2_FILENAME + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, INVALID_2_FILENAME));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + INVALID_3_FILENAME + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, INVALID_3_FILENAME));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + INVALID_3_FILENAME + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, INVALID_3_FILENAME));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + INVALID_4_FILENAME + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, INVALID_4_FILENAME));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + INVALID_4_FILENAME + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, INVALID_4_FILENAME));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + INVALID_5_FILENAME + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, INVALID_5_FILENAME));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + INVALID_5_FILENAME + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, INVALID_5_FILENAME));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + INVALID_6_FILENAME + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, INVALID_6_FILENAME));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + INVALID_6_FILENAME + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, INVALID_6_FILENAME));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + INVALID_7_FILENAME + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, INVALID_7_FILENAME));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + INVALID_7_FILENAME + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, INVALID_7_FILENAME));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + INVALID_8_FILENAME + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, INVALID_8_FILENAME));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + INVALID_8_FILENAME + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, INVALID_8_FILENAME));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + INVALID_9_FILENAME + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, INVALID_9_FILENAME));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + INVALID_9_FILENAME + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, INVALID_9_FILENAME));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + INVALID_10_FILENAME + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, INVALID_10_FILENAME));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + INVALID_10_FILENAME + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, INVALID_10_FILENAME));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + INVALID_11_FILENAME + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, INVALID_11_FILENAME));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + INVALID_11_FILENAME + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, INVALID_11_FILENAME));
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + INVALID_12_FILENAME + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, INVALID_12_FILENAME));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + INVALID_12_FILENAME + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, INVALID_12_FILENAME));
			// invalid file name
			printTest("CircuitTracer({\"-s\", \"-c\" \"" + NO_SUCH + "\"})",
					testCircuitTracerInvalidFile(STACK, CONSOLE, NO_SUCH));
			printTest("CircuitTracer({\"-q\", \"-c\" \"" + NO_SUCH + "\"})",
					testCircuitTracerInvalidFile(QUEUE, CONSOLE, NO_SUCH));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE CIRCUITTRACER TESTS ON INVALID INPUT FILES\n");
			e.printStackTrace();
		}

		// CircuitTracer Invalid Command Line Argument Tests
		// should result in a usage message and clean exit,
		// but without enforcing specific output, will require
		// manual review of usage message output by reader
		System.out.println("****************************************");
		System.out.println("CircuitTracer Invalid Command Line Tests");
		System.out.println("****************************************\n");
		try {
			printTest("java CircuitTracer -z -c " + VALID_1_FILENAME,
					testCircuitTracerArgs(INVALID_OPTION, CONSOLE, VALID_1_FILENAME));
			printTest("java CircuitTracer -s -z " + VALID_1_FILENAME,
					testCircuitTracerArgs(STACK, INVALID_OPTION, VALID_1_FILENAME));
			printTest("java CircuitTracer -q -z " + VALID_1_FILENAME,
					testCircuitTracerArgs(QUEUE, INVALID_OPTION, VALID_1_FILENAME));
			printTest("java CircuitTracer -s " + VALID_1_FILENAME, 
					testCircuitTracerArgs(STACK, VALID_1_FILENAME));
			printTest("java CircuitTracer -q " + VALID_1_FILENAME, 
					testCircuitTracerArgs(QUEUE, VALID_1_FILENAME));
			printTest("java CircuitTracer -s -c -z " + VALID_1_FILENAME,
					testCircuitTracerArgs(STACK, CONSOLE, INVALID_OPTION, VALID_1_FILENAME));
			printTest("java CircuitTracer -q -c -z " + VALID_1_FILENAME,
					testCircuitTracerArgs(QUEUE, CONSOLE, INVALID_OPTION, VALID_1_FILENAME));
			printTest("java CircuitTracer -s -c", 
					testCircuitTracerArgs(STACK, CONSOLE));
			printTest("java CircuitTracer -q -c", 
					testCircuitTracerArgs(QUEUE, CONSOLE));
			printTest("java CircuitTracer", 
					testCircuitTracerArgs());
			printTest("java CircuitTracer " + VALID_1_FILENAME, 
					testCircuitTracerArgs(VALID_1_FILENAME));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE CIRCUITTRACER TESTS WITH INVALID COMMAND LINE ARGUMENTS\n");
			e.printStackTrace();
		}

		// CircuitTracer GUI Option Test
		// - either launches GUI or exits with unsupported option message
		System.out.println("*******************************");
		System.out.println("CircuitTracer GUI Option Tests");
		System.out.println("*******************************\n");
		try {
			printTest("java CircuitTracer -s -g " + VALID_1_FILENAME, testCircuitTracerGui(STACK, VALID_1_FILENAME));
			printTest("java CircuitTracer -q -g " + VALID_1_FILENAME, testCircuitTracerGui(QUEUE, VALID_1_FILENAME));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE CIRCUITTRACER TESTS WITH GUI OUTPUT SPECIFIED\n");
			e.printStackTrace();
		}

		// report final verdict
		printFinalSummary();
	}

	//////////////////////////
	// XXX CircuitBoard Test
	//////////////////////////

	/**
	 * Confirm expected result of constructing new CircuitBoard from input file
	 * 
	 * @param fileName       input file
	 * @param contents       expected board contents for valid files
	 * @param expectedResult expected test result
	 * @return true if result matches expectedResult
	 */
	private boolean testCircuitBoard(String fileName, char[][] contents, Result expectedResult) {
		totalTests += 1;
		System.out.printf("Testing CircuitBoard(\"%s\")\n", fileName);
		if (expectedResult == Result.FileNotFound) {
			System.out.println("Expecting FileNotFoundException.");
		} else if (expectedResult == Result.InvalidFileFormat) {
			System.out.println("Expecting InvalidFileFormatException.");
		}
		Result result;
		try {
			CircuitBoard board = new CircuitBoard(fileName);
			result = Result.MatchingContents;
			if (contents.length != board.numRows() || contents[0].length != board.numCols()) {
				result = Result.Fail;
			} else {
				for (int row = 0; row < contents.length; row++) {
					for (int col = 0; col < contents[row].length; col++) {
						if (contents[row][col] != board.charAt(row, col)) {
							result = Result.Fail;
						}
					}
				}
			}
			if (result == Result.Fail) {
				System.out.println("CircuitBoard contents do not match expected values");
			} else {
				System.out.println("CircuitBoard contents match expected values.");
			}
		} catch (InvalidFileFormatException e) {
			System.out.println(e.toString());
			result = Result.InvalidFileFormat;
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
			result = Result.FileNotFound;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	//////////////////////////
	// XXX CircuitTracer Tests
	//////////////////////////

	/**
	 * Confirm CircuitTracer constructor reports messages and exits cleanly when
	 * given an invalid input file.
	 * In case you're curious, the code below does several cool things that
	 * might not make sense at first glance.
	 * In order to capture the console output from CircuitTracer() for analysis,
	 * I'm redirecting the standard System output and error streams that are 
	 * usually mapped to the console to a PrintStream connected to a file, 
	 * before calling CircuitTracer(), then reattaching them to the console 
	 * after the constructor has completed (or been killed).
	 * Being able to kill another program after some reasonable time has passed
	 * is also necessary, because it's possible that some people's CircuitTracer
	 * might go into an endless loop. To accomplish this, I'm running the
	 * CircuitTracer in a separate Thread via an Executor (a Thread manager)
	 * that allows a configurable time for the Thread to close itself before
	 * pulling the plug. 
	 * 
	 * @param storage        stack or queue
	 * @param outputTarget   console or gui
	 * @param fileName       input file
	 * @return true if test passes, else false
	 */
	private boolean testCircuitTracerInvalidFile(String storage, String outputTarget, String fileName) {
		totalTests += 1;
		Result result;
		System.out.printf("Testing CircuitTracer({\"%s\" \"%s\" \"%s\"})\n", storage, outputTarget, fileName);
		System.out.printf("Expecting a report on problem with %s and clean program exit.\n", fileName);
		// remember console output streams for stdout and stderr
		PrintStream consoleOut = System.out;
		PrintStream consoleErr = System.err;
		try {
			// redirect stdout and stderr to an output file
			File outfile = new File(fileName + ".out");
			PrintStream fileOut = new PrintStream(outfile);
			System.setOut(fileOut);
			System.setErr(fileOut);
			// run CircuitTracer with the specified storage argument (-s or -q), output
			// target argument (-c or -g), and input file argument
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.submit(() -> {
				String[] args = { storage, outputTarget, fileName };
				new CircuitTracer(args);
			});
			try {
				executor.shutdown();
				executor.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				// reset console stdout and stderr
				System.setOut(consoleOut);
				System.setErr(consoleErr);
				System.out.println("CircuitTracer() has exceeded reasonable completion time");
			} finally {
				// reset console stdout and stderr
				System.setOut(consoleOut);
				System.setErr(consoleErr);
				if (!executor.isTerminated()) {
					System.out.println("cancelling unfinished CircuitTracer()");
				}
				executor.shutdownNow();
				// System.out.println("CircuitTracer() shutdown finished");
			}
			result = Result.NoException;
			// print output file for review by reader
			boolean includesInvalidFileFormatException = false;
			boolean includesFileNotFoundException = false;
			String line;
			int lineCount = 0;
			try {
				Scanner scan = new Scanner(outfile);
				System.out.println("***begin output***");
				while (scan.hasNextLine()) {
					line = scan.nextLine();
					System.out.println(line);
					if (line.contains("InvalidFileFormatException")) {
						includesInvalidFileFormatException = true;
					} else if (line.contains("FileNotFoundException")) {
						includesFileNotFoundException = true;
					}
					lineCount++;
				}
				System.out.println("***end output***");
				scan.close();
				if (!includesInvalidFileFormatException && !includesFileNotFoundException) {
					System.out.println("Output missing expected exception name.");
					result = Result.InvalidOutput;
				}
				if (lineCount > 3) { // arbitrarily allowing for a couple lines, but not stack trace
					System.out.println("Output exceeds expected brief descriptive exception message.");
					result = Result.InvalidOutput;
				}
			} catch (FileNotFoundException e) {
				System.out.println("Unexpected FileNotFoundException");
				result = Result.FileNotFound;
			}
		} catch (InvalidFileFormatException e) {
			result = Result.InvalidFileFormat;
			System.out.println("Unhandled InvalidFileFormatException.");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			result = Result.UnexpectedException;
			System.out.println("Unexpected exception crashed the program.");
			System.out.println("Program is expected to exit cleanly after reporting problems.");
			e.printStackTrace(System.out);
		}
		return result == Result.NoException;
	}

	/**
	 * Confirm CircuitTracer produces correct results in the expected output format
	 * when given a valid input file.
	 * In case you're curious, the code below does several cool things that
	 * might not make sense at first glance.
	 * In order to capture the console output from CircuitTracer() for analysis,
	 * I'm redirecting the standard System output and error streams that are 
	 * usually mapped to the console to a PrintStream connected to a file, 
	 * before calling CircuitTracer(), then reattaching them to the console 
	 * after the constructor has completed (or been killed).
	 * Being able to kill another program after some reasonable time has passed
	 * is also necessary, because it's possible that some people's CircuitTracer
	 * might go into an endless loop. To accomplish this, I'm running the
	 * CircuitTracer in a separate Thread via an Executor (a Thread manager)
	 * that allows a configurable time for the Thread to close itself before
	 * pulling the plug. 
	 * 
	 * @param storage        stack or queue
	 * @param outputTarget   console or gui
	 * @param fileName       input file
	 * @param solutions      expected solutions
	 * @param expectedResult ValidOutput
	 * @return true if test passes, else false
	 */
	private boolean testCircuitTracerValidFile(String storage, String outputTarget, String fileName,
			char[][][] solutions) {
		totalTests += 1;
		Result result;
		System.out.printf("Testing CircuitTracer({\"%s\" \"%s\" \"%s\"})\n", storage, outputTarget, fileName);
		// remember console output streams for stdout and stderr
		PrintStream consoleOut = System.out;
		PrintStream consoleErr = System.err;
		try {
			// redirect stdout and stderr to an output file
			File outfile = new File(fileName + ".out");
			PrintStream fileOut = new PrintStream(outfile);
			System.setOut(fileOut);
			System.setErr(fileOut);
			// run CircuitTracer with the specified storage argument (-s or -q), output
			// target argument (-c or -g), and input file argument
			ExecutorService executor = Executors.newSingleThreadExecutor();
			executor.submit(() -> {
				String[] args = { storage, outputTarget, fileName };
				new CircuitTracer(args);
			});
			try {
				executor.shutdown();
				executor.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				// reset console stdout and stderr
				System.setOut(consoleOut);
				System.setErr(consoleErr);
				System.out.println("CircuitTracer() has exceeded reasonable completion time");
			} finally {
				// reset console stdout and stderr
				System.setOut(consoleOut);
				System.setErr(consoleErr);
				if (!executor.isTerminated()) {
					System.out.println("cancelling unfinished CircuitTracer()");
				}
				executor.shutdownNow();
				// System.out.println("CircuitTracer() shutdown finished");
			}
			// read in output file for comparison with expected solutions
			int expectedRows = (solutions.length > 0 ? solutions[0].length : 0);
			int expectedCols = (expectedRows > 0 ? solutions[0][0].length : 0);
			char[][][] outputSolutions = readOutFile(outfile, solutions.length, expectedRows, expectedCols);
			// find all known solutions in outputSolutions
			result = Result.ValidOutput;
			for (int solution = 0; solution < solutions.length; solution++) {
				if (!findSolution(solutions[solution], outputSolutions)) {
					result = Result.InvalidOutput;
				}
			}
			if (result == Result.ValidOutput) {
				System.out.println("Output matches expected content and format.");
			} else {
				System.out.println("Output does NOT match expected content and format.");
			}
		} catch (InvalidOutputException e) {
			result = Result.InvalidOutput;
			System.out.println("Output does NOT match expected content and format.");
		} catch (Exception e) {
			result = Result.UnexpectedException;
			e.printStackTrace(System.out);
		} finally {
			// reset console stdout and stderr
			System.setOut(consoleOut);
			System.setErr(consoleErr);
		}
		return result == Result.ValidOutput;
	}

	/**
	 * Confirm CircuitTracer reports messages and exits cleanly when given an
	 * invalid input file.
	 * In case you're curious, the code below does several cool things that
	 * might not make sense at first glance.
	 * In order to run another program as if it is being run from the command
	 * line with command line arguments, I'm using a ProcessBuilder that
	 * constructs a command line Process and then executes it. The ProcessBuilder
	 * has functionality for specifying where standard output and error streams
	 * should go, allowing me to capture all console output from CircuitTracer
	 * for analysis. The Process then allows me to specify the maximum time it
	 * is allowed to run before it will be forced to exit. That way a runaway 
	 * CircuitTracer (most likely an endless loop) will not prevent this test
	 * class from continuing to the next test.
	 * 
	 * @param storage        stack or queue
	 * @param outputTarget   console or gui
	 * @param fileName       input file
	 * @return true if test passes, else false
	 */
	private boolean testCircuitTracerInvalidFileCmdLine(String storage, String outputTarget, String fileName) {
		totalTests += 1;
		Result result;
		System.out.printf("Testing java CircuitTracer %s %s %s\n", storage, outputTarget, fileName);
		System.out.printf("Expecting a report on problem with %s and clean program exit.\n", fileName);
		try {
			// run CircuitTracer with the specified storage argument (-s or -q), output
			// target argument (-c or -g), and input file argument
			ProcessBuilder ctProcessBuilder = new ProcessBuilder("java", "CircuitTracer", storage, outputTarget,
					fileName);
			File outfile = new File(fileName + ".out");
			ctProcessBuilder.redirectErrorStream(true);
			ctProcessBuilder.redirectOutput(outfile);
			Process ctProcess = ctProcessBuilder.start();
			ctProcess.waitFor(TIMEOUT, TimeUnit.SECONDS);
			result = Result.NoException;
			// print output file for review by reader
			boolean includesInvalidFileFormatException = false;
			boolean includesFileNotFoundException = false;
			String line;
			int lineCount = 0;
			try {
				Scanner scan = new Scanner(outfile);
				System.out.println("***begin output***");
				while (scan.hasNextLine()) {
					line = scan.nextLine();
					System.out.println(line);
					if (line.contains("InvalidFileFormatException")) {
						includesInvalidFileFormatException = true;
					} else if (line.contains("FileNotFoundException")) {
						includesFileNotFoundException = true;
					}
					lineCount++;
				}
				System.out.println("***end output***");
				scan.close();
				if (!includesInvalidFileFormatException && !includesFileNotFoundException) {
					System.out.println("Output missing expected exception name.");
					result = Result.InvalidOutput;
				}
				if (lineCount > 3) { // arbitrarily allowing for a couple lines, but not stack trace
					System.out.println("Output exceeds expected brief descriptive exception message.");
					result = Result.InvalidOutput;
				}
			} catch (FileNotFoundException e) {
				System.out.println("Unexpected FileNotFoundException");
				result = Result.FileNotFound;
			}
		} catch (InvalidFileFormatException e) {
			result = Result.InvalidFileFormat;
			System.out.println("Unhandled InvalidFileFormatException.");
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			result = Result.UnexpectedException;
			System.out.println("Unable to complete CircuitTracer process in reasonable time.");
		} catch (Exception e) {
			result = Result.UnexpectedException;
			System.out.println("Unexpected exception crashed the program.");
			System.out.println("Program is expected to exit cleanly after reporting problems.");
			e.printStackTrace(System.out);
		}
		return result == Result.NoException;
	}

	/**
	 * Confirm CircuitTracer produces correct results in the expected output format
	 * when given a valid input file.
	 * In case you're curious, the code below does several cool things that
	 * might not make sense at first glance.
	 * In order to run another program as if it is being run from the command
	 * line with command line arguments, I'm using a ProcessBuilder that
	 * constructs a command line Process and then executes it. The ProcessBuilder
	 * has functionality for specifying where standard output and error streams
	 * should go, allowing me to capture all console output from CircuitTracer
	 * for analysis. The Process then allows me to specify the maximum time it
	 * is allowed to run before it will be forced to exit. That way a runaway 
	 * CircuitTracer (most likely an endless loop) will not prevent this test
	 * class from continuing to the next test.
	 * 
	 * @param storage        stack or queue
	 * @param outputTarget   console or gui
	 * @param fileName       input file
	 * @param solutions      expected solutions
	 * @param expectedResult ValidOutput
	 * @return true if test passes, else false
	 */
	private boolean testCircuitTracerValidFileCmdLine(String storage, String outputTarget, String fileName,
			char[][][] solutions) {
		totalTests += 1;
		Result result;
		System.out.printf("Testing java CircuitTracer %s %s %s\n", storage, outputTarget, fileName);
		try {
			// run CircuitTracer with the specified storage argument (-s or -q), output
			// target argument (-c or -g), and input file argument
			ProcessBuilder ctProcessBuilder = new ProcessBuilder("java", "CircuitTracer", storage, outputTarget,
					fileName);
			File outfile = new File(fileName + ".out");
			ctProcessBuilder.redirectErrorStream(true);
			ctProcessBuilder.redirectOutput(outfile);
			Process ctProcess = ctProcessBuilder.start();
			ctProcess.waitFor(TIMEOUT, TimeUnit.SECONDS);
			// read in output file for comparison with expected solutions
			int expectedRows = (solutions.length > 0 ? solutions[0].length : 0);
			int expectedCols = (expectedRows > 0 ? solutions[0][0].length : 0);
			char[][][] outputSolutions = readOutFile(outfile, solutions.length, expectedRows, expectedCols);
			// find all known solutions in outputSolutions
			result = Result.ValidOutput;
			for (int solution = 0; solution < solutions.length; solution++) {
				if (!findSolution(solutions[solution], outputSolutions)) {
					result = Result.InvalidOutput;
				}
			}
			if (result == Result.ValidOutput) {
				System.out.println("Output matches expected content and format.");
			} else {
				System.out.println("Output does NOT match expected content and format.");
			}
		} catch (InterruptedException e) {
			result = Result.UnexpectedException;
			System.out.println("Unable to complete CircuitTracer process in reasonable time.");
		} catch (InvalidOutputException e) {
			result = Result.InvalidOutput;
			System.out.println("Output does NOT match expected content and format.");
		} catch (Exception e) {
			result = Result.UnexpectedException;
			e.printStackTrace(System.out);
		}
		return result == Result.ValidOutput;
	}

	/**
	 * Read output into char arrays for comparison with known solutions
	 * 
	 * @param file
	 * @return solutions array
	 * @throws FileNotFoundException
	 * @throws InvalidOutputException
	 */
	private char[][][] readOutFile(File file, int expectedSolutions, int expectedRows, int expectedCols)
			throws FileNotFoundException, InvalidOutputException {
		Scanner fileScan = new Scanner(file);
		char[][][] reportedSolutions = new char[expectedSolutions][expectedRows][expectedCols];
		try {
			int solutions = 0;
			int row = 0;
			while (fileScan.hasNextLine()) {
				String line = fileScan.nextLine().trim();
				if (line.length() == 0) { // blank line, separates solutions
					solutions++;
					row = 0;
				} else if (solutions > expectedSolutions) { // extra content
					throw new InvalidOutputException();
				} else { // not a blank line
					Scanner lineScan = new Scanner(line);
					for (int col = 0; col < expectedCols; col++) {
						String token = lineScan.next();
						if (token.length() > 1) {
							System.out.println("Invalid output: " + token);
							lineScan.close();
							throw new InvalidOutputException();
						}
						reportedSolutions[solutions][row][col] = token.charAt(0);
					}
					if (lineScan.hasNext()) {
						lineScan.close();
						throw new InvalidOutputException();
					}
					row++;
					lineScan.close();
				}
			}
		} catch (Exception e) {
			throw new InvalidOutputException();
		} finally {
			fileScan.close();
		}
		return reportedSolutions;
	}

	/**
	 * Search for given solution in reportedSolutions
	 * 
	 * @param solution
	 * @param reportedSolutions
	 * @return true if solution is found in reportedSolutions
	 */
	private boolean findSolution(char[][] solution, char[][][] reportedSolutions) {
		for (int candidate = 0; candidate < reportedSolutions.length; candidate++) {
			boolean found = true;
			for (int row = 0; row < solution.length; row++) {
				for (int col = 0; col < solution[row].length; col++) {
					if (solution[row][col] != reportedSolutions[candidate][row][col]) {
						found = false;
					}
				}
			}
			if (found) {
				return true;
			}
		}
		return false;
	}

	//////////////////////////////////////////////
	// XXX CircuitTracer Invalid Command Line Test
	//////////////////////////////////////////////

	/**
	 * Run invalid set of command line arguments with CircuitTracer and confirm
	 * program exits cleanly.
	 * 
	 * @param args command line args to use with CircuitTracer
	 * @return true if program exits cleanly
	 */
	private boolean testCircuitTracerArgs(String... args) {
		totalTests += 1;
		Result result;
		System.out.print("Testing java CircuitTracer ");
		for (String arg : args) {
			System.out.print(arg + " ");
		}
		System.out.println();
		System.out.println("Expecting a usage message and clean exit with no other output.");
		try {
			// run CircuitTracer with the specified (invalid) combination of
			// arguments
			String[] processArgs = new String[args.length + 2];
			processArgs[0] = "java";
			processArgs[1] = "CircuitTracer";
			for (int i = 0; i < args.length; i++) {
				processArgs[i + 2] = args[i];
			}
			ProcessBuilder ctProcessBuilder = new ProcessBuilder(processArgs);
			File outfile = new File("cmdln.out");
			ctProcessBuilder.redirectErrorStream(true);
			ctProcessBuilder.redirectOutput(outfile);
			Process ctProcess = ctProcessBuilder.start();
			ctProcess.waitFor(TIMEOUT, TimeUnit.SECONDS);
			result = Result.NoException;
			// print output file for review by reader
			try {
				String line;
				boolean hasStackArg = false;
				boolean hasQueueArg = false;
				boolean hasConsoleArg = false;
				boolean hasGUIArg = false;
				boolean hasCircuitTracer = false;
				boolean hasStackDesc = false;
				boolean hasQueueDesc = false;
				boolean hasConsoleDesc = false;
				boolean hasGUIDesc = false;
				boolean hasException = false;
				Scanner scan = new Scanner(outfile);
				System.out.println("***begin output***");
				while (scan.hasNextLine()) {
					line = scan.nextLine();
					System.out.println(line);
					if (line.contains("-s"))
						hasStackArg = true;
					if (line.contains("-q"))
						hasQueueArg = true;
					if (line.contains("-c"))
						hasConsoleArg = true;
					if (line.contains("-g"))
						hasGUIArg = true;
					if (line.contains("java CircuitTracer"))
						hasCircuitTracer = true;
					if (line.toLowerCase().contains("stack"))
						hasStackDesc = true;
					if (line.toLowerCase().contains("queue"))
						hasQueueDesc = true;
					if (line.toLowerCase().contains("console"))
						hasConsoleDesc = true;
					if (line.toLowerCase().contains("gui"))
						hasGUIDesc = true;
					if (line.contains("Exception"))
						hasException = true;
				}
				System.out.println("***end output***");
				scan.close();
				if (!hasCircuitTracer) {
					System.out.println("Missing 'java CircuitTracer' in usage message.");
					result = Result.InvalidOutput;
				}
				if (!hasStackArg || !hasQueueArg || !hasConsoleArg || !hasGUIArg) {
					System.out.println("Missing required cmd line args in usage message.");
					result = Result.InvalidOutput;
				}
				if (!hasStackDesc || !hasQueueDesc || !hasConsoleDesc || !hasGUIDesc) {
					System.out.println("Missing explanation of cmd line args in usage message.");
					result = Result.InvalidOutput;
				}
				if (hasException) {
					System.out.println("Inappropriate Exception following invalid cmd line args.");
					result = Result.InvalidOutput;
				}
			} catch (FileNotFoundException e) {
				System.out.println("Unexpected FileNotFoundException");
				result = Result.InvalidOutput;
			}
		} catch (Exception e) {
			result = Result.UnexpectedException;
			System.out.println("Program crashed with unhandled exception.");
			e.printStackTrace(System.out);
		}
		return result == Result.NoException;
	}

	//////////////////////////////
	// XXX CircuitTracer GUI Test
	//////////////////////////////

	/**
	 * Run CircuitTracer with GUI output specified. Program should open GUI or exit
	 * cleanly with unsupported option message.
	 * 
	 * @param storage
	 * @param fileName
	 * @return true if program exits cleanly
	 */
	private boolean testCircuitTracerGui(String storage, String fileName) {
		totalTests += 1;
		Result result;
		System.out.println("Testing java CircuitTracer " + storage + " -g " + fileName);
		System.out.println("Expecting an unsupported option message and clean exit with no other console output,");
		System.out.println("or for GUI to open and no console output.");
		try {
			// run CircuitTracer with the specified arguments
			ProcessBuilder ctProcessBuilder = new ProcessBuilder("java", "CircuitTracer", storage, GUI, fileName);
			File outfile = new File("gui.out");
			ctProcessBuilder.redirectErrorStream(true);
			ctProcessBuilder.redirectOutput(outfile);
			Process ctProcess = ctProcessBuilder.start();
			boolean ctProcessExited = ctProcess.waitFor(TIMEOUT, TimeUnit.SECONDS);
			if (!ctProcessExited) { // GUI is likely open and waiting
				ctProcess.destroy(); // end the process
			}
			result = Result.ValidOutput; // until proven guilty
			// print output file for review by reader
			try {
				Scanner scan = new Scanner(outfile);
				int lineCount = 0;
				System.out.println("***begin console output***");
				while (scan.hasNextLine()) {
					System.out.println(scan.nextLine());
					if (!ctProcessExited) { // GUI ran, so no console output allowed
						result = Result.InvalidOutput;
					}
					lineCount++;
				}
				System.out.println("***end console output***");
				scan.close();
				if (lineCount > 7) { // arbitrarily allowing for long usage message
					System.out.println("More output than expected to report GUI isn't available.");
					result = Result.InvalidOutput;
				}
				if (ctProcessExited && lineCount == 0) { // GUI did not run, but no console out
					System.out.println("Missing expected 'GUI not available' message.");
					result = Result.InvalidOutput;
				}
			} catch (FileNotFoundException e) {
				System.out.println("Unexpected FileNotFoundException.");
				result = Result.InvalidOutput;
			}
		} catch (Exception e) {
			result = Result.UnexpectedException;
			System.out.println("Program crashed with unhandled exception.");
			e.printStackTrace(System.out);
		}
		return result == Result.ValidOutput;
	}

	//////////////////////////////////////////////////////////
	// XXX Generate valid and invalid input files for testing
	//////////////////////////////////////////////////////////

	/**
	 * Generate all valid and invalid input files for testing
	 * 
	 * @throws FileNotFoundException
	 */
	private void makeFiles() throws FileNotFoundException {
		makeFile(VALID_0, VALID_0_FILENAME);
		makeFile(VALID_1, VALID_1_FILENAME);
		makeFile(VALID_2, VALID_2_FILENAME);
		makeFile(VALID_3, VALID_3_FILENAME);
		makeFile(VALID_4, VALID_4_FILENAME);
		makeFile(VALID_5, VALID_5_FILENAME);
		makeFile(VALID_6, VALID_6_FILENAME);
		makeFile(VALID_7, VALID_7_FILENAME);
		makeFile(VALID_8, VALID_8_FILENAME);
		makeFile(VALID_9, VALID_9_FILENAME);
		makeFile(VALID_10, VALID_10_FILENAME);
		makeFile(INVALID_1, INVALID_1_FILENAME);
		makeFile(INVALID_2, INVALID_2_FILENAME);
		makeFile(INVALID_3, INVALID_3_FILENAME);
		makeFile(INVALID_4, INVALID_4_FILENAME);
		makeFile(INVALID_5, INVALID_5_FILENAME);
		makeFile(INVALID_6, INVALID_6_FILENAME);
		makeFile(INVALID_7, INVALID_7_FILENAME);
		makeFile(INVALID_8, INVALID_8_FILENAME, INVALID_8_ROWCOL);
		makeFile(INVALID_9, INVALID_9_FILENAME, INVALID_9_ROWCOL);
		makeFile(INVALID_10, INVALID_10_FILENAME, INVALID_10_ROWCOL);
		makeFile(INVALID_11, INVALID_11_FILENAME, INVALID_11_ROWCOL);
		makeFile(INVALID_12, INVALID_12_FILENAME, INVALID_12_ROWCOL);
	}

	/**
	 * Generate an input file where row/col line matches contents
	 * 
	 * @throws FileNotFoundException
	 */
	private void makeFile(char[][] contents, String fileName) throws FileNotFoundException {
		Random rand = new Random();
		PrintStream outFile = new PrintStream(new File(fileName));
		outFile.println(contents.length + " " + contents[0].length);
		for (int row = 0; row < contents.length; row++) {
			for (int col = 0; col < contents[row].length; col++) {
				outFile.print(contents[row][col] + " " + (rand.nextBoolean() ? "" : " "));
			}
			outFile.println();
		}
		outFile.close();
	}

	/**
	 * Generate an input file where row/col line matches given rowCol String
	 * 
	 * @throws FileNotFoundException
	 */
	private void makeFile(char[][] contents, String fileName, String rowCol) throws FileNotFoundException {
		Random rand = new Random();
		PrintStream outFile = new PrintStream(new File(fileName));
		outFile.println(rowCol);
		for (int row = 0; row < contents.length; row++) {
			for (int col = 0; col < contents[row].length; col++) {
				outFile.print(contents[row][col] + " " + (rand.nextBoolean() ? "" : " "));
			}
			outFile.println();
		}
		outFile.close();
	}

	///////////////////////////////////////////////
	// XXX Valid Input File Contents and Solutions
	///////////////////////////////////////////////

	private static final String VALID_0_FILENAME = "valid0.dat";
	private static final char[][] VALID_0 = { { '1', '2' } };
	private static final char[][][] VALID_0_SOLUTIONS = {
			// none
	};

	private static final String VALID_1_FILENAME = "valid1.dat";
	private static final char[][] VALID_1 = { { 'X', 'O', '1', 'O', 'O', 'O' }, { 'X', 'X', 'X', 'O', 'O', 'O' },
			{ 'O', 'O', 'O', 'O', 'X', 'O' }, { 'O', '2', 'O', 'O', 'X', 'O' }, { 'O', 'X', 'O', 'O', 'O', 'O' } };
	private static final char[][][] VALID_1_SOLUTIONS = {
			{ { 'X', 'O', '1', 'T', 'O', 'O' }, { 'X', 'X', 'X', 'T', 'O', 'O' }, { 'O', 'T', 'T', 'T', 'X', 'O' },
					{ 'O', '2', 'O', 'O', 'X', 'O' }, { 'O', 'X', 'O', 'O', 'O', 'O' } },
			{ { 'X', 'O', '1', 'T', 'O', 'O' }, { 'X', 'X', 'X', 'T', 'O', 'O' }, { 'O', 'O', 'T', 'T', 'X', 'O' },
					{ 'O', '2', 'T', 'O', 'X', 'O' }, { 'O', 'X', 'O', 'O', 'O', 'O' } },
			{ { 'X', 'O', '1', 'T', 'O', 'O' }, { 'X', 'X', 'X', 'T', 'O', 'O' }, { 'O', 'O', 'O', 'T', 'X', 'O' },
					{ 'O', '2', 'T', 'T', 'X', 'O' }, { 'O', 'X', 'O', 'O', 'O', 'O' } } };
	private static final String VALID_2_FILENAME = "valid2.dat";
	private static final char[][] VALID_2 = { { '1', 'O', 'O' }, { 'O', 'X', 'O' }, { 'O', '2', 'O' } };
	private static final char[][][] VALID_2_SOLUTIONS = { { { '1', 'O', 'O' }, { 'T', 'X', 'O' }, { 'T', '2', 'O' } } };
	private static final String VALID_3_FILENAME = "valid3.dat";
	private static final char[][] VALID_3 = { { 'X', 'O', '2', 'O', 'O', 'O' }, { 'X', 'X', 'X', 'O', 'O', 'O' },
			{ 'O', 'O', 'O', 'O', 'X', 'O' }, { 'O', '1', 'O', 'O', 'X', 'O' }, { 'O', 'X', 'O', 'O', 'O', 'O' } };
	private static final char[][][] VALID_3_SOLUTIONS = {
			{ { 'X', 'O', '2', 'T', 'O', 'O' }, { 'X', 'X', 'X', 'T', 'O', 'O' }, { 'O', 'T', 'T', 'T', 'X', 'O' },
					{ 'O', '1', 'O', 'O', 'X', 'O' }, { 'O', 'X', 'O', 'O', 'O', 'O' } },
			{ { 'X', 'O', '2', 'T', 'O', 'O' }, { 'X', 'X', 'X', 'T', 'O', 'O' }, { 'O', 'O', 'T', 'T', 'X', 'O' },
					{ 'O', '1', 'T', 'O', 'X', 'O' }, { 'O', 'X', 'O', 'O', 'O', 'O' } },
			{ { 'X', 'O', '2', 'T', 'O', 'O' }, { 'X', 'X', 'X', 'T', 'O', 'O' }, { 'O', 'O', 'O', 'T', 'X', 'O' },
					{ 'O', '1', 'T', 'T', 'X', 'O' }, { 'O', 'X', 'O', 'O', 'O', 'O' } } };
	private static final String VALID_4_FILENAME = "valid4.dat";
	private static final char[][] VALID_4 = { { 'O', 'O', 'X', 'X', 'O', 'O', 'X', 'X', 'O', 'O' },
			{ 'O', '1', 'O', 'X', 'X', 'O', 'X', 'O', 'O', 'X' }, { 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'X', 'O' },
			{ 'X', 'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'O' }, { 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'O', 'O', 'O' },
			{ 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'O', 'X', 'X' }, { 'X', 'X', 'X', 'O', 'X', 'O', 'O', 'O', 'O', 'O' },
			{ 'X', 'O', 'O', 'O', 'O', 'O', 'O', '2', 'X', 'O' }, { 'O', 'O', 'X', 'X', 'O', 'X', 'O', 'X', 'O', 'O' },
			{ 'O', 'X', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O' } };
	private static final char[][][] VALID_4_SOLUTIONS = { { { 'O', 'O', 'X', 'X', 'O', 'O', 'X', 'X', 'O', 'O' },
			{ 'O', '1', 'O', 'X', 'X', 'O', 'X', 'O', 'O', 'X' }, { 'X', 'T', 'T', 'O', 'O', 'O', 'X', 'O', 'X', 'O' },
			{ 'X', 'X', 'T', 'X', 'X', 'O', 'O', 'O', 'X', 'O' }, { 'O', 'X', 'T', 'X', 'O', 'O', 'X', 'O', 'O', 'O' },
			{ 'O', 'O', 'T', 'T', 'O', 'X', 'O', 'O', 'X', 'X' }, { 'X', 'X', 'X', 'T', 'X', 'O', 'O', 'O', 'O', 'O' },
			{ 'X', 'O', 'O', 'T', 'T', 'T', 'T', '2', 'X', 'O' }, { 'O', 'O', 'X', 'X', 'O', 'X', 'O', 'X', 'O', 'O' },
			{ 'O', 'X', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O' } },
			{ { 'O', 'O', 'X', 'X', 'O', 'O', 'X', 'X', 'O', 'O' },
					{ 'O', '1', 'O', 'X', 'X', 'O', 'X', 'O', 'O', 'X' },
					{ 'X', 'T', 'T', 'T', 'T', 'T', 'X', 'O', 'X', 'O' },
					{ 'X', 'X', 'O', 'X', 'X', 'T', 'T', 'T', 'X', 'O' },
					{ 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'T', 'O', 'O' },
					{ 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'T', 'X', 'X' },
					{ 'X', 'X', 'X', 'O', 'X', 'O', 'O', 'T', 'O', 'O' },
					{ 'X', 'O', 'O', 'O', 'O', 'O', 'O', '2', 'X', 'O' },
					{ 'O', 'O', 'X', 'X', 'O', 'X', 'O', 'X', 'O', 'O' },
					{ 'O', 'X', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O' } },
			{ { 'O', 'O', 'X', 'X', 'O', 'O', 'X', 'X', 'O', 'O' },
					{ 'O', '1', 'T', 'X', 'X', 'O', 'X', 'O', 'O', 'X' },
					{ 'X', 'O', 'T', 'O', 'O', 'O', 'X', 'O', 'X', 'O' },
					{ 'X', 'X', 'T', 'X', 'X', 'O', 'O', 'O', 'X', 'O' },
					{ 'O', 'X', 'T', 'X', 'O', 'O', 'X', 'O', 'O', 'O' },
					{ 'O', 'O', 'T', 'T', 'O', 'X', 'O', 'O', 'X', 'X' },
					{ 'X', 'X', 'X', 'T', 'X', 'O', 'O', 'O', 'O', 'O' },
					{ 'X', 'O', 'O', 'T', 'T', 'T', 'T', '2', 'X', 'O' },
					{ 'O', 'O', 'X', 'X', 'O', 'X', 'O', 'X', 'O', 'O' },
					{ 'O', 'X', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O' } },
			{ { 'O', 'O', 'X', 'X', 'O', 'O', 'X', 'X', 'O', 'O' },
					{ 'O', '1', 'T', 'X', 'X', 'O', 'X', 'O', 'O', 'X' },
					{ 'X', 'O', 'T', 'T', 'T', 'T', 'X', 'O', 'X', 'O' },
					{ 'X', 'X', 'O', 'X', 'X', 'T', 'T', 'T', 'X', 'O' },
					{ 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'T', 'O', 'O' },
					{ 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'T', 'X', 'X' },
					{ 'X', 'X', 'X', 'O', 'X', 'O', 'O', 'T', 'O', 'O' },
					{ 'X', 'O', 'O', 'O', 'O', 'O', 'O', '2', 'X', 'O' },
					{ 'O', 'O', 'X', 'X', 'O', 'X', 'O', 'X', 'O', 'O' },
					{ 'O', 'X', 'O', 'O', 'O', 'X', 'O', 'O', 'O', 'O' } } };
	private static final String VALID_5_FILENAME = "valid5.dat";
	private static final char[][] VALID_5 = { { 'O', '1', '2' }, { 'O', 'O', 'O' }, { 'O', 'O', 'O' } };
	private static final char[][][] VALID_5_SOLUTIONS = { { { 'O', '1', '2' }, { 'O', 'T', 'T' }, { 'O', 'O', 'O' } } };
	private static final String VALID_6_FILENAME = "valid6.dat";
	private static final char[][] VALID_6 = { { 'O', '2', 'O' }, { 'O', '1', 'O' }, { 'O', 'O', 'O' } };
	private static final char[][][] VALID_6_SOLUTIONS = { { { 'T', '2', 'O' }, { 'T', '1', 'O' }, { 'O', 'O', 'O' } },
			{ { 'O', '2', 'T' }, { 'O', '1', 'T' }, { 'O', 'O', 'O' } } };
	private static final String VALID_7_FILENAME = "valid7.dat";
	private static final char[][] VALID_7 = { { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'O', 'O', 'O' },
			{ 'O', 'O', 'O', 'O', 'O' }, { 'O', 'O', 'O', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' } };
	private static final char[][][] VALID_7_SOLUTIONS = {
			{ { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'O', 'O', 'O' }, { 'O', 'T', 'O', 'O', 'O' },
					{ 'O', 'T', 'T', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' }, },
			{ { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'O', 'O', 'O' }, { 'O', 'T', 'T', 'O', 'O' },
					{ 'O', 'O', 'T', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' }, },
			{ { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'O', 'O', 'O' }, { 'O', 'T', 'T', 'T', 'O' },
					{ 'O', 'O', 'O', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' }, },
			{ { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'T', 'O', 'O' }, { 'O', 'O', 'T', 'O', 'O' },
					{ 'O', 'O', 'T', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' }, },
			{ { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'T', 'O', 'O' }, { 'O', 'O', 'T', 'T', 'O' },
					{ 'O', 'O', 'O', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' }, },
			{ { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'T', 'T', 'O' }, { 'O', 'O', 'O', 'T', 'O' },
					{ 'O', 'O', 'O', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' }, } };
	private static final String VALID_8_FILENAME = "valid8.dat";
	private static final char[][] VALID_8 = { { 'X', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'X' },
			{ 'O', '1', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
			{ 'X', 'O', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'O', 'X' },
			{ 'X', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'X' },
			{ 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X' },
			{ 'X', 'O', 'O', 'O', 'X', 'X', 'X', 'O', 'O', 'O', 'X' },
			{ 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X' },
			{ 'X', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'X' },
			{ 'X', 'O', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'O', 'X' },
			{ 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '2', 'O' },
			{ 'X', 'O', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'X' } };
	private static final char[][][] VALID_8_SOLUTIONS = {
			{ { 'X', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'X' },
					{ 'O', '1', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
					{ 'X', 'T', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'O', 'X' },
					{ 'X', 'T', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'X' },
					{ 'X', 'T', 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X' },
					{ 'X', 'T', 'O', 'O', 'X', 'X', 'X', 'O', 'O', 'O', 'X' },
					{ 'X', 'T', 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X' },
					{ 'X', 'T', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'X' },
					{ 'X', 'T', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'O', 'X' },
					{ 'O', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', '2', 'O' },
					{ 'X', 'O', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'X' } },
			{ { 'X', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'X' },
					{ 'O', '1', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
					{ 'X', 'T', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'O', 'X' },
					{ 'X', 'T', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'O', 'X' },
					{ 'X', 'T', 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X' },
					{ 'X', 'T', 'T', 'T', 'X', 'X', 'X', 'O', 'O', 'O', 'X' },
					{ 'X', 'O', 'X', 'T', 'X', 'O', 'X', 'O', 'X', 'O', 'X' },
					{ 'X', 'O', 'X', 'T', 'T', 'T', 'O', 'O', 'X', 'O', 'X' },
					{ 'X', 'O', 'X', 'X', 'X', 'T', 'X', 'X', 'X', 'O', 'X' },
					{ 'O', 'O', 'O', 'O', 'O', 'T', 'T', 'T', 'T', '2', 'O' },
					{ 'X', 'O', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'X' } },
			{ { 'X', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'X' },
					{ 'O', '1', 'T', 'T', 'T', 'T', 'O', 'O', 'O', 'O', 'O' },
					{ 'X', 'O', 'X', 'X', 'X', 'T', 'X', 'X', 'X', 'O', 'X' },
					{ 'X', 'O', 'X', 'O', 'O', 'T', 'T', 'T', 'X', 'O', 'X' },
					{ 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'T', 'X', 'O', 'X' },
					{ 'X', 'O', 'O', 'O', 'X', 'X', 'X', 'T', 'T', 'T', 'X' },
					{ 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'T', 'X' },
					{ 'X', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'T', 'X' },
					{ 'X', 'O', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'T', 'X' },
					{ 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '2', 'O' },
					{ 'X', 'O', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'X' } },
			{ { 'X', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'X' },
					{ 'O', '1', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'O' },
					{ 'X', 'O', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'T', 'X' },
					{ 'X', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'T', 'X' },
					{ 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'T', 'X' },
					{ 'X', 'O', 'O', 'O', 'X', 'X', 'X', 'O', 'O', 'T', 'X' },
					{ 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X', 'T', 'X' },
					{ 'X', 'O', 'X', 'O', 'O', 'O', 'O', 'O', 'X', 'T', 'X' },
					{ 'X', 'O', 'X', 'X', 'X', 'O', 'X', 'X', 'X', 'T', 'X' },
					{ 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', '2', 'O' },
					{ 'X', 'O', 'O', 'X', 'X', 'X', 'X', 'X', 'X', 'O', 'X' } } };
	private static final String VALID_9_FILENAME = "valid9.dat";
	private static final char[][] VALID_9 = { { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'O', 'O', 'O' },
			{ 'O', 'O', 'O', 'O', 'O' }, { 'O', 'O', 'O', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' } };
	private static final char[][][] VALID_9_SOLUTIONS = {
			{ { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'O', 'O', 'O' }, { 'O', 'T', 'O', 'O', 'O' },
					{ 'O', 'T', 'T', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' } },
			{ { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'O', 'O', 'O' }, { 'O', 'T', 'T', 'O', 'O' },
					{ 'O', 'O', 'T', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' } },
			{ { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'O', 'O', 'O' }, { 'O', 'T', 'T', 'T', 'O' },
					{ 'O', 'O', 'O', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' } },
			{ { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'T', 'O', 'O' }, { 'O', 'O', 'T', 'O', 'O' },
					{ 'O', 'O', 'T', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' } },
			{ { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'T', 'O', 'O' }, { 'O', 'O', 'T', 'T', 'O' },
					{ 'O', 'O', 'O', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' } },
			{ { 'O', 'O', 'O', 'O', 'O' }, { 'O', '1', 'T', 'T', 'O' }, { 'O', 'O', 'O', 'T', 'O' },
					{ 'O', 'O', 'O', '2', 'O' }, { 'O', 'O', 'O', 'O', 'O' } } };
	private static final String VALID_10_FILENAME = "valid10.dat";
	private static final char[][] VALID_10 = { { '1', 'O', 'X' }, { 'O', 'X', 'O' }, { 'X', 'O', '2' } };
	private static final char[][][] VALID_10_SOLUTIONS = {
			// none
	};

	//////////////////////////////////
	// XXX Invalid Input File Contents
	//////////////////////////////////

	private static final String INVALID_1_FILENAME = "invalid1.dat";
	private static final char[][] INVALID_1 = { // multiple '1's
			{ '1', 'O', 'O' }, { 'O', '1', 'O' }, { 'O', 'O', '2' } };
	private static final String INVALID_2_FILENAME = "invalid2.dat";
	private static final char[][] INVALID_2 = { // multiple '2's
			{ '1', 'O', 'O' }, { 'O', '2', 'O' }, { 'O', 'O', '2' } };
	private static final String INVALID_3_FILENAME = "invalid3.dat";
	private static final char[][] INVALID_3 = { // missing '2'
			{ 'O', '1', 'O' }, { 'O', 'X', 'O' }, { 'O', 'O', 'O' } };
	private static final String INVALID_4_FILENAME = "invalid4.dat";
	private static final char[][] INVALID_4 = { // missing '1'
			{ 'O', 'X', 'O' }, { 'X', 'O', '2' }, { 'O', 'O', 'O' } };
	private static final String INVALID_5_FILENAME = "invalid5.dat";
	private static final char[][] INVALID_5 = { // invalid char 'N'
			{ '1', 'O', 'X' }, { 'O', '2', 'O' }, { 'N', 'O', 'O' } };
	private static final String INVALID_6_FILENAME = "invalid6.dat";
	private static final char[][] INVALID_6 = { // extra value
			{ 'O', '1', 'O' }, { 'O', 'O', 'O' }, { 'O', '2', 'O', 'O' } };
	private static final String INVALID_7_FILENAME = "invalid7.dat";
	private static final char[][] INVALID_7 = { // missing value
			{ 'O', '1', 'O' }, { 'O', 'O', 'O' }, { 'O', '2' } };
	private static final String INVALID_8_FILENAME = "invalid8.dat";
	private static final String INVALID_8_ROWCOL = "4 5";
	private static final char[][] INVALID_8 = { // row/col reversed
			{ 'O', 'O', 'O', 'O' }, { 'O', '2', 'O', 'O' }, { 'O', 'O', 'O', 'O' }, { 'O', 'O', '1', 'O' },
			{ 'O', 'O', 'O', 'O' } };
	private static final String INVALID_9_FILENAME = "invalid9.dat";
	private static final String INVALID_9_ROWCOL = "3 three";
	private static final char[][] INVALID_9 = { // row/col line "3 three"
			{ 'O', '1', 'O' }, { 'O', 'O', 'O' }, { 'O', '2', 'O' } };
	private static final String INVALID_10_FILENAME = "invalid10.dat";
	private static final String INVALID_10_ROWCOL = "2 3";
	private static final char[][] INVALID_10 = { // extra row
			{ 'O', '1', 'O' }, { 'O', '2', 'O' }, { 'O', 'O', 'O' } };
	private static final String INVALID_11_FILENAME = "invalid11.dat";
	private static final String INVALID_11_ROWCOL = "3 3 0";
	private static final char[][] INVALID_11 = { // 1st board value on 1st line
			{ '1', 'O' }, { 'O', 'O', 'O' }, { 'O', '2', 'O' } };
	private static final String INVALID_12_FILENAME = "invalid12.dat";
	private static final String INVALID_12_ROWCOL = "3 3.0";
	private static final char[][] INVALID_12 = { // row/col double value
			{ 'O', '1', 'O' }, { 'O', 'O', 'O' }, { 'O', '2', 'O' } };

	/** Indicates invalid output format from CircuitTracer */
	private class InvalidOutputException extends IOException {
		private static final long serialVersionUID = 1L;

		public InvalidOutputException() {
			super();
		}
	}
}