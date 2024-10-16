/**
 * @author Data Structures. ETSI Informática. UMA.
 *
 * Undirected graph implemented with a Dictionary from vertices (sources) to another
 * dictionary from vertices (destinations) to weights
 */

package dataStructures.graph;

import java.util.Iterator;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;

import dataStructures.set.Set;
import dataStructures.set.HashSet;
import dataStructures.tuple.Tuple2;

public class DictionaryWeightedGraph<V, W extends Comparable<? super W>> implements WeightedGraph<V, W> {

    static class WE<V1, W1 extends Comparable<? super W1>> implements WeightedEdge<V1, W1> {
        V1 src, dst;
        W1 wght;

        WE(V1 s, V1 d, W1 w) {
            src = s;
            dst = d;
            wght = w;
        }

        @Override
        public V1 source() {
            return src;
        }

        @Override
        public V1 destination() {
            return dst;
        }

        @Override
        public W1 weight() {
            return wght;
        }

        public boolean equals(Object o) {
            if (!(o instanceof WeightedEdge<?, ?>))
                return false;
            else {
                WeightedEdge<V1, W1> we = (WeightedEdge<V1, W1>) o;

                return (src.equals(we.source()) && dst.equals(we.destination())
                        || src.equals(we.destination()) && dst.equals(we.source())
                ) && wght.equals(we.weight());
            }
        }

		public int hashCode() { // must return same code for to equal edges
			int hash = 17;
			hash = 31*hash + src.hashCode() + dst.hashCode();
			hash = 31*hash + wght.hashCode();
			return hash;
		}

        public String toString() {
            return "WE(" + src + "," + dst + "," + wght + ")";
        }

        @Override
        public int compareTo(WeightedEdge<V1, W1> we) {
            return wght.compareTo(we.weight());
        }
    }

    /**
     * Each vertex is associated to a dictionary containing associations
     * from each successor to its weight
     */
    protected Dictionary<V, Dictionary<V, W>> graph;

    public DictionaryWeightedGraph() {
        graph = new HashDictionary<>();
    }


    public void addVertex(V v) {
        if (!graph.isDefinedAt(v))
            graph.insert(v, new HashDictionary<>());
    }

    public void addEdge(V src, V dst, W w) {
        Dictionary<V, W> dSrc = graph.valueOf(src);
        if (dSrc == null)
            throw new GraphException("addEdge. vertex " + src + " is not in graph");

        Dictionary<V, W> dDst = graph.valueOf(dst);
        if (dDst == null)
            throw new GraphException("addEdge. vertex " + dst + " is not in graph");

        dSrc.insert(dst, w);
        dDst.insert(src, w);
    }

    public Set<Tuple2<V, W>> successors(V v) {
		Dictionary<V, W> dict = graph.valueOf(v);
        if (dict == null)
            throw new GraphException("successors. vertex " + v + " is not in graph");
		
        Set<Tuple2<V, W>> succs = new HashSet<>();
        for (Tuple2<V, W> t : dict.keysValues())
            succs.insert(t);
        return succs;
    }

    public Set<V> vertices() {
        Set<V> vs = new HashSet<>();
        for (V v : graph.keys())
            vs.insert(v);
        return vs;
    }


    public Set<WeightedEdge<V, W>> edges() {
        Set<WeightedEdge<V, W>> wes = new HashSet<>();
        for (Tuple2<V, Dictionary<V, W>> t1 : graph.keysValues())
            for (Tuple2<V, W> t2 : t1._2().keysValues())
                wes.insert(new WE<>(t1._1(), t2._1(), t2._2()));
        return wes;
    }






    /** DON'T EDIT ANYTHING BELOW THIS COMMENT **/


    public boolean isEmpty() {
        return graph.isEmpty();
    }

    public int numVertices() {
        return graph.size();
    }


    public int numEdges() {
        int num = 0;
        for (Dictionary<V, W> d : graph.values())
            num += d.size();
        return num / 2;
    }


    public String toString() {
        String className = getClass().getSimpleName();
        String s = className + "(vertices=(";

        Iterator<V> it1 = vertices().iterator();
        while (it1.hasNext())
            s += it1.next() + (it1.hasNext() ? ", " : "");
        s += ")";

        s += ", edges=(";
        Iterator<WeightedEdge<V, W>> it2 = edges().iterator();
        while (it2.hasNext())
            s += it2.next() + (it2.hasNext() ? ", " : "");
        s += "))";

        return s;
    }
}
