package front;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import data.DBHelper;
import model.AntecedentesFamiliares;
import model.AntecedentesPaciente;


public class DatosAntecedentes  extends JPanel implements WindowListener, ActionListener{
	
	public static String[] strAntecedentesPaciente= { "Colitis",
			"Dactilitis" ,
			"Entesitis" ,
			"Uveitis",
			"InfeccionGastrointestinal",
			"InfeccionUrogenital",
			"Psoriasis",
			"EnfermedadCrohn",
			"ColitisUlcerosa" };
	
	public static String[] strAntecedentesPacienteCheckBox= { "Colitis",
			"Dactilítis" ,
			"Entesítis" ,
			"Uveítis",
			"Infección Gastrointestinal",
			"Infección Urogenital",
			"Psoriasis",
			"Enfermedad de Crohn",
			"Colitis Ulcerosa" };
	
	public static int cantAntecedentesPaciente = 9;
	
	public static String[] strAntecedentesFamiliares= { "EnfermedadCrohn",
			"ColitisUlcerosa" ,
			"Uveitis" ,
			"EspondilitisAnquilosante",
			"ArtritisReactiva",
			"ArtritisPsoriasica",
			"Psoriasis",
			"EspondiloartritisIndiferenciada",
			"EspondiloartritisJuvenil",
			"ArtritisReumatoide"};
	
	public static String[] strAntecedentesFamiliaresCheckBox= { "Enfermedad de Crohn",
			"Colitis Ulcerosa" ,
			"Uveítis" ,
			"Espondilitis Anquilosante",
			"Artritis Reactiva",
			"Artritis Psoriásica",
			"Psoriasis",
			"Espondiloartritis Indiferenciada",
			"Espondiloartritis Juvenil",
			"Artritis Reumatoide"};
	
	public static int cantAntecedentesFamiliares = 10;
	
	public static final int TIPO_PACIENTE = 0;
	public static final int TIPO_FAMILIAR = 1;
	
	protected MainFrame frame;
	
	public static int id_paciente_actual;
	
	protected static JOptionPane jOptionPane;
	
	public JLabel lbl_titulo;
	
	public JLabel lbl_antecedentes_paciente;
	public JLabel lbl_antecedentes_familiares;
	
	public JCheckBox chk_antecedentes_paciente[];
	public JCheckBox chk_antecedentes_familiares[];
	
	protected JButton btSiguiente;
	protected JButton btAtras;
	
	public int current_x = 0;
	public int current_y = 0;
	
	public boolean estadoCarga = false;
	private boolean esPrimeraDolencia = true;
	
	protected DBHelper dbHelper;
	
	public DatosAntecedentes(MainFrame prevframe, boolean esPrimeraDolencia) {
		
		frame = prevframe;
		
		this.esPrimeraDolencia = esPrimeraDolencia;
		
		dbHelper = new DBHelper();
		
		jOptionPane = new JOptionPane();
		
		id_paciente_actual = MainFrame.consulta.paciente.id_paciente;
		
		setLayout(null);
		
		//createAndShowGUI();
		
		current_x = 10;
		current_y = 10;
		
		//Titulo
		lbl_titulo = new JLabel("Por favor tilde los antecedentes que correspondan:");
		lbl_titulo.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X - MainFrame.padding_controles,MainFrame.alto_controles);
		add(lbl_titulo); 
		
		current_y = current_y + MainFrame.alto_controles * 2;
		
