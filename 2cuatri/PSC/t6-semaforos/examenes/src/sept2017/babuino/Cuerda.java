package sept2017.babuino;


import java.util.concurrent.Semaphore;

public class Cuerda {

	private Semaphore norte_entrar = new Semaphore(1,true);
	private Semaphore sur_entrar = new Semaphore(1,true);
	private Semaphore cruzando = new Semaphore(1,true);	// al principio no hay nadie cruzando
	private Semaphore mutex = new Semaphore(1,true);

	private int babuinosSur = 0;
	private int babuinosNorte = 0;
	

	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionNS(int id) throws InterruptedException{
		norte_entrar.acquire();
		mutex.acquire();
		if(babuinosNorte==0){	// es el primero que entra
			mutex.release();
			cruzando.acquire();
			mutex.acquire();
		}

		babuinosNorte++;
		System.out.println("Norte: babuino " + id + " ha entrado a la cuerda");
		if(babuinosNorte<3){
			norte_entrar.release();
		}
		mutex.release();
	}
	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón  colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionSN(int id) throws InterruptedException{
		sur_entrar.acquire();
		mutex.acquire();
		if(babuinosSur==0){	// es el primero que entra
			mutex.release();
			cruzando.acquire();
			mutex.acquire();
		}

		babuinosSur++;
		System.out.println("Sur: babuino " + id + " ha entrado a la cuerda");
		if(babuinosSur<3){
			sur_entrar.release();
		}
		mutex.release();
	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionNS(int id) throws InterruptedException{
		mutex.acquire();
		babuinosNorte--;
		System.out.println("	Norte: babuino " + id + " ha salido de la cuerda");
		if(babuinosNorte==0){
			cruzando.release();
			norte_entrar.release();
		}
		mutex.release();
	}
	
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionSN(int id) throws InterruptedException{
		mutex.acquire();
		babuinosSur--;
		System.out.println("	Sur: babuino " + id + " ha salido de la cuerda");
		if(babuinosSur==0){
			cruzando.release();
			sur_entrar.release();
		}
		mutex.release();
	}	
		
}
