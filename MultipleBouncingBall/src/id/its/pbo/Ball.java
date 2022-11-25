package id.its.pbo;

import java.awt.*;
public class Ball {
	Point posisi;
	float speedX, speedY;
	float radius;
	public Color color;
	
	public Ball(int x, int y, float radius, float speed, float angleInDegree, Color color) {
		posisi = new Point(x, y);
		this.speedX = (float)(speed *
		Math.cos(Math.toRadians(angleInDegree)));
		this.speedY = (float)(-speed *
		(float)Math.sin(Math.toRadians(angleInDegree)));
		this.radius = radius;
		this.color = color;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int)(posisi.x - radius), (int)(posisi.y - radius), (int)(2 *
		radius), (int)(2 * radius));
	}
	
	public void collide(BallArea box) {
		int ballMinX = (int) (box.minX + radius);
		int ballMinY = (int) (box.minY + radius);
		int ballMaxX = (int) (box.maxX - radius);
		int ballMaxY = (int) (box.maxY - radius);
		
		posisi.x += speedX;
		posisi.y += speedY;
		
		if (posisi.x < ballMinX) {
			speedX = -speedX;             
			posisi.x = ballMinX;
			
		} else if (posisi.x > ballMaxX) {
			speedX = -speedX;
			posisi.x = ballMaxX;
		}
		
		if (posisi.y < ballMinY) {
			speedY = -speedY;
			posisi.y = ballMinY;
			
		} else if (posisi.y > ballMaxY) {
			speedY = -speedY;
			posisi.y = ballMaxY;
		}
	}
	
}