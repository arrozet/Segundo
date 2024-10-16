package parciales;

public class Parcial2021 {

	/**
	* Una máquina de entradas para eventos tiene un problema, el cual asigna a distintas 
	* personas,las mismas entradas, no sabemos cómo lo hace ni por qúe motivo. Pero sí 
	* sabemos que se produce como mucho una vez por evento. Las entradas se asignan de
	* manera consecutiva, de la forma {1,2,3,4,5} donde no tendría ningun error, en 
	* cambio, {1,2,3,3,3,3,4,5,6} tendría error al asignar más de una vez la entrada 3. 
	* Encuentra, dado un vector, si existe error en la asignación de entradas
	* y en caso afirmativo, devuelve la entrada errónea.
	* Realiza un algoritmo Divide y Vencerás de coste O (log n) que resuelva este problema.
	*/
	
	public static int errorEntradas(int[] a) {
		return errorEntradas(a,0,a.length-1);
	}
	
	public static int errorEntradas(int[] a, int ini, int fin) {
		int mid = ini+fin/2;
		// si me queda 1 solo elemento es que no hay repes
		if(ini==fin) {
			return Integer.MIN_VALUE;
		}
		// compruebo que los indices son correctos y miro si alguno de los extremos se repite
		else if (mid-1 >= 0 && mid+1 < a.length && 
				(a[mid-1] == a[mid] || a[mid+1] == a[mid])) {
			return a[mid];
		}
		else {	// mid+1 pq el array que nos dan no empieza en 0, sino en 1
			if(a[mid] == mid+1) {	// está ordenado hasta ahí, pues el índice es coherente con el elemento. 
									// Luego pues, debe estar a la derecha
				return errorEntradas(a, mid, fin);
			}
			else {
				return errorEntradas(a, ini, mid);
			}
		}
	}
	
	public static void main(String[] args) {
		int[] a = {1,2,3,4,5};
		int[] b = {1,2,3,3,3,3,4,5,6};
		// como este no tiene entrada erronea, debe salir un valor muy pequeño
		System.out.println(errorEntradas(a));
		System.out.println(errorEntradas(b));

	}

}
