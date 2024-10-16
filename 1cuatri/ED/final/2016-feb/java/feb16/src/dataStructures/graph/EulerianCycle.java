/**
 * Student's name:
 * Student's group:
 *
 * Data Structures. Grado en Informática. UMA.
 */

package dataStructures.graph;

import dataStructures.list.*;

import java.util.Iterator;

public class EulerianCycle<V> {
    private List<V> eCycle;

    @SuppressWarnings("unchecked")
    public EulerianCycle(Graph<V> g) {
        try{
            Graph<V> graph = (Graph<V>) g.clone();
            eCycle = eulerianCycle(graph);
        }
        catch(GraphException e) {
            System.err.println(e.getMessage());
        }

    }

    public boolean isEulerian() {
        return eCycle != null;
    }

    public List<V> eulerianCycle() {
        return eCycle;
    }

    // J.1
    private static <V> boolean isEulerian(Graph<V> g) {
        for(V v : g.vertices()){
            if(g.degree(v) % 2 != 0){
                return false;
            }
        }
        return true;
    }

    // J.2
    private static <V> void remove(Graph<V> g, V v, V u) {
        g.deleteEdge(v,u);
        for(V x : g.vertices()){
            if(g.degree(x)==0){
                g.deleteVertex(x);
            }
        }
    }

    // J.3
    private static <V> List<V> extractCycle(Graph<V> g, V v0) {
        List<V> cycle = new LinkedList<>();
        cycle.append(v0);

        return extractCycle(g, cycle);
    }

    private static <V> List<V> extractCycle(Graph<V> g, List<V> cycle) {
        if(cycle.size() > 1 && cycle.get(0) == cycle.get(cycle.size()-1)){
            return cycle;
        }
        else{
            V v = cycle.get(cycle.size()-1);
            Iterator<V> it = g.successors(v).iterator();
            V u = it.next();
            cycle.append(u);
            remove(g,v,u);
            return extractCycle(g,cycle);
        }
    }

    // J.4
    private static <V> void connectCycles(List<V> xs, List<V> ys) {
        boolean metidos = false;

        for(int i=0; i<xs.size() && !metidos; i++){
            // cuando os encuentro
            if(xs.get(i)==ys.get(0)) {
                ys.remove(0);
                // los voy metiendo hasta que me quedo sin elementos en ys
                while (!ys.isEmpty()) {
                    xs.insert(i, ys.get(0));
                    ys.remove(0);
                }
                // y con esto acabo el bucle
                metidos = true;
            }
        }
    }


    // J.5
    private static <V> V vertexInCommon(Graph<V> g, List<V> cycle) {
        for(V v : g.vertices()){
            for(V x : cycle){
                if(v.equals(x)){
                    return v;
                }
            }
        }
        return null;
    }

    // J.6
    private static <V> List<V> eulerianCycle(Graph<V> g) {

        if(!isEulerian(g)){
            throw new GraphException("eulerianCycle: the graph is not eulerian");
        }
        else{
            Iterator<V> it = g.vertices().iterator();
            V v0 = it.next();
            List<V> c = extractCycle(g,v0);
            return eulerianCycle(g,c);
        }
    }

    private static <V> List<V> eulerianCycle(Graph<V> g, List<V> cycle) {
        if(g.isEmpty()){
            return cycle;
        }
        else{
            V v = vertexInCommon(g,cycle);
            List<V> c2 = extractCycle(g,v);
            connectCycles(cycle,c2);
            return eulerianCycle(g,cycle);
        }
    }
}
