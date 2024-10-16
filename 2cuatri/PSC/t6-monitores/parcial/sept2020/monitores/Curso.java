package sept2020.monitores;

public class Curso {

	private final int MAX_ALUMNOS_INI = 10;
	private final int ALUMNOS_AV = 3;
	
	private int conexionIni = 0;
	private int conexionAva = 0;
	private boolean grupoAvanzado = false;
	
	public synchronized void esperaPlazaIniciacion(int id) throws InterruptedException{
		while (conexionIni == MAX_ALUMNOS_INI)
			wait();
		
		conexionIni++;
		System.out.println("PARTE INICIACION: Alumno " + id + " cursa parte principiantes");		
	}

	public synchronized void finIniciacion(int id) throws InterruptedException{
		conexionIni--;
		notifyAll();
		System.out.println("PARTE INICIACION: Alumno " + id + " termina parte principiantes");		
	}
	
	public synchronized void esperaPlazaAvanzado(int id) throws InterruptedException{
		//Espera mientras no haya plazas libres
		while(grupoAvanzado) //Espera mientras ya haya un grupo avanzado formado
			wait();
		conexionAva++;
		System.out.println("PARTE AVANZADA: Alumno " + id + " espera a que haya " + ALUMNOS_AV + " alumnos");
		if(conexionAva==3) {
			grupoAvanzado = true;
			notifyAll();
			System.out.println("PARTE AVANZADA: Hay " + ALUMNOS_AV + " alumnos. Alumno " + id + " empieza el proyecto");
		}else {			
			//Espera a que el grupo se forme
			while(conexionAva<ALUMNOS_AV)
				wait();
		}
	}
	
	public synchronized void finAvanzado(int id) throws InterruptedException{
		//Espera a que los alumnos terminen su parte avanzada
		conexionAva--;
		System.out.println("PARTE AVANZADA: Alumno " +  id + " termina su parte del proyecto. Espera al resto");
		if(conexionAva ==0)
		{
			System.out.println("PARTE AVANZADA: Los " + ALUMNOS_AV + " alumnos han terminado su proyecto");
			grupoAvanzado = false;
			notifyAll();
		}else {			
			while(grupoAvanzado)
				wait();
		}
	}
}
