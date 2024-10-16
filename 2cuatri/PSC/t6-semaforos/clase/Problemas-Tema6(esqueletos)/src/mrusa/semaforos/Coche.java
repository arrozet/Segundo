package mrusa.semaforos;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Coche{
	
	private int asientos; 	//Capacidad del coche
	
	private int numPas = 0;
	private Semaphore mutex = new Semaphore(1); //exclusion mutua
	
	private Semaphore esperaSubir = new Semaphore(1);
	private Semaphore esperaBajar = new Semaphore(0);
	private Semaphore esperaLleno = new Semaphore(0);
	private Random r = new Random();
	
	public Coche(int tam){
		asientos = tam;
	}
	
	public Coche(){
		asientos = 5;
	}
	
	//CS1-Pasajero: espera hasta que se abra la puerta
	//CS2-Pasajero: espera hasta que pueda bajar
	public void darVuelta(int id) throws InterruptedException{
		esperaSubir.acquire();
		mutex.acquire();
		numPas++;

		System.out.println("El pasajero " + id + " sube al coche");
		if(numPas<asientos){
			esperaSubir.release();	//si hay asientos libres, se pueden seguir subiendo
		}else{
			//esperaVacio.acquire();
			esperaLleno.release();
		}
		mutex.release();

		Thread.sleep(r.nextInt(500));
		// esperaBajar
		esperaBajar.acquire();
		mutex.acquire();
		numPas--;
		System.out.println("	Pasajero " + id + " baja del coche");
		if(numPas>0){
			esperaBajar.release();
		}
		else{
			//esperaVacio.release();
			esperaSubir.release();	// ya se han bajado todos, asi que pueden subir
		}

		mutex.release();
	}

	//CS-Control: espera hasta que este lleno
	public void esperaLleno() throws InterruptedException{
		//Se espera a que el coche este lleno
		esperaLleno.acquire();
	}

	public void finVuelta() throws InterruptedException {
		//Avisa a los pasajeros que deben bajar
		esperaBajar.release();
	}
}