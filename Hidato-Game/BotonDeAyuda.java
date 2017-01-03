//**************************************************
// BotonDeAyuda.java
// Descripcion: Deriva de la clase Boton.
// Representa los botones que se encuentran
// fijos en un tablero de Hidato.
//**************************************************

import javax.swing.*;
import java.awt.*;

// Esta clase deriva de la clase Boton. Utiliza los mismos metodos que
// Boton pero actua de una manera mas especifica, ya que un BotonDeAyuda
// representa los botones en el tablero que sirven de guia para el usuario para 
// completar el rompecabezas.
public class BotonDeAyuda extends Boton
{	
	// Constructor.
	public BotonDeAyuda(byte NumeroClave)
	{
		// Llama al constructor de su clase padre.
		super(NumeroClave);
		// Un BotonDeAyuda se encuentra deshabilitado desde el inicio y muestra su numero correcto.
		this.deshabilitar();
		this.setNumeroClave();
	}
	
	// Se sobreescribio el metodo arreglarBoton con diferente borde y diferente tipo de letra por estetica, para diferenciarlos.
	public void arreglarBoton()
	{
		this.setBorder(BorderFactory.createLineBorder(Color.white, 4));
		this.setFont(new Font("Arial", Font.BOLD, 17));
	}
	
	// Este metodo se utiliza para los botones que tienen el menor numero y el mayor, estos se resaltaran para que el usuario
	// los identifique facilmente.
	public void setNumeroExtremo()
	{
		this.setFont(new Font("Arial", Font.BOLD, 22));
		this.setBorder(BorderFactory.createRaisedBevelBorder());
	}
	
}
