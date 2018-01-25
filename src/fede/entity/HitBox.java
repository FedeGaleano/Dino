package fede.entity;

import java.awt.Point;

public class HitBox {
	public int x0, y0, delta_x, delta_y;
	
	public HitBox(int x0, int y0, int delta_x, int delta_y) {
		this.x0 = x0;
		this.y0 = y0;
		this.delta_x = delta_x;
		this.delta_y = delta_y;
	}

	public boolean collidesWith(Point point) {
		return point.x >= x0 && point.x <= x0 + delta_x && point.y >= y0 && point.y <= y0 + delta_y;
	}
	
	public boolean collidesWith(HitBox another) {
		return another.collidesWith(this.getUpperLeftCorner()) || another.collidesWith(this.getUpperRightCorner()) || another.collidesWith(this.getLowerLeftCorner()) || another.collidesWith(this.getLowerRightCorner());
	}
	
	public Point getUpperLeftCorner() {
		return new Point(x0, y0);
	}
	
	public Point getUpperRightCorner() {
		return new Point(x0 + delta_x, y0);
	}
	
	public Point getLowerLeftCorner() {
		return new Point(x0, y0 + delta_y);
	}
	
	public Point getLowerRightCorner() {
		return new Point(x0 + delta_x, y0 + delta_y);
	}
}
