/*
 * ALUMNO: Oliva Zamora, Rubén
 * GRADO: Ingeniería del Software A
 */

package B_publish_process;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener{
    private Panel panel;
    private Worker workerTwin;
    private Worker workerCousin;
    private Worker workerSexy;

    // Para no desactivar el botón de cancelar cuando haya más de una hebra aún funcionando
    private static int running=0;

    public Controlador(Panel panel){
        this.panel = panel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("TWIN")) {
            // Si hay alguno funcionando, lo cancelo (para que no se solapen)
            if(workerTwin!=null && workerTwin.cancel(true)){
                running--;  // Si lo he cancelado, decremento running
            }
            panel.limpiaAreaTwin();
            workerTwin = new Worker(panel.numero1(), panel, Worker.KindOfPrime.TWIN);
            workerTwin.execute();
            running++;
            panel.mensajeTwin("Calculando primos twin...");
            panel.activarCancelar(true);
        }
        else if(e.getActionCommand().equals("COUSIN")){
            if(workerCousin!=null && workerCousin.cancel(true)){
                running--;
            }
            panel.limpiaAreaCousin();
            workerCousin = new Worker(panel.numero2(), panel, Worker.KindOfPrime.COUSIN);
            workerCousin.execute();
            running++;
            panel.mensajeCousin("Calculando primos cousin...");
            panel.activarCancelar(true);
        }
        else if(e.getActionCommand().equals("SEXY")){
            if(workerSexy!=null && workerSexy.cancel(true)){
                running--;
            }

            panel.limpiaAreaSexy();
            workerSexy = new Worker(panel.numero3(), panel, Worker.KindOfPrime.SEXY);
            workerSexy.execute();
            running++;
            panel.mensajeSexy("Calculando primos sexy...");
            panel.activarCancelar(true);
        }
        else if(e.getActionCommand().equals("FIN")){
            if (workerTwin!=null){
                if(workerTwin.cancel(true)){
                    panel.mensajeTwin("Tarea cancelada");
                }
            }

            if (workerCousin!=null){
                if(workerCousin.cancel(true)){
                    panel.mensajeCousin("Tarea cancelada");
                }
            }

            if (workerSexy!=null){
                if(workerSexy.cancel(true)){
                    panel.mensajeSexy("Tarea cancelada");
                }
            }

            panel.mensaje("Las tareas que se estaban ejecutando han sido canceladas");
            panel.activarCancelar(false);
        }
    }

    public static int getRunning() {
        return running;
    }

    public static void setRunning(int n){
        running = n;
    }
}
