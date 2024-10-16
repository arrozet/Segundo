package _3amigosProgressBar;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;

public class ControladorBarra implements PropertyChangeListener{

	private Panel panel;
	
	public ControladorBarra(Panel panel){
		this.panel = panel;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("progress")){
			panel.progreso((Integer) evt.getNewValue());
		}
	}

}
