package resources;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Piece extends JButton { 
	private static final long serialVersionUID = 1L;
	
	private boolean Z = false;
	private int width;
	private int height;
	
	public Piece(int w, int h, int x, int y) {
		super();
		try {
		  Image icon = ImageIO.read(getClass().getResource("wood.gif"));
		  this.setIcon(new ImageIcon(icon));
		} catch (IOException ex) { }
		width = w;
		height = h;
		this.setPreferredSize(new Dimension(width,height));
		this.setVisible(true);
	}
	
	public void setTargetPiece() {
		this.Z = true;
	}

}