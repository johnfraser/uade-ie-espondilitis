package front;

import java.awt.Dimension;
import java.awt.Font;
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

import data.DBHelper;
import model.AntecedentesFamiliares;
import model.AntecedentesPaciente;
import model.Consulta;
import model.Estudio;

public class ResultadoDiagnostico  extends JPanel implements WindowListener, ActionListener {
	
	public static String[] strDolorlumbarBE = { "nil", "notiene", "inflamatorio", "mecanico" };
	public static String[] strDolorlumbarFE = { "No presenta", "No tiene" , "Inflamatorio", "Mecánico" };
	public static int cantDolorlumbar = 4;
	
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
	
	protected MainFrame frame;
	
	protected static JOptionPane jOptionPane;
	
	protected static int id_paciente_actual;
	
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
	
	
	
	public ResultadoDiagnostico(MainFrame prevframe) {
		
		frame = prevframe;
				
		id_paciente_actual = MainFrame.consulta.paciente.id_paciente;
		
		setLayout(null);
		
		current_x = 10;
		current_y = 10;
		
		current_y = current_y + MainFrame.alto_controles ;
		
		//Titulo
		lbl_titulo = new JLabel("RESULTADO DEL DIAGNÓSTICO");
//		lbl_titulo.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X - MainFrame.padding_controles,MainFrame.alto_controles);
		Font font = lbl_titulo.getFont();
		lbl_titulo.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize() + 4));
		int w = lbl_titulo.getFontMetrics(lbl_titulo.getFont()).stringWidth(lbl_titulo.getText());
		lbl_titulo.setBounds(MainFrame.APP_WINDOW_X/2 - w/2,current_y,w,MainFrame.alto_controles);
		add(lbl_titulo); 
		
		current_y = current_y + MainFrame.alto_controles * 4;
		current_y = current_y + MainFrame.alto_controles;
		
		cargarDatosPaciente();
		current_y = current_y + MainFrame.alto_controles ;
		cargarDolorLumbar();
		current_y = current_y + MainFrame.alto_controles ;
		cargarGradoSospecha();
		current_y = current_y + MainFrame.alto_controles ;
		cargarEnfermedad();
		current_y = current_y + MainFrame.alto_controles ;
		cargarDerivacion();
		current_y = current_y + MainFrame.alto_controles ;
		cargarEstudioSolicitado();
				
		current_y = current_y + MainFrame.alto_controles * 2;
		/*
		btFinalizar = new JButton("Finalizar");
		btFinalizar.addActionListener(this);
		btFinalizar.setBounds(MainFrame.APP_WINDOW_X / 2 - ( 150 / 2) + 100 ,current_y, 200, MainFrame.alto_controles);
		add(btFinalizar);
		*/
		btFinalizar = new JButton(MainFrame.BOTON_FINALIZAR_TEXTO);
		btFinalizar.addActionListener(this);
		btFinalizar.setForeground(MainFrame.BOTON_FINALIZAR_COLOR);
		btFinalizar.setBounds(MainFrame.BOTON_FINALIZAR_X , MainFrame.BOTON_FINALIZAR_Y, MainFrame.BOTONES_ANCHO , MainFrame.alto_controles);
		add(btFinalizar);
		
		estadoCarga = true;

		this.setVisible(true);
		
	}
	
	
	private void cargarDatosPaciente() {
		
		//Nombre
		lbl_nombre = new JLabel("Nombre:");
		lbl_nombre.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_nombre); //, gridBagConstraints);
		tfd_nombre = new JTextField (MainFrame.consulta.paciente.nombre);
		tfd_nombre.setEditable(false);
		tfd_nombre.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfd_nombre); //, gridBagConstraints);
		
		current_y = current_y + MainFrame.alto_controles ;
		
		//Apellido
		current_y = current_y + MainFrame.alto_controles;
		
		lbl_apellido = new JLabel("Apellido:");
		lbl_apellido.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_apellido); //, gridBagConstraints);
		tfd_apellido = new JTextField (MainFrame.consulta.paciente.apellido);
		tfd_apellido.setEditable(false);
		tfd_apellido.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfd_apellido); //, gridBagConstraints);
		
	}
	
	private void cargarDolorLumbar() {
		
		String strDolorLumbar ="";
		
		for (int i = 0; i < cantDolorlumbar ; i++) {
			if ( MainFrame.consulta.diagnostico.dolor_lumbar.compareTo(strDolorlumbarBE[i]) == 0) {
				strDolorLumbar = strDolorlumbarFE[i];
			}
		}
		
		current_y = current_y + MainFrame.alto_controles;
		//Dolor Lumbar
		lbl_dolorlumbar = new JLabel("Tipo de dolor lumbar:");
		lbl_dolorlumbar.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_dolorlumbar); //, gridBagConstraints);
		tfd_dolorlumbar = new JTextField (strDolorLumbar);
		tfd_dolorlumbar.setEditable(false);
		tfd_dolorlumbar.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfd_dolorlumbar); //, gridBagConstraints);

	}
	
	private void cargarGradoSospecha() {
		
		String strGradoSospecha ="";
		
		for (int i = 0; i < cantGradoSospecha; i++) {
			if ( MainFrame.consulta.diagnostico.grado_sospecha.compareTo(strGradoSospechaBE[i]) == 0) {
				strGradoSospecha = strGradoSospechaFE[i];
			}
		}
		
		current_y = current_y + MainFrame.alto_controles;
		//Grado Sospecha
		lbl_gradosospecha = new JLabel("Grado de sospecha:");
		lbl_gradosospecha.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_gradosospecha); //, gridBagConstraints);
		tfd_gradosospecha = new JTextField (strGradoSospecha);
		tfd_gradosospecha.setEditable(false);
		tfd_gradosospecha.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfd_gradosospecha); //, gridBagConstraints);

	}
	
	private void cargarEnfermedad() {
		
		String strEnfermedad ="";
		
		for (int i = 0; i < cantEnfermedad; i++) {
			if ( MainFrame.consulta.diagnostico.enfermedad.compareTo(strEnfermedadBE[i]) == 0) {
				strEnfermedad = strEnfermedadFE[i];
			}
		}
		
		current_y = current_y + MainFrame.alto_controles;
		//Grado Sospecha
		lbl_enfermedad = new JLabel("Enfermedad:");
		lbl_enfermedad.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_enfermedad); //, gridBagConstraints);
		tfd_enfermedad = new JTextField (strEnfermedad);
		tfd_enfermedad.setEditable(false);
		tfd_enfermedad.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfd_enfermedad); //, gridBagConstraints);

	}
	
	private void cargarDerivacion() {
		
		String strDerivacion ="";
		
		for (int i = 0; i < cantDerivacion; i++) {
			if ( MainFrame.consulta.diagnostico.derivacion.compareTo(strDerivacionBE[i]) == 0) {
				strDerivacion = strDerivacionFE[i];
			}
		}
		
		current_y = current_y + MainFrame.alto_controles;
		//Grado Sospecha
		lbl_derivacion= new JLabel("Derivación:");
		lbl_derivacion.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_derivacion); //, gridBagConstraints);
		tfd_derivacion = new JTextField (strDerivacion);
		tfd_derivacion.setEditable(false);
		tfd_derivacion.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfd_derivacion); //, gridBagConstraints);
	}
	
	private void cargarEstudioSolicitado() {
		
		String strEstudioSolicitado ="";
		
		for (int i = 0; i < cantEstudiosSolicitados; i++) {
			if ( MainFrame.consulta.diagnostico.estudios_solicitados.compareTo(strEstudiosSolicitadosBE[i]) == 0) {
				strEstudioSolicitado = strEstudiosSolicitadosFE[i];
			}
		}
		
		current_y = current_y + MainFrame.alto_controles;
		//Grado Sospecha
		lbl_estudio_solicitado = new JLabel("Estudio Solicitado:");
		lbl_estudio_solicitado.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_estudio_solicitado); //, gridBagConstraints);
		tfd_estudio_solicitado = new JTextField (strEstudioSolicitado);
		tfd_estudio_solicitado.setEditable(false);
		tfd_estudio_solicitado.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfd_estudio_solicitado); //, gridBagConstraints);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		  if (estadoCarga) {
			  if ( e.getSource() == btFinalizar) {
				  btFinalizar();
			  }
		  }
		
	}
	
	
	private void btFinalizar(){
		
					
			GuardarResultados();
		
			String strMsg = "El diagnóstico se ha guardado exitosamente.\n¿Desea realizar otro diagóstico?\nSi elige 'No' la aplicación se cerrará.";
			int jOptionResult = jOptionPane.showOptionDialog(frame, strMsg, "Consulta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,null );

			if ( jOptionResult == JOptionPane.YES_OPTION) {
		
				frame.ReiniciarDiagnostico();
						
			}else {
				System.exit(1);
			}
	}
	
	private void GuardarResultados() {
		
		
		DBHelper dbHelper = new DBHelper();
		
		dbHelper.deleteAntecedentes(id_paciente_actual);
		
		if (MainFrame.consulta.antecedentes_paciente != null ) {
			if (MainFrame.consulta.antecedentes_paciente.size() > 0 ) {
				for ( AntecedentesPaciente ap : MainFrame.consulta.antecedentes_paciente) {
					dbHelper.InsertAntecedentePaciente(ap);
				}
			}
		}
		
		if (MainFrame.consulta.antecedentes_familiares != null ) {
			if (MainFrame.consulta.antecedentes_familiares.size() > 0 ) {
				for ( AntecedentesFamiliares af : MainFrame.consulta.antecedentes_familiares) {
					dbHelper.InsertAntecedenteFamiliares(af);
				}
			}
		}
		
		dbHelper.deleteEstudios(id_paciente_actual);
		
		if (MainFrame.consulta.estudio_gen != null) {
			
			Estudio estudio = dbHelper.getEstudioGen(id_paciente_actual);
			
			if (estudio == null) {
				dbHelper.InsertEstudio(MainFrame.consulta.estudio_gen);
			}
		}
		
		dbHelper.InsertDiagnostico(MainFrame.consulta.diagnostico);
		
	}
	
	

	@Override
	public void windowActivated(WindowEvent e) {
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
		String strMsg = "¿Está seguro que desea salir?";
		int jOptionResult = jOptionPane.showOptionDialog(frame, strMsg, "Consulta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,null );

		if ( jOptionResult == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
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
