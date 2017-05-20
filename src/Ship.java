import processing.core.PApplet;
import processing.core.PImage;

public class Ship extends PApplet {

    private int x;
    private int y;
    private boolean status;
    String imageSrc;
    PImage img;

    public Ship(int x, int y, PImage img){
        this.x = x;
        this.y = y;
        imageSrc = "image/ship.png";
        this.img = img;
        this.status = true;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
