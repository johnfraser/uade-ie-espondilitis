package front;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Consulta;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final String APP_VERSION = "1.0"; 
	public static final String APP_NAME = "Sistema de Diagnóstico"; 
	
	public static final int alto_controles = 20;
	public static final int ancho_controles = 500;
	public static final int padding_controles = 50;
	public static final int padding_radio = 250;
	
	public static final int APP_WINDOW_X = 640;
	public static final int APP_WINDOW_Y = 640;
	
	private static MainFrame frame;
	
	//private SistemaDiagnostico sistemaDiagnostico;
	private static DatosPaciente datosPaciente; 
	private static DatosDolencias datosDolencia1;
	private static DatosDolencias datosDolencia2;
	private static DatosAntecedentes datosAntecedentes;
	private static DatosEstudios datosEstudios;
	private static ResultadoDiagnostico resultadoDiagnostico;
	private static ResultadosAnteriores resultadosAnteriores;
	
	public static Consulta consulta;
		 
	public MainFrame() {
		 super(APP_NAME + " "+ APP_VERSION);
		 consulta = new Consulta();
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
    	
    	
    	
        //Create and set up the window.
        frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        MostrarDatosPaciente(null);

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
    
    public static void MostrarDatosPaciente(JPanel panel_anterior) {
    	
    	removeAllWindowListeners();
    	
    	if ( panel_anterior != null) {
    		panel_anterior.setVisible(false);
    	}
    	
    	if ( datosPaciente == null) {
    		datosPaciente = new DatosPaciente(frame);
    	}else {
    		datosPaciente.setVisible(true);
    	}
    	
		frame.addWindowListener(datosPaciente);
    	
    	frame.getContentPane().removeAll();
        frame.getContentPane().add(datosPaciente);
        frame.repaint();
        frame.revalidate();
    }
    
    public static void MostrarDatosPrimeraDolencia(JPanel panel_anterior) {

    	removeAllWindowListeners();
    	
    	if ( panel_anterior != null) {
    		panel_anterior.setVisible(false);
    	}

     	if ( datosDolencia1 == null) {
     		datosDolencia1 = new DatosDolencias(frame,true);
    	}else {
    		datosDolencia1.setVisible(true);
    	}
     	
 		frame.addWindowListener(datosDolencia1);
    	     	
    	frame.getContentPane().removeAll();
        frame.getContentPane().add(datosDolencia1);
        frame.repaint();
        frame.revalidate();
    	
    }
    
    public static void MostrarDatosSegundaDolencia(JPanel panel_anterior) {

    	removeAllWindowListeners();
    	
    	if ( panel_anterior != null) {
    		panel_anterior.setVisible(false);
    	}
    	
     	if ( datosDolencia2 == null) {
     		datosDolencia2 = new DatosDolencias(frame,false);	
    	}else {
    		datosDolencia2.setVisible(true);

    	}
     	
		frame.addWindowListener(datosDolencia2);
     	
    	frame.getContentPane().removeAll();
        frame.getContentPane().add(datosDolencia2);
        frame.repaint();
        frame.revalidate();
    	
    }
	
    
    public static void MostrarDatosAntecedentes(JPanel panel_anterior, boolean esPrimeraDolencia) {
    	
    	removeAllWindowListeners();
    	
    	if ( panel_anterior != null) {
    		panel_anterior.setVisible(false);
    	}
    	
     	if ( datosAntecedentes== null) {
     		datosAntecedentes = new DatosAntecedentes(frame,esPrimeraDolencia);	
    	}else {
    		datosAntecedentes.setVisible(true);
    	}

     	frame.addWindowListener(datosAntecedentes);
     	
    	frame.getContentPane().removeAll();
        frame.getContentPane().add(datosAntecedentes);
        frame.repaint();
        frame.revalidate();
    	
    }
    
    public static void MostrarDatosEstudios(JPanel panel_anterior) {
    	
    	removeAllWindowListeners();
    	
    	if ( panel_anterior != null) {
    		panel_anterior.setVisible(false);
    	}
    	
     	if ( datosEstudios== null) {
     		datosEstudios = new DatosEstudios(frame);	
    	}else {
    		datosEstudios.setVisible(true);
    	}
     	
		frame.addWindowListener(datosEstudios);
     	
    	frame.getContentPane().removeAll();
        frame.getContentPane().add(datosEstudios);
        frame.repaint();
        frame.revalidate();
    	
    }
    
    public static void MostrarResultadoDiagnostico(JPanel panel_anterior) {
    	
    	removeAllWindowListeners();
    	
    	if ( panel_anterior != null) {
    		panel_anterior.setVisible(false);
    	}
    	
     	if ( resultadoDiagnostico== null) {
     		resultadoDiagnostico = new ResultadoDiagnostico(frame);	
    	}else {
    		resultadoDiagnostico.setVisible(true);
    	}
     	
    	frame.addWindowListener(resultadoDiagnostico);
     	
    	frame.getContentPane().removeAll();
        frame.getContentPane().add(resultadoDiagnostico);
        frame.repaint();
        frame.revalidate();
    	
    }
    
    public static void MostrarResultadosAnteriores(JPanel panel_anterior, Consulta consulta_anterior) {
    	
    	removeAllWindowListeners();
    	
    	if ( panel_anterior != null) {
    		panel_anterior.setVisible(false);
    	}
    	
     	resultadosAnteriores = null;
     	resultadosAnteriores = new ResultadosAnteriores(frame, consulta_anterior);	
    	
     	frame.addWindowListener(resultadosAnteriores);
    	frame.getContentPane().removeAll();
        frame.getContentPane().add(resultadosAnteriores);
        frame.repaint();
        frame.revalidate();
    	
    }
    
    public static void BorrarSegundaDolencia() {
    	
    	datosDolencia2 = null;
    	consulta.dolor2 = null;
    }
    
    public static void ReiniciarDiagnostico() {
    	
    	datosPaciente = null; 
    	datosDolencia1 = null;
    	datosDolencia2 = null;
    	datosAntecedentes = null;
    	datosEstudios = null;
    	resultadoDiagnostico = null;
    	resultadosAnteriores = null;
    	
    	consulta = new Consulta();
    	
    	MostrarDatosPaciente(null);
    	
    }
    
    private static void removeAllWindowListeners() {
    	
    	WindowListener[] windowListeners = frame.getListeners(WindowListener.class);
    	
    	for ( int i = 0; i < windowListeners.length ; i++) {
    	
    		frame.removeWindowListener(windowListeners[i]);
    		
    	}
    	
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
