package atomosAgua_3_esqueleto;

import java.util.concurrent.Semaphore;

public class GestorAgua {	
	private int nO = 0;
	private int nH = 0;
	private Semaphore barrera = new Semaphore(0);
	private Semaphore oxigeno = new Semaphore(1);
	private Semaphore hidrogeno = new Semaphore(1);
	private Semaphore mutex = new Semaphore(1);

	public void hListo(int id) throws InterruptedException {
		hidrogeno.acquire();
		mutex.acquire();

		nH++;
		System.out.println("🕷Añado hidrógeno número " + nH);
		if(nH<2){
			hidrogeno.release();
		}
		else if (nH == 2 && nO==1){	// tengo suficientes hidrogenos y oxigenos
			barrera.release();
		}

		mutex.release();
		barrera.acquire();// lo dejo pillado en la barrera

		//CODIGO BARRERA
		mutex.acquire();
		System.out.println("		Despierto a hidrógeno " +  nH);
		nH--;

		formarMolecula();

		mutex.release();
	}
	
	public void oListo(int id) throws InterruptedException {
		oxigeno.acquire();	// oxigeno se queda pillado
		//mutex.acquire();

		nO++;
		System.out.println("🤑El oxígeno está listo");
		if(nH==2 && nO==1){
			barrera.release();
		}

		//CODIGO BARRERA
		barrera.acquire();		// me espero en la barrera

		mutex.acquire();
		System.out.println("		Despierto a oxígeno");
		nO--;
		formarMolecula();
		mutex.release();
	}

	public void formarMolecula(){
		if(nH>0 || nO>0){	// despertado en cascada
			barrera.release();
		}
		else if (nH==0 && nO==0){	// ya he usado los hidrogenos y oxigenos que tenia
			System.out.println("-----Creo la molécula-----");
			oxigeno.release();	// permito que entren oxigenos
			hidrogeno.release();	// permito que entren hidrogenos
		}
	}
}