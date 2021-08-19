import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
List of all changes made to the specs:
    - The funds used in campaigning was increased to $2 from $1
    - Removed boolean turn as turn control can be done in the driver
    - Added polledStates variable
*/

public class Candidate {
    String name; // canidate name
    String slogan; // canidate slogan
    boolean isDem; // (true would be Democratic, false would be Republican)
    double funds; // fund amount
    ArrayList<Issue> issueViews; // Array of Canidate Issues
    int polledStates; //number of states polled
    int campaignedStates; //number of states campaigned 
    ArrayList<State> statesFundraised = new ArrayList<State>(); //// list of states fundraised in to calculate fundraiser total
    

    // default constructor
    public Candidate() {
        name = " ";
        slogan = " ";
        isDem = true;
        funds = 0.0;
        polledStates=0;
    }

    // constructor
    public Candidate(String name, String slogan, boolean isDem, double funds, ArrayList<Issue> issueViews) {
        this.name = name;
        this.slogan = slogan;
        this.isDem = isDem;
        this.funds = funds;
        this.issueViews = issueViews;
        polledStates=0;
    }

    // gets name
    public String getName() {
        return name;
    }

    // sets name
    public void setName(String name) {
        this.name = name;
    }

    // gets slogan
    public String getSlogan() {
        return slogan;
    }

    // sets slogan
    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    // returns true if the canidate is dem, false if it is false
    public boolean isDem() {
        return isDem;
    }

    // sets isDem
    public void setDem(boolean isDem) {
        this.isDem = isDem;
    }

    // gets funds
    public double getFunds() {
        return funds;
    }

    // sets funds
    public void setFunds(int funds) {
        this.funds = funds;
    }

    // gets array of canidate issues
    public ArrayList<Issue> getIssueViews() {
        return issueViews;
    }

    // sets array of canidate issues
    public void setIssueViews(ArrayList<Issue> issueViews) {
        this.issueViews = issueViews;
    }

    // campaigns in state s and gives you a 15% boost to your polling percentage in that state
    public void campaign(State s) {
        funds -= 6.0;
        funds=Math.round(funds*10.0)/10.0;
        if (isDem) // if canidate is dem, add dem percentage by .15
            s.setPercentDem(s.getPercentDem() +(Math.random()/10+0.05));
        else // if canidate is gop, multiply republican percentage by 1.15
            s.setPercentDem(s.getPercentDem()-(Math.random()/10+0.05));
        
        campaignedStates++;
    }

    public ArrayList<State> getStatesFundraised() {
		return statesFundraised;
	}

	public void setStatesFundraised(ArrayList<State> statesFundraised) {
		this.statesFundraised = statesFundraised;
	}

	// polls state s and gives the user acess to the information about it
    public void poll(State s) {
        funds-=4.0;
        funds=Math.round(funds*10.0)/10.0;
        s.setDataVisible(true);
        polledStates++;
    }

    // fundraises in state s and gives the canidate funds according to the state's funds to give
    public void fundraise(State s) {
    	int timesFundraised = 0;
    	for (State state:statesFundraised)
    	if (s.equals(state))
    	timesFundraised++;
    	funds += s.getFundraiserTotal(isDem)/Math.pow((1+timesFundraised), 2);
    	funds=Math.round(funds*10.0)/10.0;
    	statesFundraised.add(s);
    	}

