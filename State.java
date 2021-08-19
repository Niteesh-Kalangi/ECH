
/*
List of all changes made to the specs:
    - Removed boolean isDem, as that can be inferred from the double percentDem
    - Removed getters and setters for isDem
    - Renamed isColored to isDataVisible
    - Added int electoralVotes
    - Added getters and setters for electoralVotes
    
    - Added String abbriviation
*/

import java.awt.Color;

import javax.swing.JButton;

public class State implements Cloneable {
    private double percentDem; // (% voters who will vote for the Dem Candidate)
    private Issue[] relevantIssues;
    private boolean isDataVisible; // shows if the color and polling date for the state is visible to the user
    public JButton stateButton; // don’t do anything with this in constructor
    private String name; //name of state
    private String abbriviation; //abbreviation of the name of the state
    private int electoralVotes; //number of electoral votes
    private boolean isStartDem; //shows if state is dem at the start before all of the options
  

    // default constructor
    public State() {
        percentDem = .5;
        isDataVisible = false;
        name = " ";
        isStartDem=false;
        
    }

    // constructor
    public State(Issue[] relevantIssues, boolean isDataVisible, String name, String abbriviation, int electoralVotes, boolean isStartDem) {
        this.percentDem=0.5;
        this.relevantIssues = relevantIssues;
        this.isDataVisible = isDataVisible;
        this.name = name;
        this.abbriviation = abbriviation;
        this.electoralVotes = electoralVotes;
        //this.isStartDem=isStartDem;
    }

    // gets percent in state who like the dem canidate
    public double getPercentDem() {
        return percentDem;
    }

    // sets percentDem
    public void setPercentDem(double percentDem) {
        this.percentDem = percentDem;
    }
    //adds given amount to percntDem
    public void addPercentDem(double add) {
    	this.percentDem+=add;
    }

    // gets the array of relevant Issues
    public Issue[] getRelevantIssues() {
        return relevantIssues;
    }

    // sets the array of relevant Issues
    public void setRelevantIssues(Issue[] relevantIssues) {
        this.relevantIssues = relevantIssues;
    }

    // returns true if the color is shown to the user
    public boolean isDataVisible() {
        return isDataVisible;
    }

    // sets whether the user can see the states color
    public void setDataVisible(boolean isDataVisible) {
        this.isDataVisible = isDataVisible;
    }

    // gets state name
    public String getName() {
        return name;
    }

    // sets state name
    public void setName(String name) {
        this.name = name;
    }
    //gets electoral votes
    public int getElectoralVotes() {
        return electoralVotes;
    }
    //sets electoral votes
    public void setElectoralVotes(int electoralVotes) {
        this.electoralVotes = electoralVotes;
    }
    //gets total funds earned if this state is fundraised
    public double getFundraiserTotal(boolean isDem){
        if (isDem) {
           double fundsDem= electoralVotes*percentDem*.33;
            return Math.round(fundsDem*10.0)/10.0;
        }
        double fundRep=(1-percentDem)*electoralVotes*.33;
        return Math.round(fundRep*10.0)/10.0;
    }
    	//gets abbreviation of the state
    public String getAbbriviation() {
    	return abbriviation;
    }
    	//sets abbreviation of the state
    public void setAbbreviation(String set) {
    	abbriviation=set;
    
    }
    //gives the ability to clone the state
    public Object clone() throws CloneNotSupportedException{
    	return super.clone();
    }
    //gets start dem
    /*public boolean getStartDem() {
    	return isStartDem;
    }
    //sets start dem
    public void setStartDem(boolean x) {
    	isStartDem=x;
    }
    */
    //gets the color of the JButton based on isDataVisible and percentDem
    public Color getColor() {
    	if(isDataVisible==false)
    		return Color.gray;
    	else {
    		if(percentDem<=0)
    			return new Color(255,0,0);
    		else if(percentDem<=0.2)
    			return new Color(255,101,101);
    		else if(percentDem<=0.4)
    			return new Color(255,193,193);
    		else if(percentDem<=0.6)
    			return new Color(188,140,188);
    		else if(percentDem>=1)
    			return new Color(0,0,255);
    		else if(percentDem>=0.8)
    			return new Color(118,118,255);
    		else
    			return new Color(191,191,255);
    	}
    }
    //checks if an issue is in its relevant issues
    public boolean isInRelevantIssues(Issue s) {
    	for(int i=0; i<relevantIssues.length; i++) {
    		if(s.equals(relevantIssues[i]))
    			return true;
    	}
    	return false;
    }
    

}
