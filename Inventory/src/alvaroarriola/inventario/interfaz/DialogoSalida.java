package alvaroarriola.inventario.interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import alvaroarriola.inventario.interfaz.*;
import alvaroarriola.inventario.mundo.*;

public class DialogoSalida extends JDialog implements ActionListener
{
	JPanel pfecha;
	JPanel pprod;
	JPanel ptipo;
	
	ArrayList prods;
	
	private JComboBox cDia;
	private String[] tDia = new String[31];
	private JComboBox cMes;
	private String[] tMes = new String[12];
	private JComboBox cAño;
	private String[] tAño = new String[20];
	private JLabel edia = new JLabel("Dia");
	private JLabel emes = new JLabel("Mes");
	private JLabel eaño = new JLabel("Año");
	
	private JLabel	codigoe = new JLabel("Codigo: ");
	private JTextField codigot;
	private JComboBox cProd;
	private String[] tProd;
	private JLabel	prode = new JLabel("Producto: ");
	
	private JTextField tamañot;
	private JLabel	tamañoe = new JLabel("Medida: ");
	private JTextField cantidadt;
	private JLabel	cantidade = new JLabel("Cantidad: ");
	
	private JRadioButton uno;
	private JRadioButton dos;
	private JRadioButton tres;
	private JRadioButton cuatro;
	ButtonGroup grupo;
	
	private int tipoSalida = 1;
	
	private JButton Aceptar;
	private JButton Continuar;
	private int NumeroDeSalida;
	
	InterfazPrincipal principal;
	
	public DialogoSalida(InterfazPrincipal ip, int nsalida)
	{
		super(ip,true);
		principal = ip;
		
		NumeroDeSalida = nsalida;
		
		JPanel opts = new JPanel();
		
		
		setLayout(new BorderLayout());
		opts.setLayout(new BorderLayout());
		
		panelFecha(); // Inicializa Panel de la fecha
		panelDatosProducto(); // Inicializa panel con la linea, nombre, cantidad y tamaño
		panelTipoIngreso(); // Inicializa el panel con opciones del tipo de ingreso
		
		
		opts.add(pfecha,BorderLayout.NORTH);
		opts.add(pprod,BorderLayout.CENTER);
		opts.add(ptipo,BorderLayout.SOUTH);
		
		Aceptar = new JButton("Aceptar");
		Aceptar.addActionListener(this);
		Continuar = new JButton("Continuar");
		Continuar.addActionListener(this);
		
		JPanel pp = new JPanel(new GridLayout(1,2,1,1));
		pp.add(Continuar);
		pp.add(Aceptar);
		
		setSize(350, 300);
		setTitle("Salida de Mercaderia");
		setLocationRelativeTo(principal);
		
		add(opts,BorderLayout.CENTER);
		add(pp,BorderLayout.SOUTH);
		
	}
	
	public void panelTipoIngreso()
	{
		ptipo = new JPanel(new GridLayout(2, 2));
		ptipo.setPreferredSize(new Dimension(150,45));
		//ptipo.setBorder(BorderFactory.createTitledBorder("Tipo de ingreso"));
		
		uno = new JRadioButton("Pedido");
		dos = new JRadioButton("Hoja de Salida");
		tres = new JRadioButton("Devolucione");
		cuatro = new JRadioButton("Prestamo");
		
		uno.setSelected(true);
		
		uno.setActionCommand("1");
		dos.setActionCommand("2");
		tres.setActionCommand("3");
		cuatro.setActionCommand("4");
		
		uno.addActionListener(this);
		dos.addActionListener(this);
		tres.addActionListener(this);
		cuatro.addActionListener(this);
		
		grupo = new ButtonGroup();
		grupo.add(uno);
		grupo.add(dos);
		grupo.add(tres);
		grupo.add(cuatro);
		
		ptipo.add(uno);
		ptipo.add(dos);
		ptipo.add(tres);
		ptipo.add(cuatro);
	}
	
