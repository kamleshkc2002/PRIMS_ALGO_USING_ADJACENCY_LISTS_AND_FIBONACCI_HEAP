import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Edge {
	private Node X, Y;
	private int weight;

	public Edge(Node x, Node y)
	{
		this(x, y, Integer.MAX_VALUE);
	}
	public Edge(Node x, Node y, int EdgeWeight)
	{
		X = x;
		Y = y;
		this.weight = EdgeWeight;
	}
	public int getTotalWeight(){	return this.weight;}

	public Node getNodeX(){return X;}
	public Node getNodeY(){return Y;}
		public boolean equals(Object o){
		if( o != null && o instanceof Edge)
		{
			return ( this.X.getNodeID() == ((Edge)o).getNodeX().getNodeID() &&
					this.Y.getNodeID() == ((Edge)o).getNodeY().getNodeID() ) ||
					( this.Y.getNodeID() == ((Edge)o).getNodeX().getNodeID() &&
					this.X.getNodeID() == ((Edge)o).getNodeY().getNodeID() );
		}

		return false;
	}

}
class Node {
	private int Id;
	private boolean visited = false;
        //creates the nodes for the algorithm to create graphs
	public Node(){}
	public Node(int ID){ this.Id = ID;}
        //Condition to check if the node has been seen before in the algorithm
	public boolean IsVisited(){ return visited; }
        //Returns the nodeid of the node visited
	public int getNodeID() {return Id;}
	public void visit(){visited = true;}
	//Returns false if the node hasnt been visited before by the algorithm
	public void unvisit(){visited = false;}

	@Override
	public boolean equals(Object visitnode) {

		if(visitnode != null && visitnode instanceof Node)
		{
			return this.getNodeID() == ((Node) visitnode).getNodeID();
		}

		return false;
	}

}

class HeapTreeNode {
	private int degree;
	private Node n;

}

class RandomGraphGenerate {
	private int NoNode;
	private int NoEdge;
	private double density;

	public RandomGraphGenerate(int nodes, double dense)
	{
		NoNode = nodes;
		NoEdge = (int) ( dense/100.0 * nodes*(nodes-1)/200);
		density = dense;
	}

	public InitialGraphGenerate CreateGraph(){
		InitialGraphGenerate graph = new InitialGraphGenerate();
		Random rd = new Random(System.currentTimeMillis());
		ArrayList<Node> newNode = new ArrayList<Node>();
		ArrayList<Edge> newEdge = new ArrayList<Edge>();
		int weight;

		int edgecount;
                edgecount = 0;

		for(int a = 0; a <NoNode; a++)
		{
			for(int b = 0; b <a; b++)
			{

				if(rd.nextDouble() <= density )
				{
					weight = rd.nextInt(1000) + 1;
					Edge e = new Edge(new Node(a), new Node(b), weight);
					newEdge.add(e);
					graph.AddUnqEdge(e);
				}
			}
		}

		//adds the node to the new graph to be generated
		int graphSize;
                graphSize = 0;
		for(Edge e : newEdge)
		{
			graph.AttachNode(e.getNodeX());
		}

		System.out.println(NoNode +" "+ density);
		return graph;


	}

}
class InitialGraphGenerate {
	private ArrayList<Node> leaf = new ArrayList<Node>();
	private ArrayList<Edge> leaflength = new ArrayList<Edge>();


	public boolean AttachNode(Node node){
		if(!leaf.contains(node))
		{
			leaf.add(node);
			return true;
		}
		else
		{
        //this checks for the duplicate nodes. If duplicate node exists returns false
			return false;
		}
	}
	//adds a unique node to the tree
	public boolean AddUnqNode(Node node){
			leaf.add(node);
			return true;
	}
	//adds the edge between two nodes
	public boolean AddEdge(Edge e)
	{
		if(!leaflength.contains(e))
		{
			leaflength.add(e);
			return true;
		}
		else
        //checks if a node visited is in the graph and connects. If not then returns false
			return false;
	}

	public boolean AddUnqEdge(Edge e)
	{

		leaflength.add(e);
		return true;
	}
	//returns the nodes added to the graph
	public ArrayList<Node> getLeaf(){ return leaf;}
	//returns the leaf length of the nodes in the graph
	public ArrayList<Edge> getLeafLength(){ return leaflength;}
	//removes a node from the graph
	public boolean RemoveLeaf(Node node){
		return	leaf.remove(node);
	}

