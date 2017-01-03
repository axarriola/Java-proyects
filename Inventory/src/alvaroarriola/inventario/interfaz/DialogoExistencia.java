package alvaroarriola.inventario.interfaz;

import java.awt.Color;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import alvaroarriola.inventario.mundo.ConexionBD;
import alvaroarriola.inventario.mundo.Producto;

public class DialogoExistencia extends JDialog
{
	public JTable tabla;
	
	public JScrollPane scroll;
	
	static Statement stmt;
	
	static Connection conn;
	
	public InterfazPrincipal principal;
	
	public ArrayList productos;
	
	public DialogoExistencia(InterfazPrincipal ip)
	{
		super(ip,true);
		principal = ip;
		
		productos = principal.inventario.darListaProductos();
		
		crearTabla();
		
		scroll = new JScrollPane(tabla);
		tabla.setFillsViewportHeight(true);
		
		scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setBorder( new LineBorder( Color.BLACK, 1 ) );
		
		setSize(500, 200);
		setTitle("Mercaderia en existencia");
		setLocationRelativeTo(principal);
		
		add(scroll);
	}
	
	public void crearTabla()
	{
		DefaultTableModel modelo = new DefaultTableModel();
		tabla = new JTable(modelo);
		
		modelo.addColumn("Linea");
		modelo.addColumn("Codigo");
		modelo.addColumn("Nombre");
		modelo.addColumn("Medida");
		modelo.addColumn("Cantidad");

		for(int indTabla = 0; indTabla < productos.size(); indTabla++)
		{
			Producto actual = (Producto) productos.get(indTabla);
			String lineaProdActual = Integer.toString(actual.getLinea());
			String codigoProdActual = actual.getCodigo();
			String nombreProdActual = actual.getNombre();
			
			conn = ConexionBD.connectDatabase();
			String driver = ConexionBD.getDriver();
			String query = "SELECT * FROM " + nombreProdActual;
			
			try 
			{
				Class.forName(driver);
			} 
			catch (java.lang.ClassNotFoundException e)
			{
				System.err.print("ClassNotFoundException: ");
				System.err.println(e.getMessage());
			}
			
			try 
			{
				stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery(query);
				int contador = 0;
				// Bucle para cada resultado en la consulta
				while (rs.next())
				{
					// Se crea un array que será una de las filas de la tabla.
					Object [] filaActual = new Object[5]; // Hay tres columnas en la tabla
					
					if(contador == 0)
					{
						filaActual[0] = lineaProdActual;
						filaActual[1] = codigoProdActual;
						filaActual[2] = nombreProdActual;
					}

					filaActual[3] = rs.getInt("medida");
					filaActual[4] = rs.getInt("cantidad");
					
					// Se añade al modelo la fila completa.
					modelo.addRow(filaActual);
					contador++;
				}
				
				stmt.close();
				rs.close();
				conn.close();

			}
			catch (SQLException ex)
			{
				System.err.print("SQLException: ");
				System.err.println(ex.getMessage());
			}
			
		}
	}
}
