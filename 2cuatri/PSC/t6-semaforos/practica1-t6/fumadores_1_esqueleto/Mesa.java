package fumadores_1_esqueleto;

import java.util.concurrent.Semaphore;

public class Mesa {
	//0 (Papel) /1 (Tabaco) /2 (Cerillas);	
	//ingrediente == 0 - en la mesa están los ing. 1 y 2	
	//ingrediente == 1 - en la mesa están los ing. 0 y 2	
	//ingrediente == 2 - en la mesa están los ing. 0 y 1
	private int ingrediente; 
	private Semaphore[] fumador = new Semaphore[3];
	private Semaphore mutex = new Semaphore(1,true);
	private Semaphore agente = new Semaphore(1,true);
	
	public Mesa() {
		ingrediente = -1;	 //-1 indica que no hay nada en la mesa
		for(int i=0; i<3; i++){
			fumador[i] = new Semaphore(0,true);
		}
	}

	/* CS_Fumador id 0 - el fumador con id == 0 tiene que esperar si el ingrediente que falta en la mesa
	                  no es el 0. O dicho de otra manera, si en la mesa no están los ingredientes que le 
					  faltan a él (los ingredientes 1 y 2)
	*/
	/* CS_Fumador id 1 - el fumador con id == 1 tiene que esperar si el ingrediente que falta en la mesa
	                   no es el 1. O dicho de otra manera, si en la mesa no están los ingredientes que le 
    				   faltan a él (los ingredientes 0 y 2)
    */
	/* CS_Fumador id 2 - el fumador con id == 2 tiene que esperar si el ingrediente que falta en la mesa
	                    no es el 2. O dicho de otra manera, si en la mesa no están los ingredientes que le 
						faltan a él (los ingredientes 0 y 1)
	*/
	public  void quiereFumar(int id) throws InterruptedException {
		// ¿Quién fuma? El puma
		fumador[id].acquire();
		System.out.println("Fumador "+id+" empieza a fumar");
	}

	/* El fumador id indica que ha terminado de fumar */
	public void terminaFumar(int id) {
		System.out.println("Fumador "+id+" termina de fumar ");
		agente.release();
	}

	// CS_Agente - El agente tiene que esperar si la mesa no está vacía
	public void poneIngrediente(int ing) throws InterruptedException {
		ingrediente=ing;
		agente.acquire();
		System.out.println("Agente pone ingredientes que necesita fumador "+ingrediente);
		fumador[ing].release();
	}
}
