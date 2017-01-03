package alvaroarriola.inventario.interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DialogoBusquedaReportes extends JFrame implements ActionListener
{
	public InterfazPrincipal principal;
	
	JPanel pfechas;
	JPanel ptipos;
	
	String[] nombresTablas = {"ingresomercaderia","entradadevoluciones","entradahojas",
			"entradaprestamos","pedidomercaderia","salidadevoluciones","salidahojas",
			"salidaprestamos"};
	String[] nombresChecks = {"Ingreso Mercaderia","Entrada Devoluciones","Entrada Hojas",
			"Entrada Prestamos","Pedido Mercaderia","Salida Devoluciones","Salida Hojas",
			"Salida Prestamos"};
	
	private JCheckBox[] checks;
	
	private JComboBox cDiauno;
	private JComboBox cDiados;
	private String[] tDia = new String[31];
	private JComboBox cMesuno;
	private JComboBox cMesdos;
	private String[] tMes = new String[12];
	private JComboBox cAñouno;
	private JComboBox cAñodos;
	private String[] tAño = new String[20];
	private JLabel edia = new JLabel("Dia");
	private JLabel emes = new JLabel("Mes");
	private JLabel eaño = new JLabel("Año");
	
	private JButton buscar;
	
	public DialogoBusquedaReportes(InterfazPrincipal ip)
	{
		//super(ip,true);
		principal = ip;
		
		panelFechas();
		tipoMovimientos();
		
		setSize(500, 300);
		setTitle("Busqueda Reportes");
		setLocationRelativeTo(principal);
		
		buscar = new JButton("Buscar");
		buscar.addActionListener(this);
		
		//setLayout(new GridLayout(3,1));
		setLayout(new BorderLayout());
		
		add(pfechas, BorderLayout.NORTH);
		add(ptipos, BorderLayout.CENTER);
		add(buscar, BorderLayout.SOUTH);
		
	}
	
	public void tipoMovimientos()
	{
		ptipos = new JPanel(new GridLayout(4,2,1,1));
		checks = new JCheckBox[8];
		
		for(int i = 0; i < checks.length; i++)
		{
			checks[i] = new JCheckBox(nombresChecks[i]);
			checks[i].setSelected(false);
			ptipos.add(checks[i]);
		}
	}
	
	public void panelFechas()
	{
		pfechas = new JPanel();
		pfechas.setLayout(new GridLayout(1,3));
		pfechas.setPreferredSize(new Dimension(500,50));
		
		for(int i = 0; i < tDia.length; i++)
			tDia[i] = Integer.toString(i+1);
		cDiauno = new JComboBox(tDia);
		cDiauno.setBorder(BorderFactory.createTitledBorder("Dia 1"));
		cDiauno.setPreferredSize(new Dimension(40,20));
		for(int i = 0; i < tMes.length; i++)
			tMes[i] = Integer.toString(i+1);
		cMesuno = new JComboBox(tMes);
		cMesuno.setPreferredSize(new Dimension(40,20));
		cMesuno.setBorder(BorderFactory.createTitledBorder("Mes 1"));
		for(int i = 0; i < tAño.length; i++)
			tAño[i] = Integer.toString(i+2000);
		cAñouno = new JComboBox(tAño);
		cAñouno.setBorder(BorderFactory.createTitledBorder("Año 1"));
		cAñouno.setPreferredSize(new Dimension(40,20));
		
		cDiados = new JComboBox(tDia);
		cDiados.setBorder(BorderFactory.createTitledBorder("Dia 2"));
		cDiados.setPreferredSize(new Dimension(40,20));
		cMesdos = new JComboBox(tMes);
		cMesdos.setPreferredSize(new Dimension(40,20));
		cMesdos.setBorder(BorderFactory.createTitledBorder("Mes 2"));
		cAñodos = new JComboBox(tAño);
		cAñodos.setBorder(BorderFactory.createTitledBorder("Año 2"));
		cAñodos.setPreferredSize(new Dimension(40,20));
		
		pfechas.add(cDiauno);
		pfechas.add(cMesuno);
		pfechas.add(cAñouno);
		pfechas.add(cDiados);
		pfechas.add(cMesdos);
		pfechas.add(cAñodos);
	}
	
	private void cerrarDialogo()
	{
		String diauno = (String) cDiauno.getSelectedItem();
		String mesuno = (String) cMesuno.getSelectedItem();
		String añouno = (String) cAñouno.getSelectedItem();
		String fechauno = añouno + "-" + mesuno + "-" + diauno;
		
		String diados = (String) cDiados.getSelectedItem();
		String mesdos = (String) cMesdos.getSelectedItem();
		String añodos = (String) cAñodos.getSelectedItem();
		String fechados = añodos + "-" + mesdos + "-" + diados;
		
		// Se crea un String con los nombres de las tablas separados por comas.
		String tablasABuscar = tablasPorBuscar();
		
		if(tablasABuscar.equals(""))
		{
			dispose();
			return;
		}
		
		// listaProductosReportes AQUI
		listaProductosReportes repo = new listaProductosReportes(this,fechauno,fechados,tablasABuscar);
		repo.setVisible(true);
		
		dispose();
	}
	
	public String tablasPorBuscar()
	{
		String tablas = "";
		int contSeleccionados = 0;
		for(int on = 0; on < checks.length; on++)
			if(checks[on].isSelected())
				contSeleccionados++;
		
		if(contSeleccionados == 0)
		{
			JOptionPane.showMessageDialog(this, "Seleccione algun tipo de movimiento!", "Error", JOptionPane.WARNING_MESSAGE);
			dispose( );
			return "";
		}
		
		int conto = 0;
		for(int in = 0; in < checks.length; in++)
		{
			if(checks[in].isSelected())
			{
				conto++;
				tablas += nombresTablas[in];
			
				if(conto != contSeleccionados)
					tablas += ",";
			}
		}
		
		return tablas;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == buscar)
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
