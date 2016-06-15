package de.neuenberger.serendipity.game;

/**
 * Keeps a Result of a match.
 * 
 * @author Michael Kirchmann
 *
 */
public class MatchResult {

	private final Consequence consequence;
	private Match match;

	private boolean initialized;

	public MatchResult(Consequence consequence) {
		this.consequence = consequence;
	}

	public synchronized void init(Match match) {
		if (initialized) {
			throw new IllegalStateException("Initialization may only be done once.");
		}
		this.match = match;
		initialized = true;
	}

	public Match getMatch() {
		return match;
	}

	public Consequence getConsequence() {
		return consequence;
	}

	@Override
	public String toString() {
		return match + " " + consequence;
	}

}
