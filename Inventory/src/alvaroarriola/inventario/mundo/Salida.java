package alvaroarriola.inventario.mundo;

public class Salida 
{
	// Representa un ingreso de mercaderia, el producto que entra
	// la medida del rollo y la cantidad de estos.
	public String NombreProducto;
	public int Medida;
	public int Cantidad;
	public int Linea;
	
	public Salida(String nombreprod, int medida, int cantidad, int linea)
	{
		NombreProducto = nombreprod;
		this.Medida = medida;
		this.Cantidad = cantidad;
		this.Linea = linea;
	}
	
	public String getNombreProducto()
	{
		return NombreProducto;
	}
	
	public int getMedidaPorRollo()
	{
		return Medida;
	}
	
	public int getCantidadRollos()
	{
		return Cantidad;
	}
}
