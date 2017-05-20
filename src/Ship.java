
public class Ship {

    private int x;
    private int y;
    private boolean statusAlive;
    private int dismissPart;
    private int length = 1 ;

    public Ship(int length, int x, int y){
    	if(length > 0){
    		this.length = length;
    	}
        this.x = x;
        this.y = y;
        this.statusAlive = true;
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
    
    public int getLength(){
    	return length;
    }
    

    public boolean isStatus() {
    	if(dismissPart == 0){
    		return statusAlive = false;
    	}
        return statusAlive = true;
    }

    public void setStatus(boolean statusAlive) {
        this.statusAlive = statusAlive;
    }
    
    public void beShot(){
    	dismissPart--;
    }
    
   
}
