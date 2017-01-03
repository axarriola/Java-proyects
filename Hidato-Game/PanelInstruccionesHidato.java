//**************************************************
// Nombre: Alvaro Arriola          Carnet: 09245
//               Pablo Contreras                    09251
// Seccion 20
//
// Nombre programa: PanelInstruccionesHidato.java
// Descripcion: Define al panel que contiene las 
// instrucciones iniciales del juego. Subclase
// de PanelHidato.
//**************************************************

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Esta clase hereda los metodos y variables de la clase PanelHidato.
public class PanelInstruccionesHidato extends PanelHidato 
{
	// Declaracion de variables y objetos.
	private Timer timer; // Objeto Timer para disparar eventos cada cierto tiempo especificado mas adelante.
	private JButton iniciar; // Componente para iniciar el juego.
	private int contador = -1; // Contador inicializado en -1.
	private JPanel panel; // Panel para agregar los componentes.
	private JLabel titulo; // Etiqueta para mostrar el titulo HIDATO.
	public static JFrame juego; // Ventana para desplegar un PanelJuegoHidato.
	private JLabel[] instrucciones; // Arreglo de objetos JLabel.
	
	public PanelInstruccionesHidato() // Constructor
	{
		panel = new JPanel(); // Se crea el panel y se le agrega un flow layout con orden hacia la izquierda.
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
			
		titulo = new JLabel("HIDATO"); // Se crea la etiqueta con el titulo y se le agrega un tipo de letra.
		titulo.setFont(new Font("Verdana", Font.BOLD,26));
		
		crearInstrucciones(); // llamada al metodo crearInstrucciones que se encuentra en esta clase.
			
		iniciar = new JButton("Jugar!"); // Se crea el boton y se le agrega un listener.
		iniciar.addActionListener(new IniciarListener());
		
		// Se instancia el objeto Timer y se le especifica un tiempo de "delay" de 1 segundo y medio y se le agrega un listener.
		timer = new Timer(1500, new TimerListener()); 
			
		paneltablero = new JPanel(); // Se inicializa el panel.
		// Se llama al metodo heredado de la clase PanelHidato enviando como parametros las matrices de datos tipo byte
		// que se encuentran en la interfaz Tableros, se envian los tableros de muestra ya que este tablero es de instrucciones.
		crearTablero(tableromuestra, tablemuestra);
		
		// Se recorren las filas y columnas de la matriz de objetos Boton para agregar cada uno en ese orden al panel.
		for(int fila = 0; fila < BotonesTablero.length; fila++) 
			for(int columna = 0; columna < BotonesTablero[fila].length; columna++)
					paneltablero.add(BotonesTablero[fila][columna]);
		
		// Se le asigna un tamano especifico al panel.
		paneltablero.setPreferredSize(new Dimension(300,300));
		
		// Se agrega el titulo y el panel con el tablero a otro panel.
		panel.add(titulo);
		panel.add(paneltablero);
		
		// Se agrega al panel las etiquetas del arreglo instrucciones en orden.
		for(int i = 0; i < instrucciones.length; i++)
			panel.add(instrucciones[i]);
			
		// Se agrega el boton para iniciar y se le asigna un tamano al panel.
		panel.add(iniciar);
		panel.setPreferredSize(new Dimension(350,610));
		
		// Se agrega el panel.
		add(panel);
		timer.start(); // Se inicia el objeto Timer.
		System.gc(); // Se llama al garbage collector de java para que recicle la memoria de objetos a los cuales ninguna referencia les apunta.
	}
	
	// Este metodo crea un arreglo de componentes JLabel y les asigna un texto a cada una para poder agregarlas al panel. Contienen las instrucciones para el juego.
	public void crearInstrucciones()
	{
		instrucciones = new JLabel[11];
		
		instrucciones[0] = new JLabel("Los rompecabezas inician semillenos con numeros con");
		instrucciones[1] = new JLabel("borde  blanco como guia.  El primer y  ultimo  numero se");
		instrucciones[2] = new JLabel("encuentran resaltados.  Los numeros consecutivos de -");
		instrucciones[3] = new JLabel("ben tocarse de  manera horizontal o vertical para ganar");
		instrucciones[4] = new JLabel("como se ve en el ejemplo arriba. Para colocar un nume -");
		instrucciones[5] = new JLabel("ro en una casilla seleccionela.  Para retirarlo presionela");
		instrucciones[6] = new JLabel("de nuevo y esta  se deseleccionara.  Para saltarse el or -");
		instrucciones[7] = new JLabel("den de colocacion  de los numeros  presione uno de los");
		instrucciones[8] = new JLabel("numeros de ayuda  para saltar al  siguiente numero (en"); 
		instrucciones[9] = new JLabel("orden ascendente) aun no colocado en el tablero. Utilice");
		instrucciones[10]= new JLabel("el boton \"Revisar\" para saber si resolvio el tablero!");
	}
	
	// Clase que define un IniciarListener, implementa ActionListener.
	private class IniciarListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// es un proceso que se llama Runnable que al instanciarce se ejecuta su metodo run en el cual se puede ejecutar cualquier metodo propio
			javax.swing.SwingUtilities.invokeLater(new Runnable() 
			{
				public void run()
				{
					createAndShowGUI(); // Metodo que muestra un GUI.
				}
			});
			// Se elimina la referencia de memoria para el JFrame que contiene este panel.
			Hidato.instrucciones.dispose();
		}
	}
	
	public static void createAndShowGUI()
	{
		// Se crea una ventana y se invoca a la instruccion que establece que este se cierre cuando se presione la X.
		juego = new JFrame("Hidato");
		juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		// Se crea un objeto PanelJuegoHidato.
		PanelJuegoHidato PanelTablero = new PanelJuegoHidato();
		
		// se le agrega el panel al contenido de la ventana y se muestra esta.
		juego.getContentPane().add(PanelTablero);
		juego.pack();
		juego.setVisible(true);
	}
	
	private class TimerListener implements ActionListener
	{
		// Metodo que se llama cada vez que el Timer dispara un evento (cada 1500 milisegundos).
		public void actionPerformed(ActionEvent e)
		{
			contador++; // Se le suma una unidad al contador.
			
			// Se evalua el valor del contador, y segun su valor se presiona uno de los
			// botones y se muestra su numero correcto, ya que son 4 botones, al llegar al
			// cuarto boton se reinician los botones y el contador, todo esto con el fin
			// de mostrarle al usuario un ejemplo
			switch(contador)
			{
				case 0: 
					BotonesTablero[2][2].autoPresionar();
				break;
				case 1:
					BotonesTablero[2][1].autoPresionar();
				break;
				case 2:
					BotonesTablero[1][0].autoPresionar();
				break;
				case 3:
					BotonesTablero[0][1].autoPresionar();
				break;	
				case 4:
					BotonesTablero[2][2].reiniciar();
					BotonesTablero[2][1].reiniciar();
					BotonesTablero[1][0].reiniciar();
					BotonesTablero[0][1].reiniciar();
					contador = -1;
				break;
			}
		}
	}
	
}	