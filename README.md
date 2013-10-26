PRIMS_ALGO_USING_ADJACENCY_LISTS_AND_FIBONACCI_HEAP
===================================================

Implementation of Prims Algorithm using Array Adjacency List and Fibonacci Heap on graphs using Random Graph Generator
The methods invoked are as follows:
1)	If the input is –r  the randomGraphGenerate method is called to create the random Graph
2)	If the input is –s the MSTBasic Method is called to check the prims algorithm using the basic array representation of the adjacency lists
3)	If the input is –f the MSTFibonacci method is called to check the prims algorithm using the Fibonacci heap generated for the given graph.

The time is calculated as instructed in the requirement i.e. by checking the start time of the algorithm and substracting it by the end time of the algorithm.

Expected Result:
The Prims algorithm was run on various random graphs generated using the random graph generate option. It was expected that the for smaller number of nodes both the algorithms should take approximately the same amount of time. But as the number of edges and the density is increased there should be a substantial time difference as the array list representation should take O(N(squared)) time where N = number of Nodes. Further the Fibonacci representation should take substantially lesser amount of time as the running time of the algorithm is logarithmic as compared to the array representation of the array representation

Results:
The program ran in logarithmic time for the fibonacci heap implementation and in polynomial time O(n(squared)) for the array adjacency list implementation as expected.
 

As is expected the Fibonacci heap took a lot lesser amount of time compared to the array representation and the time is logarithmic as compared to the array representation.

Usage:

$java mst -r v d
here v and d depict number of vertices and density resppectively. this will create a random graph based on the number of vertices and density provided. Based on this graph the fibonacci heap and the array adjacency list representation was run and the timewas computed for both the algorithms.
