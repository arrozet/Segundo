package jun2020;
public class Barca {

	boolean meHePerdidoEnElViajeDeTuCuerpo = true;	// bloqueo bajada durante viaje. saludo!!
	boolean meEstoyBajandoEsperamePorfaPlis=false;
	int MAX_PERSONAS = 3;
	int personas=0;
	int orilla=1;//norte
	/*
	 * El Pasajero id quiere darse una vuelta en la barca desde la orilla pos
	 */
	public synchronized void subir(int id,int pos) throws InterruptedException{
		while(pos!=orilla || personas==MAX_PERSONAS || meEstoyBajandoEsperamePorfaPlis){	// estoy en pos y la barca no
																							// o hay todas las personas o hay gente bajandose
			wait();
		}

		personas++;
		if(personas==3){
			meHePerdidoEnElViajeDeTuCuerpo=true;	// a viajar!!
		}
		notifyAll();	// llo despierto siempre total ajajjajaj el ordenador con el que hago el examen no es mio k trabaje
		System.out.println("Pasajero " + id + " se sube al barco en la orilla " + orilla);
	}
	
	/*
	 * Cuando el viaje ha terminado, el Pasajero que esta en la barca se baja
	 */
	public synchronized int bajar(int id) throws InterruptedException{
		while(!meEstoyBajandoEsperamePorfaPlis){	// si me toca bajareme
			wait();
		}
		if(personas==MAX_PERSONAS){	// pa hacerlo solo 1 vez jajajjjajaj canelón
			orilla=(orilla+1)%2;
		}
		personas--;
		System.out.println("	Pasajero " + id + " se baja del barco en la orilla " + orilla);

		if(personas==0){
			meEstoyBajandoEsperamePorfaPlis=false;	// ya se han bajado todos e o no e... pefeto
		}
		notifyAll(); // va siendo hora de despertarse no manito

		return orilla;
	}
	/*
	 * El Capitan espera hasta que se suben 3 pasajeros para comenzar el viaje
	 */
	public synchronized void esperoSuban() throws InterruptedException{
		while(personas<MAX_PERSONAS || !meHePerdidoEnElViajeDeTuCuerpo){	// no hay suficientes personas o no me toca viajar
			wait();
		}
		meHePerdidoEnElViajeDeTuCuerpo = true;	//viajando
		System.out.println("Empiezo el viajecito!! guau!!");
	}
	/*
	 * El Capitan indica a los pasajeros que el viaje ha terminado y tienen que bajarse
	 */
	public synchronized void finViaje() throws InterruptedException{
		System.out.println("Termino el viajecito!! Mola cantidubi!!");
		meHePerdidoEnElViajeDeTuCuerpo=false;	// jajaj buen viaje colega
		meEstoyBajandoEsperamePorfaPlis=true;	// a bajarse que hay que alomeón ay k ver a la parienta
		notifyAll();
	}

}
