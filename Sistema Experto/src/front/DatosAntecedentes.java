package front;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


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
	public static int cantAntecedentesFamiliares = 10;
	
		
	
	protected static JFrame frame;
	protected JFrame prev_frame;
	
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
	
	public DatosAntecedentes(JFrame prevframe) {
		
		prev_frame = prevframe;
		prev_frame.setVisible(false);
		
		
		setLayout(null);
		
		createAndShowGUI();
		
		current_x = 10;
		current_y = 10;
		
		//Titulo
		lbl_titulo = new JLabel("Por favor tilde los antecedentes que correspondan:");
		lbl_titulo.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X - SistemaDiagnostico.padding_controles,SistemaDiagnostico.alto_controles);
		add(lbl_titulo); 
		
		current_y = current_y + SistemaDiagnostico.alto_controles * 2;
		
		//Antecedentes Paciente
		current_y = current_y + SistemaDiagnostico.alto_controles;
		lbl_antecedentes_paciente = new JLabel("Antecedentes del Paciente");
		lbl_antecedentes_paciente.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_antecedentes_paciente);
		
		chk_antecedentes_paciente = new JCheckBox[cantAntecedentesPaciente];
		
		for (int i = 0; i < cantAntecedentesPaciente; i++) {			
			chk_antecedentes_paciente[i] = new JCheckBox(strAntecedentesPaciente[i]);
			chk_antecedentes_paciente[i].setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
			add(chk_antecedentes_paciente[i]);
			current_y = current_y + SistemaDiagnostico.alto_controles;
		}
		
		current_y = current_y + SistemaDiagnostico.alto_controles ;
		
		//Antecedentes Familiares
		current_y = current_y + SistemaDiagnostico.alto_controles;
		lbl_antecedentes_familiares = new JLabel("Antecedentes Familiares");
		lbl_antecedentes_familiares.setBounds(current_x,current_y,SistemaDiagnostico.APP_WINDOW_X / 2 - SistemaDiagnostico.padding_controles, SistemaDiagnostico.alto_controles);
		add(lbl_antecedentes_familiares);
		
		chk_antecedentes_familiares = new JCheckBox[cantAntecedentesFamiliares];
		
		for (int i = 0; i < cantAntecedentesFamiliares; i++) {			
			chk_antecedentes_familiares[i] = new JCheckBox(strAntecedentesFamiliares[i]);
			chk_antecedentes_familiares[i].setBounds(SistemaDiagnostico.APP_WINDOW_X / 2,current_y, SistemaDiagnostico.padding_radio  , SistemaDiagnostico.alto_controles);
			add(chk_antecedentes_familiares[i]);
			current_y = current_y + SistemaDiagnostico.alto_controles;
		}

		
	    current_y = current_y + SistemaDiagnostico.alto_controles * 2;
	    
		btAtras = new JButton("Atrás");
		btAtras.addActionListener(this);
		btAtras.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 - ( 150 / 2) - 150 ,current_y, 200 , SistemaDiagnostico.alto_controles);
		add(btAtras);
		
		btSiguiente = new JButton("Siguiente");
		btSiguiente.addActionListener(this);
		btSiguiente.setBounds(SistemaDiagnostico.APP_WINDOW_X / 2 - ( 150 / 2) + 100 ,current_y, 200, SistemaDiagnostico.alto_controles);
		add(btSiguiente);
		
		estadoCarga = true;
		
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

			  if ( e.getSource() == btSiguiente) {
				  btSiguiente();
			  }

			  if ( e.getSource() == btAtras) {
				  btAtras();
			  }
		  }
	}
	
	private void btSiguiente() {
		
		  DatosEstudios datosEstudios = new DatosEstudios(frame);
		  
	}
	
	private void btAtras() {
		
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	
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
