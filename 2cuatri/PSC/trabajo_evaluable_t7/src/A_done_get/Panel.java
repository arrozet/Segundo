/*
 * ALUMNO: Oliva Zamora, Rubén
 * GRADO: Ingeniería del Software A
 */

package A_done_get;

import B_publish_process.Controlador;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.List;

public class Panel extends JPanel {

	private JLabel mensaje = new JLabel("GUI creada");
	private JButton fin = new JButton("Cancelar");

	// Twin
	private JLabel etiqueta1 = new JLabel("¿cuántos de primos twin quieres?");
	private JTextField numero1 = new JTextField(3);
	private JTextArea listaPrimos1 = new JTextArea(10, 40);
	private JScrollPane scroll1 = new JScrollPane(listaPrimos1);
	private JLabel mensaje1 = new JLabel("Area  primos 'twin' creada");
	//private JProgressBar progreso1 = new JProgressBar(0, 100);

	// Cousin
	private JLabel etiqueta2 = new JLabel("¿cuántos de primos cousin quieres?");
	private JTextField numero2 = new JTextField(3);
	private JTextArea listaPrimos2 = new JTextArea(10, 40);
	private JScrollPane scroll2 = new JScrollPane(listaPrimos2);
	private JLabel mensaje2 = new JLabel("Area  primos 'cousin' creada");
	//private JProgressBar progreso2 = new JProgressBar(0, 100);

	// Sexy
	private JLabel etiqueta3 = new JLabel("¿cuántos de primos sexy quieres?");
	private JTextField numero3 = new JTextField(3);
	private JTextArea listaPrimos3 = new JTextArea(10, 40);
	private JScrollPane scroll3 = new JScrollPane(listaPrimos3);
	private JLabel mensaje3 = new JLabel("Area  primos 'sexy' creada");
	//private JProgressBar progreso3 = new JProgressBar(0, 100);

	//Ya implementado el constructor y los atributos.

	// He reordenado el código para poder entenderlo más fácilmente
	public Panel() {
		// Establece el layout del panel principal como BorderLayout
		this.setLayout(new BorderLayout());

		//-------------------------------------------------------------------------------------------------------------
		// CANCELAR
		// Crea el panel para la parte norte y añade el botón de cancelar
		JPanel norte = new JPanel();
		// norte.add(etiqueta);norte.add(numero);
		norte.add(fin);
			// Desactivo el botón de cancelar, ya que inicialmente está desactivado
		activarCancelar(false);

		//-------------------------------------------------------------------------------------------------------------
		// PANEL DE PANELES
		// Crea el panel para meter todas las áreas y establece su layout como GridLayout con una fila y tres columnas
		JPanel centro = new JPanel();
		centro.setLayout(new GridLayout(1, 3));

		/*
		// Inicializa barras de progreso con valor 0 y texto visible
		progreso1.setValue(0);
		progreso1.setStringPainted(true);
		progreso2.setValue(0);
		progreso2.setStringPainted(true);
		progreso3.setValue(0);
		progreso3.setStringPainted(true);
		*/

		//-------------------------------------------------------------------------------------------------------------
		// TWIN
		// Crea el panel para la esquina superior izquierda ("título" de twin)
		JPanel izdarriba = new JPanel();
		izdarriba.add(etiqueta1);
		izdarriba.add(numero1);
		//izdarriba.add(progreso1);

		// Crea el panel para mostrar la lista de twin
		JPanel izquierda = new JPanel();
		izquierda.setLayout(new BorderLayout());
			// Añade el "título" de twin encima de donde se muestra la lista y le pone el scroll
		izquierda.add(BorderLayout.NORTH, izdarriba);
		izquierda.add(BorderLayout.CENTER, scroll1);

		// Crea el panel para la parte inferior izquierda ("subtítulo" de twin)
		JPanel izqabajo = new JPanel();
		izqabajo.add(mensaje1);
		//izqabajo.add(progreso1);
			// Añade el "subtítulo" de twin abajo de donde se muestra la lista
		izquierda.add(BorderLayout.SOUTH, izqabajo);

		//-------------------------------------------------------------------------------------------------------------
		// COUSIN
		// Crea el panel para la esquina superior izquierda ("título" de cousin)
		JPanel centroarriba = new JPanel();
		centroarriba.add(etiqueta2);
		centroarriba.add(numero2);

		// Crea el panel para mostrar la lista de cousin
		JPanel centro1 = new JPanel();
		centro1.setLayout(new BorderLayout());
			// Añade el "título" de cousin encima de donde se muestra la lista y le pone el scroll
		centro1.add(BorderLayout.NORTH, centroarriba);
		centro1.add(BorderLayout.CENTER, scroll2);

		// Crea el panel para la parte inferior central ("subtítulo" de cousin)
		JPanel centroAbajo = new JPanel();
		centroAbajo.add(mensaje2);
		//centroAbajo.add(progreso2);
			// Añade el "subtítulo" de cousin abajo de donde se muestra la lista
		centro1.add(BorderLayout.SOUTH, centroAbajo);

		//-------------------------------------------------------------------------------------------------------------
		// SEXY
		// Crea el panel para la esquina superior izquierda ("título" de sexy)
		JPanel dcharriba = new JPanel();
		dcharriba.add(etiqueta3);
		dcharriba.add(numero3);

		// Crea el panel para mostrar la lista de sexy
		JPanel derecha = new JPanel();
		derecha.setLayout(new BorderLayout());
			// Añade el "título" de sexy encima de donde se muestra la lista y le pone el scroll
		derecha.add(BorderLayout.NORTH, dcharriba);
		derecha.add(BorderLayout.CENTER, scroll3);

		// Crea el panel para la parte inferior central ("subtítulo" de sexy)
		JPanel dchaAbajo = new JPanel();
		dchaAbajo.add(mensaje3);
		//dchaAbajo.add(progreso3);
			// Añade el "subtítulo" de sexy abajo de donde se muestra la lista
		derecha.add(BorderLayout.SOUTH, dchaAbajo);

		//-------------------------------------------------------------------------------------------------------------
		// Añado mis paneles completos a mi panel de paneles (panel de todas las áreas)
		centro.add(izquierda);
		centro.add(centro1);
		centro.add(derecha);

		// Pongo el cancelar en el norte, el panel de paneles en medio y el mensaje abajo
		this.add(BorderLayout.NORTH, norte);
		this.add(BorderLayout.CENTER, centro);
		this.add(BorderLayout.SOUTH, mensaje);
	}

