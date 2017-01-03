package alvaroarriola.inventario.interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import alvaroarriola.inventario.mundo.*;

public class InterfazPrincipal extends JFrame implements ActionListener
{
	// -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------
	 
	// Clase principal del mundo.
	public Inventario inventario;
	
	//Imagenes de botones.
	public ImageIcon nuevop;
	public ImageIcon report;
	public ImageIcon ingr;
	public ImageIcon sali;
	
	public JButton boton1; // Producto Nuevo
	public JButton boton2; // Reportes
	public JButton boton3; // Ingresos
	public JButton boton4; // Salidas
	public JButton existencia; // Inventario
	
	public JPanel panel;
	
	public DialogoProductoNuevo dProdNuevo;
	public DialogoIngreso	dIngreso;
	public DialogoSalida	dSalida;
	public DialogoExistencia dExistencia;
	public DialogoBusquedaReportes dBusquedaReportes;
	
	// -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Constructor de la ventana. <br>
     * <b>post: </b> Se inicializaron los componentes gráficos de la aplicación.
     */
	public InterfazPrincipal()
	{
		
		// Construye la forma
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    setTitle( "Inventario" );
	    setSize( new Dimension( 270, 270 ) );
	    setResizable( false );
	    
	    panel = new JPanel();
	    // Layout
	    panel.setLayout(new GridLayout(2,2,2,2));
	    
	    // Crea la clase principal.
		inventario = new Inventario();
		
	    report = new ImageIcon("reportesimage.gif");
	    ingr = new ImageIcon("ingresoimage.png");
	    sali = new ImageIcon("salidaimage.png");
	    nuevop = new ImageIcon("nuevoproductoimage.jpg");
		// Botones.
		boton1 = new JButton("Producto", nuevop);
		boton1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		boton1.setPreferredSize(new Dimension(80,30));
		boton1.addActionListener(this);
		boton2 = new JButton("Reportes", report);
		boton2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		boton2.setPreferredSize(new Dimension(80,30));
		boton2.addActionListener(this);
		boton3 = new JButton("Ingreso", ingr);
		boton3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		boton3.setPreferredSize(new Dimension(80,30));
		boton3.addActionListener(this);
		boton4 = new JButton("Salida", sali);
		boton4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		boton4.setPreferredSize(new Dimension(80,30));
		boton4.addActionListener(this);
		
		existencia = new JButton("Mercaderia en existencia");
		existencia.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		existencia.addActionListener(this);
		
		panel.add(boton1);
		panel.add(boton2);
		panel.add(boton3);
		panel.add(boton4);
		
		setLayout(new BorderLayout());
		
		add(panel,BorderLayout.CENTER);
		add(existencia,BorderLayout.SOUTH);
		
		
		
	}
	
	// -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------
	/**
     * Manejo de eventos de los botones
     * @param e Evento que generó la acción - e != null.
     */
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == boton1)
		{
			dProdNuevo = new DialogoProductoNuevo(this);
			dProdNuevo.setVisible(true);
		}
		
		if(e.getSource() == boton2)
		{
			dBusquedaReportes = new DialogoBusquedaReportes(this);
			dBusquedaReportes.setVisible(true);
		}
		
		if(e.getSource() == boton3)
		{
			int nentrada = inventario.getNumeroEntrada();
			dIngreso = new DialogoIngreso(this, nentrada);
			dIngreso.setVisible(true);
		}
		
		if(e.getSource() == boton4)
		{
			int nsalida = inventario.getNumeroSalida();
			dSalida = new DialogoSalida(this,nsalida);
			dSalida.setVisible(true);
		}
		
		if(e.getSource() == existencia)
		{
			// DialogoExistencia
			dExistencia = new DialogoExistencia(this);
			dExistencia.setVisible(true);
		}
	}	
	
	/**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Son los argumentos de la aplicación. No se requiere ninguno.
     */
	public static void main(String[] args)
	{
		InterfazPrincipal interfaz = new InterfazPrincipal();
		interfaz.setVisible(true);
		interfaz.setLocation(500, 300);
		
	}
}
