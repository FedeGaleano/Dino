package fede;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cactus {
	
	// Graphics
	private Graphics g;
	private Image image;
	
//	public static final int four_cactuses = 1, three_cactuses = 2, little_cactus = 3, big_cactus = 4, two_little_cactuses = 5, two_big_cactuses = 6;
	
	private int x = 650, y = 145;
	
	public Cactus() {
		try {
			image = ImageIO.read(new File("res/cactus4.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Cactus(Graphics g) {
		this();
		this.setGraphics(g);
	}
	
	public void setGraphics(Graphics g) {
		this.g = g;
	}
	
	public void update() {
		x -= 15;
	}
	
	public void render() {
		g.drawImage(image, x, y, null);
	}
	
	public Point getCoordinates() {
		return new Point(x, y);
	}
}
