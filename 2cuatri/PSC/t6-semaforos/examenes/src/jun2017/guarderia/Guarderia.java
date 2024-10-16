package jun2017.guarderia;


import java.util.concurrent.Semaphore;

public class Guarderia {
	private int nBebe=0;
	private int nAdulto=0;
	private Semaphore bebeEntrar = new Semaphore(0);	// al principio ningun bebe puede entrar, pues no hay aun adultos
	private Semaphore adultoSalir = new Semaphore(0);	// al princupio ningun adulto puede salir
	private Semaphore mutex = new Semaphore(1);
	//private Semaphore mutexAdulto = new Semaphore(1);
	
	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void entraBebe(int id) throws InterruptedException{
		if(!(nBebe+1 <= 3*nAdulto)){	// si no se cumple, me espero
			bebeEntrar.acquire();
		}

		nBebe++;
		System.out.println("🍼Ha entrado el bebe " + id + ". Hay " + nBebe + "🍼");
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public void saleBebe(int id) throws InterruptedException{

		nBebe--;
		System.out.println("	🍼Ha salido el bebe " + id + ". Hay " + nBebe + "🍼");
		if(nBebe+1 == 3*nAdulto){	// si la condicion no se cumplia (ya no cabian mas)
			bebeEntrar.release();	// ahora si cabe
		}
		if(nBebe <= 3*(nAdulto-1)){

		}

	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public void entraAdulto(int id) throws InterruptedException{

		nAdulto++;
		System.out.println("😜Ha entrado el adulto " + id + ". Hay " + nAdulto + "😜");
		if(nAdulto == 1 && nBebe+1 <= 3*nAdulto){	// si es el primer adulto que entra
			bebeEntrar.release();
		}

		if(!(nBebe <= 3*(nAdulto-1))){	// si no se cumple, me espero
			adultoSalir.release();
		}

	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void saleAdulto(int id) throws InterruptedException{
		if(!(nBebe <= 3*(nAdulto-1))){	// si no se cumple, me espero
			adultoSalir.acquire();
		}
		nAdulto--;
		System.out.println("	😜Ha salido el adulto " + id + ". Hay " + nAdulto + "😜");

	}

}