	public boolean RemoveEdge(Edge e){
		return leaflength.remove(e);
	}

	//get neighbour edges of current vertices
		public ArrayList<Edge> getNeighbourEdges(ArrayList<Node> vertices, ArrayList<Edge> remainEdges)
		{
			ArrayList<Edge> nearbyEdgeLength = new ArrayList<Edge>();

				for(Edge e : remainEdges)
				{
					if( !vertices.contains(e.getNodeX()) && vertices.contains(e.getNodeY()))
					{
						nearbyEdgeLength.add(e);
					}
					else if( vertices.contains(e.getNodeX()) && !vertices.contains(e.getNodeY()))
					{
						nearbyEdgeLength.add(e);
					}
				}

			return nearbyEdgeLength;
		}



    public void DisplayGraph(){
    	for(Node n : leaf)
    	{
    		System.out.print("Node: "+n.getNodeID()+", ");

    	}
    	System.out.println();
    	for(Edge e : leaflength)
    	{
    		System.out.print("Edge: "+ e.getNodeX().getNodeID() +"->"
    				+e.getNodeY().getNodeID()+" Weight: "+e.getTotalWeight() +", ");

    	}
    	System.out.println();


    }

}

class MSTBasic{
	private InitialGraphGenerate graph;
	private double TotalCost = 0;

	public MSTBasic(InitialGraphGenerate g) { graph = g;}

	public double getTotalCost(){ return TotalCost; }

	public ArrayList<Edge> getMST(){
		//vertices is the nodes of the MST
		ArrayList<Node> vertices = new ArrayList<Node>();
		//path is the edges of the MST
		ArrayList<Edge> path = new ArrayList<Edge>();
		// first add the first node in graph to vertices
		ArrayList<Edge> neighbourEdges  = new ArrayList<Edge>();
		ArrayList<Edge> remainEdges = graph.getLeafLength();
		vertices.add(graph.getLeaf().get(0));
		ArrayList<Edge> buffer  = new ArrayList<Edge>();
		//it may not have MST, take care of it
		while( vertices.size() < graph.getLeaf().size() )
		{
                  Collections.sort(remainEdges,new compareEdge());
                    while(true)
                    {
			Edge minimumEdge = remainEdges.get(0);
			if(vertices.contains(minimumEdge.getNodeX())&&vertices.contains(minimumEdge.getNodeY()) )
			{
        		//Remains minimum edge from the generated graph
			remainEdges.remove(0);
			}
			else if(!vertices.contains(minimumEdge.getNodeX())&&!vertices.contains(minimumEdge.getNodeY()))
			   {
				buffer.add(remainEdges.remove(0));
                           }
			else if(vertices.contains(minimumEdge.getNodeX())&&!vertices.contains(minimumEdge.getNodeY()))
                           {
                        	vertices.add(minimumEdge.getNodeY());
				path.add(minimumEdge);
				//finds the path for the minimum edge
				break;
				}
				else if(!vertices.contains(minimumEdge.getNodeX())&&vertices.contains(minimumEdge.getNodeY()))
				{
					vertices.add(minimumEdge.getNodeX());
					path.add(minimumEdge);
					break;

				}
				else
				{
					break;
				}


			}

			remainEdges.addAll(buffer);
                        //clears the buffer to add new nodes
			buffer.clear();
                        //total cost og the MST after removing the root element
			TotalCost += path.get(path.size()-1).getTotalWeight();
		}
		return path;
	}
}

class compareEdge implements Comparator<Edge>{
	@Override
        //this method is called to check the weight of the nodes.
	public int compare(Edge e1, Edge e2) {
		if(e1.getTotalWeight() < e2.getTotalWeight())
			return -1;
		else if(e1.getTotalWeight() == e2.getTotalWeight())
			return 0;
		else
			return 1;
	}
}
class InitialFibo<T> {
//this class is responsible for the implementation og the fibonacci algo to be used later
    public class TreeNode<T> {

