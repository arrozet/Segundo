package jun2020.barcas;

import java.util.concurrent.Semaphore;

public class Barca {
	private int nOrilla = 1;
	private int pasajeros = 0;

	private Semaphore capitan = new Semaphore(0);
	private Semaphore[] viajeSubir = new Semaphore[2];
	private Semaphore bajar = new Semaphore(0);
	//private Semaphore[] viajeBajar = new Semaphore[2];
	private Semaphore mutex = new Semaphore(1);

	public Barca(){
		viajeSubir[nOrilla] = new Semaphore(1);
		viajeSubir[(nOrilla+1)%2] = new Semaphore(0);
		/*for(int i =0; i<=1; i++){
			viajeBajar[i] = new Semaphore(0);
		}

		 */

	}

	/*
	 * El Pasajero id quiere darse una vuelta en la barca desde la orilla pos
	 */
	public  void subir(int id,int pos) throws InterruptedException{
		viajeSubir[pos].acquire();	// los pasajeros quieren viajar a la orilla contraria
		mutex.acquire();
		pasajeros++;
		System.out.println("Viajero " + id + " se sube en la orilla " + pos);

		if(pasajeros<3){
			viajeSubir[pos].release();	// se pueden seguir subiendo pasajeros
		}
		else{
			//nOrilla = pos;
			capitan.release();	// estamos todos, que el capitan diga de partir
		}
		mutex.release();

	}
	
	/*
	 * Cuando el viaje ha terminado, el Pasajero que esta en la barca se baja
	 */
	public  int bajar(int id) throws InterruptedException{
		bajar.acquire();	// los que estaban en la otra orilla se quieren bajar
		mutex.acquire();
		pasajeros--;
		System.out.println("Viajero " + id + " se baja");

		if(pasajeros>0){
			bajar.release();	// se pueden seguir bajando pasajeros
		}
		else{
			nOrilla = (nOrilla+1)%2;	// cambio de orilla
			System.out.println("Barco vacío");
			viajeSubir[nOrilla].release();	// que se suban los que están en la nueva orilla
		}
		mutex.release();

		return pasajeros>0 ? ((nOrilla+1)%2) : nOrilla;	// si aun no he cambiado la orilla, la cambio con la operacion. Si si la he cambiado, no la cambio
	}
	/*
	 * El Capitan espera hasta que se suben 3 pasajeros para comenzar el viaje
	 */
	public  void esperoSuban() throws InterruptedException{
		capitan.acquire();
		System.out.println("Viaje en marcha!!!");
	}
	/*
	 * El Capitan indica a los pasajeros que el viaje ha terminado y tienen que bajarse
	 */
	public  void finViaje() throws InterruptedException{
		System.out.println("Acabó el viaje!!");
		bajar.release();

	}

}
