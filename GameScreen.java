import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/*
 Ways to fix airplane-
 repaint endScreen es
 do buffered image
 put the image icon initialization in the constructor
 
 */

/*
 Ideas-
 put a rectangle around where you show state info. 
 Have a button that when it is clicked, it puts the status of the game in the info rectangle
 */
 
public class GameScreen extends JPanel {
	public JFrame jf; //the JFrame 
	public GameScreen g; //gamescreen
	public EndScreen es; //endscreen to add
	private Candidate player; //player candidate
	private Candidate AI; //AI candidate
	private ArrayList<State> states; //states list
	private JButton[] infoButtons=new JButton[50]; //all info buttons
	private JButton fundraise; //button to fundraise
	private JButton poll; //button to poll
	private JButton adCampaign; //adCampaign button
	private JButton infoGame; //button that shows the game info
	public String mode; //button that is the mode aka hard, medium, easy
	public boolean displayInfo=false; //boolean to check whether to display the info
	private StateClass game=new StateClass(); //initialization of state buttons action listener
	private ActionClass action=new ActionClass(); //initialization of action buttons action listener
	private InfoClass info=new InfoClass(); //initialization of info buttons action listener
	private GameInfoClass gameInfo=new GameInfoClass(); //initialization of game info button action listener
	private int turns=25; //number of turns there are in the game
	private State selectedInfo; //shows the selected info of which state to display
	private boolean drawInfo=false; //checks whether to draw the info
	private boolean drawGameInfo=true; //checks whether to draw game info
	private int selectedPos; //position of the selected state
	private boolean drawStateText=true; //checks whether to draw the state text
	private boolean drawActionText=false; //checks whether to draw the action text
	private boolean pollable=false; //checks whether you can poll
	private boolean campaignable=false; //checks whether you can campaign
	//private boolean fundraisable=false; //checks whether you can fundraise 
	private String AIMove=""; //String that shows the move the AI made
	
	public GameScreen(ArrayList<State> states, Candidate player, Candidate AI) {
		setLayout(null);
		setBackground(Color.white);
		this.states=states;
		this.player=player;
		this.AI=AI;
		
		
		int count =0;
		int countx=0;
		int county=0;
		//draws all of the states as buttons along with the info buttons that are available
		for(int i=0; i<5; i++) {
			for(int j=0; j<10; j++) {
				states.get(count).stateButton=new JButton(states.get(count).getAbbriviation());
				states.get(count).stateButton.setBounds(countx*110, county*100+100, 100,50);
				states.get(count).stateButton.setBackground(states.get(count).getColor());
				infoButtons[count]=new JButton("Info");
				infoButtons[count].setBounds(countx*110+25,county*100+150,50,30);
				infoButtons[count].setFont(new Font("Comic Sans MS", Font.BOLD, 8));
				add(states.get(count).stateButton);
				states.get(count).stateButton.addActionListener(game);
				states.get(count).stateButton.setEnabled(false);
				if(states.get(count).isDataVisible())
					add(infoButtons[count]);
				infoButtons[count].addActionListener(info);
				
				count++;
				countx++;
			}
			countx=0;
			county++;
		}
		//fundriase button
		fundraise=new JButton("Fundraise");
		fundraise.setBounds(450,800,150,100);
		fundraise.setBackground(new Color(0,255,77));
		fundraise.setFont(new Font("Serif", Font.BOLD, 20));
		add(fundraise);
		fundraise.setEnabled(false);
		
		//polling button
		poll=new JButton("Poll");
		poll.setBounds(600,700,150,100);
		poll.setBackground(new Color(230,0,255));
		poll.setFont(new Font("Serif", Font.BOLD, 20));
		add(poll);
		poll.setEnabled(false);
		
		//campaigning button
		adCampaign=new JButton("Ad Campaign");
		adCampaign.setBounds(750,800,150,100);
		adCampaign.setBackground(new Color(255,230,0));
		adCampaign.setFont(new Font("Serif", Font.BOLD, 20));
		add(adCampaign);
		adCampaign.setEnabled(false);
		
		//game info button
		infoGame=new JButton("Game Info");
		infoGame.setBounds(615,820,120,75);
		infoGame.setFont(new Font("Serif", Font.BOLD, 15));
		add(infoGame);
		infoGame.addActionListener(gameInfo);
		
			//Enabling the state button
				for(int i=0; i<states.size(); i++) {
					states.get(i).stateButton.setEnabled(true);
				}
			
		
	
		
		
	}
	//action listener for all the state buttons
	private class StateClass implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			g.repaint();
			for(int i=0; i<states.size(); i++) {
				if(e.getSource()==states.get(i).stateButton) {
					
					selectedPos=i;
					break;
				}
			}
			for(int i=0; i<states.size(); i++) {
				states.get(i).stateButton.setEnabled(false);
				states.get(i).stateButton.removeActionListener(game);
			}
			
			
			fundraise.setEnabled(true);
			if(player.getFunds()>=6) {
				adCampaign.setEnabled(true);
				campaignable=true;
			}
			/*else {
				adCampaign.setEnabled(false);
				campaignable=false;
			}
			*/
			if(player.getFunds()>=4) {
				poll.setEnabled(true);
				pollable=true;
			}
			/*else {
				poll.setEnabled(false);
				pollable=false;
			}
			*/
			/*if(states.get(selectedPos).isDataVisible()) {
				fundraise.setEnabled(true);
				fundraisable=true;
			}
			/*else {
				fundraise.setEnabled(false);
				fundraisable=false;
			}
			*/
			
