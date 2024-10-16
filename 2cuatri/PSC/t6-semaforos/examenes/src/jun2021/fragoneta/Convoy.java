package jun2021.fragoneta;

import java.util.concurrent.Semaphore;

public class Convoy {
	private int tamConvoy;
	private int furgonetas;
	private int liderId;

	private Semaphore lider = new Semaphore(0);
	private Semaphore seguidor = new Semaphore(0);
	private Semaphore mutex = new Semaphore(1);
	public Convoy(int tam) {
		tamConvoy = tam;
		furgonetas = 0;
	}
	
	/**
	 * Las furgonetas se unen al convoy
	 * La primera es la lider, el resto son seguidoras 
	 **/
	public int unir(int id) throws InterruptedException {
		mutex.acquire();
		furgonetas++;
		if(furgonetas == 1){
			liderId = id;
			System.out.println("** Furgoneta " +id + " lidera del convoy **");

		} else if (furgonetas < tamConvoy) {
			System.out.println("Furgoneta "+id+" seguidora");
		}
		else{
			System.out.println("Furgoneta "+id+" seguidora");
			lider.release();
		}
		mutex.release();
		return liderId;
	}

	/**
	 * La furgoneta lider espera a que todas las furgonetas se unan al convoy 
	 * Cuando esto ocurre calcula la ruta y se pone en marcha
	 * */
	public void calcularRuta(int id) throws InterruptedException {
		lider.acquire();
		System.out.println("** Furgoneta "+id+" lider:  ruta calculada, nos ponemos en marcha **");
	}
	
	
	/** 
	 * La furgoneta lider avisa al las furgonetas seguidoras que han llegado al destino y deben abandonar el convoy
	 * La furgoneta lider espera a que todas las furgonetas abandonen el convoy
	 **/
	public void destino(int id) throws InterruptedException {
		System.out.println("** Furgoneta "+id+" lider: hemos llegado al destino **");
		seguidor.release();
		lider.acquire();
		System.out.println("** Furgoneta "+id+" lider abandona el convoy ** Quedan " + furgonetas);
	}

	/**
	 * Las furgonetas seguidoras hasta que la lider avisa de que han llegado al destino
	 * y abandonan el convoy
	 **/
	public void seguirLider(int id) throws InterruptedException {
		//mutex.acquire();
		seguidor.acquire();
		furgonetas--;
		System.out.println("Furgoneta "+id+" abandona el convoy. Quedan " + furgonetas);
		if(furgonetas>1){
			seguidor.release();
		}
		else{
			furgonetas--;
			lider.release();
		}
		//mutex.release();
	}

	
	
	/**
	* Programa principal. No modificar
	**/
	public static void main(String[] args) {

			final int NUM_FURGO = 10;
			Convoy c = new Convoy(NUM_FURGO);
			Furgoneta flota[] = new Furgoneta[NUM_FURGO];

			for (int i = 0; i < NUM_FURGO; i++)
				flota[i] = new Furgoneta(i, c);

			for (int i = 0; i < NUM_FURGO; i++)
				flota[i].start();

	}

}
