//******************************************************************************************************
// Representa al objeto PanelNivel con sus componentes.
//*******************************************************************************************************

// Importacion de los paquetes necesarios:
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class PanelNivel extends JPanel
{
	// Variables de instancia, representan los dos componentes en el panel.
    JButton botonnivel1, botonnivel2;

    public PanelNivel()
	{
		// Se crea el panel, se le agrega un borde con titulo al lado izquierdo, y se justifica el tamano del panel.
		JPanel panel = new JPanel();
		TitledBorder borde = BorderFactory.createTitledBorder ("BIENVENIDO A MEMORIA");
        borde.setTitleJustification (TitledBorder.LEFT);
        panel.setBorder (borde);
		panel.setPreferredSize(new Dimension(230, 100));
		
		// Se instancian los dos botones, se les justifica su tamano, se agrega un borde de una linea a cada uno, con un color,
		// un grosor especifico. A cada boton se le agrega un ToolTipText.
	    botonnivel1 = new JButton("Nivel 1");
		botonnivel2 = new JButton("Nivel 2");
		botonnivel1.setPreferredSize(new Dimension(100, 40));
		botonnivel2.setPreferredSize(new Dimension(100, 40));
		botonnivel2.setBorder(BorderFactory.createLineBorder (Color.red, 1));
		botonnivel1.setBorder(BorderFactory.createLineBorder (Color.blue, 1));
		botonnivel1.setToolTipText("Descubre 4 parejas!");
		botonnivel2.setToolTipText("Descubre 8 parejas!");
		 
		// Se instancia el ActionListener NivelListener y se le agrega a cada boton.
		NivelListener listener = new NivelListener();
		botonnivel1.addActionListener(listener);
		botonnivel2.addActionListener(listener);
		 
		// Se justifica el tamano.
		setPreferredSize(new Dimension(250, 120)); 
		
		// Se agregan los componentes, entre ellos una JLabel y dos botones.
		panel.add(new JLabel("Escoge un nivel de dificultad:"));
		panel.add(botonnivel1);
		panel.add(botonnivel2); 
		
		// Se agrega el panel.
		add(panel);
	}	 

	// Clase que define al ActionListener NivelListener.
    private class NivelListener implements ActionListener
	{
		// Metodo que responde cuando se realiza un evento.
	    public void actionPerformed(ActionEvent event)
		{
			// Si el evento proviene del botonnivel1:
			if(event.getSource() == botonnivel1)
			{
				// Se crea una nueva ventana.
			    JFrame frame = new JFrame("Memoria");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				// Se crea un objeto MemoriaPanel mandandole un dato int 1 como parametro actual.
		        MemoriaPanel panel = new MemoriaPanel(1);
				
				// Se agrega el contenido y se muestra.
		        frame.getContentPane().add(panel);
		        frame.pack(); 
		        frame.setVisible(true);
			}
				 
			// Si el evento proviene del botonnivel2:
			if(event.getSource() == botonnivel2){
				// Se crea una nueva ventana.
			    JFrame frame = new JFrame("Memoria");
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				// Se crea un objeto MemoriaPanel mandandole un dato int 2 como parametro actual.
		        MemoriaPanel panel = new MemoriaPanel(2);
				
				// Se agrega el contenido y se muestra.
		        frame.getContentPane().add(panel);
		        frame.pack(); 
		        frame.setVisible(true); 
			} 
		}
	}
}	 
