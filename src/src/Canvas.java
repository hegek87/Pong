package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import src.Paddle.Side;

public class Canvas extends JPanel implements ActionListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5472458446600620995L;
	private Ball ball;
	private Paddle player;
	private Paddle computer;
	
	private Graphics2D bufferedGraphics;
	private BufferedImage image;
	private long timeLasted;
	Timer time = new Timer(15, this);
	
	public Font newFont = new Font("sansserif", Font.BOLD, 20);
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	
	public Canvas(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		ball = new Ball(WIDTH/2, HEIGHT/2);
		player = new Paddle(Side.PLAYER);
		computer = new Paddle(Side.COMPUTER);
		addMouseMotionListener(this);
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		bufferedGraphics = image.createGraphics();
	}
	
	@Override public void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.BLACK);
		bufferedGraphics.setFont(newFont);
		bufferedGraphics.clearRect(0, 0, WIDTH, HEIGHT);
		bufferedGraphics.setBackground(Color.BLACK);
		bufferedGraphics.setColor(Color.WHITE);
		bufferedGraphics.fillRect(WIDTH/2-2, 0, 4, HEIGHT);	//Half court line
		bufferedGraphics.fillRect(player.X_POS, player.getY(), 10, 70); 	//Player paddle
		bufferedGraphics.fillRect(computer.X_POS, computer.getY(), 10, 70);	//Computer paddle
		bufferedGraphics.setColor(Color.RED);
		bufferedGraphics.fillOval(ball.getX(), ball.getY(), 15, 15);		//Ball
		if(player.getScore()==10 || computer.getScore()==10){
			bufferedGraphics.drawString("You lasted " + (timeLasted/1000) + " Seconds.", 50, 145);
		}
		bufferedGraphics.setColor(Color.WHITE);
		bufferedGraphics.drawString("Computer score: " + computer.getScore(), WIDTH-200, 25);
		bufferedGraphics.drawString("Player score: " + player.getScore(), 60, 25);
		g.drawImage(image, 0, 0, this);
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void start(){
		timeLasted = System.currentTimeMillis();
		time.start();
	}
	
	public void stop(){
		time.stop();
		timeLasted = System.currentTimeMillis() - timeLasted;
		repaint();
	}
	public void update(Graphics g){
		paintComponent(g);
	}
	
	@Override public void actionPerformed(ActionEvent e){
		if(player.getScore()>=10||computer.getScore()>=10)
			stop();
		ball.move();
		computer.setPos(ball.getY() - 35);
		checkCollisions();
		repaint();
	}
	private void checkCollisions() {
		if(ball.getY() <= 0) ball.setDY(ball.getDY()*-1);
		if(ball.getY() >= HEIGHT) ball.setDY(ball.getDY()*-1);
		if(ball.getX() <= 40 && hitPaddle()) ball.setDX(ball.getDX()*-1);
		if(ball.getX() <= 0 && !hitPaddle()){
			computer.setScore(computer.getScore()+1);
			ball.reset();
		}
		if(ball.getX() >= WIDTH-30) ball.setDX(ball.getDX()*-1);
	}
	
	private boolean hitPaddle() {
		return (player.getY() - 15) <= ball.getY()
			&& (player.getY() + 70) > ball.getY();
	}

	@Override public void mouseMoved(MouseEvent e){
		player.setPos(e.getY()-35);
	}
	@Override public void mouseDragged(MouseEvent e){}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Pong");
		frame.setLayout(new BorderLayout());
		final Canvas c = new Canvas();
		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(c.computer.getScore() == 10)
					c.computer.setScore(0);
				c.start();
			}
		});
		frame.add(c, BorderLayout.CENTER);
		frame.add(start, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
