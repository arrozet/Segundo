package parcialjun2020.esqueleto;


import java.util.concurrent.locks.*;

public class Huerto { //Recurso
	
	private int N; //distancia máxima entre David y Fran
	Lock l = new ReentrantLock(true);

	int semilla=0;
	int hoyo=0;
	int hechos=0;
	Condition juanSemillero = l.newCondition();
	Condition franRellenador = l.newCondition();
	Condition davidCavador = l.newCondition();
	boolean pala = false;
	public Huerto(int tam){
		N = tam;
	}
	
	/**
	 * David espera en este método para poder empezar a hacer 
	 * un hoyo. Tiene que  esperar si está alejado N hoyos sin rellenar de Fran y,
	 * opcionalmente, si la pala compartida está siendo utilizada.
	 */
	public  void esperaHacerHoyo() throws InterruptedException{
		try{
			l.lock();
			while(pala || hoyo+semilla==N){
				davidCavador.await();
			}
			pala=true;
		}finally {
			l.unlock();
		}

	}
	
	/**
	 * David ha hecho el  hoyo número num. Actualiza el recurso
	 * para informar a Juan y a Fran.
	 * @param num
	 */
	public  void finHacerHoyo(int num) throws InterruptedException{
		try{
			l.lock();
			hoyo++;
			hechos++;
			System.out.println("David hace un hoyo. Hay " + hoyo + " hoyos y " + semilla + " semillas.");
			System.out.println("Se han hecho " + hechos);
			pala=false;	// suelto la pala
			juanSemillero.signalAll();
			if(semilla>0){	// hay algo que rellenar
				franRellenador.signalAll();
			}

		}finally {
			l.unlock();
		}
	}
	
	/**
	 * Juan espera en este método para poder echar semillas a 
	 * un hoyo. Debe esperar si no hay un hoyo sin semillas.
	 */
	public  void esperaPonerSemilla() throws InterruptedException{
		try{
			l.lock();
			while(hoyo==0){
				juanSemillero.await();
			}
		}finally {
			l.unlock();
		}
	}
	
	/**
	 * Juan ha puesto semillas en el  hoyo número num. 
 	 * Actualiza el recurso para informar Fran.
	 * @param num
	 */
	public  void finPonerSemilla(int num) throws InterruptedException{
		try{
			l.lock();
			semilla++;
			hoyo--;
			System.out.println("	Juan pone una semilla. Hay " + hoyo + " hoyos y " + semilla + " semillas.");

			if(hoyo+semilla<N){	// esta a distancia ok
				davidCavador.signalAll();
			}
			franRellenador.signalAll();
		}finally {
			l.unlock();
		}
	}
	
	/**
	 * Fran espera en este método para poder rellenar 
	 * un hoyo. Espera si no hay un hoyo con semilla no relleno
	 *  y, opcionalmente, si la pala no está libre.
	 */
	public  void esperaEcharTierra() throws InterruptedException{
		try{
			l.lock();
			while(pala || semilla==0){
				franRellenador.await();
			}
			pala=true;
		}finally {
			l.unlock();
		}
	}
	
	/**
	 * Fran ha rellenado el  hoyo número num. 
 	 * Actualiza el recurso para informar a Juan y a David.
	 * @param num
	 */
	public  void finEcharTierra(int num) throws InterruptedException{
		try{
			l.lock();
			semilla--;
			System.out.println("		Fran tapa un hoyo con semilla. Hay " + hoyo + " hoyos y " + semilla + " semillas.");
			pala=false;	// suelto la pala
			davidCavador.signalAll();
			if(hoyo>0){	// hay algo que plantar
				juanSemillero.signalAll();
			}

		}finally {
			l.unlock();
		}
	}
	


}
