//importing required packages
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Dijkstra {
	// initializing Global variables
	static int i = 0;
	static int lineNumber = 0;																	 // used to maintain number of routers
	static int source_router; 																	 // input source numbers
	static int matrixSize = 0; 																     // initializing matrix
	static Map<Integer, DijkstraFunctions> connectionTable = new HashMap<Integer, DijkstraFunctions>(); // using HashMap to map values
	static GraphFunctions[][] distN = new GraphFunctions[matrixSize][matrixSize]; 						// initializing GraphFunctions file
	static int checkingHandle; 																			// handle used mark notes flagged
	static int maxDistance = Integer.MAX_VALUE;															// maximum distance to replace if nodes cannot be reached

	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception {
		System.out.println("CS 542 Link State Routing Simulator, Choose an option:\n");
		int[][] inputMatrix = new int[1][1]; 														    // initializing matrix that receives inputs
		int[][] matrixCost = new int[1][1]; 															// initializing matrix with updated matrix
		int x = 0, y = 0, a = 0, b = 0, flag = 0; 														// declaring and initializing variables
		String delimiter = " "; 																		// Spiting input matrix using space
		int minDistance = 0;
		while (i == 0) {
			try{
				System.out.println("(1). Input Network Topology File");
				System.out.println("(2). Build a connection table");
				System.out.println("(3). Shortest path to the destination router");
				System.out.println("(4). Modify the Topology");
				System.out.println("(5). Exit");
				System.out.println("\nEnter the menu option");
				Scanner user_input = new Scanner(System.in);
				int menuOption = user_input.nextInt();
				if ((menuOption < 6) && (menuOption > 0))												//checks input within the range of menu
				{
					switch (menuOption) {
					case 1:
						System.out.println("\nEnter the file path");
						BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
						// reading file content
						String filename = br1.readLine();
						try {
							FileInputStream fstream = new FileInputStream(filename);
							DataInputStream in = new DataInputStream(fstream);
							BufferedReader br = new BufferedReader(new InputStreamReader(in));
							String strLine = null;
							// getting total number of routers
							while ((strLine = br.readLine()) != null)									//reading each line in matrix
							{
								lineNumber++;
							}
							in.close();
							// calculating total matrix size including router name
							matrixSize = lineNumber + 1;
							inputMatrix = new int[matrixSize][matrixSize];								//initializing matrix with total number of line with header
							matrixCost = new int[matrixSize][matrixSize];								//initializing matrix with max value with total number of line with header
							//adding routers names along the column
							for (y = 0; y <= lineNumber; y++) {
								{
									inputMatrix[x][y] = y;
								}
							}
							FileInputStream fstream1 = new FileInputStream(filename);
							DataInputStream in1 = new DataInputStream(fstream1);
							BufferedReader br2 = new BufferedReader(new InputStreamReader(in1));
							//storing values by each element in the array
							while ((strLine = br2.readLine()) != null)
							{
								x++;
								y = 0;
								String[] matrixArray = new String[lineNumber];				//initializing array
								matrixArray = strLine.split(delimiter);				 		// using delimiter to split values
								inputMatrix[x][y] = y;										//initializing router values in rows
								inputMatrix[x][y] = x;
								for (y = 1; y <= lineNumber; y++) {
									int d = y - 1;
									inputMatrix[x][y] = Integer.parseInt(matrixArray[d]); 	// storing the values to matrix initialized already by parsing to int
								}
							}
							in1.close();
							for (x = 0; x <= lineNumber; x++)								//checking each element in the matrix
							{
								for (y = 0; y <= lineNumber; y++)
								{
									if (inputMatrix[x][y] == -1)							// checks for -1
									{
										matrixCost[x][y] = maxDistance;						// replaces -1 with the max distance
									}
									else
									{
										matrixCost[x][y] = inputMatrix[x][y];				//all the values are stored in the new matrix
									}
								}
							}
							System.out.println("\nReview original topology matrix:\n");
							printMatrix(inputMatrix);										//printing matrix
							flag++;															// Incrementing flag so that it can be checked if file read or not
						}
						catch (FileNotFoundException e)										//catches if file is not found
						{
							System.out.println("\n === File not found !!, Please enter correct file location === ");
							continue;
						}
						break;																//breaks and prints main menu again

					case 2:
						if (flag != 0)														// checks if the matrix is already loaded or not
						{
							System.out.println("\nSelect a source router [1-"+ matrixSize + "]");
							Scanner user_input1 = new Scanner(System.in);
							int source_router = user_input1.nextInt();						//stores input source value
							if (source_router <= matrixSize && source_router > 0)			//checks if the source router is within range
							{
								GraphFunctions[] fN = new GraphFunctions[matrixSize];		//initializing graphFunctions java class
								for (int i = 1; i <= lineNumber; i++)						//marks all nodes as not visited
								{
									fN[i] = new GraphFunctions();
									fN[i].setVisited(1); 									// "1" if not visited
									fN[i].setRN(i);											//initialize node number
								}
								fN[source_router].setVisited(0);							//Initialize source NodeVisited Object
								//final distanceNode Array
								distN = new GraphFunctions[lineNumber + 1][lineNumber + 1]; //distN

								for (int p = 0; p <= lineNumber; p++)						//checks all elements in the GraphFunctions
								{
									for (int q = 0; q <= lineNumber; q++)
									{
										distN[p][q] = new GraphFunctions();
									}
								}
								shortestPATH.initNodes(lineNumber, source_router,distN); 	// initializing all nodes before visiting
								int nextSourcenumber = source_router;						//setting for temp source router value

								// ALGORITHM IMPLEMENTATION
								for (b = 1; b <= lineNumber; b++)											//checks for each element
								{
									a = 0;
									GraphFunctions.initRouters(b, a, minDistance,nextSourcenumber, distN);  //Initializing routers with dist value and node numbers
									fN[nextSourcenumber].setVisited(0);									    //setting visited node to "0" which is completed

									for (a = 1; a <= lineNumber; a++)
									{
										{
											int minDistanceToBeAdded = minDistance;								//assigns temp value
											// copy the above value
											if (fN[a].isVisited() == 0|| a == nextSourcenumber) {				//checks if current is visited or next router exist
												GraphFunctions.visits(b, a, distN);								//sets value to visited with GraphFunctions value
												continue;
											}
											int currentDistance = distN[b - 1][a].getDist();					//updates current distance
											if (matrixCost[nextSourcenumber][a] != maxDistance&& minDistanceToBeAdded != maxDistance)	//checks if next matrix is highest value
											{
												int newDistance = matrixCost[nextSourcenumber][a]+ minDistanceToBeAdded;				//updates new distance with the new min dist
												if (newDistance < currentDistance) {GraphFunctions.setValues(b,a,newDistance,nextSourcenumber,distN);	//checks the least dist and set to GraphFunctions
												}
												else
												{
													GraphFunctions.setValuesnew(distN, a, b);}
											}
											else
											{
												GraphFunctions.setValuesnew(distN,a, b);
											}
										}

									}


									Integer lineMinimumDistance = null;
									Integer lineNextSourceNodeNumber = null;

									for (a = 1; a <= lineNumber; a++) 						// finding next source number and min dist to continue settin shortest path
									{

										if ((fN[a].isVisited() == 1))							//check if the router is visited
										{ if (lineMinimumDistance == null)						//checks if the min dist in line is null
										{
											lineMinimumDistance = distN[b][a].getDist();	//gets dist value from GraphFunctions
											lineNextSourceNodeNumber = a;
										} else {
											if (distN[b][a].getDist() < lineMinimumDistance)	// checks if dist value is less than dist in the line
											{
												lineMinimumDistance = distN[b][a].getDist();	//updates with new min value
												lineNextSourceNodeNumber = a;
											}
										}
										}
									}

									if (lineMinimumDistance != null) 								//checks if the line min value is not null
									{
										minDistance = lineMinimumDistance;							//updates min dist value and prints later
										nextSourcenumber = lineNextSourceNodeNumber;				//updates next router and used for printling later
									} else {
										break;
									}
								}
								connectionTable = new HashMap<Integer, DijkstraFunctions>();
								GraphFunctions.addToConnTable(lineNumber, distN,connectionTable);
								// store the distances in the arraylist

								Iterator<Integer> itr = connectionTable.keySet().iterator();		//iterates the connectiontable using key function
								while (itr.hasNext()) {
									Integer nodeNumber = itr.next();								//assigns with next value
									DijkstraFunctions node = connectionTable.get(nodeNumber);		//gets node from connection table
									int currentNodenumber = 0;										//initializing temp variable
									String strRoute = nodeNumber + " - ";
									int innerCount = 0;
									while (true) {													//executes for all elements in the matrix
										if (innerCount == lineNumber)
										{
											break;
										}
										if (nodeNumber == -1) {										//assigns not reachable if value has -1
											strRoute += " is not reachable";
											break;
										}
										if (nodeNumber == -2) {										//assigns not reachable if value has -2
											strRoute += " is not reachable";
											break;
										}


										currentNodenumber = distN[lineNumber][nodeNumber].getnNum();	//gets value for node number form conn table
										if (currentNodenumber == -1) {
											strRoute += " is not reachable";
											break;
										}
										if (currentNodenumber == source_router)							// if current is equal to source add current to route string
										{
											strRoute += currentNodenumber;
											break;
										} else {
											strRoute += currentNodenumber + " - ";						//add - to split
											nodeNumber = currentNodenumber;
										}
										innerCount++;													//increment once executed so wil come out of while loop
									}
									if (strRoute != null) {												// if route not null and contains rechable..return path
										if (!(strRoute.contains("reachable"))) {
											strRoute = shortestPATH.rv(strRoute);
										}
									}

									node.setShortDIST(strRoute);
								}

								// print the connection table
								Iterator<Integer> itr3 = connectionTable.keySet().iterator();				//initializing array
								System.out.println("\n Router " + source_router+ " Connection Table");		// prints header of conn table
								System.out.println("Destination   Interface");
								System.out.println("=======================");
								while (itr3.hasNext()) {													//Iterator checks for next value
									Integer nNum = itr3.next();
									String sourceNodeSubString = source_router+ " - ";						//gets source router value
									DijkstraFunctions node = connectionTable.get(nNum);
									System.out.print(node.getNNmber()+ "             ");
									String distanceStr = node.getShortDIST();
									if (distanceStr != null) {												//checks if the value is reachable
										if (distanceStr.contains("reachable")) {
											System.out.println("N/A");
										} else {
											String[] splitStrings = distanceStr	.split(sourceNodeSubString);	//split strings
											System.out.println(splitStrings[1].charAt(0));
										}
									}
								}
								flag++;
							} else {
								System.out.println(" Wrong Source Number, please enter valid source number");
							}
							continue;
						}
						else
						{
							System.out.println("Topology not loaded, please try option (1)\n");
						}
						break;

					case 3:

						if (flag != 0) {																		//checks if the input file is loaded
							System.out.println("\nSelect the destination router [1-"+ matrixSize + "]");		//prompts for input
							Scanner user_input2 = new Scanner(System.in);										//stores value of dest router
							int destOption = user_input2.nextInt();
							if (destOption <= matrixSize && destOption > 0) {									//validates dest router
								// print the distances and the shortest routes
								Iterator<Integer> itr2 = connectionTable.keySet().iterator();					//Initialize iterator
								shortestPATH.shortPath(destOption, itr2);										//gets shortest path and prints
							}
						}
						else
						{
							System.out.println("Please Build connection Table first, try option (2)");
						}
						break;
					case 4:
						if (flag != 0) {																	 	// checks if the matrix file is loaded

							System.out.println("\n Select a router to be removed: ");
							try {
								Scanner sourceNode = new Scanner(System.in);
								int rmrt = sourceNode.nextInt();												//stores value of remove router
								for (int i = 1; i <= lineNumber; i++) {											//checks each element in matrix
									for (int j = 1; j <= lineNumber; j++) {
										if (i == rmrt || j == rmrt) {
											matrixCost[i][j] = Integer.MAX_VALUE;								//set matched value to max value
										} else {
											matrixCost[i][j] = inputMatrix[i][j];
										}
									}
								}

								System.out.println("\nReview original topology matrix:");
								for (int i = 1; i <= lineNumber; i++) {
									for (int j = 1; j <= lineNumber; j++) {
										inputMatrix[i][j] = -2;
									}
								}

								for (int i = 1; i <= lineNumber; i++) {
									for (int j = 1; j <= lineNumber; j++) {
										if (matrixCost[i][j] != Integer.MAX_VALUE && matrixCost[i][j] != -2) {
											inputMatrix[i][j] = matrixCost[i][j];
											System.out.print(inputMatrix[i][j] + " ");
										}
									}

									if (rmrt != i)
										System.out.println();
								}

								printMatrix(inputMatrix);
								System.out.println(" The Router has been deleted, Please select (2) to build connection table\n ");
								System.out.println("and then (3) to check result of updated topology");

							} catch (IndexOutOfBoundsException e) {
								System.out
								.println("\nTopology Matrix is not N x N Form");
								continue;
							}
						}
						else
						{
							System.out.println("Topology Not loaded, please try option (1)");
						}
						break;

					case 5:
						System.out.println("Printing All connection table");

						break;

					case 6:
						System.out.println("Exit CS542 project. Good Bye!");
						System.exit(-1);														//exits program
						break;

					default:
						System.out.println("\nUnknown input, Exiting... Run Again");
						System.exit(-1);
					}

				} else {
					System.out.println("====Value not in range, Please enter options [1-5]====");		//exception if value entered is not in menu options
				}
			}
			catch(Exception e){
				System.out.println("\n ====Input Type Mismatch, Please enter valid options ==== \n");	//exception if unexpected values are entered
			}
		}

	}

	private static void printMatrix(int[][] inputMatrix)			//printing matrix
	{
		try {
			int rows = inputMatrix.length;							//stores total row numbers
			int columns = inputMatrix[0].length;					//stores total column numbers
			String str = "|";										//prints with split "|"

			for (int i = 0; i < rows; i++) {						//prints each element by element in the matrix
				for (int j = 0; j < columns; j++) {
					str += inputMatrix[i][j] + "|";
				}
				System.out.println(str + " ");
				str = "|";
			}
			System.out.println("\n");
		} catch (Exception e) {
			System.out.println("===Matrix is empty!!=====");
		}

	}

}
