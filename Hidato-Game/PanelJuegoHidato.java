//**************************************************
// Nombre: Alvaro Arriola          Carnet: 09245
//               Pablo Contreras                    09251
// Seccion 20
//
// Nombre programa: PanelJuegoHidato.java
// Descripcion: Derivada de la clase PanelHidato
// Utiliza las clases Boton y BotonDeAyuda,
// los metodos de su superclase inmediata
// y las constantes de la interfaz Tableros
// para poder crear un objeto que representa
// un tablero de juego Hidato.
//**************************************************

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

// Esta clase deriva de la clase abstracta PanelHidato.
public class PanelJuegoHidato extends PanelHidato
{
	// Declaracion de variables y objetos, e inicializacion de algunas.
	private int ind = 0; // Contador para representar el indice de un arreglo de etiquetas.
	private JPanel panel; // Panel.
	private Timer cronometro; // Objeto Timer.
	private JLabel[] etiquetas; // Arreglo de etiquetas.
	private byte[][] tablero, table; // Matrices donde se guardaran los valores de el tablero y table escogidos al azar para efectos practicos.
	private boolean[] numerosposibles; // Arreglo de datos tipo boolean para determinar que numeros se estan mostrando en el tablero y cuales no.
	private JLabel crono, indicaciones; // Etiquetas que contendran indicaciones para el usuario y una especie de "cronometro" con la ayuda de un Timer.
	private int minutos = 0, segundos = 0; // Variables que contendran los valores de los segundos y minutos transcurridos.
	private int SiguienteNumeroXColocar, NumeroMayor; // Variables que contendran el siguiente numero por colocar en el tablero, y el numero mayor en el mismo.
	private int contadornull = 0, botoneshabilitados = 0; // Contadores para guardar el numero de espacios vacios en el tablero y el numero de botones habilitados.
	private JButton BotonOtroTablero; // Boton para cambiar de tablero.
	private JButton[] Opciones; // Arreglo de objetos JButton que se utilizaran para accionar las opciones posibles para el usuario, tales como revisar, reiniciar y resolver.
	
