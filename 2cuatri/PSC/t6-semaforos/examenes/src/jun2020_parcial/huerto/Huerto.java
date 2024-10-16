// AUTHOR: ROZ
package jun2020_parcial.huerto;


import java.util.concurrent.Semaphore;

public class Huerto { //Recurso
	
	private int N; //distancia máxima entre David y Fran
	private Semaphore juanSemillero;
	private Semaphore franRellenador;
	private Semaphore davidCavador;
	private Semaphore pala;
	private Semaphore mutex;
	private int hoyos=0;
	private int semillas=0;

	
	public Huerto(int tam){
		N = tam;

		//SEMÁFOROS
		davidCavador = new Semaphore(1);	// no hay ningún hoyo cavado
		franRellenador = new Semaphore(0);
		juanSemillero = new Semaphore(0);
		pala = new Semaphore(1);	// no la está usando nadie
		mutex = new Semaphore(1);
	}
	
	/**
	 * David espera en este método para poder empezar a hacer 
	 * un hoyo. Tiene que  esperar si está alejado N hoyos sin rellenar de Fran y,
	 * opcionalmente, si la pala compartida está siendo utilizada.
	 */
	public  void esperaHacerHoyo() throws InterruptedException{
		davidCavador.acquire();
		pala.acquire();
	}
	
	/**
	 * David ha hecho el  hoyo número num. Actualiza el recurso
	 * para informar a Juan y a Fran.
	 * @param num
	 */
	public  void finHacerHoyo(int num) throws InterruptedException{
		mutex.acquire();

		hoyos++;
		System.out.println("🐽"+ num +": David cava el hoyo número " + num + "🐽. Está a distancia " + (semillas+hoyos) + " de Fran");
		if(hoyos==1){	// si hay al menos 1 hoyo, se pueden poner semillas
			juanSemillero.release();
		}
		if(hoyos+semillas<N){	// si todavia no estoy a N posiciones de Fran, sigo cavando
			davidCavador.release();
		}
		pala.release();
		mutex.release();
	}
	
	/**
	 * Juan espera en este método para poder echar semillas a 
	 * un hoyo. Debe esperar si no hay un hoyo sin semillas.
	 */
	public  void esperaPonerSemilla() throws InterruptedException{
		juanSemillero.acquire();
	}
	
	/**
	 * Juan ha puesto semillas en el  hoyo número num. 
 	 * Actualiza el recurso para informar Fran.
	 * @param num
	 */
	public  void finPonerSemilla(int num) throws InterruptedException{
		mutex.acquire();
		semillas++;
		hoyos--;
		System.out.println("🌱"+ num +": Juan ha puesto una semilla en el hoyo " + num +"🌱. Entre Fran y David hay una distancia de " + (semillas+hoyos));
		if(hoyos>0){
			juanSemillero.release();	// si quedan hoyos, que siga plantando
		}
		franRellenador.release();	// ya hay semillas, que rellene
		//juanSemillero.release();
		mutex.release();

	}
	
	/**
	 * Fran espera en este método para poder rellenar 
	 * un hoyo. Espera si no hay un hoyo con semilla no relleno
	 *  y, opcionalmente, si la pala no está libre.
	 */
	public  void esperaEcharTierra() throws InterruptedException{
		franRellenador.acquire();
		pala.acquire();
	}
	
	/**
	 * Fran ha rellenado el  hoyo número num. 
 	 * Actualiza el recurso para informar a Juan y a David.
	 * @param num
	 */
	public  void finEcharTierra(int num) throws InterruptedException{	
		mutex.acquire();

		semillas--;
		System.out.println("👷‍"+ num +": ️Fran ha rellenado el hoyo " + num + "👷‍♂️. Está a distancia " + (semillas+hoyos) + " de David");
		if(semillas+hoyos==N-1){	// si antes tenia N huecos con David, David estaba esperando. Le digo que siga
			davidCavador.release();
		}
		System.out.println("Hay " + hoyos + " hoyos SIN semilla y " + semillas + " hoyos CON semilla.");

		pala.release();
		mutex.release();
	}
	


}
