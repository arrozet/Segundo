/*
 * ALUMNO: Oliva Zamora, Rubén
 * GRADO: Ingeniería del Software A
 */

package C_publish_process_progress;

import javax.swing.*;

public class Main {
    public static void crearGUI(){
        System.out.println("crearGUI() - isEventDispatchThread? "+ SwingUtilities.isEventDispatchThread());

        JFrame ventana = new JFrame("Números primos - versión C (publish/process + progress bar)");

        Panel panel = new Panel();
        Controlador ctr = new Controlador(panel);
        panel.controlador(ctr);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setContentPane(panel);
        ventana.pack();
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        //Hacemos que la interfaz se cree en la hebra dispatcher
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                try{
                    crearGUI();
                }catch(Exception e){
                    System.out.println("Tarea cancelada");
                }
            }
        });
    }



}
