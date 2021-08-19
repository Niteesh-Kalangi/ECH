import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Graphics.*;

public class Driver {
	private static JFrame jf;
	private static StartScreen s;
	private static OptionsScreen o;
	private static PartyScreen p;
	private static IssueScreen i;
	private static GameScreen g;
	private static EndScreen e;

	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		
		Random gen=new Random();
		ArrayList<State> states=new ArrayList<State>();
		ArrayList<Issue> list=new ArrayList<Issue>();
		//adds democratic and republican methods to the list of issues
		list.addAll(demIssueList());
		list.addAll(repIssueList());
		// randomizes states
		//completely initializes the array of states by giving each state a name, its abbreviation, a set of random issues, and whether its info is disclosed
		for(int i=0; i<50; i++) {
			State r=new State();
			r.setName(stateNameList()[i]);
			r.setAbbreviation(stateAbbriviationsList()[i]);
			
			r.setElectoralVotes(electoralVotes()[i]);
			r.setPercentDem(0.15+Math.random()*0.7);
			
			
			Issue[] tempIssues=new Issue[3];
			for(int j=0; j<tempIssues.length; j++) {
				
				
					int random=gen.nextInt(list.size());
					
					tempIssues[j]=list.get(random);
					list.remove(random);
					Collections.shuffle(list);
					
					
			}
			list.clear();
			list.addAll(demIssueList());
			list.addAll(repIssueList());
			r.setRelevantIssues(tempIssues);
			
			// 30% chance of states being visible
			if(Math.random()<=0.5)
				r.setDataVisible(true);
			
			states.add((State)r.clone());
			
			
		}
		
		// Adds player and the AI to the game
		Candidate player=new Candidate();
		Candidate AI=new Candidate();
	
		
		//initializations of all the screens
		jf=new JFrame();
		s=new StartScreen(jf, s);
		p=new PartyScreen(states, player, AI);
		o=new OptionsScreen(states,player,AI);
		i=new IssueScreen(states, player, AI);
		g= new GameScreen(states,player,AI);
		e=new EndScreen(states,player,AI);
		
		// Start screen necessary variables
		s.s=s;
		s.p=p;
		
		//party screen necessary vars
		p.p=p;
		p.o=o;
		p.jf=jf;
		
	
		//options screen neccesarry vars
		o.o=o;
		o.jf=jf;
		o.i=i;
		
		//issuescreen necessary vars
		i.jf=jf;
		i.i=i;
		i.g=g;
		
		//game screen necessary vars
		g.g=g;
		g.es=e;
		g.jf=jf;
		
		
		
		
		
		//JFrame modifications
		jf.setTitle("start");
		jf.setSize(1400,1000);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().add(s);
		
		
		
		
	}
    // returns a String[] of all fifty US state names
    public static String[] stateNameList(){
        String[] statenames = {"Alabama","Alaska","Arizona","Arkansas","California","Colorado","Connecticut","Delaware",
        "District Of Columbia","Florida","Georgia","Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky",
        "Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska",
        "Nevada","New Hampshire","New Jersey","New Mexico","New York","North Carolina","North Dakota","Ohio","Oklahoma",
        "Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia",
        "Washington","West Virginia","Wisconsin","Wyoming"};
        return statenames;
    }

    // returns a String[] of all fifty US state abbriviations
    public static String[] stateAbbriviationsList(){
        String[] stateabbriviations = {"AL","AK", "AZ","AR","CA","CO","CT","DE","DC","FL","GA","HI","ID","IL","IN","IA","KS",
        "KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI","SC",
        "SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};
        return stateabbriviations;
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
    //returns electoral votes of all the states in alphabetical order
    public static int[] electoralVotes() {
    	int[] electoralVotes= {9,6,11,6,55,9,7,3,3,29,16,4,4,20,11,6,6,8,8,4,10,11,16,10,6,10,3,5,6,4,14,5,29,15,3,18,7,7,20,4,9,3,11,38,6,3,13,12,5,10,3};
    	return electoralVotes;
    }



	

		
		
		
	}


