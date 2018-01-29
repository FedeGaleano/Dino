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
		return this.collidesInX(another) && this.collidesInY(another);
	}
	
	public boolean collidesInX(HitBox another) {
		return Math.abs(this.x0 + this.delta_x / 2 - (another.x0 + another.delta_x / 2)) <= (this.delta_x + another.delta_x) / 2;
	}
	
	public boolean collidesInY(HitBox another) {
		return Math.abs(this.y0 + this.delta_y / 2 - (another.y0 + another.delta_y / 2)) <= (this.delta_y + another.delta_y) / 2;
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
