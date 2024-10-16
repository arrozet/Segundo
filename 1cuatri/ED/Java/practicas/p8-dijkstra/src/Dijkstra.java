/*
 * @author Data Structures, Grado en Informática. UMA.
 *
 * Dijkstra's algorithm for computing shortest paths in a weighted graph
 */

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.graph.DictionaryWeightedGraph;
import dataStructures.graph.WeightedGraph;
import dataStructures.list.LinkedList;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
import dataStructures.priorityQueue.LinkedPriorityQueue;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.set.HashSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

public class Dijkstra {

  public static <V> Dictionary<V, Integer> dijkstra(WeightedGraph<V, Integer> g, V src) {
    class Extension implements Comparable<Extension> {
      final V src;
      final V dst;
      final Integer totalCost;

      Extension(V src, V dst, Integer totalCost) {
        this.src = src;
        this.dst = dst;
        this.totalCost = totalCost;
      }

      @Override
      // min extension is the one with smallest total cost
      public int compareTo(Extension that) {
        return this.totalCost.compareTo(that.totalCost);
      }
    }


    Set<V> verticesRestantes = g.vertices(); // nodos a considerar
    verticesRestantes.delete(src); // el src ya esta en la solucion

    Set<V> vOpt = new HashSet<>();
    vOpt.insert(src); // condicion enunciado
    Dictionary<V,Integer> result = new HashDictionary<>();
    result.insert(src,0); // meto en mi solucion src, con valor 0, pues parto desde ahí

    while(!verticesRestantes.isEmpty()){
      PriorityQueue<Extension> caminos = new LinkedPriorityQueue<>(); // para poder escoger el camino con menor coste
      for(V v : vOpt){
        Set<Tuple2<V,Integer>> suc = g.successors(v); // FALTABA ASEGURAR QUE LOS VERTICES SUCESORES AUN NO SE HAN CONSIDERADO
        for(Tuple2<V,Integer> newEdge : suc){
          //                        desde v hasta uno de los sucesores de v -> el camino es lo que llevo hasta ahora mas lo que pesa el nuevo vertice
          if(verticesRestantes.isElem(newEdge._1())){  // el sucesor aun no se ha considerado
            Extension ext = new Extension(v, newEdge._1(), result.valueOf(v) + newEdge._2());
            caminos.enqueue(ext);
          }

        }

      }
      Extension extMin = caminos.first();
      // no hace falta hacer el dequeue pq se va a borrar la estructura entera despues
      verticesRestantes.delete(extMin.dst);
      vOpt.insert(extMin.dst);
      result.insert(extMin.dst, extMin.totalCost);
    }



    // todo
    return result;
  }

  public static WeightedGraph<Character, Integer> sampleGraph() {
    WeightedGraph<Character, Integer> g = new DictionaryWeightedGraph<>();
    g.addVertex('a');
    g.addVertex('b');
    g.addVertex('c');
    g.addVertex('d');
    g.addVertex('e');

    g.addEdge('a', 'b', 3);
    g.addEdge('a', 'd', 7);
    g.addEdge('b', 'd', 2);
    g.addEdge('b', 'c', 4);
    g.addEdge('c', 'e', 6);
    g.addEdge('d', 'c', 5);
    g.addEdge('d', 'e', 4); // en el grafo del enunciado es 4, no 5

    return g;
  }

  public static void main(String[] args) {
    WeightedGraph<Character, Integer> graph = sampleGraph();
    Character src = 'a';

    Dictionary<Character, Integer> dict = dijkstra(graph, src);
    System.out.printf("Costs of shortest paths from vertex %s are:\n%s\n", src, dict);
  }
}
