import java.util.ArrayList;
import java.util.List;

public class Laberinto {
	private int[][] laberinto;
	private Posicion entrada, salida;
	private int n; // número de filas y columnas

	public Laberinto(int[][] lab, Posicion ent, Posicion sal) {
		this.laberinto = lab;
		this.entrada = ent;
		this.salida = sal;
		this.n = lab.length;
	}

	public int getNumFilas() {
		return n;
	}

	public int getNumCols() {
		return n;
	}
	
	public int [][] getLaberinto(){
		return laberinto;
	}
	public Posicion getEntrada() {
		return entrada;
	}
	public Posicion getSalida() {
		return salida;
	}
	
	public List<Posicion> encontrarCamino() {
		List<Posicion> lista = new ArrayList<Posicion>();
		lista.add(entrada);
		return (encontrarCamino(lista)) ? lista : null;	// aqui ya esta la condición de
				// En caso de que no exista un camino debe devolverse null.
	}

	/**
	 * Algoritmo de Vuelta Atrás para encontrar un camino que nos permita
	 * salir del laberinto
	 */
	private boolean encontrarCamino(List<Posicion> sol) {
		boolean haySol = false;
		if(esCompleta(sol)) {	// si mi nueva posIni es la posFin
			haySol = true;
		}
		else {
			int dir = 1;
			while(!haySol && dir<=4) {	// pongo un while, porque conque haya 1 basta (problema de satisfacción)
					// pruebo los siguientes de todas las direcciones
					Posicion newPos = siguiente(sol.get(sol.size()-1), dir);
					// si es valida, considero abrir ramas
					if(valida(newPos, sol)) {
						sol.add(newPos);
						haySol = encontrarCamino(sol);
						if(!haySol) {	// esto irá quitando el último recursivamente
							sol.remove(sol.size()-1);
						}
					}
					dir++;
			}
		}
		
		return haySol;
	}

	/**
	 * Comprueba si una solución es completa.
	 */
	private boolean esCompleta(List<Posicion> sol) {
		// si mi nueva posIni es la posFin
		return sol.get(sol.size()-1).equals(salida);
	}

	/**
	 * Comprueba que la posición dada es una candidata válida para el siguiente paso
	 */
	private boolean valida(Posicion candidata, List<Posicion> sol) {
		// no hay ciclos, la candidata esta en la tabla, la candidata no es un muro
		return !sol.contains(candidata) && estaEnLaberinto(candidata) && !esMuro(candidata);
	}

	/**
	 * Devuelve true si en la posición p hay un muro
	 */
	private boolean esMuro(Posicion p) {
		// es muro si hay un -1 en la tabla
		return laberinto[p.getX()][p.getY()] == -1;
	}

	/**
	 * Devuelve true si la posición dada está dentro del laberinto.
	 */
	private boolean estaEnLaberinto(Posicion pos) {
		// si los índices están bien
		return pos.getX() >=0 && pos.getX() < n 
				&& pos.getY()>=0 && pos.getY() < n;
	}

	/**
	 * Dada una posición cartesiana devuelve la siguiente posición en el sentido
	 * indicado. Precondición: actual != null
	 * 
	 * @param actual Posición de partida
	 * @param dir    Sentido en el que hay que desplazarse (1->Norte, 2->Sur,
	 *               3->Este, 4-> Oeste)
	 * @return La nueva posición.
	 */
	private Posicion siguiente(Posicion actual, int dir) {
		int x = actual.getX();
		int y = actual.getY();
		if (dir == 1) {
			x--;
		} else if (dir == 2) {
			x++;
		} else if (dir == 3) {
			y++;
		} else {
			y--;
		}
		return new Posicion(x, y);
	}

	/**
	 * Devuelve todos los caminos para salir del laberinto.
	 */
	public List<List<Posicion>> encontrarCaminos() {
		List<List<Posicion>> todosCaminos = new ArrayList<List<Posicion>>();
		List<Posicion> sol = new ArrayList<Posicion>();
		sol.add(entrada);
		encontrarCaminos(sol, todosCaminos);
		return todosCaminos;
	}

	/**
	 * Algoritmo de Vuelta Atrás para encontrar todas las soluciones
	 */
	private void encontrarCaminos(List<Posicion> sol, List<List<Posicion>> todas) {
		if(esCompleta(sol)) {	// si mi nueva posIni es la posFin
			todas.add(new ArrayList<>(sol));	// la meto en la lista de listas
		}
		else {
			int dir = 1;
			while(dir<=4) {
					// pruebo los siguientes de todas las direcciones
					Posicion newPos = siguiente(sol.get(sol.size()-1), dir);
					// si es valida, considero abrir ramas
					if(valida(newPos, sol)) {
						sol.add(newPos);
						encontrarCaminos(sol, todas);
						sol.remove(sol.size()-1);
					}
					dir++;
			}
		}
	}

	public List<Posicion> encontrarCaminoMasCortoVA() {
		List<Posicion> sol = new ArrayList<Posicion>();
		sol.add(entrada);
		return encontrarCaminoMasCortoVA(sol, null);
	}

