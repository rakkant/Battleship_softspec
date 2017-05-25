package game;
import java.util.ArrayList;

import javax.swing.JButton;

import processing.core.PApplet;
import processing.core.PImage;
import network.GameClient;
import network.GameServer;
import network.Network;

public class Game extends PApplet {

	private PImage bg, canoe, boat, ferrari,readyBtn;
	private int x, y, sizeBoard = 68;
	private boolean readyState = false;

	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private Board b;
	
	public void start(){
		PApplet.main("game.Game");
	}

	public void settings(){
		size(640,800);
		b  = new Board(7,8);
	}

	public Ship createShip(int x, int y, PImage img){
		return new Ship(x, y, img);
	}

	public void setup(){

		bg = loadImage("image/Bg.jpg");
		boat = loadImage("image/ship.png");
		canoe = loadImage("image/ship2.png");
		ferrari = loadImage("image/ship3.png");
		readyBtn = loadImage("image/readyBtn.jpg");
		image(bg,0,0);

		ships.add(createShip(55, 590, boat));
		ships.add(createShip(55, 680, boat));
		ships.add(createShip(205, 600, canoe));
		ships.add(createShip(295, 600, canoe));
		ships.add(createShip(390, 600, ferrari));

		for(Ship s : ships){
			image(s.getImage(),s.getX(),s.getY());
		}
	}

	@Override
	public void mouseDragged() {
		for(Ship s : ships){
			if(s.isClick()){
				moveBoat(s);
			}
		}
		x = mouseX;
		y = mouseY;
		super.mouseDragged();
	}

	@Override
	public void mousePressed() {
		x = mouseX;
		y = mouseY;
		if ( !readyState ){
			for(Ship s : ships){
				if(!s.isClick()){
					if(s.checkClick(mouseX, mouseY)){
						break;
					}
				}
			}
			readyBtnAction("click");
			super.mousePressed();
		}
	}

	public void printStatus(){
		for(Ship s: ships){
			System.out.print(s.isClick() + " ");
		}
		System.out.println();
	}

	@Override
	public void mouseClicked() {
		readyBtnAction("click");

		super.mouseClicked();
	}

	@Override
	public void mouseReleased() {
		for(Ship s : ships){
			if(s.isClick()){
				magnetShip(s);
			}
			s.setClick(false);
		}
		readyBtnAction("release");
		if ( checkAllInField() && x >= 50 && x <= 544 && y >=600 && y <= 738){
			readyState = true;
			addShipToBoard();
		}
		super.mouseReleased();
	}
	
	public void addShipToBoard(){
		for(Ship s : ships){
			b.addShip(s.getBoardPosX(), s.getBoardPosY(), s.getSizeBoatX(), s.getSizeBoatY());
		}
		System.out.println(b.toString());
	}

	public void readyBtnAction(String state){
		if(mouseX <= readyBtn.getModifiedX2() + 50 && mouseY <= readyBtn.getModifiedY2() + 600 && mouseX >= 50 && mouseY >= 600){
			if(state.equals("click")){
				readyBtn = loadImage("image/readyBtn_click.jpg");
			} else if (state.equals("hover")){
				readyBtn = loadImage("image/readyBtn_hover.jpg");
			} else {
				readyBtn = loadImage("image/readyBtn.jpg");
			}
		}
	}

	@Override
	public void draw() {
		if ( !readyState){
			image(bg,0,0);
			for(Ship s : ships){
				image(s.getImage(), s.getX(), s.getY());
			}

			if (checkAllInField())
				image(readyBtn,50,600);
		}
		else {
			bg = loadImage("image/Bg2.jpg");
			image(bg,0,0);
		}
		//		readyBtnAction("hover");
	}


	public void moveBoat(Ship s){
		s.move(mouseX - x,mouseY- y);
	}

	public void magnetShip(Ship s){
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

	public boolean checkAllInField(){
		int count = 0;
		for (Ship s : ships){
			if (s.isInField())
				count++;
		}
		if (count == ships.size())
			return true;
		else return false;
	}

}
