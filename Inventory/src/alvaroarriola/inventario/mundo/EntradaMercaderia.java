package alvaroarriola.inventario.mundo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntradaMercaderia 
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
	public final String eingreso = "ingresomercaderia";        // 1
	public final String ehojas = "entradahojas";               // 2
	public final String edevoluciones = "entradadevoluciones"; // 3
	public final String eprestamos = "entradaprestamos";       // 4
	
	Inventario invent;
	
	public int NumeroDeEntrada;
	
	/**
     * Se encarga de un ingreso de una mercaderia. Proceso principal
     * para el ingreso de mercaderia de cualquier tipo de entrada.
     * Primero se ve si en la tab
     */
	public void ingresoMercaderia(int EntradaTipo, Ingreso in, String dia, String mes, String año, int n)
	{
		NumeroDeEntrada = n;
		// Se obtiene el nombre de la base de datos a la cual
		// ira el reporte.
		String BDestino = getNombreBDReporte(EntradaTipo);
		System.out.println(BDestino);
		
		// Se obtiene el nombre del producto, el cual es el
		// mismo nombre de la base de datos en el cual se
		// "almacenara" la mercancia.
		String nombreProd = in.getNombreProducto();
		System.out.println(nombreProd);
		
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
			String query = "SELECT * FROM "+nombreProd+" WHERE medida = " + in.getMedidaPorRollo();
			ResultSet rs = stmt.executeQuery(query);
			int cantidad = 0;
			if(!rs.next())
			{
				// Crear fila con ese tamaño de rollo si no existia.
				stmt.executeUpdate("INSERT INTO " + nombreProd +
				" VALUES(" + in.getMedidaPorRollo()+ ", "+ cantidad +")");
			}
			else
			{
				cantidad += rs.getInt("cantidad");
			}
			
			cantidad += in.getCantidadRollos();
			
			// Agregar la cantidad existente.
			stmt.executeUpdate("UPDATE " + nombreProd + " SET cantidad = " + cantidad + " WHERE medida = " +
			in.getMedidaPorRollo());
			
			// Agregar Reporte:
			stmt.executeUpdate("INSERT INTO " + BDestino + " " +
			"VALUES('"+dia+"-"+mes+"-"+año+"',"+n+","+in.Linea+",'"+nombreProd+"',"+in.getMedidaPorRollo()+
			","+in.getCantidadRollos()+")");
			
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
	public String getNombreBDReporte(int tipoEntrada)
	{
		String tipo = "";
		switch(tipoEntrada)
		{
		case 1:
			tipo = eingreso;
			break;
		case 2:
			tipo = ehojas;
			break;
		case 3:
			tipo = edevoluciones;
			break;
		case 4:
			tipo = eprestamos;
			break;
		}
		
		return tipo;
	}
	
}
