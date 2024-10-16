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

public class Kruskal {
	public static <V,W> Set<WeightedEdge<V,W>> kruskal(WeightedGraph<V,W> g) {
		Set<WeightedEdge<V,W>> sol = new HashSet<>();
		PriorityQueue<WeightedEdge<V,W>> pq = new LinkedPriorityQueue<>();
		Dictionary<V,V> dict = new HashDictionary<>();

		// metemos vertices a diccionario (inicializamos dicts)
		for(V v : g.vertices()){
			dict.insert(v,v);
		}
		System.out.println("\n" + dict);

		// metemos aristas ordenadas por peso de menor a mayor en pq
		for(WeightedEdge<V,W> edge : g.edges()){
			pq.enqueue(edge);
		}
		System.out.println(pq+ "\n");

		while(!pq.isEmpty()){
			WeightedEdge<V,W> aristaPosible = pq.first();
			V src = aristaPosible.source();
			V dst = aristaPosible.destination();
			V rep_src = representante(src,dict);
			V rep_dst = representante(dst,dict);

			if(!(rep_src.equals(rep_dst))){
				//dict.insert(src, rep_dst);
				dict.insert(rep_dst,src);
				sol.insert(aristaPosible);
			}
			pq.dequeue();
		}

		
		return sol;
	}

	private static <V> V representante(V v, Dictionary<V,V> dict){
		while(!v.equals(dict.valueOf(v))){
			v = dict.valueOf(v);
		}
		return v;
	}

	// Sólo para evaluación continua / only for part time students
	public static <V,W> Set<Set<WeightedEdge<V,W>>> kruskals(WeightedGraph<V,W> g) {

		// COMPLETAR
		
		return null;
	}
}
