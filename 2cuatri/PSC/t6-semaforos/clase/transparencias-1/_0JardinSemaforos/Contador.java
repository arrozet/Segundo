package _0JardinSemaforos;

import java.util.concurrent.Semaphore;
public class Contador {
    private int cont = 0;
    private Semaphore mutex = new Semaphore(1);
    
    public void inc() throws InterruptedException{
        mutex.acquire();
        //SC
        cont++;
        mutex.release();
    }

    public int valor(){
          return cont;
    }
}
