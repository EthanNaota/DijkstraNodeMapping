import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;

import java.util.ArrayList;
import java.util.List;


public class Dijkstra {

    public static void main(String[] args) {
        Graph<String, MyNode> graph = new DirectedSparseGraph<>();

        //set the graph as the example in class
        graph.addVertex("S");
        graph.addEdge(new MyNode(4), "S", "T");
        graph.addEdge(new MyNode(7), "S", "A");
        graph.addEdge(new MyNode(1), "S", "B");
        graph.addEdge(new MyNode(3), "S", "N");

        graph.addEdge(new MyNode(1), "T", "A");

        graph.addEdge(new MyNode(6), "A", "X");

        graph.addEdge(new MyNode(12), "B", "N");
        graph.addEdge(new MyNode(2), "B", "L");

        graph.addEdge(new MyNode(5), "N", "X");

        graph.addEdge(new MyNode(3), "L", "X");

        //execute Dijkstra
        exeDijkstra("S", graph);

    }

    public static void exeDijkstra(String start, Graph<String, MyNode> graph){
        if(graph.getOutEdges(start) == null){
            System.out.println("Can not complete");
        } else{
            GTable record = new GTable();
            List<String> toDo = new ArrayList<>();
            List<String> done = new ArrayList<>();

            // add starting point to done
            done.add(start);

            // add all other points to toDo and remove starting point
            toDo.addAll(graph.getVertices());
            toDo.remove(start);

            // add all vertex to the table
            record.vertex.addAll(toDo);

            // set up table with all edge values and pred
            for(int i = 0; i < toDo.size(); i++){
                if(graph.findEdge(start, record.vertex.get(i)) == null){
                    record.distance.add(i, ((int) Double.POSITIVE_INFINITY));
                    record.pred.add(start);
                } else {
                    record.distance.add(i, graph.findEdge(start, record.vertex.get(i)).id);
                    record.pred.add(start);
                }
            }

            record.display(); //test

            while(!toDo.isEmpty()){

                String smallest = toDo.get(0);

                for(int i = 0; i < toDo.size(); i++){
                    if(record.distance.get(record.vertex.indexOf(smallest)) > record.distance.get(record.vertex.indexOf(toDo.get(i)))){
                        smallest = toDo.get(i);
                    }
                }

                // add the smallest distance to done
                done.add(smallest);
                toDo.remove(smallest);

                //check costs to neighboring nodes
                List<String> connections = new ArrayList<>();
                connections.addAll(graph.getSuccessors(smallest));

                System.out.println(connections.toString()); // test

                if(connections.size() > 0) {
                    for (int i = 0; i < connections.size(); i++) {
                        if((record.distance.get(record.vertex.indexOf(smallest)) + graph.findEdge(smallest, connections.get(i)).id)
                                < record.distance.get(record.vertex.indexOf(connections.get(i)))){
                            record.distance.set(record.vertex.indexOf(connections.get(i)),
                                    (record.distance.get(record.vertex.indexOf(smallest)) +
                                            graph.findEdge(smallest, connections.get(i)).id));
                            record.pred.set(record.vertex.indexOf(connections.get(i)), smallest);
                        }
                    }
                }

                record.display();
                //System.exit(0);
            }
        }
    }

    // -------------------
    public static class MyNode {
        int id; // good coding practice would have this as private

        public MyNode(int id) {
            this.id = id;
        }

        public String toString() { // Always a good idea for debuging
            return "V"+id;
        }
    }

    // -------------------
    public static class GTable{
        public ArrayList<String> vertex;
        public ArrayList<Integer> distance;
        public ArrayList<String> pred;

        public GTable(){
            vertex = new ArrayList<>();
            distance = new ArrayList<>();
            pred = new ArrayList<>();
        }

        public void display() {
            if(vertex.size() != distance.size() && distance.size() != pred.size()){
                System.out.println("Something is Wrong!");
            } else {
                System.out.println("---------------------------------------------------");
                System.out.printf("%10s %10s %10s", "VERTEX", "DISTANCE", "PREDECESSOR");
                System.out.println("\n---------------------------------------------------");

                for(int i = 0; i < vertex.size(); i++){
                    System.out.printf("%10s %10s %10s\n", vertex.get(i), distance.get(i), pred.get(i));
                }
            }
        }
    }


}



