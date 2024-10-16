
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
	   //*** Implementar este método ***
		
	   //*** Se permite definir todos los métodos privados que se consideren necesarios ***
	}
	
	
}
