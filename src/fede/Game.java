package fede;

import java.awt.Canvas;

@SuppressWarnings("serial")
public class Game extends Canvas {
	
	public Game() {
		super();
		this.setIgnoreRepaint(true);
		this.setSize(600, 200);
	}
}
