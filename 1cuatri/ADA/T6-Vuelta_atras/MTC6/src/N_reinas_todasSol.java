import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

// CONTEO -> contar cuantas soluciones válidas hay
// ENUMERACIÓN -> decir todas las soluciones válidas
public class N_reinas_todasSol {
	public static List<List<Integer>> reinas_todas(int n){
		Integer [] lista = new Integer[n];
		// indice -> fila, contenido -> columna
		List<List<Integer>> todas = new ArrayList<>();
		reinas_todas(lista, 0,todas);
		return todas;
	}
	public static void reinas_todas(Integer [] sol, int etapa, List<List<Integer>> todas) {
		if(etapa==sol.length) {
            // Todas las reinas están ubicadas, se encontró una solución
			todas.add(new ArrayList<Integer>(Arrays.asList(sol)));
		}
		else {
			int i =0;
			while(i<sol.length) {
				if(!tiene(sol,i,etapa) && noDiagonal(sol,i,etapa)) {
					sol[etapa] = i;
					reinas_todas(sol,etapa+1, todas);	// miramos si dando 1 paso más hay solución
					
					// Restablecer la posición de la reina en esta etapa a cero
					// y disminuir la etapa (backtracking)
					sol[etapa] = -1;
					
				}
				i++;
			}
		}
		
	}
	
	// tengo que considerar que a lo sumo hay que mirar en la etapa en cuestion
	private static boolean tiene(Integer[] sol, int n, int etapa) {
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
	private static boolean noDiagonal(Integer[] lista, int i, int etapa){
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
		System.out.println(reinas_todas(4).toString());

	}

}
