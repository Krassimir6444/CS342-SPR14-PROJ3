package launcher;

import classes.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JPanel;

public class PuzzleGUI extends JFrame implements MouseMotionListener{
	FileInput boardPieces = new FileInput();
	private static final long serialVersionUID = 1L;
	private char[] mobility = new char[8];
	private static JPanel p1, p2, p3;						
	private JButton[] grid = new JButton[8];
	private Rectangle[] grid2 = new Rectangle[8];
	private static JMenuBar menu = new JMenuBar();			//Menu Bar to display certain options
	private JMenu m1 = new JMenu("Game");					//Menu that contains 3 sub items
	private JMenuItem exit = new JMenuItem("Exit");			//Sub MenuItem to menu m1
	private JMenuItem Help = new JMenuItem("Help");			//Sub MenuItem to menu m2
	private JMenuItem About = new JMenuItem("About");
	private JButton Hint = new JButton("Hint");
	private JButton Solve = new JButton("Solve");
	private JButton Reset = new JButton("Reset");
	private JLabel timer, moves;
	private Timer timeClock;
	private int timeAccumulator, moveCount = 0;
	
	public PuzzleGUI () {
		super("Sliding Block Puzzle");
		int start, end, height, width;
		//BuildBoard newBoard = new BuildBoard();
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();

		menu.add(m1);
		m1.add(exit);
		m1.add(Help);
		m1.add(About);
		
		p2.setLayout(null);
		for(int i =7;i>=0;i--){
			start = boardPieces.blockStart(i)*75;
			end = boardPieces.blockEnd(i)*75;
			height = boardPieces.blockLength(i)*75;
			width = boardPieces.blockWidth(i)*75;
			mobility[i] = boardPieces.blockMobile(i);
			
			if(i == 7){
				grid[i] = new JButton("Z");
				grid[i].addMouseMotionListener(this);
				grid[i].setBackground(Color.LIGHT_GRAY);
				grid[i].setBounds(end, start,width, height);
				grid2[i]= new Rectangle(end, start, width, height);
				p2.add(grid[i]);
			}
			else{
				grid[i] = new JButton(""+(i+1));
				grid[i].addMouseMotionListener(this);
				grid[i].setBackground(Color.LIGHT_GRAY);
				grid[i].setBounds(end, start,width, height);
				grid2[i]= new Rectangle(end, start, width, height);
				p2.add(grid[i]);
			}
			
			try {
				Image icon = ImageIO.read(getClass().getResourceAsStream("/resources/" + "wood.gif"));
			    Image scaledIcon = icon.getScaledInstance(width+50, height, Image.SCALE_FAST);
			    repaint();
			    grid[i].setIcon(new ImageIcon(scaledIcon));
			    grid[i].setText(""+(i+1));
			    grid[i].setHorizontalTextPosition(JButton.CENTER);
			    grid[i].setVerticalTextPosition(JButton.CENTER);
			  } catch(IOException ex){}
			
		}
		
		p3.add(Reset);
		p3.add(Hint);
		p3.add(Solve);
		
		timer = new JLabel("| Timer: " + timeAccumulator + " | ");
		int delay = 1000;
	    timeClock = new Timer(delay,new TimerHandler() );
	    timeClock.start();
		menu.add(timer);
		
		moves = new JLabel(" Moves: " + moveCount + " |");
		menu.add(moves);
		
		implementListeners();
	}
	
