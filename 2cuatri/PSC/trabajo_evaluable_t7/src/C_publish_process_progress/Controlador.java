/*
 * ALUMNO: Oliva Zamora, Rubén
 * GRADO: Ingeniería del Software A
 */

package C_publish_process_progress;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener{
    private Panel panel;
    private Worker workerTwin;
    private Worker workerCousin;
    private Worker workerSexy;
    private ControladorBarras ctrbarraTwin;
    private ControladorBarras ctrbarraCousin;
    private ControladorBarras ctrbarraSexy;

    // Para no desactivar el botón de cancelar cuando haya más de una hebra aún funcionando
    private static int running=0;

    public Controlador(Panel panel){
        this.panel = panel;
        ctrbarraTwin = new ControladorBarras(panel, Worker.KindOfPrime.TWIN);
        ctrbarraCousin = new ControladorBarras(panel, Worker.KindOfPrime.COUSIN);
        ctrbarraSexy = new ControladorBarras(panel, Worker.KindOfPrime.SEXY);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("TWIN")) {
            // Si hay alguno funcionando, lo cancelo (para que no se solapen)
            if(workerTwin!=null && workerTwin.cancel(true)){
                running--;  // Si lo he cancelado, decremento running
            }

            // Limpio el area y barra de progreso
            panel.limpiaAreaTwin();
            panel.progreso1(0);

            // Creo y ejecuto el worker
            workerTwin = new Worker(panel.numero1(), panel, Worker.KindOfPrime.TWIN);
            workerTwin.execute();
            // Añado el controlador relativo al tipo de worker
            workerTwin.addPropertyChangeListener(ctrbarraTwin);

            // Actualizo la variable que controla si está o no activado el botón de cancelar
            running++;
            // Muestro el mensaje correspondiente
            panel.mensajeTwin("Calculando primos twin...");
            // Activo el botón de cancelar
            panel.activarCancelar(true);
        }
        // En los siguientes comandos, se procede de forma parigual al del comando "TWIN"
        else if(e.getActionCommand().equals("COUSIN")){
            if(workerCousin!=null && workerCousin.cancel(true)){
                running--;
            }

            panel.limpiaAreaCousin();
            panel.progreso2(0);
            workerCousin = new Worker(panel.numero2(), panel, Worker.KindOfPrime.COUSIN);
            workerCousin.execute();
            workerCousin.addPropertyChangeListener(ctrbarraCousin);
            running++;
            panel.mensajeCousin("Calculando primos cousin...");
            panel.activarCancelar(true);
        }
        else if(e.getActionCommand().equals("SEXY")){
            if(workerSexy!=null && workerSexy.cancel(true)){
                running--;
            }

            panel.limpiaAreaSexy();
            panel.progreso3(0);
            workerSexy = new Worker(panel.numero3(), panel, Worker.KindOfPrime.SEXY);
            workerSexy.execute();
            workerSexy.addPropertyChangeListener(ctrbarraSexy);
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
