package game;
import processing.core.PApplet;
import processing.core.PImage;

public class Ship extends PApplet {

    private int x, y, sizeBoatX = 0, sizeBoatY = 0, startPositionX, startPositionY;
    private boolean status, click, inField;
    PImage img;

    public Ship(int x, int y, PImage img){
        this.x = this.startPositionX = x;
        this.y = this.startPositionY = y;
        this.img = img;
        this.status = true;
        this.click = false;
    }
    
    public boolean checkClick(int mouseX, int mouseY){
    	if(getX() + getImage().getModifiedX2() >= mouseX && getY()+ getImage().getModifiedY2() >= mouseY && getX() <= mouseX && getY() <= mouseY){
    		setClick(true);
    		System.out.println(" Click change true ");
		}
    	return isClick();
    }
    
    public void move(int x, int y){
    	setX(getX() + x);
    	setY(getY() + y);
    }
    
    public void setMagnet(int x, int y){
    	setX(x);
    	setY(y);
    	inField = true;
    }
    
    public void setStartPosition(){
    	setX(getStartPositionX());
    	setY(getStartPositionY());
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
	

	
	
	
    
    
}
