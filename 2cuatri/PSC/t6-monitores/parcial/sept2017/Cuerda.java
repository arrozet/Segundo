package sept2017;


import java.util.concurrent.locks.*;

public class Cuerda {
	
	Lock l = new ReentrantLock(true);

	int MAX_BABUINOS = 3;
	int sur=0;
	int norte=0;
	Condition babuSur = l.newCondition();
	boolean surEnCuerda=false;
	Condition babuNorte = l.newCondition();
	boolean norteEnCuerda=false;
	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionNS(int id) throws InterruptedException{
		try{
			l.lock();
			while(surEnCuerda || norte==MAX_BABUINOS){
				babuNorte.await();
			}
			norteEnCuerda=true;
			norte++;
			System.out.println("NORTE: entra babuino " + id + ". Son "+ norte);
		}finally {
			l.unlock();
		}

	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionNS(int id) throws InterruptedException{
		try{
			l.lock();
			norte--;
			if(norte==0){
				norteEnCuerda=false;
				babuSur.signalAll();
				babuNorte.signalAll();
			}
			System.out.println("	NORTE: sale babuino " + id + ". Son "+ norte);
		}finally {
			l.unlock();
		}
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
		try{
			l.lock();
			while(norteEnCuerda || sur==MAX_BABUINOS){
				babuSur.await();
			}
			surEnCuerda=true;
			sur++;
			System.out.println("SUR: entra babuino " + id + ". Son "+ sur);
		}finally {
			l.unlock();
		}
	}

	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionSN(int id) throws InterruptedException{
		try{
			l.lock();
			sur--;
			if(sur==0){
				surEnCuerda=false;
				babuNorte.signalAll();
				babuSur.signalAll();
			}
			System.out.println("	SUR: sale babuino " + id + ". Son "+ sur);
		}finally {
			l.unlock();
		}
	}	
		
}
