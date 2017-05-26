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

	private final int SIZE_BOARD = 68;

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

	public void addShip(Ship s, String status){
		int turn = (status.equals("server")) ? 0 : 1;
		((ArrayList<Ship>) allPlayer[turn]).add(s);
	}

	public void addShipToBoard(String status){
		System.out.println("Add ship to " + status);
		int turn = (status.equals("server")) ? 0 : 1;
		for(Ship s : (ArrayList<Ship>) allPlayer[turn]){
			boardList[turn].addShip(s.getBoardPosX(), s.getBoardPosY(), s.getSizeBoatX(), s.getSizeBoatY());
		}
	}

	public void move(int x, int y, int mouseX, int mouseY, String status){
		int turn = (status.equals("server")) ? 0 : 1;
		for(Ship s : (ArrayList<Ship>) allPlayer[turn]){
			if(s.isClick())
				s.move(mouseX - x,mouseY- y);
		}
	}

	public void magnetShip(String status){
		int turn = (status.equals("server")) ? 0 : 1;
		for(Ship s : (ArrayList<Ship>) allPlayer[turn]){
			if(s.isClick()){
				int startPointX = 48, startPointY = 54;
				boolean checkInField = false;
				for(int i=0; i <= 8 - s.getSizeBoatX() && !checkInField; i++){
					for(int j=0; j <= 7 - s.getSizeBoatY() && !checkInField; j++){
						if(s.getX() >= startPointX && s.getY() >= startPointY && s.getX() < startPointX+ SIZE_BOARD && s.getY() < startPointY+ SIZE_BOARD){
							s.setMagnet(startPointX, startPointY, i, j);
							System.out.println(s.getBoardPosX() + " : " + s.getBoardPosY() + " size : " + s.getSizeBoatX() + "," + s.getSizeBoatY());
							checkInField = true;
						}
						startPointY += SIZE_BOARD;
					}
					startPointY = 54;
					startPointX += SIZE_BOARD;
				}
				if(!checkInField) s.setStartPosition();
			}
			s.setClick(false);
		}
	}

	public void setClick(int mouseX, int mouseY, String status){
		System.out.println("set click at " + status);
		int turn = (status.equals("server")) ? 0 : 1;
		for(Ship s : (ArrayList<Ship>) allPlayer[turn]){
			if(!s.isClick())
				if(s.checkClick(mouseX, mouseY))
					break;
		}
	}

	public int[] checkPositionShoot(int mouseX, int mouseY){
		int posX = 48, posY = 54;
		for(int i = 0; i <= 7; i++){
			for(int j = 0; j <= 8; j++){
				if(mouseX >= posX && mouseY >= posY && mouseX < posX + SIZE_BOARD && mouseY < posY + SIZE_BOARD){
					return new int[] {j,i};
				}
				posY += SIZE_BOARD;
			}
			posY = 54;
			posX += SIZE_BOARD;
		}
		return null;
	}

	public boolean shoot(int[] position, String status){
		int turn = (status.equals("server")) ? 1 : 0;
		if(position != null){
			return boardList[turn].destroy(position[0], position[1]);
		} else
			System.out.println("Miss");
		turn++;
		return false;
		//		b.destroy(x, y);
		//		System.out.println("Shoot missle at : "+ x + " ," + y);
	}


	public boolean checkAllShipInField(String status){
		int turn = (status.equals("server")) ? 0 : 1;
		for(Ship s : (ArrayList<Ship>) allPlayer[turn]){
			if(!s.isInField())
				return false;
		}
		return true;
	}

	public Board getB(String status) {
		int turn = (status.equals("server")) ? 0 : 1;
		return boardList[turn];
	}

	public ArrayList<Ship> getShipLists(String status) {
		int turn = (status.equals("server")) ? 0 : 1;
		return (ArrayList<Ship>) allPlayer[turn];
	}

}
