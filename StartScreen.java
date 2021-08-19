import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class StartScreen extends JPanel{
	
	private JFrame jf; //JFrame
	public JButton start; //start button
	private JButton extraInfo; //button for extra info
	private boolean drawExtraInfo=false; //boolean to show whether to draw the extra info
	public StartScreen s; //startscreen to remove
	public PartyScreen p; //party screen to add
	public StartScreen(JFrame jf, StartScreen s) {
		setLayout(null);
		setBackground(Color.blue);
		this.setSize(getWidth(), getHeight());
		this.jf=jf;
		this.s=s;
		//start button
		start=new JButton("start");
		start.setBounds(600,800,200,100);
		start.setBackground(Color.green);
		start.setFont(new Font("Serif", Font.BOLD, 50));
		add(start);
		StartClass starter=new StartClass();
		start.addActionListener(starter);
		
		//initializing extra info button
		ExtraClass extra=new ExtraClass();
		extraInfo=new JButton("Extra Info");
		extraInfo.setBounds(1200,0,200,100);
		extraInfo.setBackground(Color.red);
		add(extraInfo);
		extraInfo.setFont(new Font("Serif", Font.BOLD, 30));
		extraInfo.addActionListener(extra);
		
	}
	//takes out start screen and adds party screen
	private class StartClass implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
			jf.getContentPane().add(p);
			jf.getContentPane().remove(s);
			jf.invalidate();
            jf.validate();
            remove(start);
            
		}
		
	}
	//shows the extra info
	private class ExtraClass implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(drawExtraInfo==false) {
				drawExtraInfo=true;
				extraInfo.setText("Back");
				repaint();
				start.setBounds(600,865,200,100);
			}
			else {
				drawExtraInfo=false;
				extraInfo.setText("Extra Info");
				repaint();
				start.setBounds(600,800,200,100);
				
			}
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//the background image
		ImageIcon i=new ImageIcon("startImage.png");
		
		i.paintIcon(this, g,25,0);
		Font f=new Font("Serif", Font.BOLD, 50);
		g.setFont(f);
		g.setColor(new Color(255,100,0));
		g.drawString("Win The White House", 500, 50);
		g.drawString("Rules:", 500, 200);
		f=new Font("Serif", Font.BOLD, 25);
		g.setFont(f);
		
		//all of the start instructions
		g.drawString("1. In the options screen, choose your name, slogan, political party, vice president, and issue views", 200, 250);
		g.drawString("2. After the options, a screen with boxes as states will open, some colored, showing political lean and some not", 200, 300);
		g.drawString("3. In the state screen, you have to choose a state to make an action in." , 200, 350);
		g.drawString("You can either poll voters, start ad campaigns, or fundraise in the state you pick", 200, 380);
		g.drawString("4. Ad Campaigning can increase the amount of voters you have in the state you chose.", 200, 430);
		g.drawString("Your initial amount of voters in each state depends on what choices you made in the options screen", 200, 460);
		g.drawString("5. Polling a state shows you approximately the amount of voters you have in it by coloring the state", 200, 510);
		g.drawString("6. Fundraising provides funds based on % of voters in the state. It doesnt cost money but the other actions do", 200, 560);
		g.drawString("Funds can be used to pay for polling and ad campaigning", 200, 590);
		g.drawString("7. You will be playing against an AI and both players will have 25 turns", 200, 640);
		g.drawString("8. Whoever wins the most amount of states wins the game", 200, 690);
		
		
		if(drawExtraInfo==true) {
			//all of the extra info
			g.setColor(Color.white);
			g.fillRect(0,0,1400,1000);
			g.setColor(Color.blue);
			g.setFont(new Font("Serif", Font.BOLD, 50));
			g.drawString("Actions-", 600, 50);
			g.drawString("Gameplay-",600 , 540);
			g.setFont(new Font("Serif", Font.BOLD, 35));
			g.drawString("Fundraising: ", 100, 100);
			g.drawString("Polling: ", 100, 230);
			g.drawString("Ad Campaigning: ", 100, 410);
			g.setFont(new Font("Serif", Font.BOLD, 25));
			g.drawString("-Formula to calculate total earnings: Electoral votes*Percent democrat or 1-percent democrat*0.33/(Times fundraised in state^2)" , 0, 130);
			g.drawString("-Fundraising is free of cost unlike the other actions. In your fist turn you can only fundraise", 0, 180);
			g.setFont(new Font("Serif", Font.BOLD, 19));
			g.drawString("-Polling releases info about a state such as an approximate range of the percent of voters for a party, which can be seen based on the revealed color of the states button", 0, 260);
			g.setFont(new Font("Serif", Font.BOLD, 25));
			g.drawString("-Polling also reveals an info button for the state which if clicked, shows the state's relevant issues and electoral votes", 0, 310);
			g.drawString("-Polling costs 4 dollars", 0, 360);
			g.drawString("-Campaigning in a state increases or decreases the states percent voters for the democrat candidate in your favor.", 0, 440);
			g.drawString("-Campaigning costs 6 dollars: ", 0, 490);
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("-First you will choose which party you want, next, you will select your slogan, difficulty of AI, name, and vice president. ", 0, 570);
			g.drawString("-These options will all affect the starting percent of voters you get in each the beggining of the game. ", 0, 600);
			g.drawString("-You will then select from a group of issues, three main issues you want to focus on. There will be different issues to select from based on the party you chose. ", 0, 630);
			g.drawString("-Each state has 3 relevant issues. The more issues you chose to focus on that match a state's relevant issues, the higher it will bump the # voters for you in said state. ", 0, 660);
			g.drawString("-The AI will also be choosing from these issues. The harder the difficulty, the better issues it will pick. ", 0, 690);
			g.drawString("-When you play the game, you first have to click a state and after, you have to pick an action to do in the state. Remember, it is $6 to campaign and $4 to poll. ", 0, 720);
			g.drawString("-You will be playing against an AI which will be automatically making moves. The effectivness of the moves will be based on the difficulty mode you picked. ", 0, 750);
			g.drawString("-Around 50% of the states' info will be available at the start, which means they will have a color other than gray and an info button will be available for the state. ", 0, 780);
			g.drawString("-There will be 25 turns each for you and the AI. The winner will be determined by total electoral votes a player got. ", 0, 810);
			
		}
	}

}
