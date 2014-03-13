import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class PuzzleGUI extends JFrame {
	FileInput boardPieces = new FileInput();
	private static final long serialVersionUID = 1L;
	private static JPanel p1, p2;							//Created 2 panels one for the reset, time, and bombs display and the other being the board
	private JButton[] grid = new JButton[8];;
	private static JMenuBar menu = new JMenuBar();			//Menu Bar to display certain options
	private JMenu m1 = new JMenu("Game");					//Menu that contains 3 sub items
	private JMenuItem exit = new JMenuItem("Exit");			//Sub MenuItem to menu m1
	private JMenuItem Reset = new JMenuItem("Reset");
	private JMenuItem Help = new JMenuItem("Help");			//Sub MenuItem to menu m2
	private JMenuItem About = new JMenuItem("About");
	
	public PuzzleGUI () {
		super("Sliding Puzzle");
		int start, end, height, width;
		//BuildBoard newBoard = new BuildBoard();
		p1 = new JPanel();
		p2 = new JPanel();
		
		menu.add(m1);
		m1.add(exit);
		m1.add(Reset);
		m1.add(Help);
		m1.add(About);
		
		p2.setLayout(null);
		for(int i =7;i>=0;i--){
			start = boardPieces.blockStart(i)*75;
			end = boardPieces.blockEnd(i)*75;
			height = boardPieces.blockLength(i)*75;
			width = boardPieces.blockWidth(i)*75;

			grid[i] = new JButton(""+(i+1));
			grid[i].setBackground(Color.LIGHT_GRAY);
			grid[i].setBounds(start,end,height,width);
			p2.add(grid[i]);
		}
	}
	
	public static void main(String[] args){
		PuzzleGUI  GUI = new PuzzleGUI();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();	//gets default location
		int x = (int) ((dimension.getWidth() - 500) / 2);
		int y = (int) ((dimension.getHeight() - 500) / 2);
		GUI.setLocation(x,y);
		GUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	
		GUI.setJMenuBar(menu);
		GUI.add(p1, BorderLayout.NORTH);								//Adds the panels to the JFrame
		GUI.add(p2, BorderLayout.CENTER);
		GUI.setSize(500, 500);										//Sets the size for the JFrame
		GUI.setVisible(true);											//Sets the JFrame to be visible and
		GUI.setResizable(true);
	}
	
}