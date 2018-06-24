package front;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ResultadoDiagnostico  extends JPanel implements WindowListener, ActionListener {
	
	public static String[] strDolorlumbarBE = { "inflamatorio", "mecanico" };
	public static String[] strDolorlumbarFE = { "Inflamatorio", "Mecánico" };
	public static int cantDolorlumbar = 2;
	
	public static String[] strGradoSospechaBE = { "NoHaySospechaSpax", "HaySospechaSpax", "AltaProbSpax" };
	public static String[] strGradoSospechaFE = { "No hay sospecha de SPAX", "Hay sospecha de SPAX", "Alta Probabilidad de SPAX" };
	public static int cantGradoSospecha = 3;	
	
	public static String[] strEnfermedadBE = {
			"NoHaySuficienteInformacion",
			"EII",
			"Uveitis",
			"EspondilitisAnquilosante",
			"ArtritisReactiva",
			"ArtritisPsoriasica",
            "EspondiloartritisIndiferenciada",
			"EspondiloartritisJuvenil",
			"ProblemaDegenerativo",
			"Psoriasis"
	};
	
	public static String[] strEnfermedadFE = {
			"No hay suficiente información",
			"EII",
			"Uveitis",
			"Espondilitis Anquilosante",
			"Artritis Reactiva",
			"Artritis Psoriásica",
            "Espondiloartritis Indiferenciada",
			"Espondiloartritis Juvenil",
			"Problema Degenerativo",
			"Psoriasis"
	};
	public static int cantEnfermedad = 10;	
	
	public static String[] strDerivacionBE = { 
			"Ninguna",
			"Reumatologo",
			"Gastroenterologo",
			"Oculista",
			"MedicoClinico",
			"Traumatologo",
			"Dermatologo"
			};
	public static String[] strDerivacionFE = { 
			"Ninguna",
			"Reumatólogo",
			"Gastroenterólogo",
			"Oculista",
			"Medico Clínico",
			"Traumatólogo",
			"Dermatólogo"
			};
	public static int cantDerivacion = 7;	

	
	
	
	public static String[] strEstudiosSolicitadosBE = { 
			"Ninguno",
			"ERS-PCR",
			"HLAB27",
			"RX",
			"RMN",
			};
	public static String[] strEstudiosSolicitadosFE = { 
			"Ninguno",
			"ERS-PCR",
			"Gen HLAB27",
			"Radiografía",
			"Resonancia Magnética",
			};
	public static int cantEstudiosSolicitados = 5;	
	
	
	protected static JFrame frame;
	protected JFrame prev_frame;
	
	protected static JOptionPane jOptionPane;
	
	public JLabel lbl_titulo;
	
	//datos del paciente (cortos)
	public JLabel lbl_nombre;
	public JTextField tfd_nombre;
	public JLabel lbl_apellido;
	public JTextField tfd_apellido;
	
	//datos del diagnostico
	public JLabel lbl_fecha;
	public JTextField tfd_fecha;
	
	public JLabel lbl_dolorlumbar;
	public JTextField tfd_dolorlumbar;
	
	public JLabel lbl_gradosospecha;
	public JTextField tfd_gradosospecha;
	
	public JLabel lbl_enfermedad;
	public JTextField tfd_enfermedad;
	
	public JLabel lbl_derivacion;
	public JTextField tfd_derivacion;
	
	public JLabel lbl_estudio_solicitado;
	public JTextField tfd_estudio_solicitado;
	
	protected JButton btFinalizar;
	
	public int current_x = 0;
	public int current_y = 0;
	
	public boolean estadoCarga = false;
	
	
	
	public ResultadoDiagnostico(JFrame prevframe) {
		prev_frame = prevframe;
		prev_frame.setVisible(false);
		
		
		setLayout(null);
		
		createAndShowGUI();
		
		current_x = 10;
		current_y = 10;
		
		//Titulo
		lbl_titulo = new JLabel("RESULTADO DEL DIAGNÓSTICO:");
		lbl_titulo.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X - SistemaDiagnostico.padding_controles,SistemaDiagnostico.alto_controles);
		add(lbl_titulo); 
		
		current_y = current_y + SistemaDiagnostico.alto_controles * 2;
		
		cargarDatosPaciente();
		cargarDolorLumbar();
		cargarGradoSospecha();
		cargarEnfermedad();
		cargarDerivacion();
		cargarEstudioSolicitado();
		
		current_y = current_y + SistemaDiagnostico.alto_controles * 2;
		
		btFinalizar = new JButton("Finalizar");
		btFinalizar.addActionListener(this);
		btFinalizar.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 - ( 150 / 2) + 100 ,current_y, 200, SistemaDiagnostico.alto_controles);
		add(btFinalizar);
		
		estadoCarga = true;

		
	}
	
	
	private void cargarDatosPaciente() {
		
		//Nombre
		lbl_nombre = new JLabel("Nombre:");
		lbl_nombre.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_nombre); //, gridBagConstraints);
		tfd_nombre = new JTextField ();
		tfd_nombre.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(tfd_nombre); //, gridBagConstraints);
		
		
		//Apellido
		current_y = current_y + SistemaDiagnostico.alto_controles;
		
		lbl_apellido = new JLabel("Apellido:");
		lbl_apellido.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_apellido); //, gridBagConstraints);
		tfd_apellido = new JTextField ();
		tfd_apellido.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(tfd_apellido); //, gridBagConstraints);
		
	}
	
	private void cargarDolorLumbar() {
		current_y = current_y + SistemaDiagnostico.alto_controles;
		//Dolor Lumbar
		lbl_dolorlumbar = new JLabel("Tipo de dolor lumbar:");
		lbl_dolorlumbar.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_dolorlumbar); //, gridBagConstraints);
		tfd_dolorlumbar = new JTextField ();
		tfd_dolorlumbar.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(tfd_dolorlumbar); //, gridBagConstraints);

	}
	
	private void cargarGradoSospecha() {
		current_y = current_y + SistemaDiagnostico.alto_controles;
		//Grado Sospecha
		lbl_gradosospecha = new JLabel("Grado de sospecha:");
		lbl_gradosospecha.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_gradosospecha); //, gridBagConstraints);
		tfd_gradosospecha = new JTextField ();
		tfd_gradosospecha.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(tfd_gradosospecha); //, gridBagConstraints);

	}
	
	private void cargarEnfermedad() {
		
		current_y = current_y + SistemaDiagnostico.alto_controles;
		//Grado Sospecha
		lbl_enfermedad = new JLabel("Enfermedad:");
		lbl_enfermedad.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_enfermedad); //, gridBagConstraints);
		tfd_enfermedad = new JTextField ();
		tfd_enfermedad.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(tfd_enfermedad); //, gridBagConstraints);

	}
	
	private void cargarDerivacion() {
		
		current_y = current_y + SistemaDiagnostico.alto_controles;
		//Grado Sospecha
		lbl_derivacion= new JLabel("Derivación:");
		lbl_derivacion.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_derivacion); //, gridBagConstraints);
		tfd_derivacion = new JTextField ();
		tfd_derivacion.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(tfd_derivacion); //, gridBagConstraints);
	}
	
	private void cargarEstudioSolicitado() {
		current_y = current_y + SistemaDiagnostico.alto_controles;
		//Grado Sospecha
		lbl_estudio_solicitado = new JLabel("Estudio Solicitado:");
		lbl_estudio_solicitado.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_estudio_solicitado); //, gridBagConstraints);
		tfd_estudio_solicitado = new JTextField ();
		tfd_estudio_solicitado.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(tfd_estudio_solicitado); //, gridBagConstraints);
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
