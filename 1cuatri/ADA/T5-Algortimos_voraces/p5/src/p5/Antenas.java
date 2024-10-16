package p5;

import java.util.ArrayList;
import java.util.List;

public class Antenas {
	
	private Integer[] puntosKm; //Puntos kilometricos de las urbanizaciones ordenados crecientemente.
	private int cobertura;
		
	public Antenas(Integer[] urbanizaciones, int c) {
		puntosKm = urbanizaciones;
		cobertura=c;
		
	}
	
	public List<Integer> situarAntenas(){
		// puntosKm está ordenado
		// - la antena se debe poner lo más alejado posible de un punto
		// - la izq de la primera y la der de la ultima no se miran
		List<Integer> situacion = new ArrayList<>();
		
		boolean[] cubierto = new boolean[puntosKm.length];
		
		for(int i=0; i<puntosKm.length; i++) {
			// si no está cubierto
			if(!cubierto[i]) {
				cubierto[i] = true;	// lo cubro
				int antena = puntosKm[i]+cobertura;
				situacion.add(antena);	// y pongo una antena lo más alejada posible
				
				// miro qué puntos cubre también mi antena
				int j=i;
				// (puntosKm[j]-antena) puede salir negativo, pero siempre será >= que (-cobertura) pq 
				// puntosKm[j] > puntosKm[i]
				while(j<puntosKm.length && (puntosKm[j]-antena) <= cobertura) {
					cubierto[j] = true;
					j++;
				}
			}
		}
		
		return situacion;
	}
	
	
}
