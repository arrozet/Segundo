package jun2022.supermercado;

import java.util.concurrent.Semaphore;

public class SupermercadoSemaforos implements Supermercado {

	
	
	private Cajero permanente;
	private int clientes;
	private boolean cerrado = false;
	public Semaphore mutex = new Semaphore(1);
	public Semaphore clienteEspera = new Semaphore(0);
	public Semaphore permanenteCajero = new Semaphore(0);

	
	
	public SupermercadoSemaforos() throws InterruptedException {
		permanente = new Cajero(this, true); //crea el primer cajero, el permanente
		permanente.start();
		//cajeros = 1;
		clientes = 0;
		//TODO
	}

	@Override
	public void fin() throws InterruptedException {
		mutex.acquire();
		cerrado = true;
		System.out.println("							❗❗SUPERMERCADO CERRADO❗❗");
		if(clientes==0){
			permanenteCajero.release();
		}

		mutex.release();
	}

	@Override
	public void nuevoCliente(int id) throws InterruptedException {
		mutex.acquire();

		if(!cerrado){
			clientes++;
			System.out.println("Llega cliente " + id +  ". Hay " + clientes);
			if(clientes==1) {    // si está dormido, lo despierto
				permanenteCajero.release();
			}
			if(clientes>3*permanente.numCajeros()){	// si no tengo suficientes cajeros, los creo
				Cajero nuevo_cajero = new Cajero(this, false);
				nuevo_cajero.start();
				System.out.println("				!!!!Se crea un cajero nuevo " + nuevo_cajero.id() + "!!!!");

			}

			mutex.release();
			clienteEspera.acquire();	// para que se quede bloqueado
			mutex.acquire();
		}
		mutex.release();
	}

	@Override
	public boolean permanenteAtiendeCliente( int id) throws InterruptedException {
		permanenteCajero.acquire();
		mutex.acquire();

		if(clientes>0){
			clientes--;
			System.out.println("	Cajero permanente atiende a un cliente. Quedan " + clientes);
			clienteEspera.release();	// un cliente se puede ir, ha sido atendido
		}


		if(clientes==0){
			if(cerrado){
				System.out.println("SE ACABÓ. CAJERO PERMANENTE TERMINA");
			}
			else{
				System.out.println("	Cajero permanente espera💤");
			}
		}
		else{	// si hay clientes, asi que release para que siga trabajando
			permanenteCajero.release();
		}

		mutex.release();
		return !cerrado || clientes>0;	// si atiendo a un cliente, retorno true
	}
		
	
	@Override
	public boolean ocasionalAtiendeCliente(int id) throws InterruptedException {
		mutex.acquire();
		if(clientes>0){
			clientes--;
			System.out.println("	Cajero " + id + " atiende a un cliente. Quedan " + clientes);

			clienteEspera.release();	// un cliente se puede ir, ha sido atendido
			if(clientes==0){
				System.out.println("❌No hay clientes. Cajero " + id + " termina");
			}
		}
		mutex.release();
		return clientes>0;
	}

}
