import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.*;

public class WeightedGraph {

   class WeightedNode {
      int mIndex;
      private List<WeightedEdge> mNeighbors = new ArrayList<WeightedEdge>();
      
      WeightedNode(int index) {
         mIndex = index;
      }
   }

   private class WeightedEdge implements Comparable {

      private WeightedNode mFirst, mSecond;
      private double mWeight;

      WeightedEdge(WeightedNode first, WeightedNode second, double weight) {
         mFirst = first;
         mSecond = second;
         mWeight = weight;
      }

      @Override
      public int compareTo(Object o) {
         WeightedEdge e = (WeightedEdge) o;
         return Double.compare(mWeight, e.mWeight);
      }
   }

   private List<WeightedNode> mVertices = new ArrayList<WeightedNode>();

   public WeightedGraph(int numberOfVertices) {
      for (int i = 0; i < numberOfVertices; i++) {
         mVertices.add(new WeightedNode(i));
      }
   }

   public void addEdge(int firstVertex, int secondVertex, double weight) {
      WeightedNode first = mVertices.get(firstVertex);
      WeightedNode second = mVertices.get(secondVertex);
      
      WeightedEdge edge = new WeightedEdge(first, second, weight);
      first.mNeighbors.add(edge);
      second.mNeighbors.add(edge);
   }

   /**
    * Prints the graph to the console. Each vertex is printed on its own line,
    * starting with the vertex's number (its index in the mVertices list), then
    * a colon, then a sequence of pairs for each edge incident to the vertex.
    * For each edge, print the number of the vertex at the opposite end of the
    * edge, as well as the edge's weight.
    *
    * Example: in a graph with three vertices (0, 1, and 2), with edges from 0
    * to 1 of weight 10, and from 0 to 2 of weight 20, printGraph() should print
    *
    * Vertex 0: (1, 10), (2, 20) Vertex 1: (0, 10) Vertex 2: (0, 20)
    */
   public void printGraph() {
	   
	   for (int i = 0; i < mVertices.size()-1; i++){

		   System.out.print("vertex " + i + ": ");
		   for(int j = 0; j < (mVertices.get(i).mNeighbors.size()); j++){
		   System.out.print("("+ mVertices.get(i).mNeighbors.get(j).mSecond.mIndex + "," + mVertices.get(i).mNeighbors.get(j).mWeight +"), ");
		   
	   }
		   System.out.println();
	   }
   }

   /**
    * Applies Prim's algorithm to build and return a minimum spanning tree for
    * the graph. Start by constructing a new WeightedGraph with the same number
    * of vertices as this graph. Then apply Prim's algorithm. Each time an edge
    * is selected by Prim's, add an equivalent edge to the other graph. When
    * complete, return the new graph, which is the minimum spanning tree.
    *
    * @return an UnweightedGraph representing this graph's minimum spanning
    * tree.
    */
   public WeightedGraph getMinimumSpanningTree() {
      // TODO: build and return the MST.
	   WeightedGraph MST = new WeightedGraph(mVertices.size());//mst
	   HashSet mstSet = new HashSet(); //checked
	   PriorityQueue<WeightedEdge> edges = new PriorityQueue<WeightedEdge>();//edges to add
	   
	   WeightedNode mCurrentNode = mVertices.get(0);

	   WeightedEdge curNode = null;

	   while(!mstSet.containsAll(mVertices)){
		   mstSet.add(mCurrentNode);
			for(int i = 0; i < mstSet.size(); i++){
			   for(int j = 0; j < (mVertices.get(i).mNeighbors.size()); j++){
				   if(!mstSet.contains(mVertices.get(i).mNeighbors.get(j).mSecond)){
					   edges.add(mVertices.get(i).mNeighbors.get(j));			
				   }
			   }  
			}
			curNode = edges.poll();
			if (curNode == null) break;
		   if(!mstSet.contains(curNode.mSecond)){
			   MST.addEdge(curNode.mFirst.mIndex, curNode.mSecond.mIndex, curNode.mWeight);		   
			   edges.clear();
		   }
		   mCurrentNode = curNode.mSecond;		   
	   } 
	   return MST;
   }

