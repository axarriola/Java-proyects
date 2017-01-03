package alvaroarriola.inventario.mundo;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Inventario 
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
     * Contiene los productos
     */
	private ArrayList productos;
	
	public EntradaMercaderia entradas;
	
	public SalidaMercaderia salidas;
	
	public Inventario() 
	{
		// Llamar este metodo solo la primera vez q se corre el programa!
		ConexionBD.crearTablasInventario(); // Crea las tablas de ingresos y salidas! y la tabla de productos.
		//
        productos = cargarProductosBD();
        entradas = new EntradaMercaderia();
        salidas = new SalidaMercaderia();
	}
	
	/**
     * Al agregar un producto nuevo se revisa si no existe, luego se agrega a la lista,
     * a la tabla en donde se guardan los productos, y se crea una tabla para las existencias
     * de ese producto.
     */
	public void nuevoProducto(int linea, String nombre, String codigo) throws ProductoPreexistenteException
	{
		Producto nuevo = new Producto(linea, nombre, codigo);
		if(nuevo.revisarUnicidadDeProducto(productos))
		{
			productos.add(nuevo);
			agregarProductoNuevoBD(nuevo);
			crearTablaProductoNuevoBD(nuevo);
		}
		else
			throw new ProductoPreexistenteException();
	}
	
	/**
     * Carga los productos de la base de datos al ArrayList.
     */
	public ArrayList cargarProductosBD()
	{
		ArrayList lista = new ArrayList();
		
		conn = ConexionBD.connectDatabase();
		String driver = ConexionBD.getDriver();
		String query = "SELECT * FROM productos";
		
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
			//conn = (Connection)DriverManager.getConnection(url+db, user, password);
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			
			// Se agregan uno por uno los productos al ArrayList.
			int rowCount = 1;
			while (rs.next())
			{
				int linea = rs.getInt("linea");
				String nombre = rs.getString("nombre");
				String codigo = rs.getString("codigo");
				
				lista.add( new Producto(linea, nombre, codigo) );
				
				rowCount++;
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
		
		return lista;
	}
	
	/**
     * Agrega una nueva fila a la tabla con el producto nuevo.
     */
	public void agregarProductoNuevoBD(Producto newp)
	{
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
	
			stmt.executeUpdate("INSERT INTO productos " +
			"VALUES("+newp.getLinea()+", '"+newp.getNombre()+"', '"+newp.getCodigo()+"')");
			
			System.out.println("Se agregó correctamente el producto.");

			stmt.close();
			conn.close();

		} 
		catch(SQLException ex) 
		{
			System.err.println("SQLException: " + ex.getMessage());
		}
	}
	
	public void crearTablaProductoNuevoBD(Producto newp)
	{
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
			
			stmt.executeUpdate("CREATE TABLE "+ newp.getNombre() +" (medida integer PRIMARY KEY, cantidad integer)");

			stmt.close();
			conn.close();

		} 
		catch(SQLException ex) 
		{
			System.err.println("SQLException: " + ex.getMessage());
		}
	}
	
	public ArrayList darListaProductos()
	{
		return productos;
	}
	
	public int getNumeroEntrada()
	{
		int n = 0;
		
		conn = ConexionBD.connectDatabase();
		String driver = ConexionBD.getDriver();
		String query = "SELECT * FROM nums WHERE tipo = 'entrada'";
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
			
			while (rs.next())
				n = rs.getInt("numero");
			
			stmt.close();
			rs.close();
			conn.close();

		} 
		catch(SQLException ex) 
		{
			System.err.println("SQLException: " + ex.getMessage());
		}
		
		return n;
	}
	
	public int getNumeroSalida()
	{
		int n = 0;
		
		conn = ConexionBD.connectDatabase();
		String driver = ConexionBD.getDriver();
		String query = "SELECT * FROM nums WHERE tipo = 'salida'";
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
			
			while (rs.next())
				n = rs.getInt("numero");
			
			stmt.close();
			rs.close();
			conn.close();

		} 
		catch(SQLException ex) 
		{
			System.err.println("SQLException: " + ex.getMessage());
		}
		
		return n;
	}
	
	public int setNumeroEntrada(int n)
	{
		
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
			
			stmt.executeUpdate("UPDATE nums SET numero = " + n + " WHERE tipo = 'entrada'");
			
			stmt.close();
			conn.close();

		} 
		catch(SQLException ex) 
		{
			System.err.println("SQLException: " + ex.getMessage());
		}
		
		return n;
	}
	
	public int setNumeroSalida(int n)
	{
		
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
			
			stmt.executeUpdate("UPDATE nums SET numero = " + n + " WHERE tipo = 'salida'");
			
			stmt.close();
			conn.close();

		} 
		catch(SQLException ex) 
		{
			System.err.println("SQLException: " + ex.getMessage());
		}
		
		return n;
	}
	
}
