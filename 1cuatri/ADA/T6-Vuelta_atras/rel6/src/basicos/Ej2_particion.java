package basicos;

import java.util.ArrayList;
import java.util.List;

//SATISFACCIÓN -> encontrar solo 1 solución o determinar que no existe

//********************************************************************
//EJERCICIO 2 - PARTICIÓN
//********************************************************************
//- ESTRUCTURA DE LA SOLUCIÓN: list de arraylist de integer p de dimensión a lo sumo S.length. ∀1<=i<=n: i€ℕ.
//Estado inicial: p=[]; arraylist p vacío
//
//- POLÍTICA DE RAMIFICACIÓN: tendremos un array repe de longitud U.length en el que marcaremos si el elemento correspondiente a su índice ya se está considerando en nuestra solución parcial.
//Comenzaremos añadiendo el primer subconjunto y consideraremos los siguientes. 
//Si alguno de los nuevos elementos ya estaban marcados, se descartará esa solución ya que no nos llevará a una solución válida. Se avanzará al siguiente subconjunto hasta quedarnos sin subconjuntos a considerar. 
//Si aún no hemos encontrado una solución siguiendo esta metodología, retrocederemos hasta el primer subconjunto y consideraremos la nueva posible solución descartando el primer subconjunto de S, ya que con este en la solución nunca encontraremos una válida. Seguiremos este método hasta encontrar una solución, o bien quedarnos sin subconjuntos a considerar (descartando los m-1 conjuntos posibles).
//
//- ¿VALIDEZ?: el nuevo subconjunto añadido no tiene elementos que ya hayan sido marcados.
//
//- FUNCIÓN DE TERMINACIÓN: todos los elementos de U están cubiertos en p solo 1 vez.
//
//- ENTRADA: 	U, array de elementos del conjunto.
//
//		S, list de subconjuntos de U.

public class Ej2_particion {
	
	public static boolean particion(Integer[] u, List<ArrayList<Integer>> s, List<ArrayList<Integer>> p, boolean[] repe) {
		boolean haySol=true;
		
		for(boolean elem: repe) {
			if(elem==false) {		// deben estar todos cubiertos para que la solución sea válida
				haySol=false;
			}
		}
		
		if(!haySol) {
			int c=0;
			while(!haySol && c<s.size()) {
				ArrayList<Integer> posibleSubconjunto = s.get(c);
				
				if(valido(posibleSubconjunto, repe)) {
					p.add(posibleSubconjunto);
					haySol = particion(u, s, p, repe);
					if(!haySol) {
						p.remove(p.size()-1);
					}
				}
				c++;
			}
		}
		
		return haySol;
	}
	
	private static boolean valido(ArrayList<Integer> posible, boolean[] repe) {
		boolean valido = true;
		int i = 0;
		
		while(i<repe.length && valido) {
			if(posible.contains(i+1)) {
				if(repe[i]==false) {	// si aun no estaba marcado, lo marco
					repe[i]=true;
				}
				else {		// si ya lo estaba es que ya esta en mi solucion parcial, la solucion esta no es valida
					valido=false;
				}
			}
		}
		
		
		return valido;
	}
	
	public static void main(String[] args) {
		Integer[] u = {1, 2,3,4,5,6,7,8,9,10,11, 12};
		List<ArrayList<Integer>> s = new ArrayList<ArrayList<Integer>>();
		List<ArrayList<Integer>> p = new ArrayList<ArrayList<Integer>>();
		boolean[] repe = new boolean[u.length];
		
		// rellenar s de subconjuntos
		// ...
		// ...
		
		if(particion(u, s, p, repe)) {
			System.out.println(p.toString());
		}
		else {
			System.out.println("No hay partición");
		}

	}

}
