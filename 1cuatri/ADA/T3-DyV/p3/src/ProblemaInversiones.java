public class ProblemaInversiones {
	
	public static int numInversiones(int[] v) {
		return numInv(v,0,v.length-1);
	}
	
	private static int numInv(int[] a, int prim, int ult) {
		int numInversiones = 0;
		if (prim < ult){
			int mid = (prim+ult)/2;
			numInversiones += numInv(a,prim,mid);
			numInversiones += numInv(a,mid+1,ult);
			
			// cuando llega aquí las mitades están ordenadas
			numInversiones += mezclar(a,prim,mid,ult);
			}
		
		return numInversiones;
	}
	
	// Mezclar recibe 2 mitades ordenadas y usa un array auxiliar b para almacenar la ordanación final
	private static int mezclar(int[] a,int inf,int medio,int sup){
		// Inicializamos variables y el array auxiliar
		int i = inf; int j = medio+1;
		int[] b = new int[sup-inf+1];
		int k = 0;
		int numInversiones=0;
		
		// b se rellena con los menores elementos no considerados
		while (i<=medio && j <=sup){
			if (a[i]<=a[j]){
				b[k] = a[i];
				i++;
			}
			else{
				b[k] = a[j];
				j++;
				// si a[i]>a[j], es decir, si están invertidos (ya que, además, i<j)
				numInversiones += medio-i+1;
				// es porque las MITADES que le llegan a mezclar están ORDENADAS, 
				// entonces cuando estas comparando las dos mitades y ves que hay un elemento del ARRAY IZQUIERDO 
				// (llamémoslo 'grandote') que es mayor que un elemento del ARRAY DERECHO (llamémoslo 'chiquito') 
				// (es decir, a[i]>a[j], la definición de inversión), 

				// eso significa que todos los elementos del ARRAY IZQUIERDO desde 'grandote' hasta el final (medio-i+1)
				// forman inversión con 'chiquito', ya que en la mitad izquierda, todos los elementos siguientes 
				// a 'grandote' son mayores o iguales a 'grandote' (las mitades están ordenadas), y por tanto mayores 
				// o iguales a 'chiquito' ('grandote' y sus compañeros siguientes forman inversión con 'chiquito').
			} 
			k++;
		}
		
		// Se añaden los elementos sobrantes de la mitad que aún tenga elementos
		while (i<=medio){
			b[k] = a[i];
			i++; 
			k++;
		}
			
		while (j<=sup){
			b[k] = a[j];
			j++; 
			k++;
		}
		
		// Se sobreescribe a con el contenido de b
		k=0;
		for (int f=inf; f<= sup; f++){
			a[f] = b[k];
			k++;
		}
		
		return numInversiones;
	}
	
	public static void main(String[] args) {
		int[] a = {2,4,1,3,5};
		System.out.println(numInversiones(a));
	}
}
