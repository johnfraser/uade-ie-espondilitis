package front;

	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
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
	import model.Consulta;
	import model.Diagnostico;
	import model.Paciente;



	public class DatosPaciente extends JPanel implements ActionListener, WindowListener{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public static final String APP_VERSION = "1.0"; 
		public static final String APP_NAME = "Sistema de Diagnóstico"; 
		
		public static final int alto_controles = 20;
		public static final int ancho_controles = 500;
		public static final int padding_controles = 50;
		public static final int padding_radio = 250;
		
		public static final int APP_WINDOW_X = 640;
		public static final int APP_WINDOW_Y = 640;
		
		//protected static JFrame frame;

		public static int id_paciente_actual;
		
		DBHelper dbHelper;
		
		private MainFrame frame;
		
		public boolean encontroPacienteExistente=false;
		
		public JLabel lbl_titulo;
		
		public JLabel lbl_paciente_nuevo;
		public ButtonGroup btg_paciente_nuevo;
		public JRadioButton rdb_paciente_nuevo_si;
		public JRadioButton rdb_paciente_nuevo_no;
		
		public JLabel lbl_dni_existente;
		public JTextField tld_dni_existente;
		
		public JLabel lbl_paciente;
		
		public JLabel lbl_paciente_datos;
		public JLabel lbl_dni;
		public JLabel lbl_nombre;
		public JLabel lbl_apellido;
		public JLabel lbl_edad;
		public JLabel lbl_sexo;
		
		public JLabel lbl_diagnosticos_anteriores;
		public JComboBox<String> cmb_diagnosticos_anteriores;
		DefaultComboBoxModel<String> defaultComboBoxModel_cmb_diagnosticos_anteriores;
		public JButton bt_diagnosticos_anteriores;
		
		public JTextField tld_paciente;
		public JTextField tld_dni;
		public JTextField tld_nombre;
		public JTextField tld_apellido;
		public JTextField tld_edad;

		public ButtonGroup btgSexo;
		public JRadioButton rdbMasculino;
		public JRadioButton rdbFemenino;
		
		protected JButton btBuscarCliente;
		
		//protected JButton btGuardar;
		
		protected JButton btSiguiente;
		
		public int current_x = 0;
		public int current_y = 0;
		
		protected static JOptionPane jOptionPane;
		
		public DatosPaciente (MainFrame frame) {
			
			this.frame = frame;
			
			setLayout(null);
			
			MainFrame.consulta = new Consulta();
			
			id_paciente_actual = 0;
			
			jOptionPane = new JOptionPane();
			
			dbHelper = new DBHelper();
			
			current_x = 10;
			current_y = 10;
			
			//Titulo
			lbl_titulo = new JLabel("Datos del paciente:");
			lbl_titulo.setBounds(current_x,current_y,APP_WINDOW_X / 2 - padding_controles,alto_controles);
			add(lbl_titulo); //, gridBagConstraints);
			
			current_y = current_y + alto_controles * 2;
			
			//Consulto si el paciente existe
			//Nombre
			lbl_paciente_nuevo = new JLabel("¿Es un paciente nuevo?:");
			lbl_paciente_nuevo.setBounds(current_x,current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
			add(lbl_paciente_nuevo); //, gridBagConstraints);
			rdb_paciente_nuevo_no = new JRadioButton("No");
			rdb_paciente_nuevo_no.addActionListener(this);
			rdb_paciente_nuevo_no.setSelected(true);
			rdb_paciente_nuevo_no.setBounds(APP_WINDOW_X / 2,current_y, 150 - padding_controles, alto_controles);
		    add(rdb_paciente_nuevo_no);
		    
		    rdb_paciente_nuevo_si = new JRadioButton("Sí");
		    rdb_paciente_nuevo_si.addActionListener(this);
		    rdb_paciente_nuevo_si.setBounds(APP_WINDOW_X / 2 + 150 , current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
		    add(rdb_paciente_nuevo_si);
		    
		    btg_paciente_nuevo = new ButtonGroup();
		    btg_paciente_nuevo.add(rdb_paciente_nuevo_no);
		    btg_paciente_nuevo.add(rdb_paciente_nuevo_si);

		    current_y = current_y + alto_controles * 2;
		    
		    //Nombre
		    lbl_dni_existente = new JLabel("Ingrese el DNI del paciente:");
		    lbl_dni_existente.setBounds(current_x,current_y,APP_WINDOW_X / 2 - padding_controles, alto_controles);
		    add(lbl_dni_existente); //, gridBagConstraints);
		    tld_dni_existente = new JTextField ();
		    tld_dni_existente.setBounds(APP_WINDOW_X / 2,current_y,APP_WINDOW_X / 2 - padding_controles , alto_controles);
		    add(tld_dni_existente); //, gridBagConstraints);
		    current_y = current_y + alto_controles *2;
		    btBuscarCliente = new JButton("Buscar Cliente");
		    btBuscarCliente.addActionListener(this);
		    btBuscarCliente.setBounds(APP_WINDOW_X / 2 - 100 ,current_y, APP_WINDOW_X / 2 - padding_controles - 100 , alto_controles);
			add(btBuscarCliente);
			
	     	cargarDiagnosticos_anteriores();
			
		    current_y = current_y + alto_controles * 2;
		
		    
			//Titulo
			lbl_paciente_datos = new JLabel("Por favor, ingrese los datos del paciente:");
			lbl_paciente_datos.setBounds(current_x,current_y,APP_WINDOW_X / 2 - padding_controles,alto_controles);
			add(lbl_paciente_datos); //, gridBagConstraints);
			
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
			
		    current_y = current_y + alto_controles ;
		   /* 
			btGuardar = new JButton("Guardar");
			btGuardar.addActionListener(this);
			btGuardar.setVisible(false);
			btGuardar.setBounds(APP_WINDOW_X / 2 - ( 150 / 2) ,current_y, 150 , alto_controles);
			add(btGuardar);
	*/
		    current_y = current_y + alto_controles * 2;
			
			btSiguiente = new JButton("Siguiente");
			btSiguiente.addActionListener(this);
			btSiguiente.setBounds(APP_WINDOW_X / 2 - ( 150 / 2) ,current_y, 150 , alto_controles);
			add(btSiguiente);
		    
			this.setVisible(true);
			
			encontroPacienteExistente = false;
			estadosPacienteExistente();
			
			DBHelper dbHelper = new DBHelper();
			
			dbHelper.test();
			
			
			
			
		}
		
		private void cargarDiagnosticos_anteriores() {
			
					current_y = current_y + MainFrame.alto_controles * 2;
					
					//Diagnosticos anteriores
					
					lbl_diagnosticos_anteriores = new JLabel("Diagnósticos anteriores del paciente (N°):");
					lbl_diagnosticos_anteriores.setBounds(current_x,current_y,MainFrame.APP_WINDOW_X / 2 - MainFrame.padding_controles, MainFrame.alto_controles);
					add(lbl_diagnosticos_anteriores);
					
					
					cmb_diagnosticos_anteriores = new JComboBox<String>();
					cmb_diagnosticos_anteriores.setEditable(false);
					cmb_diagnosticos_anteriores.addActionListener(this);
					defaultComboBoxModel_cmb_diagnosticos_anteriores = new DefaultComboBoxModel<String>();
					cmb_diagnosticos_anteriores.setModel(defaultComboBoxModel_cmb_diagnosticos_anteriores);
					
					//defaultComboBoxModel_cmb_diagnosticos_anteriores.addElement("");
					/*
					defaultComboBoxModelcmbritmo_evacuatorio.addElement("Diarrea");
					defaultComboBoxModelcmbritmo_evacuatorio.addElement("Moco");
					defaultComboBoxModelcmbritmo_evacuatorio.addElement("Pus");
					defaultComboBoxModelcmbritmo_evacuatorio.addElement("Sangre");
					cmbritmo_evacuatorio.setSelectedItem(0);
					*/
					cmb_diagnosticos_anteriores.setEnabled(false);
					cmb_diagnosticos_anteriores.setBounds(MainFrame.APP_WINDOW_X / 2,current_y, MainFrame.padding_radio , MainFrame.alto_controles);
					add(cmb_diagnosticos_anteriores);
					
				    current_y = current_y + alto_controles * 2;
				    bt_diagnosticos_anteriores = new JButton("Ver diagnóstico");
				    bt_diagnosticos_anteriores.addActionListener(this);
				    bt_diagnosticos_anteriores.setEnabled(false);
				    bt_diagnosticos_anteriores.setBounds(APP_WINDOW_X / 2 - 100 ,current_y, APP_WINDOW_X / 2 - padding_controles - 100 , alto_controles);
					add(bt_diagnosticos_anteriores);
			

		}
		
		/*
		public static void main(String[] args) {

		        //Schedule a job for the event-dispatching thread:
		        //creating and showing this application's GUI.
		        javax.swing.SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		                createAndShowGUI();
		            }
		        });			
			}
		*/
		/*
	    private static void createAndShowGUI() {
	    	
	    	MainFrame MainFrame;
	    	
	        //Create and set up the window.
	        frame = new JFrame(APP_NAME + " "+ APP_VERSION);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        MainFrame = new MainFrame();
	        
	        frame.add(MainFrame);
	        
	        
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
			*/
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			if ( arg0.getSource() == btSiguiente ) {
					btSiguiente();
			}
			
			if ( arg0.getSource() == btBuscarCliente ) {
				
				String tempDni = tld_dni_existente.getText();

				if ( ! tempDni.isEmpty() ) {

					if ( MainFrame.validarTextoNumerico(tempDni)  ) {

						btBuscarCliente();

					}else {
						String strError="El DNI del cliente no es válido";
						jOptionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
						JOptionPane.showMessageDialog(frame, strError, "Error", JOptionPane.ERROR_MESSAGE );
					}
				}else {
					String strError="Falta ingresar el DNI del cliente";
					jOptionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(frame, strError, "Error", JOptionPane.ERROR_MESSAGE );
				}

			}
			
			if ( arg0.getSource() == rdb_paciente_nuevo_no ) {
				//esNuevoPaciente=false;
				estadosPacienteExistente();
			}
			
			if (arg0.getSource() == rdb_paciente_nuevo_si) {
				//esNuevoPaciente=true;
				estadosPacienteNuevo();
			}
		/*	
			if (arg0.getSource() == btGuardar) {
				btGuardar();	
			}
			*/

			if (arg0.getSource() == bt_diagnosticos_anteriores) {
				
				bt_diagnosticos_anteriores();
				
			}
			
		}
		
		private void btSiguiente() {

			if (validar()) {
				if (GuardarPaciente()) {
					//DatosDolencias datosDolencias = new DatosDolencias(frame, true);
					MainFrame.MostrarDatosPrimeraDolencia(this);
					
				}
			}
		}
		
		private void btBuscarCliente() {
			
			
			Paciente paciente = dbHelper.getPaciente(Integer.parseInt(tld_dni_existente.getText()));
			
			if ( paciente != null) {
				
				tld_dni.setText( String.valueOf(paciente.dni) );
				tld_nombre.setText(paciente.nombre);
				tld_apellido.setText(paciente.apellido);
				tld_edad.setText(String.valueOf(paciente.edad));
				
				if ( paciente.sexo.contentEquals(Paciente.SEXO_MASCULINO)) {
					rdbMasculino.setSelected(true);
					rdbFemenino.setSelected(false);
				}else {
					if (paciente.sexo.contentEquals(Paciente.SEXO_FEMENINO)) {
						rdbFemenino.setSelected(true);
						rdbMasculino.setSelected(false);
					}
				}
				
				estadosPacienteEncontrado();
				id_paciente_actual = paciente.id_paciente;
				encontroPacienteExistente = true;

				MainFrame.consulta = null;
				MainFrame.consulta = new Consulta();
				MainFrame.consulta.paciente = new Paciente(paciente.id_paciente, paciente.dni , paciente.nombre, paciente.apellido, paciente.edad, paciente.sexo);

				ArrayList<Diagnostico> diagnosticos_anteriores = dbHelper.getDiagnosticosAnteriores(paciente.id_paciente);
				
				defaultComboBoxModel_cmb_diagnosticos_anteriores.removeAllElements();
				
				if (diagnosticos_anteriores != null) {
					
					if (diagnosticos_anteriores.size() > 0) {
											
						for (Diagnostico d : diagnosticos_anteriores ) {
						
							defaultComboBoxModel_cmb_diagnosticos_anteriores.addElement(String.valueOf(d.id_diagnostico));
						
						}
						
						cmb_diagnosticos_anteriores.setEnabled(true);
						bt_diagnosticos_anteriores.setEnabled(true);
						
					}else {
						defaultComboBoxModel_cmb_diagnosticos_anteriores.addElement("Sin diagnósticos");
					}
					
				}else {
					defaultComboBoxModel_cmb_diagnosticos_anteriores.addElement("Sin diagnósticos");
				}
				
				
			}else {
				String strError="El cliente no existe";
				jOptionPane.setMessageType(JOptionPane.WARNING_MESSAGE);
				JOptionPane.showMessageDialog(frame, strError, "Cliente no existe", JOptionPane.ERROR_MESSAGE );
			}
			
			
		}
	/*	
		private void btGuardar() {
			
			if ( validar() ) {
			
				GuardarPaciente();
				
			}
			
		}
	*/	
		
		private void bt_diagnosticos_anteriores() {
			
			int id_diagnostico_elegido = -1;
			
			if (cmb_diagnosticos_anteriores.isEnabled()) {
				if (cmb_diagnosticos_anteriores.getItemCount() > 0) {
					
					id_diagnostico_elegido = Integer.parseInt((String)cmb_diagnosticos_anteriores.getSelectedItem());
					
					Diagnostico diagnostico = dbHelper.getDiagnostico(id_diagnostico_elegido);
					
					Consulta c  = new Consulta();
					c.paciente = MainFrame.consulta.paciente;
					c.diagnostico = diagnostico;
					
					//ResultadosAnteriores resultadosAnteriores = new ResultadosAnteriores(frame,c);
					MainFrame.MostrarResultadosAnteriores(this, c);
					
				}			
			}
			

			
		}
		
		private boolean GuardarPaciente() {
			
			String sexo;
			boolean pacienteNuevo;
			boolean pacienteYaExisteError = false;
			DBHelper dbHelper;
			String strMensaje="";
			
			dbHelper = new DBHelper();
			
			pacienteNuevo = false;
			
			
			if (rdbFemenino.isSelected()) {
				sexo = Paciente.SEXO_FEMENINO;
			}else {
				sexo = Paciente.SEXO_MASCULINO;
			}
			
			
			if ( !encontroPacienteExistente ) {
				
				pacienteNuevo = true;	
				
				//Validar que no exista un paciente con ese DNI
				
				Paciente paciente_existente = dbHelper.getPaciente(Integer.parseInt(tld_dni.getText()));
				
				if (paciente_existente != null) {
					
					pacienteYaExisteError = true;
					String strError = "Ya existe un paciente con el DNI ingresado.";
					jOptionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(frame, strError, "Error", JOptionPane.ERROR_MESSAGE );

				}else {
					id_paciente_actual = dbHelper.getUltimoId(DBHelper.TABLE_Paciente_NAME, DBHelper.TABLE_Paciente_id_paciente) + 1;
				}
				
			}

			if ( !pacienteYaExisteError ) {

				if(encontroPacienteExistente) {
					if ( MainFrame.consulta.paciente.nombre.compareTo( tld_nombre.getText()) != 0 ||
							MainFrame.consulta.paciente.apellido.compareTo( tld_apellido.getText()) != 0 ||	
							MainFrame.consulta.paciente.edad != Integer.parseInt(tld_edad.getText()) ||
							MainFrame.consulta.paciente.sexo.compareTo(sexo) != 0 ) {

						MainFrame.consulta.paciente.nombre = tld_nombre.getText();
						MainFrame.consulta.paciente.apellido = tld_apellido.getText();	
						MainFrame.consulta.paciente.edad = Integer.parseInt(tld_edad.getText());
						MainFrame.consulta.paciente.sexo = sexo;

						dbHelper.UpdatePaciente(MainFrame.consulta.paciente);
						strMensaje="El paciente existente se actualizó correctamente en el sistema.";
						jOptionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
						JOptionPane.showMessageDialog(frame, strMensaje, "Datos del paciente", JOptionPane.INFORMATION_MESSAGE );
					}
				}
				else {
					MainFrame.consulta = null;
					MainFrame.consulta = new Consulta();
					MainFrame.consulta.paciente = new Paciente(id_paciente_actual, Integer.parseInt(tld_dni.getText()) , tld_nombre.getText(), tld_apellido.getText(), Integer.parseInt(tld_edad.getText()), sexo);
					dbHelper.InsertPaciente(MainFrame.consulta.paciente);
					strMensaje="El nuevo paciente se ingresó correctamente en el sistema.";
					jOptionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
					JOptionPane.showMessageDialog(frame, strMensaje, "Datos del paciente", JOptionPane.INFORMATION_MESSAGE );
				}

				

			}
			
			return !pacienteYaExisteError;
			
		}
		
		private void estadosPacienteNuevo() {
			
			limpiarDatos();
//			btGuardar.setEnabled(true);
			btBuscarCliente.setEnabled(false);
			lbl_paciente_datos.setEnabled(true);
			lbl_paciente_datos.setVisible(true);
			tld_apellido.setEnabled(true);
			lbl_apellido.setEnabled(true);
			lbl_nombre.setEnabled(true);
			tld_nombre.setEnabled(true);
			tld_dni.setEnabled(true);
			lbl_dni.setEnabled(true);
			tld_edad.setEnabled(true);
			lbl_edad.setEnabled(true);
			lbl_sexo.setEnabled(true);
			rdbFemenino.setEnabled(true);
			rdbMasculino.setEnabled(true);
			lbl_dni_existente.setEnabled(false);
			tld_dni_existente.setEnabled(false);
			cmb_diagnosticos_anteriores.setEnabled(false);
			cmb_diagnosticos_anteriores.removeAllItems();;
			bt_diagnosticos_anteriores.setEnabled(false);
		}
		
		private void estadosPacienteExistente() {
			
			limpiarDatos();
//			btGuardar.setEnabled(false);
			btBuscarCliente.setEnabled(true);
			lbl_paciente_datos.setEnabled(false);
			lbl_paciente_datos.setVisible(false);
			tld_apellido.setEnabled(false);
			lbl_apellido.setEnabled(false);
			lbl_nombre.setEnabled(false);
			tld_nombre.setEnabled(false);
			tld_dni.setEnabled(false);
			lbl_dni.setEnabled(false);
			tld_edad.setEnabled(false);
			lbl_edad.setEnabled(false);
			lbl_sexo.setEnabled(false);
			rdbFemenino.setEnabled(false);
			rdbMasculino.setEnabled(false);
			lbl_dni_existente.setEnabled(true);
			tld_dni_existente.setEnabled(true);
			cmb_diagnosticos_anteriores.setEnabled(false);
			cmb_diagnosticos_anteriores.removeAllItems();;
			bt_diagnosticos_anteriores.setEnabled(false);
		}
		
		
		private void estadosPacienteEncontrado() {
			
			btBuscarCliente.setEnabled(true);
		//	btGuardar.setEnabled(true);
			lbl_paciente_datos.setEnabled(false);
			lbl_paciente_datos.setVisible(false);
			tld_apellido.setEnabled(true);
			lbl_apellido.setEnabled(true);
			lbl_nombre.setEnabled(true);
			tld_nombre.setEnabled(true);
			tld_dni.setEnabled(true);
			lbl_dni.setEnabled(true);
			tld_edad.setEnabled(true);
			lbl_edad.setEnabled(true);
			lbl_sexo.setEnabled(true);
			rdbFemenino.setEnabled(true);
			rdbMasculino.setEnabled(true);
			lbl_dni_existente.setEnabled(true);
			tld_dni_existente.setEnabled(true);
			cmb_diagnosticos_anteriores.setEnabled(false);
			cmb_diagnosticos_anteriores.removeAllItems();;
			bt_diagnosticos_anteriores.setEnabled(false);
		}
		
		
		private void limpiarDatos() {
			
			tld_apellido.setText("");
			tld_nombre.setText("");
			tld_dni.setText("");
			tld_edad.setText("");
			rdbMasculino.setSelected(true);
			tld_dni_existente.setText("");
			
			MainFrame.consulta = null;
			MainFrame.consulta = new Consulta();
			
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
			
			if ( !MainFrame.validarTextoNumerico( this.tld_dni.getText() )) {
				strError = "Dato no numérico en el campo 'DNI'";
				resultado = false;
			}
			
			if ( !MainFrame.validarTextoNumerico( this.tld_edad.getText() )) {
				strError = "Dato no numérico en el campo 'edad'";
				resultado = false;
			}
			
			if (resultado == false) {
				jOptionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(frame, strError, "Error", JOptionPane.ERROR_MESSAGE );
			}
			return resultado;
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
			String strMsg = "¿Está seguro que desea salir?";
			int jOptionResult = JOptionPane.showOptionDialog(frame, strMsg, "Consulta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,null );

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
