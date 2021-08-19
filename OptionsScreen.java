import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class OptionsScreen extends JPanel{
	private JButton VP1; //first vp button
	private JButton VP2; //second vp button
	private JButton VP3; //third vp button
	private JButton start; //start button
	private JButton easy; //easy mode button
	private JButton hard; //hard mode button
	private JButton medium; //medium mode button
	private JButton slogan1; //first slogan button
	private JButton slogan2; //second slogan button
	private JButton slogan3; //third slogan button
	private JTextField name; //textfield to enter name
	JFrame jf; //JFrame
	OptionsScreen o; //options screen that is being displayed
	IssueScreen i; //issuescreen that is to be added
	private ArrayList<State> states; //states arraylist
	private Candidate player; //user candidate
	private Candidate AI; //AI candidate
	private boolean sloganClicked=false; //checks whether slogan is clicked
	private boolean viceClicked=false; //checks whether vice president buttons are clicked
	private boolean modeClicked=false; //checks whether mode button is clicked
	private boolean drawStartGameText; //checks whether or not the text to start the game should be drawn
	public OptionsScreen(ArrayList<State> states, Candidate player, Candidate AI) {
		setLayout(null);
		setBackground(Color.white);
		
		this.states=states;
		this.player=player;
		this.AI=AI;
		
		//initializes all of the buttons
		
		//vice president button
		VicePresident vice=new VicePresident();
		VP1=new JButton("Bill Board");
		VP1.setBounds(150,200,100,50);
		add(VP1);
		VP1.addActionListener(vice);
		//vice president button
		VP2=new JButton("Paige Turner");
		VP2.setBounds(140,300,120,50);
		add(VP2);
		VP2.addActionListener(vice);
		//vice president button
		VP3=new JButton("Sue Yu");
		VP3.setBounds(150,400,100,50);
		add(VP3);
		VP3.addActionListener(vice);
		
		//mode buttons
		ModeClass mode=new ModeClass();
		easy=new JButton("Easy");
		easy.setBounds(170,660,150,75);
		add(easy);
		easy.addActionListener(mode);
		
		medium=new JButton("Medium");
		medium.setBounds(170,760,150,75);
		add(medium);
		medium.addActionListener(mode);
		
		hard=new JButton("Impossible");
		hard.setBounds(170,860,150,75);
		add(hard);
		hard.addActionListener(mode);
		
		//slogan buttons
		SloganClass slogan= new SloganClass();
		slogan1=new JButton();
		slogan1.setBounds(1250,180, 70,70);
		slogan1.setIcon(new ImageIcon("checkmark.png"));
		add(slogan1);
		slogan1.addActionListener(slogan);
		
		slogan2=new JButton();
		slogan2.setBounds(1210,280, 70,70);
		slogan2.setIcon(new ImageIcon("checkmark.png"));
		add(slogan2);
		slogan2.addActionListener(slogan);
		
		slogan3=new JButton();
		slogan3.setBounds(1270,380, 70,70);
		slogan3.setIcon(new ImageIcon("checkmark.png"));
		add(slogan3);
		slogan3.addActionListener(slogan);
		
		
		//Name text field
		name=new JTextField();
		name.setBounds(490,230,300,50);
		name.setFont(new Font("Monospaced", Font.BOLD, 20));
		add(name);
		
		//start button
		start=new JButton("Next");
		start.setBounds(1100,800,200,100);
		add(start);
		start.setEnabled(false);
		start.setFont(new Font("Monospaced", Font.BOLD, 30));
		StartClass starter=new StartClass();
		start.addActionListener(starter);
		
		
	}
	//start button action listener. gets player name from the text field
	private class StartClass implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(name.getText().length()>11)
				player.setName(name.getText().substring(0,11));
			else
			player.setName(name.getText());
			AI.setName("AI");
			
			jf.getContentPane().remove(o);
			jf.getContentPane().add(i);
			
            jf.validate();
            remove(start);
            
		}
		
	}
	//vice president button action listener. gets the VP from the buttons
	private class VicePresident implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// IF VP1 is picked set California to get a little boost
			if(e.getSource()==VP1) {
				for(int i=0; i<states.size(); i++) {
					if(states.get(i).getName().equals("California")) {
						if(player.isDem)
							states.get(i).addPercentDem(0.06);
						else
							states.get(i).addPercentDem(-0.06);
					}
				}
			}
			// IF VP1 is picked set Texas to get a little boost
			else if(e.getSource()==VP2) {
				for(int i=0; i<states.size(); i++) {
					if(states.get(i).getName().equals("Texas")) {
						if(player.isDem)
							states.get(i).addPercentDem(0.09);
						else
							states.get(i).addPercentDem(-0.09);
					}
				}
			}
			// IF VP3 is picked set everything to get a very little boost
			else {
				for(int i=0; i<states.size(); i++) {
					if(player.isDem)
						states.get(i).addPercentDem(0.03);
					else
						states.get(i).addPercentDem(-0.03);
				}
					
					
			}
			// AI's Theoretical VP Boost
			for(int i=0; i<states.size(); i++) {
				if(states.get(i).getName().equals("New York")||states.get(i).getName().equals("Florida")||states.get(i).getName().equals("Pennsylvania")
						||states.get(i).getName().equals("Illinois")||states.get(i).getName().equals("Ohio")) {
					
					if(AI.isDem)
						states.get(i).addPercentDem(0.04);
					else
						states.get(i).addPercentDem(-0.04);
				}
			}
			//disables buttons
			VP1.setEnabled(false);
			VP2.setEnabled(false);
			VP3.setEnabled(false);
			
			//boolean that checks whether vp button was clicked becomes true
			viceClicked=true;
			
			//checks to see if all buttons were clicked in order to enable the start button
			if(viceClicked&&modeClicked&&sloganClicked) {
				start.setEnabled(true);
				drawStartGameText=true;
				repaint();
			}
			
			
		}
	}
	// action listener for the different modes. gets the game difficulty from the buttons
	private class ModeClass implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// GIVES A BOOST IF EASY, DOES NOTHING IF MEDIUM AND MAKES IT FAR MORE DIFFICULT IF HARD
			if(e.getSource()==easy) {
				i.mode="easy";
				for(int i=0; i<states.size(); i++) {
					if(AI.isDem)
						states.get(i).addPercentDem(-0.03);
					else
						states.get(i).addPercentDem(0.03);
				}
			}
			else if(e.getSource()==medium)
				i.mode="medium";
			else {
				i.mode="hard";
				for(int i=0; i<states.size(); i++) {
					if(AI.isDem)
						states.get(i).addPercentDem(0.05);
					else
						states.get(i).addPercentDem(-0.05);
				}
			}
			
			//disables buttons
			easy.setEnabled(false);
			medium.setEnabled(false);
			hard.setEnabled(false);
			
			modeClicked=true;
			
			if(viceClicked&&modeClicked&&sloganClicked) {
				start.setEnabled(true);
				drawStartGameText=true;
				repaint();
			}
			
		}
	}
	//slogan buttons action listener. sets players slogan to whatever they click
	private class SloganClass implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==slogan1)
				player.setSlogan("-For a Kinder, Gentler Nation");
			else if(e.getSource()==slogan2)
				player.setSlogan("-Conservative of the Heart");
			else
				player.setSlogan("-To Assure Continued Prosperity");
			
			slogan1.setEnabled(false);
			slogan2.setEnabled(false);
			slogan3.setEnabled(false);
			
			sloganClicked=true;
			
			if(viceClicked&&modeClicked&&sloganClicked) {
				start.setEnabled(true);
				drawStartGameText=true;
				repaint();
			}
			
			AI.setSlogan("-WE WILL TAKE OVER THE WORLD");
		}
		
		
	}
	
	// draws texts and pictures
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//draws imported images
		ImageIcon flag=new ImageIcon("flag.png");
		flag.paintIcon(this,g, 500, 400);
		ImageIcon whitehouse=new ImageIcon("whitehouse.png");
		whitehouse.paintIcon(this,g,450,600);
		g.setColor(Color.blue);
		
		//draws all of the questions to ask
		Font smallFont = new Font("Monospaced", Font.BOLD, 50);
		g.setFont(smallFont);
		g.drawString("Options", 575, 100);
		if(drawStartGameText) {
			g.drawString("Press Next", 1000, 600);
		}
		smallFont = new Font("Monospaced", Font.BOLD, 25);
		g.setFont(smallFont);
		g.drawString("Who is your Vice President?", 20, 150);
		g.drawString("Which Slogan?", 1000, 150);
		g.drawString("What is your name?", 500, 200);
		g.drawString("What mode?", 175, 650);
		
		if(!drawStartGameText) {
			
			g.drawString("Choose from all the options!", 900, 600);
		}
		else {
			g.drawString("Enter your name if you haven't already!", 800, 650);
		}
		
		//draws VP options
		smallFont = new Font("Monospaced", Font.BOLD, 17);
		g.setFont(smallFont);
		g.drawString("A senator of California", 70, 190);
		g.drawString("The governer of Texas", 80, 290);
		g.drawString("Secretary of the U.S.", 85, 390);
		
		//draws Slogan options
		g.setColor(Color.red);
		g.setFont(new Font("Monospaced", Font.BOLD, 22));
		g.drawString("-For a Kinder, Gentler Nation", 860, 220);
		g.drawString("-Conservative of the Heart", 860, 320);
		g.drawString("-To Assure Continued Prosperity", 860, 420);
		
		
	
	
		
	}
}
