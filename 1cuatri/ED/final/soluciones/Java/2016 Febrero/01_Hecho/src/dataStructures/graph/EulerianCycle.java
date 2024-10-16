/**
 * Student's name:
 * Student's group:
 *
 * Data Structures. Grado en Informática. UMA.
 */

package dataStructures.graph;

import dataStructures.list.*;

public class EulerianCycle<V> {
    private List<V> eCycle;

    @SuppressWarnings("unchecked")
    public EulerianCycle(Graph<V> g) {
        Graph<V> graph = (Graph<V>) g.clone();
        eCycle = eulerianCycle(graph);
    }

    public boolean isEulerian() {
        return eCycle != null;
    }

    public List<V> eulerianCycle() {
        return eCycle;
    }

    // J.1
    private static <V> boolean isEulerian(Graph<V> g) {
        // TO DO
        return true;
    }

    // J.2
    private static <V> void remove(Graph<V> g, V v, V u) {
        // TO DO
    }

    // J.3
    private static <V> List<V> extractCycle(Graph<V> g, V v0) {
        // TO DO
        return null;
    }

    // J.4
    private static <V> void connectCycles(List<V> xs, List<V> ys) {
    		// TO DO
    }

    // J.5
    private static <V> V vertexInCommon(Graph<V> g, List<V> cycle) {
    		// TO DO
    		return null;
    }

    // J.6
    private static <V> List<V> eulerianCycle(Graph<V> g) {
    		// TO DO
        return null;
    }
}
