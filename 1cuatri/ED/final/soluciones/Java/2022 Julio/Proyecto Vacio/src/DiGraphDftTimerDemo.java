import dataStructures.graph.DiGraph;
import dataStructures.graph.DictionaryDiGraph;
import exercises.DiGraphDftTimer;

public class DiGraphDftTimerDemo {

    public static void main(String[] args) {
        DiGraph<Integer> diGraph = new DictionaryDiGraph<>();
        for (int v = 0; v <= 7; v++)
            diGraph.addVertex(v);

        diGraph.addDiEdge(0, 1);
        diGraph.addDiEdge(0, 2);

        diGraph.addDiEdge(2, 3);
        diGraph.addDiEdge(2, 4);

        diGraph.addDiEdge(3, 1);
        diGraph.addDiEdge(3, 5);

        diGraph.addDiEdge(4, 5);

        diGraph.addDiEdge(6, 7);

        DiGraphDftTimer<Integer> timer = new DiGraphDftTimer<>(diGraph);

        for (Integer v : diGraph.vertices()) {
            System.out.println("Arrival time for " + v + " is " + timer.arrivalTime(v));
            System.out.println("Departure time for " + v + " is " + timer.departureTime(v));
        }
    }
}

/*

Arrival time for 0 is 0
Departure time for 0 is 11
Arrival time for 1 is 1
Departure time for 1 is 2
Arrival time for 2 is 3
Departure time for 2 is 10
Arrival time for 3 is 4
Departure time for 3 is 7
Arrival time for 4 is 8
Departure time for 4 is 9
Arrival time for 5 is 5
Departure time for 5 is 6
Arrival time for 6 is 12
Departure time for 6 is 15
Arrival time for 7 is 13
Departure time for 7 is 14

*/
