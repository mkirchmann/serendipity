package de.neuenberger.serendipity.game;

/**
 * Models the points awarded to both teams for a certain result.
 * @author Michael Kirchmann
 *
 */
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