    // returns the number of issues that match with the canidate's in an array of Issues
    public int getMatchingIssues(Issue[] arr){
        int counter = 0; // counter variable
        for (Issue issue:issueViews) // scans each view in issueViews and compares them to each view in arr
            for (Issue otherissue:arr) 
                if (issue.equals(otherissue))
                    counter++; // adds one to counter for each sucessful comparison
        return counter;
    }
 // returns number of polled states
    public int getPolledStates() {
    	return polledStates;
    }
 // AI move method
    public String autoMove(String mode, ArrayList<State> states) {
    	if(mode.equals("hard")) {  // game mode hard
    		
    		if(getFunds()<=6) {
    			//fundraise logic
    			double highestAmt=0;
    			double currAmt=0;
    			State state=states.get(1);
    			for(int i=0; i<states.size(); i++) {
    				//calculates which state would have the highest fundraiser total based on the formula for fundraising
    				if(isDem)
    				currAmt+=(states.get(i).getElectoralVotes()*states.get(i).getPercentDem());
    				else
    					currAmt+=(states.get(i).getElectoralVotes()*(1-(states.get(i).getPercentDem())));
    				
    				
    				int timesFundraised = 0;
    		    	for (State fundraised:statesFundraised)
    		    	if (states.get(i).equals(fundraised))
    		    	timesFundraised++;
    				
    		    	currAmt/=Math.pow(1+timesFundraised, 2);
    				
    				if(currAmt>highestAmt) {
    					state=states.get(i);
    					highestAmt=currAmt;
    				}
    				
    				currAmt=0;
    			}
    			
    			//fundraises the best state possible
    			this.fundraise(state);
    			return "Fundraised "+state.getName();
    			
    		}
    		else {
    			//campaign logic
    			double highestAmt=0;
    			double currAmt=0;
    			State state=states.get(1);
    			//finds the best state to campaign in based on campaign formula
    			for(int i=0; i<states.size(); i++) {
    				
    				if(isDem&&states.get(i).getPercentDem()<=0.5) {
    					currAmt+=states.get(i).getElectoralVotes();
    							
    				}
    				else if(isDem==false&&states.get(i).getPercentDem()>=0.5) {
    					currAmt+=states.get(i).getElectoralVotes();
    							
    				}
    				else if(isDem==true&&states.get(i).getPercentDem()>=0.5) {
    					currAmt+=states.get(i).getElectoralVotes()/4;
    							
    				}
    				else if(isDem==false&&states.get(i).getPercentDem()<=0.5)
    					currAmt+=states.get(i).getElectoralVotes()/4;
    				
    				if(currAmt>highestAmt) {
    					highestAmt=currAmt;
    					state=states.get(i);
    				}
    					
    				currAmt=0;
    				
    			}
    			//campaignes in best state possible
    			this.campaign(state);
    			return "Campaigned "+state.getName();
    		}	
    	}
    	else{ //medium or easy 
    		
    		if(getFunds()<6) {
    			//fundraise logic
    			ArrayList<State> state= (ArrayList<State>) states.clone();
    			double AmtZ;
		    	double AmtZ2;
    			
		    	//insertion sorts the clone of the array of states based on best states to fundraise
    			for(int i=1; i<state.size(); i++) {
    				
    				int z=i;
    				
    				int timesFundraisedZ = 0;
    		    	for (State fundraised:statesFundraised)
    		    	if (state.get(z).equals(fundraised))
    		    	timesFundraisedZ++;
    		    	
    		    	int timesFundraisedZ2 = 0;
    		    	for (State fundraised:statesFundraised)
    		    	if (state.get(z-1).equals(fundraised))
    		    	timesFundraisedZ2++;
    	
    		    	if(isDem) {
    		    	AmtZ=(state.get(z).getElectoralVotes()*state.get(z).getPercentDem())/Math.pow(1+timesFundraisedZ, 2);
    		    	AmtZ2=(state.get(z-1).getElectoralVotes()*state.get(z-1).getPercentDem())/Math.pow(1+timesFundraisedZ2, 2);
    		    	}
    		    	else {
    		    		AmtZ=(state.get(z).getElectoralVotes()*(1-(state.get(i).getPercentDem())))/Math.pow(1+timesFundraisedZ, 2);
        		    	AmtZ2=(state.get(z-1).getElectoralVotes()*(1-(state.get(i).getPercentDem())))/Math.pow(1+timesFundraisedZ2, 2);
    		    	}
    				while(z>0&&AmtZ<AmtZ2) {
    					
    					State one=state.get(z-1);
    					state.set(z-1, state.get(z));
    					state.set(z, one);
    					z--;
    					if(z>0) {
    					if(isDem) {
    	    		    	AmtZ=(state.get(z).getElectoralVotes()*state.get(z).getPercentDem())/Math.pow(1+timesFundraisedZ, 2);
    	    		    	AmtZ2=(state.get(z-1).getElectoralVotes()*state.get(z-1).getPercentDem())/Math.pow(1+timesFundraisedZ2, 2);
    	    		    	}
    	    		    	else {
    	    		    		AmtZ=(state.get(z).getElectoralVotes()*(1-(state.get(i).getPercentDem())))/Math.pow(1+timesFundraisedZ, 2);
    	        		    	AmtZ2=(state.get(z-1).getElectoralVotes()*(1-(state.get(i).getPercentDem())))/Math.pow(1+timesFundraisedZ2, 2);
    	    		    	}
    					}
    					
    				}
    			
    			}
    			
    			//based on if it is medium or hard level, it picks from the top 5 best states or the top 12 best states randomly
    			Random gen=new Random();
    			int prob;
    			if(mode.equals("medium"))
    			prob=gen.nextInt(4)+46;
    			else
    				prob=gen.nextInt(10)+38;
    			//fundraises the state selected by the random number
    			this.fundraise(state.get(prob));
    			return "Fundraised "+state.get(prob).getName();
    			
    			
    		}
    		else {
    			//campaign logic
    			//creates clone of states arraylist to not change the order of the actual arraylist itself
    			ArrayList<State> state= (ArrayList<State>) states.clone();
    			Collections.shuffle(state);
    			double highestAmt=0;
    			double currAmt=0;
    			State stater=state.get(1);
    			//based on if the mode is medium or easy, it has 30-40 or 20-30 chances to iterate through the 50 length, shuffled array of states and pick the best one
    			Random gen=new Random();
    			int prob;
    			if(mode.equals("medium"))
    			prob=gen.nextInt(10)+40;
    			else
    			prob=gen.nextInt(10)+30;
    			
    			//check which state is the best to do an action in out of the number of randomly generated iterations it gets
    			for(int i=0; i<state.size(); i++) {
    				
    				if(isDem&&state.get(i).getPercentDem()<=0.5) {
    					currAmt+=state.get(i).getElectoralVotes();
    							
    				}
    				else if(isDem==false&&state.get(i).getPercentDem()>=0.5) {
    					currAmt+=state.get(i).getElectoralVotes();
    							
    				}
    				else if(isDem==true&&state.get(i).getPercentDem()>=0.5) {
    					currAmt+=state.get(i).getElectoralVotes()/4;
    							
    				}
    				else if(isDem==false&&state.get(i).getPercentDem()<=0.5)
    					currAmt+=state.get(i).getElectoralVotes()/4;
    				
    				
    				if(currAmt>highestAmt) {
    					highestAmt=currAmt;
    					stater=state.get(i);
    				}
    				prob--;	
    				currAmt=0;
    				
    				if(prob<=0)
    					break;
    				
    			}
    			//campaignes in seected state
    			this.campaign(stater);
    			return "Campaigned "+stater.getName();
    		}
    		
    	}
   	
    }
}
