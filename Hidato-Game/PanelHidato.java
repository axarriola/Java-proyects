//**************************************************
// PanelHidato.java
// Descripcion: Clase abstracta que contiene
// metodos genericos para ser heredados a
// a PanelInstruccionesHidato y PanelJuegoHidato.
// Hereda de la clase JPanel, e implementa la
// interfaz Tableros.
//**************************************************

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Clase abstracta PanelHidato que contiene metodos y objetos genericos para ser heredados a otras clases.
// PanelInstruccionesHidato y PanelJuegoHidato heredan estos objetos y metodos para utilizarlos de una manera mas especifica.
public abstract class PanelHidato extends JPanel implements Tableros
{
	// Se declara una matriz de objetos Boton, y un JPanel.
	protected Boton[][] BotonesTablero;
	protected JPanel paneltablero;
	
	// Este metodo al tener el modificador final no puede ser sobreescrito. Recibe 2 matrices de datos tipo byte como argumentos.
	// Este metodo devuelve el primer numero que deberia de poder colocar el usuario.
	public final int getPrimerNumeroXColocar(byte[][] tablero, byte[][] table)
	{
		// Se inicializa la variable NumeroMenor con un valor de 100, para que el primer numero con el que se compare
		// sea menor a este valor.
		int NumeroMenor = 100;
		
		for(int fila = 0; fila < tablero.length; fila++) // Recorremos las filas de la matriz.
			for(int columna = 0; columna < tablero[fila].length; columna++) // Recorremos columnas.
				if(tablero[fila][columna] != 0) // Si el valor al que se esta apuntando es igual a "0" no se toma en cuenta, ya que representa un espacio vacio en el arreglo.
					if(table[fila][columna] == 1) // Si el valor es igual a 1 en la matriz table significa que no es un boton guia.
						if(tablero[fila][columna] < NumeroMenor) // Si este valor es menor al valor actual de NumeroMenor.
							NumeroMenor = tablero[fila][columna]; // Entonces en ese caso el valor al que se apunta en la matriz es el nuevo NumeroMenor.
		
		// Al haber recorrido todo el arreglo se devuelve el valor del NumeroMenor encontrado.
		return NumeroMenor;
	}
	
	// Este metodo devuelve el numero mas alto de la matriz de datos tipo byte que recibe como parametro.
	// Se utiliza para resaltar el boton que contiene ese numero, de esta manera el usuario identifica el ultimo numero del rompecabezas.
	public final int getNumeroMasAlto(byte[][] tablero)
	{
		int NumeroMayor = 0; // Se inicializa el numero en 0.
		
		for(int fila = 0; fila < tablero.length; fila++) // Recorremos las filas de la matriz.
			for(int columna = 0; columna < tablero[fila].length; columna++) // Recorremos columnas.
				if(tablero[fila][columna] != 0) // Si el valor al que se esta apuntando es igual a "0" no se toma en cuenta, ya que representa un espacio vacio en el arreglo.
					if(tablero[fila][columna] > NumeroMayor) // Si ese numero es mayor al valor actual de NumeroMayor, 
							NumeroMayor = tablero[fila][columna]; // entonces se le asigna ese valor a la variable.		
		
		// Al haber recorrido la matriz se encontro el numero mas alto de la matriz, y se devuelve este valor a donde se llamo el metodo.
		return NumeroMayor;
	}
	
	// Este metodo no devuelve ningun valor, ya que solo crea el tablero Hidato a partir de dos matrices de datos tipo byte que recibe en su lista de parametros.
	public void crearTablero(byte[][] tablero, byte[][] table)
	{
		// Se crea la matriz de objetos Boton con el mismo numero de casillas de la matriz de datos tipo byte que contienen los numeros de cada boton en el tablero.
		BotonesTablero = new Boton[tablero.length][tablero[0].length];
		// Se le asigna un gridlayot al panel con la misma longitud y con una separacion entre cada componente de 3.
		paneltablero.setLayout(new GridLayout(tablero.length, tablero[0].length,3,3));
		
		for(int fila = 0; fila < BotonesTablero.length; fila++) // Recorremos filas.
			for(int columna = 0; columna < BotonesTablero[fila].length; columna++) // Recorremos columnas.
				if(tablero[fila][columna] != 0 && table[fila][columna] != 0) // Si en ninguna de las matrices el indice contiene un valor igual a 0 la condicion es verdadera.
				{
					if(table[fila][columna] == 2) // Si el indice es 2 en la matriz table significa que es un boton preestablecido (BotonDeAyuda).
						BotonesTablero[fila][columna] = new BotonDeAyuda(tablero[fila][columna]);
					else // Sino es un boton normal.
						BotonesTablero[fila][columna] = new Boton(tablero[fila][columna]);	
					
					// Se arregla esteticamente cada boton. Ya que la clase BotonDeAyuda sobreescribio este metodo de la clase Boton, se realiza el metodo,
					// segun el tipo de objeto hacia el que se apunta.
					BotonesTablero[fila][columna].arreglarBoton(); // Polimorfismo
					
					// Si el numero clave del boton es igual a 1 o es el numero mayor, entonces se le coloca como NumeroExtremo.
					if(BotonesTablero[fila][columna].getNumeroClave() == 1 || 
					   BotonesTablero[fila][columna].getNumeroClave() == getNumeroMasAlto(tablero))
							((BotonDeAyuda)BotonesTablero[fila][columna]).setNumeroExtremo();		
				}
	}
}
