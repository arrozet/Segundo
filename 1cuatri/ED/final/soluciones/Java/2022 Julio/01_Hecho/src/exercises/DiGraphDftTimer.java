/* ------------------------------------------------------------------------------
   -- Student's name:
   -- Student's group:
   -- Identity number (DNI if Spanish/passport if Erasmus):
   --
   -- Data Structures. Grado en Informática. UMA.
   -------------------------------------------------------------------------------
*/

package exercises;

import dataStructures.dictionary.Dictionary;
import dataStructures.graph.DiGraph;
import dataStructures.graph.DictionaryDiGraph;
import dataStructures.set.Set;

public class DiGraphDftTimer<V> {
  
  private int time;
  private Dictionary<V, Integer> arrivalD, departureD;
  private Set<V> visited;

// -- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
// -- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
// -- EXERCISE 3

  public DiGraphDftTimer(DiGraph<V> diGraphg) {
    // TODO
  }

  public int arrivalTime(V v) {
    return arrivalD.valueOf(v);
  }

  public int departureTime(V v) {
    return departureD.valueOf(v);
  }
}
