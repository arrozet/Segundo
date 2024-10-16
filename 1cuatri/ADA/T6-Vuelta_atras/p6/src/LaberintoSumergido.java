
import java.util.ArrayList;
import java.util.List;

public class LaberintoSumergido {
	private int[][] laberinto;
	private Posicion entrada, salida;
	private int n;
	private int cantidadAire; //Cantidad de aire que tiene el buzo.

	
	public LaberintoSumergido(int[][] lab, Posicion ent, Posicion sal, int c) {
		this.laberinto = lab;
		this.n = laberinto.length;
		this.entrada = ent;
		this.salida = sal;
		cantidadAire = c;
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
	public int cantidadAire () {
		return cantidadAire;
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

	
	public List<Posicion> encontrarCaminoOptimo(){
		List<Posicion> sol = new ArrayList<Posicion>();
		sol.add(entrada);
		return encontrarCaminoOptimo(sol, null);
	}
	
	private List<Posicion> encontrarCaminoOptimo(List<Posicion> sol, List<Posicion> mejor){
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
						cantidadAire-=laberinto[newPos.getX()][newPos.getY()];
						mejor = encontrarCaminoOptimo(sol,mejor);	// actualizo el mejor
						sol.remove(sol.size()-1);	// como quiero explorar todas las ramas, tengo que volver atras siempre
						cantidadAire+=laberinto[newPos.getX()][newPos.getY()];
					}
					dir++;
			}
		}
		
		return mejor;
	}
	
	/**
	 * Comprueba si una solución es completa.
	 */
	private boolean esCompleta(List<Posicion> sol) {
		// si mi nueva posIni es la posFin
		return sol.get(sol.size()-1).equals(salida) && cantidadAire>=1;
	}

	/**
	 * Comprueba que la posición dada es una candidata válida para el siguiente paso
	 */
	private boolean valida(Posicion candidata, List<Posicion> sol) {
		// no hay ciclos, la candidata esta en la tabla, la candidata no es un muro
		return !sol.contains(candidata) && estaEnLaberinto(candidata) && !esMuro(candidata) && cantidadAire-laberinto[candidata.getX()][candidata.getY()] >= 1;
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
	
	
	
	
}
