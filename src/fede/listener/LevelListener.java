package fede.listener;

import java.awt.event.KeyEvent;
import fede.Game;

public class LevelListener extends FedeListener
{
	public LevelListener(Game game)
	{
		super(game);
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
