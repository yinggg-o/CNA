import java.util.*;

public class GraphNlogN{

	private List<Object> nodeList;
	private int[][] graph;
    private int infinite;
    private ArrayList<String> dijResult;


	//constructor
	//inital edge value set as -1
	public Graph(int nodesNum)
	{
		nodeList = new ArrayList<>(nodesNum);		
        infinite = 999999;		
	}


	//add nodes to this graph
	public Graph insertNode(Object node)
	{
		nodeList.add(node);
        graph = new int[nodeList.size()][nodeList.size()];

		return this;
	}


	//add edges to this graph
	public Graph addEdge(Object start, Object end, int value)
	{
		//if both source and destination are in this graph
		if (nodeList.contains(start) && nodeList.contains(end)) {

			int indexStart = nodeList.indexOf(start);
			int indexEnd = nodeList.indexOf(end);

			graph[indexStart][indexEnd] = value;
			graph[indexEnd][indexStart] = value;
			
		}

        return this;

	}


    public Graph removeEdge(Object start, Object end)
    {
        
        if (nodeList.contains(start) && nodeList.contains(end)) {

            int indexStart = nodeList.indexOf(start);
            int indexEnd = nodeList.indexOf(end);

            graph[indexStart][indexEnd] = 0;
            graph[indexEnd][indexStart] = 0;
            
        }

        return this;
    }



    public int getWeight(Object object1, Object object2){
   
        int result = Integer.MAX_VALUE; 
        if (nodeList.contains(object1) && nodeList.contains(object2)){
   
            int index1 = nodeList.indexOf(object1);
            int index2 = nodeList.indexOf(object2);
            result = graph[index1][index2];
        }
        return result;
    }



    public void printNeighbourTable(Object node)
    {
 		System.out.println(node.toString() + " " + "Neighbour Table:");   

    	StringBuilder sb = new StringBuilder();
    	int index = nodeList.indexOf(node);

    	if (index >= 0) {

    		for (int i =0;i<graph.length ; i++ )
    		 {

    			if (graph[index][i] >0) {

    				sb.append(nodeList.get(i) + "," + graph[index][i]+ "\n");
    				
        		}
    		
    		} 
    				
    	}
    	String result = sb.toString(); 	
    	System.out.println(result);		
    }






    public void printLSDB(Object node)
    {       
        HashMap<Integer, HashMap<Object, Object>> dfsPath = new HashMap<>();
       
        ArrayList<String> result = new ArrayList<>();

        System.out.println(node.toString() + " " + "LSDB:");  

        StringBuilder sb = new StringBuilder();

        dfsPath = dfs(node);

        if(!dfsPath.isEmpty())
        {         
            for (Integer temp : dfsPath.keySet()) {
                
                for ( Object o: dfsPath.get(temp).keySet() ) 
                {                
                   
                    Object[] array = new Object[2];

                    array[0] = o;
                    array[1] = dfsPath.get(temp).get(o);
                    Arrays.sort(array);
                    int i = getWeight(array[0], array[1]);                 
                    String str = array[0] +"," + array[1] + "," + i;
                    result.add(str);  

                }   
                         
                }     
             
             } 

                Collections.sort(result); 

                for (int i =0; i<result.size() ;i++ ) {
                 System.out.println(result.get(i));
        }   

        System.out.println();
    
    }


    /*
    This function takes a node then run dfs to all the reachable nodes
    Returns a hashmap of the path, where the key is set for unique number to map all the connected nodes
    
    DFS implementation adapted from https://www.mathcs.emory.edu/~cheung/Courses/171/Syllabus/11-Graph/Progs/dfs/
    */

