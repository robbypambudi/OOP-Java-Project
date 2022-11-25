package id.its.pbo;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import java.util.ArrayList;
public class BallPanel extends JPanel  {

	private static final long serialVersionUID = 1L;
	private static final int REFRESH_RATE = 30;
	private ArrayList<Ball> ball;
	private BallArea box;
	private int areaWidth;
	private int areaHeight;
	
	
	public BallPanel(int width, int height) {
		this.areaWidth = width;
		this.areaHeight = height;
		this.setPreferredSize(new Dimension(areaWidth, areaHeight));
		
		Random rand = new Random();
		int radius = 50;
		float speed = 6;
		int angleInDegree = rand.nextInt(360);
		
		
		box = new BallArea(0, 0, 640, 480, Color.BLACK, Color.WHITE);
		ball = new ArrayList<Ball>();
		for (int i = 0; i < 5; i++) {
			int x = rand.nextInt(width - radius * 2 - 20) + radius + 10;
			int y = rand.nextInt(height - radius * 2 - 20) + radius + 10;
			ball.add(new Ball(x, y, radius ,speed, angleInDegree, Color.BLUE));
		}
		
		//untuk mendapatkan ukuran area latar belakang jika frame diresize
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Component c = (Component)e.getSource();
				Dimension dim = c.getSize();
				areaWidth = dim.width;
				areaHeight = dim.height;
				box.set(0, 0, areaWidth, areaHeight);
				}
			}
		);
		
		this.setFocusable(true);
		startThread();
	}
	
	public void colider(Ball ball, Ball ball2) {
		if (testCircle(ball, ball2)) {
			
			int bx = ball.posisi.x - ball2.posisi.y;
			int by = ball.posisi.y - ball2.posisi.y;
			
			double direction = Math.atan2(by, bx);
			
			ball.speedX = -ball.speedX;
			ball.speedY = -ball.speedY;
			ball2.speedX = -ball2.speedX;
			ball2.speedY = -ball2.speedY;
			
			
		}
	}
	
	public boolean testCircle (Ball b1, Ball b2) {
		boolean flag = false;
		double distance = b1.posisi.distance(b2.posisi.getLocation());
		if (distance <= 90) {
			flag = true;
		}
		return flag;
	}
	
	
	public void startThread() {
		Thread gameThread = new Thread() {
			
			public void run() {
				while (true) {
					for (Ball eachBall : ball) {
						eachBall.collide(box);
						for(Ball temp: ball) {
							colider(eachBall, temp);
						}
						
					}
					repaint();
					
				try {
					Thread.sleep(1400 / REFRESH_RATE);
				} catch (InterruptedException ex) {
					
					}
				}
			}

		
		};
		gameThread.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		box.draw(g);
		for(Ball eachBall : ball) {
			eachBall.draw(g);
		}
	}

}	