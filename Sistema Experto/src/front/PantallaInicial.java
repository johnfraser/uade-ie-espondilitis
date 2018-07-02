package front;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PantallaInicial extends JPanel {
	private static final long serialVersionUID = 1L;
	
	
	private MainFrame frame;
	private Image image;
	public JLabel lbl_titulo;
	public JLabel lbl_titulo_sombra;
	public int current_x = 0;
	public int current_y = 0;
	
	
	public PantallaInicial (MainFrame frame) {
				
		this.frame = frame;
		setLayout(null);
		try {
		image = javax.imageio.ImageIO.read(new java.net.URL(getClass().getResource("/img/intro3.png"), "intro3.png"));
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		//Titulo
		lbl_titulo = new JLabel(MainFrame.APP_NAME);
		lbl_titulo_sombra = new JLabel(MainFrame.APP_NAME);
		Font font = lbl_titulo.getFont();
		int newFontSize = (int)(font.getSize() * 3);
		Font font3 = new Font("SansSerif",  Font.BOLD, 50);
		//lbl_titulo.setFont(new Font(font.getName(), Font.BOLD, newFontSize));
		lbl_titulo.setFont(font3);
		lbl_titulo_sombra.setFont(font3);
		Font font2 = lbl_titulo.getFont();
		int w = lbl_titulo.getFontMetrics(font2).stringWidth(lbl_titulo.getText());
		lbl_titulo.setBounds(MainFrame.APP_WINDOW_X/2 - w/2,MainFrame.APP_WINDOW_Y/2 - 150 / 2,MainFrame.APP_WINDOW_X - w/2 + 215 ,75); //-35
		int sombra_w = 3;
		int sombra_h = 2;
		lbl_titulo_sombra.setBounds(MainFrame.APP_WINDOW_X/2 - w/2 + sombra_w ,MainFrame.APP_WINDOW_Y/2 - 150 / 2 + sombra_h,MainFrame.APP_WINDOW_X - w/2 + 215 + sombra_w ,75 + sombra_h); //-35
		lbl_titulo.setForeground(Color.RED);
		lbl_titulo_sombra.setForeground(Color.DARK_GRAY);
		//lbl_titulo.setBackground(Color.LIGHT_GRAY); 
		//lbl_titulo.setBackground(new Color(224, 224, 224));
		//lbl_titulo.setOpaque(true);
		add(lbl_titulo); //, gridBagConstraints);
		add(lbl_titulo_sombra); //, gridBagConstraints);
	      
	    //this.frame.CerrarPantallaInicial(this);
		
	}
	
	@Override
	  protected void paintComponent(Graphics g)
	  {
	    super.paintComponent(g); 
	    if (image != null)
	      g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);
	  }
	

	
	 
	   /** Returns an ImageIcon, or null if the path was invalid. */
	    protected static ImageIcon createImageIcon(String path) {
	        java.net.URL imgURL = PantallaInicial.class.getResource(path);
	        if (imgURL != null) {
	            return new ImageIcon(imgURL);
	        } else {
	            System.err.println("Couldn't find file: " + path);
	            return null;
	        }
	    }

}
