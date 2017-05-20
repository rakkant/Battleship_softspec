
public abstract class Ship extends Field {


	
    private boolean statusAlive;
    private int dismissPart;
   
    public Ship(int length, int x, int y) {
 		super(length, x, y);
 		dismissPart = this.getLength();
 		
 	}


    public void setStatus(boolean statusAlive) {
        this.statusAlive = statusAlive;
    }

    public boolean isStatus() {
    	if(dismissPart == 0){
    		return statusAlive = false;
    	}
        return statusAlive = true;
    }

    
    
    public void beShot(){
    	dismissPart--;
    }
    
    public abstract String type();
}
