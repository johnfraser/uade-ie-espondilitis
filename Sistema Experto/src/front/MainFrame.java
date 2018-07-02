package front;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import clips.ClipsHandler;
import model.Consulta;
import model.Diagnostico;

public class MainFrame extends JFrame{
	public static final String APP_VERSION = "1.0"; 
	public static final String APP_NAME = "Sistema de Diagnóstico"; 
	
	public static final boolean MOSTRAR_INTRO = true;
	
	public static final int alto_controles = 20;
	public static final int ancho_controles = 500;
	public static final int padding_controles = 50;
	public static final int padding_radio = 250;
	
	public static final int APP_WINDOW_X = 640;
	public static final int APP_WINDOW_Y = 720;
	
	//public static final int BOTON_SIGUIENTE_X = APP_WINDOW_X / 2;
	public static final int BOTON_SIGUIENTE_X = MainFrame.APP_WINDOW_X / 2 - ( MainFrame.BOTONES_ANCHO / 2) + MainFrame.BOTONES_ANCHO;
	public static final int BOTON_SIGUIENTE_Y = APP_WINDOW_Y - alto_controles * 5;
	public static final String BOTON_SIGUIENTE_TEXTO = "Siguiente > ";
	
	public static final int BOTON_ATRAS_X = MainFrame.APP_WINDOW_X / 2 - ( MainFrame.BOTONES_ANCHO / 2) - MainFrame.BOTONES_ANCHO;
	public static final int BOTON_ATRAS_Y = APP_WINDOW_Y - alto_controles * 5;
	public static final String BOTON_ATRAS_TEXTO = " < Atrás";
	
	public static final int BOTON_PROCESAR_X = MainFrame.APP_WINDOW_X / 2 - ( MainFrame.BOTONES_ANCHO / 2) + MainFrame.BOTONES_ANCHO;
	public static final int BOTON_PROCESAR_Y = APP_WINDOW_Y - alto_controles * 5;
	public static final String BOTON_PROCESAR_TEXTO = "Procesar";
	public static final Color BOTON_PROCESAR_COLOR = Color.black;
	
	public static final int BOTON_FINALIZAR_X = MainFrame.APP_WINDOW_X / 2 - ( MainFrame.BOTONES_ANCHO / 2);
	public static final int BOTON_FINALIZAR_Y = APP_WINDOW_Y - alto_controles * 5;
	public static final String BOTON_FINALIZAR_TEXTO = "Finalizar";
	public static final Color BOTON_FINALIZAR_COLOR = Color.black;

	public static final int BOTON_VOLVER_X = MainFrame.APP_WINDOW_X / 2 - ( MainFrame.BOTONES_ANCHO / 2);
	public static final int BOTON_VOLVER_Y = APP_WINDOW_Y - alto_controles * 5;
	public static final String BOTON_VOLVER_TEXTO = "Volver";
	public static final Color BOTON_VOLVER_COLOR = Color.black;
	
	
	public static final int BOTONES_ANCHO = 150;
	
	private static MainFrame frame;
	
	//private SistemaDiagnostico sistemaDiagnostico;
	private static PantallaInicial pantallaInicial; 
	private static DatosPaciente datosPaciente; 
	private static DatosDolencias datosDolencia1;
	private static DatosDolencias datosDolencia2;
	private static DatosAntecedentes datosAntecedentes;
	private static DatosEstudios datosEstudios;
	private static ResultadoDiagnostico resultadoDiagnostico;
	private static ResultadosAnteriores resultadosAnteriores;
	
	public static Consulta consulta;
	
	private static Timer pantalla_inicio_timer;
	private static int pantalla_inicio_counter = 10;
	private static int pantalla_inicio_delay = 200;
		 
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
    	
    	/*
    	ClipsHandler clips = new ClipsHandler();
		
		Diagnostico diagnostico = clips.correrConsulta(MainFrame.consulta);
    	*/
    	
        //Create and set up the window.
        frame = new MainFrame();
        //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //MostrarDatosPaciente(null);
        
        frame.setResizable(false);
        
        if (MOSTRAR_INTRO) {
        	frame.setUndecorated(true);
        	MostrarPantallaInicial();	
        }else {
        	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        	MostrarDatosPaciente(null);
        }
        
        
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
    
    public static void MostrarPantallaInicial() {
    	
    	removeAllWindowListeners();
    	
    	frame.getContentPane().removeAll();
    	pantallaInicial = new PantallaInicial(frame);
    	pantallaInicial.setVisible(true);
    	frame.getContentPane().add(pantallaInicial);
    	frame.repaint();
    	frame.revalidate();

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	
            	if ( pantalla_inicio_counter == 0) {
            		pantalla_inicio_timer.stop();
            		removeAllWindowListeners();
                	
                	frame.getContentPane().removeAll();
                	pantallaInicial = null;
                	frame.dispose();
                	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                	frame.setUndecorated(false);
                    MostrarDatosPaciente(null);
                    frame.setVisible(true);
                    
            	}else {
            		pantalla_inicio_counter--;
            	}
            	
            	
            }
        };
        pantalla_inicio_timer = new Timer(pantalla_inicio_delay, taskPerformer);
        pantalla_inicio_timer.setInitialDelay(0);
        pantalla_inicio_timer.start();
    	
    	
    	
    			
    	
    }
    
    public static void MostrarDatosPaciente(JPanel panel_anterior) {
    	
    	removeAllWindowListeners();
    	
    	/*
    	if ( panel_anterior != null) {
    		//panel_anterior.setVisible(false);
    	}
    	*/
    	
    	datosDolencia1 = null;
    	datosDolencia2 = null;
    	datosAntecedentes = null;
    	datosEstudios = null;
    	
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
    	
    	datosDolencia1 = null;
    	datosDolencia2 = null;
    	datosAntecedentes = null;
    	datosEstudios = null;
    	
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
    	
    	datosDolencia2 = null;
    	datosAntecedentes = null;
    	datosEstudios = null;
    	
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
    	
    	datosAntecedentes = null;
    	datosEstudios = null;
    	
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
    	
    	datosEstudios = null;
    	
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
	 
	 public static final boolean validarTextoFloat(String text) {
		 
		 if (text.length() == 0) {
			 return true;
		 }
		 
	      try {
	         Float.parseFloat(text);
	         return true;
	      } catch (NumberFormatException e) {
	         return false;
	      }
	  }
    
}
