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

		Dictionary<V,V> dict = new HashDictionary<>();
		PriorityQueue<WeightedEdge<V,W>> pq = new LinkedPriorityQueue<>();
		Set<WeightedEdge<V,W>> setPaso = g.edges();
		Set<WeightedEdge<V,W>> s = new HashSet<>();

		for (WeightedEdge<V,W> x : setPaso )
		{
			pq.enqueue(x);
		}

		for(V x : g.vertices())
		{
			dict.insert(x, x);
		}

		while(!pq.isEmpty())
		{
			WeightedEdge<V,W> we = pq.first();
			pq.dequeue();

			if(!representante(we.source(), dict).equals(representante(we.destination(), dict)))
			{
				dict.insert(representante(we.destination(), dict), we.source());
				s.insert(we);
			}

		}
		
		return s;
	}

	private static <V> V representante(V rep, Dictionary<V,V> dict) {

		for(Tuple2<V,V> x : dict.keysValues())
		{
			if (x._1().equals(rep))
			{
				if(x._1().equals(x._2()))
				{
					return x._1();
				} else
				{
					return representante(x._2(), dict);
				}
			}
		}

		return null;
	}


	// Sólo para evaluación continua / only for part time students
	public static <V,W> Set<Set<WeightedEdge<V,W>>> kruskals(WeightedGraph<V,W> g) {

		// COMPLETAR
		
		return null;
	}
}
