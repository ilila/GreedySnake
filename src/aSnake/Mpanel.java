package aSnake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Mpanel extends JPanel implements KeyListener,ActionListener{
	public static Clip bgm;
	ImageIcon title;
	ImageIcon body;
	ImageIcon up;
	ImageIcon down;
	ImageIcon left;
	ImageIcon right;
	ImageIcon food;
	
	int len = 3;
	int score = 0;
	int [] snakex = new int[750];
	int [] snakey = new int[750];
	String fx = "R";
	boolean isStarted = false;
	boolean isFailed = false;
	Timer timer = new Timer(100, this);
	int foodx;
	int foody;
	Random random = new Random();
	
	public Mpanel() {
		loadImages();
		initialSnake();
		this.setFocusable(true);
		this.addKeyListener(this);
		timer.start();
		foodx = 25 + 25*random.nextInt(34);
		foody = 75 + 25*random.nextInt(24);
		loadBGM();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		title.paintIcon(this, g, 25, 11);
		
		g.fillRect(25, 75, 850, 600);
		
		g.setColor(Color.WHITE);
		g.drawString("len"+len, 750, 35);
		g.drawString("score"+score, 750, 50);
		
		if(fx == "R") {
			right.paintIcon(this, g, snakex[0], snakey[0]);
		}else if(fx == "L"){
			left.paintIcon(this, g, snakex[0], snakey[0]);
		}else if(fx == "U"){
			up.paintIcon(this, g, snakex[0], snakey[0]);
		}else if(fx == "D"){
			down.paintIcon(this, g, snakex[0], snakey[0]);
		}
		
		for(int i=1;i<len;i++) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}
		
		food.paintIcon(this, g, foodx, foody);
		
		if(isStarted == false) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.BOLD,40));
		g.drawString("Press Space to Start", 300, 300);
		}
		if(isFailed == true) {
			g.setColor(Color.RED);
			g.setFont(new Font("arial",Font.BOLD,40));
			g.drawString("Faile : Press Space to Restart", 200, 300);
			}
	}
	private void initialSnake() {
		len = 3;
		snakex[0]  = 100;
		snakey[0]  = 100;
		snakex[1]  = 75;
		snakey[1]  = 100;
		snakex[2]  = 50;
		snakey[2]  = 100;	
		fx = "R";
		score = 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE) {
			if(isFailed == true) {
				isFailed = false;
				initialSnake();
			}else {
			isStarted = !isStarted;
			}
			repaint();
			if(isStarted) {
				playBGM();
			}else {
				stopBGM();
			}
		}else if(keyCode == KeyEvent.VK_LEFT) {
			fx = "L";
		}else if(keyCode == KeyEvent.VK_RIGHT) {
			fx = "R";
		}else if(keyCode == KeyEvent.VK_UP) {
			fx = "U";
		}else if(keyCode == KeyEvent.VK_DOWN) {
			fx = "D";
		}
			
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(isStarted && !isFailed) {
			for(int i = len-1;i>0;i--) {
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			if(fx == "R") {
				snakex[0] = snakex[0]+25;
				if(snakex[0]>850) {
					snakex[0] = 25;
				}
			}else if(fx == "L") {
				snakex[0] = snakex[0] - 25;
				if(snakex[0]<25) {
					snakex[0] = 850;
				}
			}else if(fx == "U") {
				snakey[0] = snakey[0] - 25;
				if(snakey[0]<75) {
					snakey[0] = 650;
				}
			}else if(fx == "D") {
				snakey[0] = snakey[0] + 25;
				if(snakey[0]>650) {
					snakey[0] = 75;
				}
			}
			if(snakex[0] == foodx && snakey[0] == foody) {
				len ++;
				score += 10;
				foodx = 25 + 25*random.nextInt(34);
				foody = 75 + 25*random.nextInt(24);
			}
			for(int i=1;i<len;i++) {
				if(snakex[i]==snakex[0]&&snakey[i]==snakey[0]) {
					isFailed = true;
				}
			}
		}
		repaint();
		timer.start();
	}
	private void loadBGM() {
		try {
			bgm = AudioSystem.getClip();
			InputStream is = this.getClass().
					getClassLoader().
					getResourceAsStream("sound/bgm.wav");
			AudioInputStream ais = AudioSystem.
					getAudioInputStream(new BufferedInputStream(is));
			bgm.open(ais);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void playBGM() {
		bgm.loop(Clip.LOOP_CONTINUOUSLY);
	}
	private void stopBGM() {
		bgm.stop();
	}
	private void loadImages() {
		InputStream is;
		try {
			is = getClass().getClassLoader().getResourceAsStream("images/title.jpg");
			title = new ImageIcon(ImageIO.read(is)); 
			
			is = getClass().getClassLoader().getResourceAsStream("images/body.png");
			body = new ImageIcon(ImageIO.read(is)); 
			
			is = getClass().getClassLoader().getResourceAsStream("images/down.png");
			down = new ImageIcon(ImageIO.read(is)); 
			
			is = getClass().getClassLoader().getResourceAsStream("images/food.png");
			food = new ImageIcon(ImageIO.read(is)); 
			
			is = getClass().getClassLoader().getResourceAsStream("images/left.png");
			left = new ImageIcon(ImageIO.read(is)); 
			
			is = getClass().getClassLoader().getResourceAsStream("images/right.png");
			right = new ImageIcon(ImageIO.read(is)); 
			
			is = getClass().getClassLoader().getResourceAsStream("images/up.png");
			up = new ImageIcon(ImageIO.read(is)); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
