package parkingSemaforosBinarios;

import java.util.concurrent.Semaphore;

public class Parking {
    
    private Semaphore s;
    private Semaphore mutex = new Semaphore(1,true);
    private int nPlazas;
    public Parking(int plazas) {
        s = new Semaphore(1, true);
        nPlazas = plazas;
    }
    
    public void entrar(int id) throws InterruptedException {
    	System.out.println("El coche " + id + " quiere aparcar");
        s.acquire();
        mutex.acquire();
        nPlazas--;
        if(nPlazas>0){
            s.release();
        }
        System.out.println("	El coche " + id + " ha aparcado");
        mutex.release();
    }
    
    public void salir(int id) throws InterruptedException {
        mutex.acquire();
    	System.out.println("	El coche " + id + " ha abandonado el parking");

        nPlazas++;
        if(nPlazas==1){ // ya quedan plazas libres
            s.release();
        }
        mutex.release();
    }
}