package fede;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	public Window() {
		super("Dino Game");
		set();
	}
	
	private void set() {
		this.setSize(600, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
