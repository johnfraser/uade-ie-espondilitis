package front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import data.DBHelper;
import model.Diagnostico;
import model.Dolor;

public class DatosDolencias extends JPanel implements WindowListener, ActionListener{
	private static final long serialVersionUID = 1L;
	
	
	public static final int ZONA_LUMBAR = 0;
	public static final int ZONA_DORSAL_CERVICAL = 1;
	public static final int ZONA_ART_OCU_PIEL = 2;
	public static final int ZONA_INTESTINAL = 3;
	
	protected MainFrame frame;
	
	private boolean esPrimeraDolencia = true;
	
	int zona_elegida;
	
	protected static JOptionPane jOptionPane;
	
	public static int id_dolor_actual;
	public static int id_paciente_actual;
	public static int id_diagnostico_actual;
	
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
	
	public DatosDolencias ( MainFrame prevframe , boolean esPrimeraDolencia) {
	//public DatosDolencias (boolean esPrimeraDolencia) {
		
		frame = prevframe;
		
		this.esPrimeraDolencia = esPrimeraDolencia;
		
		jOptionPane = new JOptionPane();
		
		id_dolor_actual = 0;
		id_paciente_actual = MainFrame.consulta.paciente.id_paciente;
		id_diagnostico_actual = 0;
		
		setLayout(null);
		
		//createAndShowGUI();
		
		current_x = 10;
		current_y = 10;
		
		//Titulo
		
		if (esPrimeraDolencia) {
			lbl_titulo = new JLabel("A continuación indique las características del dolor que manifiesta el paciente:");	
		}else {
			lbl_titulo = new JLabel("Indique las características del dolor adicional que manifiesta el paciente:");
		}
		lbl_titulo.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X - MainFrame.padding_controles,MainFrame.alto_controles);
		add(lbl_titulo); 
		
		current_y = current_y + MainFrame.alto_controles * 2;
		
		cargarZona();
		cargarOrigen_dolor();
		cargarDespierta();
		cargarMejoria();
		
