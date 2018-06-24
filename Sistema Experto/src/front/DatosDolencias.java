package front;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import back.ModelManager;
import model.Dolor;

public class DatosDolencias extends JPanel implements WindowListener, ActionListener{
	
	protected static JFrame frame;
	protected JFrame prev_frame;
	
	boolean esPrimeraDolencia = true;
	
	protected static JOptionPane jOptionPane;
	
	public JLabel lbl_titulo;
	
	public JLabel lbl_zona;
	public JLabel lbl_origen_dolor;
	public JLabel lbl_lodespiertanoche;
	public JLabel lbl_mejoria;
	public JLabel lbl_edad_inicio_dolor;
	public JLabel lbl_meses_persistencia;
	public JLabel lbl_aspecto;
	public JLabel lbl_inflamacion;
	public JLabel lbl_ritmo_evacuatorio;
	
	public JComboBox<String> cmbzona;
	/*
	public JRadioButton rdbzona_columnalumbar;
	public JRadioButton rdbzona_columnadorsal;
	public JRadioButton rdbzona_columnacervical;
	public JRadioButton rdbzona_articular;
	public JRadioButton rdbzona_intestinal;
	public JRadioButton rdbzona_ocular;
	public JRadioButton rdbzona_piel;
	*/
	public JComboBox<String> cmborigen_dolor; 
	/*
	public JRadioButton rdborigen_dolor_levantopeso;
	public JRadioButton rdborigen_dolor_muchoejercicio;
	public JRadioButton rdborigen_dolor_malapostura;
	public JRadioButton rdborigen_dolor_desconocido;
	*/
	
	public JRadioButton rdblodespiertanoche_si;
	public JRadioButton rdblodespiertanoche_no;
	
	public JRadioButton rdbmejoria_ninguna;
	public JRadioButton rdbmejoria_enreposo;
	public JRadioButton rdbmejoria_conactividad;
	
	public JTextField tfd_edad_inicio_dolor;
	public JTextField tfd_meses_persistencia;
	
	public JRadioButton rdbaspecto_Colorado;
	public JRadioButton rdbaspecto_Normal;
	
	public JRadioButton rdbinflamacion_Ninguna;
	public JRadioButton rdbinflamacion_Poca;
	public JRadioButton rdbinflamacion_Mucha;
	
	public JComboBox<String> cmbritmo_evacuatorio;
	/*
	public JRadioButton rdbritmo_evacuatorio_Normal;
	public JRadioButton rdbritmo_evacuatorio_Diarrea;
	public JRadioButton rdbritmo_evacuatorio_Moco;
	public JRadioButton rdbritmo_evacuatorio_Pus;
	public JRadioButton rdbritmo_evacuatorio_Sangre;
	*/
	
	protected JButton btSiguiente;
	protected JButton btAtras;
	
	public int current_x = 0;
	public int current_y = 0;
	
	public boolean estadoCarga = false;
	
	public DatosDolencias ( JFrame prevframe , boolean esPrimeraDolencia) {
		
		prev_frame = prevframe;
		prev_frame.setVisible(false);
		
		this.esPrimeraDolencia = esPrimeraDolencia;
		
		jOptionPane = new JOptionPane();
		
		
		setLayout(null);
		
		createAndShowGUI();
		
		current_x = 10;
		current_y = 10;
		
		//Titulo
		lbl_titulo = new JLabel("A continuaci�n indique las caracter�sticas del dolor que manifiesta el paciente:");
		lbl_titulo.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X - SistemaDiagnostico.padding_controles,SistemaDiagnostico.alto_controles);
		add(lbl_titulo); 
		
		current_y = current_y + SistemaDiagnostico.alto_controles * 2;
		
		cargarZona();
		cargarOrigen_dolor();
		cargarDespierta();
		cargarMejoria();
		
