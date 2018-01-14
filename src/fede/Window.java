package fede;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	private Game game;

	public Window(Game game) {
		super("Dino Game");
		this.game = game;
		this.set();
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

	public void onClose(Callback callback) {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				callback.call();
			}
		});
	}
}

interface Callback {
	public abstract void call();
}
