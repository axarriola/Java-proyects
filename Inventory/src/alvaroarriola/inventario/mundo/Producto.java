package alvaroarriola.inventario.mundo;

import java.io.Serializable;
import java.util.ArrayList;

public class Producto 
{
	
	/*
	 * Linea a la que pertenece
	 * Nombre del producto
	 * Codigo unico
	 */
	private int linea;
	private String nombre;
	private String codigo;
	
	public Producto(int linea, String name, String codigo)
	{
		this.linea = linea;
		this.nombre = revNombre(name);
		this.codigo = codigo;
	}
	
	public String revNombre(String n)
	{
		String nom;
		nom = n.replace(" ", "_");
		
		return nom;
	}
	
	public int getLinea()
	{
		return linea;
	}
	
	public String getNombre()
	{
		return nombre;
	}
	
	public String getCodigo()
	{
		return codigo;
	}
	
	/**
	 * Revisa si el producto nuevo no existe.
	 */
	public boolean revisarUnicidadDeProducto(ArrayList existentes)
	{
		String codigoNuevo = this.getCodigo();
		
		for(int i = 0; i < existentes.size(); i++)
		{
			Producto actual = (Producto) existentes.get(i);
			String codigoActual = actual.getCodigo();
			if(codigoNuevo.equalsIgnoreCase(codigoActual))
				return false;
		}
		
		return true;
	}
	
	public String toString()
	{
		return nombre;
	}
	
}
