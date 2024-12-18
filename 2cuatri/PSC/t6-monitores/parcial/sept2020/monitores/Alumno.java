package sept2020.monitores;


public class Alumno extends Thread{
	private java.util.Random rnd = new java.util.Random();
	private int id;
	private Curso curso;
	
	public Alumno(int id, Curso curso){
		this.id = id;
		this.curso = curso;
	}
	
	public void run(){
		try {
			//Decide cuando iniciar el curso
			//Thread.sleep(rnd.nextInt(200));
			
			//Espera para tener una plaza en la parte de iniciación
			curso.esperaPlazaIniciacion(id);
			Thread.sleep(rnd.nextInt(100));
			curso.finIniciacion(id);

			//Espera para tener plaza en la parte avanzada
			curso.esperaPlazaAvanzado(id);
			Thread.sleep(rnd.nextInt(1000));
			curso.finAvanzado(id);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
