package alvaroarriola.inventario.interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import alvaroarriola.inventario.mundo.*;

import javax.swing.JButton;
import javax.swing.JDialog; 
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DialogoProductoNuevo extends JDialog implements ActionListener
{

	private InterfazPrincipal principal;
	
	private JButton agregar;
	
	private JTextField[] camposTexto;
	
	private JLabel[] etiquetas;
	
	private JPanel pan;
	
	public DialogoProductoNuevo(InterfazPrincipal ip)
	{
		super(ip, true);
		principal = ip;
		
		agregar = new JButton("Agregar");
		agregar.addActionListener(this);
		
		setLayout(new BorderLayout());
		
		camposTexto = new JTextField[3];
		
		camposTexto[0] = new JTextField();
		camposTexto[1] = new JTextField();
		camposTexto[2] = new JTextField();
		
		etiquetas = new JLabel[3];
		
		etiquetas[0] = new JLabel("Linea:  ");
		etiquetas[1] = new JLabel("Codigo: ");
		etiquetas[2] = new JLabel("Nombre: ");
		
		for(int i = 0; i < etiquetas.length; i++)
		{
			etiquetas[i].setPreferredSize(new Dimension(80,20));
			camposTexto[i].setPreferredSize(new Dimension(80,20));
		}
		
		pan = new JPanel();
		pan.setLayout(new GridLayout(3,2));
		
		for(int i = 0; i < etiquetas.length; i++)
		{
			pan.add(etiquetas[i]);
			pan.add(camposTexto[i]);
		}
		
		setSize(200, 150);
		setTitle("Producto Nuevo");
		setLocationRelativeTo(principal);
		add(pan, BorderLayout.CENTER);
		add(agregar, BorderLayout.SOUTH);
	}
	
	private void cerrarDialogo() throws ProductoPreexistenteException
    {
		int Linean;
		String Codigon;
		String Nombren;
		if(camposTexto[0].getText().equals("") 	|| camposTexto[1].getText().equals("") || camposTexto[2].getText().equals(""))
    	{
    		JOptionPane.showMessageDialog(this, "Ingrese los datos!", "Error", JOptionPane.WARNING_MESSAGE);
    	}
		else
		{
			try
    		{
    			Linean = Integer.parseInt(camposTexto[0].getText());
    		}
    		catch(NumberFormatException e)
    		{
    			JOptionPane.showMessageDialog(this, "Ingrese solo numeros en la Linea!", "Error", JOptionPane.WARNING_MESSAGE);
    			dispose( );
    			return;
    		}
    		
    		Codigon = camposTexto[1].getText().toUpperCase();
    		Nombren = camposTexto[2].getText().toUpperCase();
    		
    		char ini = Nombren.charAt(0);
    		
    		if(Character.isDigit(ini))
    		{
    			String warn = "El nombre del producto no debe iniciar con un digito!";
    			JOptionPane.showMessageDialog(this, warn, "Error", JOptionPane.WARNING_MESSAGE);
				dispose( );
				return;
    		}
    			
    		try
    		{
    			principal.inventario.nuevoProducto(Linean, Nombren, Codigon);
    		}
    		catch(ProductoPreexistenteException e)
    		{
    			JOptionPane.showMessageDialog(this, "Ya existe el producto!", "Error", JOptionPane.WARNING_MESSAGE);
				dispose( );
				return;
    		}
    		
		}
			
			
		dispose();
    }
	
	public void actionPerformed(ActionEvent e) 
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