   /**
    * Applies Dijkstra's algorithm to compute the shortest paths from a source
    * vertex to all other vertices in the graph. Returns an array of path
    * lengths; each value in the array gives the length of the shortest path
    * from the source vertex to the corresponding vertex in the array.
    */
   public double[] getShortestPathsFrom(int source) {
      // TODO: apply Dijkstra's algorithm and return the distances array.
      
      // This queue is used to select the vertex with the smallest "d" value
      // so far.
      // Each time a "d" value is changed by the algorithm, the corresponding
      // DijkstraDistance object needs to be removed and then re-added to
      // the queue so its position updates.
      PriorityQueue<DijkstraDistance> vertexQueue = 
       new PriorityQueue<DijkstraDistance>();
      
      // Initialization: set the distance of the source node to 0, and all
      // others to infinity. Add all distances to the vertex queue.
      DijkstraDistance[] distances = new DijkstraDistance[mVertices.size()];
      distances[source] = new DijkstraDistance(source, 0);
      double[] d = new double[distances.length];
      
      for (int i = 0; i < distances.length; i++) {
         if (i != source)
            distances[i] = new DijkstraDistance(i, Integer.MAX_VALUE);
         
         vertexQueue.add(distances[i]);
      
      }
      DijkstraDistance temp = distances[1];
      double length = 0;

      while (vertexQueue.size() > 0) {
         // Finish the algorithm.
    	  temp = vertexQueue.poll();
    	  for(int j = 0; j < (mVertices.get(temp.mVertex).mNeighbors.size()); j++){
    		  if (mVertices.get(temp.mVertex).mIndex < mVertices.get(temp.mVertex).mNeighbors.get(j).mSecond.mIndex){
    			  int tempint = mVertices.get(temp.mVertex).mIndex; 
    			  double tempWeight = mVertices.get(temp.mVertex).mNeighbors.get(j).mWeight;
    			  int tempSecondInt = mVertices.get(temp.mVertex).mNeighbors.get(j).mSecond.mIndex;
    			  length = distances[tempint].mCurrentDistance + tempWeight;
    			  double lengthComp = distances[tempSecondInt].mCurrentDistance; 
    			  if (length < lengthComp){
    				  DijkstraDistance newDij = new DijkstraDistance(tempSecondInt, length);
    				  vertexQueue.add(newDij);    
    				  distances[tempSecondInt].mCurrentDistance = length;
    			  }
    		  }
    	  }
    	  
      }
      
      for (int i = 0; i < distances.length; i++){
    	  d[i] = distances[i].mCurrentDistance;
      }
      return d;
   }

   class DijkstraDistance implements Comparable {

      int mVertex;
      double mCurrentDistance;

      DijkstraDistance(int vertex, double distance) {
         mVertex = vertex;
         mCurrentDistance = distance;
      }

      @Override
      public int compareTo(Object other) {
         DijkstraDistance d = (DijkstraDistance) other;
         return Double.compare(mCurrentDistance, d.mCurrentDistance);
      }
   }
   
   public static void main(String[] args) {
      // Use this main to test your code by hand before implementing the program.
//      WeightedGraph g = new WeightedGraph(8);
//      g.addEdge(0, 1, 1);
//      g.addEdge(0, 2, 3);
//      g.addEdge(1, 2, 1);
//      g.addEdge(1, 3, 1);
//      g.addEdge(1, 4, 4);
//      g.addEdge(2, 3, 1);
//      g.addEdge(2, 5, 2);
//      g.addEdge(3, 4, 1);
//      g.addEdge(3, 5, 3);
//      g.addEdge(4, 5, 2);
//      g.printGraph();
      
      try{
    	  
          int choice;
    	  Scanner in = new Scanner(System.in);
    	  System.out.println("Input File Name");
    	  String userfile = in.next();
    	  
    	  int count = 0;
    	  Scanner scanner = new Scanner(new File(userfile));
    	  int size = scanner.nextInt();
    	  WeightedGraph g = new WeightedGraph(size);
    	  
        g.addEdge(0,scanner.nextInt(),scanner.nextInt());
        g.addEdge(0,scanner.nextInt(),scanner.nextInt());
        g.addEdge(0,scanner.nextInt(),scanner.nextInt());
        g.addEdge(0,scanner.nextInt(),scanner.nextInt());
        g.addEdge(1,scanner.nextInt(),scanner.nextInt());
        g.addEdge(1,scanner.nextInt(),scanner.nextInt());
        g.addEdge(1,scanner.nextInt(),scanner.nextInt());
        g.addEdge(1,scanner.nextInt(),scanner.nextInt());
        g.addEdge(2,scanner.nextInt(),scanner.nextInt());
        g.addEdge(2,scanner.nextInt(),scanner.nextInt());
        g.addEdge(3,scanner.nextInt(),scanner.nextInt());
        g.addEdge(3,scanner.nextInt(),scanner.nextInt());
        g.addEdge(4,scanner.nextInt(),scanner.nextInt());
        g.addEdge(4,scanner.nextInt(),scanner.nextInt());
    	  
    	  boolean check = true;

    	  while(check){
        	  System.out.println("Option 1) print the graph \nOption 2) print the minimum spanning tree \nOption 3) print shortest paths \nOption 4) Exit");
        	  choice = in.nextInt();
        	  System.out.println();
        	  switch(choice){
        	  case 1:
        		  g.printGraph();
        		  System.out.println();
        		  break;
        	  case 2:
        	      WeightedGraph mst = g.getMinimumSpanningTree();
        	      System.out.println("Minimum spanning tree:");
        	      mst.printGraph();
        	      System.out.println();
        		  break;
        	  case 3:
        	      double[] distances = g.getShortestPathsFrom(0);
        	      for (int i = 0; i < distances.length; i++) {
        	         System.out.println("Distance from 0 to " + i + ": " + 
        	          distances[i]);   
        	      }
        	      System.out.println();
        		  break;
        	  case 4:
        		  check = false;
        		  break;
        	  }
    		  
    	  }

    	  scanner.close();
      }	catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } 
      
      
      }   
}
