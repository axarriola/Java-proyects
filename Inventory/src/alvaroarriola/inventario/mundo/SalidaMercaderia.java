package alvaroarriola.inventario.mundo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import alvaroarriola.inventario.interfaz.DialogoSalida;

public class SalidaMercaderia 
{
	/**
     * Statement
     */
	static Statement stmt;
	/**
     * Conexion con la base de datos
     */
	static Connection conn;
	
	/**
     * Nombres de las tablas en la base de datos
     */
	public final String spedido = "pedidomercaderia";         // 1
	public final String shojas = "salidahojas";               // 2
	public final String sdevoluciones = "salidadevoluciones"; // 3
	public final String sprestamos = "salidaprestamos";       // 4
	
	Inventario invent;
	
	public int NumeroDeSalida;
	
	/**
     * Se encarga de un ingreso de una mercaderia. Proceso principal
     * para el ingreso de mercaderia de cualquier tipo de entrada.
     * Primero se ve si en la tab
     */
	public void salidaMercaderia(DialogoSalida dialogo, int SalidaTipo, Salida sal, String dia, String mes, String año, int nsalida)
	{
		NumeroDeSalida = nsalida;
		// Se obtiene el nombre de la base de datos a la cual
		// ira el reporte.
		String BDestino = getNombreBDReporte(SalidaTipo);
		
		// Se obtiene el nombre del producto, el cual es el
		// mismo nombre de la base de datos en el cual se
		// "almacenara" la mercancia.
		String nombreProd = sal.getNombreProducto();
		
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
			
			// Primero revisamos si existe una fila con el tamaño del rollo.
			String query = "SELECT * FROM "+nombreProd+" WHERE medida = " + sal.getMedidaPorRollo();
			ResultSet rs = stmt.executeQuery(query);
			
			// Revisar si existe esa medida, si no evitar todo el proceso
			if(!rs.next())
			{
				JOptionPane.showMessageDialog(dialogo, "Ingrese medidas en existencia!", "Error", JOptionPane.WARNING_MESSAGE);
				dialogo.dispose();
				return;
			}
			
			int cantidad = rs.getInt("cantidad");
			
			// Revisar que la cantidad este en existencia
			if(cantidad < sal.getCantidadRollos())
			{
				JOptionPane.showMessageDialog(dialogo, "Ingrese cantidades en existencia!", "Error", JOptionPane.WARNING_MESSAGE);
				dialogo.dispose();
				return;
			}
				
			cantidad -= sal.getCantidadRollos();
			
			// Agregar la cantidad existente.
			stmt.executeUpdate("UPDATE " + nombreProd + " SET cantidad = " + cantidad + " WHERE medida = " +
			sal.getMedidaPorRollo());
			
			// Agregar Reporte:
			stmt.executeUpdate("INSERT INTO " + BDestino + " " +
			"VALUES('"+dia+"-"+mes+"-"+año+"',"+NumeroDeSalida+","+sal.Linea+",'"+nombreProd+"',"+sal.getMedidaPorRollo()+
			","+sal.getCantidadRollos()+")");

			stmt.close();
			rs.close();
			conn.close();
		} 
		catch(SQLException ex) 
		{
			System.err.println("SQLException: " + ex.getMessage());
		}
	}
	
	/**
     * Este metodo devuelve el nombre de la base de datos a la que se registrara
     * el reporte, dependiendo del numero recibido.
     */
	public String getNombreBDReporte(int tipoSalida)
	{
		String tipo = "";
		switch(tipoSalida)
		{
		case 1:
			tipo = spedido;
			break;
		case 2:
			tipo = shojas;
			break;
		case 3:
			tipo = sdevoluciones;
			break;
		case 4:
			tipo = sprestamos;
			break;
		}
		
		return tipo;
	}
}