	public void panelDatosProducto()
	{
		pprod = new JPanel();
		pprod.setPreferredSize(new Dimension(250, 100));
		pprod.setLayout(new GridLayout(4,2,3,3));
		
		codigoe.setPreferredSize(new Dimension(80, 20));
		prode.setPreferredSize(new Dimension(80, 20));
		tamañoe.setPreferredSize(new Dimension(80, 20));
		cantidade.setPreferredSize(new Dimension(80, 20));
		
		tamañot = new JTextField();
		tamañot.setPreferredSize(new Dimension(80, 20));
		cantidadt = new JTextField();
		cantidadt.setPreferredSize(new Dimension(80, 20));
		codigot = new JTextField();
		codigot.setPreferredSize(new Dimension(80, 20));
		codigot.setEditable(false);
		
		
		prods = principal.inventario.darListaProductos();
		tProd = new String[prods.size()];
		
		for(int i = 0; i < prods.size(); i++)
		{
			Producto actual = (Producto) prods.get(i);
			tProd[i] = actual.getNombre();
		}
		
		cProd = new JComboBox(tProd);
		cProd.setPreferredSize(new Dimension(80, 20));
		cProd.addActionListener(this);
		
		pprod.add(prode);
		pprod.add(cProd);
		pprod.add(codigoe);
		pprod.add(codigot);
		pprod.add(tamañoe);
		pprod.add(tamañot);
		pprod.add(cantidade);
		pprod.add(cantidadt);
		
	}
	
	public void panelFecha()
	{
		pfecha = new JPanel();
		pfecha.setLayout(new GridLayout(1,3));
		pfecha.setPreferredSize(new Dimension(250,50));
		
		for(int i = 0; i < tDia.length; i++)
			tDia[i] = Integer.toString(i+1);
		cDia = new JComboBox(tDia);
		cDia.setBorder(BorderFactory.createTitledBorder("Dia"));
		cDia.setPreferredSize(new Dimension(40,20));
		for(int i = 0; i < tMes.length; i++)
			tMes[i] = Integer.toString(i+1);
		cMes = new JComboBox(tMes);
		cMes.setPreferredSize(new Dimension(40,20));
		cMes.setBorder(BorderFactory.createTitledBorder("Mes"));
		for(int i = 0; i < tAño.length; i++)
			tAño[i] = Integer.toString(i+2000);
		cAño = new JComboBox(tAño);
		cAño.setBorder(BorderFactory.createTitledBorder("Año"));
		cAño.setPreferredSize(new Dimension(40,20));
		
		pfecha.add(cDia);
		pfecha.add(cMes);
		pfecha.add(cAño);
	}
	
	private void cerrarDialogo() throws Exception
	{
		String dia = (String) cDia.getSelectedItem();
		String mes = (String) cMes.getSelectedItem();
		String año = (String) cAño.getSelectedItem();
		
		String nomProducto = (String) cProd.getSelectedItem();
		
		int linea = 0;
		int tamaño = 0;
		int cantidad = 0;
		
		for(int i = 0; i < prods.size(); i++)
		{
			Producto actual = (Producto) prods.get(i);
			String nom = actual.getNombre();
			if(nom.equals(nomProducto))
				linea = actual.getLinea();
		}
		
		try
		{
			tamaño = Integer.parseInt(tamañot.getText());
			cantidad = Integer.parseInt(cantidadt.getText());
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(this, "Ingrese solo numeros en tamaño y cantidad!", "Error", JOptionPane.WARNING_MESSAGE);
			dispose( );
			return;
		}
		
		Salida sal = new Salida(nomProducto,tamaño,cantidad,linea);
		
		principal.inventario.salidas.salidaMercaderia(this,tipoSalida, sal, dia, mes, año, NumeroDeSalida);
		
		dispose();
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == cProd)
		{
			String seleccionado = (String) cProd.getSelectedItem();
			for(int i = 0; i < prods.size(); i++)
			{
				Producto actual = (Producto) prods.get(i);
				String nom = actual.getNombre();
				if(nom.equals(seleccionado))
					codigot.setText(actual.getCodigo());
			}
		}
		
		if(e.getSource() == uno)
			tipoSalida = 1;
		
		if(e.getSource() == dos)
			tipoSalida = 2;
		
		if(e.getSource() == tres)
			tipoSalida = 3;
		
		if(e.getSource() == cuatro)
			tipoSalida = 4;
		
		if(e.getSource() == Aceptar)
		{
			try
			{
				cerrarDialogo();
				// Se guarda en la base de datos el numero de entrada nuevo para la siguiente entrada
				principal.inventario.setNumeroSalida(NumeroDeSalida+1);
			}
			catch(Exception exc)
			{
				JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			}
			finally
			{
				dispose();
			}
		}
		
		if(e.getSource() == Continuar)
		{
			try
			{
				cerrarDialogo();
				// Se debe abrir otro de estos dialogos con el mismo numero de entrada.
				DialogoSalida dSalida = new DialogoSalida(principal, NumeroDeSalida);
				dSalida.setVisible(true);
			}
			catch(Exception exc)
			{
				JOptionPane.showMessageDialog(this, exc.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			}
			finally
			{
				dispose();
			}
		}
	}


}
