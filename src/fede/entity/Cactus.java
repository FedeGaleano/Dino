package fede.entity;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cactus extends Entity {
	public Cactus() {
		try {
			image = ImageIO.read(new File("res/cactus4.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		x = 650;
		y = 145;
	}
	
	public Cactus(Graphics g) {
		this();
		this.setGraphics(g);
	}
	
	@Override
	public void update() {
		x -= 5;
	}
	
	@Override
	public void render() {
		g.drawImage(image, x, y, null);
	}
}
