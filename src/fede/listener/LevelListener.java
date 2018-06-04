package fede.listener;

import java.awt.event.KeyEvent;
import fede.Game;

public class LevelListener extends FedeListener
{
	public LevelListener(Game game)
	{
		super(game);
		this.onPressed(this::managePressed);
		this.onRelased(this::manageReleased);
	}
	
	private void managePressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			game.makeDinosaurJump();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			game.makeDinosaurDuckDown();
		}
	}
	
	private void manageReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			game.makeDinosaurStand();
		}
	}
}