	public PanelJuegoHidato() // Constructor.
	{
		// Se crea el panel tablero y se le asigna un tamano.
		paneltablero = new JPanel();
		paneltablero.setPreferredSize(new Dimension(600,600));
		
		// Se llama al metodo escogerTablero que se encuentra en esta clase.
		escogerTablero();
		
		//  Se recorre el arreglo de objetos Boton indice por indice.
		for(int fila = 0; fila < BotonesTablero.length; fila++)// Recorriendo filas.
			for(int columna = 0; columna < BotonesTablero[fila].length; columna++) // Recorriendo columnas.
				if(BotonesTablero[fila][columna] != null) // Se realiza esta condicion para evitar una NullPointerException.
					paneltablero.add(BotonesTablero[fila][columna]); // Se agrega el boton al tablero.
				else
				{	// En el caso de que el indice apunte a null se le agrega una etiqueta al tablero, para crear la forma del mismo, ya que si tiene
					// referencia null significa que es un espacio vacio en el tablero.
					paneltablero.add(etiquetas[ind]); // Se agrega la etiqueta.
					ind++; // Se le suma una unidad al indice para agregar otra etiqueta la proxima vez.
				}
		
		// Se inicializa el panel que contendra los botones revisar, reiniciar y resolver.
		panel = new JPanel();
		panel.setLayout(new BorderLayout()); // Se le asigna un BorderLayout.
		panel.setPreferredSize(new Dimension(600,90)); // Se le asigna un tamano especifico.
		
		// Se inicializa el boton para cambiar de tablero, se le agrega un listener, se le asigna un tamanio y un borde.
		BotonOtroTablero = new JButton("Cambiar Tablero");
		BotonOtroTablero.addActionListener(new CambiarTableroListener());
		BotonOtroTablero.setPreferredSize(new Dimension(120,30));
		BotonOtroTablero.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		
		// Se inicializa el objeto cronometro tipo Timer con un delay de 1 segundo y un listener.
		cronometro = new Timer(1000, new CronometroListener());
		
		// Etiqueta que contendra el tiempo transcurrido.
		crono = new JLabel("                                                              Tiempo 0:00");
		crono.setFont(new Font("Arial",Font.BOLD,16)); // Se la asigna un tipo de letra y un borde.
		crono.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		
		// Se inicializa el arreglo de objetos JButton, y se inicializan sus indices los cuales contendran los botones revisar, reiniciar y resolver.
		Opciones = new JButton[3];
		Opciones[0] = new JButton("Resolver");
		Opciones[1] = new JButton("Reiniciar");
		Opciones[2] = new JButton("Revisar");
		
		// Se le agrega a cada indice de este arreglo un listener y un borde.
		for(int i = 0; i < Opciones.length; i++)
		{
			Opciones[i].addActionListener(new OpcionesListener());
			Opciones[i].setBorder(BorderFactory.createLineBorder(Color.white, 1));
		}
		
		// Se inicializa la etiqueta que contendra especificaciones para el usuario en algun momento de la ejecucion del programa,
		// con un tipo de letra y un borde en especifico.
		indicaciones = new JLabel("");
		indicaciones.setFont(new Font("Arial",Font.PLAIN,15));
		indicaciones.setBorder(BorderFactory.createLineBorder(Color.white, 1));
		
		// Se agregan los componentes al panel segun el BorderLayout.
		panel.add(crono, BorderLayout.NORTH);
		panel.add(Opciones[2], BorderLayout.CENTER);
		panel.add(Opciones[1], BorderLayout.EAST);
		panel.add(Opciones[0], BorderLayout.WEST);
		panel.add(indicaciones, BorderLayout.SOUTH);
		
		// Se inicializa el siguiente numero por colocar con la ayuda del metodo de la clase padre.
		SiguienteNumeroXColocar = getPrimerNumeroXColocar(tablero,table);
		// Se obtiene el numero mayor y se le asigna el valor a la variable NumeroMayor con la ayuda del metodo de la clase padre.
		NumeroMayor = getNumeroMasAlto(tablero);
		// Se llama a este metodo que se encuentra en esta misma clase.
		crearArreglosNumerosPosibles();
		// Se le asigna un tamano.
		setPreferredSize(new Dimension(620,737));
		// Se agregan los componentes.
		add(BotonOtroTablero);
		add(paneltablero);
		add(panel);
		// Se inicia el Timer para que empiece a disparar eventos.
		cronometro.start();
		System.gc(); // Se llama al garbage collector de java para que recicle la memoria de objetos a los cuales ninguna referencia les apunta.
	}
	
	// Metodo privado que escoge el tablero por utilizar al azar.
	private void escogerTablero()
	{
		Random generador = new Random(); // Se crea un objeto Random.
		// Se llama al metodo de este objeto para que genere un integer entre 0 y 11, pero al
		// necesitar un numero entre 1 y 12 se le suma una unidad a ese numero y se le 
		// asigna a la variable tablero escogido.
		int TableroEscogido = generador.nextInt(12) + 1;
		// Se evalua el valor de esta variable y se crea el tablero segun el numero escogido.
		switch(TableroEscogido)
		{
			case 1: crearTablero(tablero1, table1); break;
			case 2: crearTablero(tablero2, table2); break;
			case 3: crearTablero(tablero3, table3); break;
			case 4: crearTablero(tablero4, table4); break;
			case 5: crearTablero(tablero5, table5); break;
			case 6: crearTablero(tablero6, table6); break;
			case 7: crearTablero(tablero7, table7); break;
			case 8: crearTablero(tablero8, table8); break;
			case 9: crearTablero(tablero9, table9); break;
			case 10: crearTablero(tablero10, table10); break;
			case 11: crearTablero(tablero11, table11); break;
			case 12: crearTablero(tablero12, table12); break;
		}
	}
	
