// DECISIÓN O SATISFACCIÓN -> encontrar alguno o determinar que no existe solución
import java.util.Arrays;

public class N_reinas_array {
	public static int [] reinas_v2(int n){
		int [] lista = new int[n];
		// indice -> fila, contenido -> columna
		boolean sol = reinas_v2(lista, 0);
		return sol?lista:null;
	}
	public static boolean reinas_v2(int [] sol, int etapa) {
		boolean haySolucion = false;
		if(etapa==sol.length) {
            // Todas las reinas están ubicadas, se encontró una solución
			haySolucion = true;
		}
		int i =0;
		while(!haySolucion && i<sol.length) {
			if(!tiene(sol,i,etapa) && noDiagonal(sol,i,etapa)) {
				sol[etapa] = i;
				haySolucion = reinas_v2(sol,etapa+1);	// miramos si dando 1 paso más hay solución
				if(!haySolucion) {
					// Restablecer la posición de la reina en esta etapa a cero
					// y disminuir la etapa (backtracking)
					sol[etapa] = 0;
					//etapa--;	// no hace falta ponerlo pq en la pila de llamadas se crea como
								// variable local y se destruye al final de cada llamada
				}
			}
			i++;
		}
		return haySolucion;
	}
	
	// tengo que considerar que a lo sumo hay que mirar en la etapa en cuestion
	private static boolean tiene(int[] sol, int n, int etapa) {
		int i=0;
		boolean tiene = false;
		
		while(i<etapa && !tiene) {
			if(sol[i] == n) {
				tiene = true;
			}
			i++;
		}
		return tiene;
	}
	
	//Se atacan en diagonal si |x-x'| == |y-y'|, siendo (x,y) y (x',y') las 
	//coordenadas de las reinas
	
	// tengo que considerar que a lo sumo hay que mirar en la etapa en cuestion
	private static boolean noDiagonal(int[] lista, int i, int etapa){
		boolean noCome = true;
		
		int j = etapa;
		int k = 0;
		while (k < etapa && noCome){
			// 				fila-fila'			columna-columna'
			noCome = Math.abs(j-k) != Math.abs((i)-lista[k]);
			k++;
		}
		return noCome;
	}


	public static void main(String[] args) {
		System.out.println(Arrays.toString(reinas_v2(4)));

	}

}
