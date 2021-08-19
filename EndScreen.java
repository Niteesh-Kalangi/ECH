import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class EndScreen extends JPanel {
	private BufferedImage imageWin; //winning image
	private String imageWinPath="Win.png"; //image path of winning image
	private BufferedImage imageLose; //losing image
	private String imageLosePath="Lose.png"; //path of losing image
	private ArrayList<State> states; //states list
	private Candidate player; //player candidate
	private Candidate AI; //AI candidate
	public EndScreen(ArrayList<State> states, Candidate player, Candidate AI) {
		this.states=states;
		this.player=player; 
		this.AI=AI;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draws red and blue boxes at the top of the screen
		g.setColor(Color.red);
		g.fillRect(0, 0, 700, 224);
		g.setColor(Color.blue);
		g.fillRect(700, 0, 700, 224);
		
		int toDemVotes=0;
		int toRepVotes=0;
		//counts the total votes each candidate got from the states
		for(int i=0; i<states.size(); i++) {
			if(states.get(i).getPercentDem()>0.5)
				toDemVotes+=states.get(i).getElectoralVotes();
			else
				toRepVotes+=states.get(i).getElectoralVotes();
		}
		if(player.isDem==true&&toDemVotes>toRepVotes||player.isDem==false&&toRepVotes>toDemVotes)
			drawWin(g);
		else
			drawLose(g);
		
		
		g.setFont(new Font("Sans Serif", Font.BOLD, 60));
		g.setColor(Color.white);
		
		//draws whether you won or lost and each candidate's total elecotral votes
		if(player.isDem) {
		
			g.drawString(player.getName(), 1000, 50);
			g.drawString("Electoral Votes: " +toDemVotes, 800, 150);
			g.drawString("AI", 250, 50);
			g.drawString("Electoral Votes: "+toRepVotes, 50, 150);
		}
		else {
			g.drawString("AI", 1000, 50);
			g.drawString("Electoral Votes: " +toDemVotes, 800, 150);
			g.drawString(player.getName(), 100, 50);
			g.drawString("Electoral Votes: "+toRepVotes, 50, 150);
		}
		
	}
	//draws the winning picture
	public void drawWin(Graphics g) {
		g.setFont(new Font("Sans Serif", Font.BOLD, 100));
		g.setColor(Color.green);
		g.drawString("You    Win", 450, 100);
		try {
			imageWin = ImageIO.read(new File(imageWinPath));
		} catch (IOException ex) {
		}
		g.drawImage(imageWin,0,224,this);
		
	}
	//draws the losing picture
	public void drawLose(Graphics g) {
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
		g.setColor(new Color(95,48,48));
		g.drawString("You   Lose", 450, 100);
		try {
			imageLose = ImageIO.read(new File(imageLosePath));
		} catch (IOException ex) {
		}
		g.drawImage(imageLose,0,224,this);
	}

}
