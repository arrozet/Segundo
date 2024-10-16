package _4TallerMecanicoSemaforos;

import java.util.concurrent.Semaphore;



public class Taller {
	private Semaphore mecanico = new Semaphore(0);
	private Semaphore administrativo = new Semaphore(0);
	private Semaphore clienteEntra = new Semaphore(1);
	private Semaphore clienteSalir = new Semaphore(0);

	private Semaphore mutex = new Semaphore(0);
	public Taller() {

	}
	
	//CS-Mecanico: Espera hasta que el cliente ha subido su coche a la plataforma
	public void esperaParaRevisar() throws InterruptedException{
		mecanico.acquire();
		System.out.println("	Hay un coche en la plataforma. El mecanico empieza su trabajo");
	}
	
	public void finRevision(){
		System.out.println("	Fin de la revision. El mecanico termina su trabajo");
		administrativo.release();
	}

	//CS-Administrativo: Espera hasta que el mecanimo le avisa que ha terminado de revisar el coche
	public void esperaParaFacturar() throws InterruptedException{
		administrativo.acquire();
		System.out.println("	Hay una factura que hacer. El administrativo empieza su trabajo");
	}
	
	public void finFactura(){
		System.out.println("	Fin de la factura. El administrativo termina su trabajo");
		clienteSalir.release();
	}	
	
	public void revisarCoche(int id) throws InterruptedException{
		//CS_Cliente1: Espera a que le avisen de que puede entrar al taller
		//System.out.println("El cliente " + id + " llega al taller y espera su turno");
		clienteEntra.acquire();
		//System.out.println("");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!\nEl cliente " + id + " sube el coche a la plataforma\n!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		mecanico.release();
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		clienteSalir.acquire();
		//CS_Cliente2: Espera a que le avisen de que el coche y la factura están listas
		System.out.println("----------");
		System.out.println("El cliente " + id + " recoge su coche");
		System.out.println("----------");

		clienteEntra.release();
	}
}