		//Antecedentes Paciente
		current_y = current_y + MainFrame.alto_controles;
		lbl_antecedentes_paciente = new JLabel("Antecedentes del Paciente");
		lbl_antecedentes_paciente.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_antecedentes_paciente);
		
		chk_antecedentes_paciente = new JCheckBox[cantAntecedentesPaciente];
		
		for (int i = 0; i < cantAntecedentesPaciente; i++) {			
			chk_antecedentes_paciente[i] = new JCheckBox(strAntecedentesPacienteCheckBox[i]);
			chk_antecedentes_paciente[i].setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
			add(chk_antecedentes_paciente[i]);
			current_y = current_y + MainFrame.alto_controles;
		}
		
		current_y = current_y + MainFrame.alto_controles ;
		
		//Antecedentes Familiares
		current_y = current_y + MainFrame.alto_controles;
		lbl_antecedentes_familiares = new JLabel("Antecedentes Familiares");
		lbl_antecedentes_familiares.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_antecedentes_familiares);
		
		chk_antecedentes_familiares = new JCheckBox[cantAntecedentesFamiliares];
		
		for (int i = 0; i < cantAntecedentesFamiliares; i++) {			
			chk_antecedentes_familiares[i] = new JCheckBox(strAntecedentesFamiliaresCheckBox[i]);
			chk_antecedentes_familiares[i].setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio  , MainFrame.alto_controles);
			add(chk_antecedentes_familiares[i]);
			current_y = current_y + MainFrame.alto_controles;
		}

		
	    current_y = current_y + MainFrame.alto_controles * 2;
	    
		btAtras = new JButton("Atrás");
		btAtras.addActionListener(this);
		btAtras.setBounds(MainFrame.APP_WINDOW_X / 2 - ( 150 / 2) - 150 ,current_y, 200 , MainFrame.alto_controles);
		add(btAtras);
		
		btSiguiente = new JButton("Siguiente");
		btSiguiente.addActionListener(this);
		btSiguiente.setBounds(MainFrame.APP_WINDOW_X / 2 - ( 150 / 2) + 100 ,current_y, 200, MainFrame.alto_controles);
		add(btSiguiente);
		
		LimpiarCheckBoxes();
		
		estadoCarga = true;
		
		this.setVisible(true);
		
		
		
		if (ObtenerAntecedentesExistentes()) {
			String strMensaje="El paciente cuenta antecedentes cargados en el sistema.";
			jOptionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
			jOptionPane.showMessageDialog(frame, strMensaje, "Datos existentes", JOptionPane.INFORMATION_MESSAGE );
		}
		
		
	}
	
	private void LimpiarCheckBoxes() {
		
		for (int i = 0; i < cantAntecedentesPaciente ; i++ ) {
			
			chk_antecedentes_paciente[i].setSelected(false);
			
		}
		
		for (int i = 0; i < cantAntecedentesFamiliares ; i++ ) {
			
			chk_antecedentes_familiares[i].setSelected(false);
			
		}
		
	}
	
	
	private boolean ObtenerAntecedentesExistentes() {
		
		boolean resultado = false;
		
		int id_enfermedad;
	
		MainFrame.consulta.antecedentes_paciente = dbHelper.getAntecedentesPaciente(id_paciente_actual);
		MainFrame.consulta.antecedentes_familiares = dbHelper.getAntecedentesFamiliares(id_paciente_actual);
		
		if ( MainFrame.consulta.antecedentes_paciente != null ) {

			if ( MainFrame.consulta.antecedentes_paciente.size() > 0 ) {

				for ( AntecedentesPaciente ap : MainFrame.consulta.antecedentes_paciente) {

					id_enfermedad = BuscarEnfermedad(TIPO_PACIENTE, ap.enfermedad );

					if ( id_enfermedad > -1 ) {

						chk_antecedentes_paciente[id_enfermedad].setSelected(true);

					}

				}

				resultado = true;
			}
		}
	
		if ( MainFrame.consulta.antecedentes_familiares != null ) {

			if ( MainFrame.consulta.antecedentes_familiares.size() > 0 ) {

				for ( AntecedentesFamiliares af : MainFrame.consulta.antecedentes_familiares) {

					id_enfermedad = BuscarEnfermedad(TIPO_FAMILIAR, af.enfermedad );

					if ( id_enfermedad > -1 ) {

						chk_antecedentes_familiares[id_enfermedad].setSelected(true);

					}

				}


				resultado = true;

			}
		}
		
		return resultado;
		
	}

	private int BuscarEnfermedad(int tipo_antecedente, String enfermedad) {
		
		int resultado=-1;
		
		if (tipo_antecedente == TIPO_PACIENTE) {
			
			for ( int i = 0; i < cantAntecedentesPaciente ; i++ ) {
				if ( strAntecedentesPaciente[i].compareTo(enfermedad) == 0 ){
					resultado = i;
				}
			}
			
		}
		
		if (tipo_antecedente == TIPO_FAMILIAR) {
			
			for ( int i = 0; i < cantAntecedentesFamiliares ; i++ ) {
				if ( strAntecedentesFamiliares[i].compareTo(enfermedad) == 0 ){
					resultado = i;
				}
			}
			
		}

		return resultado;
	}

	private void GuardarAntecedentes() {
		
		MainFrame.consulta.antecedentes_paciente = null;
		
		int id_antecedentes = dbHelper.getUltimoId(DBHelper.TABLE_Antecedente_NAME, DBHelper.TABLE_Antecedente_id_antecedente);
		
		if (validarAntecedentesPaciente()) {
			MainFrame.consulta.antecedentes_paciente = new ArrayList<AntecedentesPaciente>();
			for (int i = 0; i < cantAntecedentesPaciente ; i++ ) {
				
				if (chk_antecedentes_paciente[i].isSelected() ) {
					
					//String condiciones[] = {String.valueOf(DBHelper.TABLE_Antecedente_id_paciente)};
					//String valores[] = {String.valueOf(MainFrame.id_paciente_actual)};
					//int cantidad_condiciones = 1;

					//int id_antecedentes_paciente = dbHelper.getUltimoId(DBHelper.TABLE_Antecedente_NAME, DBHelper.TABLE_Antecedente_id_paciente, cantidad_condiciones, condiciones, valores) + 1;

					id_antecedentes++;
					
					MainFrame.consulta.antecedentes_paciente.add( new AntecedentesPaciente(id_paciente_actual, id_antecedentes, strAntecedentesPaciente[i]) );
					//dbHelper.InsertAntecedentePaciente(MainFrame.consulta.antecedentes_paciente.get(MainFrame.consulta.antecedentes_paciente.size()-1));
					
				}
			}
		}
		
		MainFrame.consulta.antecedentes_familiares = null;
		
		if (validarAntecedentesFamiliares()) {
			MainFrame.consulta.antecedentes_familiares = new ArrayList<AntecedentesFamiliares>();
			for (int i = 0; i < cantAntecedentesFamiliares ; i++ ) {
				
				if (chk_antecedentes_familiares[i].isSelected() ) {
					
					//String condiciones[] = {String.valueOf(DBHelper.TABLE_Antecedente_id_paciente)};
					//String valores[] = {String.valueOf(MainFrame.id_paciente_actual)};
					//int cantidad_condiciones = 1;

					//int id_antecedentes_familiares = dbHelper.getUltimoId(DBHelper.TABLE_Antecedente_NAME, DBHelper.TABLE_Antecedente_id_paciente, cantidad_condiciones, condiciones, valores) + 1;
					//int id_antecedentes = dbHelper.getUltimoId(DBHelper.TABLE_Antecedente_NAME, DBHelper.TABLE_Antecedente_id_antecedente) + 1;
					
					id_antecedentes++;
					
					MainFrame.consulta.antecedentes_familiares.add( new AntecedentesFamiliares(id_paciente_actual, id_antecedentes, strAntecedentesFamiliares[i]) );
					//dbHelper.InsertAntecedenteFamiliares(MainFrame.consulta.antecedentes_familiares.get(MainFrame.consulta.antecedentes_familiares.size()-1));
					
				}				
			}
		}
		
	}
	
	private boolean validarAntecedentesPaciente() {
		
		boolean resultado=false;
		
		for (int i = 0; i < cantAntecedentesPaciente ; i++ ) {
			if (chk_antecedentes_paciente[i].isSelected() ) {
				resultado = true;
			}
		}
		
		return resultado;
	}
	
	private boolean validarAntecedentesFamiliares() {
		
		boolean resultado=false;
		
		for (int i = 0; i < cantAntecedentesFamiliares ; i++ ) {
			if (chk_antecedentes_familiares[i].isSelected() ) {
				resultado = true;
			}
		}
		
		return resultado;
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		  if (estadoCarga) {

			  if ( e.getSource() == btSiguiente) {
				  btSiguiente();
			  }

			  if ( e.getSource() == btAtras) {
				  btAtras();
			  }
		  }
	}
	
	private void btSiguiente() {
		
		  GuardarAntecedentes();
		
		  //DatosEstudios datosEstudios = new DatosEstudios(frame);
		  frame.MostrarDatosEstudios(this);
		  
	}
	
	private void btAtras() {
		
		//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		if (esPrimeraDolencia) {
			frame.MostrarDatosPrimeraDolencia(this);
		}else {
			frame.MostrarDatosSegundaDolencia(this);
		}
	
	}

	

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		//frame.setVisible(true);
		String strMsg = "¿Está seguro que desea salir?";
		int jOptionResult = jOptionPane.showOptionDialog(frame, strMsg, "Consulta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,null );

		if ( jOptionResult == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		//frame.setVisible(true);
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
