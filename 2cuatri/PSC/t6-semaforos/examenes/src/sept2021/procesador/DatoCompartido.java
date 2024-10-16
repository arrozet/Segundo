package sept2021.procesador;

import java.util.concurrent.Semaphore;

public class DatoCompartido {
	private int dato; //Dato a procesar
	private int nProcesadores; //Numero de procesadores totales
	private int procPend; //Numero de procesadores pendientes de procesar el dato

	public Semaphore generador = new Semaphore(0);
	public Semaphore leer = new Semaphore(0);
	//public Semaphore actualizar = new Semaphore(0); -> NO HACE FALTA
	public Semaphore mutex = new Semaphore(1);
	public Semaphore barrera = new Semaphore(0);
	
	/* Recibe como par�metro el n�mero de procesadores que tienen que manipular 
	 * cada dato generado. Debe ser un n�mero mayor que 0. 
	 */
	public DatoCompartido(int nProcesadores) {
		this.nProcesadores = nProcesadores;
		this.procPend = nProcesadores;
	}
	
	/*  El Generador utiliza este metodo para almacenar un nuevo dato a procesar. 
	 *  Una vez almacenado el dato se debe avisar a los procesadores de que se ha 
	 *  almacenado un nuevo dato. 
	 *  
	 *  Por ultimo, el Generador tendra que esperar en este metodo a que todos los 
	 *  procesadores terminen de procesar el dato. Una vez que todos terminen, 
	 *  se devolvera el resultado del procesamiento, permitiendo al Generador 
	 *  la generacion de un nuevo dato.
	 * 
	 *  CS_Generador: Una vez generado un dato, el Generador espera a que todos los procesadores 
	 *  terminen antes de generar el siguiente dato
	 */
	public int generaDato(int d) throws InterruptedException {
		dato = d;
		System.out.println("Dato a procesar: " + dato);
		System.out.println("Numero de procesadores pendientes: " + procPend);
		leer.release();	// ya tienen el dato -> procesadores, a leer!

		generador.acquire();	// lo bloqueo hasta que se procesen los datos
		// puedo hacer esto asi porque solo hay 1 generador
		return dato;
	}

	/*  El Procesador con identificador id utiliza este metodo para leer el 
	 *  dato que debe procesar (el dato se devuelve como valor de retorno del metodo). 
	 *  Debera esperarse si no hay datos nuevos para procesar 
	 *  o si otro procesador esta manipulando el dato. 
	 * 
	 *  CS1_Procesador: Espera si no hay un nuevo dato que procesar. 
	 *                  Esto puede ocurrir porque el generador aun no haya almacenado 
	 *                  ningun dato o porque el Procesador ya haya procesado el dato 
	 *                  almacenado en ese momento en el recurso compartido.
	 *  CS2_Procesador: Espera a que el dato este disponible para poder procesarlo 
	 *                  (es decir, no hay otro Procesador procesando al dato)
	 */
	public int leeDato(int id) throws InterruptedException {
		leer.acquire();	// quien quiera leer que se espere a que haya un dato nuevo jjj
		System.out.println("	Procesador " + id + " lee el dato para procesarlo");
		// en el run de Procesador.java, cuando se ejecuta este codigo se hace entre medias dato=dato+1
		//actualizar.release();	// NO HACE FALTA, siempre se lee y despue actualiza

		return dato;
	}
	
	/*  El Procesador con identificador id almacena en el recurso compartido el resultado 
	 *  de haber procesado el dato. Una vez hecho esto actuara de una de las dos formas siguientes: 
	 *  
	 *  (1) Si aun hay procesadores esperando a procesar el dato los avisara, 
	 *  (2) Si el era el ultimo procesador avisara al Generador de que han terminado. 
	 * 
	 */
	public void actualizaDato(int id, int datoActualizado) throws InterruptedException {
		//actualizar.acquire();	// no hace falta, siempre se lee y actualiza despue jjj
		mutex.acquire();

		dato = datoActualizado;
		System.out.println("	Procesador " + id + " ha procesado el dato. Nuevo dato: " + dato);
		procPend--;
		System.out.println("Numero de procesadores pendientes: " + procPend);

		if(procPend>0){	// todavia tengo pendientes por procesar... pues que lean!
			leer.release();
		}
		else{	// si ya han procesado todos, que se vayan despertando en la barrera
			barrera.release();
		}
		mutex.release();

		// BARRERA
		barrera.acquire();	// a esperar pa que no se repita procesador!!
		mutex.acquire();
		procPend++;	// oh dios tengo 1 mas disponible
		if(procPend<nProcesadores){	// pero tengo suficientes? si no, sigo despertando!!
			barrera.release();
		}
		else{
			generador.release();	// ya estan todos, a trabajar!
		}
		mutex.release();
	}

	// JOGE ON TOP🔝
}
