package src;

public class Ball {
	private int xPos, yPos;
	private int dX = 5, dY = -5;
	
	public Ball(int xPos, int yPos){
		this.xPos=xPos;
		this.yPos=yPos;
	}
	
	public int getX(){ return xPos; }
	public int getY(){ return yPos; }
	public int getDX(){ return dX; }
	public int getDY(){ return dY; }
	
	public void setPos(int xPos, int yPos){
		this.xPos=xPos;
		this.yPos=yPos;
	}
	public void setDX(int dX){ this.dX = dX; }
	public void setDY(int dY){ this.dY = dY; }
	
	public void move(){
		xPos = xPos+dX;
		yPos = yPos+dY;
	}
	
	public void reset(){
		this.xPos = 240;
		this.yPos = 150;
		dX = 5;
		dY = -5;
	}
}
