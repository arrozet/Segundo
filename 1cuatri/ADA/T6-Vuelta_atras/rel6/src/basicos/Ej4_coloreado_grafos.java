package basicos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//********************************************************************
//EJERCICIO 4 - COLOREADO
//********************************************************************
//- ESTRUCTURA DE LA SOLUCIÓN: lista S=[s_1,...,s_k], k <= n=|V|, s_i es el color asignado al vértice i.
//Estado inicial: S=[]
//
//- POLÍTICA DE RAMIFICACIÓN: sea la solución parcial S=[s_1,...,s_i], ¿s_i+1? 
//	* RAMAS POSIBLES: s_i+1 € {1,...,k} -> s_i+1 € {1,...,min(i+1,k)} (optimización -> en el nodo i, no se mirarán más de i posibilidades)
//	* VALIDEZ: 
//			# s_i+1 no coincide con el color de los vértices adyacentes
//			# Para todo 1<=j<=i: adyacentes(i,j) => s_i != s_j
//
//- FUNCIÓN DE TERMINACIÓN: S.length = n
//
//- FUNCIÓN OBJETIVO: max(1<=j<=n) {s_j} <- minimizar
//
//- FUNCIÓN DE COTA:
//
//- ENTRADA: 
//	# Grafo G(V,E)
//	# Nº de colores: k


public class Ej4_coloreado_grafos {
	
	public static boolean colorear(Grafo G, int k) {
		List<Integer> s = new ArrayList<>();
		s.add(1);
		return colorear(G, k,s);
	}
	
	private static boolean colorear(Grafo g, int k, List<Integer> sol) {
		boolean haySol = sol.size()==g.nodos().size();
		int opcion = 1;
		while(!haySol && opcion<=k) {
			if(valida(g,sol,opcion)) {
				sol.add(opcion);
				haySol = colorear(g, k, sol);
				if(!haySol) {
					sol.remove(sol.size()-1);
				}
			}
			opcion++;
		}
		
		return haySol;
	}

// BUSCAR EL MÍNIMO
	public static boolean colorearOpt(Grafo G, int k) {
		List<Integer> s = new ArrayList<>();
		List<Integer> mejor = null;
		s.add(1);
		return colorear(G, k,s);
	}
	
	private static List<Integer> colorearOpt(Grafo g, int k, List<Integer> sol, List<Integer> mejor) {
		if(sol.size()==g.nodos().size()) {
			if(mejor==null) {
				mejor = new ArrayList<>();
			}
			if(calidad(sol)<calidad(mejor)) {
				mejor.clear();
				mejor.addAll(sol);
			}
		}
		
		int opcion = 1;
		while(opcion<=k) {
			if(valida(g,sol,opcion)) {
				sol.add(opcion);
				mejor = colorearOpt(g, k, sol,mejor);
				sol.remove(sol.size()-1);
			}
			opcion++;
		}
		
		return mejor;
	}
	
	private static int calidad(List<Integer> sol) {
		Set<Integer> s = new HashSet<>();
		s.addAll(sol);
		
		// numero de elementos distintos que hay en la coloración
		return s.size();
	}
	
	private static boolean valida(Grafo g, List<Integer> sol, int color) {
		boolean valida = true;
		
		int i=0;
		while(valida && i<sol.size()) {
			// que no sean la vez adyacentes e iguales los colores con respecto el nodo a meter
			valida = !g.adyacentes(i,sol.size()) || color!=sol.get(i);
			//			no he implementado adyacentes, hay que hacerlo
			i++;
		}
		
		return valida;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
