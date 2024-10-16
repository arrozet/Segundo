package p5;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DivideLista {
	
	/**
	 * 
	 * @param datos lista de entrada
	 * @param k     longitud de una de las listas solución
	 * @param a   lista solución 1 (salida)
	 * @param b   lista solución 2 (salida)
	 * @return  la diferencia entre las dos listas
	 * 
	 */
	public static int resolverVoraz(int []datos, int k, List<Integer> a, List<Integer> b) {
		int sumaA=0;
		int sumaB=0;
		
		// ordeno el array de MENOR A MAYOR
		Arrays.sort(datos);
		
		// me aseguro de que k es la mitad mas pequeña, pues uso k como "barrera" para meter los
		if(datos.length-k < k) {	// 	elementos más pequeños
			k = datos.length-k;
		}
		
		// relleno los dos arrays. Primero el de pequeños y luego el de grandes
		for(int i = 0; i<datos.length; i++) {
			if(i<k) {
				b.add(datos[i]);
			}
			else {
				a.add(datos[i]);
			}
		}
		
		// sumo los elementos de a
		for(int v=0; v<a.size(); v++) {
			sumaA+=a.get(v);
		}
		
		// sumo los elementos de b
		for(int w=0; w<b.size(); w++) {
			sumaB+=b.get(w);
		}
		
		// devuelvo la diferencia
		return sumaA-sumaB;
	}
	
	public static void main(String[] args) {
		int[] a = {2, 7, 6, 0, 4};
		
		List<Integer> a1 = new ArrayList<>();
		List<Integer> b1 = new ArrayList<>();
		System.out.println(resolverVoraz(a, 3, a1, b1));
		System.out.println(a1.toString());
		System.out.println(b1.toString());
	}
	
}
