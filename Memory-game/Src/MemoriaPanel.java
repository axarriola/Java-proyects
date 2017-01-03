//******************************************************************************************************
// Nombre y # de carnet: Alvaro Arriola 09245   
//                                     Pablo Contreras 09251
//			       Pablo Galvez 09091
// Seccion 20
// Programa: MemoriaPanel.java
// Descripcion: Esta clase contiene los componentes que se agregaran a la ventana del
// juego, segun el nivel seleccionado.
//*******************************************************************************************************

// Importacion de los paquetes necesarios:
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class MemoriaPanel extends JPanel 
{
    // Variables de instancia:
	private MemoriaListener listener;
	private int contador = 0;
	private JPanel panel;
	private JButton[][] GrupoDeBotones;
	private MemoriaImagenes imagenes;
	private int filalistener, columnalistener, filalistener2, columnalistener2;
	private int niveldedificultad, intentos = 0, contadorreloj = 0;
	// Aqui investigamos el uso del Timer: http://java.sun.com/docs/books/tutorial/uiswing/misc/timer.html 
	private Timer timer = new Timer(180, new TimerListener());
	private Timer cronometro = new Timer(1000, new CronometroListener());
	private JLabel informacion;
	 
	// Constructor que recibe como parametro el nivel de dificultad del juego, el cual se mando segun el boton de nivel
	// de dificultad seleccionado.
	public MemoriaPanel(int niveldedificultad)
	{	
		// Si el nivel es igual a 2 se crea una matriz que contendra JButtons 4 x 4.
		if(niveldedificultad == 2){
		    GrupoDeBotones = new JButton[4][4];
			// Se crea un objeto MemoriaImagenes enviando un 2 como parametro.
			imagenes = new MemoriaImagenes(2);
			// El valor del nivel de dificultad enviado como parametro se le asigna al valor del nivel de dificultad del objeto.
			this.niveldedificultad = niveldedificultad;
        }			
		else{
			// Si el nivel es igual a 1 se crea una matriz que contendra JButtons 2 x 4.
            GrupoDeBotones = new JButton[2][4];
			// Se crea un objeto MemoriaImagenes enviando un 1 como parametro.
            imagenes = new MemoriaImagenes(1);
			// El valor del nivel de dificultad enviado como parametro se le asigna al valor del nivel de dificultad del objeto.
            this.niveldedificultad = niveldedificultad;			 
		}	 
		 
		// Se instancia el actionlistener MemoriaListener.
		listener = new MemoriaListener(); 
		 
		// Con ciclos for anidados se recorre la matriz GrupoDeBotones. En cada indice se instancia un boton, se le agrega
		// un MemoriaListener, se le justifica un tamanio y se le agrega un borde elevado.
		for(int fila = 0; fila < GrupoDeBotones.length; fila++)
		    for(int columna = 0; columna < GrupoDeBotones[fila].length; columna++){
	       	    GrupoDeBotones[fila][columna] = new JButton();
				GrupoDeBotones[fila][columna].setBorder(BorderFactory.createRaisedBevelBorder());
				GrupoDeBotones[fila][columna].addActionListener(listener);
				GrupoDeBotones[fila][columna].setPreferredSize(new Dimension(90, 80));		
			}
		 	 
		// Se crea el panel.
		panel = new JPanel();
		String titulo;
		if(niveldedificultad == 1)
			titulo = "Nivel 1";
		else
			titulo = "Nivel 2";
		
		// Se le agrega un borde con titulo justificado a la izquierda.
		TitledBorder bordenivel = BorderFactory.createTitledBorder(titulo);
        bordenivel.setTitleJustification(TitledBorder.LEFT);
		panel.setBorder(bordenivel);
		
		// Se crea una etiqueta con borde bajo, y un tamanio.
		JLabel etiqueta = new JLabel("     MEMORIA");
		etiqueta.setPreferredSize(new Dimension(90, 80));
		etiqueta.setBorder(BorderFactory.createLoweredBevelBorder());
		
		// Se crea una JLabel con la informacion del juego: parejas acertadas, intentos, y el tiempo que ha transcurrido.
		informacion = new JLabel("  Parejas acertadas: " + imagenes.getParejasAcertadas() +
						       	"      Intentos: " + intentos + "      Tiempo: 0 s");
		 
		// Dependiendo del nivel de dificultad elegido se le asignara un tamanio especifico al panel que contendra los botones y la JLabel con la
		// informacion.
		if(niveldedificultad == 2)
		{
		    panel.setPreferredSize(new Dimension(410, 370));
			informacion.setPreferredSize(new Dimension(410, 70));
		}
		else
		{
		    panel.setPreferredSize(new Dimension(320, 290));
			informacion.setPreferredSize(new Dimension(320, 70));
		}
		
		// Se recorre la matriz de nuevo para agregar los botones al panel. 
		for(int fila = 0; fila < GrupoDeBotones.length; fila++)
		    for(int columna = 0; columna < GrupoDeBotones[fila].length; columna++)
			{
				// Si el nivel de dificultad es uno, y la columna es 0, y la fila es 1, se agregara la JLabel etiqueta para
				// agregar simetria y estetica.
			    if(fila == 1 && columna == 0 && niveldedificultad == 1)
				    panel.add(etiqueta);
					 
			    panel.add(GrupoDeBotones[fila][columna]);
			}	 
		
		// Se le agrega un borde con titulo justificado a la izquierda a la JLabel con la informacion del juego.
		TitledBorder bordeinfo = BorderFactory.createTitledBorder("Informacion");
        bordeinfo.setTitleJustification(TitledBorder.LEFT);
		informacion.setBorder(bordeinfo);
		
		// Se agregan los componentes.
        add(panel);	 
		add(informacion);
		// Dependiendo del nivel de dificultad elegido se le justificara cierto tamanio a la ventana.
		if(niveldedificultad == 1)
			setPreferredSize(new Dimension(350, 380));
		else
			setPreferredSize(new Dimension(420, 460));
	}
	 
	// Clase MemoriaListener 
	private class MemoriaListener implements ActionListener
	{
		// Metodo que reacciona ante la realizacion de un evento.
	    public void actionPerformed(ActionEvent event)
		{
			// Se suma una unidad al contador
		    contador++;
			// Se comienza el Timer que representa el tiempo transcurrido/
			cronometro.start();
			
			// Si el contador es impar:
		    if(contador % 2 != 0)
			{
				// Se recorrera la matriz de botones hasta encontrar que componente disparo el evento.
		        for(int fila = 0; fila < GrupoDeBotones.length; fila++)
		            for(int columna = 0; columna < GrupoDeBotones[fila].length; columna++)
			            if(event.getSource() == GrupoDeBotones[fila][columna])
						{
							// A ese componente se le asignara una imagen segun su posicion en la matriz.
				            imagenes.asignarImagen(GrupoDeBotones[fila][columna], fila, columna);
							// Ese componente se deshabilitara.
					        GrupoDeBotones[fila][columna].setEnabled(false);
							// La posicion del componente en la matriz se guardara en una variable de instancia para uso posterior.
							filalistener = fila;
							columnalistener = columna;
			            }	
		    }
			
			// Si el contador es par:
			if(contador % 2 == 0)
			{
				// Se sumara una unidad al contador de intentos.
				intentos++;
				// Se recorrera la matriz de botones nuevamente para encontrar que componente disparo el evento.
			    for(int filaA = 0; filaA < GrupoDeBotones.length; filaA++)
		            for(int columnaA = 0; columnaA < GrupoDeBotones[filaA].length; columnaA++)
					{
					    if(event.getSource() == GrupoDeBotones[filaA][columnaA])
						{ 
							// A ese componente se le asignara una imagen segun su posicion en la matriz.
							imagenes.asignarImagen(GrupoDeBotones[filaA][columnaA], filaA, columnaA);
							// Tambien se deshabilitara
					        GrupoDeBotones[filaA][columnaA].setEnabled(false);
							
							// La posicion del componente en la matriz se guardara en otra variable de instancia para uso posterior.
							filalistener2 = filaA;
							columnalistener2 = columnaA;
							// Se dara inicio al Timer que sirve para mostrar por determinado tiempo limitado las imagenes de los 2 componentes.
							// Al transcurrir ese tiempo el Timer disparara un evento, y las acciones ocurridas entonces estan definidas en
							// la clase TimerListener.
							timer.start();
														 
						}		 
					}	 
			}
		}
	}

	private class TimerListener implements ActionListener
	{
		// Este metodo ocurre cuando el Timer timer dispara un evento, lo cual ocurre 180 milisegundos despues de ser activado.
		public void actionPerformed(ActionEvent event)
		{
			// Se para el timer para que no continue respondiendo este evento.
			timer.stop();
			// Se comparan las 2 imagenes con los metodos del objeto imagenes, definidos en la clase MemoriaImagenes.
			// En el caso de que no sean iguales:
			if(!imagenes.compararImagenes(filalistener, columnalistener, filalistener2, columnalistener2))
			{
				// Los dos botones se habilitaran nuevamente			 
				GrupoDeBotones[filalistener][columnalistener].setEnabled(true);							
				GrupoDeBotones[filalistener2][columnalistener2].setEnabled(true);
				// A los dos botones se les quitara la imagen que tenian asignada, utilizando los metodos del objeto imagenes, definidos en la clase MemoriaImagenes.		 
				imagenes.removerImagen(GrupoDeBotones[filalistener][columnalistener]);
				imagenes.removerImagen(GrupoDeBotones[filalistener2][columnalistener2]);
			}	
		}
	}
	
	private class CronometroListener implements ActionListener
	{
		// Este metodo reacciona cuando el Timer cronometro dispara un evento, lo cual ocurrira cada segundo.
		public void actionPerformed(ActionEvent event)
		{
			// Se le suma una unidad al contador de segundos, el cual sirve para mostrarlo.
			contadorreloj++;
			// Se le asigna el texto deseado a la etiqueta de informacion.
			informacion.setText("  Parejas acertadas: " + imagenes.getParejasAcertadas() +
							"      Intentos: " + intentos + "      Tiempo: " + contadorreloj + " s");
			
			// Se verifica si el usuario ya acerto todas las parejas dependiendo del nivel, en el caso de que ya haya acertado todas, el timer se detiene.
			if(niveldedificultad == 1)
				if(imagenes.getParejasAcertadas() == 4)
					cronometro.stop();
					
			if(niveldedificultad == 2)
				if(imagenes.getParejasAcertadas() == 8)
					cronometro.stop();		
		}
	}
	
} 