	private void implementListeners() {
		// when user clicks alt+g allows for shortcuts mentioned below 
		m1.setMnemonic('g');
		
		exit.setMnemonic('x');
		exit.addActionListener(
				new ActionListener() {
					// terminate application when user clicks exitItem, or alt+x
					public void actionPerformed(ActionEvent event) { System.exit(1); }
			    }
		);
		Help.setMnemonic('p');
		Help.addActionListener(
				new ActionListener() {
					// displays help when user clicks HelpItem, or alt+p
					public void actionPerformed(ActionEvent event) {
						JOptionPane.showMessageDialog( PuzzleGUI.this,
			    				  "For more information about Blocked go to:\n"
			    				  + "http://en.wikipedia.org/wiki/Sliding_puzzle", 
			    				  "About", JOptionPane.PLAIN_MESSAGE );
					}
			    }
		);
		About.setMnemonic('a');
		About.addActionListener(
				new ActionListener() {
					// displays about information when user clicks AboutItem, or alt+a
					public void actionPerformed(ActionEvent event) {
						JOptionPane.showMessageDialog( PuzzleGUI.this,
			    				  "Developers:\nAntonio Villarreal & Krassimir Manolov", 
			    				  "About", JOptionPane.PLAIN_MESSAGE );
					}
			    }
		);
		Reset.setMnemonic('r');
		Reset.addActionListener(
				new ActionListener() {
					// reinitializes game when user clicks resetItem, or alt+r
					public void actionPerformed(ActionEvent event) { 
						//TODO: implement functionality
					}
			    }
		);
		Hint.setMnemonic('h');
		Hint.addActionListener(
				new ActionListener() {
					// reinitializes game when user clicks resetItem, or alt+h
					public void actionPerformed(ActionEvent event) { 
						//TODO: implement functionality
					}
			    }
		);
		Solve.setMnemonic('s');
		Solve.addActionListener(
				new ActionListener() {
					// reinitializes game when user clicks resetItem, or alt+s
					public void actionPerformed(ActionEvent event) { 
						//TODO: implement functionality
					}
			    }
		);
	}
	
	private class TimerHandler implements ActionListener {
		// accumulates time between events (every second)
	    public void actionPerformed(ActionEvent event) {
	    	// very weird but works, change if see fit
	    	timeAccumulator += timeClock.getDelay() / 1000;
	    	timer.setText("| Timer: " + timeAccumulator + " | ");
	    }
	}
	
	public void stopTimer() {
		// stops the timer when necessary
		timeClock.stop();
	}
	
	public void addMove() {
		moveCount++;
		moves.setText(" Moves: " + moveCount + " |");
	}
	
	public boolean isCollision(Rectangle r1, int x){
		for(int i=0;i<8;i++){
			if(grid2[i].intersects(r1) && i != x){
				return false;
			}
		}
		return true;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		int nX, nY, x, y, finalX, finalY;

		Rectangle temp;
		for(int i = 0;i<8;i++){
			if (e.getSource() == grid[i]) {
				temp = grid2[i];
				
				x = e.getX();
				y = e.getY();
				nX = grid[i].getX();
				nY = grid[i].getY();
				
				finalX = x - nX;
				finalY = y - nY;
				if(finalX > 75 && mobility[i] == 'b' ){
					temp.setLocation(nX+75,nY);
					if(isCollision(temp, i)){
						grid[i].setLocation(nX + 75, nY);
						grid2[i].setLocation(nX + 75, nY);
						addMove();
					}
				}
				if(finalX < -75 && mobility[i] == 'b' ){
					temp.setLocation(nX-75,nY);
					if(isCollision(temp, i)){
						grid[i].setLocation(nX - 75, nY);
						grid2[i].setLocation(nX - 75, nY);
						addMove();
					}
				}
				if(finalY > 75 && mobility[i] == 'b'){
					temp.setLocation(nX,nY+75);
					if(isCollision(temp, i)){
						grid[i].setLocation(nX, nY+75);
						grid2[i].setLocation(nX, nY+75);
						addMove();
					}
				}
				if(finalY < -75 && mobility[i] == 'b'){
					temp.setLocation(nX,nY-75);
					if(isCollision(temp, i)){
						grid[i].setLocation(nX, nY-75);
						grid2[i].setLocation(nX, nY-75);
						addMove();
					}
				}
			}
		}
	}

	public void mouseMoved(MouseEvent e) {}
	
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
		GUI.add(p3, BorderLayout.SOUTH);
		GUI.setSize(500, 500);										//Sets the size for the JFrame
		GUI.setVisible(true);											//Sets the JFrame to be visible and
		GUI.setResizable(true);
	}

}