package game;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class Ship extends PApplet {

    private int x, y, sizeBoatX = 0, sizeBoatY = 0, startPositionX, startPositionY, boardPosX, boardPosY;
    private boolean status, click, inField;
    private PImage img;

    public Ship(){
//        this.x = this.startPositionX = x;
//        this.y = this.startPositionY = y;
//        this.img = img;
        this.status = true;
        this.click = false;
    }
	public boolean checkClick(int mouseX, int mouseY){
    	if(getX() + getImage().getModifiedX2() >= mouseX && getY()+ getImage().getModifiedY2() >= mouseY && getX() <= mouseX && getY() <= mouseY)
    		setClick(true);
    	return isClick();
    }
    
    public void move(int x, int y){
    	setX(getX() + x);
    	setY(getY() + y);
    }
    
    public void setMagnet(int x, int y, int posX, int posY){
    	setX(x);
    	setY(y);
    	setBoardPosX(posX);
    	setBoardPosY(posY);
    	inField = true;
    }
    
    public void setStartPosition(){
    	setX(getStartPositionX());
    	setY(getStartPositionY());
    	inField = false;
    }
    
    public boolean isStatus() {
        return status;
    }
    
    public boolean isClick() {
		return click;
	}

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public void setClick(boolean click) {
		this.click = click;
	}

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public PImage setImage(PImage img){
    	return this.img = img;
    }
    
    public PImage getImage(){
    	return img;
    }

	public int getSizeBoatX() {
		if(this.sizeBoatX == 0)
			this.sizeBoatX = (int) Math.ceil(img.getModifiedX2()/68.0);
		return sizeBoatX;
	}

	public int getSizeBoatY() {
		if(this.sizeBoatY == 0)
			this.sizeBoatY = (int) Math.ceil(img.getModifiedY2()/68.0);
		return sizeBoatY;
	}

	public int getStartPositionX() {
		return startPositionX;
	}

	public int getStartPositionY() {
		return startPositionY;
	}

	public boolean isInField() {
		return inField;
	}

	public void setInField(boolean inField) {
		this.inField = inField;
	}

	public int getBoardPosX() {
		return boardPosX;
	}

	public void setBoardPosX(int boardPosX) {
		this.boardPosX = boardPosX;
	}

	public int getBoardPosY() {
		return boardPosY;
	}

	public void setBoardPosY(int boardPosY) {
		this.boardPosY = boardPosY;
	}
	
    public void setStartPositionX(int startPositionX) {
		this.startPositionX = startPositionX;
	}

	public void setStartPositionY(int startPositionY) {
		this.startPositionY = startPositionY;
	}
}
