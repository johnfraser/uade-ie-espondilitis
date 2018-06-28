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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import data.DBHelper;
import model.Diagnostico;
import model.Dolor;
import model.Estudio;
import model.Laboratorio;

public class DatosEstudios extends JPanel implements WindowListener, ActionListener{
	
	public static String[] strRadios = { "No", "Si" };
	public static int cantRadios = 2;
	public static String[] strResultadoImagenes = { "Normal", "Sacroilitis" };
	public static int cantResultadoImagenes = 2;
	public static String[] strResultadoGen = { "Positivo", "Negativo" };
	public static int cantResultadoGen = 2;
	
	protected MainFrame frame;
	
	public static int id_paciente_actual;
	public static int id_estudio_gen_actual;
	
	protected static JOptionPane jOptionPane;
	
	public JLabel lbl_titulo;
	
	public JLabel lbl_laboratorio;
	public JLabel lbl_laboratorio_ERS;
	public JLabel lbl_laboratorio_ERSu;
	public JLabel lbl_laboratorio_PCR;
	public JLabel lbl_laboratorio_PCRu;
	
	public JLabel lbl_estudio_gen;
	public JLabel lbl_estudio_gen_nombre;

	public JLabel lbl_estudio_rx;
	public JLabel lbl_estudio_rx_nombre;
	
	public JLabel lbl_estudio_rmn;
	public JLabel lbl_estudio_rmn_nombre;
	
	public JRadioButton rdb_laboratorio[];
	public JRadioButton rdb_estudio_gen[];
	
	public JRadioButton rdb_estudio_gen_valor[];
	public JRadioButton rdb_estudio_rx[];
	
	public JRadioButton rdb_estudio_rx_valor[];
	
	public JRadioButton rdb_estudio_rmn[];
	
	public JRadioButton rdb_estudio_rmn_valor[];
	
	public JTextField tfl_laboratorio_ERS;
	public JTextField tfl_laboratorio_PCR;
	
	public JComboBox<String> cmb_estudio_gen_valor;
	public JComboBox<String> cmb_estudio_rx;
	public JComboBox<String> cmb_estudio_rmn;
	
	
	protected JButton btProcesar;
	protected JButton btAtras;
	
	public int current_x = 0;
	public int current_y = 0;
	
	public boolean estadoCarga = false;
	
	public boolean tieneLaboratorio = false;
	public boolean tieneGen = false;
	public boolean tieneRX = false;
	public boolean tieneRMN = false;
	
	public boolean mostrarEstudioGenRealizado = false; 
	
	private DBHelper dbHelper;
	
	public DatosEstudios (MainFrame prevframe) {
		
		frame = prevframe;
		
		id_paciente_actual = MainFrame.consulta.paciente.id_paciente;
		id_estudio_gen_actual = 0;
		
		jOptionPane = new JOptionPane();
		
		dbHelper = new DBHelper();
		
		setLayout(null);
		
		current_x = 10;
		current_y = 10;
		
		//Titulo
		lbl_titulo = new JLabel("Por último, complete la información sobre los estudios del paciente:");
		lbl_titulo.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X - MainFrame.padding_controles,MainFrame.alto_controles);
		add(lbl_titulo); 
		
		current_y = current_y + MainFrame.alto_controles * 2;
		
		cargarLaboratorio();
		cargarEstudioGEN();
		cargarEstudioRX();
		cargarEstudioRMN();
		
	    current_y = current_y + MainFrame.alto_controles * 2;
	    
		btAtras = new JButton("Atrás");
		btAtras.addActionListener(this);
		btAtras.setBounds(MainFrame.APP_WINDOW_X / 2 - ( 150 / 2) - 150 ,current_y, 200 , MainFrame.alto_controles);
		add(btAtras);
		
		btProcesar = new JButton("Procesar");
		btProcesar.addActionListener(this);
		btProcesar.setBounds(MainFrame.APP_WINDOW_X / 2 - ( 150 / 2) + 100 ,current_y, 200, MainFrame.alto_controles);
		add(btProcesar);
		
		cambiarEstadoLaboratorio(false);
		cambiarEstadoEstudioGEN(mostrarEstudioGenRealizado);
		cambiarEstadoEstudioRX(false);
		cambiarEstadoEstudioRMN(false);

		estadoCarga = true;
		
		this.setVisible(true);
		