    public HashMap<Integer, HashMap<Object, Object>> dfs(Object node)
    {
    	int graphLength = graph.length;
        HashMap<Integer, HashMap<Object, Object>> map = new HashMap<>();


    	Stack<Object> s = new Stack<Object>();
    	boolean visited[] = new boolean[graphLength];

    	for ( int j =0; j< visited.length;j++ ) {  		
    		visited[j] = false;
    	}  	

    	s.push(node);

        int counter =0;

    	//into the loop
    	while(!s.isEmpty())
    	{
    		Object nextNode;
    		nextNode = s.pop();

    		if (!visited[nodeList.indexOf(nextNode)]) 
    		{
    			visited[nodeList.indexOf(nextNode)] = true;		
                
    				for(int i =0; i < graphLength; i++)
                    {
   
    					if (graph[nodeList.indexOf(nextNode)][i] >0 && ! visited[i]) {

    					    counter++;

                            map.put(counter, new HashMap<Object, Object>());

                            s.push(nodeList.get(i));
  
                            map.get(counter).put(nextNode, nodeList.get(i));                   

    					}				
    				}
    		}
    	} 

        return map;
	}


    public void printlnDijkstra(Object node)
    {

        ArrayList<String>  dijPath = new ArrayList<String>();

        System.out.println(node.toString() + " " + "Routing Table:");
        Dijkstra(node);

        dijPath = dijResult;

        Collections.sort(dijPath);


        for (int i =0; i< dijPath.size() ;i++ ) {
            System.out.println(dijPath.get(i));
        }

        System.out.println();
   
    }




    public void Dijkstra(Object node)
    {
    

        int numNodes = graph.length;  
        int[][] linkCost = new int[numNodes][numNodes];

        //initial a linkcost matrix
        for ( int i =0; i<numNodes ; i++ ) {        
            for ( int j =0 ; j<numNodes ; j++ ) {

                 linkCost[i][j] = graph[i][j];

                if(linkCost[i][j] == 0){
                    linkCost[i][j] =infinite;
                }
              
            }
        }

      //dijkstra algo
       Object[] predNode = new Object[numNodes];
       int source = nodeList.indexOf(node);
       predNode[source] = node;
       boolean[] reached = new boolean[numNodes];
       int[] distance = new int[numNodes];
        int m =0;
        int k =0;
        int n =0;


        for (int i =0; i< numNodes ;i++ ) {

            reached[i] = false;
            reached[source] = true;//{source node}
            
        }


        for ( int i =0; i< numNodes ;i ++ ) {

            distance[i] = linkCost[source][i];//distance[n] = length of shortest path s ->n

           // System.out.println(distance[i]);

            if (linkCost[source][i]< infinite) {
                predNode[i] =nodeList.get(i);

              //  System.out.println("The next node is:"+ predNode[i].toString());
            }
            
        }

        //find the first unreached node m(source node's edge)
        for ( k =0;k<numNodes-1 ; k++) {
            for (m =0; m<numNodes ; m++ ) {   
                if (!reached[m]) {
                    break;
                }
            }
            

        //calculate the min distance
        for (n = m+1; n<numNodes ; n++ ) {
            if (reached[n] == false && distance[n] < distance[m]) {
                m=n;
                
            }
        }
        //marked as explored
        reached[m] = true;

        //update possible route
        for ( n=0; n<numNodes ; n++ ) {
            if (reached[n] == false) {
                if (distance[m] + linkCost[m][n] < distance[n]) {
                    distance[n] = distance[m] + linkCost[m][n];
                    predNode[n] = nodeList.get(m);
                    
                }
            }          
        }     
        
        printResult(reached, distance, predNode);
        
        } 
    }



    ArrayList<String> printResult(boolean[] reached, int[] distance, Object[] a)   
    {
        dijResult =  new ArrayList< String>();
      
        String hopStr = "";
        String destStr= "";
        String path = "";              

        for (int i =0; i<reached.length ;i++ )
         {        
            if (distance[i] < infinite) 
           {
                destStr = nodeList.get(i).toString() ;                              
                hopStr = a[i].toString(); 
                path = destStr + "," + hopStr + "," +   String.valueOf(distance[i]);
                dijResult.add(path);
           }         
        }
       return dijResult;
    }

}