	// Se sobreescribio el metodo crearTablero de la clase padre por conveniencia.
	public void crearTablero(byte[][] tablero, byte[][] table)
	{
		// Se invoca al metodo de la clase padre.
		super.crearTablero(tablero, table);
		// Se asignan los valores de las matrices recibidas como parametros a matrices de esta clase por conveniencia.
		this.tablero = tablero;
		this.table = table;
		
		// Se recorren las matrices indice por indice.
		for(int fila = 0; fila < BotonesTablero.length; fila++)
			for(int columna = 0; columna < BotonesTablero[fila].length; columna++)
				try // Se indica esta condicion para evitar una NullPointerException.
				{
					// Utilizamos la palabra reservada "instanceof" para diferenciar si el objeto hacia el cual apunta el indice de la matriz
					// es una instancia de la clase BotonDeAyuda, esto devuelve un valor booleano que determina la condicion.
					// Si devuelve true entonces se le agrega un listener DeshabilitadosListener, y en caso contrario se le agregan
					// un ActionListener y un MouseListener de la clase HabilitadosListener.
					if(BotonesTablero[fila][columna] instanceof BotonDeAyuda)
						BotonesTablero[fila][columna].addMouseListener(new DeshabilitadosListener());
					else
					{
						BotonesTablero[fila][columna].addActionListener(new HabilitadosListener());
						BotonesTablero[fila][columna].addMouseListener(new HabilitadosListener());
						botoneshabilitados++;
					}	
				}
				catch(NullPointerException e1) { contadornull++; 
				} // Se cuenta el numero de espacios vacios en el tablero.
				
		// El contador se utiliza para crear el arreglo de etiquetas con el numero exacto de etiquetas necesarias.
		etiquetas = new JLabel[contadornull];
		// Se instancia cada indice del arreglo mediante una repeticion.
		for(int indice = 0; indice < contadornull; indice++)
			etiquetas[indice] = new JLabel();
	}
	
	// Metodo que reinicia el siguiente numero por colocar.
	public void reiniciarSiguienteNumeroXColocar()
	{
		// Reasigna el primer numero por colocar a la variable que contiene el siguiente numero por colocar.
		SiguienteNumeroXColocar = super.getPrimerNumeroXColocar(tablero, table);
		// Crea de nuevo el arreglo de numeros posibles.
		crearArreglosNumerosPosibles();
	}
	
	// Metodo privado que inicializa el arreglo booleano que determina que numeros tiene permitido el usuario ingresar al tablero.
	private void crearArreglosNumerosPosibles() 
	{
		// Se crea el arreglo con el tamano del numero mas alto en el tablero.
		numerosposibles = new boolean[getNumeroMasAlto(tablero)];
		
		// Se recorre el tablero de objetos Boton indice por indice.
		for(int fila = 0; fila < BotonesTablero.length; fila++)
			for(int columna = 0; columna < BotonesTablero[fila].length; columna++)
				try
				{ 
					if(BotonesTablero[fila][columna] instanceof BotonDeAyuda) // Condicion discutida anteriormente.
						numerosposibles[(BotonesTablero[fila][columna].getNumeroClave())-1] = true;
						// Si el indice apunta a un BotonDeAyuda significa que el numero ya se encuentra en el tablero , ya que estos componentes
						// tienen la caracteristica de mostrar su numero clave desde un inicio como guia, en este caso se le asigna a esa casilla del
						// arreglo un "true" que significa que ya se encuentra en el tablero.
				}
				catch(NullPointerException e2) { }
	}
	
