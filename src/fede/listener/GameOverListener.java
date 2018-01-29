package fede.listener;

import java.awt.event.KeyEvent;

import fede.Game;
import fede.Window;

public class GameOverListener extends FedeListener {

	private Game game;
	
	public GameOverListener(Window window, Game game) {
		super(window);
		this.game = game;
		this.onPressed(this::restartIfSpaceKey);
	}
	
	private void restartIfSpaceKey(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			game.restart();
		}
	}
}
