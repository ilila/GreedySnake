package aSnake;

import javax.swing.JFrame;

public class Msnake {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(10, 10, 900, 720);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Mpanel());
		
		frame.setVisible(true);
		
	}
}
