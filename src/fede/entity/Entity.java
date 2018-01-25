package fede.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public abstract class Entity {
	protected Graphics g;
	protected Image image;
	protected int x, y;
	
	public void setGraphics(Graphics g) {
		this.g = g;
	}
	
	public Point getCoordinates() {
		return new Point(x, y);
	};
	
	public HitBox getHitBox() {
		return new HitBox(x, y, image.getWidth(null), image.getHeight(null));
	}
	
	public abstract void render(); 
	public abstract void update();
}
