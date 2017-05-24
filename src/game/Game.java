package game;
import java.util.ArrayList;

import javax.swing.JButton;

import processing.core.PApplet;
import processing.core.PImage;
import network.GameClient;
import network.GameServer;
import network.Network;

public class Game extends PApplet {
	
	PImage bg1, bg2, canoe, boat, ferrari;
	int x, y, sizeBoard = 68;

	ArrayList<Ship> ships = new ArrayList<Ship>();

	public static void main(String [] args){
		PApplet.main("game.Game");
	}
	


	public void settings(){
		size(640,800);
		//		bg2 = loadImage("image/Bg2.jpg");
	}

	public Ship createShip(int x, int y, PImage img){
		return new Ship(x, y, img);
	}

	public void setup(){
		
		bg1 = loadImage("image/Bg.jpg");
		boat = loadImage("image/ship.png");
		canoe = loadImage("image/ship2.png");
		ferrari = loadImage("image/ship3.png");
		image(bg1,0,0);
		
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
		for(Ship s : ships){
			if(!s.isClick()){
				if(s.checkClick(mouseX, mouseY)){
					break;
				}
			}
		}
		printStatus();
		super.mousePressed();
	}

	public void printStatus(){
		for(Ship s: ships){
			System.out.print(s.isClick() + " ");
		}
		System.out.println();
	}

	@Override
	public void mouseReleased() {
		for(Ship s : ships){
			if(s.isClick()){
				magnetShip(s);
			}
			s.setClick(false);
		}

		printStatus();
		super.mouseReleased();
	}

	@Override
	public void draw() {
		image(bg1,0,0);
		for(Ship s : ships){
			image(s.getImage(), s.getX(), s.getY());
		}
	}

	public void moveBoat(Ship s){
		s.move(mouseX - x,mouseY- y);
	}
	
	public void magnetShip(Ship s){
		int startPointX = 48, startPointY = 54;
		System.out.println("Y : " + s.getSizeBoatY() + " X : " + s.getSizeBoatX());
		for(int i=0; i <= 7 - s.getSizeBoatX(); i++){
			for(int j=0; j <= 7 - s.getSizeBoatY(); j++){
				if(s.getX() >= startPointX && s.getY() >= startPointY && s.getX() < startPointX+sizeBoard && s.getY() < startPointY+sizeBoard){
					s.setMagnet(startPointX, startPointY);
					break;
				}
				startPointY += sizeBoard;
			}
			startPointY = 54;
			startPointX += sizeBoard;
		}
	}
}
