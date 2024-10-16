
package mrusa.semaforosV2;

import java.util.SortedMap;
import java.util.concurrent.Semaphore;

public class Coche{
	//CS1_Pasajero 	=> Espera hasta que haya un hueco para subir		-> esperaSubir
	private Semaphore esperaSubir = new Semaphore(1,true);;
	//CS2_Pasajero 	=> Espera hasta que termine la vuelta para bajar	-> esperaBajar
	private Semaphore esperaBajar = new Semaphore(0,true);
	//CS_Control   	=> Espera hasta que la atracción esté llena			-> esperaLleno
	private Semaphore esperaLleno = new Semaphore(0,true);
	private Semaphore esperaVacio = new Semaphore(0,true);
	private Semaphore mutex = new Semaphore(1,true);
	private Semaphore mutex2 = new Semaphore(1,true);
	private int asientos; 	//Capacidad del coche
	
	private int numPas = 0;	
	
	
	public Coche(int tam){
		asientos = tam;
	}
	
	public Coche(){
		asientos = 5;
	}
	
	//CS1-Pasajero: espera hasta que se abra la puerta
	public void esperaSubir(int id) throws InterruptedException{
		esperaSubir.acquire();
		mutex.acquire();
		numPas++;

		if(numPas<asientos){
			esperaSubir.release();	//si hay asientos libres, se pueden seguir subiendo
		}else{
			//esperaVacio.acquire();
			esperaLleno.release();
		}
		System.out.println("El pasajero " + id + " sube al coche");
		mutex.release();
	}

	//CS2-Pasajero: espera hasta que pueda bajar
	public void esperaBajar(int id) throws InterruptedException{
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
		System.out.println("--------------------");
		System.out.println("Atraccion funcionando");
		System.out.println("--------------------");
	}

	public void finVuelta() throws InterruptedException {
		//Avisa a los pasajeros que deben bajar
		System.out.println("Pasajeros, bájense");
		esperaBajar.release();
		//while(numPas>0) Thread.yield();
		//esperaVacio.acquire();
		//esperaSubir.acquire();
	}
}