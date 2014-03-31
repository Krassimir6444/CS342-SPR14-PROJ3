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

	public PuzzleSolverV2(Pieces[] blocks, int numBlocks, int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		Snapshot FirstSnap = new Snapshot(null,blocks, numBlocks, 'a', 0, 0);
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
			Snapshot newRightSnap = new Snapshot(temp, tempPiece, tempPiece.length, 'r', 1, id);
			
			if (addToPrevConfig(newRightSnap.board) == true){
				System.out.println("Added to Q");
				queue.add(newRightSnap);
			}
		}
		if(Left){
			System.out.println("Left!");
			Pieces movedPiece = new Pieces(x,y-1,width,height,id,mobility);
			tempPiece[specificBlock] = movedPiece;
			Snapshot newLeftSnap = new Snapshot(temp, tempPiece, tempPiece.length, 'l', -1, id);
			
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
			System.out.println("Up!");
			Pieces movedPiece = new Pieces(x-1,y,width,height,id,mobility);
			tempPiece[specificBlock] = movedPiece;
			Snapshot newUpSnap = new Snapshot(temp, tempPiece, tempPiece.length, 'u', 1, id);
			
			if (addToPrevConfig(newUpSnap.board) == true){
				System.out.println("Added to Q");
				queue.add(newUpSnap);
			}
		}
		if(Down){
			System.out.println("Down!");
			Pieces movedPiece = new Pieces(x+1,y,width,height,id,mobility);
			tempPiece[specificBlock] = movedPiece;
			Snapshot newDownSnap = new Snapshot(temp, tempPiece, tempPiece.length, 'd', -1, id);
			
			if (addToPrevConfig(newDownSnap.board) == true){
				System.out.println("Added to Q");
				queue.add(newDownSnap);
			}
		}
	}
	
	private class Snapshot
	{
		private int board[][];			// Current board
		public ArrayList<Move> moves = new ArrayList<Move>();	// Moves it took to get this board
		private Pieces[] snapshotPieces;
		
		public Snapshot(Snapshot old, Pieces[] blocks, int numBlocks, char direction, int value, int ID){
			this.board = createBoard(blocks, numBlocks);
			this.snapshotPieces = blocks;
			printBoard(board);
			System.out.println("Moves: "+movesNeeded);
			if(movesNeeded !=0){
				for (int i=0; i<old.moves.size(); i++)
				{
					Move temp = old.moves.get(i);
					this.moves.add(temp);
				}
			
				Move temp = new Move(direction, value, ID);
				this.moves.add(temp);
			}
			else if(movesNeeded==0){
				Move temp = new Move(direction, value, ID);
				this.moves.add(temp);
			}
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
	
	public void printHint(int numBlocks)
	{
		if (solution != null) {
			int id = hint.blockID;

			if (id == 8)
				System.out.print("Hint: Move block Z one spot ");
			else
				// Numbers
				System.out.print("Hint: Move block " + (id + 1) + " one spot ");

			if (hint.direction == 'u') {
				System.out.print("up\n");
			} 
			else if (hint.direction == 'd'){
				System.out.print("down\n");
			}
			else if (hint.direction == 'r') {
				System.out.print("right\n");
			}
			else if (hint.direction == 'l'){
				System.out.print("left\n");
			}
		}
		if (solution == null)
			System.out.println("Puzzle has no solution");
	}

	public void printSolution() {
		if (solution != null) {
			for (int i = 0; i < solution.moves.size(); i++) {
				int id = solution.moves.get(i).blockID;
				System.out.print(i + 1 + ". ");

				if (id == 8)
					System.out.print("Move block Z one spot ");
				else
					// Numbers
					System.out.print("Move block " + id + " one spot ");

				if (hint.direction == 'u') {
					System.out.print("up\n");
				} else if (hint.direction == 'd') {
					System.out.print("down\n");
				} else if (hint.direction == 'r') {
					System.out.print("right\n");
				} else if (hint.direction == 'l') {
					System.out.print("left\n");
				}
			}

			// Print out the solved board
			System.out.println("\nSolved board:");
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++)
					System.out.print(solution.board[i][j] + " ");

				System.out.println();
			}
		}
		
		if (solution == null)
			System.out.println("Puzzle has no solution");
	}
}