	// Clase DeshabilitadosListener que implementa la interfaz MouseListener
	private class DeshabilitadosListener implements MouseListener
	{
			// Este evento responde cuando se clickea el mouse sobre el componente que contiene un listener de esta clase.
			public void mouseClicked(MouseEvent e) 
			{	
				// Se inicializa una variable tipo int con valor cero.
				int numero = 0;
				
				// Se recorre la matriz de botones indice por indice para determinar de que boton provino el evento.
				for(int fila = 0; fila < BotonesTablero.length; fila++)
					for(int columna = 0; columna < BotonesTablero[fila].length; columna++)
						if(e.getSource() == BotonesTablero[fila][columna]) // Si el evento se disparo por el objeto en ese indice se le asigna a la variable numero el numero clave de ese boton.
							numero = BotonesTablero[fila][columna].getNumeroClave();
				
				// Se verifica mediante una repeticion si ese numero ya se encuentra en el tablero segun el indice del arreglo de numerosposibles
				// si la casilla es verdadera se suma una unidad mas al numero, hasta que la condicion devuelva false.
				while(numerosposibles[numero - 1])
					numero++;
				
				// Se le asigna ese numero a la variable que contiene el siguiente numero por colocar, ya que el punto de presionar uno de los
				// botones de ayuda es saltarse al siguiente numero sin ser colocado en el tablero todavia.
				SiguienteNumeroXColocar = numero;
			}
			// Metodos de la interfaz Mouselistener, se requiere que se les agregue un cuerpo ya que se implementa la interfaz, pero no hallamos
			// un uso para ellos entonces sus cuerpos estan vacios.
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
			public void mousePressed(MouseEvent e){}
	}
	
	// Clase HabilitadosListener que implementa la interfaz ActionListener y la MouseListener.
	private class HabilitadosListener implements ActionListener, MouseListener
	{
			// Este metodo responde cuando uno de los componentes dispara un ActionEvent.
			public void actionPerformed(ActionEvent event)
			{
				// Se recorre indice por indice la matriz BotonesTablero.
				for(int fila = 0; fila < BotonesTablero.length; fila++)
					for(int columna = 0; columna < BotonesTablero[fila].length; columna++)
						if(event.getSource() == BotonesTablero[fila][columna]) // Si el evento se genero por ese indice.
							if(BotonesTablero[fila][columna].isSelected())// Y este boton esta seleccionado
							{
								// Se le coloca a ese boton como texto el siguiente numero por ser colocado.
								BotonesTablero[fila][columna].setText(SiguienteNumeroXColocar);
								// A la casilla del arreglo numerosposibles se le asigna un true para notificar que este numero ya esta en juego.
								numerosposibles[SiguienteNumeroXColocar - 1] = true;
								// Se le suma una unidad a la variable SiguienteNumeroXColocar.
								SiguienteNumeroXColocar++;
							}
							else // En el caso que no se encuentre seleccionado.
							{
								// Se le retira el texto.
								BotonesTablero[fila][columna].setText("");
								// A la casilla del arreglo numerosposibles se le asigna un false para notificar que este numero ya no esta en juego.
								numerosposibles[BotonesTablero[fila][columna].getIntentoNumero() - 1] = false;
								// Se le asigna un borde al boton.
								BotonesTablero[fila][columna].setBorder(BorderFactory.createEtchedBorder());
								// Se reinicia la JLabel indicaciones para no mostrar algo.
								indicaciones.setText("");
							}
				// Se verifica mediante una repeticion si el valor actual de la variable SiguienteNumeroXColocar ya se encuentra en el tablero segun el indice del arreglo de numerosposibles
				// si la casilla es verdadera se suma una unidad mas al numero, hasta que la condicion devuelva false.			
				while(numerosposibles[SiguienteNumeroXColocar - 1])
					SiguienteNumeroXColocar++;
				
			}
			
			// Este metodo se llama cuando el cursor entra en el area del componente.
			public void mouseEntered(MouseEvent e) 
			{
				// Se realiza el mismo proceso anterior.
				for(int fila = 0; fila < BotonesTablero.length; fila++)
					for(int columna = 0; columna < BotonesTablero[fila].length; columna++)
						if(e.getSource() == BotonesTablero[fila][columna])
							if(!(BotonesTablero[fila][columna].isSelected())) // Solo en el caso de que el boton no este seleccionado.
							{
								while(numerosposibles[SiguienteNumeroXColocar - 1])
									SiguienteNumeroXColocar++;
						
									BotonesTablero[fila][columna].setText(SiguienteNumeroXColocar);
							}
				// Con este metodo logramos que el siguiente numero por colocar aparezca en cada boton posible para colocarlo sobre el cual el usuario pase el cursor.				
			}
			