			AIMove="";
			repaint();
			
			
			
			
			fundraise.addActionListener(action);
			adCampaign.addActionListener(action);
			poll.addActionListener(action);
			
			drawActionText=true;
			drawStateText=false;
			repaint();
			
		}
	}
	//action listener for the fundraise, poll, and campaign buttons.
	//If fundraise is clicked, it fundraises, if campaign is clicked, it campaignes, if poll is clicked it polls all in the state that is selected
	private class ActionClass implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			
			
			if(e.getSource()==fundraise) {
				player.fundraise(states.get(selectedPos));
			}
			else if(e.getSource()==poll) {
				player.poll(states.get(selectedPos));
				states.get(selectedPos).stateButton.setBackground(states.get(selectedPos).getColor());
				states.get(selectedPos).setDataVisible(true);
				add(infoButtons[selectedPos]);
			}
			else
				player.campaign(states.get(selectedPos));
			//AI making its move
			AIMove=AI.autoMove(mode, states);
			
			repaint();
			for(int i=0; i<states.size(); i++) {
				states.get(i).stateButton.setEnabled(true);
				states.get(i).stateButton.addActionListener(game);
				states.get(i).stateButton.setBackground(states.get(i).getColor());
			}
			fundraise.removeActionListener(action);
			adCampaign.removeActionListener(action);
			poll.removeActionListener(action);
			fundraise.setEnabled(false);
			poll.setEnabled(false);
			adCampaign.setEnabled(false);
			turns--;
			if(turns<=0) {
				//removes the gamescreen and adds the endscreen if the weeks left is 0
				jf.getContentPane().remove(g);
				jf.getContentPane().add(es);
				jf.validate();
			}
			
			drawStateText=true;
			drawActionText=false;
			g.repaint();
			
			
			
		}
		}
	//Action Listener for the info buttons. When you poll a state, its info button becomes available along with the color of the state
	private class InfoClass implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			for(int i=0; i<infoButtons.length; i++) {
				if(e.getSource()==infoButtons[i]) {
					selectedInfo=states.get(i);
					drawInfo=true;
					drawGameInfo=false;
					repaint();
				}
			}
		}
	}
	//game info button action listener. shows the issues, total polled states and total campaigned states of the AI and the player along with the AI difficulty
	private class GameInfoClass implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			drawInfo=false;
			drawGameInfo=true;
			repaint();
		}
	}
	
			
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	
		
		int startx=-5;
		int starty=95;
		int count=0;
		
		//adding decoration to make the buttons look nicer
		for(int i=0; i<5; i++) {
			
			for(int j=0; j<10; j++) {
				g.setColor(states.get(count).getColor());
				states.get(count).stateButton.setBackground(states.get(count).getColor());
				g.fillOval(startx, starty, 110, 60);
				startx+=110;
				count++;
			}
			starty+=100;
			startx=-5;
		}
		
		Font f= new Font("Comic Sans MS", Font.BOLD,18);
		g.setFont(f);
		g.setColor(Color.blue);
		g.drawString(player.getSlogan(), 100,930);
		g.drawString(AI.getSlogan(), 1000,930);
		//draws the not possible sign above actions that aren't possible
		if(campaignable==false) {
			g.drawString("Not Possible", 600, 690);
		}
		if(pollable==false) {
			g.drawString("Not Possible", 750, 790);
		}
		/*if(fundraisable==false) {
			g.drawString("Not Possible", fundraise.getX(), 690);
		}
		*/
		g.setFont( new Font("Comic Sans MS", Font.BOLD,45));
		if(player.isDem()) {
			g.setColor(Color.blue);
			g.drawString("Candidate: "+player.getName(), 70, 770);
			g.setColor(Color.red);
			g.drawString("Candidate: "+AI.getName(), 970, 770);
		}
		else {
			g.setColor(Color.red);
			g.drawString("Candidate: "+player.getName(), 70, 770);
			g.setColor(Color.blue);
			g.drawString("Candidate: "+AI.getName(), 970, 770);
		}
	
		g.setColor(Color.GREEN);
		g.fillRect(30, 800, 120, 100);
		g.fillRect(930,800,120,100);
		g.setColor(Color.black);
		
		//instruction on what to do
		if(drawStateText)
			g.drawString("Pick a state", 500,650);
		
		g.setFont( new Font("Comic Sans MS", Font.BOLD,40));
		//draws the move that the AI made
		if(!AIMove.equals(""))
			g.drawString("AI Move: ", 950, 650);
		
		g.drawString(AIMove, 850, 700);
		
		g.drawString("Weeks Left: "+turns, 30, 650);
		
		//instruction on what to do
		if(drawActionText) {
			g.setFont(new Font("Comic Sans MS", Font.BOLD,35));
			g.drawString("Pick an action to do in the state", 400, 650);
		}
		//draws funds of the players
		g.setColor(Color.black);
		g.setFont(new Font("Comic Sans MS", Font.BOLD,40));
		g.drawString(""+player.getFunds()+"$", 40, 870);
		g.drawString(""+AI.getFunds()+"$", 940, 870);
		f= new Font("Comic Sans MS", Font.BOLD,35);
		g.setFont(f);
		g.drawString("Polled States:" + player.getPolledStates(), 170, 850);
		
		//draws the info of the state you selected
		if(drawInfo==true&&selectedInfo!=null) {
			g.setFont(new Font("Comic Sans MS", Font.BOLD,27));
			g.setColor(Color.blue);
			
			g.drawString(selectedInfo.getName(), 1100, 150);
			g.drawString("Relevant Issues:", 1100, 200);
			
			g.setFont(new Font("Comic Sans MS", Font.BOLD,22));
			
			for(int i=0; i<selectedInfo.getRelevantIssues().length; i++) {
				g.drawString(selectedInfo.getRelevantIssues()[i].getName(), 1100, 230+30*i);
			}
			g.drawString("Electoral Votes:"+ selectedInfo.getElectoralVotes(),1100,370);
			
			if(player.getStatesFundraised().contains(states.get(selectedPos))) {
				g.drawString("Already Fundraised", 1100, 420);
			}
			else {
				g.drawString("Not Fundraised", 1100, 420);
			}
		}
		//draws the game info
		if(drawGameInfo==true) {
			g.setFont(new Font("Comic Sans MS", Font.BOLD,27));
			g.setColor(Color.blue);
			
			g.drawString(player.getName()+": ", 1100, 100);
			g.setFont(new Font("Comic Sans MS", Font.BOLD,22));
			g.drawString("Issue Focuses-", 1100, 130);
			
			for(int i=0; i<player.getIssueViews().size(); i++) {
				g.drawString(player.getIssueViews().get(i).getName(), 1100, 170+i*20);
			}
			
			g.drawString("Polled States: "+player.polledStates, 1100, 270);
			g.drawString("Campaigned States: "+player.campaignedStates, 1100, 300);
		
			
			g.setFont(new Font("Comic Sans MS", Font.BOLD,27));
			g.setColor(Color.blue);
			
			g.drawString(AI.getName()+": ", 1100, 350);
			g.setFont(new Font("Comic Sans MS", Font.BOLD,22));
			g.drawString("Issue Focuses-", 1100, 380);
			
			for(int i=0; i<AI.getIssueViews().size(); i++) {
				g.drawString(AI.getIssueViews().get(i).getName(), 1100, 420+i*20);
			}
			
			g.drawString("Campaigned States: "+AI.campaignedStates, 1100, 520);
			g.drawString("Difficulty: "+mode, 1100, 550);
			
			
			
			
			
		}
		
		//draws the info about what the different colors of the buttons mean
		g.setFont(new Font("Comic Sans MS", Font.BOLD,13));
		g.setColor(new Color(255,0,0));
		g.fillRect(50, 35,30, 30);
		g.drawString("Percent Dem<=0", 10, 20);
		
		g.setColor(new Color(255,101,101));
		g.fillRect(210, 35,30, 30);
		g.drawString("0<Percent Dem<=0.2", 160, 20);
		
		g.setColor(new Color(255,193,193));
		g.fillRect(370, 35,30, 30);
		g.drawString("0.2<Percent Dem<=0.4", 320, 20);
		
		g.setColor(new Color(188,140,188));
		g.fillRect(530, 35,30, 30);
		g.drawString("0.4<Percent Dem<=0.6", 480, 20);
		
		g.setColor(new Color(191,191,255));
		g.fillRect(690, 35,30, 30);
		g.drawString("0.6<Percent Dem<=0.8", 640, 20);
		
		g.setColor(new Color(118,118,255));
		g.fillRect(850, 35,30, 30);
		g.drawString("0.8<Percent Dem<=1", 800, 20);
		
		g.setColor(new Color(0,0,255));
		g.fillRect(1010, 35,30, 30);
		g.drawString("Percent Dem>=1", 960, 20);
		
		g.setColor(Color.gray);
		g.fillRect(1170, 35,30, 30);
		g.drawString("Undisclosed", 1140, 20);
		
		
	}
	

}
