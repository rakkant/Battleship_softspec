package game;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;
import network.GameClient;
import network.GameServer;

public class Game extends PApplet implements Observer {

	private PImage bg, canoe, boat, ferrari, readyBtn, player1Btn, player2Btn, freezeBg, winBg, loseBg;
	private boolean readyState, player1State, player2State, isServer, isClient, freeze;

	private int x, y;
	private int shootField[][];
	
	private ConcreteShip concreteShip;

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
		concreteShip = new ConcreteShip();
		
		bg = loadImage("image/Bg.jpg");
		boat = loadImage("image/ship.png");
		canoe = loadImage("image/ship2.png");
		ferrari = loadImage("image/ship3.png");
		readyBtn = loadImage("image/readyBtn.jpg");
		player1Btn = loadImage("image/player1Btn.jpg");
		player2Btn = loadImage("image/player2Btn.jpg");
		winBg = loadImage("image/Winner.jpg");
		loseBg = loadImage("image/lose.jpg");

		freezeBg = loadImage("image/freeze.png");

		addShip("server");
		addShip("client");
	}

	public void addShip(String status){
		gameLogic.addShip(new Boat(concreteShip, boat, 55, 550), status);
		gameLogic.addShip(new Boat(concreteShip, boat, 55, 620), status);
		gameLogic.addShip(new Canoe(concreteShip, canoe, 205, 550), status);
		gameLogic.addShip(new Canoe(concreteShip, canoe, 295, 550), status);
		gameLogic.addShip(new Ferrari(concreteShip, ferrari, 390, 550), status);
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
		if(freeze || ( gameLogic.getTurn()%2 == 0 && getStatus().equals("server") ) || (gameLogic.getTurn()%2 == 1 && getStatus().equals("client")))
			shoot();
		if ( !readyState ){
			gameLogic.setClick(mouseX, mouseY, getStatus());
			readyBtnAction("click");
			super.mousePressed();
		}
	}

	public void shoot(){
		if(readyState ){
			int[] posShoot = gameLogic.checkPositionShoot(mouseX, mouseY);
			if (gameLogic.shoot(posShoot, getStatus()) )
				shootField[posShoot[0]][posShoot[1]] = -1;
			else if (shootField[posShoot[0]][posShoot[1]] != -1 )
				shootField[posShoot[0]][posShoot[1]] = -2;

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

		if(checkBtnPlayerClick(player1Btn, 100, 701)){
			player1State = true;
			startServer();
		}
		if(checkBtnPlayerClick(player2Btn, 350, 701)){
			player2State = true;
			startClient();
		}

		super.mouseReleased();
	}
	public boolean checkBtnPlayerClick(PImage img, int posX, int posY){
		if(mouseX >= posX && mouseX <= posX + img.getModifiedX2() && mouseY >= posY && mouseY <= posY + img.getModifiedY2())
			return true;
		return false;
	}

	public void readyBtnAction(String state){
		if(mouseX <= readyBtn.getModifiedX2() + 50 && mouseY <= readyBtn.getModifiedY2() + 600 && mouseX >= 50 && mouseY >= 600){
			if(state.equals("click"))
				readyBtn = loadImage("image/readyBtn_click.jpg");
			else if (state.equals("hover"))
				readyBtn = loadImage("image/readyBtn_hover.jpg");
			else if ( state.equals("release")){
				readyBtn = loadImage("image/readyBtn.jpg");
				gameLogic.ready();
			} else {
				readyBtn = loadImage("image/readyBtn.jpg");
			}
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
			if ( (getStatus().equalsIgnoreCase("Server") && gameLogic.getTurn()%2 == 1) || (getStatus().equalsIgnoreCase("client") && gameLogic.getTurn()%2 == 0)){
				freeze = false;
				image(freezeBg,0,0);
			}
			if(getStatus().equals("client") && gameLogic.getCheckReady() == 2){
				if(gameLogic.checkLose().equals("client"))
					image(winBg, 0, 0);
				else if (gameLogic.checkLose().equals("server"))
					image(loseBg, 0, 0);

			} else if (getStatus().equals("server") && gameLogic.getCheckReady() == 2){
				if(gameLogic.checkLose().equals("client"))
					image(loseBg, 0, 0);
				else if (gameLogic.checkLose().equals("server"))
					image(winBg, 0, 0);
			}
		}
	}

	private void drawAllShip(){
		for(Ship s : gameLogic.getShipLists(getStatus())){
			image(s.getImage(), s.getX(), s.getY());
		}
	}

	private void drawField(){
		drawRecOfField(65, 68, 49, 56, shootField, "field");
	}

	private void drawPreviewField(){
		noStroke();
		fill(161, 225, 234);
		rect(35, 560, 220, 195, 10);
		drawRecOfField(24, 25, 45, 570, gameLogic.getB(getStatus()).getSquare(), "preview");
	}

	public void drawRecOfField(int sizeBox, int sizeMove, int posX, int posY, int[][] field, String type){
		int tempPosY = posY;
		if(type.equals("preview"))
			stroke(255, 255, 255);
		else 
			noStroke();

		for(int i = 0; i < field[0].length; i++){
			for(int j = 0; j < field.length; j++){
				if ( field[j][i] == 1 )
					fill(46, 204, 113);
				else if ( field[j][i] == -1)
					fill(230, 126, 34);
				else if ( field[j][i] == -2)
					fill(41, 128, 185);
				else
					noFill();

				rect(posX, posY, sizeBox, sizeBox);
				posY += sizeMove;
			}
			posX += sizeMove;
			posY = tempPosY;
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
