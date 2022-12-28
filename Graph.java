import java.util.*;

public class Graph {

    //path tracker for DFS
    LinkedList<String> dfs_tracker = new LinkedList<String>();


    private class Node {
        private String id;
        LinkedList<Edge> adjacentEdges = new LinkedList<Edge>();
        private Node next;
        private boolean visited;
        //private boolean done;
        private Node parent;

        //constructor
        private Node(String id) {
            this.id = id;

        }

        //method to add edge to the node's edge list
        private void addToAdjacencyList(Edge e) {

            this.adjacentEdges.add(e);

        }

        //method to return the node's edge list
        private LinkedList<Edge> getEdgeList(){

            return this.adjacentEdges;
        }

    }

    private class Edge {
        private Node start;
        private Node end;

        private Edge next;

        private Edge(Node start, Node end) {
            this.start = start;
            this.end = end;

        }
    }

    public static void main(String args []){

        Graph preston = new Graph();
        preston.addNode("first");
        preston.addNode("second");
        preston.addNode("third");
        preston.addNode("first");
        preston.addNode("fourth");
        preston.addNode("fifth");
        preston.addNode("sixth");

        preston.addEdge("first", "second");
        preston.addEdge("first", "third");
        preston.addEdge("first", "fourth");
        preston.addEdge("third", "fourth");
        preston.addEdge("second", "fifth");
        preston.addEdge("third", "fifth");
        preston.addEdge("fifth", "sixth");

        preston.BFS("first", "sixth");
        preston.BFS("first", "sixth");
        preston.BFS("first", "first");
        preston.BFS("first", "second");
        preston.BFS("first", "tenth");
        preston.DFS("first", "tenth");
        preston.DFS("first", "sixth");

       Graph another_Graph = new Graph();
        another_Graph.addNode("A");
        another_Graph.addNode("B");
        another_Graph.addNode("C");
        another_Graph.addNode("D");
        another_Graph.addNode("E");
        another_Graph.addNode("F");

        another_Graph.addEdge("A", "B");
        another_Graph.addEdge("A", "C");
        another_Graph.addEdge("B", "D");
        another_Graph.addEdge("D", "E");
        another_Graph.addEdge("C", "E");
        another_Graph.addEdge("C", "F");

        //Testing BFS
        another_Graph.BFS("A", "E");
        //testing DFS
        another_Graph.DFS("A", "E");
        //testing DFS
        another_Graph.DFS("A", "C");

    }


    //Table to get a node based on specified id
    HashMap<String, Node> lookUpNode = new HashMap<String, Node>();


    //Adds nodes to graph if possible
    public boolean addNode(String id) {


        if (lookUpNode.containsKey(id) == true) {

            return false;

        } else {
            Node newNode = new Node(id);

            lookUpNode.put(id, newNode);



        }

        return true;
    }

    //Adds edges to the graph if possible
    public boolean addEdge(String from, String to) {

        //If one node does not exist return false
        if (lookUpNode.containsKey(from) == false || lookUpNode.containsKey(to) == false) {

            return false;

        }

        //checks if the edge exists and if it does returns false
        for (Edge i : lookUpNode.get(from).adjacentEdges) {

            if (i.start.id.equals(from) && i.end.id.equals(to)) {

                return false;

            }

            else if (i.start.id.equals(to) && i.end.id.equals(from)) {

                return false;

            }

        }
        //Adding edge in both directions
        Edge newEdge = new Edge(lookUpNode.get(from), lookUpNode.get((to)));

        Edge newEdgeReverse = new Edge(lookUpNode.get(to), lookUpNode.get(from));


        lookUpNode.get(from).addToAdjacencyList(newEdge);

        lookUpNode.get((to)).addToAdjacencyList(newEdgeReverse);


        return true;
    }

    //Breadth First Search
    public void BFS(String start, String end) {

        if (!lookUpNode.containsKey(start) || !lookUpNode.containsKey(end)) {

            return;
        }

        //queue to add nodes to for the traversal
        Queue<Node> queue = new LinkedList<>();

        //add the starting node to the queue
        queue.add(this.lookUpNode.get(start));

        while (!queue.isEmpty()) {

            Node theNode = queue.remove();

            theNode.visited = true;

            //get the adjacent nodes
            theNode.adjacentEdges.forEach(edge -> {

                //add node if not visited
                if (edge.end.visited == false) {

                    edge.end.parent = theNode;
                    queue.add(edge.end);
                    edge.end.visited = true;

                }
            });

        }

        //Path traversed from start to end nodes
        LinkedList<String> pathList = new LinkedList<String>();

        if (lookUpNode.get(end).visited == true) {

            Node ptr = lookUpNode.get(end);
            pathList.addFirst(ptr.id);

            while (ptr.parent != null) {

                ptr = ptr.parent;
                pathList.addFirst(ptr.id);

            }
        }

        //reset all visited and parent attributes
        for (Node node : lookUpNode.values()) {

            node.visited = false;
            node.parent = null;
        }

        System.out.println(pathList);
    }

    public void helper_DFS(String start, String end) {

        if (!lookUpNode.containsKey(start) || !lookUpNode.containsKey(end)) {

            return;
        }

        lookUpNode.get(start).visited = true;
        dfs_tracker.add(start);
        //get the adjacent nodes
        lookUpNode.get(start).adjacentEdges.forEach(edge -> {

            //cpntinue searching if node not visited
            if (edge.end.visited == false) {

              this.helper_DFS(edge.end.id, end);

            }
        });

    }

    //recursive DFS
    public void DFS(String start, String end) {

        if (!lookUpNode.containsKey(start) || !lookUpNode.containsKey(end)) {

            return;
        }

        this.helper_DFS(start, end);

        for (Node node : lookUpNode.values()) {

            node.visited = false;
            node.parent = null;
        }

        //Removes excess of list of path if present
        LinkedList<String> copy = new LinkedList<String>();
        int index = 0;
        for (int i = 0; i < dfs_tracker.size(); i++) {
            copy.add(dfs_tracker.get(i));
            if (dfs_tracker.get(i).equals(end)) {
                break;
            }
        }

        System.out.println(copy);
        dfs_tracker.clear();
    }

}








