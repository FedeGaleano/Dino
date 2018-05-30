package fede;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	private Game game;
	private static final String baseText = "Dino Game";
	
	public Window(Game game) {
		super(baseText);
		this.game = game;
		this.game.window = this;
		this.set();
	}
	
	public void appendText(String text) {
		this.setTitle(baseText + " | " + text);
	}
	
	private void set() {
		this.setIgnoreRepaint(true);
		this.setLayout(new BorderLayout());
		this.add(game);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.revalidate();
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
