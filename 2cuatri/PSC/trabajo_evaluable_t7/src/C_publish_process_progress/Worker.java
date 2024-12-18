/*
* ALUMNO: Oliva Zamora, Rubén
* GRADO: Ingeniería del Software A
*/

package C_publish_process_progress;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

// Va publicando los resultados intermedios como Primos
public class Worker extends SwingWorker<Void, Primos> {

    private int n;
    private Panel panel; //Para actualizar la vista desde el A_done_get.Worker
    private KindOfPrime kind; // Para indicar el tipo de Primo a calcular

    public enum KindOfPrime {    // Enum que indica los tres tipos posibles de primos
        TWIN, COUSIN, SEXY
    }

    // Declaración de constantes de distancias de los distintos pares de primos
    private final long DIST_TWIN = 2;
    private final long DIST_COUSIN = 4;
    private final long DIST_SEXY = 6;

    public Worker(int n, Panel panel, KindOfPrime kind) {
        this.n = n;
        this.panel = panel;
        this.kind = kind;
    }

    private boolean isPrime(long n){
        boolean prime = true;

        if (n<=1){
            prime = false;
        } else if (n <= 3) {
            prime = true;
        }
        else if (n % 2 == 0 || n % 3 == 0){
            prime = false;
        }
        // Hasta aquí es preprocesamiento, para ahorrarme el bucle en muchos casos

        else{
            // Basta con revisar hasta la raíz cuadrada de n, ya que si n es primo, se puede factorizar en
            // dos factores (llamémoslos a, b), y ambos no pueden ser mayores que la sqrt(n), ya que eso significa que
            // a*b > sqrt(n) * sqrt(n) = n --> a*b > n --> a*b no son factores de n.
            // Luego, solo hace falta mirar hasta sqrt(n)

            int i = 5; // Hasta el 4 se comprueba en los pasos anteriores
            while (i <= (long) Math.sqrt(n) && prime){
                if (n % i == 0){
                    prime = false;
                }
                i++;
            }
        }
        return prime;
    }

    // Halla el siguiente primo a un número dado
    private long siguientePrimo(long numero) {
        long siguiente = numero + 1;
        while (!isPrime(siguiente)) {
            siguiente++;
        }
        return siguiente;
    }

    // Obtiene los pares de primos a una distancia dada
    private void obtenerPrimosDistancia(long dist){
        int numPrimos = 0;
        long primo1=2;
        long primo2 = siguientePrimo(primo1);

        while(numPrimos<n && !this.isCancelled()){
            // Hallo la distancia entre los primos
            long distEntrePrimos = primo2 - primo1;

            // Si todavia no he cubierto suficiente distancia, miro el siguiente primo, conservando el primero del par
            if (distEntrePrimos < dist) {
                primo2 = siguientePrimo(primo2);
            } else {
                // Si ya lo he hecho, y es la que busco, añado a la lista
                if (distEntrePrimos == dist) {
                    publish(new Primos(primo1, primo2, numPrimos));
                    numPrimos++;

                    // Actualizo el progreso
                    this.setProgress((numPrimos*100)/n);
                }
                // En cualquier caso, miro las siguientes parejas de primos
                primo1 = siguientePrimo(primo1);
                primo2 = siguientePrimo(primo1);
            }
        }

    }

    @Override
    protected Void doInBackground() throws Exception {

        // Dependiendo del tipo de hebra, tendré que hacer lo mismo con distancias distintas
        if(kind == KindOfPrime.TWIN){
            obtenerPrimosDistancia(DIST_TWIN);
        } else if (kind == KindOfPrime.COUSIN) {
            obtenerPrimosDistancia(DIST_COUSIN);
        }else{
            obtenerPrimosDistancia(DIST_SEXY);
        }

        return null;
    }

    public void done() {
        try {
            switch (kind) {
                case TWIN:
                    get();  // Espero a que termine de ejecutarse el doInBackground

                    if(n>0){
                        panel.mensajeTwin("Números twin calculados");
                    }
                    else{
                        panel.mensajeTwin("Número incorrecto o demasiado grande");
                        panel.progreso1(0);
                    }
                    break;
                case COUSIN:
                    get();

                    if(n>0){
                        panel.mensajeCousin("Números cousin calculados");
                    }else{
                        panel.mensajeCousin("Número incorrecto o demasiado grande");
                        panel.progreso2(0);
                    }
                    break;
                case SEXY:
                    get();

                    if(n>0){
                        panel.mensajeSexy("Números sexy calculados");
                    }else{
                        panel.mensajeSexy("Número incorrecto o demasiado grande");
                        panel.progreso3(0);
                    }
                    break;
            }

            // Antes lo hacía en el método "escribePrimos<PrimoQueCorresponda>", pero como ahora llamo
            // a este método continuamente, tengo que hacerlo una vez termine

            // Para no desactivar el botón de cancelar cuando haya más de una hebra aún funcionando
            int running = Controlador.getRunning();
            running--;
            Controlador.setRunning(running);
            if(running==0){
                panel.activarCancelar(false);
            }

        } catch (InterruptedException e) {
            System.out.println("tarea cancelada");
            e.printStackTrace();
        }catch (ExecutionException | CancellationException e){
            panel.limpiaAreaTwin();
            System.out.println("tarea cancelada");
        }
    }

    public void process(List<Primos> pares){
        try {
            if(!this.isCancelled()){
                switch (kind) {
                    case TWIN:
                        panel.escribePrimosTwin(pares);
                        break;
                    case COUSIN:
                        panel.escribePrimosCousin(pares);
                        break;
                    case SEXY:
                        panel.escribePrimosSexy(pares);
                        break;
                }
            }
        }catch(CancellationException e) {
            System.out.println("Tarea cancelada!!");
        }
    }
}