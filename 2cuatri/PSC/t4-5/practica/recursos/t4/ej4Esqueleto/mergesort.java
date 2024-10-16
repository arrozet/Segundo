package ej4Esqueleto;

import java.util.List;      //Interfaz Java para una lista
import java.util.ArrayList; //Posible implementación de la interfaz List
import java.util.Random;	//Clase para la generación de números aleatorios

public class mergesort {

	//Método estático para crear la lista de valores aleatorios
	private static List<Integer> crear_lista_aleatoria() {
		//Paso 1. Crear objeto de tipo Random

		//Paso 2. Crear objeto de tipo List (lista vacia de Integer)
		
		//Paso 3. Generar 100 numeros aleatorios y guardarlos en la lista
		
		//Paso 4. Devolver la lista
	}

	public static void main(String[] args) {
		try {
			//Paso 1.Crea una lista de 100 valores int aleatorios (entre 0 y 100)
			//Paso 1.1. Declarar una lista de Integer
			
			//Paso 1.2. Llamar al metodo anterior para crear la lista
			
			
			//Paso 2. Mostrar por pantalla la lista no ordenada
			System.out.println("Inicial: ");
			System.out.println(lista);
			
			//Paso 3. Crea el primer nodo del árbol y le pasa la lista completa
			//Paso 3.1. Crea un nodo y le pasa la lista creada anteriormente
			
			//Paso 3.2. Inicia la hebra
			
			//Paso 3.3. Espera a que la hebra termine la ejecución
			
			
			//Paso 4. Muestra la lista ordenada una vez terminan todas las hebras
			System.out.println("Resultado: ");
			System.out.println(lista);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

//Implementa la clase Nodo - es una hebra
class Nodo extends Thread {
	//Paso 1. Define el atributo "lista"

	/* Paso 2. Implementa el constructor que recibe una lista
	 *       - guarda la lista recibida en el atributo "lista"
	 *       - Si la lista es null lanza IllegarArgumentException
	 */
	public Nodo(List<Integer> l) {

	}
	
	/* Paso 3. Implementa el método mezcla que recibe dos listas 
	 * 			- l1 y l2 son las listas a mezclar
	 * 			- l1 y l2 ya están ordenadas
	 * 			- la lista mezclada la guarda en el atributo "lista"
	 * 
	 * 	AYUDA: Usar los metodos clear(), add() y addAll() de la interfaz List
	 */
	private void mezcla(List<Integer> l1, List<Integer> l2) {
		int i1 = 0, i2 = 0; //posición inicial en l1 y l2
		
		//Paso 3.1 Vaciar el atributo "lista" 
		
		//Paso 3.2 Mezclar mientras haya datos en l1 y l2
		while ((i1 < l1.size()) && (i2 < l2.size())) {
			
		}
		
		/* Paso 3.3 Añadir el resto de valores 
		 *   - Si l1 y l2 son de distinto tamaño al salir del bucle
		 *   - l1 o l2 tendrán valores que incorporar a lista
		 */
	}
	
	/* Paso 4. Implementar comportamiento de la hebra
	 *	- Caso base: Si el tamaño de la lista es 1 no se hace nada
	 *  - Caso general: 
	 *        - Se crean dos nodos (dos hebras) y a cada nodo se le pasa la mitad de la lista
	 *        - Se empiezan las dos hebras hijas
	 *        - Se espera a que terminen
	 *        - Se mezclan las dos listas
	 *                  
	 *                   
	 *
	 */
	public void run() {
		try {
			//Paso 4.1 Se calcula el tamaño de la lista
			int sz = lista.size();
			//Caso general
			if (sz > 1) {
				//Paso 4.2 Se crean dos listas cada una la mitad de la lista original

				//Paso 4.3 Se crean dos nodos y se les pasa cada una de las listas creadas

				//Paso 4.4 Se inicia la ejecución de los dos nodos (hebras)
				
				//Paso 4.5 Se espera a que terminen los dos nodos (hebras)

				//Paso 4.6 Se mezclan las dos listas ya ordenadas
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