	// Debes registrar el ctr como observador de los botones y JTextField
	// correspondiendes
	public void controlador(ActionListener ctr) {
		numero1.addActionListener(ctr);
		numero1.setActionCommand("TWIN");

		numero2.addActionListener(ctr);
		numero2.setActionCommand("COUSIN");

		numero3.addActionListener(ctr);
		numero3.setActionCommand("SEXY");

		fin.addActionListener(ctr);
		fin.setActionCommand("FIN");
	}

	// Devuelve el entero que tiene el JTextField numero 1
	public int numero1() {
		try {
			return Integer.parseInt(numero1.getText());
		} catch (NumberFormatException e) {
			return -1; // Devuelve -1 si la entrada no es un número válido
		}
	}

	// Devuelve el entero que tiene el JTextField numero 2
	public int numero2() {
		try {
			return Integer.parseInt(numero2.getText());
		} catch (NumberFormatException e) {
			return -1; // Devuelve -1 si la entrada no es un número válido
		}
	}

	// Devuelve el entero que tiene el JTextField numero 3
	public int numero3() {
		try {
			return Integer.parseInt(numero3.getText());
		} catch (NumberFormatException e) {
			return -1; // Devuelve -1 si la entrada no es un número válido
		}
	}

	// Añade a JTextArea listaPrimos1 la lista que se le pasa.
	// Recuerda meter retornos de carro para que salga como en la captura de
	// pantalla
	public void escribePrimosTwin(List<Primos> list) {
        for (Primos twin : list) {
            listaPrimos1.append(twin.toString() + "   ");
            if ((twin.pos() + 1) % 5 == 0){
				listaPrimos1.append("\n");
			}
        }

		comprobarCancelar();
	}

	// Añade a JTextArea listaPrimos2 la lista que se le pasa.
	// Recuerda meter retornos de carro para que salga como en la captura de
	// pantalla
	public void escribePrimosCousin(List<Primos> list) {
		for (Primos cousin : list) {
			listaPrimos2.append(cousin.toString() + "   ");
			if ((cousin.pos() + 1) % 5 == 0){
				listaPrimos2.append("\n");
			}
		}

		comprobarCancelar();
	}

	// Añade a JTextArea listaPrimos3 la lista que se le pasa.
	// Recuerda meter retornos de carro para que salga como en la captura de
	// pantalla
	public void escribePrimosSexy(List<Primos> list) {
		for (Primos sexy : list) {
			listaPrimos3.append(sexy.toString() + "   ");
			if ((sexy.pos() + 1) % 5 == 0){
				listaPrimos3.append("\n");
			}
		}

		comprobarCancelar();
	}

	// Limpia el JTextArea listaPrimos1
	public void limpiaAreaTwin() {
		listaPrimos1.setText("");
	}

	// Limpia el JTextArea listaPrimos2
	public void limpiaAreaCousin() {
		listaPrimos2.setText("");
	}

	// Limpia el JTextArea listaPrimos3
	public void limpiaAreaSexy() {
		listaPrimos3.setText("");
	}

	// Establece mensaje que se le pasa el JLabel mensaje
	public void mensaje(String str) {
		mensaje.setText(str);
	}

	// Establece mensaje que se le pasa el JLabel mensaje1
	public void mensajeTwin(String str) {
		mensaje1.setText(str);
	}

	// Establece mensaje que se le pasa el JLabel mensaje2
	public void mensajeCousin(String str) {
		mensaje2.setText(str);
	}

	// Establece mensaje que se le pasa el JLabel mensaje3
	public void mensajeSexy(String str) {
		mensaje3.setText(str);
	}

	// Establece el nivel de progreso n en el JProgressBar progreso1
	public void progreso1(int n) {

	}

	// Establece el nivel de progreso n en el JProgressBar progreso2
	public void progreso2(int n) {

	}

	// Establece el nivel de progreso n en el JProgressBar progreso3
	public void progreso3(int n) {

	}

	// Para activar o desactivar el botón de cancelar
	public void activarCancelar(boolean valor) {
		fin.setEnabled(valor);
	}

	private void comprobarCancelar(){
		int running = Controlador.getRunning();
		running--;
		Controlador.setRunning(running);
		if(running==0){
			activarCancelar(false);
		}
	}
}
