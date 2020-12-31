package de.neuenberger.serendipity.outage;

import java.util.function.Function;

public enum EventResult {
	WORKING(1, true), // 
	FAIL(0, false);
	
	private final int score;
	private boolean negate;
	
	
	EventResult(int score, boolean negate) {
		this.score = score;
		this.negate = negate;
	}
	
	public int getScore() {
		return score;
	}
	
	public double getProbability(double d) {
		return negate?1.0-d:d;
	}
}
