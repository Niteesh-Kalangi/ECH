import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class IssueScreen extends JPanel{
	public String mode; //game mode (hard/easy)
	private Candidate player; //player candidate
	private Candidate AI; //AI candidate
	private ArrayList<State> states; //list of states
	public IssueScreen i; //issue screen that is being displayed
	public JFrame jf; //JFrame
	public GameScreen g; //Game screen that is to be added after issue selection is done
	private JButton[] issueButtons; //list of issue Buttons
	private JButton[] descriptionButtons; //list of descriptions of issue buttons
	private int numIssues; //number of issues
	private ArrayList<Issue> issues=new ArrayList<Issue>(); //list of issues
	private String drawnDescription=""; //description of current issue selected
	
	public IssueScreen(ArrayList<State> states, Candidate player, Candidate AI) {
		setLayout(null);
		setBackground(Color.white);
		this.states=states;
		this.player=player;
		this.AI=AI;
		
		//initialization of selection of issue and description buttons
		issueButtons=new JButton[6];
		descriptionButtons=new JButton[6];
		IssueClass issueViews=new IssueClass();
		DescriptionClass description=new DescriptionClass();
		for(int i=0; i<issueButtons.length; i++) {
			issueButtons[i]=new JButton();
			issueButtons[i].setBounds(335, i*110+100,75,75);
			issueButtons[i].setIcon(new ImageIcon("checkmark.png"));
			add(issueButtons[i]);
			issueButtons[i].addActionListener(issueViews);
			
			descriptionButtons[i]=new JButton("Description");
			descriptionButtons[i].setBounds(500,issueButtons[i].getY()+63, 100,50);
			add(descriptionButtons[i]);
			descriptionButtons[i].addActionListener(description);
		}
	
	}
	//action listener for description buttons. If clicked, description is shown
	private class DescriptionClass implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<descriptionButtons.length; i++) {
				if(e.getSource()==descriptionButtons[i]) {
					
					if(player.isDem()) {
						drawnDescription=demIssueList().get(i).getDescription();
						repaint();
						break;
					}
					else {
						drawnDescription=repIssueList().get(i).getDescription();
						repaint();
						break;
					}
					
				}
			}
			
		}
	}
	//action listener for issue buttons. add selected issues to the canidate's key issues
	private class IssueClass implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			for(int i=0; i<issueButtons.length; i++) {
				if(e.getSource()==issueButtons[i]) {
					if(player.isDem)
						issues.add(demIssueList().get(i));
					else
						issues.add(repIssueList().get(i));
					
					issueButtons[i].setEnabled(false);
					numIssues++;
					break;
				}
			}
			
			if(numIssues>=3) {
				
				player.setIssueViews(issues);
				
				
				for(int i=0; i<issueButtons.length; i++) {
					issueButtons[i].setEnabled(false);
				}
				
				//AI issue selection code
				int[] issueRanks=new int[6];
				
				for(int i=0; i<states.size(); i++) {
					
					if(AI.isDem) {
						
						for(int j=0; j<issueRanks.length; j++) {
							if(states.get(i).isInRelevantIssues(demIssueList().get(j)))
								issueRanks[j]++;
						}
												
					}
					else {
						for(int j=0; j<issueRanks.length; j++) {
							if(states.get(i).isInRelevantIssues(repIssueList().get(j)))
								issueRanks[j]++;
						}
						
						
					}
				}
					int[] posRanks= {0,1,2,3,4,5};
					
					
					for(int k=1; k<issueRanks.length;k++) {
						int z=k;
						
						while(z>0&&issueRanks[z]<issueRanks[z-1]) {
							
							int temp=posRanks[z];
							posRanks[z]=posRanks[z-1];
							posRanks[z-1]=temp;
							
							int temp2=issueRanks[z];
							issueRanks[z]=issueRanks[z-1];
							issueRanks[z-1]=temp;
							
							z--;
							
						}
						
						
					}
					ArrayList<Issue> issueList=new ArrayList<Issue>();
					if(mode.equals("hard")) {
						if(AI.isDem()) {
							issueList.add(demIssueList().get(posRanks[5]));
							issueList.add(demIssueList().get(posRanks[4]));
							issueList.add(demIssueList().get(posRanks[3]));
						}
						else {
							issueList.add(repIssueList().get(posRanks[5]));
							issueList.add(repIssueList().get(posRanks[4]));
							issueList.add(repIssueList().get(posRanks[3]));
						}
							
					}
					else if(mode.equals("medium")) {
						if(AI.isDem()) {
							issueList.add(demIssueList().get(posRanks[4]));
							issueList.add(demIssueList().get(posRanks[3]));
							issueList.add(demIssueList().get(posRanks[2]));
						}
						else {
							issueList.add(repIssueList().get(posRanks[4]));
							issueList.add(repIssueList().get(posRanks[3]));
							issueList.add(repIssueList().get(posRanks[2]));
						}
					}
					else {
						if(AI.isDem()) {
							issueList.add(demIssueList().get(posRanks[3]));
							issueList.add(demIssueList().get(posRanks[2]));
							issueList.add(demIssueList().get(posRanks[1]));
						}
						else {
							issueList.add(repIssueList().get(posRanks[3]));
							issueList.add(repIssueList().get(posRanks[2]));
							issueList.add(repIssueList().get(posRanks[1]));
						}
					}
					
					AI.setIssueViews(issueList);
				
				
				
				//code to apply the effect of the issues selected on the states percent dem
				for(int i=0; i<states.size(); i++) {
					if(player.isDem())
						states.get(i).addPercentDem(player.getMatchingIssues(states.get(i).getRelevantIssues())*0.04);
					else
						states.get(i).addPercentDem(-player.getMatchingIssues(states.get(i).getRelevantIssues())*0.04);
					
					if(AI.isDem())
						states.get(i).addPercentDem(AI.getMatchingIssues(states.get(i).getRelevantIssues())*0.04);
					else
						states.get(i).addPercentDem(-AI.getMatchingIssues(states.get(i).getRelevantIssues())*0.04);
				}
				g.mode=mode;
				jf.getContentPane().add(g);
				jf.getContentPane().remove(i);
				jf.invalidate();
	            jf.validate();
				
				}
			
			
			}
			
		}
	
	//draws issue descriptions and names
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(player.isDem)
			setBackground(Color.blue);
		else
			setBackground(Color.red);
		ArrayList<Issue> list=new ArrayList<Issue>();
		if(player.isDem())
			list=demIssueList();
		else
			list=repIssueList();
		
		g.setColor(Color.white);
		g.fillRect(860,390,500,60);
		g.setColor(Color.black);
		g.setFont(new Font("Sans Serif", Font.BOLD, 30));
		g.drawString("SELECT 3 ISSUES TO FOCUS ON",870,430);
		//shows what the issues are
		for(int i=0; i<list.size(); i++) {
			g.drawString(""+(i+1)+". "+list.get(i), 430, i*110+150);
		}
		g.setFont(new Font("Sans Serif", Font.BOLD, 50));
		g.drawString("Issue Selection", 360, 50);
		
		//draws description of the issues
		g.setColor(Color.white);
		if(!drawnDescription.equals("")) {
			g.drawString("Description:", 400, 850);
		}
		
		g.setFont(new Font("Sans Serif", Font.BOLD, 20));
		g.drawString(drawnDescription, 40, 900);
	}
	// returns a Issue[] of all current issues facing america in the democratic stance
	 public static ArrayList<Issue> demIssueList(){
	        ArrayList<Issue> issuelist = new ArrayList<Issue>();
	        
	        issuelist.add(new Issue("Immigration Reform", true, "We must perform our immigration system to make it fair and faster, while maintaining our national security."));
	        issuelist.add(new Issue("Global Cooperation", true, "21st century challenges require participation organizations like the United Nations and NATO to make the world a safer place"));
	        issuelist.add(new Issue("Funding for Education", true, "The federal government has a responsibility to help children get the best public K-12 education and support affordable college options for all."));
	        issuelist.add(new Issue("LGBTQ+ Rights", true, "We support equality for the LGBTQ+ people. We must protect them from discrimination in all areas."));
	        issuelist.add(new Issue("Preventing Gun Violence", true, "Sensible action, like restricting the purchase of some firearms, must be taken to address gun violence in our communities."));
	        issuelist.add(new Issue("Cleaner Energy", true, "Renewable energy's boost economy through high-paying jobs, fight climate change, and reduce foreign oil use."));
	        return issuelist;
	    }
	// returns a Issue[] of all current issues facing america in the republican stance
	    public static ArrayList<Issue> repIssueList() {
	    	ArrayList<Issue> issuelist = new ArrayList<Issue>();
	    	issuelist.add(new Issue("Limited government", false, "The government needs to spend less and limited role in Americans daily lives."));
	    	issuelist.add(new Issue("Gun Rights", false, "Law abiding citizen should not be restricted from going ownership by the federal government. The second amendment must be protected."));
	    	issuelist.add(new Issue("Lower Taxes", false, "We should lowers taxes so citizens can keep more of what they earn and choose to save, spend, or invest as they see fit."));
	    	issuelist.add(new Issue("School Choice", false, "A greater portion of education funding should go directly to families so they can choose the best public or private schools for the children."));
	    	issuelist.add(new Issue("Securing the Border", false, "The humanitarian a national crisis security crisis on our southern border requires immediate and significant action."));
	    	issuelist.add(new Issue("Job Creation", false, "Let's put America back to work. We need policies that help businesses grow and keep high-paying jobs in the US."));
	    	return issuelist;
	    }
}
