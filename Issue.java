
public class Issue {
	private String name; //name of the issue
    private boolean isDem; //true if they picked the democratic side of the issue, false if republican
    private String description; //description of the issue

    public Issue() {
        super();
    }

    public Issue(String name, boolean isDem, String description) {
        this.name = name;
        this.isDem = isDem;
        this.description=description;
    }
    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsDem() {
        return isDem;
    }

    public void setSide(boolean side) {
        this.isDem = side;
    }
    public String getDescription() {
    	return description;
    }
    public void setDescription(String description) {
    	this.description=description;
    }
    @Override
    public String toString() {
    	return name;
        //return "[Issue:"+" name- " + name + ", side- " + isDem + "]";
    }
    //compares two issues
    public boolean equals(Issue other){ 
        if (name.equals(other.getName()) && isDem == other.getIsDem())
            return true;
        return false;
    }
    
}
