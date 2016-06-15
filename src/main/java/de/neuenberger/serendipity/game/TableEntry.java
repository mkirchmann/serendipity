package de.neuenberger.serendipity.game;

import java.util.Objects;

import de.neuenberger.serendipity.ProbabilityOutcome;

/**
 * An entry for a table for having a probability and points which are useful for matches.
 * 
 * @author Michael Kirchmann
 *
 */
public class TableEntry implements Comparable<TableEntry> {
	private final ProbabilityOutcome team;
	private final Integer points;
	
	public TableEntry(ProbabilityOutcome team, Integer points) {
		super();
		this.team = team;
		this.points = points;
		
		Objects.requireNonNull(team);
		Objects.requireNonNull(points);
	}

	public ProbabilityOutcome getTeam() {
		return team;
	}

	public Integer getPoints() {
		return points;
	}

	@Override
	public int compareTo(TableEntry o) {
		return -points.compareTo(o.getPoints());
	}
	
	@Override
	public String toString() {
		return team + " " + points;
	}

}
