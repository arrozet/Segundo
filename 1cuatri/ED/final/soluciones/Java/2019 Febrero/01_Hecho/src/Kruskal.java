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

import dataStructures.graph.DictionaryWeightedGraph;
import dataStructures.graph.WeightedGraph;
import dataStructures.graph.WeightedGraph.WeightedEdge;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.priorityQueue.LinkedPriorityQueue;
import dataStructures.set.Set;
import dataStructures.set.HashSet;
import dataStructures.tuple.Tuple2;

public class Kruskal {
	public static <V,W> Set<WeightedEdge<V,W>> kruskal(WeightedGraph<V,W> g) {

		// COMPLETAR


		Set<WeightedEdge<V,W>> PQmal = g.edges();
		PriorityQueue<WeightedEdge<V,W>> PQ = new LinkedPriorityQueue<>();

		PQmal.forEach(x -> PQ.enqueue(x));

		Dictionary<V,V> DICT = new HashDictionary<>();

		for(V x : g.vertices())
		{
			DICT.insert(x, x);
		}

		Set<WeightedEdge<V,W>> T = new HashSet<>();

		while(!PQ.isEmpty())
		{
			WeightedEdge<V,W> w = PQ.first();
			PQ.dequeue();

			if(!representante(w.source(), DICT).equals(representante(w.destination(), DICT)))
			{
				V aux = representante(w.destination(), DICT);
				DICT.delete(aux);
				DICT.insert(aux, w.source());

				T.insert(w);
			}

		}

		return T;
	}

	private static <V> V representante(V v, Dictionary<V, V> DICT)
	{
		V key = v;
		V val = DICT.valueOf(key);

		while(!key.equals(val))
		{
			key = val;
			val = DICT.valueOf(key);
		}

		return key;
	}

	// Sólo para evaluación continua / only for part time students
	public static <V,W> Set<Set<WeightedEdge<V,W>>> kruskals(WeightedGraph<V,W> g) {

		// COMPLETAR

		return null;
	}
}
