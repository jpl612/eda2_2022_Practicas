package org.eda2.practica1;

import java.util.ArrayList;

public class Player implements Comparable<Player>{

	private String playerName;
    private ArrayList<String> teams;
    private ArrayList<String> positions;
    private int score;

    public Player(String playerName, String team, String position, int score){
        this.playerName = playerName;
        this.teams = new ArrayList<String>();
        this.teams.add(team);
        this.positions = new ArrayList<String>();
        this.positions.add(position);
        this.score = score;
    }

    public String getPlayerName(){
        return this.playerName;
    }
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public ArrayList<String> getTeams(){
        return this.teams;
    }
    public void setTeams(ArrayList<String> teams){
        this.teams = teams;
    }

    public ArrayList<String> getPositions(){
        return this.positions;
    }
    public void setPositions(ArrayList<String> positions){
        this.positions = positions;
    }

    public int getScore(){
        return this.score;
    }
    public void setScore(int score){
        this.score = score;
    }

	@Override
	public int compareTo(Player p) {
		Player otro = (Player) p;
		if(this.getPlayerName()==otro.getPlayerName()) {
			return 0;
		}
		if(this.getScore()<otro.getScore()) {
			return -1;
		}
		if(this.getScore()==otro.getScore()) {
			return 0;
		}
		return 1;
	}

	@Override
	public String toString() {
		return "Player [playerName=" + playerName + ", teams=" + teams + ", positions=" + positions + ", score=" + score
				+ "]\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (playerName == null) {
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		return true;
	}
	
	
}

