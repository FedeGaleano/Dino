package fede.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import fede.Game;

import static fede.utils.FedeCollections.any;

public abstract class Entity {
	protected Graphics g;
	protected BufferedImage image;
	protected int pixels[], width, height;
	protected int x, y;
	
	public void setGraphics(Graphics g) {
		this.g = g;
	}
	
	public Point getCoordinates() {
		return new Point(x, y);
	};
	
	public HitBox getDefaultHitBox() {
		return new HitBox(x, y, image.getWidth(null), image.getHeight(null));
	}

	public List<HitBox> getHitBoxes() {
		return Arrays.asList(new HitBox[]{this.getDefaultHitBox()});
	}
	
	public abstract void renderOn(int destinationBuffer[]);
	public abstract void update();
	
	public void renderDefaultHitBox(int buffer[], int scansize) {
		this.renderHitBox(this.getDefaultHitBox(), buffer, scansize);
	}
	
	private void renderHitBox(HitBox hitBox, int buffer[], int scansize) {
		renderRectangle(hitBox.x0, hitBox.y0, hitBox.delta_x, hitBox.delta_y, Color.RED.getRGB(), buffer, scansize);
	}
	
	public void renderHitBoxes(int buffer[], int scansize) {
		this.getHitBoxes().forEach(hitbox -> this.renderHitBox(hitbox, buffer, scansize));
	}
	
	public boolean collidesWith(Entity another) {
		return any(this.getHitBoxes(), hitBox -> 
			any(another.getHitBoxes(), foreignHitbox -> hitBox.collidesWith(foreignHitbox))
		);
	}
	
	protected void renderRectangle(int x0, int y0, int delta_x, int delta_y, int color, int buffer[], int scanSize) {
		int initialPoint = y0 * scanSize + x0;
		for(int i = initialPoint; i < initialPoint + delta_x; ++i) {
			buffer[i] = buffer[delta_y * scanSize + i] = color;
		}
		for(int i = 1; i < delta_y; ++i) {
			buffer[initialPoint + i * scanSize] = buffer[initialPoint + i * scanSize + delta_x] = color;
		}
	}	
}
