package fede;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

@SuppressWarnings("serial")
public class Game extends Canvas {
	
	private static final Color foregroundColor = Color.decode("#535353");
	private static final Color backgroundColor = Color.decode("#F7F7F7");
	
	private Graphics g;
	private BufferStrategy bufferStrategy;
	
	public Game() {
		super();
		this.setIgnoreRepaint(true);
		this.setSize(600, 200);
		this.setBackground(backgroundColor);
		this.setForeground(foregroundColor);
	}
	
	public void activate() {
		this.createBufferStrategy(2);
		bufferStrategy = this.getBufferStrategy();
		g = bufferStrategy.getDrawGraphics();
	}
	
	public void render() {
		g.fillRect(100, 100, 20, 50);
		bufferStrategy.show();	
		
		g.dispose();
	}
}
