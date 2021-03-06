package game;

import java.util.ArrayList;

public class GameLogic {

	private static GameLogic gameLogic;

	private Board b1, b2;
	private ArrayList<Ship> shipLists1, shipLists2;
	private Board[] boardList;
	private ArrayList[] allPlayer;
	private int checkReady = 0, checkShip = 0;

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
		if(serverOrClient.equals("server")){
			checkCreateServer = true;
		}
	}

	public static GameLogic getInstance(String serverOrClient){
		if(gameLogic == null)
			gameLogic = new GameLogic(serverOrClient);
		else {
			turn = 1;
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
		if(checkShip != 10){
			int turn = (status.equals("server")) ? 0 : 1;
			((ArrayList<Ship>) allPlayer[turn]).add(s);
			checkShip += 1;
		}
	}

	public void addShipToBoard(String status){
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
			this.turn++;
			return boardList[turn].destroy(position[0], position[1]);
		} 
		return false;
	}

	public boolean checkAllShipInField(String status){
		int turn = (status.equals("server")) ? 0 : 1;
		for(Ship s : (ArrayList<Ship>) allPlayer[turn]){
			if(!s.isInField())
				return false;
		}
		return true;
	}

	public String checkLose(){
		if(boardList[0].isLose())
			return "client";
		if(boardList[1].isLose())
			return "server";
		return "noWin";
	}

	public void ready(){
		this.checkReady += 1;
	}

	public Board getB(String status) {
		int turn = (status.equals("server")) ? 0 : 1;
		return boardList[turn];
	}

	public ArrayList<Ship> getShipLists(String status) {
		int turn = (status.equals("server")) ? 0 : 1;
		return (ArrayList<Ship>) allPlayer[turn];
	}

	public static int getTurn() {
		return turn;
	}

	public int getCheckReady() {
		return checkReady;
	}

	public void setCheckReady(int checkReady) {
		this.checkReady = checkReady;
	}
}
