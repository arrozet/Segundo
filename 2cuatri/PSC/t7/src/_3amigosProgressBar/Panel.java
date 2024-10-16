package _3amigosProgressBar;

import java.awt.*;
import java.awt.event.*;


import javax.swing.*;


public class Panel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JLabel etiqueta = new JLabel("Cuantos amigos quieres?");
	private JLabel mensaje = new JLabel("GUI Creada");
	private JTextField numero = new JTextField(3);
	
	private JTextArea listaAmigos = new JTextArea(10,40);
	private JScrollPane scroll = new JScrollPane(listaAmigos);
	private JButton fin = new JButton("Cancelar");
	private JProgressBar progreso = new JProgressBar(0,100);
	
	public Panel(){
		this.setLayout(new BorderLayout());
		JPanel norte = new JPanel();
		norte.add(etiqueta);
		norte.add(numero);
		fin.setEnabled(false);
		norte.add(fin);
		this.add(BorderLayout.NORTH, norte);
	    this.add(BorderLayout.CENTER,  scroll);
	    JPanel sur = new JPanel();
	    sur.add(mensaje); sur.add(progreso);
	    this.add(BorderLayout.SOUTH,  sur);
	    progreso.setStringPainted(true);
	}
	
	public void controlador(ActionListener ctr){
		numero.addActionListener(ctr);
		numero.setActionCommand("NUMERO");
		fin.addActionListener(ctr);
		fin.setActionCommand("FIN");
		
	}
	
	
	public int numero() {
		return Integer.parseInt(numero.getText());
	}

	public void escribeAmigos(java.util.List<Amigos> lista){
		for (int i=0; i<lista.size(); i++){
			Amigos amigo = lista.get(i);
			listaAmigos.append(amigo.toString()+ "   ");
			if ((amigo.pos()+1)%5 == 0) listaAmigos.append("\n");
		}
	}
	public void limpiaArea(){
		listaAmigos.setText("");
	}
	public void mensaje(String str){
		mensaje.setText(str);
	}
	
	public void progreso(int n){
		progreso.setValue(n);
	}
	
	public void activarCancelar(boolean valor) {
		fin.setEnabled(valor);
	}
}
