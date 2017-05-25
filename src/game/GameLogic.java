package game;

import java.util.ArrayList;

public class GameLogic {
	
	private Board b;
	private ArrayList<Ship> shipLists;
	private int sizeBoard = 68;
	
	public GameLogic(){
		b = new Board(7, 8);
		shipLists = new ArrayList<>();
	}

	public void start() {
		
	}
	
	public void addShip(Ship s){
		shipLists.add(s);
	}
	
	public void addShipToBoard(){
		for(Ship s : shipLists){
			b.addShip(s.getBoardPosX(), s.getBoardPosY(), s.getSizeBoatX(), s.getSizeBoatY());
		}
	}
	
	public void move(int x, int y, int mouseX, int mouseY){
		for(Ship s : shipLists){
			if(s.isClick())
				s.move(mouseX - x,mouseY- y);
		}
	}
	
	public void magnetShip(){
		for(Ship s : shipLists){
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
		for(Ship s : shipLists){
			if(!s.isClick())
				if(s.checkClick(mouseX, mouseY))
					break;
		}
	}
	
	public void shoot(int x, int y){
		b.destroy(x, y);
		System.out.println("Shoot missle at : "+ x + " ," + y);
	}
	
	public boolean checkAllShipInField(){
		for(Ship s : shipLists){
			if(!s.isInField())
				return false;
		}
		return true;
	}

	public Board getB() {
		return b;
	}

	public void setB(Board b) {
		this.b = b;
	}

	public ArrayList<Ship> getShipLists() {
		return shipLists;
	}

	public void setShipLists(ArrayList<Ship> shipLists) {
		this.shipLists = shipLists;
	}

}
