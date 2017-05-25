package game;

import processing.core.PApplet;

public class Main {
	
	public static void main(String[] args) {
		Game player1 = new Game();
		Game player2 = new Game();
		
		String[] args1 = {"Window1"};
		String[] args2 = {"Window2"};
		
		PApplet.runSketch(args1, player1);
		PApplet.runSketch(args2, player2);
		
	}

}
