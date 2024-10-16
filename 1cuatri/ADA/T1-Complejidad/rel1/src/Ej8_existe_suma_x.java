

public class Ej8_existe_suma_x {

	public static boolean existe_suma_x(int[] a, int x) {
		//
		//int iteraciones = 0;
		//
		int pos_limite = 0;
		boolean encontrado = false;
		while(!encontrado && pos_limite<a.length) {
			if(a[pos_limite]==x) {
				encontrado = true;
			}
			pos_limite++;
		}
		
		int i=0;
		int j = pos_limite-1;
		encontrado = false;
		
		while(i<j && !encontrado) {
			//
			//iteraciones++;
			//
			if(a[i]+a[j]==x) {
				encontrado = true;
			}
			else if(a[i]+a[j]>x) {
				j--;
			}
			else {
				i++;
			}
		}
		
		return encontrado;
				//". Nº iteraciones: " + iteraciones;
	}
	
	public static void existe_suma_x_2(int[] a, int x) {
		/*
		int iteraciones = 0;
		//
		int pos_limite = 0;
		boolean encontrado = false;
		while(!encontrado && pos_limite<a.length) {
			if(a[pos_limite]==x) {
				encontrado = true;
			}
			pos_limite++;
		}
		
		int izq=0;
		int der = pos_limite-1;
		encontrado = false;
		
		while(izq < der && izq<pos_limite && !encontrado) {
			if(a[izq]+a[der]==x) {
				encontrado = true;
			}
			else if(a[izq]+a[der]>x) {
				izq*=2;
				der/=2;
			}
			else {
				izq/=2;
				der*=2;
			}
			
	        
	        int left = i + 1;
	        int right = a.length - 1;
	        
	        while (left <= right && !encontrado) {
	        	//
				iteraciones++;
				//
	            int mid = left + (right - left) / 2;
	            
	            if (a[mid] == complemento) {
	                encontrado = true; // Encontramos una suma igual a x.
	            } else if (a[mid] < complemento) {
	                left = mid + 1;
	            } else {
	                right = mid - 1;
	            }
	        }
	        i++;
	        
	    }
	    return "Existe: " + encontrado + ". Nº iteraciones: " + iteraciones;*/
	}

	
	public static void main(String[] args) {
		int[] a = {2,8,10,15,32};
		
		System.out.println(existe_suma_x(a, 10));
		//System.out.println(existe_suma_x_2(a, 10));
		System.out.println();
		
		int[] b = {3, 7, 11, 15, 19, 23, 27, 31, 35, 39, 43, 47, 51, 55, 59, 63, 67, 71, 75, 79};
		System.out.println(existe_suma_x(b, 30));
		System.out.println(existe_suma_x(b, 80));	// peor caso - no está (19 iteraciones)
		System.out.println(existe_suma_x(b, 59+55));
		System.out.println();
		// complejidad O(n)
		
		/*
		System.out.println(existe_suma_x_2(b, 30));
		System.out.println(existe_suma_x_2(b, 80));	
		System.out.println(existe_suma_x_2(b, 59+55));
		*/
	
	}

}
