/*
 * ALUMNO: Oliva Zamora, Rubén
 * GRADO: Ingeniería del Software A
 */

package C_publish_process_progress;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ControladorBarras implements PropertyChangeListener{

    private Panel panel;
    private Worker.KindOfPrime kind;

    public ControladorBarras(Panel panel, Worker.KindOfPrime kind){
        this.panel = panel;
        this.kind = kind;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Cuando se actualice la propiedad, que lo muestre en la barra de progreso
        if (evt.getPropertyName().equals("progress")){
            if(kind == Worker.KindOfPrime.TWIN){
                panel.progreso1((Integer) evt.getNewValue());
            } else if (kind == Worker.KindOfPrime.COUSIN) {
                panel.progreso2((Integer) evt.getNewValue());
            }else{
                panel.progreso3((Integer) evt.getNewValue());
            }
        }
    }

}
