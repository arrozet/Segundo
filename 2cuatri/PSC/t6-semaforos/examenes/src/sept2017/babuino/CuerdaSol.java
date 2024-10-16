package sept2017.babuino;
import java.util.concurrent.Semaphore;

public class CuerdaSol {
    private int numBabuinosNS = 0;
    private int numBabuinosSN = 0;

    private Semaphore babuinosNS = new Semaphore(1,true);
    private Semaphore babuinosSN = new Semaphore(1,true);
    private Semaphore espera = new Semaphore(1,true);
    private Semaphore mutexNS = new Semaphore(1,true);
    private Semaphore mutexSN = new Semaphore(1,true);
    /**
     * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
     * cuerda en dirección Norte-Sur
     * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
     * las dos condiciones de sincronización
     * @param id del babuino que entra en la cuerda
     * @throws InterruptedException
     */
    public  void entraDireccionNS(int id) throws InterruptedException{
        babuinosNS.acquire();
        mutexNS.acquire();
        numBabuinosNS++; //todavia no ha entrado

        if (numBabuinosNS == 1){ //Es el primero en intentar entrar
            espera.acquire(); //o se bloquea aqui o le cierra el paso a los del otro extremo
        }

        //cuando consigue entrar si son menos de 3 le permite entrar a otro
        if (numBabuinosNS < 3) babuinosNS.release();

        System.out.println("   Babuino " + id + " ha subido NS");
        mutexNS.release();
    }
    /**
     * Utilizado por un babuino cuando quiere cruzar el cañón  colgándose de la
     * cuerda en dirección Norte-Sur
     * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
     * las dos condiciones de sincronización
     * @param id del babuino que entra en la cuerda
     * @throws InterruptedException
     */
    public  void entraDireccionSN(int id) throws InterruptedException{
        babuinosSN.acquire();
        mutexSN.acquire();
        numBabuinosSN++; //todavia no ha entrado

        if (numBabuinosSN == 1){ //Es el primero en entrar
            espera.acquire(); //no libera babuinosNS para que el resto se quede bloqueado ahi
        }

        if (numBabuinosSN < 3) babuinosSN.release();

        System.out.println("   Babuino " + id + " ha subido SN");
        mutexSN.release();
    }
    /**
     * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
     * @param id del babuino que sale de la cuerda
     * @throws InterruptedException
     */
    public  void saleDireccionNS(int id) throws InterruptedException{
        //Salen los babuinos, sin seguir ningún orden
        mutexNS.acquire();
        numBabuinosNS--;
        System.out.println("   Babuino " + id + " sale NS");
        if (numBabuinosNS == 0) {
            espera.release();
            babuinosNS.release();
        }
        mutexNS.release();
    }

    /**
     * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
     * @param id del babuino que sale de la cuerda
     * @throws InterruptedException
     */
    public  void saleDireccionSN(int id) throws InterruptedException{
        //Salen los babuinos, sin seguir ningún orden
        mutexSN.acquire();
        numBabuinosSN--;

        System.out.println("   Babuino " + id + " sale SN");
        if (numBabuinosSN == 0) {
            espera.release();
            babuinosSN.release();
        }
        mutexSN.release();
    }

}