        private TreeNode<T> rightChild;   // sibling elements in the list
        private TreeNode<T> leftChild;
        private TreeNode<T> parent;
        private TreeNode<T> child;  // Child node
	private int  degree = 0;    // initial degree set to minimum for the node to add
        private boolean childremove = false;
        private T      edgedata;     // edge = T
        private double weight;
        //this method gets the data of the edges
        public T getData() {
            return edgedata;
        }
        //this method sets the data for the edges in the graphs
        public void setData(T value) {
            edgedata = value;
        }
        //gets the data for the weight of the nodes
        public double getWeight() {
            return weight;
        }
		//constructor for treenode
        private TreeNode(T initdata, double edgeweight) {
            rightChild = leftChild = this;
            edgedata = initdata;
            weight = edgeweight;
        }
    }

    //Pointer to the minimum element in the heap.
    private TreeNode<T> min = null;

    //Initialization of the heap size
    private int heapsize = 0;

    public void insertNode(T value, double priority) {
        //This creates a new node and inserts the same in the graph
        TreeNode<T> result = new TreeNode<T>(value, priority);
        //the new node created is merged with the existing graph
        min = merge(min, result);
       //this increases the heap size after adding the nodes
        ++heapsize;
    }

   //this returns the minimum node in the graph
    public TreeNode<T> heapMin() {
        return min;
    }

   //checks if the heap is empty
    public boolean heapEmpty() {
        return min == null;
    }


    public int heapSize() {
        return heapsize;
    }


    public TreeNode<T> delMinNode() {

        //store the current min element
        TreeNode<T> minElem = min;


        if (min.rightChild == min) { //if only one element
            min = null;
        }
        else { // delete min from top level list
            min.leftChild.rightChild = min.rightChild;
            min.rightChild.leftChild = min.leftChild;
            min = min.rightChild; // set min to some value and decide real min later
        }

      //this erases the parent of the minimum child
        if (minElem.child != null) {

            TreeNode<?> curr = minElem.child;
            do {
                curr.parent = null;
                curr = curr.rightChild;
            } while (curr != minElem.child);
        }


        min = merge(min, minElem.child);

        //after merging find the minimum element and return the value of the minimum node
        if (min == null) return minElem;

        //Stores the data of the entire graph table
        List<TreeNode<T>> treeTable = new ArrayList<TreeNode<T>>();

        //this denotes the node to be visited to check for minimum
        List<TreeNode<T>> toVisit = new ArrayList<TreeNode<T>>();

        //addition of elements to check
        for (TreeNode<T> curr = min; toVisit.isEmpty()
		|| toVisit.get(0) != curr; curr = curr.rightChild)
            toVisit.add(curr);

        //comparision pair-wise of the elements in the list
        for (TreeNode<T> latest: toVisit) {

            while (true) {
                while (latest.degree >= treeTable.size())
                    treeTable.add(null);

                if (treeTable.get(latest.degree) == null) {
                    treeTable.set(latest.degree, latest);
                    break;
                }

                TreeNode<T> other = treeTable.get(latest.degree);
                treeTable.set(latest.degree, null);

                TreeNode<T> min = (other.weight < latest.weight)? other : latest;
                TreeNode<T> max = (other.weight < latest.weight)? latest  : other;

                max.rightChild.leftChild = max.leftChild;
                max.leftChild.rightChild = max.rightChild;
                max.rightChild = max.leftChild = max;
                min.child = merge(min.child, max);
                max.parent = min;
                max.childremove = false;

                //if pairwise compare fails increase the degree so that it is not picked
                ++min.degree;

                latest = min;
            }

          //after pairwise check assign the value of current node to the minimum pointer
            if (latest.weight <= min.weight) min = latest;
        }
        return minElem;
    }



