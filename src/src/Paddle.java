package src;

public class Paddle {
	private int y, score;
	public final int X_POS;
	
	public enum Side{
		PLAYER(30),
		COMPUTER(Canvas.WIDTH - 30);
		
		int x;
		Side(int x){
			this.x=x;
		}
	}
	
	public Paddle(Side side){
		X_POS=side.x;
		score=0;
		y=Canvas.HEIGHT/2;
	}
	
	public int getY(){ return y; }
	public int getScore(){ return score; }
	
	public void setPos(int y){
		this.y=y;
		if(y < 0) this.y = 0;
		if(y > Canvas.HEIGHT-70) this.y = Canvas.HEIGHT-70;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
