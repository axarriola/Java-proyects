//**************************************************
// Nombre: Alvaro Arriola          Carnet: 09245
//               Pablo Contreras                    09251
// Seccion 20
//
// Nombre programa: Hidato.java
// Descripcion: Driver. Contiene el metodo
// e inicia el juego mostrando un JFrame
// con un objeto PanelInstruccionesHidato.
//**************************************************

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Clase principal
public class Hidato 
{
	// Se declara un objeto JFrame estatico afuera del metodo main para poder referenciarlo en las demas clases.
	public static JFrame instrucciones;
	
	public static void main(String[] args) 
	{
		// Se crea una ventana JFrame.
		instrucciones = new JFrame("Instrucciones");
		// Instruccion para que la ventana se cierre al presionar la "X" en el extremo superior derecho.
		instrucciones.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Se instancia un objeto PanelInstruccionesHidato.
		PanelInstruccionesHidato panel = new PanelInstruccionesHidato();
		
		// Se le agrega al contenido de la ventana, y esta ultima se muestra.
		instrucciones.getContentPane().add(panel);
		instrucciones.pack();
		instrucciones.setVisible(true);
	}
}