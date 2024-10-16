import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Ej2_DosElemSumaS {
	public static boolean esSuma1 (List<Integer> v, int s) {
		v.sort(Comparator.naturalOrder()); // quicksort - hace falta un comparador para indicar como ordenar
		int j = v.size()-1;
		boolean encontrado = false;
		
		int pos_limite = 0;
		while(!encontrado && pos_limite<v.size()) {
			if(v.get(pos_limite)==s) {
				encontrado = true;
			}
			pos_limite++;
		}
		
		int i=0;
		j = pos_limite-1;
		encontrado = false;
		
		while(i<j && !encontrado) {
			//
			//iteraciones++;
			//
			if(v.get(i)+v.get(j)==s) {
				encontrado = true;
			}
			else if(v.get(i)+v.get(j)>s) {
				j--;
			}
			else {
				i++;
			}
		}
		
		return encontrado;
	}
	public static boolean esSuma2 (List<Integer> v, int s) {
		boolean encontrado = false;
		return encontrado;
	}
	
	public static void main(String[] args) {
		Integer[] a = {2,8,10,15,32};
		
		
		
		System.out.println(esSuma1(Arrays.asList(a), 10));
		//System.out.println(existe_suma_x_2(a, 10));
		System.out.println();
		
		Integer [] b = {3, 7, 11, 15, 19, 23, 27, 31, 35, 39, 43, 47, 51, 55, 59, 63, 67, 71, 75, 79};
		
		System.out.println(esSuma1(Arrays.asList(b), 30));
		System.out.println(esSuma1(Arrays.asList(b), 80));	// peor caso - no está (19 iteraciones)
		System.out.println(esSuma1(Arrays.asList(b), 59+55));
		System.out.println();
	}
}