		if ( mostrarEstudioGenRealizado ) {
			
			String strMensaje="El paciente ya se ha realizado el estudio del gen HLAB27.";
			jOptionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
			jOptionPane.showMessageDialog(frame, strMensaje, "Datos existentes", JOptionPane.INFORMATION_MESSAGE );

		}
		
	}
	
	private void cargarLaboratorio() {
		
		//Laboratorio
		current_y = current_y + MainFrame.alto_controles * 2;
		lbl_laboratorio = new JLabel("¿El paciente se hizo analisis ERS y PCR?");
		lbl_laboratorio.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_laboratorio);
		
		rdb_laboratorio  = new JRadioButton[cantRadios];
		
		rdb_laboratorio[0] = new JRadioButton(strRadios[0]);
		rdb_laboratorio[0].setSelected(true);
		rdb_laboratorio[0].setBounds(MainFrame.APP_WINDOW_X / 2,current_y, 150 - MainFrame.padding_controles, MainFrame.alto_controles);
		rdb_laboratorio[0].addActionListener(this);
		add(rdb_laboratorio[0]);
		
		rdb_laboratorio[1] = new JRadioButton(strRadios[1]);
		rdb_laboratorio[1].setBounds(MainFrame.APP_WINDOW_X / 2 + 150 , current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		rdb_laboratorio[1].addActionListener(this);
		add(rdb_laboratorio[1]);
		
	    ButtonGroup btgLaboratorio = new ButtonGroup();
	    btgLaboratorio.add(rdb_laboratorio[0]);
	    btgLaboratorio.add(rdb_laboratorio[1]);
	    
		current_y = current_y + MainFrame.alto_controles;
		lbl_laboratorio_ERS = new JLabel("Valor ERS (en mm): ");
		lbl_laboratorio_ERS.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_laboratorio_ERS); 	
		tfl_laboratorio_ERS = new JTextField ();
		tfl_laboratorio_ERS.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfl_laboratorio_ERS); //, gridBagConstraints);
		
		current_y = current_y + MainFrame.alto_controles;
		lbl_laboratorio_PCR = new JLabel("Valor PCR (en mg/dl): ");
		lbl_laboratorio_PCR.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_laboratorio_PCR); 	
		tfl_laboratorio_PCR = new JTextField ();
		tfl_laboratorio_PCR.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfl_laboratorio_PCR);
	}
	
	private void cargarEstudioGEN() {

		//Estudio GEN
		
		int idResultado = 0;
		
		mostrarEstudioGenRealizado = false;
		
		Estudio estudio = dbHelper.getEstudioGen(id_paciente_actual);
		
		if (estudio != null) {
		
			id_estudio_gen_actual = estudio.id_estudio;
			
			for (int i = 0; i < cantResultadoGen ; i++) {
				if ( estudio.resultado.compareTo(strResultadoGen[i]) == 0) {
					idResultado = i;
				}
			}
			
			mostrarEstudioGenRealizado = true;
				
		}
		
				
		current_y = current_y + MainFrame.alto_controles * 2;
		lbl_estudio_gen = new JLabel("¿El paciente se hizo analisis genético? (HLAB27)");
		lbl_estudio_gen.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_estudio_gen);
		
		rdb_estudio_gen  = new JRadioButton[cantRadios];
		
		rdb_estudio_gen[0] = new JRadioButton(strRadios[0]);
		if (estudio == null) {
			rdb_estudio_gen[0].setSelected(true);
		}
		rdb_estudio_gen[0].addActionListener(this);
		rdb_estudio_gen[0].setBounds(MainFrame.APP_WINDOW_X / 2,current_y, 150 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(rdb_estudio_gen[0]);
		
		rdb_estudio_gen[1] = new JRadioButton(strRadios[1]);
		if (estudio != null) {
			rdb_estudio_gen[1].setSelected(true);
		}
		rdb_estudio_gen[1].addActionListener(this);
		rdb_estudio_gen[1].setBounds(MainFrame.APP_WINDOW_X / 2 + 150 , current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(rdb_estudio_gen[1]);
		
	    ButtonGroup btgEstudioGen = new ButtonGroup();
	    btgEstudioGen.add(rdb_estudio_gen[0]);
	    btgEstudioGen.add(rdb_estudio_gen[1]);
	    
		current_y = current_y + MainFrame.alto_controles;
		lbl_estudio_gen_nombre = new JLabel("           Resultado: ");
		lbl_estudio_gen_nombre.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_estudio_gen_nombre);
	    
		cmb_estudio_gen_valor = new JComboBox<String>();
		cmb_estudio_gen_valor.setEditable(false);
		cmb_estudio_gen_valor.addActionListener(this);
		DefaultComboBoxModel defaultComboBoxModel_cmb_estudio_gen_valor = new DefaultComboBoxModel();
		cmb_estudio_gen_valor.setModel(defaultComboBoxModel_cmb_estudio_gen_valor);
		
		for (int i = 0 ; i < cantResultadoGen ; i ++){
			defaultComboBoxModel_cmb_estudio_gen_valor.addElement(strResultadoGen[i]);
		}
		
		cmb_estudio_gen_valor.setSelectedIndex(idResultado);
		if (mostrarEstudioGenRealizado) {
			cmb_estudio_gen_valor.setEnabled(true);
		}
		cmb_estudio_gen_valor.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
		add(cmb_estudio_gen_valor);
	    
	}
	
	private void cargarEstudioRX(){
		
		//Estudio RX
		current_y = current_y + MainFrame.alto_controles * 2;
		lbl_estudio_rx = new JLabel("¿El paciente se hizo una radiografía de columna?");
		lbl_estudio_rx.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_estudio_rx);
		
		rdb_estudio_rx = new JRadioButton[cantRadios];
		
		rdb_estudio_rx[0] = new JRadioButton(strRadios[0]);
		rdb_estudio_rx[0].setSelected(true);
		rdb_estudio_rx[0].addActionListener(this);
		rdb_estudio_rx[0].setBounds(MainFrame.APP_WINDOW_X / 2,current_y, 150 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(rdb_estudio_rx[0]);
		
		rdb_estudio_rx[1] = new JRadioButton(strRadios[1]);
		rdb_estudio_rx[1].addActionListener(this);
		rdb_estudio_rx[1].setBounds(MainFrame.APP_WINDOW_X / 2 + 150 , current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(rdb_estudio_rx[1]);
		
	    ButtonGroup btgEstudioRX = new ButtonGroup();
	    btgEstudioRX.add(rdb_estudio_rx[0]);
	    btgEstudioRX.add(rdb_estudio_rx[1]);
	    
		current_y = current_y + MainFrame.alto_controles;
		lbl_estudio_rx_nombre = new JLabel("           Resultado: ");
		lbl_estudio_rx_nombre.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_estudio_rx_nombre);
	    
		cmb_estudio_rx = new JComboBox<String>();
		cmb_estudio_rx.setEditable(false);
		cmb_estudio_rx.addActionListener(this);
		DefaultComboBoxModel defaultComboBoxModel_cmb_estudio_rx = new DefaultComboBoxModel();
		cmb_estudio_rx.setModel(defaultComboBoxModel_cmb_estudio_rx);
		
		for (int i = 0 ; i < cantResultadoImagenes ; i ++){
			defaultComboBoxModel_cmb_estudio_rx.addElement(strResultadoImagenes[i]);
		}
		
		cmb_estudio_rx.setSelectedItem(0);
		cmb_estudio_rx.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
		add(cmb_estudio_rx);
		
	}
	
	public void cargarEstudioRMN() {
		
		//Estudio RX
		current_y = current_y + MainFrame.alto_controles * 2;
		lbl_estudio_rmn = new JLabel("¿El paciente se hizo una resonancia magnética de columna?");
		lbl_estudio_rmn.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_estudio_rmn);
		
		rdb_estudio_rmn = new JRadioButton[cantRadios];
		
		rdb_estudio_rmn[0] = new JRadioButton(strRadios[0]);
		rdb_estudio_rmn[0].setSelected(true);
		rdb_estudio_rmn[0].addActionListener(this);
		rdb_estudio_rmn[0].setBounds(MainFrame.APP_WINDOW_X / 2,current_y, 150 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(rdb_estudio_rmn[0]);
		
		rdb_estudio_rmn[1] = new JRadioButton(strRadios[1]);
		rdb_estudio_rmn[1].addActionListener(this);
		rdb_estudio_rmn[1].setBounds(MainFrame.APP_WINDOW_X / 2 + 150 , current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(rdb_estudio_rmn[1]);
		
	    ButtonGroup btgEstudioRMN = new ButtonGroup();
	    btgEstudioRMN.add(rdb_estudio_rmn[0]);
	    btgEstudioRMN.add(rdb_estudio_rmn[1]);
	    
		current_y = current_y + MainFrame.alto_controles;
		lbl_estudio_rmn_nombre = new JLabel("           Resultado: ");
		lbl_estudio_rmn_nombre.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_estudio_rmn_nombre);
	    
		cmb_estudio_rmn = new JComboBox<String>();
		cmb_estudio_rmn.setEditable(false);
		cmb_estudio_rmn.addActionListener(this);
		DefaultComboBoxModel defaultComboBoxModel_cmb_estudio_rmn = new DefaultComboBoxModel();
		cmb_estudio_rmn.setModel(defaultComboBoxModel_cmb_estudio_rmn);
		
		for (int i = 0 ; i < cantResultadoImagenes ; i ++){
			defaultComboBoxModel_cmb_estudio_rmn.addElement(strResultadoImagenes[i]);
		}
		
		cmb_estudio_rmn.setSelectedItem(0);
		cmb_estudio_rmn.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
		add(cmb_estudio_rmn);
	}

	public boolean validar() {
		
		boolean resultado=true;
		
		if ( tieneLaboratorio ) {
			
			if (tfl_laboratorio_ERS.getText().isEmpty()) {
				String strError="Falta ingresar valor de ERS";
				jOptionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
				jOptionPane.showMessageDialog(frame, strError, "Error", JOptionPane.ERROR_MESSAGE );
				resultado=false;
			}else {
				if (!MainFrame.validarTextoNumerico(tfl_laboratorio_ERS.getText())) {
					String strError="Valor de ERS inválido";
					jOptionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
					jOptionPane.showMessageDialog(frame, strError, "Error", JOptionPane.ERROR_MESSAGE );
					resultado=false;
				}
			}
		}
		
		return resultado;
		
	}
	
	public void GuardarEstudios() {

		float ERS;
		float PCR;
		String gen;
		String rx;
		String rmn;
		Object selectedItem;
		


		ERS = 0f;
		PCR = 0f;
		if (tieneLaboratorio) {
			ERS = Float.valueOf(tfl_laboratorio_ERS.getText());
			PCR = Float.valueOf(tfl_laboratorio_PCR.getText());

			int id_laboratorio = dbHelper.getUltimoId(DBHelper.TABLE_Laboratorio_NAME, DBHelper.TABLE_Laboratorio_id_laboratorio) + 1;

			MainFrame.consulta.laboratorio = new Laboratorio(id_laboratorio, id_paciente_actual, ERS, PCR);

		}



		gen = "";
		if (tieneGen) {

			selectedItem = cmb_estudio_gen_valor.getSelectedItem();

			if (selectedItem.toString().equals("Negativo")) {
				gen = Estudio.RESULTADO_NEGATIVO;
			}
			if (selectedItem.toString().equals("Positivo")) {
				gen = Estudio.RESULTADO_POSITIVO;
			}

			int id_estudio=0;
			
			if (mostrarEstudioGenRealizado) {
				
				id_estudio = id_estudio_gen_actual;
			}else {
				id_estudio = dbHelper.getUltimoId(DBHelper.TABLE_Estudios_NAME , DBHelper.TABLE_Estudios_id_estudios ) + 1;
			}

			MainFrame.consulta.estudio_gen = new Estudio(id_paciente_actual, id_estudio, Estudio.ESTUDIO_GEN, Estudio.ESTADO_REALIZADO, gen); 
		}

		rx = "";
		if (tieneRX) {

			selectedItem = cmb_estudio_rx.getSelectedItem();

			if (selectedItem.toString().equals("Normal")) {
				rx = Estudio.RESULTADO_NORMAL;
			}
			if (selectedItem.toString().equals("Sacroilitis")) {
				rx = Estudio.RESULTADO_SACROILITIS;
			}

			int id_estudio = dbHelper.getUltimoId(DBHelper.TABLE_Estudios_NAME , DBHelper.TABLE_Estudios_id_estudios ) + 1;

			MainFrame.consulta.estudio_rx = new Estudio(id_paciente_actual, id_estudio, Estudio.ESTUDIO_RX, Estudio.ESTADO_REALIZADO, rx); 

		}

		rmn = "";
		if (tieneRMN) {

			selectedItem = cmb_estudio_rmn.getSelectedItem();

			if (selectedItem.toString().equals("Normal")) {
				rmn = Estudio.RESULTADO_NORMAL;
			}
			if (selectedItem.toString().equals("Sacroilitis")) {
				rmn = Estudio.RESULTADO_SACROILITIS;
			}

			int id_estudio = dbHelper.getUltimoId(DBHelper.TABLE_Estudios_NAME , DBHelper.TABLE_Estudios_id_estudios ) + 1;

			MainFrame.consulta.estudio_rmn = new Estudio(id_paciente_actual, id_estudio, Estudio.ESTUDIO_RMN, Estudio.ESTADO_REALIZADO, rmn); 

		}

	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		  if (estadoCarga) {

			  if ( e.getSource() == btProcesar) {
				  btProcesar();
			  }

			  if ( e.getSource() == btAtras) {
				  btAtras();
			  }
			  
			  if (e.getSource() == rdb_laboratorio[0]) {
				  
				  cambiarEstadoLaboratorio(false);
				  tieneLaboratorio = false;
				  
			  }
			  if (e.getSource() == rdb_laboratorio[1]) {
				 
				  cambiarEstadoLaboratorio(true);
				  tieneLaboratorio = true;
			  }
			  
			  if (e.getSource() == rdb_estudio_gen[0]) {
				  
				  cambiarEstadoEstudioGEN(false);
				  tieneGen = false;
				  
			  }
			  if (e.getSource() == rdb_estudio_gen[1]) {
				 
				  cambiarEstadoEstudioGEN(true);
				  tieneGen = true;
			  }
			  
			  if (e.getSource() == rdb_estudio_rx[0]) {
				  
				  cambiarEstadoEstudioRX(false);
				  tieneRX = false;
				  
			  }
			  if (e.getSource() == rdb_estudio_rx[1]) {
				 
				  cambiarEstadoEstudioRX(true);
				  tieneRX = true;
			  }
			  
			  if (e.getSource() == rdb_estudio_rmn[0]) {
				  
				  cambiarEstadoEstudioRMN(false);
				  tieneRMN = false;
				  
			  }
			  if (e.getSource() == rdb_estudio_rmn[1]) {
				 
				  cambiarEstadoEstudioRMN(true);
				  tieneRMN = false;
			  }
			  
		  }
	}
	
	private void btProcesar() {
		
		if ( validar() ) {
			String strMsg = "Se procesará el diagnóstico. ¿Desea continuar?";
			int jOptionResult = jOptionPane.showOptionDialog(frame, strMsg, "Atención", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,null );

			if ( jOptionResult == JOptionPane.YES_OPTION) {		

				GuardarEstudios();

				ProcesarCLIPS();

				//ResultadoDiagnostico resultadoDiagnostico = new ResultadoDiagnostico(frame);
				frame.MostrarResultadoDiagnostico(this);

			}
		}
	}

	private void btAtras() {
		
		//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		frame.MostrarDatosAntecedentes(this,true);
	
	}
	
	public void cambiarEstadoLaboratorio(boolean estado) {
		
		tfl_laboratorio_ERS.setEnabled(estado);
		tfl_laboratorio_PCR.setEnabled(estado);
		
	}
	
	public void cambiarEstadoEstudioGEN(boolean estado) {
		
		cmb_estudio_gen_valor.setEnabled(estado);
		
	}
	
	public void cambiarEstadoEstudioRX(boolean estado) {
		
		cmb_estudio_rx.setEnabled(estado);
		
	}
	
	public void cambiarEstadoEstudioRMN(boolean estado) {
		
		cmb_estudio_rmn.setEnabled(estado);
		
	}
	
	

	
	public void ProcesarCLIPS() {
		
		
		MainFrame.consulta.diagnostico.dolor_lumbar = Diagnostico.DOLOR_LUMBAR_MECANICO;
		MainFrame.consulta.diagnostico.grado_sospecha = Diagnostico.GRADO_SOSPECHA_AltaProbSpax;
		MainFrame.consulta.diagnostico.enfermedad = Diagnostico.ENFERMEDAD_EspondilitisAnquilosante;
		MainFrame.consulta.diagnostico.derivacion = Diagnostico.DERIVACION_Gastroenterologo;
		MainFrame.consulta.diagnostico.estudios_solicitados = Diagnostico.ESTUDIOS_SOLICITADOS_HLAB27;
		
	}
	
	
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
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
