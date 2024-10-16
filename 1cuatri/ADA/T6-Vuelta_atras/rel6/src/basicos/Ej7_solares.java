package basicos;

import java.util.LinkedList;
import java.util.List;


//********************************************************************
//EJERCICIO 7 - SOLARES
//********************************************************************
//- ESTRUCTURA DE LA SOLUCIÓN: arraylist mejor con contenido los indices 0,1,2 para el solar A,B,C en cada índice. El índice 0 representará al banco, el 1 al hotel y el 2 al colegio.
//Estado inicial: mejor = []
//
//- POLÍTICA DE RAMIFICACIÓN: una solución parcial será prometedora si el solar añadido no está en la solución y la suma de los elementos que se lleven es menor que la suma del mejor. 
//Si es así, se explorarán las alternativas. De lo contrario, volveremos atrás.
//
//- ¿VALIDEZ?:
//	# No se pueden construir dos edificios en el mismo solar
//	# Se tienen que construir todos los edificios
//	# No se puede construir el mismo edificio dos o más veces.
//
//- FUNCIÓN DE TERMINACIÓN: cuando hayamos recorrido todas las posibilidades válidas.
//
//- ENTRADA:	tabla t con los precios de los edificios según solar en millones de euros.
//		Fila -> edificio, Columna -> solar
public class Ej7_solares {


	public static List<Integer> solares(int[][] tabla){
		List<Integer> mejor = new LinkedList<>();
		List<Integer> sol = new LinkedList<>();
		
		for(int i=0; i<tabla.length; i++) {
			mejor.add(i);
		}
		
		solares(tabla,0,sol, mejor);
		
		return mejor;
	}
	
	private static void solares(int[][] t, int edificio, List<Integer> sol, List<Integer> mejor) {
		if(edificio == t.length && sum(t,sol)<sum(t, mejor)) {
			mejor.clear();
			mejor.addAll(sol);
		}
		
		// puedo reducir los costes pasando por parámetro los resultados de las sumas
		for(int s=0; s<t.length; s++) {	// asi me evito tener en cuenta no equivocarme con los edificios
			
				if(valido(t,sol,mejor,s)) {	// si es válida
					sol.add(s);
					solares(t, edificio+1, sol, mejor);
					sol.remove(sol.size()-1);
				}
		}
}
	
	private static boolean valido(int[][] t, List<Integer> sol, List<Integer> mejor, int solar) {
													// me faltaba ESTO -> si no eso nunca se va a cumplir (estado inicial es vacio -> la suma sale Integer.MAX_VALUE)
		return !sol.contains(solar) && sum(t, sol)+ t[sol.size()][solar] < sum(t, mejor);
		//		la sol no tiene el solar			la sol parcial es prometedora
	}

	
	private static int sum(int[][] t, List<Integer> l) {
		int sum = 0;
		// Fila -> Edificio, Columna -> solar
		// l almacena los solares (contenido) y los edificios (indices)
		
		for(int i=0; i<l.size(); i++) {
			// t[edificio][solar]
			sum+=t[i][l.get(i)];
		}
		
		// si la lista esta vacia, que me ponga infinito para no seleccionarla
		return l.size()==0 ? Integer.MAX_VALUE : sum;
	}
	
	public static void main(String[] args) {
		int[][] t = {{1,2,8},
				{4,5,3},
				{1,7,9}
				};
		
		
		System.out.println(solares(t));
		System.out.println("Suma costes: " + sum(t, solares(t)));
	}

}
