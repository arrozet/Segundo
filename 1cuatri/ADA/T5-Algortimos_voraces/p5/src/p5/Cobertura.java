package p5;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Cobertura {

	private Grafo grafo;

	public Cobertura(Grafo g) {
		grafo = g;
	}

	public Set<Integer> getConjuntoCobertura() {
		Set<Integer> cobertura = new HashSet<>();
		Grafo gCopia = new Grafo(grafo);
		
		// creo una lista de nodos orednados por grado
//		List<Integer> nodosPorGrado = new ArrayList<>(gCopia.nodosConectados());
		// lo que hay entre parentesis es una expresion lambda
		// (parametro1, parametro2) -> criterio de ordenacion
//		nodosPorGrado.sort((v1, v2) -> Integer.compare(gCopia.grado(v2), gCopia.grado(v1)));



		
		// creo un array para determinar si un vertice esta o no cubierto
//		boolean[] cubierto = new boolean[gCopia.nodosConectados().size()];

		// me equivoqué e hice: el mínimo número de nodos que conecta todos los nodos, pero NO QUE CUBRE TODAS LAS ARISTAS
//		int i=0;
//		while(i<cubierto.length) {
//			if(!cubierto[i]) {
//				Integer nodo = nodosPorGrado.get(i);
//				cobertura.add(nodo);
//				cubierto[i] = true;
//				// compruebo qué nodos he cubierto y si no están cubiertos, los cubro
//				List<Integer> nuevoCubierto = new ArrayList<>(gCopia.sucesores(nodo));
//				for(int nodoPosible:nuevoCubierto) {
//					if(!cubierto[nodoPosible]) {
//						cubierto[nodoPosible] = true;
//					}
//				}
//			}
//			else {
//				i++;
//			}
//			
//		}
		
		// SOLUCIÓN QUE DA 10/10
		// lo haré ahora para cubrir todas las aristas
		while(gCopia.numAristas()>0) {
			// cojo el vertice de mayor grado
			int nodo = 0;
			int maxGrado = 0;
			for(int nodoPosible:gCopia.nodosConectados()) {
				if(gCopia.grado(nodoPosible) > maxGrado) {
					maxGrado = gCopia.grado(nodoPosible);
					nodo = nodoPosible;
				}
			}
			
			// lo añado a la cobertura
			cobertura.add(nodo);
			// quito las aristas que tiene adyacentes
			for(int conexion:gCopia.sucesores(nodo)) {
				gCopia.removeArista(nodo, conexion);
			}
			
		// SOLUCIÓN QUE DA 4,29/10
//			while(gCopia.numAristas()>0) {
//				// cojo el vertice de mayor grado
//				int nodo = nodosPorGrado.get(0);
//				
//				// lo añado a la cobertura
//				cobertura.add(nodo);
//				// quito las aristas que tiene adyacentes
//				for(int conexion:gCopia.sucesores(nodo)) {
//					gCopia.removeArista(nodo, conexion);
//				}
//			
//			// cada vez que elimino aristas, debo ordenar la lista de nuevo por los vértices con mayor grado, ya que se modifica
//			nodosPorGrado.sort((v1, v2) -> Integer.compare(gCopia.grado(v2), gCopia.grado(v1)));

		}
		
		return cobertura;
	}

}
