package game;

import processing.core.PImage;

public class Boat extends Ship{
	
	private Ship s;
	
	public Boat(Ship s, PImage img, int x, int y) {
		this.s = s;
		this.setImage(img);
		this.setX(x);
		this.setY(y);
		this.setStartPositionX(x);
		this.setStartPositionY(y);
	}

}
