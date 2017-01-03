package alvaroarriola.inventario.mundo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Esta clase crea la conexion con la base de datos
 * y puede dar el driver necesario para hacer consultas
 * o modificaciones.
 */
public class ConexionBD
{
	/**
     * Statement
     */
	static Statement stmt;
	/**
     * Conexion con la base de datos
     */
	private static Connection conn;
	
	/**
     * Informacion para la base de datos
     */
	private static String url = "jdbc:postgresql://localhost:5432/";
	private static String db = "postgres";
	private static String driver = "org.postgresql.Driver";
	private static String user = "postgres";
	private static String password = "ATO112358";
	
	/**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @return La conexion establecida
     */
	public static Connection connectDatabase()
	{
		try
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+db, user, password);
			return conn;
		} 
		catch(Exception e) 
		{
			System.err.println("Falló la conexión");
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getDriver()
	{
		return driver;
	}
	
	// Lo utilizo para crear todas las tablas que se necesitan para el inventario
	public static void crearTablasInventario()
	{
		conn = connectDatabase();
		String driver = getDriver();
		
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
			
			// Tablas de ingresos y salidas
			String[] nombresTablas = {"ingresomercaderia","entradadevoluciones","entradahojas",
					"entradaprestamos","pedidomercaderia","salidadevoluciones","salidahojas",
					"salidaprestamos"};
			
			for(int i = 0; i < nombresTablas.length;i++)
			stmt.executeUpdate("CREATE TABLE "+ nombresTablas[i] +" (fecha date, numero integer, linea integer,"+
					" producto text, medida integer, cantidad integer)");
			
			// Tabla de productos:
			stmt.executeUpdate("CREATE TABLE productos (linea integer, nombre text PRIMARY KEY, codigo text)");
			
			// Tabla para numero de entrada y salida:
			stmt.executeUpdate("CREATE TABLE nums (tipo text PRIMARY KEY, numero integer)");
			
			stmt.close();
			conn.close();

		} 
		catch(SQLException ex) 
		{
			System.err.println("SQLException: " + ex.getMessage());
		}
	}
}
