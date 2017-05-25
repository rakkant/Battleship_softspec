package game;
/**
 * Created by nune on 5/20/2017 AD.
 */
public class Board {
	private int square[][];

	public Board(int sizeX, int sizeY){
		square = new int [sizeX][sizeY];
		for (int i = 0; i < sizeX ; i++)
			for (int j = 0; j < sizeY ; j++)
				square[i][j] = 0;
	}

	public void addShip(int x, int y, int sizeX, int sizeY){
		System.out.println("X : " + x + "Y : "+ y + " sizeX : " + sizeX + " sizeY : " + sizeY);
		for(int j = y; j < y+sizeY; j++){
			for(int i = x; i < x+sizeX; i++){
				square[j][i] = 1;
			}
		}
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
}