   //merges the doubly linked list to create a single tree
    private <T> TreeNode<T> merge(TreeNode<T> min, TreeNode<T> inserted) {

        if (min == null && inserted == null) {
            return null;
        }
        else if (min != null && inserted == null) {
            return min;
        }
        else if (min == null && inserted != null) {
            return inserted;
        }
        else {
            TreeNode<T> minNext = min.rightChild;
            min.rightChild = inserted.rightChild;
            min.rightChild.leftChild = min;
            inserted.rightChild = minNext;
            inserted.rightChild.leftChild = inserted;

            return min.weight < inserted.weight? min : inserted;
        }
    }

//this is to check if the child is removed from the tree. If no parent is found set parent as null
//also compares if the node has any siblings or children which have to be cut
    private void ChildRemove(TreeNode<T> TreeNode) {
        TreeNode.childremove = false;

        if (TreeNode.parent == null) return;

        if (TreeNode.rightChild != TreeNode) {
            TreeNode.rightChild.leftChild = TreeNode.leftChild;
            TreeNode.leftChild.rightChild = TreeNode.rightChild;
        }

        if (TreeNode.parent.child == TreeNode) {

            if (TreeNode.rightChild != TreeNode) {
                TreeNode.parent.child = TreeNode.rightChild;
            }

            else {
                TreeNode.parent.child = null;
            }
        }

       //decreases the degree of the parent node whose child is removed
        --TreeNode.parent.degree;


        TreeNode.leftChild = TreeNode.rightChild = TreeNode;
        min = merge(min, TreeNode);


        if (TreeNode.parent.childremove)
            ChildRemove(TreeNode.parent);
        else
            TreeNode.parent.childremove = true;

        TreeNode.parent = null;
    }
}
//starts implementation of the fibonacci algorithm for the MST. This will use the
//initialFibo Class defined earlier to create a fibonnaci heap and then check for the nodes
class MSTFibonacci{
	private InitialGraphGenerate graph;
	private double TotalCost = 0;
	//generates a graph to be input in the initialFibo class
	public MSTFibonacci(InitialGraphGenerate g) { graph = g;}
	//gets the total weight of the minimum spanning tree
	public double getTotalCost(){ return TotalCost; }

	public ArrayList<Edge> returnMST(){
		//vertices is the nodes of the MST
		ArrayList<Node> vertices = new ArrayList<Node>();
		//path is the edges of the MST
		ArrayList<Edge> path = new ArrayList<Edge>();
		// first add the first node in graph to vertices
		vertices.add(graph.getLeaf().get(0));
                //create the heap for the tree to get started
                InitialFibo<Edge> fiboheap = new InitialFibo<Edge>();

		for(Edge e : graph.getLeafLength())
		{
                    //inserts the nodes into the tree
                    fiboheap.insertNode(e, e.getTotalWeight());
		}
                //stores the new edge in the buffer
		ArrayList<Edge> buffer = new ArrayList<Edge>();
		while( vertices.size() < graph.getLeaf().size() )
		{
			while(true)
			{
				//gets the value of the minimum edge
				Edge minEdge = fiboheap.heapMin().getData();
				if(vertices.contains(minEdge.getNodeX())&&vertices.contains(minEdge.getNodeY()))
				{
                                //deletes the node if there is a duplicate node existence
					fiboheap.delMinNode();
				}
				else if(!vertices.contains(minEdge.getNodeX())&&!vertices.contains(minEdge.getNodeY()))
				{
                                //if not a duplicate then add it to the tree
					buffer.add(fiboheap.delMinNode().getData());
				}
				else if(vertices.contains(minEdge.getNodeX())&&!vertices.contains(minEdge.getNodeY()))
				{
					vertices.add(minEdge.getNodeY());
					path.add(minEdge);
					break;
				}
				else if(!vertices.contains(minEdge.getNodeX())&&vertices.contains(minEdge.getNodeY()))
				{
					vertices.add(minEdge.getNodeX());
					path.add(minEdge);
					break;
                                //the above statements choose which node to add to the tree
				}
				else
				{
                                //if this piece of code is touched that means there is no node to be considered
					break;
				}

			}

			for(Edge e : buffer)
			{
				fiboheap.insertNode(e, e.getTotalWeight());

			}

			buffer.clear();
			TotalCost += path.get(path.size()-1).getTotalWeight();

		}
		return path;
	}
}
//start of the main class. this checks for the arguments provided and calls the appropriate method
// the usage is as under
// mst -r v d (this creates a random graph with the given vertices and densities
// mst -s filename (this runs the simple array adjacency list rep of Prims to find the weight of the nodes)
// mst -r filename (this runs the fibonacci heap method to get the weight of the nodes)
//
public class mst {
	public static void main(String[] args) {
	     if (args == null || args.length < 2 || args.length > 3)
             {
			System.out.println("Please choose the given choices only -");
			System.out.println("mst -r v d (for random mode. v and d have to be integers)");
			System.out.println("mst -s filename (for simple scheme. File should Exist)");
			System.out.println("mst -f filename (for fibonacci scheme. File should Exist)");
                        return;
             }
		long SimpleStart;
		long FiboStart;
		long SimpleEnd;
		long FiboEnd;
		int noEdges;
		int noNodes;
		ArrayList<Edge> finalMST;
		if( args[0].equals("-r"))
		{
			RandomGraphGenerate rdgraph = new RandomGraphGenerate(Integer.parseInt(args[1]), Double.parseDouble(args[2]));
			InitialGraphGenerate randomGraph = rdgraph.CreateGraph();
			//this checks for the time for the fibonacci heap algo to run
			FiboStart = System.currentTimeMillis();
			MSTFibonacci mstfib = new MSTFibonacci(randomGraph);
			printMST(mstfib.returnMST());
			System.out.println("Fibonacci Cost "+mstfib.getTotalCost());
			FiboEnd = System.currentTimeMillis() - FiboStart;                        

                        //this checks for the time for the simple scheme to run
                        SimpleStart = System.currentTimeMillis();
			MSTBasic simpleMST = new MSTBasic(randomGraph);
			printMST(simpleMST.getMST());
			System.out.println("Simple Scheme cost "+simpleMST.getTotalCost());
			SimpleEnd = System.currentTimeMillis() - SimpleStart;
			System.out.println("Heap time "+ FiboEnd + "\n"+ "Array time "+ SimpleEnd); 
                       
		}

		else if(args[0].equals("-s"))
		{
			//user input mode: mst -s filename
			InitialGraphGenerate InputGraph = new InitialGraphGenerate();
			try {
                        //assumes file input is in the proper format    
				Scanner FileIn = new Scanner(new File(args[1]));
				noNodes = Integer.parseInt(FileIn.next() );
				noEdges = Integer.parseInt(FileIn.next() );
				while(FileIn.hasNextLine())
				{
					Node a = new Node(Integer.parseInt(FileIn.next()) );
					Node b = new Node(Integer.parseInt(FileIn.next()) );
					Edge e = new Edge(a, b, Integer.parseInt(FileIn.next()) );
					InputGraph.AttachNode(a);
					InputGraph.AttachNode(b);
					InputGraph.AddEdge(e);
				}

				FileIn.close();
			} catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block                            
			System.out.println("File does not exist: " + args[1]);
                        return;
			}


			//simple array input mode
			MSTBasic simple = new MSTBasic(InputGraph);
			finalMST = simple.getMST();
			System.out.println(simple.getTotalCost());
			outputMST(finalMST);



		}

