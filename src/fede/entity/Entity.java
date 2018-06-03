package fede.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
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
	
	public void renderDefaultHitBox() {
		this.renderHitBox(this.getDefaultHitBox());
	}
	
	private void renderHitBox(HitBox hitBox) {
		g.drawRect(hitBox.x0, hitBox.y0, hitBox.delta_x, hitBox.delta_y);
	}
	
	public void renderHitBoxes() {
		Color defaultColor = g.getColor();
		g.setColor(Color.RED);
		this.getHitBoxes().forEach(this::renderHitBox);
		g.setColor(defaultColor);
	}
	
	public boolean collidesWith(Entity another) {
		return any(this.getHitBoxes(), hitBox -> 
			any(another.getHitBoxes(), foreignHitbox -> hitBox.collidesWith(foreignHitbox))
		);
	}
}
