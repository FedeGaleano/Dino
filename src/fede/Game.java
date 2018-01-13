package fede;

import java.awt.Canvas;
import java.awt.Color;

@SuppressWarnings("serial")
public class Game extends Canvas {
	
	private static final Color foregroundColor = Color.decode("#535353");
	private static final Color backgroundColor = Color.decode("#F7F7F7");
	
	public Game() {
		super();
		this.setIgnoreRepaint(true);
		this.setSize(600, 200);
		this.setBackground(backgroundColor);
		this.setForeground(foregroundColor);
	}
}