		else if(args[0].equals("-f") )
		{
                    //user input mode: mst -f filename
                    //inputs data from a file given at command prompt
			InitialGraphGenerate InitGraph = new InitialGraphGenerate();
			try {
				Scanner FileIn = new Scanner(new File(args[1]));
				noNodes = Integer.parseInt(FileIn.next() );
				noEdges = Integer.parseInt(FileIn.next() );
				while(FileIn.hasNextLine())
				{
					Node x = new Node(Integer.parseInt(FileIn.next()) );
					Node y = new Node(Integer.parseInt(FileIn.next()) );
					Edge e = new Edge(x, y, Integer.parseInt(FileIn.next()) );
					InitGraph.AttachNode(x);
					InitGraph.AttachNode(y);
					InitGraph.AddEdge(e);
				}

				FileIn.close();
			} catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
			System.out.println("File does not exist: " + args[1]);
                        return;
			}

			MSTFibonacci mstfib = new MSTFibonacci(InitGraph);
			finalMST = mstfib.returnMST();
			System.out.println(mstfib.getTotalCost());
			outputMST(finalMST);
		}
		else{
			System.out.println("Please choose the given choices only -");
			System.out.println("mst -r v d (for random mode. v and d have to be integers)");
			System.out.println("mst -s filename (for simple scheme. File should Exist)");
			System.out.println("mst -f filename (for fibonacci scheme. File should Exist)");
		}


	}

	public static void outputMST(ArrayList<Edge> mst)
	{
		if(mst != null)
		{
			for(Edge e : mst)
	    	{
	    		System.out.println(e.getNodeX().getNodeID() +" "
	    				+e.getNodeY().getNodeID());
	    	}


		}
		else
			System.out.println();

	}

	public static void printMST(ArrayList<Edge> mst)
	{
		if(mst != null)
		{
			for(Edge e : mst)
	    	{
	    		System.out.print(e.getNodeX().getNodeID()+ " "
	    				+e.getNodeY().getNodeID()+"\n");
	    	}


		}
		else
                //practically this should never exist. This will be touched if no MST exists
			System.out.println("Error");

	}

}