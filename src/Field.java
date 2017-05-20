
public abstract class Field {
	 private int length = 1 ;
	 private int x;
	 private int y;
	  
	 
	 public Field(int length, int x, int y){
		 if (length > 0){
			 this.length = length;
		 }
		 this.x = x;
		 this.y = y;
	 }
	 
	 public int getLength(){
		 return length;
	 }
	 
	 public int getX(){
		 return x;
	 }
	 
	 public int getY(){
		 return y;
	 }
	 
	 public abstract void beShot();
}
