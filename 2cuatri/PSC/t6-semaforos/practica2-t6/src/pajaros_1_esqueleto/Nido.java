package pajaros_1_esqueleto;
import java.util.concurrent.Semaphore;

public class Nido {
	private final int maxBichos;
	private int bichos;
	private Semaphore mutex = new Semaphore(1,true);
	private Semaphore deposito = new Semaphore(1,true);
	private Semaphore comer = new Semaphore(0,true);
	public Nido(int max) {
		maxBichos = max;
		bichos = 0;
	}
	
	public void depositarBicho(int id) throws InterruptedException {

		deposito.acquire();
		mutex.acquire();
		bichos++;
		System.out.println("😁Deposito un bichito " + bichos);
		if(bichos<maxBichos){
			deposito.release();
		}
		if(bichos == 1){
			comer.release();
		}

		mutex.release();
	}
	
	public void comerBicho(int id) throws InterruptedException {
		comer.acquire();
		mutex.acquire();

		System.out.println("	😋Me como un bicho " + bichos);
		bichos--;

		if(bichos>0){
			comer.release();
		}
		mutex.release();
	}
	
}

