package basicos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReyesMagos {
	
	// datos de entrada:
	// lista M con el número de unidades disponibles de cada regalo
	// lista P con una lista de referencia por cada persona
	
	public static List<Integer> repartirRegalosVA(List<Integer> unidades, List<List<Integer>> preferencias) {
		List<Integer> sol = new ArrayList<Integer>();
		return repartirRegalosVA(unidades,preferencias,sol) ? sol : null;
	}

	private static boolean repartirRegalosVA(List<Integer> unidades, List<List<Integer>> preferencias, List<Integer> sol) {
		boolean haySol = false;
		if(sol.size() == preferencias.size()) {	// hay un regalo para cada uno
			haySol = true;
		}
		else {
			int regalo = 0;
			while(!haySol && regalo < unidades.size()) {
				if (valido(regalo+1,unidades,preferencias,sol)) {
					sol.add(regalo+1);
					haySol = repartirRegalosVA(unidades, preferencias, sol);
					if(!haySol) {
						sol.remove(sol.size()-1);
					}
				}
				regalo++;
			}
		}
		return haySol;
	}

	private static boolean valido(int regalo, List<Integer> unidades, List<List<Integer>> preferencias,List<Integer> sol) {
		int consumidos = 0;
		for(int elem:sol) {
			if(elem==regalo) {
				consumidos++;
			}
		}
		
		return consumidos<unidades.get(regalo-1) && preferencias.get(sol.size()).contains(regalo);
	}
	
	public static void main(String[] args) {
		Integer[] m = {2,1,2};
		List<Integer> unidades = Arrays.asList(m);
		Integer[][] p = {{1,2},{1,2,3},{2,3},{1},{2}};
		List<List<Integer>> preferencias = new ArrayList<>();
		for(Integer[] pref : p) {
			preferencias.add(Arrays.asList(pref));
		}
		System.out.println(repartirRegalosVA(unidades,preferencias));
	}
	
	
}
