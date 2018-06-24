package front;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.xml.stream.events.EndDocument;

import model.Consulta;
import model.Paciente;



public class SistemaDiagnostico extends JPanel implements ActionListener{
	
	public static final String APP_VERSION = "1.0"; 
	public static final String APP_NAME = "Sistema de Diagnóstico"; 
	
	public static final int alto_controles = 20;
	public static final int ancho_controles = 500;
	public static final int padding_controles = 50;
	public static final int padding_radio = 250;
	
	public static final int APP_WINDOW_X = 640;
	public static final int APP_WINDOW_Y = 640;
	
	public static final int id_paciente = 1;
	public static final int id_diagnostico = 1;
	public static final int id_dolor = 1;
	public static final int id_antecedente_paciente = 1;
	public static final int id_antecedente_familiar = 1;
	public static final int id_laboratorio = 1;
	public static final int id_estudio_gen = 1;
	public static final int id_estudio_rx = 1;
	public static final int id_estudio_rmn = 1;
	
	protected static JFrame frame;
	
	public static Consulta consulta;
	
	public JLabel lbl_titulo;
	
	public JLabel lbl_paciente;
	public JLabel lbl_dni;
	public JLabel lbl_nombre;
	public JLabel lbl_apellido;
	public JLabel lbl_edad;
	public JLabel lbl_sexo;
	
	public JTextField tld_paciente;
	public JTextField tld_dni;
	public JTextField tld_nombre;
	public JTextField tld_apellido;
	public JTextField tld_edad;

	public ButtonGroup btgSexo;
	public JRadioButton rdbMasculino;
	public JRadioButton rdbFemenino;
	
	
	protected JButton btSiguiente;
	
	public int current_x = 0;
	public int current_y = 0;
	
	protected static JOptionPane jOptionPane;
	
