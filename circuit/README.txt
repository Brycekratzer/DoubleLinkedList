****************
* Final Project / Circut
* 221-03
* 12/06/23
* Bryce Kratzer
**************** 

OVERVIEW:

The progam takes in a valid file in the same directory as the 
program that represents a circut, then finds all the possible moves 
in order to find the best possible routes. 

INCLUDED FILES:

 * CircuitTracer.java - source file
 * CircuitBoard.java - source file
 * CircuitTracerTester.txt - test file used to ensure proper functionality
 * InvalidFileFormatException.jave - source file
 * OccuipedPositionException.java - source file
 * Storage.java - source file
 * TraceState.java - source file
 * README - this file

COMPILING AND RUNNING:

 From the directory containing all source files, compile the
 driver class (and all dependencies) with the command:
 $ javac CircuitTracer.java

 Run the compiled class file with the command:
 $ java CircuitTracer [-s stack storage | -q queueu storage] [-c run program in console mode | -g GUI mode] [filename]

 Console output will give the results after the program finishes.

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

The 'CircuitBoard' class starts by creating a circuit board from a specified input file. It uses a Scanner to read the file, 
extracting essential information about the board's dimensions from the first row. If the format for initializing rows and 
columns is invalid, an InputMismatchException is triggered. Following this, the class initializes the board based on the extracted 
dimensions. It then reads each row of the file, ensuring the row and column counts match the provided values. It checks for valid 
characters and ensures no duplicates for starting and ending positions. If any inconsistency is detected, it throws an InvalidFileFormatException 
with a descriptive error message. Additionally, it checks if the number of rows matches the inputted value and if both starting and ending points 
were found. This part of the code ensures the proper construction of a valid circuit board and identifies potential issues with the input file. 
The design aims to ensure the file has the correct dimensions and layout.

The program is designed to find every path from a given circuit file, storing paths in either a stack or a queue. The 'Storage' 
class serves as a utility for accessing or adding data to/from a queue or stack. In stack configuration, the program explores one 
path until all possible routes are found from one of the initial steps, then repeats the process for the other initial step.
In queue configuration, the program records the next move from the first possible moves and evaluates the next move. 
This method systematically figures out each possible move one state at a time, providing flexibility in exploring various paths through the 
circuit board.

The 'TraceState' class acts as a snapshot during the exploration of a circuit board, keeping track of the current state and potential path. 
It uses 'CircuitBoard' and 'ArrayList<Point>' to record the path's progress. The class facilitates checking if specific board positions are open, 
determining the path length, and identifying proximity to completion. It also allows for obtaining a modified board displaying the path and creating copies of the path. 
'TraceState' simplifies understanding and exploring paths through a circuit board without using complex language.

The 'CircuitTracer' class assists in finding the shortest paths between start and end points on a circuit board, read from an input file. 
It uses either a stack or a queue to manage search states, and the output can be displayed on the console or a graphical user interface (GUI). 
To run the program, three arguments are needed: storage type (-s for stack or -q for queue), output type (-c for console or -g for GUI), 
and the input file name. The class sets up the circuit board based on these arguments and explores adjacent positions, creating new states accordingly. 
The algorithm uses a stack or a queue to manage states during the search until no more states are left. It keeps track of the best paths, 
considering path length and updating if a shorter path is found. The algorithm accounts for conditions like out-of-bounds positions and occupied positions, 
ensuring a systematic exploration of potential paths. The program outputs results to the console or indicates GUI functionality is planned for a future version.

The other two methods function as program-specific exceptions that get triggered if the input data doesn't match expectations or if there's an occupied position 
while traversing paths.

TESTING:


ChatGPT
While creating the program, Mason Vail's CircuitTracerTester class played a crucial role in testing. You can find this class as a text file in the directory. 
The class underwent testing with around 12 files containing errors and 12 files with the correct data. Its main job was to check if the program showed the 
right results when used with each file. These files were of different sizes and presented various situations that could cause the program to crash if not handled correctly. 
The test class ran a total of 86 tests and passed all of them. The program demonstrated its ability to handle files with incorrect data and react properly to different
 situations. The extensive testing with CircuitTracerTester ensures that the program works well and can handle various scenarios.

DISCUSSION:
 
The program's development overall was quite smooth. One of the more challenging parts was dealing with various scenarios that could lead to exceptions. 
The process involved going through each test in the tester class to identify different exception handling needs. Apart from that, the development proceeded
relatively smoothly and encountered only a few minor roadblocks.

ANALYSIS:

    i. 
    When using stack storage configuration, the first two possible steps are initated.
    From there, one path is explored until all possible routes are found from one of the first
    steps. After all paths are found from one of the first steps, the other first step is then 
    evalutaed until all paths are found. 

    When using queue storage configuration, the first possible moves are found. From there, out of 
    possible first moves one is picked and the next move from the first is recorded into storage. 
    Unlike the stack storage, the queue storage then returns to the other first move and evaluates 
    the next move. This method figures out each possible move one state at a time. 

    ii.
    No, each possible move is recorded regardless of the type of storage. 
    However the paths are discovered in different ways.

    iii.
    Depending on how short a path may be, the stack queue may find a solution faster, as it goes
    down one path at a time. However if there is a super optimal route that the stack queue doesn't 
    go down first, the queue storage may find it quicker as it goes through each branch at a time. 

    iv.
    Since the queue based storage goes doesn't go down one singular path, rather finds each
    move per branch, the queue based search will find the most optimal path first before it 
    finds any other path. However, if you find a solution with a stack based search it may or 
    may not be the most optimal. 

    v.
    Using a stack based search may use less memory as it goes down one path at a time, while
    a queue based search goes down every path one branch at a time before finding a solution. 
    This may result in more paths before finding a solution with a queue based search.

    vi.
    The Big-O runtime would be n as there is a while loop that executes n times, depending on
    how many paths there are. So, the n represents the length of all the paths in the maze. If the maze
    is larger it may take longer to execute. 




