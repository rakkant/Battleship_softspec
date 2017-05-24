package game;
import java.util.ArrayList;

import javax.swing.JButton;

import processing.core.PApplet;
import processing.core.PImage;
import network.GameClient;
import network.GameServer;
import network.Network;

public class Game extends PApplet {

	// not used yet
//	private GameServer gameServer;
//	private GameClient gameClient;
//	private boolean isServer;
//	private boolean isClient;
//	private JButton startServerButton;
//	private JButton startClientButton;
	
	PImage bg1, bg2, canoe, boat, ferrari;
	int x, y;
	
	
	ArrayList<Ship> ships = new ArrayList<Ship>();

	public static void main(String [] args){
		PApplet.main("Game");
	}
	


	public void settings(){
		size(640,800);
		
		bg1 = loadImage("image/Bg.jpg");
		boat = loadImage("image/ship.png");
		canoe = loadImage("image/ship2.png");
		ferrari = loadImage("image/ship3.png");
		
		ships.add(createShip(55, 590, boat));
		ships.add(createShip(55, 680, boat));
		ships.add(createShip(205, 600, canoe));
		ships.add(createShip(295, 600, canoe));
		ships.add(createShip(390, 600, ferrari));

//		bg2 = loadImage("image/Bg2.jpg");
	}
	
	public Ship createShip(int x, int y, PImage img){
		return new Ship(x, y, img);
	}

	public void setup(){
		image(bg1,0,0);
		
		for(Ship s : ships){
			image(s.getImage(),s.getX(),s.getY());
		}
	}
	
	@Override
	public void mouseDragged() {
		moveBoat();
		super.mouseDragged();
	}
	
	@Override
	public void mousePressed() {
		x  = mouseX;
		y = mouseY;
		super.mousePressed();
	}
	
	@Override
	public void draw() {
		image(bg1,0,0);
		for(Ship s : ships){
			image(s.getImage(), s.getX(), s.getY());
		}
	}
	
	public void moveBoat(){
		for(Ship s : ships){
			if(s.getX() + s.getImage().getModifiedX2() >= mouseX && s.getY()+ s.getImage().getModifiedY2() >= mouseY){
				s.setX(s.getX() + (mouseX - x));
				s.setY(s.getY() + (mouseY - y));
				x = mouseX;
				y = mouseY;
			}
		}
	}
}
