package sept2020.esqueleto;

public class Curso {

	//Numero maximo de alumnos cursando simultaneamente la parte de iniciacion
	private final int MAX_ALUMNOS_INI = 10;
	int iniciacion=0;
	int avanzado=0;
	boolean ocupado=false;

	
	//Numero de alumnos por grupo en la parte avanzada
	private final int ALUMNOS_AV = 3;
	
	
	//El alumno tendra que esperar si ya hay 10 alumnos cursando la parte de iniciacion
	public synchronized void esperaPlazaIniciacion(int id) throws InterruptedException{
		//Espera si ya hay 10 alumnos cursando esta parte
		while(iniciacion==MAX_ALUMNOS_INI){
			wait();
		}
		iniciacion++;
		System.out.println("PARTE INICIACION: Alumno " + id + " cursa parte iniciacion. Hay " + iniciacion);
	}

	//El alumno informa que ya ha terminado de cursar la parte de iniciacion
	public synchronized void finIniciacion(int id) throws InterruptedException{
		//Mensaje a mostrar para indicar que el alumno ha terminado la parte de principiantes
		iniciacion--;
		System.out.println("	PARTE INICIACION: Alumno " + id + " termina parte iniciacion. Hay " + iniciacion);
		//if(iniciacion==MAX_ALUMNOS_INI-1){
		notifyAll();
		//} // En verdad no hace falta el if

		//Libera la conexion para que otro alumno pueda usarla
	}
	
	/* El alumno tendra que esperar:
	 *   - si ya hay un grupo realizando la parte avanzada
	 *   - si todavia no estan los tres miembros del grupo conectados
	 */
	public synchronized void esperaPlazaAvanzado(int id) throws InterruptedException{
		//Espera a que no haya otro grupo realizando esta parte
		while(ocupado){
			wait();
		}
		//Espera a que haya tres alumnos conectados

		avanzado++;
		if(avanzado<ALUMNOS_AV){
			System.out.println("❗PARTE AVANZADA: Alumno " + id + " espera a que haya " + ALUMNOS_AV + " alumnos");
			while(avanzado<ALUMNOS_AV){
				wait();
			}
		}
		else{
			System.out.println("❗PARTE AVANZADA: Hay " + ALUMNOS_AV + " alumnos. Alumno " + id + " empieza el proyecto");
			ocupado=true;
			notifyAll();//Despierto a mis colegas dormidos (como yo ahora mismo)
		}
	}
	
	/* El alumno:
	 *   - informa que ya ha terminado de cursar la parte avanzada 
	 *   - espera hasta que los tres miembros del grupo hayan terminado su parte 
	 */ 
	public synchronized void finAvanzado(int id) throws InterruptedException{
		//Espera a que los 3 alumnos terminen su parte avanzada
		avanzado--;
		if(avanzado>0){
			System.out.println("	❗PARTE AVANZADA: Alumno " +  id + " termina su parte del proyecto. Espera al resto");
			while(avanzado>0){
				wait();
			}
		}
		else{
			System.out.println("	❗PARTE AVANZADA: LOS " + ALUMNOS_AV + " ALUMNOS HAN TERMINADO EL CURSO. Dice alumno " + id);
			notifyAll();
			ocupado=false;
		}
	}
}
