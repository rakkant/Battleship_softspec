package game;
/**
 * Created by nune on 5/20/2017 AD.
 */
public class Board {
	private int square[][];
	private int countShip = 0;

	public Board(int sizeX, int sizeY){
		square = new int [sizeX][sizeY];
		for (int i = 0; i < sizeX ; i++)
			for (int j = 0; j < sizeY ; j++)
				square[i][j] = 0;
	}

	public void addShip(int x, int y, int sizeX, int sizeY){
		countShip += sizeX*sizeY;
		for(int i = x; i < x+sizeX; i++){
		for(int j = y; j < y+sizeY; j++){
				square[j][i] = 1;
			}
		}
	}

	public boolean destroy(int x, int y){
		if(checkShip(x, y)){
			square[x][y] = -1;
			countShip -= 1;
			return true;
		}
		square[x][y] = -2;
		System.out.println("Destroy at : " + x + ", " + y);
		return false;
	}

	public boolean checkShip(int x, int y){
		if(square[x][y] == 1)
			return true;
		return false;
	}

	public boolean isLose(){
		if(countShip == 0)
			return true;
		return false;
	}

	public String toString(){
		String board = "";
		for(int i = 0; i < square.length; i++){
			for(int j = 0; j < square[i].length; j++){
				if(square[i][j] == 1)
					board += "T ";
				else if (square[i][j] == -1)
					board += "F ";
				else 
					board += "0 ";
			}
			board += "\n";
		}
		System.out.println();
		return board;
	}

	public int[][] getSquare() {
		return square;
	}

	public void setSquare(int[][] square) {
		this.square = square;
	}

	public int getCountShip() {
		return countShip;
	}

	public void setCountShip(int countShip) {
		this.countShip = countShip;
	}

}
