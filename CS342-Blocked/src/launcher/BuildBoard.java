
public class BuildBoard {
	private final int row, column;
	private int[][] puzzleBoard;
	
	public BuildBoard(){
			FileInput newPuzzle = new FileInput();
			row = newPuzzle.boardCol();
			column = newPuzzle.boardRow();
			puzzleBoard = new int[row][column];
			setBoard();
			//printBoard();
			setPieces(newPuzzle);
			//printBoard();
	}
	
	public int rows(){return row;}
	
	public int columns(){return column;}
	
	public int [][] returnPuzzle(){return puzzleBoard;}
	
	public void setBoard(){
		for(int i =0;i<row;i++){
			for(int j = 0;j<column;j++){
				puzzleBoard[i][j] = -1;
			}
		}
	}
	
	public void printBoard(){
		for(int i =0;i<row;i++){
			System.out.println();
			for(int j = 0;j<column;j++){
				System.out.printf("\t%d ", puzzleBoard[i][j]);
			}
			System.out.printf("\n");
		}
		System.out.printf("\n");
	}
	
	public void setPieces(FileInput newPuzzle){
		int start, end, height, width, finalheight, finalWidth;
		for(int i =0;i<8;i++){
			start = newPuzzle.blockStart(i) - 1;
			end = newPuzzle.blockEnd(i) - 1;
			height = newPuzzle.blockLength(i);
			width = newPuzzle.blockWidth(i);

			puzzleBoard[start][end] = i + 1;
			for (int j = 0; j < height; j++) {
				finalheight = j + start;
				for (int k = 0; k < width; k++) {
					finalWidth = k + end;
					if (i == 7) {
						puzzleBoard[finalheight][finalWidth] = 50;
					} else {
						puzzleBoard[finalheight][finalWidth] = i + 1;
					}
				}
			}

		}
	}
}
