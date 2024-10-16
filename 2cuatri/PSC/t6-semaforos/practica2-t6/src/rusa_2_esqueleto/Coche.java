package rusa_2_esqueleto;
import java.util.concurrent.Semaphore;

public class Coche {
	//CS1_Pasajero 	=> Espera hasta que haya un hueco para subir		-> esperaSubir
	private Semaphore esperaSubir = new Semaphore(1);
	//CS2_Pasajero 	=> Espera hasta que termine la vuelta para bajar	-> esperaBajar
	private Semaphore esperaBajar = new Semaphore(0);
	//CS_Control   	=> Espera hasta que la atracción esté llena			-> esperaLleno
	private Semaphore esperaLleno = new Semaphore(0);
	private Semaphore mutex = new Semaphore(1);

	private int numPas = 0;
	private int capacidad = 4;

	public Coche() {

	}

	//CS1-Pasajero: espera hasta que se abra la puerta
	public void quieroSubir(int id) throws InterruptedException {
		esperaSubir.acquire();
		mutex.acquire();
		numPas++;
		System.out.println("El pasajero " + id + " sube al coche");

		if(numPas<capacidad){
			esperaSubir.release();	//si hay asientos libres, se pueden seguir subiendo
		}else{
			esperaLleno.release();
		}

		mutex.release();
	}

	//CS2-Pasajero: espera hasta que pueda bajar
	public void quieroBajar(int id) throws InterruptedException {
		esperaBajar.acquire();
		mutex.acquire();
		numPas--;
		System.out.println("	Pasajero " + id + " baja del coche");
		if(numPas>0){
			esperaBajar.release();
		}
		else{
			esperaSubir.release();	// ya se han bajado todos, asi que pueden subir
		}

		mutex.release();
	}

	//CS-Control: espera hasta que este lleno
	public void inicioViaje() throws InterruptedException {
		esperaLleno.acquire();
		/*
		System.out.println("--------------------");
		System.out.println("Atraccion funcionando");
		System.out.println("--------------------");
		 */
	}
	
	public void finViaje() throws InterruptedException {
		System.out.println("Pasajeros, bájense");
		esperaBajar.release();
	}
}