		//edad_inicio_dolor
		current_y = current_y + MainFrame.alto_controles * 2;
		lbl_edad_inicio_dolor = new JLabel("Edad de inicio del dolor:");
		lbl_edad_inicio_dolor.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_edad_inicio_dolor);
		tfd_edad_inicio_dolor = new JTextField ();
		tfd_edad_inicio_dolor.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfd_edad_inicio_dolor);
		
		// meses_persistencia
		current_y = current_y + MainFrame.alto_controles * 2;
		lbl_meses_persistencia = new JLabel("¿Hace cuántos meses tiene el dolor?:");
		lbl_meses_persistencia.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_meses_persistencia);
		tfd_meses_persistencia = new JTextField ();
		tfd_meses_persistencia.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfd_meses_persistencia);
	
		cargarAspecto();
		cargarInflamacion();
		cargarRitmo();
		
	    current_y = current_y + MainFrame.alto_controles * 5;
	    
		btAtras = new JButton("Atrás");
		btAtras.addActionListener(this);
		btAtras.setBounds(MainFrame.APP_WINDOW_X / 2 - ( 150 / 2) - 150 ,current_y, 200 , MainFrame.alto_controles);
		add(btAtras);
		
		btSiguiente = new JButton("Siguiente");
		btSiguiente.addActionListener(this);
		btSiguiente.setBounds(MainFrame.APP_WINDOW_X / 2 - ( 150 / 2) + 100 ,current_y, 200, MainFrame.alto_controles);
		add(btSiguiente);
		
		
		estadoCarga = true;
		
		this.setVisible(true);
		
		checkZona(cmbzona);
		
	}
	
	
	private void cargarZona() {

		//Zona
		current_y = current_y + MainFrame.alto_controles;
		lbl_zona = new JLabel("Zona del dolor");
		lbl_zona.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
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
		cmbzona.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
		add(cmbzona);
	/*	
		rdbzona_columnalumbar = new JRadioButton("Columna Lumbar");
		rdbzona_columnalumbar.addActionListener(this);
		rdbzona_columnalumbar.setSelected(true);
		rdbzona_columnalumbar.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
	    add(rdbzona_columnalumbar);
	    
		current_y = current_y + MainFrame.alto_controles;
	    
	    rdbzona_columnadorsal = new JRadioButton("Columna Dorsal");
	    rdbzona_columnadorsal.addActionListener(this);
	    rdbzona_columnadorsal.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
	    add(rdbzona_columnadorsal);
	    
		current_y = current_y + MainFrame.alto_controles;
	    
		rdbzona_columnacervical = new JRadioButton("Columna Cervical");
		rdbzona_columnacervical.addActionListener(this);
		rdbzona_columnacervical.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
	    add(rdbzona_columnacervical);
	    
		current_y = current_y + MainFrame.alto_controles;
	    
		rdbzona_articular = new JRadioButton("Articular");
		rdbzona_articular.addActionListener(this);
		rdbzona_articular.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
	    add(rdbzona_articular);
	    
		current_y = current_y + MainFrame.alto_controles;
	    
		rdbzona_intestinal = new JRadioButton("Intestinal");
		rdbzona_intestinal.addActionListener(this);
		rdbzona_intestinal.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
	    add(rdbzona_intestinal);
	    
		current_y = current_y + MainFrame.alto_controles;
	    
		rdbzona_ocular = new JRadioButton("Ocular");
		rdbzona_ocular.addActionListener(this);
		rdbzona_ocular.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
	    add(rdbzona_ocular);
	    
		current_y = current_y + MainFrame.alto_controles;
	    
		rdbzona_piel = new JRadioButton("Piel");
		rdbzona_piel.addActionListener(this);
		rdbzona_piel.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
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
		current_y = current_y + MainFrame.alto_controles;
		
		//Zona
		current_y = current_y + MainFrame.alto_controles;
		lbl_origen_dolor = new JLabel("Origen del Dolor");
		lbl_origen_dolor.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_origen_dolor);
		
		
		cmborigen_dolor = new JComboBox<String>();
		cmborigen_dolor.setEditable(false);
		cmborigen_dolor.addActionListener(this);
		DefaultComboBoxModel<String> defaultComboBoxModelcmborigen_dolor = new DefaultComboBoxModel<String>();
		cmborigen_dolor.setModel(defaultComboBoxModelcmborigen_dolor);
		
		defaultComboBoxModelcmborigen_dolor.addElement("Motivo Desconocido");
		defaultComboBoxModelcmborigen_dolor.addElement("Levantó un peso excesivo");
		defaultComboBoxModelcmborigen_dolor.addElement("Ha realizado demasiado ejercicio");
		defaultComboBoxModelcmborigen_dolor.addElement("Mantuvo una mala postura");
		cmborigen_dolor.setSelectedItem(0);
		cmborigen_dolor.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
		add(cmborigen_dolor);
		
		/*
		
		rdborigen_dolor_desconocido = new JRadioButton("Motivo Desconocido");
		rdborigen_dolor_desconocido.addActionListener(this);
		rdborigen_dolor_desconocido.setSelected(true);
		rdborigen_dolor_desconocido.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
	    add(rdborigen_dolor_desconocido);
	    
		current_y = current_y + MainFrame.alto_controles;
		
		rdborigen_dolor_levantopeso = new JRadioButton("Levantó un peso excesivo");
		rdborigen_dolor_levantopeso.addActionListener(this);
		rdborigen_dolor_levantopeso.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
	    add(rdborigen_dolor_levantopeso);
	    
		current_y = current_y + MainFrame.alto_controles;
	    
		rdborigen_dolor_muchoejercicio = new JRadioButton("Ha realizado demasiado ejercicio");
		rdborigen_dolor_muchoejercicio.addActionListener(this);
		rdborigen_dolor_muchoejercicio.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
	    add(rdborigen_dolor_muchoejercicio);
	    
		current_y = current_y + MainFrame.alto_controles;
	    
		rdborigen_dolor_malapostura = new JRadioButton("Mantuvo una mala postura");
		rdborigen_dolor_malapostura.addActionListener(this);
		rdborigen_dolor_malapostura.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
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
		current_y = current_y + MainFrame.alto_controles;
		
		//Zona
		current_y = current_y + MainFrame.alto_controles;
		lbl_lodespiertanoche = new JLabel("¿Lo despierta de noche?");
		lbl_lodespiertanoche.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_lodespiertanoche);
		
		rdblodespiertanoche_no = new JRadioButton("No");
		rdblodespiertanoche_no.addActionListener(this);
		rdblodespiertanoche_no.setSelected(true);
		rdblodespiertanoche_no.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, 150 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(rdblodespiertanoche_no);
		
		rdblodespiertanoche_si = new JRadioButton("Si");
		rdblodespiertanoche_si.addActionListener(this);
		rdblodespiertanoche_si.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, 150 - MainFrame.padding_controles, MainFrame.alto_controles);
		rdblodespiertanoche_si.setBounds(MainFrame.APP_WINDOW_X / 2 + 150 , current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);

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
		current_y = current_y + MainFrame.alto_controles;
		
		//Zona
		current_y = current_y + MainFrame.alto_controles;
		lbl_mejoria = new JLabel("¿Identifica alguna mejoría?");
		lbl_mejoria.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_mejoria);
		
		rdbmejoria_ninguna = new JRadioButton("No");
		rdbmejoria_ninguna.addActionListener(this);
		rdbmejoria_ninguna.setSelected(true);
		rdbmejoria_ninguna.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
	    add(rdbmejoria_ninguna);
	    
		current_y = current_y + MainFrame.alto_controles;
		
		rdbmejoria_enreposo = new JRadioButton("Estando en reposo");
		rdbmejoria_enreposo.addActionListener(this);
		rdbmejoria_enreposo.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
	    add(rdbmejoria_enreposo);
	    
		current_y = current_y + MainFrame.alto_controles;
	    
		rdbmejoria_conactividad = new JRadioButton("Estando en actividad");
		rdbmejoria_conactividad.addActionListener(this);
		rdbmejoria_conactividad.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
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
		current_y = current_y + MainFrame.alto_controles;
		
		// Aspecto
		current_y = current_y + MainFrame.alto_controles;
		lbl_lodespiertanoche = new JLabel("Indicar aspecto de la zona afectada");
		lbl_lodespiertanoche.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_lodespiertanoche);
		
		rdbaspecto_Normal = new JRadioButton("Normal");
		rdbaspecto_Normal.addActionListener(this);
		rdbaspecto_Normal.setSelected(true);
		rdbaspecto_Normal.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, 150 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(rdbaspecto_Normal);
		
		rdbaspecto_Colorado = new JRadioButton("Colorado");
		rdbaspecto_Colorado.addActionListener(this);
		rdbaspecto_Colorado.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, 150 - MainFrame.padding_controles, MainFrame.alto_controles);
		rdbaspecto_Colorado.setBounds(MainFrame.APP_WINDOW_X / 2 + 150 , current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
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
			current_y = current_y + MainFrame.alto_controles;
			
			//Inflamacion
			current_y = current_y + MainFrame.alto_controles;
			lbl_mejoria = new JLabel("¿Presenta inflamación?");
			lbl_mejoria.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
			add(lbl_mejoria);
			
			rdbinflamacion_Ninguna = new JRadioButton("No");
			rdbinflamacion_Ninguna.addActionListener(this);
			rdbinflamacion_Ninguna.setSelected(true);
			rdbinflamacion_Ninguna.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
		    add(rdbinflamacion_Ninguna);
		    
			current_y = current_y + MainFrame.alto_controles;
			
			rdbinflamacion_Poca = new JRadioButton("Poca");
			rdbinflamacion_Poca.addActionListener(this);
			rdbinflamacion_Poca.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
		    add(rdbinflamacion_Poca);
		    
			current_y = current_y + MainFrame.alto_controles;
		    
			rdbinflamacion_Mucha = new JRadioButton("Mucha");
			rdbinflamacion_Mucha.addActionListener(this);
			rdbinflamacion_Mucha.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
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
			current_y = current_y + MainFrame.alto_controles;
			
			//Inflamacion
			current_y = current_y + MainFrame.alto_controles;
			lbl_ritmo_evacuatorio = new JLabel("Indique ritmo evacuatorio");
			lbl_ritmo_evacuatorio.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
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
			cmbritmo_evacuatorio.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
			add(cmbritmo_evacuatorio);
			
			/*
			
			
			rdbritmo_evacuatorio_Normal = new JRadioButton("Normal");
			rdbritmo_evacuatorio_Normal.addActionListener(this);
			rdbritmo_evacuatorio_Normal.setSelected(true);
			rdbritmo_evacuatorio_Normal.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
		    add(rdbritmo_evacuatorio_Normal);
		    
			current_y = current_y + MainFrame.alto_controles;
			
			rdbritmo_evacuatorio_Diarrea = new JRadioButton("Diarrea");
			rdbritmo_evacuatorio_Diarrea.addActionListener(this);
			rdbritmo_evacuatorio_Diarrea.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
		    add(rdbritmo_evacuatorio_Diarrea);
		    
			current_y = current_y + MainFrame.alto_controles;
		    
			rdbritmo_evacuatorio_Moco = new JRadioButton("Moco");
			rdbritmo_evacuatorio_Moco.addActionListener(this);
			rdbritmo_evacuatorio_Moco.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
		    add(rdbritmo_evacuatorio_Moco);
		    
			current_y = current_y + MainFrame.alto_controles;
		    
			rdbritmo_evacuatorio_Pus = new JRadioButton("Pus");
			rdbritmo_evacuatorio_Pus.addActionListener(this);
			rdbritmo_evacuatorio_Pus.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
		    add(rdbritmo_evacuatorio_Pus);
		    
			current_y = current_y + MainFrame.alto_controles;
		    
			rdbritmo_evacuatorio_Sangre = new JRadioButton("Sangre");
			rdbritmo_evacuatorio_Sangre.addActionListener(this);
			rdbritmo_evacuatorio_Sangre.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
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
		this.tfd_edad_inicio_dolor.setText("");
		this.tfd_meses_persistencia.setEnabled(false);
		this.tfd_meses_persistencia.setText("");
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
		this.tfd_edad_inicio_dolor.setText("");
		this.tfd_meses_persistencia.setEnabled(false);
		this.tfd_meses_persistencia.setText("");
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
		this.tfd_edad_inicio_dolor.setText("");
		this.tfd_meses_persistencia.setEnabled(false);
		this.tfd_meses_persistencia.setText("");
		this.rdbaspecto_Colorado.setEnabled(false);
		this.rdbaspecto_Normal.setEnabled(false);
		this.rdbinflamacion_Mucha.setEnabled(true);
		this.rdbinflamacion_Ninguna.setEnabled(true);
		this.rdbinflamacion_Poca.setEnabled(true);
		this.cmbritmo_evacuatorio.setEnabled(true);
	}
	

	  @Override
	  public void actionPerformed(ActionEvent arg0) {
		  // TODO Auto-generated method stub


		  if (estadoCarga) {

			  if ( arg0.getSource() == btSiguiente) {
				 
					  btSiguiente();
				  
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
			  	  zona_elegida = ZONA_LUMBAR;
				  estadoControlesLumbar();
			  }

		  if( selected.toString().equals("Columna Dorsal") || selected.toString().equals("Columna Cervical") ) {
		  	  zona_elegida = ZONA_DORSAL_CERVICAL;
			  estadoControlesDorsalCervical();

		  }
		  
		  if( selected.toString().equals("Articular") || selected.toString().equals("Ocular") || selected.toString().equals("Piel") ) {
		  	  zona_elegida = ZONA_ART_OCU_PIEL;
			  estadoControlesArtOcuPiel();

		  }
		  
		  
		  if( selected.toString().equals("Intestinal")){
		  	  zona_elegida = ZONA_INTESTINAL;
			  estadoControlesIntestinal();
		  }
		  
		  
	  }
	  
	
	private void btSiguiente() {
					
		if (validar()) {
			
			GuardarDolor();

			if ( zona_elegida == ZONA_LUMBAR && esPrimeraDolencia ){

				jOptionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
				jOptionPane.setOptionType(JOptionPane.YES_NO_OPTION);
				int jOptionResult = JOptionPane.showOptionDialog(frame, "¿El paciente presenta alguna otra dolencia?", "Dolencia adicional", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,null );

				if ( jOptionResult == JOptionPane.YES_OPTION) {

					//DatosDolencias datosDolencias = new DatosDolencias(frame, false);
					//this.setVisible(false);
					MainFrame.MostrarDatosSegundaDolencia(this);
					
				}
				else {
					//DatosAntecedentes datosAntecedentes = new DatosAntecedentes(frame);
					MainFrame.BorrarSegundaDolencia();
					MainFrame.MostrarDatosAntecedentes(this,esPrimeraDolencia);
				}

			} else {
				//DatosAntecedentes datosAntecedentes = new DatosAntecedentes(frame);
				MainFrame.BorrarSegundaDolencia();
				MainFrame.MostrarDatosAntecedentes(this,esPrimeraDolencia);
			}
			
		}


	}
	
	private void GuardarDolor() {
		
		String zona;
		String origen_dolor;
		String lodespiertanoche;
		String mejoria;
		int edad_inicio_dolor;
		int meses_persistencia;
		String aspecto;
		String inflamacion;
		String ritmo_evacuatorio;
		Object selectedItem;
		DBHelper dbHelper;
		
		dbHelper = new DBHelper();
		
		selectedItem = cmbzona.getSelectedItem();

		zona = "";
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
	defaultComboBoxModelcmborigen_dolor.addElement("Levantó un peso excesivo");
	defaultComboBoxModelcmborigen_dolor.addElement("Ha realizado demasiado ejercicio");
	defaultComboBoxModelcmborigen_dolor.addElement("Mantuvo una mala postura");
		 */

		origen_dolor = "";
		
		if ( cmborigen_dolor.isEnabled() ) {

			selectedItem = cmborigen_dolor.getSelectedItem();

			if ( selectedItem.toString().equals("Motivo Desconocido")){			
				origen_dolor = Dolor.ORIGEN_DOLOR_desconocido;
			}
			if ( selectedItem.toString().equals("Levantó un peso excesivo")){			
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

		lodespiertanoche = "";
		
		if ( rdblodespiertanoche_no.isEnabled() && rdblodespiertanoche_si.isEnabled() ) {

			if ( rdblodespiertanoche_no.isSelected() ){			
				lodespiertanoche = Dolor.DESPIERTA_no;
			} else {
				lodespiertanoche = Dolor.DESPIERTA_si;
			}
		}


		mejoria = "";
		
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
		}

		edad_inicio_dolor = 0;
		
		if ( tfd_edad_inicio_dolor.isEnabled() ) {
			edad_inicio_dolor = Integer.parseInt(tfd_edad_inicio_dolor.getText());
		}

		meses_persistencia = 0;
		if ( tfd_meses_persistencia.isEnabled() ) {
			meses_persistencia = Integer.parseInt(tfd_meses_persistencia.getText());
		}

		aspecto = "";
		if ( rdbaspecto_Normal.isEnabled() && rdbaspecto_Colorado.isEnabled() ) {

			if ( rdbaspecto_Normal.isSelected() ){			
				aspecto = Dolor.ASPECTO_Normal;
			} else {
				aspecto = Dolor.ASPECTO_Colorado;
			}
		}

		inflamacion = "";	
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
		}
		
		ritmo_evacuatorio = "";
		
		if ( cmbritmo_evacuatorio.isEnabled()) {
						
			selectedItem = cmbritmo_evacuatorio.getSelectedItem();

			if ( selectedItem.toString().equals("Normal")){			
				ritmo_evacuatorio = Dolor.RITMO_EVACUATORIO_Normal;
			}
			if ( selectedItem.toString().equals("Diarrea")){			
				ritmo_evacuatorio = Dolor.RITMO_EVACUATORIO_Diarrea;
			}
			if ( selectedItem.toString().equals("Moco")){			
				ritmo_evacuatorio = Dolor.RITMO_EVACUATORIO_Moco;
			}
			if ( selectedItem.toString().equals("Pus")){			
				ritmo_evacuatorio = Dolor.RITMO_EVACUATORIO_Pus;
			}
			if ( selectedItem.toString().equals("Sangre")){			
				ritmo_evacuatorio = Dolor.RITMO_EVACUATORIO_Sangre;
			}
		}
		
		
//		String condiciones[] = {String.valueOf(DBHelper.TABLE_Diagnostico_id_paciente)};
//		String valores[] = {String.valueOf(MainFrame.id_paciente_actual)};
//		int cantidad_condiciones = 1;
		
		
		//id_diagnostico_actual = dbHelper.getUltimoId(DBHelper.TABLE_Diagnostico_NAME, DBHelper.TABLE_Diagnostico_id_diagnostico, cantidad_condiciones,condiciones,valores) + 1;
		id_diagnostico_actual = dbHelper.getUltimoId(DBHelper.TABLE_Diagnostico_NAME, DBHelper.TABLE_Diagnostico_id_diagnostico) + 1;
		
		MainFrame.consulta.diagnostico = new Diagnostico(id_diagnostico_actual, id_paciente_actual);
		
		
//		String condiciones2[] = {String.valueOf(DBHelper.TABLE_Dolencias_id_paciente),String.valueOf(DBHelper.TABLE_Dolencias_id_diagnostico)};
//		String valores2[] = {String.valueOf(id_paciente_actual),String.valueOf(id_diagnostico_actual)};
//		int cantidad_condiciones2 = 2;
		
		//id_dolor_actual = dbHelper.getUltimoId(DBHelper.TABLE_Dolencias_NAME , DBHelper.TABLE_Dolencias_id_dolor , cantidad_condiciones2 , condiciones2 , valores2 ) + 1;
		id_dolor_actual = dbHelper.getUltimoId(DBHelper.TABLE_Dolencias_NAME , DBHelper.TABLE_Dolencias_id_dolor) + 1;
		
		Dolor dolor_actual = new Dolor(
				id_paciente_actual, 
				id_diagnostico_actual,
				id_dolor_actual,
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
		
		if (esPrimeraDolencia) {
			MainFrame.consulta.dolor1 = null;
			MainFrame.consulta.dolor1 = dolor_actual;
		}
		else {
			MainFrame.consulta.dolor2 = null;
			MainFrame.consulta.dolor2 = dolor_actual;
		}
	
	}
	
	private void btAtras() {
		
		//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		if (esPrimeraDolencia) {
			MainFrame.MostrarDatosPaciente(this);
		}else {
			MainFrame.MostrarDatosPrimeraDolencia(this);
		}
		
	
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		//frame.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		//frame.setVisible(true);
		String strMsg = "¿Está seguro que desea salir?";
		int jOptionResult = JOptionPane.showOptionDialog(frame, strMsg, "Consulta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,null );

		if ( jOptionResult == JOptionPane.YES_OPTION) {
			System.exit(0);
		}

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
		
		
		if ( this.tfd_edad_inicio_dolor.getText().isEmpty() && zona_elegida == ZONA_LUMBAR ) {
			strError = "Falta completar la edad de inicio de la dolencia";
		    resultado = false;
		}
		
		if ( this.tfd_meses_persistencia.getText().isEmpty() && zona_elegida == ZONA_LUMBAR ) {
			strError = "Falta completar cuántos meses hace que el paciente tiene dolor";
			resultado = false;
		}
		
		if ( (!MainFrame.validarTextoNumerico( this.tfd_edad_inicio_dolor.getText()) && zona_elegida == ZONA_LUMBAR   )) {
			strError = "Edad de inicio de la dolencia inválida";
			resultado = false;
		}
		
		if ( (!MainFrame.validarTextoNumerico( this.tfd_meses_persistencia.getText()) && zona_elegida == ZONA_LUMBAR )) {
			strError = "Cantidad de meses con dolor inválida";
			resultado = false;
		}
		
		if (resultado == false) {
			jOptionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(frame, strError, "Error", JOptionPane.ERROR_MESSAGE );
		}
		return resultado;
	}

}
