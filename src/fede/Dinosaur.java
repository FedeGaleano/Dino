package fede;

import java.awt.Graphics;

public class Dinosaur {
	
	private Graphics g;
	
	public void setGraphics(Graphics g) {
		this.g = g;
	}
	
	public void render() {
		g.fillRect(100, 100, 20, 50);
	}
}
