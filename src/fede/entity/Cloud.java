package fede.entity;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Cloud extends Entity {

	private float virtual_x;
	
	public Cloud() {
		x = 700;
		y = 50;
		
		virtual_x = x;
		
		try {
			image = ImageIO.read(new File("res/cloud.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Cloud(Graphics g) {
		this();
		this.setGraphics(g);
	}
	
	@Override
	public void render() {
		g.drawImage(image, x, y, null);
	}

	@Override
	public void update() {
		virtual_x -= 0.25;
		x = (int)virtual_x;
	}

}
