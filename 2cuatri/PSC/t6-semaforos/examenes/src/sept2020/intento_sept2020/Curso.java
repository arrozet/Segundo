package sept2020.intento_sept2020;

import java.util.concurrent.Semaphore;

public class Curso {

	//Numero maximo de alumnos cursando simultaneamente la parte de iniciacion
	private final int MAX_ALUMNOS_INI = 10;
	
	//Numero de alumnos por grupo en la parte avanzada
	private final int ALUMNOS_AV = 3;
	private int nIniciacion=0;
	private int nAvanzado=0;
	private int nTerminado=0;
	private Semaphore iniciacion = new Semaphore(1);
	private Semaphore mutex = new Semaphore(1);
	private Semaphore esperando = new Semaphore(0);
	private Semaphore buscando = new Semaphore(1);
	private Semaphore terminado = new Semaphore(0);


	//El alumno tendra que esperar si ya hay 10 alumnos cursando la parte de iniciacion
	public void esperaPlazaIniciacion(int id) throws InterruptedException{
		//Espera si ya hay 10 alumnos cursando esta parte
		mutex.acquire();
		iniciacion.acquire();

		//Mensaje a mostrar cuando el alumno pueda conectarse y cursar la parte de iniciacion
		nIniciacion++;
		System.out.println("PARTE INICIACION: Alumno " + id + " 📙cursa📙 parte iniciacion. Quedan " + nIniciacion + " alumnos");

		if(nIniciacion<MAX_ALUMNOS_INI) iniciacion.release();
		mutex.release();

	}

	//El alumno informa que ya ha terminado de cursar la parte de iniciacion
	public void finIniciacion(int id) throws InterruptedException{
		//Mensaje a mostrar para indicar que el alumno ha terminado la parte de principiantes
		mutex.acquire();
		nIniciacion--;
		System.out.println("PARTE INICIACION: Alumno " + id + " 🐚termina🐚 parte iniciacion. Quedan " + nIniciacion + " alumnos");


		//Libera la conexion para que otro alumno pueda usarla
		if(nIniciacion == MAX_ALUMNOS_INI-1) iniciacion.release();	// solo cuando iniciación se haya quedado pillado
		mutex.release();
	}
	
	/* El alumno tendra que esperar:
	 *   - si ya hay un grupo realizando la parte avanzada
	 *   - si todavia no estan los tres miembros del grupo conectados
	 */
	public void esperaPlazaAvanzado(int id) throws InterruptedException{
		//Espera a que no haya otro grupo realizando esta parte
		buscando.acquire();
		mutex.acquire();
		//Espera a que haya tres alumnos conectados

		nAvanzado++;
		//Mensaje a mostrar si el alumno tiene que esperar al resto de miembros en el grupo
		System.out.println("	PARTE AVANZADA: Alumno " + id + " espera a que haya " + ALUMNOS_AV + " alumnos. Hay " + nAvanzado + " alumnos esperando");
		if(nAvanzado<ALUMNOS_AV){
			buscando.release();
			mutex.release();
			esperando.acquire();	// los bloqueo hasta que haya 3
			mutex.acquire();
		}
		else{
			esperando.release();	// si hay 3, a trabajar
		}
		mutex.release();

		// DESPERTADO EN CASCADA
		mutex.acquire();
		nAvanzado--;
		nTerminado++;
		if(nAvanzado>1){	// solo tengo que despertar a 1 hebra, pq 1 no se bloquea y la otra la despierta la que no se bloquea
			esperando.release();
		}
		else{
			terminado.release();
		}

		//Mensaje a mostrar cuando el alumno pueda empezar a cursar la parte avanzada
		System.out.println("	PARTE AVANZADA: Hay " + ALUMNOS_AV + " alumnos. Alumno " + id + " empieza el proyecto");
		mutex.release();
	}
	
	/* El alumno:
	 *   - informa que ya ha terminado de cursar la parte avanzada 
	 *   - espera hasta que los tres miembros del grupo hayan terminado su parte 
	 */ 
	public void finAvanzado(int id) throws InterruptedException{
		//Espera a que los 3 alumnos terminen su parte avanzada
		mutex.acquire();
		terminado.acquire();
		nTerminado--;
		System.out.println("	PARTE AVANZADA: Alumno " +  id + " termina su parte del proyecto. Espera al resto");
		//Mensaje a mostrar si el alumno tiene que esperar a que los otros miembros del grupo terminen
		if(nTerminado>0){
			terminado.release();
		}
		else{
			//Mensaje a mostrar cuando los tres alumnos del grupo han terminado su parte
			System.out.println("	PARTE AVANZADA: LOS " + ALUMNOS_AV + " ALUMNOS HAN TERMINADO EL CURSO");
			buscando.release();	// que sigan entrando alumnos
		}
		mutex.release();
	}
}
