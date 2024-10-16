package canibales_3_esqueleto;

import java.util.concurrent.*;

public class Olla implements IOlla{

	private int numRac = 10;
	private Semaphore cocinero = new Semaphore(0,true);
	private Semaphore comer = new Semaphore(1,true);
	private Semaphore mutex = new Semaphore(1,true);
	public void nuevoExplorador() throws InterruptedException{
		// solo hay 1 cocinero, no hace falta mutex
		cocinero.acquire();
		numRac=10;
		System.out.println("A cocinar! 😋 ");
		comer.release();
	}	
	
	public void comeRacion(int id) throws InterruptedException{
		mutex.acquire();
		comer.acquire();
		System.out.println("Me como " + numRac);
		numRac--;

		if(numRac==0){
			cocinero.release();
		}
		else{
			comer.release();
		}
		mutex.release();
	}

}