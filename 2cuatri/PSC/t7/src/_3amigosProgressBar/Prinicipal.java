package _3amigosProgressBar;


import javax.swing.*;

public class Prinicipal {

	public static void crearGUI(JFrame ventana){
		Panel panel = new Panel();
		ControladorBarra ctrbarra = new ControladorBarra(panel);
		Controlador ctr = new Controlador(panel,ctrbarra);

		panel.controlador(ctr);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final JFrame ventana = new JFrame("Numeros amigos");
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				try{
					crearGUI(ventana);
				}catch(Exception e){
					System.out.println("tarea cancelada");
				}
			}
		});
	}
	
	

}
