/*The main method class, it does
	1. read and process input
	2. initial the method class object
	3. print out the three tables
*/


import java.util.*;
import java.lang.*;
import java.io.*;

public class Dijkstra{
	//all nodes are stored in Object for arbitrary testing
	static HashSet<Object> graphNode;
	static Graph graph;
	static int nodeNum;
	static ArrayList<Object> linkstate;
	

	public static void main(String[] args) throws IOException{

		graphNode = new HashSet<>();
		
		String input= null ;
		Scanner s = new Scanner(System.in);

		while(s.hasNext()){
			input= s.nextLine();

			if(input.equals("END"))
			{
				System.exit(0) ;
			// for keyword is given or not both situations
			}else if( input.equals("UPDATE")||input.equals("LINKSTATE") || input.matches(".*\\d.*" ))			
			{

				if (input.equals("LINKSTATE") || input.equals("UPDATE") ) {
					String temp = s.nextLine();//skip the LINKSTATE & UPDATE line
					processingInput(temp);				
				}else {
					processingInput(input);					
				}
			//initiate the graph class object	
			//construct the typology
			//How to keep reading to a point where the 
			}else{
			 	graphNode.add(input);			 	
			 	nodeNum = graphNode.size();
				graph = new Graph(nodeNum);
				 for (Object n : graphNode) {
				 	graph.insertNode(n);	 	
				 }

			}
		}
	}



	/*This program takes the input line, and parse it to tokens and store it in a array
	1. the first part of the array will call add edge function
	2. the second part of the array will be the edge value
	3. then I loop through the rest of the array and pass the alphatically sorted nodes in my method class*/
	static void processingInput(String str)
	{
		//parse the input string, get rid of the multiple spaces and commas
		Object[] array = str.split("[ ,]+");
		//cast the object to be integer
	   	int edgeValue = Integer.valueOf((String)array[2]);
		

	   	//check if nodes exist in the hashset container
	   	if(graphNode.contains(array[0]) && graphNode.contains(array[1]))
	   	{

	   		//check if the value is -1
	   		if (edgeValue == -1) {	   			
	   			
	   			graph.removeEdge(array[0],array[1]);  			
	   	
	   		}else{
	   			graph.addEdge(array[0], array[1], edgeValue);
	   		
	   		}	
	   	//add new nodes to the hashset	
	   	}else{
	   		//add possible new nodes
	   		graphNode.add(array[0]);
	   		graphNode.add(array[1]);
	   		graph.insertNode(array[0]);
	   		graph.insertNode(array[1]);

	   		//check if the edge value is -1
	   		if (edgeValue == -1) {	

	   			graph.removeEdge(array[0],array[1]); 			
	   		}else{
	   			graph.addEdge(array[0], array[1], edgeValue);
	   		}	

	   	}

	   	//loop through the optinal list and pass the nodes
		if (array.length > 3) 
		{

			Object[] optionalList = Arrays.copyOfRange(array, 3, array.length);

			Arrays.sort(optionalList);

			for (int i =0; i< optionalList.length ; i++ ) {

				graph.printNeighbourTable(optionalList[i]);

				graph.printLSDB(optionalList[i]);

				graph.printlnDijkstra(optionalList[i]);

				
			}	
		}		
	}

}








	

