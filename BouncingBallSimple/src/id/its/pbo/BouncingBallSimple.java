package id.its.pbo;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class BouncingBallSimple extends JPanel {

	private static final long serialVersionUID = 1L;
	//atribut area
	private static final int AREA_WIDTH = 640;
	private static final int AREA_HEIGHT = 480;
	//atribut bola
	private static final int BALL_X_INITIAL_POSITION = 50;
	private static final int BALL_Y_INITIAL_POSITION = 20;
	// Radius bola
	private double ballRadius = 50;
	// Titik tengah bola di awal
	private double ballX = ballRadius + BALL_X_INITIAL_POSITION;
	private double ballY = ballRadius + BALL_Y_INITIAL_POSITION;
	// Kecepatan bola di axis X dan axis Y
	private float ballSpeedX = 4;
	private float ballSpeedY = 3;
	// Jumlah refresh layar dalam satu detik misal 30 frame persecond.
	private static final int REFRESH_RATE = 30;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //gambar background JPanel
		//Gambar area latar belakang dengan warna hitam
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, AREA_WIDTH, AREA_HEIGHT);
		//Gambar bola dengan warna merah
		g.setColor(Color.RED);
		g.fillOval((int) (ballX - ballRadius), (int) (ballY -
		ballRadius), (int)(2 * ballRadius), (int)(2 * ballRadius));
		}
	
	public BouncingBallSimple() {
		this.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
		//Thread untuk update posisi bola dan deteksi tepi
			Thread gameThread = new Thread() {
			public void run() {
				while (true) {
				// update posisi dengan kecepatan
				ballX += ballSpeedX;
				ballY += ballSpeedY;
				// deteksi tepi kanan-kiri
				if (ballX - ballRadius < 0) {
					ballSpeedX = -ballSpeedX;
					ballX = ballRadius;
				} else if (ballX + ballRadius > AREA_WIDTH) {
					ballSpeedX = -ballSpeedX;
					ballX = AREA_WIDTH - ballRadius;
				}
				// deteksi tepi atas bawah
				if (ballY - ballRadius < 0) {
					ballSpeedY = -ballSpeedY;
					ballY = ballRadius;
				} else if (ballY + ballRadius > AREA_HEIGHT) {
					ballSpeedY = -ballSpeedY;
					ballY = AREA_HEIGHT - ballRadius;
				}
				// Refresh layar
				// fungsi repaint akan memanggil paintComponent()
				repaint();
				// Beri delay
				try {
					Thread.sleep(1000 / REFRESH_RATE); //milliseconds
				} catch (InterruptedException ex) { }
				}
			}
		};
		gameThread.start(); // jalankan thread
		}
	
	public static void main(String[] args) {
		// Menjalankan UI di Event dispatcher thread
		SwingUtilities.invokeLater(new Runnable() {
		@Override
			public void run() {
			// Membuat frame dan memasangkan JPanel ke JFrame
			JFrame frame = new JFrame("Simple Bouncing Ball");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(new BouncingBallSimple());
			frame.pack();
			frame.setVisible(true);
			}
		});
	}
	
	
}
