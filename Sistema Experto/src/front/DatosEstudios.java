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

public class DatosEstudios extends JPanel implements WindowListener, ActionListener{
	
	public static String[] strRadios = { "No", "Si" };
	public static int cantRadios = 2;
	public static String[] strResultadoImagenes = { "Normal", "Sacroilitis" };
	public static int cantResultadoImagenes = 2;
	public static String[] strResultadoGen = { "Positivo", "Negativo" };
	public static int cantResultadoGen = 2;
	
	
	protected static JFrame frame;
	protected JFrame prev_frame;
	
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
	
	public DatosEstudios (JFrame prevframe) {
		
		prev_frame = prevframe;
		prev_frame.setVisible(false);
		
		
		setLayout(null);
		
		createAndShowGUI();
		
		current_x = 10;
		current_y = 10;
		
		//Titulo
		lbl_titulo = new JLabel("Por último, complete la información sobre los estudios del paciente:");
		lbl_titulo.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X - SistemaDiagnostico.padding_controles,SistemaDiagnostico.alto_controles);
		add(lbl_titulo); 
		
		current_y = current_y + SistemaDiagnostico.alto_controles * 2;
		
		cargarLaboratorio();
		cargarEstudioGEN();
		cargarEstudioRX();
		cargarEstudioRMN();
		
	    current_y = current_y + SistemaDiagnostico.alto_controles * 2;
	    
