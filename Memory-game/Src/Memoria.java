//******************************************************************************************************
// Clase que contiene el metodo main.
//*******************************************************************************************************

// Importacion de los paquetes necesarios:
import java.awt.*;
import javax.swing.*;


public class Memoria 
{
    public static void main(String[] args) 
	{
		// Se crea la ventana que contendra el objeto PanelNivel.
		JFrame frame = new JFrame("Memoria");
		// Instruccion para que la ventana se cierre al presionar la X en el extremo superior derecho.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		// Se crea el objeto PanelNivel.
		PanelNivel panelnivel = new PanelNivel();
		 
		// Se agrega al contenido.
		frame.getContentPane().add(panelnivel);
		 
		// Se muestra la ventana y su contenido.
		frame.pack();
		frame.setVisible(true);   
	}
}
