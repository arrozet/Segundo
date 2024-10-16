/**
 * Student's name:
 * Student's group:
 *
 * Data Structures. Grado en Informática. UMA.
 */

package dataStructures.graph;

import dataStructures.list.*;
import dataStructures.set.Set;

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

        boolean euleriano = true;
        Set<V> vertices = g.vertices();


        for(V x : vertices)
        {
            if(g.successors(x).size() % 2 == 0)
            {

            } else
            {
                return false;
            }
        }

        if(g.isEmpty())
        {
            return true;
        }

        if(vertices.size() == 1)
        {
            return true;
        }


        return euleriano;
    }

    // J.2
    private static  <V> void remove(Graph<V> g, V v, V u) {
        // TO DO

        g.deleteEdge(v, u);

        if(g.successors(v).size() == 0)
        {
            g.deleteVertex(v);
        }

        if(g.successors(u).size() == 0)
        {
            g.deleteVertex(u);
        }

    }

    // J.3
    private static <V> List<V> extractCycle(Graph<V> g, V v0) {
        // TO DO

        List<V> l = new ArrayList<>();
        V u = null;
        l.append(v0);
        int i = 0;

        while(u != l.get(0)) {
            u = null;
            Set<V> sucesores = g.successors(l.get(i));

            for (V x : sucesores) {
                if (u == null) {
                    u = x;
                    remove(g, l.get(i), u);
                }
            }

            l.append(u);
            i++;
        }

        return l;
    }

    // J.4
    private static <V> void connectCycles(List<V> xs, List<V> ys) {
    		// TO DO

        boolean encontrado = false;

        for(int i = 0; i<xs.size(); i++)
        {
            if(encontrado == false)
            {
                if (xs.get(i) == ys.get(0))
                {
                    xs.remove(i);
                    for(int j = ys.size()-1; j>=0; j--)
                    {
                        xs.insert(i, ys.get(j));
                    }

                    encontrado = true;
                }
            }

        }

    }

    // J.5
    private static <V> V vertexInCommon(Graph<V> g, List<V> cycle) {
    		// TO DO

        Set<V> vertices = g.vertices();
        V verticeComun = null;

        for(V x : vertices)
        {
            if(verticeComun == null)
            {
                for(V y : cycle)
                {
                    if (verticeComun == null && x == y)
                    {
                        verticeComun = x;
                    }
                }
            }
        }

    	return verticeComun;
    }

    // J.6
    private static <V> List<V> eulerianCycle(Graph<V> g) {
    		// TO DO

        if(!isEulerian(g))
        {
            return null;
        } else {

            List<V> l = new ArrayList<>();
            List<V> l2 = new ArrayList<>();
            Set<V> vertices = g.vertices();
            V v = null;

            for (V x : vertices)
            {
                if (v == null)
                {
                    v = x;
                }
            }

            l = extractCycle(g,v);

            while(!g.isEmpty())
            {
                vertices = g.vertices();
                v = vertexInCommon(g, l);

                l2 = extractCycle(g,v);

                connectCycles(l,l2);

            }

            return l;
        }


    }
}