			// Este metodo se llama cuando el cursor sale del area del componente.
			public void mouseExited(MouseEvent e)
			{
				// Este metodo sirve para que cuando el usuario saque el cursor de encima del componente se retire el SiguienteNumeroXColocar.
				for(int fila = 0; fila < BotonesTablero.length; fila++)
					for(int columna = 0; columna < BotonesTablero[fila].length; columna++)
						if(e.getSource() == BotonesTablero[fila][columna])
							if(!(BotonesTablero[fila][columna].isSelected()))
								BotonesTablero[fila][columna].setText("");
			}
			// Metodos de la interfaz Mouselistener, se requiere que se les agregue un cuerpo ya que se implementa la interfaz, pero no hallamos
			// un uso para ellos entonces sus cuerpos estan vacios.
			public void mouseClicked(MouseEvent e){}
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
	}
	
	// Clase CronometroListener que implementa ActionListener.
	private class CronometroListener implements ActionListener
	{
		// Este metodo se llama cada vez que el Timer cronometro dispara un evento, ya que este es el unico
		// componente que contiene un listener de esta clase.
		public void actionPerformed(ActionEvent event)
		{
			// Ya que el Timer dispara eventos cada segundo se le suma una unidad a la variable segundos.
			segundos++;
				
				// Si esta es igual a 60 se le reinicia a 0 y se le suma una unidad a los minutos.
				if(segundos == 60)
				{
					minutos++;
					segundos = 0;
				}
			// Se muestra el contenido de estas variables asignandolo de una manera ordenada a la JLabel crono.
			crono.setText("                                                              Tiempo " + 
							minutos + ((segundos < 10)? ":0" : ":") + segundos);
		}
	}
	
	// Clase que representa los metodos de un OpcionesListener, implementa la interfaz ActionListener.
	private class OpcionesListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// Si el evento proviene del boton Resolver. Se realiza el cuerpo de la condicion.
			if(e.getSource() == Opciones[0])
			{
				cronometro.stop(); // Se detiene el cronometro.
				// Se recorre la matriz BotonesTablero y se resuelven los botones que se deben de resolver.
				for(int fila = 0; fila < BotonesTablero.length; fila++)
					for(int columna = 0; columna < BotonesTablero[fila].length; columna++)
						try
						{
							if(!(BotonesTablero[fila][columna] instanceof BotonDeAyuda))
								BotonesTablero[fila][columna].resolver();
						}
						catch(NullPointerException e3) { }
						
				// Se deshabilita el boton Revisar ya que no existe un uso para el en ese momento.
				Opciones[2].setEnabled(false);
				// Se le asginan indicaciones para el usuario a la JLabel indicaciones.
				indicaciones.setText("Presione el boton reiniciar para empezar de nuevo!");
			}
			
			// Si el evento lo disparo el boton Reiniciar.
			if(e.getSource() == Opciones[1])
			{
				// Se habilita el boton revisar.
				Opciones[2].setEnabled(true);
				// Se reinicia el Siguiente numero por colocar.
				reiniciarSiguienteNumeroXColocar();
				// Se detiene el cronometro, se reasignan sus variables y se inicia de nuevo el mismo.
				cronometro.stop();
				minutos = 0;
				segundos = -1;
				cronometro.start();
				// Se recorre la matris BotonesTablero para reiniciar cada boton que no es una instancia de la clase BotonDeAyuda.
				for(int fila = 0; fila < BotonesTablero.length; fila++)
					for(int columna = 0; columna < BotonesTablero[fila].length; columna++)
						try
						{
							if(!(BotonesTablero[fila][columna] instanceof BotonDeAyuda))
								BotonesTablero[fila][columna].reiniciar();
						}
						catch(NullPointerException e4) { }
						
				indicaciones.setText(""); // Se le quitan las indicaciones a la JLabel indicaciones ya que no se requieren.
			}
			
