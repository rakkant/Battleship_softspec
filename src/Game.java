import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Game extends PApplet {
	PImage bg1;
	PImage bg2;
	PImage canoe,canoe2;
	PImage boat,boat2;
	PImage ferrari;
	int valueX = 55 , valueY = 590, x, y;
	Ship ship;
	
	ArrayList<Ship> ships = new ArrayList<Ship>();

	public static void main(String [] args){
		PApplet.main("Game");
	}

	public void settings(){
		size(640,800);
		

		boat = loadImage("image/ship.png");
		ship = new Ship(valueX,valueY, boat);
	
		bg1 = loadImage("image/Bg.jpg");
//		bg2 = loadImage("image/Bg2.jpg");
//		canoe = loadImage("image/ship2.png");
//		boat2 = loadImage("image/ship.png");
//		canoe2 = loadImage("image/ship2.png");
//		ferrari = loadImage("image/ship3.png");
	}

	public void setup(){
		image(bg1,0,0);
		image(ship.getImage(),ship.getX(),ship.getY());
//		image(boat2,55,680);
//		image(canoe,205,600);
//		image(canoe2,295,600);
//		image(ferrari,390,600);
		
	}
	@Override
	public  void mouseDragged() {	
		moveBoat();
	}
	
	@Override
	public void mousePressed() {
		x  = mouseX;
		y = mouseY;
		super.mousePressed();
	}
	
	public void moveBoat(){
		if(ship.getX()+123 >= mouseX && ship.getY()+63 >= mouseY){
			ship.setX(ship.getX() + (mouseX - x));
			ship.setY(ship.getY() + (mouseY - y));
			image(ship.getImage(), ship.getX(), ship.getY());
			x = mouseX;
			y = mouseY;
			setup();
		}
	}
}