		//edad_inicio_dolor
		current_y = current_y + SistemaDiagnostico.alto_controles * 2;
		lbl_edad_inicio_dolor = new JLabel("Edad de inicio del dolor:");
		lbl_edad_inicio_dolor.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_edad_inicio_dolor);
		tfd_edad_inicio_dolor = new JTextField ();
		tfd_edad_inicio_dolor.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(tfd_edad_inicio_dolor);
		
		// meses_persistencia
		current_y = current_y + SistemaDiagnostico.alto_controles * 2;
		lbl_meses_persistencia = new JLabel("�Hace cu�ntos meses tiene el dolor?:");
		lbl_meses_persistencia.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_meses_persistencia);
		tfd_meses_persistencia = new JTextField ();
		tfd_meses_persistencia.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(tfd_meses_persistencia);
	
		cargarAspecto();
		cargarInflamacion();
		cargarRitmo();
		
	    current_y = current_y + SistemaDiagnostico.alto_controles * 5;
	    
		btAtras = new JButton("Atr�s");
		btAtras.addActionListener(this);
		btAtras.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 - ( 150 / 2) - 150 ,current_y, 200 , SistemaDiagnostico.alto_controles);
		add(btAtras);
		
		btSiguiente = new JButton("Siguiente");
		btSiguiente.addActionListener(this);
		btSiguiente.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 - ( 150 / 2) + 100 ,current_y, 200, SistemaDiagnostico.alto_controles);
		add(btSiguiente);
		
		estadoCarga = true;
		checkZona(cmbzona);
		
	}
	
	
	private void cargarZona() {

		//Zona
		current_y = current_y + SistemaDiagnostico.alto_controles;
		lbl_zona = new JLabel("Zona del dolor");
		lbl_zona.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_zona);
		
		cmbzona = new JComboBox<String>();
		cmbzona.setEditable(false);
		cmbzona.addActionListener(this);
		DefaultComboBoxModel<String> defaultComboBoxModelzona = new DefaultComboBoxModel<String>();
		cmbzona.setModel(defaultComboBoxModelzona);
		
		if (esPrimeraDolencia ) {
		defaultComboBoxModelzona.addElement("Columna Lumbar");
		defaultComboBoxModelzona.addElement("Columna Dorsal");
		defaultComboBoxModelzona.addElement("Columna Cervical");
		}
		defaultComboBoxModelzona.addElement("Articular");
		defaultComboBoxModelzona.addElement("Intestinal");
		defaultComboBoxModelzona.addElement("Ocular");
		defaultComboBoxModelzona.addElement("Piel");
		cmbzona.setSelectedItem(0);
		cmbzona.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
		add(cmbzona);
	/*	
		rdbzona_columnalumbar = new JRadioButton("Columna Lumbar");
		rdbzona_columnalumbar.addActionListener(this);
		rdbzona_columnalumbar.setSelected(true);
		rdbzona_columnalumbar.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
	    add(rdbzona_columnalumbar);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
	    
	    rdbzona_columnadorsal = new JRadioButton("Columna Dorsal");
	    rdbzona_columnadorsal.addActionListener(this);
	    rdbzona_columnadorsal.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
	    add(rdbzona_columnadorsal);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
	    
		rdbzona_columnacervical = new JRadioButton("Columna Cervical");
		rdbzona_columnacervical.addActionListener(this);
		rdbzona_columnacervical.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
	    add(rdbzona_columnacervical);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
	    
		rdbzona_articular = new JRadioButton("Articular");
		rdbzona_articular.addActionListener(this);
		rdbzona_articular.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
	    add(rdbzona_articular);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
	    
		rdbzona_intestinal = new JRadioButton("Intestinal");
		rdbzona_intestinal.addActionListener(this);
		rdbzona_intestinal.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
	    add(rdbzona_intestinal);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
	    
		rdbzona_ocular = new JRadioButton("Ocular");
		rdbzona_ocular.addActionListener(this);
		rdbzona_ocular.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
	    add(rdbzona_ocular);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
	    
		rdbzona_piel = new JRadioButton("Piel");
		rdbzona_piel.addActionListener(this);
		rdbzona_piel.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
	    add(rdbzona_piel);
	    
	    ButtonGroup btgZona = new ButtonGroup();
	    btgZona.add(rdbzona_columnalumbar);
	    btgZona.add(rdbzona_columnadorsal);
		btgZona.add(rdbzona_columnacervical);
		btgZona.add(rdbzona_articular);
		btgZona.add(rdbzona_intestinal);
		btgZona.add(rdbzona_ocular);
		btgZona.add(rdbzona_piel);
		*/
		
	}
	
	private void cargarOrigen_dolor() {
/*
		public JRadioButton rdborigen_dolor_levantopeso;
		public JRadioButton rdborigen_dolor_muchoejercicio;
		public JRadioButton rdborigen_dolor_malapostura;
		public JRadioButton rdborigen_dolor_desconocido;
		*/
		current_y = current_y + SistemaDiagnostico.alto_controles;
		
		//Zona
		current_y = current_y + SistemaDiagnostico.alto_controles;
		lbl_origen_dolor = new JLabel("Origen del Dolor");
		lbl_origen_dolor.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_origen_dolor);
		
		
		cmborigen_dolor = new JComboBox<String>();
		cmborigen_dolor.setEditable(false);
		cmborigen_dolor.addActionListener(this);
		DefaultComboBoxModel<String> defaultComboBoxModelcmborigen_dolor = new DefaultComboBoxModel<String>();
		cmborigen_dolor.setModel(defaultComboBoxModelcmborigen_dolor);
		
		defaultComboBoxModelcmborigen_dolor.addElement("Motivo Desconocido");
		defaultComboBoxModelcmborigen_dolor.addElement("Levant� un peso excesivo");
		defaultComboBoxModelcmborigen_dolor.addElement("Ha realizado demasiado ejercicio");
		defaultComboBoxModelcmborigen_dolor.addElement("Mantuvo una mala postura");
		cmborigen_dolor.setSelectedItem(0);
		cmborigen_dolor.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
		add(cmborigen_dolor);
		
		/*
		
		rdborigen_dolor_desconocido = new JRadioButton("Motivo Desconocido");
		rdborigen_dolor_desconocido.addActionListener(this);
		rdborigen_dolor_desconocido.setSelected(true);
		rdborigen_dolor_desconocido.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
	    add(rdborigen_dolor_desconocido);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
		
		rdborigen_dolor_levantopeso = new JRadioButton("Levant� un peso excesivo");
		rdborigen_dolor_levantopeso.addActionListener(this);
		rdborigen_dolor_levantopeso.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
	    add(rdborigen_dolor_levantopeso);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
	    
		rdborigen_dolor_muchoejercicio = new JRadioButton("Ha realizado demasiado ejercicio");
		rdborigen_dolor_muchoejercicio.addActionListener(this);
		rdborigen_dolor_muchoejercicio.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
	    add(rdborigen_dolor_muchoejercicio);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
	    
		rdborigen_dolor_malapostura = new JRadioButton("Mantuvo una mala postura");
		rdborigen_dolor_malapostura.addActionListener(this);
		rdborigen_dolor_malapostura.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
	    add(rdborigen_dolor_malapostura);
	    
	    /*
		public JRadioButton rdborigen_dolor_levantopeso;
		public JRadioButton rdborigen_dolor_muchoejercicio;
		public JRadioButton rdborigen_dolor_malapostura;
		public JRadioButton rdborigen_dolor_desconocido;
		*/
	    /*
	    ButtonGroup btgOrigen_dolor = new ButtonGroup();
	    btgOrigen_dolor.add(rdborigen_dolor_levantopeso);
	    btgOrigen_dolor.add(rdborigen_dolor_muchoejercicio);
	    btgOrigen_dolor.add(rdborigen_dolor_malapostura);
	    btgOrigen_dolor.add(rdborigen_dolor_desconocido);
	    
	    */
	}
	
	private void cargarDespierta() {
		/*
		public JRadioButton rdblodespiertanoche_si;
		public JRadioButton rdblodespiertanoche_no;
		*/
		current_y = current_y + SistemaDiagnostico.alto_controles;
		
		//Zona
		current_y = current_y + SistemaDiagnostico.alto_controles;
		lbl_lodespiertanoche = new JLabel("�Lo despierta de noche?");
		lbl_lodespiertanoche.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_lodespiertanoche);
		
		rdblodespiertanoche_no = new JRadioButton("No");
		rdblodespiertanoche_no.addActionListener(this);
		rdblodespiertanoche_no.setSelected(true);
		rdblodespiertanoche_no.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, 150 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(rdblodespiertanoche_no);
		
		rdblodespiertanoche_si = new JRadioButton("Si");
		rdblodespiertanoche_si.addActionListener(this);
		rdblodespiertanoche_si.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, 150 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		rdblodespiertanoche_si.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 + 150 , current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);

		add(rdblodespiertanoche_si);

		ButtonGroup btgDespierta = new ButtonGroup();
		btgDespierta.add(rdblodespiertanoche_no);
		btgDespierta.add(rdblodespiertanoche_si);

	}
	

	private void cargarMejoria() {
/*
	public JRadioButton rdbmejoria_ninguna;
	public JRadioButton rdbmejoria_enreposo;
	public JRadioButton rdbmejoria_conactividad;
		*/
		current_y = current_y + SistemaDiagnostico.alto_controles;
		
		//Zona
		current_y = current_y + SistemaDiagnostico.alto_controles;
		lbl_mejoria = new JLabel("�Identifica alguna mejor�a?");
		lbl_mejoria.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_mejoria);
		
		rdbmejoria_ninguna = new JRadioButton("No");
		rdbmejoria_ninguna.addActionListener(this);
		rdbmejoria_ninguna.setSelected(true);
		rdbmejoria_ninguna.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
	    add(rdbmejoria_ninguna);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
		
		rdbmejoria_enreposo = new JRadioButton("Estando en reposo");
		rdbmejoria_enreposo.addActionListener(this);
		rdbmejoria_enreposo.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
	    add(rdbmejoria_enreposo);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
	    
		rdbmejoria_conactividad = new JRadioButton("Estando en actividad");
		rdbmejoria_conactividad.addActionListener(this);
		rdbmejoria_conactividad.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
	    add(rdbmejoria_conactividad);
	    
	    
	    /*
		public JRadioButton rdborigen_dolor_levantopeso;
		public JRadioButton rdborigen_dolor_muchoejercicio;
		public JRadioButton rdborigen_dolor_malapostura;
		public JRadioButton rdborigen_dolor_desconocido;
		*/
	    
	    ButtonGroup btgMejoria = new ButtonGroup();
	    btgMejoria.add(rdbmejoria_ninguna);
	    btgMejoria.add(rdbmejoria_enreposo);
	    btgMejoria.add(rdbmejoria_conactividad);
	}
	

	private void cargarAspecto() {
		/*
	public JRadioButton rdbaspecto_Colorado;
	public JRadioButton rdbaspecto_Normal;
		*/
		current_y = current_y + SistemaDiagnostico.alto_controles;
		
		// Aspecto
		current_y = current_y + SistemaDiagnostico.alto_controles;
		lbl_lodespiertanoche = new JLabel("Indicar aspecto de la zona afectada");
		lbl_lodespiertanoche.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_lodespiertanoche);
		
		rdbaspecto_Normal = new JRadioButton("Normal");
		rdbaspecto_Normal.addActionListener(this);
		rdbaspecto_Normal.setSelected(true);
		rdbaspecto_Normal.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, 150 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(rdbaspecto_Normal);
		
		rdbaspecto_Colorado = new JRadioButton("Colorado");
		rdbaspecto_Colorado.addActionListener(this);
		rdbaspecto_Colorado.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, 150 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		rdbaspecto_Colorado.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 + 150 , current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(rdbaspecto_Colorado);

		ButtonGroup btgAspecto = new ButtonGroup();
		btgAspecto.add(rdbaspecto_Normal);
		btgAspecto.add(rdbaspecto_Colorado);

	}
	
	private void cargarInflamacion() {
		
		/*
	public JRadioButton rdbinflamacion_Ninguna;
	public JRadioButton rdbinflamacion_Poca;
	public JRadioButton rdbinflamacion_Mucha;
			*/
			current_y = current_y + SistemaDiagnostico.alto_controles;
			
			//Inflamacion
			current_y = current_y + SistemaDiagnostico.alto_controles;
			lbl_mejoria = new JLabel("�Presenta inflamaci�n?");
			lbl_mejoria.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
			add(lbl_mejoria);
			
			rdbinflamacion_Ninguna = new JRadioButton("No");
			rdbinflamacion_Ninguna.addActionListener(this);
			rdbinflamacion_Ninguna.setSelected(true);
			rdbinflamacion_Ninguna.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
		    add(rdbinflamacion_Ninguna);
		    
			current_y = current_y + SistemaDiagnostico.alto_controles;
			
			rdbinflamacion_Poca = new JRadioButton("Poca");
			rdbinflamacion_Poca.addActionListener(this);
			rdbinflamacion_Poca.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
		    add(rdbinflamacion_Poca);
		    
			current_y = current_y + SistemaDiagnostico.alto_controles;
		    
			rdbinflamacion_Mucha = new JRadioButton("Mucha");
			rdbinflamacion_Mucha.addActionListener(this);
			rdbinflamacion_Mucha.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
		    add(rdbinflamacion_Mucha);
		    
		    
		    /*
	public JRadioButton rdbinflamacion_Ninguna;
	public JRadioButton rdbinflamacion_Poca;
	public JRadioButton rdbinflamacion_Mucha;
			*/
		    
		    ButtonGroup btgInflamacion = new ButtonGroup();
		    btgInflamacion.add(rdbinflamacion_Ninguna);
		    btgInflamacion.add(rdbinflamacion_Poca);
		    btgInflamacion.add(rdbinflamacion_Mucha);
		
	}
	
	
	private void cargarRitmo() {
		
		/*
	public JRadioButton rdbritmo_evacuatorio_Normal;
	public JRadioButton rdbritmo_evacuatorio_Diarrea;
	public JRadioButton rdbritmo_evacuatorio_Moco;
	public JRadioButton rdbritmo_evacuatorio_Pus;
	public JRadioButton rdbritmo_evacuatorio_Sangre;
			*/
			current_y = current_y + SistemaDiagnostico.alto_controles;
			
			//Inflamacion
			current_y = current_y + SistemaDiagnostico.alto_controles;
			lbl_ritmo_evacuatorio = new JLabel("Indique ritmo evacuatorio");
			lbl_ritmo_evacuatorio.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
			add(lbl_ritmo_evacuatorio);
			
			
			cmbritmo_evacuatorio = new JComboBox<String>();
			cmbritmo_evacuatorio.setEditable(false);
			cmbritmo_evacuatorio.addActionListener(this);
			DefaultComboBoxModel<String> defaultComboBoxModelcmbritmo_evacuatorio = new DefaultComboBoxModel<String>();
			cmbritmo_evacuatorio.setModel(defaultComboBoxModelcmbritmo_evacuatorio);
			
			defaultComboBoxModelcmbritmo_evacuatorio.addElement("Normal");
			defaultComboBoxModelcmbritmo_evacuatorio.addElement("Diarrea");
			defaultComboBoxModelcmbritmo_evacuatorio.addElement("Moco");
			defaultComboBoxModelcmbritmo_evacuatorio.addElement("Pus");
			defaultComboBoxModelcmbritmo_evacuatorio.addElement("Sangre");
			cmbritmo_evacuatorio.setSelectedItem(0);
			cmbritmo_evacuatorio.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
			add(cmbritmo_evacuatorio);
			
			/*
			
			
			rdbritmo_evacuatorio_Normal = new JRadioButton("Normal");
			rdbritmo_evacuatorio_Normal.addActionListener(this);
			rdbritmo_evacuatorio_Normal.setSelected(true);
			rdbritmo_evacuatorio_Normal.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
		    add(rdbritmo_evacuatorio_Normal);
		    
			current_y = current_y + SistemaDiagnostico.alto_controles;
			
			rdbritmo_evacuatorio_Diarrea = new JRadioButton("Diarrea");
			rdbritmo_evacuatorio_Diarrea.addActionListener(this);
			rdbritmo_evacuatorio_Diarrea.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
		    add(rdbritmo_evacuatorio_Diarrea);
		    
			current_y = current_y + SistemaDiagnostico.alto_controles;
		    
			rdbritmo_evacuatorio_Moco = new JRadioButton("Moco");
			rdbritmo_evacuatorio_Moco.addActionListener(this);
			rdbritmo_evacuatorio_Moco.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
		    add(rdbritmo_evacuatorio_Moco);
		    
			current_y = current_y + SistemaDiagnostico.alto_controles;
		    
			rdbritmo_evacuatorio_Pus = new JRadioButton("Pus");
			rdbritmo_evacuatorio_Pus.addActionListener(this);
			rdbritmo_evacuatorio_Pus.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
		    add(rdbritmo_evacuatorio_Pus);
		    
			current_y = current_y + SistemaDiagnostico.alto_controles;
		    
			rdbritmo_evacuatorio_Sangre = new JRadioButton("Sangre");
			rdbritmo_evacuatorio_Sangre.addActionListener(this);
			rdbritmo_evacuatorio_Sangre.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
		    add(rdbritmo_evacuatorio_Sangre);
		    
		    
		    /*
	public JRadioButton rdbritmo_evacuatorio_Normal;
	public JRadioButton rdbritmo_evacuatorio_Diarrea;
	public JRadioButton rdbritmo_evacuatorio_Moco;
	public JRadioButton rdbritmo_evacuatorio_Pus;
	public JRadioButton rdbritmo_evacuatorio_Sangre;
			*/
		  
			/*
		    ButtonGroup btgRitmo = new ButtonGroup();
		    btgRitmo.add(rdbritmo_evacuatorio_Normal);
		    btgRitmo.add(rdbritmo_evacuatorio_Diarrea);
		    btgRitmo.add(rdbritmo_evacuatorio_Moco);
		    btgRitmo.add(rdbritmo_evacuatorio_Pus);
		    btgRitmo.add(rdbritmo_evacuatorio_Sangre);
		
		*/
		
		
	}
	
	private void estadoControlesDorsalCervical() {
		
		this.cmborigen_dolor.setEnabled(false);
		this.rdblodespiertanoche_no.setEnabled(false);
		this.rdblodespiertanoche_si.setEnabled(false);
		this.rdbmejoria_conactividad.setEnabled(false);
		this.rdbmejoria_enreposo.setEnabled(false);
		this.rdbmejoria_ninguna.setEnabled(false);
		this.tfd_edad_inicio_dolor.setEnabled(false);
		this.tfd_meses_persistencia.setEnabled(false);
		this.rdbaspecto_Colorado.setEnabled(false);
		this.rdbaspecto_Normal.setEnabled(false);
		this.rdbinflamacion_Mucha.setEnabled(false);
		this.rdbinflamacion_Ninguna.setEnabled(false);
		this.rdbinflamacion_Poca.setEnabled(false);
		this.cmbritmo_evacuatorio.setEnabled(false);
		
		
	}
	
	private void estadoControlesLumbar() {
		
		this.cmborigen_dolor.setEnabled(true);
		this.rdblodespiertanoche_no.setEnabled(true);
		this.rdblodespiertanoche_si.setEnabled(true);
		this.rdbmejoria_conactividad.setEnabled(true);
		this.rdbmejoria_enreposo.setEnabled(true);
		this.rdbmejoria_ninguna.setEnabled(true);
		this.tfd_edad_inicio_dolor.setEnabled(true);
		this.tfd_meses_persistencia.setEnabled(true);
		this.rdbaspecto_Colorado.setEnabled(false);
		this.rdbaspecto_Normal.setEnabled(false);
		this.rdbinflamacion_Mucha.setEnabled(false);
		this.rdbinflamacion_Ninguna.setEnabled(false);
		this.rdbinflamacion_Poca.setEnabled(false);
		this.cmbritmo_evacuatorio.setEnabled(false);
	}
	
	private void estadoControlesArtOcuPiel() {
		this.cmborigen_dolor.setEnabled(false);
		this.rdblodespiertanoche_no.setEnabled(false);
		this.rdblodespiertanoche_si.setEnabled(false);
		this.rdbmejoria_conactividad.setEnabled(false);
		this.rdbmejoria_enreposo.setEnabled(false);
		this.rdbmejoria_ninguna.setEnabled(false);
		this.tfd_edad_inicio_dolor.setEnabled(false);
		this.tfd_meses_persistencia.setEnabled(false);
		this.rdbaspecto_Colorado.setEnabled(true);
		this.rdbaspecto_Normal.setEnabled(true);
		this.rdbinflamacion_Mucha.setEnabled(true);
		this.rdbinflamacion_Ninguna.setEnabled(true);
		this.rdbinflamacion_Poca.setEnabled(true);
		this.cmbritmo_evacuatorio.setEnabled(false);
	}
	
	private void estadoControlesIntestinal() {
		this.cmborigen_dolor.setEnabled(false);
		this.rdblodespiertanoche_no.setEnabled(false);
		this.rdblodespiertanoche_si.setEnabled(false);
		this.rdbmejoria_conactividad.setEnabled(false);
		this.rdbmejoria_enreposo.setEnabled(false);
		this.rdbmejoria_ninguna.setEnabled(false);
		this.tfd_edad_inicio_dolor.setEnabled(false);
		this.tfd_meses_persistencia.setEnabled(false);
		this.rdbaspecto_Colorado.setEnabled(false);
		this.rdbaspecto_Normal.setEnabled(false);
		this.rdbinflamacion_Mucha.setEnabled(true);
		this.rdbinflamacion_Ninguna.setEnabled(true);
		this.rdbinflamacion_Poca.setEnabled(true);
		this.cmbritmo_evacuatorio.setEnabled(true);
	}
	
	
	
	  private void createAndShowGUI() {
	        //Create and set up the window.
	        frame = new JFrame(SistemaDiagnostico.APP_NAME + " "+ SistemaDiagnostico.APP_VERSION);
	        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


	        //Add contents to the window.
	        frame.add(this);
	        frame.addWindowListener(this);
	        
	        
	        frame.setSize(SistemaDiagnostico.APP_WINDOW_X, SistemaDiagnostico.APP_WINDOW_Y);		
	   		
	   		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	   	    int x = (int) ((screen.getWidth() - frame.getWidth()) / 2);
	   	    int y = (int) ((screen.getHeight() - frame.getHeight()) / 2);
	   	    
	   	    frame.setLocation(x, y);
	   	 
	   	    frame.setPreferredSize(new Dimension(SistemaDiagnostico.APP_WINDOW_X,SistemaDiagnostico.APP_WINDOW_Y));
	         
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }

	  @Override
	  public void actionPerformed(ActionEvent arg0) {
		  // TODO Auto-generated method stub


		  if (estadoCarga) {

			  if ( arg0.getSource() == btSiguiente) {
				  if (validar()) {
					  btSiguiente();
				  }
			  }

			  if ( arg0.getSource() == btAtras) {
				  btAtras();
			  }

			  if ( arg0.getSource() == cmbzona ) {

				  /*
		defaultComboBoxModelzona.addElement("Columna Lumbar");
		defaultComboBoxModelzona.addElement("Columna Dorsal");
		defaultComboBoxModelzona.addElement("Columna Cervical");
		defaultComboBoxModelzona.addElement("Articular");
		defaultComboBoxModelzona.addElement("Intestinal");
		defaultComboBoxModelzona.addElement("Ocular");
		defaultComboBoxModelzona.addElement("Piel");
				   */
				  checkZona(cmbzona);
			  }
		  }

	  }
	  
	  private void checkZona(JComboBox<String> c) {

		  Object selected = c.getSelectedItem();
		  
		  if( selected.toString().equals("Columna Lumbar")){
				  estadoControlesLumbar();
			  }

		  if( selected.toString().equals("Columna Dorsal") || selected.toString().equals("Columna Cervical") ) {

			  estadoControlesDorsalCervical();

		  }
		  
		  if( selected.toString().equals("Articular") || selected.toString().equals("Ocular") || selected.toString().equals("Piel") ) {

			  estadoControlesArtOcuPiel();

		  }
		  
		  
		  if( selected.toString().equals("Intestinal")){
			  estadoControlesIntestinal();
		  }
		  
		  
	  }
	  
	
	private void btSiguiente() {
			
		String zona = "";
		String origen_dolor = "";
		String lodespiertanoche = "";
		String mejoria = "";
		int edad_inicio_dolor;
		int meses_persistencia;
		String aspecto = "";
		String inflamacion = "";
		String ritmo_evacuatorio = "";
		
		Object selectedItem;
		
		selectedItem = cmbzona.getSelectedItem();
		
		if ( selectedItem.toString().equals("Columna Lumbar")){			
			zona = Dolor.ZONA_columnalumbar;
		}
		if ( selectedItem.toString().equals("Columna Dorsal")){			
			zona = Dolor.ZONA_columnadorsal;
		}
		if ( selectedItem.toString().equals("Columna Cervical")){			
			zona = Dolor.ZONA_columnacervical;
		}
		if ( selectedItem.toString().equals("Articular")){			
			zona = Dolor.ZONA_articular;
		}
		if ( selectedItem.toString().equals("Intestinal")){			
			zona = Dolor.ZONA_intestinal;
		}
		if ( selectedItem.toString().equals("Ocular")){			
			zona = Dolor.ZONA_ocular;
		}
		if ( selectedItem.toString().equals("Piel")){			
			zona = Dolor.ZONA_piel;
		}
		
		/*
		defaultComboBoxModelcmborigen_dolor.addElement("Motivo Desconocido");
		defaultComboBoxModelcmborigen_dolor.addElement("Levant� un peso excesivo");
		defaultComboBoxModelcmborigen_dolor.addElement("Ha realizado demasiado ejercicio");
		defaultComboBoxModelcmborigen_dolor.addElement("Mantuvo una mala postura");
		*/
		
		
		if ( cmborigen_dolor.isEnabled() ) {

			selectedItem = cmborigen_dolor.getSelectedItem();

			if ( selectedItem.toString().equals("Motivo Desconocido")){			
				origen_dolor = Dolor.ORIGEN_DOLOR_desconocido;
			}
			if ( selectedItem.toString().equals("Levant� un peso excesivo")){			
				origen_dolor = Dolor.ORIGEN_DOLOR_levantopeso;
			}
			if ( selectedItem.toString().equals("Ha realizado demasiado ejercicio")){			
				origen_dolor = Dolor.ORIGEN_DOLOR_muchoejercicio;
			}
			if ( selectedItem.toString().equals("Mantuvo una mala postura")){			
				origen_dolor = Dolor.ORIGEN_DOLOR_malapostura;
			}

		} else {
			origen_dolor = Dolor.ORIGEN_DOLOR_nil;
		}
		
		
		if ( rdblodespiertanoche_no.isEnabled() && rdblodespiertanoche_si.isEnabled() ) {
			
			if ( rdblodespiertanoche_no.isSelected() ){			
				lodespiertanoche = Dolor.DESPIERTA_no;
			} else {
				lodespiertanoche = Dolor.DESPIERTA_si;
			}
		}else {
			lodespiertanoche = "";
		}
		
		
		
		if ( rdbmejoria_ninguna.isEnabled() && rdbmejoria_enreposo.isEnabled() && rdbmejoria_conactividad.isEnabled() ) {
			
			if ( rdbmejoria_enreposo.isSelected() ){			
				mejoria = Dolor.MEJORIA_enreposo;
			} else {
				if (rdbmejoria_conactividad.isSelected()) {
					mejoria = Dolor.MEJORIA_conactividad;
				} else {
					mejoria = "";
				}
			}
		}else {
			mejoria = "";
		}
		
		
		if ( tfd_edad_inicio_dolor.isEnabled() ) {
			edad_inicio_dolor = Integer.parseInt(tfd_edad_inicio_dolor.getText());
		}else {
			edad_inicio_dolor = 0;
		}
		
		if ( tfd_meses_persistencia.isEnabled() ) {
			meses_persistencia = Integer.parseInt(tfd_meses_persistencia.getText());
		}else {
			meses_persistencia = 0;
		}
		
		
		if ( rdbaspecto_Normal.isEnabled() && rdbaspecto_Colorado.isEnabled() ) {
			
			if ( rdbaspecto_Normal.isSelected() ){			
				lodespiertanoche = Dolor.ASPECTO_Normal;
			} else {
				lodespiertanoche = Dolor.ASPECTO_Colorado;
			}
		}else {
			lodespiertanoche = "";
		}
		
		
		if ( rdbinflamacion_Mucha.isEnabled() && rdbinflamacion_Poca.isEnabled()  && rdbinflamacion_Ninguna.isEnabled()  ) {
			
			if ( rdbinflamacion_Mucha.isSelected() ){			
				inflamacion = Dolor.INFLAMACION_Mucha;
			} else {
					if ( rdbinflamacion_Poca.isSelected()) {
						inflamacion = Dolor.INFLAMACION_Poca;
					}	else {
								if ( rdbinflamacion_Ninguna.isSelected()) {
									inflamacion = Dolor.INFLAMACION_Ninguna;
								}else {
									inflamacion = "";
								}
						}	
				}
		}else {
			lodespiertanoche = "";
		}
		
		
		
		Object selected = cmbzona.getSelectedItem();
		  
		  if ( selected.toString().equals("Columna Lumbar") && esPrimeraDolencia ){
			  
			    jOptionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
				jOptionPane.setOptionType(JOptionPane.YES_NO_OPTION);
				int jOptionResult = JOptionPane.showOptionDialog(frame, "�El paciente presenta alguna otra dolencia?", "Dolencia adicional", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,null );
				
				ModelManager.c.GenerarDolor2(
						SistemaDiagnostico.id_diagnostico, 
						SistemaDiagnostico.id_dolor,
						zona, origen_dolor, lodespiertanoche, mejoria, edad_inicio_dolor, meses_persistencia, aspecto, inflamacion, ritmo_evacuatorio);
			
				
				if ( jOptionResult == JOptionPane.YES_OPTION) {
					/*
					SistemaDiagnostico.consulta.GenerarDolor1(
							SistemaDiagnostico.id_diagnostico, 
							SistemaDiagnostico.id_dolor, 
							zona, 
							origen_dolor, 
							lodespiertanoche,
							mejoria,
							edad_inicio_dolor,
							meses_persistencia,
							aspecto,
							inflamacion,
							ritmo_evacuatorio
							);
					*/
					
					
					DatosDolencias datosDolencias = new DatosDolencias(frame, false);
				}
		  		else {
					DatosAntecedentes datosDolencias = new DatosAntecedentes(frame);
				}
 
		  } else {
			  ModelManager.c.GenerarDolor2(
						SistemaDiagnostico.id_diagnostico, 
						SistemaDiagnostico.id_dolor,
						zona, origen_dolor, lodespiertanoche, mejoria, edad_inicio_dolor, meses_persistencia, aspecto, inflamacion, ritmo_evacuatorio);
			
			  
			  DatosAntecedentes datosDolencias = new DatosAntecedentes(frame);
		  }
	}
	
	private void btAtras() {
		
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		prev_frame.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		prev_frame.setVisible(true);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean validar() {
		
		boolean resultado=true;
		String strError="";
		
		if ( this.tfd_edad_inicio_dolor.getText().isEmpty()) {
			strError = "Falta completar la edad de inicio de la dolencia";
		    resultado = false;
		}
		
		if ( this.tfd_meses_persistencia.getText().isEmpty()) {
			strError = "Falta completar la cantidad de meses de persistencia";
			resultado = false;
		}
		
		if ( ! SistemaDiagnostico.validarTextoNumerico( this.tfd_edad_inicio_dolor.getText() )) {
			strError = "Dato no num�rico en el campo de la edad de inicio de la dolencia";
			resultado = false;
		}
		
		if ( ! SistemaDiagnostico.validarTextoNumerico( this.tfd_meses_persistencia.getText() )) {
			strError = "Dato no num�rico en el campo de la cantidad de meses de persistencia";
			resultado = false;
		}
		
		if (resultado == false) {
			jOptionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(frame, strError, "Error", JOptionPane.ERROR_MESSAGE );
		}
		return resultado;
	}

}
