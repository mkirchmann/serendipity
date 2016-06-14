package de.neuenberger.serendipity.game;

import java.util.Objects;

import de.neuenberger.serendipity.ProbabilityOutcome;

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
		return points.compareTo(o.getPoints());
	}
	
	@Override
	public String toString() {
		return team + " " + points;
	}

}
