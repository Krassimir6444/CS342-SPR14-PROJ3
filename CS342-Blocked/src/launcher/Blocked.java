package launcher;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
//import resources.*;

public class Blocked extends JFrame{
	private static JPanel p1, p2;
	private static JMenuBar menu = new JMenuBar();			//Menu Bar to display certain options
	private final JMenu m1 = new JMenu("Game");					//Menu that contains 3 sub items
	private final JMenuItem exit = new JMenuItem("Exit");			//Sub MenuItem to menu m1
	private final JMenuItem Help = new JMenuItem("Help");			//Sub MenuItem to menu m2
	private final JMenuItem About = new JMenuItem("About");
	
	public Blocked() {
		menu.add(m1);										//Adds the first drop down menu
		m1.add(About);										//Adds sub menus to the drop down menu
		m1.add(Help);
		m1.add(exit);
		
		
	}
	
	public static void main (String[] args) {
		Blocked mainBoard = new Blocked();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();	//gets default location
		int x = (int) ((dimension.getWidth() - 500) / 2);
		int y = (int) ((dimension.getHeight() - 500) / 2);

		mainBoard.setLocation(x, y);										//Sets location for the board
		mainBoard.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	//General exit operation
		mainBoard.setJMenuBar(menu);
		mainBoard.setSize(500, 500);										//Sets the size for the JFrame
		mainBoard.setVisible(true);											//Sets the JFrame to be visible and
		mainBoard.setResizable(true);
	}
	
}