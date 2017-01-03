package alvaroarriola.inventario.interfaz;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import alvaroarriola.inventario.mundo.ConexionBD;
import alvaroarriola.inventario.mundo.Producto;

public class DialogoResultadoBusquedaReportes extends JDialog
{
	public InterfazPrincipal principal;
	
	String FechaInicial;
	String FechaFinal;
	String TablasTipos;
	
	public JTable tabla;
	
	public JScrollPane scroll;
	public String[] nombreProductos;
	
	static Statement stmt;
	static Statement stmtdos;
	
	static Connection conn;
	
	public DialogoResultadoBusquedaReportes(listaProductosReportes lpr, String fechauno, String fechados, String tablas, ArrayList prods)
	{
		super(lpr, true);
		principal = lpr.principal;
		
		FechaInicial = fechauno;
		FechaFinal = fechados;
		TablasTipos = tablas;
		
		nombreProductos = getNombresListaProds(prods);
		
		crearTabla();
		
		scroll = new JScrollPane(tabla);
		tabla.setFillsViewportHeight(true);
		
		scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scroll.setBorder( new LineBorder( Color.BLACK, 1 ) );
		
		setSize(750, 200);
		setTitle("Resultado Busqueda");
		setLocationRelativeTo(lpr);
		
		add(scroll);
	}
	
	public String[] getNombresListaProds(ArrayList listap)
	{
		String[] names = new String[listap.size()];
		
		for(int z = 0; z < listap.size(); z++)
		{
			Producto act = (Producto) listap.get(z);
			names[z] = act.getNombre();
		}
		
		return names;
	}
	
	public String getNombresWhere()
	{
		String finals = "producto = '";
		int z = 0;
		for(int i = 0; i < nombreProductos.length; i++)
		{
			if(z == 0)
				finals += nombreProductos[i] + "' ";
			else
			{
				finals += "OR producto = '" + nombreProductos[i] + "' ";
			}
			z++;
		}
		System.out.print(finals);
		return finals;
	}
	
	public void crearTabla()
	{
		DefaultTableModel modelo = new DefaultTableModel();
		tabla = new JTable(modelo);
		
		modelo.addColumn("Año-Mes-Dia");
		modelo.addColumn("Numero");
		modelo.addColumn("Linea");
		modelo.addColumn("Producto");
		modelo.addColumn("Tipo movimiento");
		modelo.addColumn("Medida");
		modelo.addColumn("Cantidad");
		
		conn = ConexionBD.connectDatabase();
		String driver = ConexionBD.getDriver();
		
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
			stmtdos = conn.createStatement();

			stmt.executeUpdate("CREATE TABLE resultado (fecha date, numero int, tipom text, linea integer, producto text, medida integer, cantidad integer)");
			
			StringTokenizer tokens = new StringTokenizer(TablasTipos,",");
			while(tokens.hasMoreTokens())
			{
				String tab = tokens.nextToken();
				String query = "SELECT * FROM " + tab + " WHERE fecha >= '" + FechaInicial +
				"' AND fecha <= '" + FechaFinal + "'";
				
				ResultSet rs = stmt.executeQuery(query);
				
				while (rs.next())
				{
					stmtdos.executeUpdate("INSERT INTO resultado " +
					"VALUES('"+rs.getString("fecha")+"',"+rs.getInt("numero")+" ,'"+tab+"', "+rs.getInt("linea")+", '"+rs.getString("producto")+"', "+
								rs.getInt("medida") + ", "+ rs.getInt("cantidad")+ ")");
				}
				
			}
			
			String query = "SELECT * FROM resultado WHERE "+getNombresWhere()+" ORDER BY fecha";
			
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next())
			{
				// Se crea un array que será una de las filas de la tabla.
				Object [] filaActual = new Object[7]; // Hay tres columnas en la tabla

				filaActual[0] = rs.getString("fecha");
				filaActual[1] = rs.getInt("numero");
				filaActual[2] = rs.getInt("linea");
				filaActual[3] = rs.getString("producto");
				filaActual[4] = darTipo(rs.getString("tipom"));
				filaActual[5] = rs.getInt("medida");
				filaActual[6] = rs.getInt("cantidad");
				
				// Se añade al modelo la fila completa.
				modelo.addRow(filaActual);
			}
			
			stmt.executeUpdate("DROP TABLE resultado");
			
			stmt.close();
			rs.close();
			conn.close();

		}
		catch (SQLException ex)
		{
			System.err.print("SQLException: ");
			System.err.println(ex.getMessage());
		}
		finally
		{
			try
			{
			stmt.executeUpdate("DROP TABLE resultado");
			
			
			stmt.close();
			conn.close();
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	public String darTipo(String s)
	{
		String n = "";
		
		String[] nombresTablas = {"ingresomercaderia","entradadevoluciones","entradahojas",
				"entradaprestamos","pedidomercaderia","salidadevoluciones","salidahojas",
				"salidaprestamos"};
		String[] nombresReales = {"Ingreso Mercaderia","Entrada Devoluciones","Entrada Hojas",
				"Entrada Prestamos","Pedido Mercaderia","Salida Devoluciones","Salida Hojas",
				"Salida Prestamos"};
		
		if(s.equals(nombresTablas[0]))
			n = nombresReales[0];
		if(s.equals(nombresTablas[1]))
			n = nombresReales[1];
		if(s.equals(nombresTablas[2]))
			n = nombresReales[2];
		if(s.equals(nombresTablas[3]))
			n = nombresReales[3];
		if(s.equals(nombresTablas[4]))
			n = nombresReales[4];
		if(s.equals(nombresTablas[5]))
			n = nombresReales[5];
		if(s.equals(nombresTablas[6]))
			n = nombresReales[6];
		if(s.equals(nombresTablas[7]))
			n = nombresReales[7];
		
		return n;
	}
}
