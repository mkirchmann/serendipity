package de.neuenberger.serendipity.game;

public enum Consequence {
	WIN1(3,0),DRAW(1,1),WIN2(0,3);
	
	private final int teamOnePoints;
	private final int teamTwoPoints;
	
	Consequence(int teamOnePoints, int teamTwoPoints) {
		this.teamOnePoints = teamOnePoints;
		this.teamTwoPoints = teamTwoPoints;
	}

	public int getTeamOnePoints() {
		return teamOnePoints;
	}

	public int getTeamTwoPoints() {
		return teamTwoPoints;
	}
}
