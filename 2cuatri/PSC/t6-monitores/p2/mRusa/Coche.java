package mRusa;
import java.util.concurrent.locks.*;
public class Coche{
	
	private int asientos; 	//Capacidad del coche
	private int pasajeros = 0;
	private Lock l = new ReentrantLock(true);
	Condition subirCoche = l.newCondition();
	boolean subir = true;
	Condition bajarCoche = l.newCondition();
	boolean bajar = false;
	Condition llenoCoche = l.newCondition();
	boolean lleno = false;
	
	public Coche(int tam){
		asientos = tam;
	}
	
	public Coche(){
		asientos = 5;
	}
	
	public void darVuelta(int id) throws InterruptedException {
		try{
			l.lock();
			// Espero para subir
			while(!subir){
				subirCoche.await();
			}
			// Me subo
			pasajeros++;
			System.out.println("Pasajero " + id + " se sube al coche. Hay " + pasajeros);
			if(pasajeros==asientos){	// Espero a que se llene para poner en marcha la atraccion
				subir=false;
				lleno=true;
				llenoCoche.signalAll();
			}

			// Espero para bajar
			while(!bajar){
				bajarCoche.await();
			}
			// Me bajo
			pasajeros--;
			System.out.println("	Pasajero " + id + " se baja del coche. Hay " + pasajeros);
			if(pasajeros==0){	// Espero a que se bajen todos para que se vuelvan a subir
				bajar = false;
				subir = true;
				subirCoche.signalAll();
			}
		}
		finally {
			l.unlock();
		}

	}
	
	public void esperaLleno() throws InterruptedException {
		try{
			l.lock();
			while(!lleno){
				llenoCoche.await();
			}
			lleno = false;
		}finally {
			l.unlock();
		}
	}
	
	public void finVuelta() throws InterruptedException {
		try{
			l.lock();
			bajarCoche.signalAll();
			bajar=true;
		}finally {
			l.unlock();
		}

	}
}