/**----------------------------------------------
 * -- Estructuras de Datos.  2018/19
 * -- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
 * -- Escuela Técnica Superior de Ingeniería en Informática. UMA
 * --
 * -- Examen 4 de febrero de 2019
 * --
 * -- ALUMNO/NAME:
 * -- GRADO/STUDIES:
 * -- NÚM. MÁQUINA/MACHINE NUMBER:
 * --
 * ----------------------------------------------
 */

package dataStructures.graph;

import java.util.Iterator;
import java.util.Objects;

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

        public V1 source() {
            return src;
        }

        public V1 destination() {
            return dst;
        }

        public W1 weight() {
            return wght;
        }

        public String toString() {
            return "WE(" + src + "," + dst + "," + wght + ")";
        }

		public int hashCode() {
			
			return Objects.hash(src, dst, wght);
		}

		public boolean equals(Object obj) {
			
			if(obj instanceof DictionaryWeightedGraph.WE<?,?>){
                WE<V1,W1> edge = (WE<V1, W1>) obj;
                return src.equals(edge.source()) && dst.equals(edge.destination()) &&
                        src.equals(edge.destination()) && dst.equals(edge.source()) &&  // para considerar aristas invertidas
                        weight().equals(edge.weight());
            }
            return false;
		}
		
		public int compareTo(WeightedEdge<V1, W1> o) {
			return weight().compareTo(o.weight());
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
        if(!graph.isDefinedAt(v)){
            graph.insert(v, new HashDictionary<>());
        }

    	// COMPLETAR

    }

    public void addEdge(V src, V dst, W w) {
        if(!(graph.isDefinedAt(src) && graph.isDefinedAt(dst))){
            throw new GraphException("DictionaryWeightedGraph addEdge: vertex not in graph");

        }
        graph.valueOf(src).insert(dst, w);
        graph.valueOf(dst).insert(src,w);

    	// COMPLETAR

    }

    public Set<Tuple2<V, W>> successors(V v) {
        if(!graph.isDefinedAt(v)){
            throw new GraphException("DictionaryWeightedGraph successors: vertex not in graph");
        }
        Set<Tuple2<V,W>> s = new HashSet<>();
        Dictionary<V,W> dic = graph.valueOf(v);

//        for(V suc : dic.keys()){
//            s.insert(new Tuple2<>(suc, dic.valueOf(suc)));
//        }

        Iterator<V> it = dic.keys().iterator();
        while(it.hasNext()){
            V suc = it.next();
            s.insert(new Tuple2<>(suc, dic.valueOf(suc)));
        }

        return s;
    }


    public Set<WeightedEdge<V, W>> edges() {
        Set<WeightedEdge<V,W>> s = new HashSet<>();
//    	for(V vertex : graph.keys()){
//            Dictionary<V,W> dic = graph.valueOf(vertex);
//            for(V vertexConnected : dic.keys()){
//                W weight = dic.valueOf(vertexConnected);
//                s.insert(new WE<>(vertex, vertexConnected, weight));
//            }
//        }

        Iterator<V> it = graph.keys().iterator();
        while(it.hasNext()){
            V vertex = it.next();
            Dictionary<V,W> dic = graph.valueOf(vertex);

            Iterator<V> it2 = dic.keys().iterator();
            while(it2.hasNext()){
                V vertexConnected = it2.next();
                W weight = dic.valueOf(vertexConnected);
                s.insert(new WE<>(vertex, vertexConnected, weight));
            }
        }
    	
        return s;
    }






    /** DON'T EDIT ANYTHING BELOW THIS COMMENT **/


    public Set<V> vertices() {
        Set<V> vs = new HashSet<>();
        for (V v : graph.keys())
            vs.insert(v);
        return vs;
    }


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
//        return num;
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