
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PartyScreen extends JPanel {
	public OptionsScreen o; //options screen that is being displayed
	public PartyScreen p; //partyscreen that is to be added after options are done
	public JFrame jf; //JFrame
	private Candidate player; //user candidate
	private Candidate AI; //AI candidate
	private JButton dem; //democrat option
	private JButton rep; //republican option
	private JButton start; //button to move to next screen
	private ArrayList<State> states; //list of states

	public PartyScreen(ArrayList<State> states, Candidate player, Candidate AI) {
		setLayout(null);
		setBackground(Color.white);
		
		this.states=states;
		this.player=player;
		this.AI=AI;
		PartyClass party=new PartyClass();
		
		//dem party button
		dem=new JButton("Democratic Party");
		dem.setFont(new Font("Monospaced", Font.BOLD, 25));
		dem.setBounds(400,600,300,200);
	
		dem.setBackground(Color.blue);
		add(dem);
		dem.addActionListener(party);
		
		//rep party button
		rep=new JButton("Republican Party");
		rep.setBounds(700,600,300,200);
		rep.setBackground(Color.red);
		rep.setFont(new Font("Monospaced", Font.BOLD, 25));
		add(rep);
		rep.addActionListener(party);
		
		//start button
		start=new JButton("Next");
		start.setBounds(1100,800,200,100);
		add(start);
		start.setEnabled(false);
		start.setFont(new Font("Monospaced", Font.BOLD, 35));
		StartClass starter=new StartClass();
		start.addActionListener(starter);
	
	}

	//takes out partyscreen and puts in options screen
	private class StartClass implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			
			jf.getContentPane().remove(p);
			jf.getContentPane().add(o);
			
	        jf.validate();
	        remove(start);
	        
	}
	}
	
	private class PartyClass implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==dem) {
				
				player.setDem(true);
				AI.setDem(false);
			}
			else if(e.getSource()==rep) {
				
				player.setDem(false);
				AI.setDem(true);
			}
			
			
			
			
			start.setEnabled(true);
			
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//draws red and blue rectangles
		g.setColor(Color.red);
		g.fillRect(0,0,700,1000);
		g.setColor(Color.blue);
		g.fillRect(700, 0, 700, 1000);
		g.setFont(new Font("Monospaced", Font.BOLD, 50));
		g.setColor(Color.black);
		g.drawString("Which Party?", 530, 100);
		ImageIcon democrat=new ImageIcon("democrat.png");
		democrat.paintIcon(this, g,440, 400);
		ImageIcon republican=new ImageIcon("rebublican.png");
		republican.paintIcon(this, g, 830, 410);
		
	}
	
	

}
