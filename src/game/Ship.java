package game;
import processing.core.PApplet;
import processing.core.PImage;

public class Ship extends PApplet {

    private int x;
    private int y;
    private boolean status, click;
    String imageSrc;
    PImage img;

    public Ship(int x, int y, PImage img){
        this.x = x;
        this.y = y;
        imageSrc = "image/ship.png";
        this.img = img;
        this.status = true;
        this.click = false;
    }
    
    public boolean checkClick(int mouseX, int mouseY){
    	if(getX() + getImage().getModifiedX2() >= mouseX && getY()+ getImage().getModifiedY2() >= mouseY && getX() <= mouseX && getY() <= mouseY){
    		setClick(true);
    		System.out.println(getImgSrc() + " : Click change true ");
		}
    	return isClick();
    }
    
    public void move(int x, int y){
    	setX(getX() + x);
    	setY(getY() + y);
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
    
    public void createImg(){
    	img = loadImage(imageSrc);
    }
    
    public String getImgSrc(){
		return imageSrc;
    }
    
    public PImage getImage(){
    	return img;
    }
}
