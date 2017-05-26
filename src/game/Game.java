package game;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;
import network.GameClient;
import network.GameServer;

public class Game extends PApplet implements Observer {

	private PImage bg, canoe, boat, ferrari, readyBtn, player1Btn, player2Btn, freezeBg;
	private boolean readyState, player1State, player2State, isServer, isClient;
	private int x, y,count = 0;
	private int shootField[][];

	private GameLogic gameLogic;
	private GameServer gameServer;
	private GameClient gameClient;

	public void start(){
		gameLogic = (GameLogic.checkServerIsCreate()) ? GameLogic.getInstance("client") : GameLogic.getInstance("server");

		gameServer = new GameServer();
		gameServer.addObserver(this);
		gameClient = new GameClient();
		gameClient.addObserver(this);

		shootField = new int [7][8];
		for(int i = 0; i< shootField[0].length; i++){
			for(int j = 0; j < shootField.length; j++){
				shootField[j][i] = 0;
			}
		}
		readyState = false;
	}

	public void startServer() {
		gameServer.start();
		isServer = true;
	}

	public void startClient() {
		gameClient.connect();
		isClient = true;
	}

	public void settings(){
		size(640,800);
	}

	public String getStatus(){
		if(isServer)
			return "server";
		return "client";
	}

	public void setup(){
		bg = loadImage("image/Bg.jpg");
		boat = loadImage("image/ship.png");
		canoe = loadImage("image/ship2.png");
		ferrari = loadImage("image/ship3.png");
		readyBtn = loadImage("image/readyBtn.jpg");
		player1Btn = loadImage("image/player1Btn.jpg");
		player2Btn = loadImage("image/player2Btn.jpg");
		
		freezeBg = loadImage("image/freeze.png");

		addShip("server");
		addShip("client");
	}

	public void addShip(String status){
		gameLogic.addShip(new Boat(55, 550, boat), status);
		//		gameLogic.addShip(new Canoe(55, 620, boat), status);
		//		gameLogic.addShip(new Canoe(205, 550, canoe), status);
		//		gameLogic.addShip(new Canoe(295, 550, canoe), status);
		//		gameLogic.addShip(new Ferrari(390, 550, ferrari), status);
	}

	@Override
	public void mouseDragged() {
		gameLogic.move(x, y, mouseX, mouseY, getStatus());
		x = mouseX;
		y = mouseY;
		super.mouseDragged();
	}

	@Override
	public void mousePressed() {
		x = mouseX;
		y = mouseY;
		shoot();
		if ( !readyState ){
			gameLogic.setClick(mouseX, mouseY, getStatus());
			readyBtnAction("click");
			super.mousePressed();
		}
	}
	
	public void shoot(){
		if(readyState && count == 1){

			int[] posShoot = gameLogic.checkPositionShoot(mouseX, mouseY);

//			System.out.println("Shot !");
			if (gameLogic.shoot(posShoot, getStatus())) {
				shootField[posShoot[0]][posShoot[1]] = -1;
			} else {
				shootField[posShoot[0]][posShoot[1]] = 1;
			}
			
			//			gameLogic.shoot(0, 0, getStatus());
//			gameServer.send(gameLogic);
//			gameClient.send(gameLogic);
		}
		else if (readyState && count == 0)
		{
			count =1;
		}
		
	}

	@Override
	public void mouseReleased() {

		gameLogic.magnetShip(getStatus());
		readyBtnAction("release");

		if ( gameLogic.checkAllShipInField(getStatus()) && x >= 50 && x <= 544 && y >=600 && y <= 738){
			readyState = true;
			gameLogic.addShipToBoard(getStatus());
		}
		if ( x >= 100 && x <= 100+player1Btn.getModifiedX2() && y >= 701 && y <= 701 + player1Btn.getModifiedY2()){
			player1State = true;
			System.out.println("player1Btn is clicked");
			startServer();
		}
		if ( x >= 350 && x <= 350+player2Btn.getModifiedX2() && y >= 701 && y <= 701 + player2Btn.getModifiedY2()){
			player2State = true;
			System.out.println("player2Btn is clicked");
			startClient();
		}
		super.mouseReleased();
	}

	public void readyBtnAction(String state){
		if(mouseX <= readyBtn.getModifiedX2() + 50 && mouseY <= readyBtn.getModifiedY2() + 600 && mouseX >= 50 && mouseY >= 600){
			if(state.equals("click"))
				readyBtn = loadImage("image/readyBtn_click.jpg");
			else if (state.equals("hover"))
				readyBtn = loadImage("image/readyBtn_hover.jpg");
			else
				readyBtn = loadImage("image/readyBtn.jpg");
		}
	}

	@Override
	public void draw() {
		if (!readyState){
			image(bg, 0, 0);
			if ( !player1State && !player2State ){
				image(player1Btn, 100, 700);
				image(player2Btn, 350, 700);
			} else {
				if (gameLogic.checkAllShipInField(getStatus()))
					image(readyBtn,50,600);
				else { 
					if ( player1State )
						image(player1Btn, 100, 700);
					if ( player2State)
						image(player2Btn, 350, 700);
				}
				drawAllShip();
			}
		} else {
			bg = loadImage("image/Bg2.jpg");
			image(bg, 0, 0);
			drawPreviewField();
			drawField();
			if (getStatus().equalsIgnoreCase("Server") && gameLogic.getTurn()%2 == 1)
				image(freezeBg,0,0);
			 if (getStatus().equalsIgnoreCase("client") && gameLogic.getTurn()%2 == 0)
				image(freezeBg,0,0);
		}
	}

	private void drawAllShip(){
		for(Ship s : gameLogic.getShipLists(getStatus())){
			image(s.getImage(), s.getX(), s.getY());
		}
	}

	private void drawField(){
		int posX = 49, posY = 56;
		for ( int i = 0 ; i < shootField[0].length ; i++){
			for ( int j = 0 ; j < shootField.length ; j++){
				if ( shootField[j][i] == 1 )
					fill(46, 204, 113);
				else if ( shootField[j][i] == -1)
					fill(230, 126, 34);
				else
					noFill();
				noStroke();
				rect(posX, posY, 65, 65);
				posY += 68;
			}
			posX+= 68;
			posY = 56;
		}

	}
	private void drawPreviewField(){
		int posX = 45, posY = 570;
		noStroke();
		fill(161, 225, 234);
		rect(35, 560, 220, 195, 10);
		int [][] array = new int [8][7];
		array = gameLogic.getB(getStatus()).getSquare();

		for(int i = 0; i < array[0].length; i++){
			for(int j = 0; j < array.length; j++){
				if ( array[j][i] == 1 )
					fill(46, 204, 113);
				else if ( array[j][i] == -1)
					fill(230, 126, 34);
				else
					noFill();

				stroke(255, 255, 255);
				rect(posX, posY, 24, 24);
				posY += 25;
			}
			posX += 25;
			posY = 570;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(!(arg instanceof GameLogic))
			gameLogic.start();
		else {
			gameLogic = (GameLogic) arg;
		}
	}
}