	public SistemaDiagnostico () {
		
		setLayout(null);
		
		consulta = new Consulta();
		
		jOptionPane = new JOptionPane();
		
		current_x = 10;
		current_y = 10;
		
		//Titulo
		lbl_titulo = new JLabel("Por favor, ingrese los datos del paciente:");
		lbl_titulo.setBounds(current_x,current_y,APP_WINDOW_X / 2 - padding_controles,alto_controles);
		add(lbl_titulo); //, gridBagConstraints);
		
		current_y = current_y + alto_controles * 2;
		
		//Nombre
		lbl_nombre = new JLabel("Nombre:");
		lbl_nombre.setBounds(current_x,current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
		add(lbl_nombre); //, gridBagConstraints);
		tld_nombre = new JTextField ();
		tld_nombre.setBounds(APP_WINDOW_X / 2,current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
		add(tld_nombre); //, gridBagConstraints);
		
		
		//Apellido
		current_y = current_y + alto_controles;
		
		lbl_apellido = new JLabel("Apellido:");
		lbl_apellido.setBounds(current_x,current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
		add(lbl_apellido); //, gridBagConstraints);
		tld_apellido = new JTextField ();
		tld_apellido.setBounds(APP_WINDOW_X / 2,current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
		add(tld_apellido); //, gridBagConstraints);
		
		//DNI
		current_y = current_y + alto_controles;
		lbl_dni = new JLabel("Dni:");
		lbl_dni.setBounds(current_x,current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
		add(lbl_dni); //, gridBagConstraints);
		tld_dni = new JTextField ();
		tld_dni.setBounds(APP_WINDOW_X / 2,current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
		add(tld_dni); //, gridBagConstraints);
		
		//Edad
		current_y = current_y + alto_controles;
		lbl_edad = new JLabel("Edad:");
		lbl_edad.setBounds(current_x,current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
		add(lbl_edad); //, gridBagConstraints);
		tld_edad = new JTextField ();
		tld_edad.setBounds(APP_WINDOW_X / 2,current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
		add(tld_edad); //, gridBagConstraints);
		
		
		//Sexo
		current_y = current_y + alto_controles;
		lbl_sexo = new JLabel("Sexo");
		lbl_sexo.setBounds(current_x,current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
		add(lbl_sexo); //, gridBagConstraints);
		
	    rdbMasculino = new JRadioButton("Masculino");
	    rdbMasculino.addActionListener(this);
	    rdbMasculino.setSelected(true);
	    rdbMasculino.setBounds(APP_WINDOW_X / 2,current_y, 150 - padding_controles, alto_controles);
	    add(rdbMasculino);
	    
	    rdbFemenino = new JRadioButton("Femenino");
	    rdbFemenino.addActionListener(this);
	    rdbFemenino.setBounds(APP_WINDOW_X / 2 + 150 , current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
	    add(rdbFemenino);
	    
	    btgSexo = new ButtonGroup();
	    btgSexo.add(rdbMasculino);
	    btgSexo.add(rdbFemenino);
			
	    current_y = current_y + alto_controles * 5;
	    
		btSiguiente = new JButton("Siguiente");
		btSiguiente.addActionListener(this);
		btSiguiente.setBounds(APP_WINDOW_X / 2 - ( 150 / 2) ,current_y, 150 , alto_controles);
		add(btSiguiente);
		
		
	}
	
	
	public static void main(String[] args) {

	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });			
		}
	
    private static void createAndShowGUI() {
    	
    	SistemaDiagnostico sistemaDiagnostico;
    	
        //Create and set up the window.
        frame = new JFrame(APP_NAME + " "+ APP_VERSION);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        sistemaDiagnostico = new SistemaDiagnostico();
        
        frame.add(sistemaDiagnostico);
        
        
        frame.setSize(APP_WINDOW_X, APP_WINDOW_Y);		
   		
   		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
   	    int x = (int) ((screen.getWidth() - frame.getWidth()) / 2);
   	    int y = (int) ((screen.getHeight() - frame.getHeight()) / 2);
   	    
   	    frame.setLocation(x, y);
   	 
   	    frame.setPreferredSize(new Dimension(APP_WINDOW_X,APP_WINDOW_Y));
         
        //Display the window.
        frame.pack();
        
       
        frame.setVisible(true);
        
    }
		
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		if ( arg0.getSource() == btSiguiente ) {
			if (validar()) {
				btSiguiente();
			}
		}
	}
	
	private void btSiguiente() {
		
		String sexo;
		
		if (rdbFemenino.isSelected()) {
			sexo = Paciente.SEXO_FEMENINO;
		}else {
			sexo = Paciente.SEXO_MASCULINO;
		}
		
		consulta.GenerarPaciente(id_paciente, Integer.parseInt(tld_dni.getText()) , tld_nombre.getText(), tld_apellido.getText(), Integer.parseInt(tld_edad.getText()), sexo );
		
		DatosDolencias datosDolencias = new DatosDolencias(frame, true);
	
	}
	
	public boolean validar() {
		
		boolean resultado=true;
		String strError="";
		
		if ( this.tld_nombre.getText().isEmpty()) {
			strError = "Falta completar el campo 'nombre'";
		    resultado = false;
		}
		
		if ( this.tld_apellido.getText().isEmpty()) {
			strError = "Falta completar el campo 'apellido'";
			resultado = false;
		}
		if ( this.tld_dni.getText().isEmpty()) {
			strError = "Falta completar el campo 'DNI'";
			resultado = false;
		}
				
		if ( this.tld_edad.getText().isEmpty()) {
			strError = "Falta completar el campo 'edad'";
			resultado = false;
		}
		
		if ( !validarTextoNumerico( this.tld_dni.getText() )) {
			strError = "Dato no numérico en el campo 'DNI'";
			resultado = false;
		}
		
		if ( !validarTextoNumerico( this.tld_edad.getText() )) {
			strError = "Dato no numérico en el campo 'edad'";
			resultado = false;
		}
		
		if (resultado == false) {
			jOptionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
			jOptionPane.showMessageDialog(frame, strError, "Error", JOptionPane.ERROR_MESSAGE );
		}
		return resultado;
	}

	
	 public static final boolean validarTextoNumerico(String text) {
		 
		 if (text.length() == 0) {
			 return true;
		 }
		 
	      try {
	         Integer.parseInt(text);
	         return true;
	      } catch (NumberFormatException e) {
	         return false;
	      }
	  }

    

}
