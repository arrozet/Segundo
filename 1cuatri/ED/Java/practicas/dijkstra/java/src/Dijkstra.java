/*
 * @author Data Structures, Grado en Informática. UMA.
 *
 * Dijkstra's algorithm for computing shortest paths in a weighted graph
 */

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.graph.DictionaryWeightedGraph;
import dataStructures.graph.WeightedGraph;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
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

    // todo
    return null;
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
    g.addEdge('d', 'e', 5);

    return g;
  }

  public static void main(String[] args) {
    WeightedGraph<Character, Integer> graph = sampleGraph();
    Character src = 'a';

    Dictionary<Character, Integer> dict = dijkstra(graph, src);
    System.out.printf("Costs of shortest paths from vertex %s are:\n%s\n", src, dict);
  }
}
