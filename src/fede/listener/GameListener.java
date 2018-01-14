package fede.listener;

import java.awt.event.KeyEvent;

import fede.Window;
import fede.Game;

public class GameListener extends FedeListener
{
	private Game game;
	
	public GameListener(Window window, Game game)
	{
		super(window);
		this.game = game;
		this.onPressed(this::jumpIfSpaceKey);
	}
	
	private void jumpIfSpaceKey(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			game.makeDinosaurJump();
		}
	}
}
