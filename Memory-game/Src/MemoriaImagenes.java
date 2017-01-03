//******************************************************************************************************
// Nombre y # de carnet: Alvaro Arriola 09245   
//                                     Pablo Contreras 09251
//			       Pablo Galvez 09091
// Seccion 20
// Programa: MemoriaImagenes.java
// Descripcion: Define al objeto MemoriaImagenes, sus variables de instancia y sus metodos.
// Esta clase representa las imagenes que se asignaran a los botones del objeto MemoriaPanel
// y contiene metodos necesarios para poder llevar a cabo el juego.
//*******************************************************************************************************

// Importacion de los paquetes necesarios.
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MemoriaImagenes
{
	// Variables de instancia
    private ImageIcon[][] Imagenes;
	private int[][] representacionIntImagenes;
	private int ParejasAcertadas;
	private int niveldedificultad;
	
	// El constructor recibe como parametro el nivel de dificultad seleccionado con los botones del objeto PanelNivel:
	public MemoriaImagenes(int niveldedificultad)
	{
		// El valor del nivel de dificultad enviado como parametro se le asigna al valor del nivel de dificultad del objeto.
	    this.niveldedificultad = niveldedificultad;
		 
		// Se verifica el nivel de dificultad recibido como parametro y segun su valor se realizan diferentes acciones: 
	    if(niveldedificultad == 1)
        {
			// Se crea una matriz de 2x4 que contendra ImageIcons.
            Imagenes = new ImageIcon[2][4];
			 
			// Se le asigna a cada indice un ImageIcon 
			Imagenes[0][0] = new ImageIcon("Imagenes/huella.JPG");
		    Imagenes[0][1] = new ImageIcon("Imagenes/octagono.JPG");
		    Imagenes[0][2] = new ImageIcon("Imagenes/elipse.JPG");
		    Imagenes[0][3] = new ImageIcon("Imagenes/rectangulo.JPG");
			 
			Imagenes[1][0] = new ImageIcon("Imagenes/rectangulo.JPG");
		    Imagenes[1][1] = new ImageIcon("Imagenes/huella.JPG");
		    Imagenes[1][2] = new ImageIcon("Imagenes/elipse.JPG");
		    Imagenes[1][3] = new ImageIcon("Imagenes/octagono.JPG");
			 
			// Se inicia el contador de parejas acertadas:
			ParejasAcertadas = 0;
			// Se utiliza un metodo de la clase para representar las imagenes con datos tipo integer
			// para asi poder compararlas facilmente. Enviando como parametro el nivel de dificultad recibido.
			crearRepresentacionIntImagenes(1);
        }		 
	 
	    if(niveldedificultad == 2)
		{
			// Se crea una matriz de 4x4 que contendra ImageIcons.
	        Imagenes = new ImageIcon[4][4];
			 
			// Se le asigna a cada indice un ImageIcon 
		    Imagenes[0][0] = new ImageIcon("Imagenes/huella.JPG");
	        Imagenes[0][1] = new ImageIcon("Imagenes/octagono.JPG");
		    Imagenes[0][2] = new ImageIcon("Imagenes/elipse.JPG");
		    Imagenes[0][3] = new ImageIcon("Imagenes/rectangulo.JPG");
		    Imagenes[1][0] = new ImageIcon("Imagenes/elipse.JPG");
		    Imagenes[1][1] = new ImageIcon("Imagenes/pentagono.JPG");
		    Imagenes[1][2] = new ImageIcon("Imagenes/pentagono.JPG");
		    Imagenes[1][3] = new ImageIcon("Imagenes/cuadrado.JPG");
		 
		    Imagenes[2][0] = new ImageIcon("Imagenes/circulo.JPG");
		    Imagenes[2][1] = new ImageIcon("Imagenes/huella.JPG");
		    Imagenes[2][2] = new ImageIcon("Imagenes/octagono.JPG");
		    Imagenes[2][3] = new ImageIcon("Imagenes/triangulo.JPG");
		    Imagenes[3][0] = new ImageIcon("Imagenes/triangulo.JPG");
		    Imagenes[3][1] = new ImageIcon("Imagenes/circulo.JPG");
		    Imagenes[3][2] = new ImageIcon("Imagenes/rectangulo.JPG");
		    Imagenes[3][3] = new ImageIcon("Imagenes/cuadrado.JPG");
		 
		    ParejasAcertadas = 0;
			// Se utiliza un metodo de la clase para representar las imagenes con datos tipo integer
			// para asi poder compararlas facilmente. Enviando como parametro el nivel de dificultad recibido.
		    crearRepresentacionIntImagenes(2);
		}
	}
	 
	// Metodo que recibe como parametro un JButton y 2 numeros tipo int representando su posicion en la matriz
	// de botones en la clase MemoriaPanel.
	public void asignarImagen(JButton boton, int fila, int columna)
	{
		// Ya que el indice de cada boton en la matriz de botones en la clase MemoriaPanel coincide con el
		// indice de su respectiva imagen en la matriz de las imagenes. Se le asigna el ImageIcon al boton
		// recibido de la matriz de las imagenes con el indice recibido.
	    boton.setIcon(Imagenes[fila][columna]);
	}
	 
	public void removerImagen(JButton boton)
	{
		// El metodo recibe un JButton como parametro, y le asigna un ImageIcon con nada
		// asi "quita" la imagen antes asignada.
	    boton.setIcon(new ImageIcon());
	}
	
	// Metodo de acceso para obtener la imagen que corresponde a un boton en 
	// especifico. 
	public ImageIcon getImagen(int fila, int columna)
	{
		// Devuelve el ImageIcon en el indice de la matriz de imagenes correspondiente
		// al indice con los valores recibidos como parametros formales.
	    return Imagenes[fila][columna];
	}
	
	// Metodo que recibe 4 numeros int como parametros, los cuales representan 2 indices en una matriz. Sirve para comparar
	// si las imagenes de los botones (segun la matriz de representacion con numeros int) son iguales.
	public boolean compararImagenes(int filaA, int columnaA, int fila, int columna)
	{
	    boolean comparacion;
		
		// Si los numeros en los indices de la matriz son los mismos la condicion sera verdadera.
		if(representacionIntImagenes[filaA][columnaA] == representacionIntImagenes[fila][columna])
		{
			// En el caso de que sea verdadera se sumara una unidad a las parejas acertadas, se le asignara true al boolean por devolver,
			// y se verificara si ya todas las parejas estan descubiertas con el metodo finalizarJuego.
		    comparacion = true;
			ParejasAcertadas++;
			finalizarJuego();
		}	 
		else // Si la condicion es falsa se asignara false al valor por devolver
            comparacion = false;
		
		// Se devuelve si es true o false, o sea si son las mismas imagenes o no.
		return comparacion;
	}
	 
	// Se crea la representacion de las imagenes de la matriz con otra matriz
	// de datos tipo int, los cuales representaran cada imagen con un numero
	// respectivo para cada imagen. Esta matriz sera equivalente a la de imagenes,
	// ya que los numeros que representan cada imagen se posicionaran en el mismo
	// numero de indice que estan en la matriz de imagenes.
	private void crearRepresentacionIntImagenes(int nivel)
	{
		// Se crea la matriz segun el nivel de dificultad recibido.
	    if(nivel == 1)
		{
		    representacionIntImagenes = new int[2][4];
		 
		    representacionIntImagenes[0][0] = 1;
		    representacionIntImagenes[0][1] = 2;
		    representacionIntImagenes[0][2] = 3;
		    representacionIntImagenes[0][3] = 4;
			 
		    representacionIntImagenes[1][0] = 4;
		    representacionIntImagenes[1][1] = 1;
		    representacionIntImagenes[1][2] = 3;
		    representacionIntImagenes[1][3] = 2;
		}
	 
	    if(nivel == 2)
		{
	        representacionIntImagenes = new int[4][4];
		 
		    representacionIntImagenes[0][0] = 1;
		    representacionIntImagenes[0][1] = 2;
		    representacionIntImagenes[0][2] = 3;
		    representacionIntImagenes[0][3] = 4;
		    representacionIntImagenes[1][0] = 3;
		    representacionIntImagenes[1][1] = 5;
		    representacionIntImagenes[1][2] = 5;
		    representacionIntImagenes[1][3] = 6;
		 
		    representacionIntImagenes[2][0] = 8;
		    representacionIntImagenes[2][1] = 1;
		    representacionIntImagenes[2][2] = 2;
		    representacionIntImagenes[2][3] = 7;
		    representacionIntImagenes[3][0] = 7;
		    representacionIntImagenes[3][1] = 8;
		    representacionIntImagenes[3][2] = 4;
		    representacionIntImagenes[3][3] = 6;
		}
	}
	 
	// Este metodo verifica si todas las parejas estan descubiertas, o sea si
	// el usuario ya acerto todas las parejas.
	private void finalizarJuego()
	{
		// Si el nivel de dificultad es 1, y el # de parejas acertadas es 4, significa
		// que el usuario gano el juego, y se llamara al metodo finJuego.
	    if(niveldedificultad == 1)
		    if(ParejasAcertadas == 4)
			    finJuego();
		 
		// Si el nivel de dificultad es 2, y el # de parejas acertadas es 8, significa
		// que el usuario gano el juego, y se llamara al metodo finJuego. 
	    if(niveldedificultad == 2)
		    if(ParejasAcertadas == 8)
			    finJuego();
	}
	
	// Este metodo se llama cuando el usuario ya acerto todas las parejas.
	private void finJuego()
	{
		// Despliega un JOptionPane con opciones Yes, o No, especificadas en el siguiente arreglo.
		Object[] options = {"Si ! ! !", ". . . No"};
		// Se muestra el JOptionPane:
	    int opcion = JOptionPane.showOptionDialog(new JFrame(), "Felicitaciones! Ganaste!\nDeseas jugar otra vez?", 
		"Memoria", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		
		// Si el usuario presiona el boton con la opcion Yes, se llamara al metodo otroJuego
		if(opcion == JOptionPane.YES_OPTION)
		    otroJuego();
	}
	
	// Este metodo se llama cuando el usuario decide jugar otro juego al haber ganado el anterior.
	private void otroJuego()
	{
		// Se inicia nuevamente el juego desde el principio, creando la misma ventana
		// que se creo al inicio con un objeto PanelNivel.
	    JFrame frame = new JFrame("Memoria");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		PanelNivel panelnivel = new PanelNivel();
		 
		frame.getContentPane().add(panelnivel);
		 
		frame.pack();
		frame.setVisible(true);   
	}
	
	// Este metodo de acceso devuelve el numero de parejas ya acertadas por el usuario.
	public int getParejasAcertadas()
	{
		return ParejasAcertadas;
	}
}