			// Si el evento se genero por el boton Resolver.
			if(e.getSource() == Opciones[2])
			{
				int correctos = 0; // Se inicia una variable correctos con un valor igual a cero.
				boolean incorrecto = false; // Este contiene el valor booleano si el usuario se equivoco en algun numero.
				
				// Se recorre indice por indice el arreglo de botones.
				for(int fila = 0; fila < BotonesTablero.length; fila++)
					for(int columna = 0; columna < BotonesTablero[fila].length; columna++)
						try
						{
							if(!(BotonesTablero[fila][columna] instanceof BotonDeAyuda)) // Si el boton es una instancia de BotonDeAyuda no lo debemos revisar.
								if(BotonesTablero[fila][columna].isSelected()) // Solo se revisa el boton si este esta seleccionado, sino no tiene sentido.
									if(BotonesTablero[fila][columna].getIntentoNumero() != tablero[fila][columna])
									{ // Si el numero que el usuario le asigno al boton no es igual al numero clave del boton se le asigna un borde rojo al boton para notificarle que este es incorrecto.
										BotonesTablero[fila][columna].setBorder(BorderFactory.createLineBorder(Color.red, 2));
										incorrecto = true; // Se indica que el usuario si se equivoco.
									}
									else
										correctos++;// Se cuentan el numero de botones correctos.
				        }
						catch(NullPointerException e5) { }
				// Si el usuario se equivoco se le indica que los numeros con borde rojo son incorrectos.
				if(incorrecto) 
					indicaciones.setText("Los numeros con borde rojo son incorrectos! Presionelos de nuevo para retirarlos!");	
				else // Si no se equivoco y tiene mas de algun correcto se le felicita.
					if(correctos > 0)
						indicaciones.setText("Muy bien! Sigue asi! Cada vez te encuentras mas cerca de resolver el rompecabezas!");
			
				// Si el numero de correctos es igual al numero de los botones a los cuales les debe asignar un numero entonces resolvio el rompecabezas.
				if(correctos == botoneshabilitados)
				{
					// Se le felicita mediante la etiqueta que contiene las indicaciones.
					indicaciones.setText("Felicitaciones! Has resuelto el rompecabezas!");
					cronometro.stop(); // Se para el Timer.
					resolvioHidato(); // Se llama al metodo resolvioHidato.
				}
			}
			
		}
	}
	
	private class CambiarTableroListener implements ActionListener
	{
		// Este metodo se llama cuando el boton OtroTablero dispara un evento.
		public void actionPerformed(ActionEvent e)
		{
			// Metodo de la clase JFrame para quitar la referencia a la memoria del JFrame, heredado de su superclase Window.
			PanelInstruccionesHidato.juego.dispose();
			// Se llama al metodo de la clase PanelInstruccionesHidato para que muestre  una nueva ventana con un nuevo tablero de juego.
		    PanelInstruccionesHidato.createAndShowGUI();
		}
	}
	
	// Este metodo se llama cuando el usuario resolvio el tablero.
	public void resolvioHidato()
	{
		// Despliega un JOptionPane con opciones Yes, o No, especificadas en el siguiente arreglo.
		String[] options = {"Si ! ! !", ". . . No"};
		// Se muestra el JOptionPane:
	    int opcion = JOptionPane.showOptionDialog(new JFrame(), "Felicitaciones! Resolviste el\nrompecabezas en menos de "+
		(minutos + 1) + ((minutos+1)>1?" minutos!":" minuto!")+"\nDeseas jugar otra vez?", "Hidato", JOptionPane.YES_NO_OPTION, 
		JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		
		// Si el usuario presiona el boton con la opcion Yes, se llamara al metodo otroJuego
		if(opcion == JOptionPane.YES_OPTION)
		{
			// Metodo de la clase JFrame para quitar la referencia a la memoria del JFrame, heredado de su superclase Window.
			PanelInstruccionesHidato.juego.dispose();
			// Se llama al metodo de la clase PanelInstruccionesHidato para que muestre  una nueva ventana con un nuevo tablero de juego.
		    PanelInstruccionesHidato.createAndShowGUI();
		}
		else // En caso que elija que no desea jugar de nuevo se finaliza la ejecucion del programa de una manera normal.
			System.exit(0);
	}
	
}