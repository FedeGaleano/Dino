package fede.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fede.Window;

public class FedeListener implements KeyListener
{	
	private Window window;

	private KeyCallback pressedCallback = e -> {}, releasedCallback = e -> {}, typedCallback = e -> {};
	
	public FedeListener(Window window)
	{
		this.window = window;
	}
	
	public FedeListener start()
	{
		window.addKeyListener(this);
		return this;
	}
	
	public FedeListener stop()
	{
		window.removeKeyListener(this);
		return this;
	}
	
	public FedeListener onPressed(KeyCallback callback)
	{
		this.pressedCallback = callback;
		return this;
	}
	
	public FedeListener onRelased(KeyCallback callback)
	{
		this.releasedCallback = callback;
		return this;
	}
	
	public FedeListener onTyped(KeyCallback callback)
	{
		this.typedCallback = callback;
		return this;
	}
	
	@Override
	public void keyPressed(KeyEvent keyDown)
	{
		pressedCallback.call(keyDown);
	}

	@Override
	public void keyReleased(KeyEvent keyUp)
	{
		releasedCallback.call(keyUp);
	}

	@Override
	public void keyTyped(KeyEvent keyTyped)
	{
		typedCallback.call(keyTyped);
	}
}
