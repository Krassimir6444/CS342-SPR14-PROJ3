package classes;

import java.util.*;

public class PuzzleSolverV2
{
	private Move hint = null;	
	private Snapshot solution = null;				
	private ArrayList<Snapshot> queue = new ArrayList<Snapshot>();		// Snapshots to process
	private HashSet<Integer> prevConfigs;	// Previous configurations of the board
	private int rows;
	private int columns;
	private int movesNeeded = 0;
	private ArrayList<Pieces> blocks;

	public PuzzleSolverV2(Pieces[] blocks, int numBlocks, int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		Snapshot FirstSnap = new Snapshot(blocks, numBlocks);
		prevConfigs = new HashSet<Integer>();
		queue.add(FirstSnap);
		addToPrevConfig(FirstSnap.boardSnap());
		
		while(!queue.isEmpty()){
			FirstSnap = queue.get(0);
			queue.remove(0);
			Pieces[] updatedPieces = FirstSnap.piecesSnap();
			
			if(foundSolution(FirstSnap)){
				solution = FirstSnap;
				hint = FirstSnap.moves.get(0);
				break;
			}
			
			for(int i=0;i<numBlocks;i++){
				char movement = updatedPieces[i].mobility;
				
				switch(movement){
					case 'h':
						Horizontal(FirstSnap, i);
						break;
					case 'v':
						Vertical(FirstSnap, i);
						break;
					case 'b':
						Horizontal(FirstSnap, i);
						Vertical(FirstSnap, i);
						break;
					default:
						break;
				}
			}
		}
		
		if (solution == null)
			System.out.println("Puzzle has no solution");
	}
	
	public void Horizontal(Snapshot temp, int specificBlock){
		Pieces[] tempPiece = temp.piecesSnap();
		int x = tempPiece[specificBlock].x;
		int y = tempPiece[specificBlock].y;
		int width = tempPiece[specificBlock].width;
		int height = tempPiece[specificBlock].height;
		int id = tempPiece[specificBlock].id;
		char mobility = tempPiece[specificBlock].mobility;
		boolean Right = tempPiece[specificBlock].moveRight(temp.boardSnap());
		boolean Left = tempPiece[specificBlock].moveLeft(temp.boardSnap());
		
		if(Right){
			System.out.println("Right!");
			Pieces movedPiece = new Pieces(x,y+1,width,height,id,mobility);
			tempPiece[specificBlock] = movedPiece;
			Snapshot newRightSnap = new Snapshot(tempPiece, tempPiece.length);
			
			if (addToPrevConfig(newRightSnap.board) == true){
				System.out.println("Added to Q");
				queue.add(newRightSnap);
			}
		}
		if(Left){
			System.out.println("Left!");
			Pieces movedPiece = new Pieces(x,y-1,width,height,id,mobility);
			tempPiece[specificBlock] = movedPiece;
			Snapshot newLeftSnap = new Snapshot(tempPiece, tempPiece.length);
			
			if (addToPrevConfig(newLeftSnap.board) == true){
				System.out.println("Added to Q");
				queue.add(newLeftSnap);
			}
		}
	}
	
	public void Vertical(Snapshot temp, int specificBlock){
		Pieces[] tempPiece = temp.piecesSnap();
		int x = tempPiece[specificBlock].x;
		int y = tempPiece[specificBlock].y;
		int width = tempPiece[specificBlock].width;
		int height = tempPiece[specificBlock].height;
		int id = tempPiece[specificBlock].id;
		char mobility = tempPiece[specificBlock].mobility;
		boolean Up = tempPiece[specificBlock].moveUp(temp.boardSnap());
		boolean Down = tempPiece[specificBlock].moveDown(temp.boardSnap());
		
		if(Up){
			System.out.println("Right!");
			Pieces movedPiece = new Pieces(x-1,y,width,height,id,mobility);
			tempPiece[specificBlock] = movedPiece;
			Snapshot newUpSnap = new Snapshot(tempPiece, tempPiece.length);
			
			if (addToPrevConfig(newUpSnap.board) == true){
				System.out.println("Added to Q");
				queue.add(newUpSnap);
			}
		}
		if(Down){
			System.out.println("Left!");
			Pieces movedPiece = new Pieces(x+1,y,width,height,id,mobility);
			tempPiece[specificBlock] = movedPiece;
			Snapshot newDownSnap = new Snapshot(tempPiece, tempPiece.length);
			
			if (addToPrevConfig(newDownSnap.board) == true){
				System.out.println("Added to Q");
				queue.add(newDownSnap);
			}
		}
	}
	
	private class Snapshot
	{
		private int board[][];			// Current board
		public ArrayList<Move> moves = new ArrayList<Move>(movesNeeded);	// Moves it took to get this board
		private Pieces[] snapshotPieces;
		
		public Snapshot(Pieces[] blocks, int numBlocks){
			this.board = createBoard(blocks, numBlocks);
			this.snapshotPieces = blocks;
			printBoard(board);
			
			/*if(movesNeeded !=0){
				Move temp = new Move()
			}
			
			for (int i=0; i<movesNeeded; i++)
			{
				Move temp = new Move(moves.get(i));
				this.moves.add(temp);
			}*/
			movesNeeded++;
			
		}
		public int[][] boardSnap(){
			return this.board;
		}
		public Pieces[] piecesSnap(){
			return this.snapshotPieces;
		}
	}
	
	public int[][] createBoard(Pieces[] blocks, int numBlocks){
		int [][] temp = new int[rows][columns];
		
		for(int i=0;i<numBlocks;i++){
			int x = blocks[i].x;
			int y = blocks[i].y;
			int height = blocks[i].height; 
			int width = blocks[i].width;
			int id = blocks[i].id;
			
			for(int j=0;j<height;j++){
				for(int k=0;k<width;k++){
					temp[x+j][y+k] = id;
				}
			}
		}
		return temp;
	}
	
	public void printBoard(int board[][]){
		for(int j=0;j<rows;j++){
			for(int k=0;k<columns;k++){
				System.out.printf("%d ", board[j][k]);
			}
			System.out.printf("\n");
		}
	}
	
	private class Move
	{
		public char direction;	
		public int value;		// -1 for left/down, +1 for right/up
		public int blockID;		// Which block was moved

		public Move(char direction, int value, int ID)
		{
			this.direction = direction;
			this.value = value;
			this.blockID = ID;
		}

		// Constructor that creates a copy of the move parameter
		public Move (Move m)
		{
			this(m.direction, m.value, m.blockID);
		}
	}
	
	public boolean addToPrevConfig(int key[][]){	
		int s = 0;	// Convert board to a string

		for (int i=0; i<rows; i++)
			for (int j = 0; j < columns; j++)
				if(key[i][j] != 0){
					s += (i*j);
				}

		// Hashsets do not allow duplicate values - duplicate strings would not be added (false returned)
		boolean ret = prevConfigs.add(s);
		return ret;
	}

	public boolean foundSolution(Snapshot temp){
		int [][] tempBoard = temp.boardSnap();
		
		for(int i=0;i<rows;i++){
				if(tempBoard[i][columns-1] == 8){
					return true;
				}
		}
		return false;
	}
}