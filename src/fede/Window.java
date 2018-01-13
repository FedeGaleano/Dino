package fede;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	private Game game;

	public Window(Game game) {
		super("Dino Game");
		this.game = game;
		this.set();
		game.activate();
		game.render();
	}
	
	private void set() {
		this.setIgnoreRepaint(true);
		this.getContentPane().add(game);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
