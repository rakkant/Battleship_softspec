package game;

import processing.core.PImage;

public class Ferrari extends Ship{
	
	private Ship s;

	public Ferrari(Ship s, PImage img, int x, int y) {
		this.s = s;
		this.setImage(img);
		this.setX(x);
		this.setY(y);
		this.setStartPositionX(x);
		this.setStartPositionY(y);
	}

}
