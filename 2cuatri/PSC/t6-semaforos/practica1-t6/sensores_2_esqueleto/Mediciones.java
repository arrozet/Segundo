package sensores_2_esqueleto;

import java.util.concurrent.*;

public class Mediciones {
    private int mediciones [];
    private int numMediciones = 0;
	private Semaphore trabajador = new Semaphore(0,true);
	private Semaphore barrera = new Semaphore(0,true);
	private Semaphore sigIteracion = new Semaphore(1);
	private Semaphore mutex = new Semaphore(1,true);
	public Mediciones(){
		mediciones = new int[3];

	}
	
	public void nuevaMedicion(int id, int valor) throws InterruptedException {
		sigIteracion.acquire();
		mutex.acquire();

		mediciones[id] = valor;
		numMediciones++;

		if(numMediciones==3){
			trabajador.release();
		}
		else{
			sigIteracion.release();
		}
		mutex.release();
		barrera.acquire();	// dejo bloqueado

		// DESPERTADO EN CASCADA
		mutex.acquire();
		// cuando libero mi proceso, hace despertado en cascada, por si todavía hay algunas BLOQUEADAS
		numMediciones--;
		if(numMediciones>0){
			barrera.release();
		}else{
			sigIteracion.release();
		}
		mutex.release();
	}
	
	public int[] leerMediciones() throws InterruptedException{
		//El trabajador se debe quedar bloqueado mientras no haya mediciones
		trabajador.acquire();
		System.out.println("El trabajador lee las mediciones");
		
		return mediciones;
	}
	
	public void finTarea() throws InterruptedException{
		//El trabajador termina y despierta a los tres sensores
		mutex.acquire();
		System.out.println("El trabajador termina su tarea");
		// libero 1 sensor. Los demás se liberarán con despertado en cascada
		barrera.release();
		mutex.release();
	}

}
