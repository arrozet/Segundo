//package ej4Esqueleto;

import java.util.LinkedList;
import java.util.List;      //Interfaz Java para una lista
//import java.util.ArrayList; //Posible implementación de la interfaz List
import java.util.Random;	//Clase para la generación de números aleatorios

public class mergesort {

	//Método estático para crear la lista de valores aleatorios
	private static List<Integer> crear_lista_aleatoria() {
		//Paso 1. Crear objeto de tipo Random
		Random random = new Random();
		//Paso 2. Crear objeto de tipo List (lista vacia de Integer)
		List<Integer> list = new LinkedList<>();
		//Paso 3. Generar 100 numeros aleatorios y guardarlos en la lista
		for(int i=0;i<100;i++){
			list.add(random.nextInt(0, 500));	// no tengo ganas de que me genere valores extraños, asi que los pongo entre 0 y 500
		}
		//Paso 4. Devolver la lista
		return list;
	}

	public static void main(String[] args) {
		try {
			//Paso 1.Crea una lista de 100 valores int aleatorios (entre 0 y 100)
			//Paso 1.1. Declarar una lista de Integer
			List<Integer> lista;
			
			//Paso 1.2. Llamar al metodo anterior para crear la lista
			lista = crear_lista_aleatoria();
			
			//Paso 2. Mostrar por pantalla la lista no ordenada
			System.out.println("Inicial: ");
			System.out.println(lista);
			
			//Paso 3. Crea el primer nodo del árbol y le pasa la lista completa
			//Paso 3.1. Crea un nodo y le pasa la lista creada anteriormente
			Nodo n = new Nodo(lista);
			//Paso 3.2. Inicia la hebra
			n.start();
			//Paso 3.3. Espera a que la hebra termine la ejecución
			n.join();
			
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
	private List<Integer> lista;
	/* Paso 2. Implementa el constructor que recibe una lista
	 *       - guarda la lista recibida en el atributo "lista"
	 *       - Si la lista es null lanza IllegarArgumentException
	 */
	public Nodo(List<Integer> l) {
		if(l==null){
			throw new IllegalArgumentException("Nodo: no se pudo crear el nodo");
		}
		lista = l;
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
		lista.clear();
		
		//Paso 3.2 Mezclar mientras haya datos en l1 y l2
		while ((i1 < l1.size()) && (i2 < l2.size())) {
			if(l1.get(i1)<=l2.get(i2)){
				lista.add(l1.get(i1));
				i1++;
			}
			else{
				lista.add(l2.get(i2));
				i2++;
			}
		}

		/* Paso 3.3 Añadir el resto de valores 
		 *   - Si l1 y l2 son de distinto tamaño al salir del bucle
		 *   - l1 o l2 tendrán valores que incorporar a lista
		 */
		// si haces addAll los metes todos. deberia haber borrado antes. eso es ineficiente, mejor asi
		while (i1 < l1.size()) {
			lista.add(l1.get(i1));
			i1++;
		}

		while (i2 < l2.size()) {
			lista.add(l2.get(i2));
			i2++;
		}
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
				List<Integer> l1 = new LinkedList<>(lista.subList(0, sz/2));
				List<Integer> l2 = new LinkedList<>(lista.subList(sz/2, sz));
				//Paso 4.3 Se crean dos nodos y se les pasa cada una de las listas creadas
				Nodo n1 = new Nodo(l1);
				Nodo n2 = new Nodo(l2);
				//Paso 4.4 Se inicia la ejecución de los dos nodos (hebras)
				n1.start();
				n2.start();
				//Paso 4.5 Se espera a que terminen los dos nodos (hebras)
				n1.join();
				n2.join();
				//Paso 4.6 Se mezclan las dos listas ya ordenadas
				mezcla(l1,l2);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