		btAtras = new JButton("Atrás");
		btAtras.addActionListener(this);
		btAtras.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 - ( 150 / 2) - 150 ,current_y, 200 , SistemaDiagnostico.alto_controles);
		add(btAtras);
		
		btProcesar = new JButton("Procesar");
		btProcesar.addActionListener(this);
		btProcesar.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 - ( 150 / 2) + 100 ,current_y, 200, SistemaDiagnostico.alto_controles);
		add(btProcesar);
		
		estadoCarga = true;
		
	}
	
	private void cargarLaboratorio() {
		
		//Laboratorio
		current_y = current_y + SistemaDiagnostico.alto_controles * 2;
		lbl_laboratorio = new JLabel("¿El paciente se hizo analisis ERS y PCR?");
		lbl_laboratorio.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_laboratorio);
		
		rdb_laboratorio  = new JRadioButton[cantRadios];
		
		rdb_laboratorio[0] = new JRadioButton(strRadios[0]);
		rdb_laboratorio[0].setSelected(true);
		rdb_laboratorio[0].setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, 150 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		rdb_laboratorio[0].addActionListener(this);
		add(rdb_laboratorio[0]);
		
		rdb_laboratorio[1] = new JRadioButton(strRadios[1]);
		rdb_laboratorio[1].setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 + 150 , current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		rdb_laboratorio[1].addActionListener(this);
		add(rdb_laboratorio[1]);
		
	    ButtonGroup btgLaboratorio = new ButtonGroup();
	    btgLaboratorio.add(rdb_laboratorio[0]);
	    btgLaboratorio.add(rdb_laboratorio[1]);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
		lbl_laboratorio_ERS = new JLabel("Valor ERS (en mm): ");
		lbl_laboratorio_ERS.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_laboratorio_ERS); 	
		tfl_laboratorio_ERS = new JTextField ();
		tfl_laboratorio_ERS.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(tfl_laboratorio_ERS); //, gridBagConstraints);
		
		current_y = current_y + SistemaDiagnostico.alto_controles;
		lbl_laboratorio_PCR = new JLabel("Valor PCR (en mg/dl): ");
		lbl_laboratorio_PCR.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_laboratorio_PCR); 	
		tfl_laboratorio_PCR = new JTextField ();
		tfl_laboratorio_PCR.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(tfl_laboratorio_PCR);
	}
	
	private void cargarEstudioGEN() {
		//Estudio GEN
		current_y = current_y + SistemaDiagnostico.alto_controles * 2;
		lbl_estudio_gen = new JLabel("¿El paciente se hizo analisis genético?");
		lbl_estudio_gen.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_estudio_gen);
		
		rdb_estudio_gen  = new JRadioButton[cantRadios];
		
		rdb_estudio_gen[0] = new JRadioButton(strRadios[0]);
		rdb_estudio_gen[0].setSelected(true);
		rdb_estudio_gen[0].addActionListener(this);
		rdb_estudio_gen[0].setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, 150 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(rdb_estudio_gen[0]);
		
		rdb_estudio_gen[1] = new JRadioButton(strRadios[1]);
		rdb_estudio_gen[1].addActionListener(this);
		rdb_estudio_gen[1].setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 + 150 , current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(rdb_estudio_gen[1]);
		
	    ButtonGroup btgEstudioGen = new ButtonGroup();
	    btgEstudioGen.add(rdb_estudio_gen[0]);
	    btgEstudioGen.add(rdb_estudio_gen[1]);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
		lbl_estudio_gen_nombre = new JLabel("HLAB27");
		lbl_estudio_gen_nombre.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_estudio_gen_nombre);
	    
		cmb_estudio_gen_valor = new JComboBox<String>();
		cmb_estudio_gen_valor.setEditable(false);
		cmb_estudio_gen_valor.addActionListener(this);
		DefaultComboBoxModel defaultComboBoxModel_cmb_estudio_gen_valor = new DefaultComboBoxModel();
		cmb_estudio_gen_valor.setModel(defaultComboBoxModel_cmb_estudio_gen_valor);
		
		for (int i = 0 ; i < cantResultadoGen ; i ++){
			defaultComboBoxModel_cmb_estudio_gen_valor.addElement(strResultadoGen[i]);
		}
		
		cmb_estudio_gen_valor.setSelectedItem(0);
		cmb_estudio_gen_valor.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
		add(cmb_estudio_gen_valor);
	    
	}
	
	private void cargarEstudioRX(){
		
		//Estudio RX
		current_y = current_y + SistemaDiagnostico.alto_controles * 2;
		lbl_estudio_rx = new JLabel("¿El paciente se hizo una radiografía?");
		lbl_estudio_rx.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_estudio_rx);
		
		rdb_estudio_rx = new JRadioButton[cantRadios];
		
		rdb_estudio_rx[0] = new JRadioButton(strRadios[0]);
		rdb_estudio_rx[0].setSelected(true);
		rdb_estudio_rx[0].addActionListener(this);
		rdb_estudio_rx[0].setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, 150 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(rdb_estudio_rx[0]);
		
		rdb_estudio_rx[1] = new JRadioButton(strRadios[1]);
		rdb_estudio_rx[1].addActionListener(this);
		rdb_estudio_rx[1].setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 + 150 , current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(rdb_estudio_rx[1]);
		
	    ButtonGroup btgEstudioRX = new ButtonGroup();
	    btgEstudioRX.add(rdb_estudio_rx[0]);
	    btgEstudioRX.add(rdb_estudio_rx[1]);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
		lbl_estudio_rx_nombre = new JLabel("RX");
		lbl_estudio_rx_nombre.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
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
		cmb_estudio_rx.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
		add(cmb_estudio_rx);
		
	}
	
	public void cargarEstudioRMN() {
		
		//Estudio RX
		current_y = current_y + SistemaDiagnostico.alto_controles * 2;
		lbl_estudio_rmn = new JLabel("¿El paciente se hizo una resonancia?");
		lbl_estudio_rmn.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_estudio_rmn);
		
		rdb_estudio_rmn = new JRadioButton[cantRadios];
		
		rdb_estudio_rmn[0] = new JRadioButton(strRadios[0]);
		rdb_estudio_rmn[0].setSelected(true);
		rdb_estudio_rmn[0].addActionListener(this);
		rdb_estudio_rmn[0].setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, 150 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(rdb_estudio_rmn[0]);
		
		rdb_estudio_rmn[1] = new JRadioButton(strRadios[1]);
		rdb_estudio_rmn[1].addActionListener(this);
		rdb_estudio_rmn[1].setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 + 150 , current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(rdb_estudio_rmn[1]);
		
	    ButtonGroup btgEstudioRMN = new ButtonGroup();
	    btgEstudioRMN.add(rdb_estudio_rmn[0]);
	    btgEstudioRMN.add(rdb_estudio_rmn[1]);
	    
		current_y = current_y + SistemaDiagnostico.alto_controles;
		lbl_estudio_rmn_nombre = new JLabel("RMN");
		lbl_estudio_rmn_nombre.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
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
		cmb_estudio_rmn.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio , SistemaDiagnostico.alto_controles);
		add(cmb_estudio_rmn);
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
		  if (estadoCarga) {

			  if ( e.getSource() == btProcesar) {
				  btSiguiente();
			  }

			  if ( e.getSource() == btAtras) {
				  btAtras();
			  }
			  
			  if (e.getSource() == rdb_laboratorio[0]) {
				  
				  cambiarEstadoLaboratorio(false);
				  
			  }
			  if (e.getSource() == rdb_laboratorio[1]) {
				 
				  cambiarEstadoLaboratorio(true);
			  }
			  
			  if (e.getSource() == rdb_estudio_gen[0]) {
				  
				  cambiarEstadoEstudioGEN(false);
				  
			  }
			  if (e.getSource() == rdb_estudio_gen[1]) {
				 
				  cambiarEstadoEstudioGEN(true);
			  }
			  
			  if (e.getSource() == rdb_estudio_rx[0]) {
				  
				  cambiarEstadoEstudioRX(false);
				  
			  }
			  if (e.getSource() == rdb_estudio_rx[1]) {
				 
				  cambiarEstadoEstudioRX(true);
			  }
			  
			  if (e.getSource() == rdb_estudio_rmn[0]) {
				  
				  cambiarEstadoEstudioRMN(false);
				  
			  }
			  if (e.getSource() == rdb_estudio_rmn[1]) {
				 
				  cambiarEstadoEstudioRMN(true);
			  }
			  
		  }
	}
	
	private void btSiguiente() {
		
		ResultadoDiagnostico resultadoDiagnostico = new ResultadoDiagnostico(frame);
		  
	}
	
	private void btAtras() {
		
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	
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
