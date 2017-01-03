package alvaroarriola.inventario.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import alvaroarriola.inventario.mundo.Producto;

public class listaProductosReportes extends JDialog implements ActionListener
{
	private ImageIcon derecha;
	private ImageIcon izquierda;
	private JButton Agregar;
	private JButton Eliminar;
	private JList listano; // no escogidos
	private JList listasi; // si escogidos
	private ArrayList si;
	private ArrayList no;
	private JScrollPane scrollno;
	private JScrollPane scrollsi;
	public InterfazPrincipal principal;
	private JButton Aceptar;
	
	private String fechauno;
	private String fechados;
	private String tablasABuscar;
	
	public listaProductosReportes(DialogoBusquedaReportes dbr, String fechaun, String fechado, String tablas)
	{
		super(dbr,true);
		principal = dbr.principal;
		fechauno = fechaun;
		fechados = fechado;
		tablasABuscar = tablas;
		
		no = (ArrayList) principal.inventario.darListaProductos().clone();
		si = new ArrayList();
		
		listasi = new JList();
        listasi.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        scrollsi = new JScrollPane( listasi );
        scrollsi.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        scrollsi.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scrollsi.setBorder(BorderFactory.createTitledBorder("Seleccionados"));
        //scrollsi.setPreferredSize(new Dimension(100,100));
        
        listano = new JList(no.toArray() );
        listano.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        scrollno = new JScrollPane( listano );
        scrollno.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        scrollno.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scrollno.setBorder(BorderFactory.createTitledBorder("No seleccionados"));
        //scrollsi.setPreferredSize(new Dimension(100,100));
		
		setSize(500, 300);
		setTitle("Lista de productos para la busqueda");
		setLocationRelativeTo(dbr);
		
		derecha = new ImageIcon("flechader.png");
		izquierda = new ImageIcon("flechaizq.png");
		Agregar = new JButton("Agregar",derecha);
		Agregar.setToolTipText("Agregar a la lista");
		Eliminar = new JButton("Eliminar",izquierda);
		Eliminar.setToolTipText("Eliminar de la lista");
		Agregar.addActionListener(this);
		Eliminar.addActionListener(this);
		JPanel pp = new JPanel(new GridLayout(2,1,5,5));
		pp.add(Agregar);
		pp.add(Eliminar);
		
		JPanel lists = new JPanel(new GridLayout(1,3,1,1));
		lists.add(scrollno);
		lists.add(pp);
		lists.add(scrollsi);
		
		setLayout(new BorderLayout());
		
		Aceptar = new JButton("Aceptar");
		Aceptar.addActionListener(this);
		
		add(lists, BorderLayout.CENTER);
		add(Aceptar, BorderLayout.SOUTH);
		
	}
	
	public void refrescarListas()
	{
		listano.setListData( no.toArray( ) );
        listano.setSelectedIndex( 0 );
        listasi.setListData( si.toArray( ) );
        listasi.setSelectedIndex( 0 );
	}
	
	private void cerrarDialogo()
	{
		
		if(si == null)
		{
			dispose();
			return;
		}
		
		DialogoResultadoBusquedaReportes diag = new DialogoResultadoBusquedaReportes(this,fechauno,fechados,tablasABuscar, si);
		diag.setVisible(true);
		
		this.dispose();
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getSource() == Agregar)
		{
			Producto p = (Producto) listano.getSelectedValue();
			if(p != null)
			{
				si.add(p);
				no.remove(p);
				refrescarListas();
			}
		}
		
		if(e.getSource() == Eliminar)
		{
			Producto p = (Producto) listasi.getSelectedValue();
			if(p != null)
			{
				no.add(p);
				si.remove(p);
				refrescarListas();
			}
		}
		
		if(e.getSource() == Aceptar)
		{
			try
			{
				cerrarDialogo();
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
