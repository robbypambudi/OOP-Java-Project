package id.its.pbo;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class BallPanel extends JPanel  implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	private static final int REFRESH_RATE = 30;
	private Ball ball;
	private BallArea box;
	private int areaWidth;
	private int areaHeight;
	
	
	public BallPanel(int width, int height) {
		this.areaWidth = width;
		this.areaHeight = height;
		this.setPreferredSize(new Dimension(areaWidth, areaHeight));
		
		Random rand = new Random();
		int radius = 50;
		int x = rand.nextInt(width - radius * 2 - 20) + radius + 10;
		int y = rand.nextInt(height - radius * 2 - 20) + radius + 10;
		int speed = 5;
		int angleInDegree = rand.nextInt(360);
		
		
		box = new BallArea(0, 0, 640, 480, Color.BLACK, Color.WHITE);
		BuildBall(x, y, radius ,speed, angleInDegree);
		
		//untuk mendapatkan ukuran area latar belakang jika frame diresize
		this.addComponentListener(new ComponentAdapter() {
			
		@Override
		public void componentResized(ComponentEvent e) {
			Component c = (Component)e.getSource();
			Dimension dim = c.getSize();
			areaWidth = dim.width;
			areaHeight = dim.height;
			box.set(0, 0, width, height);
			}
		});
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);
		startThread();
	}
	
	public void BuildBall(int x, int y, int radius, int speed , int angle) {
		
		ball = new Ball(x, y, radius ,speed, angle, Color.BLUE);
		
	}
	
	public void startThread() {
		Thread gameThread = new Thread() {
			
			public void run() {
				while (true) {
					ball.collide(box);
					repaint();
				try {
					Thread.sleep(1000 / REFRESH_RATE);
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
		ball.draw(g);
		g.drawString("Bisa Di Click, Di Drag, Mouse Exited, Entered", 0, areaHeight/3);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Random rand = new Random();
		int angleInDegree = rand.nextInt(360);

		
		BuildBall(x, y, 50 ,5, angleInDegree);			
		

		repaint();
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getPoint());
		
		int location = (int) ball.posisi.distance(e.getX(), e.getY());
		if (location < 50) {
			ball.speedX = 0;
			ball.speedY = 0;
		}
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		Random rand = new Random();
		int angleInDegree = rand.nextInt(360);
		
		BuildBall(x, y, 50 ,30, angleInDegree);

		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		this.ball.color = Color.blue;

		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		this.ball.color = Color.red;
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		this.ball.posisi.x = e.getX();
		this.ball.posisi.y = e.getY();
		
		repaint();
	
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}
}
