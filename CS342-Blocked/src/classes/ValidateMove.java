package classes;

public class ValidateMove {
	private int[][] board;
	
	
	public ValidateMove(){
		BuildBoard newBoard = new BuildBoard();
		board = newBoard.returnPuzzle();
	}
}
