import java.util.*;
import java.lang.*;
import java.io.*;

public class DijkstraNlogN{
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
            // for both keyword is given or not both situations
            }else if( input.equals("UPDATE")||input.equals("LINKSTATE") || input.matches(".*\\d.*" ))           
            {

                if (input.equals("LINKSTATE") || input.equals("UPDATE") ) {

                    String temp = s.nextLine();//skip the LINKSTATE line
                    processingInput(temp);
                
                }else {

                    processingInput(input);
                    
                }
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





    /*This program takes */
    static void processingInput(String str)
    {
        Object[] array = str.split("[ ,]+");
        int edgeValue = Integer.valueOf((String)array[2]);//cast the object to be integer
        

        //check new nodes in the hashset
        if(graphNode.contains(array[0]) && graphNode.contains(array[1]))
        {

            //check if the value is -1
            if (edgeValue == -1) {
                graph.removeEdge(array[0],array[1]);
                
            }else{
                graph.addEdge(array[0], array[1], edgeValue);
            }   

        }else{
            //add possible new nodes
            graphNode.add(array[0]);
            graphNode.add(array[1]);
            graph.insertNode(array[0]);
            graph.insertNode(array[1]);

            //check if the value is -1
            if (edgeValue == -1) {  
                graph.removeEdge(array[0],array[1]);            
            }else{
                graph.addEdge(array[0], array[1], edgeValue);
            }   

        }

        //print out the optional list
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