	/**
	 * Algoritmo de Vuelta Atrás que devuelve la mejor solución encontrada
	 */
	private List<Posicion> encontrarCaminoMasCortoVA(List<Posicion> sol, List<Posicion> mejor) {
		if(esCompleta(sol)) {	// si mi nueva posIni es la posFin
			int tam = 0;
			// para evitar nullPointerException
			if(mejor != null) {
				tam = mejor.size();
			}
			
			// como entra como null, debo considerar cuando entre como null; si hay alguna solución completa inicializarlo
			// de lo contrario al hacer clear / addAll no estaré haciendo esencialmente nada
			if(mejor==null || sol.size() < tam) {
				mejor = new ArrayList<>(sol);
				
//				mejor.clear();
//				mejor.addAll(sol); // escojo la nueva mejor
			}
		}
		else {
			int dir = 1;
			while(dir<=4) {
					// pruebo los siguientes de todas las direcciones
					Posicion newPos = siguiente(sol.get(sol.size()-1), dir);
					// si es valida, considero abrir ramas
					if(valida(newPos, sol)) {
						sol.add(newPos);
						mejor = encontrarCaminoMasCortoVA(sol,mejor);	// actualizo el mejor
						sol.remove(sol.size()-1);	// como quiero explorar todas las ramas, tengo que volver atras siempre
					}
					dir++;
			}
		}
		
		return mejor;
	}

	/**
	 * Devuelve la calidad de la solución indicada
	 */
	private int calidad(List<Posicion> sol) {
		//***Completar la implementación****
		
		// realmente nunca va a ser null, pq siempre que se accede a calidad, es dentro de un while que
		// tiene una condición de que la ED no sea vacia; por lo que siempre accederá a un sol!=null
		return sol==null ? 0 : sol.size();
	}

	public List<Posicion> encontrarCaminoMasCortoBB() {
		ColaPrioridad c = new ColaPrioridad();// Creamos la estructura de datos

		List<Posicion> solInicial = new ArrayList<>();
		solInicial.add(entrada);	// mi posición de partida
		Estado inicial = new Estado(solInicial, funcionCota(solInicial)); // Creamos el estado inicial

		List<Posicion> mejor = null;	// requirimientos de los test, debe inicializarse a null
		int cotaMejor = Integer.MAX_VALUE; // infinito
		
		//***Completar la implementación****
		// para que c no este vacio (debe tener al menos la inicial)
		c.insertar(inicial);
		
		while(!c.estaVacia()) {
			// la implementación de la PQ es un sortedSet que ordena los elementos por cota, por lo que el primero siempre será el de menor cota (más óptimo en este caso)
			Estado estSig = c.extraer();
			
			// si su camino es completo, intentamos podar los que tengan peores soluciones
			if(esCompleta(estSig.getCamino())) {
				if(estSig.cota()<cotaMejor) {	// solo podaremos si nuestro posible camino tiene una cota más optima
					mejor = estSig.getCamino();	// cambiamos los valores de nuestro mejor camino hasta la fecha
					cotaMejor = estSig.cota();
					c.eliminar(cotaMejor);	// y podamos
				}
			}
			// si no, miramos los posibles hijos siguientes
			else {
				int dir = 1;
				while(dir<=4) {
					// pruebo los siguientes de todas las direcciones (igual que en VA)
					
					// En la clase 'Estado', no se devuelve la implementación; sino una interfaz. Si no lo metes en una ArrayList no hace nada 
					List<Posicion> hijo = new ArrayList<>(estSig.getCamino());
					Posicion newPos = siguiente(hijo.get(hijo.size()-1), dir);	// siguiente posición
					
					// ramificar, tengo que ver los posibles candidatos siguientes de mi posible estado siguiente
					if(valida(newPos, hijo)) {	// si mi pos es valida
						hijo.add(newPos);
						if(funcionCota(hijo) <cotaMejor) {	// y la cota es más óptima que la mejor
							Estado nuevoEstado = new Estado(hijo, funcionCota(hijo));	
							c.insertar(nuevoEstado);	// la meto en la ED
						}
					}
					dir++;
				}
			}
		}
		
		return mejor;
	}

	/**
	 *  Devuelve el valor de cota de la solución indicada. 
	 */
	private int funcionCota(List<Posicion> sol) {
		int calidad = calidad(sol);
		
		Posicion pos = sol.get(sol.size()-1);
		
		// distancia de Manhattan
		int estimacion = Math.abs((salida.getX()-pos.getX())) + Math.abs(salida.getY()-pos.getY());
		
		return calidad+estimacion;
	}
	
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
//			int [] [] t2 =  { {0, -1, -1, -1},
//							{  0,  0, -1,  0},
//							{  0,  0, -1, -1},
//							{ -1,  0,  0,  0},
//					};
//			Posicion entrada2 = new Posicion(0, 0);
//			Posicion salida2 = new Posicion(3,3);
			
			
			int [] [] t = { { 0,  0,  0,  0,  0, -1,  0, -1,  0,  0},
							{-1, -1,  0, -1, -1, -1,  0, -1, -1,  0},
							{ 0,  0,  0, -1,  0, -1,  0, -1,  0,  0},
							{ 0, -1, -1, -1,  0,  0,  0, -1,  0, -1},
							{ 0,  0,  0, -1,  0, -1, -1, -1,  0,  0},
							{-1, -1,  0, -1,  0,  0,  0, -1, -1,  0},
							{ 0, -1,  0,  0,  0, -1,  0,  0,  0,  0},
							{ 0, -1,  0, -1, -1, -1, -1, -1, -1,  0},
							{ 0, -1,  0,  0,  0, -1,  0,  0, -1,  0},
							{ 0,  0,  0, -1,  0, -1,  0,  0,  0,  0}};
			Posicion entrada = new Posicion(8, 0);
			Posicion salida = new Posicion(1,9);
			Laberinto l = new Laberinto(t,entrada,salida);
			
			System.out.println(l.encontrarCamino());
			System.out.println(l.encontrarCaminos());
			System.out.println(l.encontrarCaminoMasCortoVA());
			System.out.println(l.encontrarCaminoMasCortoBB());
		
					
					
					
					

		}
}

