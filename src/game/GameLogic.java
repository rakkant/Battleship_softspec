package game;

import java.util.ArrayList;

public class GameLogic {

	private static GameLogic gameLogic;

	private Board b1, b2;
	private ArrayList<Ship> shipLists1, shipLists2;
	private Board[] boardList;
	private ArrayList[] allPlayer;

	private static int turn = 0;
	private static boolean checkCreateServer;

	private int sizeBoard = 68;

	private GameLogic(String serverOrClient){
		shipLists2 = new ArrayList<>();
		shipLists1 = new ArrayList<>();
		allPlayer = new ArrayList[] {shipLists1, shipLists2};
		b1 = new Board(7, 8);
		b2 = new Board(7, 8);
		boardList = new Board[] {b1, b2};
		System.out.println("Create server");
		if(serverOrClient.equals("server")){
			checkCreateServer = true;
		}
	}

	public static GameLogic getInstance(String serverOrClient){
		if(gameLogic == null)
			gameLogic = new GameLogic(serverOrClient);
		else {
			turn = 1;
			System.out.println("Create client");
		}	
		return gameLogic;
	}

	public static boolean checkServerIsCreate(){
		if(checkCreateServer)
			return true;
		return false;
	}

	public void start() {
		this.turn = 0;
	}

	public void addShip(Ship s){
		((ArrayList<Ship>) allPlayer[turn%2]).add(s);
	}

	public void addShipToBoard(){
		for(Ship s : (ArrayList<Ship>) allPlayer[turn%2]){
			boardList[turn%2].addShip(s.getBoardPosX(), s.getBoardPosY(), s.getSizeBoatX(), s.getSizeBoatY());
		}
	}

	public void move(int x, int y, int mouseX, int mouseY){
		for(Ship s : (ArrayList<Ship>) allPlayer[turn%2]){
			if(s.isClick())
				s.move(mouseX - x,mouseY- y);
		}
	}

	public void magnetShip(){
		for(Ship s : (ArrayList<Ship>) allPlayer[turn%2]){
			if(s.isClick()){
				int startPointX = 48, startPointY = 54;
				boolean checkInField = false;
				for(int i=0; i <= 8 - s.getSizeBoatX() && !checkInField; i++){
					for(int j=0; j <= 7 - s.getSizeBoatY() && !checkInField; j++){
						if(s.getX() >= startPointX && s.getY() >= startPointY && s.getX() < startPointX+sizeBoard && s.getY() < startPointY+sizeBoard){
							s.setMagnet(startPointX, startPointY, i, j);
							System.out.println(s.getBoardPosX() + " : " + s.getBoardPosY() + " size : " + s.getSizeBoatX() + "," + s.getSizeBoatY());
							checkInField = true;
						}
						startPointY += sizeBoard;
					}
					startPointY = 54;
					startPointX += sizeBoard;
				}
				if(!checkInField) s.setStartPosition();
			}
			s.setClick(false);
		}
	}

	public void setClick(int mouseX, int mouseY){
		for(Ship s : (ArrayList<Ship>) allPlayer[turn%2]){
			if(!s.isClick())
				if(s.checkClick(mouseX, mouseY))
					break;
		}
	}

	public void shoot(int x, int y){
		boardList[turn%2].destroy(x, y);
		turn++;
		//		b.destroy(x, y);
		//		System.out.println("Shoot missle at : "+ x + " ," + y);
	}

	public boolean checkAllShipInField(){
		for(Ship s : (ArrayList<Ship>) allPlayer[turn%2]){
			if(!s.isInField())
				return false;
		}
		return true;
	}

	public Board getB() {
		return boardList[turn%2];
	}

	public ArrayList<Ship> getShipLists() {
		return (ArrayList<Ship>) allPlayer[turn%2];
	}

}
