package front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.SwingConstants;

import clips.ClipsHandler;
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
	public JLabel lbl_estudio_gen_l2;
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
	
	public JLabel lbl_estudio_gen_anterior;
		
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
		
		current_y = current_y + MainFrame.alto_controles ;
		
		//Titulo
		lbl_titulo = new JLabel("Por �ltimo, complete la siguiente informaci�n sobre los estudios del paciente");
		//lbl_titulo.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X - MainFrame.padding_controles,MainFrame.alto_controles);
		Font font = lbl_titulo.getFont();
		lbl_titulo.setFont(new Font(font.getFontName(),Font.BOLD,font.getSize() + 4));
		int w = lbl_titulo.getFontMetrics(lbl_titulo.getFont()).stringWidth(lbl_titulo.getText());
		lbl_titulo.setBounds(MainFrame.APP_WINDOW_X/2 - w/2,current_y,w,MainFrame.alto_controles);
		add(lbl_titulo); 
		
		current_y = current_y + MainFrame.alto_controles * 2;
		
		lbl_estudio_gen_anterior = new JLabel("*El paciente ya se ha realizado el estudio del gen HLAB27 con anterioridad*");
    	w = lbl_estudio_gen_anterior.getFontMetrics(lbl_estudio_gen_anterior.getFont()).stringWidth(lbl_estudio_gen_anterior.getText());
    	lbl_estudio_gen_anterior.setBounds(MainFrame.APP_WINDOW_X/2 - w/2,current_y,w,MainFrame.alto_controles);
    	//lbl_antecedentes_anteriores.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X - MainFrame.padding_controles, MainFrame.alto_controles);
    	lbl_estudio_gen_anterior.setForeground(Color.blue);
    	lbl_estudio_gen_anterior.setVisible(false);
    	add(lbl_estudio_gen_anterior);
    	
		cargarLaboratorio();
		cargarEstudioGEN();
		cargarEstudioRX();
		cargarEstudioRMN();
		
	    current_y = current_y + MainFrame.alto_controles * 2;
	    
	    /*
		btAtras = new JButton("Atr�s");
		btAtras.addActionListener(this);
		btAtras.setBounds(MainFrame.APP_WINDOW_X / 2 - ( 150 / 2) - 150 ,current_y, 200 , MainFrame.alto_controles);
		add(btAtras);
		
		btProcesar = new JButton("Procesar");
		btProcesar.addActionListener(this);
		btProcesar.setBounds(MainFrame.APP_WINDOW_X / 2 - ( 150 / 2) + 100 ,current_y, 200, MainFrame.alto_controles);
		add(btProcesar);
		*/
	    
		btAtras = new JButton(MainFrame.BOTON_ATRAS_TEXTO);
		btAtras.addActionListener(this);
		btAtras.setBounds(MainFrame.BOTON_ATRAS_X ,MainFrame.BOTON_ATRAS_Y, MainFrame.BOTONES_ANCHO , MainFrame.alto_controles);
		add(btAtras);
		
		btProcesar = new JButton(MainFrame.BOTON_PROCESAR_TEXTO);
		btProcesar.addActionListener(this);
		btProcesar.setForeground(MainFrame.BOTON_PROCESAR_COLOR);
		btProcesar.setBounds(MainFrame.BOTON_PROCESAR_X , MainFrame.BOTON_PROCESAR_Y, MainFrame.BOTONES_ANCHO , MainFrame.alto_controles);
		add(btProcesar);
		
		
		
		
		cambiarEstadoLaboratorio(false);
		cambiarEstadoEstudioGEN(mostrarEstudioGenRealizado);
		cambiarEstadoEstudioRX(false);
		cambiarEstadoEstudioRMN(false);

		estadoCarga = true;
		
		this.setVisible(true);
		
		if ( mostrarEstudioGenRealizado ) {
		/*	
			String strMensaje="El paciente ya se ha realizado el estudio del gen HLAB27.";
			jOptionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
			jOptionPane.showMessageDialog(frame, strMensaje, "Datos existentes", JOptionPane.INFORMATION_MESSAGE );
			*/
			lbl_estudio_gen_anterior.setVisible(true);
		}
		
	}
	
	private void cargarLaboratorio() {
		
		//Laboratorio
		current_y = current_y + MainFrame.alto_controles * 2;
		lbl_laboratorio = new JLabel("�Se hizo analisis ERS y PCR?");
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
		lbl_laboratorio_ERS = new JLabel("Valor ERS (en mm): ",SwingConstants.RIGHT);
		lbl_laboratorio_ERS.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_laboratorio_ERS); 	
		tfl_laboratorio_ERS = new JTextField ();
		tfl_laboratorio_ERS.setHorizontalAlignment(SwingConstants.RIGHT);
		tfl_laboratorio_ERS.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 5 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfl_laboratorio_ERS); //, gridBagConstraints);
		
		current_y = current_y + MainFrame.alto_controles;
		lbl_laboratorio_PCR = new JLabel("Valor PCR (en mg/dl): ",SwingConstants.RIGHT);
		lbl_laboratorio_PCR.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(lbl_laboratorio_PCR); 	
		tfl_laboratorio_PCR = new JTextField ();
		tfl_laboratorio_PCR.setHorizontalAlignment(SwingConstants.RIGHT);
		tfl_laboratorio_PCR.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 5 - MainFrame.padding_controles, MainFrame.alto_controles);
		add(tfl_laboratorio_PCR);
	}
	
	private void cargarEstudioGEN() {

		//Estudio GEN
		
		int idResultado = 0;
		
		mostrarEstudioGenRealizado = false;
		
		Estudio estudio = dbHelper.getEstudioGen(id_paciente_actual);
		
		if (estudio != null) {
		
			tieneGen = true;
			
			id_estudio_gen_actual = estudio.id_estudio;
			
			for (int i = 0; i < cantResultadoGen ; i++) {
				if ( estudio.resultado.compareTo(strResultadoGen[i]) == 0) {
					idResultado = i;
				}
			}
			
			mostrarEstudioGenRealizado = true;
				
		}
		
				
		current_y = current_y + MainFrame.alto_controles * 2;
		lbl_estudio_gen = new JLabel("�Se hizo analisis gen�tico? (HLAB27)");
		lbl_estudio_gen.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2- MainFrame.padding_controles, MainFrame.alto_controles);
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
		lbl_estudio_gen_nombre = new JLabel("Resultado: ",SwingConstants.RIGHT);
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
		//cmb_estudio_gen_valor.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
		cmb_estudio_gen_valor.setBounds(MainFrame.APP_WINDOW_X / 2,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles,MainFrame.alto_controles);
		add(cmb_estudio_gen_valor);
	    
	}
	
	private void cargarEstudioRX(){
		
		//Estudio RX
		current_y = current_y + MainFrame.alto_controles * 2;
		lbl_estudio_rx = new JLabel("�Se hizo una radiograf�a de columna?");
		lbl_estudio_rx.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2  - MainFrame.padding_controles, MainFrame.alto_controles);
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
		lbl_estudio_rx_nombre = new JLabel("Resultado: ",SwingConstants.RIGHT);
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
		lbl_estudio_rmn = new JLabel("�Se hizo una resonancia magn�tica de columna?");
		lbl_estudio_rmn.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X - MainFrame.padding_controles, MainFrame.alto_controles);
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
		lbl_estudio_rmn_nombre = new JLabel("Resultado: ",SwingConstants.RIGHT);
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
				if (!MainFrame.validarTextoFloat(tfl_laboratorio_ERS.getText())) {
					String strError="Valor de ERS inv�lido";
					jOptionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
					jOptionPane.showMessageDialog(frame, strError, "Error", JOptionPane.ERROR_MESSAGE );
					resultado=false;
				}
			}
			
			if (resultado) {
				if (tfl_laboratorio_PCR.getText().isEmpty()) {
					String strError="Falta ingresar valor de PCR";
					jOptionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
					jOptionPane.showMessageDialog(frame, strError, "Error", JOptionPane.ERROR_MESSAGE );
					resultado=false;
				}else {
					if (!MainFrame.validarTextoFloat(tfl_laboratorio_PCR.getText())) {
						String strError="Valor de PCR inv�lido";
						jOptionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
						jOptionPane.showMessageDialog(frame, strError, "Error", JOptionPane.ERROR_MESSAGE );
						resultado=false;
					}
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
			String strMsg = "Se procesar� el diagn�stico. �Desea continuar?";
			int jOptionResult = jOptionPane.showOptionDialog(frame, strMsg, "Atenci�n", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,null );

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
	
	

	
	public boolean ProcesarCLIPS() {
		ClipsHandler clips = new ClipsHandler();
		
		Diagnostico diagnostico = clips.correrConsulta(MainFrame.consulta);
		
		if(diagnostico != null) {
			MainFrame.consulta.diagnostico = diagnostico;
			return true;
		} else {
			// welp, show error message
		}
		
		
		MainFrame.consulta.diagnostico.dolor_lumbar = diagnostico.dolor_lumbar;
		MainFrame.consulta.diagnostico.grado_sospecha = diagnostico.grado_sospecha;
		MainFrame.consulta.diagnostico.enfermedad = diagnostico.enfermedad;
		MainFrame.consulta.diagnostico.derivacion = diagnostico.derivacion;
		MainFrame.consulta.diagnostico.estudios_solicitados = diagnostico.estudios_solicitados;
		return false;
				
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
		String strMsg = "�Est� seguro que desea salir?";
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
