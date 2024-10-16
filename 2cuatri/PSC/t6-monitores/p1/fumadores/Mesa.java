package fumadores;

public class Mesa {
	private int ingr = -1; //el que no esta
	private boolean fumando = false;
	public synchronized void nuevoIngrediente(int i) throws InterruptedException {
		//i es el ingrediente que no se pone
		while(ingr!=-1 || fumando){	// no se ha consumido el ingrediente que ha puesto el agente ni hay nadie fumando
			wait();
		}

		ingr = i;
		System.out.println("El agente no pone "+ingr);
		notifyAll();
	}

	public synchronized void quieroFumar(int id) throws InterruptedException{
		//id espera los ingredientes que no tiene
		while(ingr!=id){
			wait();
		}
		System.out.println("Fumador "+id+" empieza a fumar");
		fumando = true;
		ingr = -1;
	}
	
	public synchronized void finFumar(int id){
		//el fumador id ha terminado de fumar
		fumando = false;
		notifyAll();
		System.out.println("Fumador "+id+" termina de fumar");
	}
}
