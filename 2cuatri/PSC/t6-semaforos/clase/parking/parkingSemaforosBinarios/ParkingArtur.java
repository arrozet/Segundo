package parkingSemaforosBinarios;

import java.util.concurrent.Semaphore;

public class ParkingArtur {
	private Semaphore s = new Semaphore(1);
	int nPlazas;
    

    public ParkingArtur(int plazas) {
    	nPlazas= plazas;
    }
    
    public void entrar(int id) throws InterruptedException {
    	System.out.println("El coche " + id + " quiere aparcar");
    	
    	s.acquire();
    	nPlazas--;
    	if(nPlazas>0) s.release();
    
        System.out.println("	El coche " + id + " ha aparcado");
    }
    
    public void salir(int id) throws InterruptedException {
    	System.out.println("	El coche " + id + " ha abandonado el parking");
    	nPlazas++;
    	if(nPlazas == 1) s.release();
    
    }
}