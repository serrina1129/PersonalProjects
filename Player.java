package Ghost2;

public class Player {
	private String name;
	private String ghost;
	
	public Player(String name) {
		this.name = name;
		ghost = "";
	}
	
	public void loseRound() {
		if(ghost.length()==0) ghost += 'G';
		else if(ghost.length()==1) ghost += 'H';
		else if(ghost.length()==2) ghost += 'O';
		else if(ghost.length()==3) ghost += 'S';
		else if(ghost.length()==4) ghost += 'T';
	}
	
	public boolean isEliminated() {
		return ghost.equals("GHOST"); 
	}
	
	public String toString() {
		return name + " (" + ghost + ")";
	}
}
