//**************************************************
// Nombre: Alvaro Arriola          Carnet: 09245
//               Pablo Contreras                    09251
// Seccion 20
//
// Nombre programa: Boton.java
// Descripcion: Hereda los metodos de la clase
// JToggleButton. Representa un objeto boton
// de un tablero de Hidato con los metodos
// necesarios para realizar el juego.
//**************************************************

import javax.swing.*;
import java.awt.*;

// Esta clase hereda de la clase JToggleButton, de esta manera se le agregaron y sobreescribieron metodos para
// ser utilizados en nuestro programa de una manera mas comoda para nosotros.
public class Boton extends JToggleButton
{
	// Datos de instancia con modificador protected para poder ser nombrados en su clase hija BotonDeAyuda,
	// contienen el numero que el usuario asigno al boton, y el numero correcto para completar el tablero.
	protected int numeroclave;
	protected int intentonumero;

	// Constructor. Este crea el objeto recibiendo como parametro el numero correcto que deberia de contener
	// para completar el rompecabezas Hidato segun el tablero escogido, y lo guarda en su variable numeroclave.
	public Boton(byte numeroclave)
	{
		this.numeroclave = numeroclave;
	}
	
	// Este metodo arregla esteticamente el boton agregandole un borde y un tipo de letra.
	public void arreglarBoton()
	{
		this.setFont(new Font("Comic Sans MS", Font.ITALIC, 16));
		this.setBorder(BorderFactory.createEtchedBorder());
	}
	
	// Este metodo se sobrecargo. Se adapto para recibir un numero y asignarlo como texto al boton que llama al metodo.
	public void setText(int c)
	{
		super.setText(Integer.toString(c));
		intentonumero = c;
	}
	
	// Este metodo asigna el numero correcto al boton que llamo al metodo.
	public void setNumeroClave()
	{
		this.setText(this.getStringNumeroClave());
	}
	
	// Devuelve el numero que el usuario le asigno al boton.
	public int getIntentoNumero()
	{
		return intentonumero;
	}
	
	// Este metodo se utiliza para mostrar al usuario el boton con su numero correcto.
	public void resolver()
	{
		this.deshabilitar(); // Se deshabilita el boton para evitar trampas.
		this.setNumeroClave();
		this.arreglarBoton();
	}
	
	// Devuelve el numero clave como un String para colocarlo como el texto del boton.
	public String getStringNumeroClave()
	{
		String texto = Integer.toString(this.getNumeroClave());
		return texto;
	}
	
	// Devuelve el numero correcto del boton.
	public int getNumeroClave()
	{
		return numeroclave;
	}
	
	// Selecciona el boton y lo deshabilita.
	public void deshabilitar()
	{
		this.setSelected(true);
		this.setEnabled(false);
	}
	
	// Selecciona el boton y le agrega su numero clave.
	public void autoPresionar()
	{
		this.setSelected(true);
		this.setNumeroClave();
	}
	
	// Habilita de nuevo el boton.
	public void habilitar()
	{
		this.setSelected(false);
		this.setEnabled(true);
	}
	
	// Habilita el boton, le quita cualquier texto que contenga y le agrega de nuevo su borde y letra originales.
	public void reiniciar()
	{
		this.habilitar();
		this.setText("");
		this.arreglarBoton();
	}

}