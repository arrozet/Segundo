/** ------------------------------------------------------------------------------
  * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
  *
  * Control del día 4-9-2013
  * 
  * Diámetro de un grafo conexo 
  *
  * (completa y sustituye los siguientes datos)
  * Titulación: Grado en Ingeniería del Software [Informática | del Software | de Computadores].
  *
  * Alumno: Oliva Zamora, Rubén
  *
  * -------------------------------------------------------------------------------
  */

import dataStructures.graph.BreadthFirstTraversal;
import dataStructures.graph.Graph;
import dataStructures.set.AVLSet;
import dataStructures.set.Set;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GraphUtil {

	/**
	 * LENGTH: Calcula el número de elementos que contiene un iterable
	 * 
	 * @param it  El iterador
	 * @return   Número de elementos en el iterador
	 */
	public static <T> int length(Iterable<T> it) {
		int cont = 0;
		Iterator<T> it2 = it.iterator();
		while(it2.hasNext()){
			cont++;
			it2.next();
		}
	    return cont;
	}

	/**
	 * ECCENTRICITY: Calcula la excentricidad de un vértice en un grafo El algoritmo toma la
	 * longitud del camino máximo en un recorrido en profundidad del grafo
	 * comenzando en el vértice dado.
	 * 
	 * @param graph    Grafo
	 * @param v        Vértice del grafo
	 * @return         Excentricidad del vértice
	 */
	public static <T> int eccentricity(Graph<T> graph, T v) {
		BreadthFirstTraversal<T> bft = new BreadthFirstTraversal<>(graph, v);

		Iterator<Iterable<T>> it2 = bft.pathsIterator();
		int max = 0;
		while(it2.hasNext()){
			int aux = length(it2.next()) - 1;
			if(max<aux){
				max = aux;
			}
		}

	    return max;
	}

	/**
	 * DIAMETER: Se define como la máxima excentricidad de los vértices del grafo.
	 * 
	 * @param graph
	 * @return
	 */

	public static <T> int diameter(Graph<T> graph) {
		Set<T> ver = graph.vertices();
		int max = 0;
		Iterator<T> it = ver.iterator();
		while(it.hasNext()) {
			int aux = eccentricity(graph, it.next());	// O(n^3)
			if (max < aux){
				max = aux;
			}
		}

	    return max;
	}
	
	/** 
	 * Estima y justifica la complejidad del método diameter
	 * O(n^3), pues en su bucle más complejo llama a una función que tiene complejidad O(n^2), pues
	 * se trata de un bucle que llama a length, con complejidad O(n)
	 */
}
