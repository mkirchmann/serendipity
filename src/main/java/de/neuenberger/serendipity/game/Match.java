package de.neuenberger.serendipity.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.neuenberger.serendipity.ProbabilityOutcome;
import de.neuenberger.serendipity.em.Team;

/**
 * Models a match of two teams.
 * 
 * @author Michael Kirchmann
 *
 */
public class Match  {
	private List<MatchResult> matchResults;

	private final ProbabilityOutcome team1;
	private final ProbabilityOutcome team2;

	public Match(ProbabilityOutcome team1, ProbabilityOutcome team2, MatchResult... matchResults) {
		this.team1 = team1;
		this.team2 = team2;
		this.matchResults = Collections.unmodifiableList(new ArrayList<>(Arrays.asList(matchResults)));

		for (MatchResult matchResult : matchResults) {
			matchResult.init(this);
		}

	}

	public static Match createMatch(ProbabilityOutcome team1, ProbabilityOutcome team2, Consequence ...consequences ) {
		List<MatchResult> matchResults = new ArrayList<>();
		for (Consequence consequence : consequences) {
			MatchResult matchResult = new MatchResult(consequence);
			matchResults.add(matchResult);
		}
		return new Match(team1,team2, matchResults.toArray(new MatchResult[]{}));
	}

	public List<MatchResult> getMatchResults() {
		return matchResults;
	}

	public ProbabilityOutcome getTeam1() {
		return team1;
	}

	public ProbabilityOutcome getTeam2() {
		return team2;
	}
	
	@Override
	public String toString() {
		return team1+" - "+team2;
	}

	public static List<Match> createPairings(List<ProbabilityOutcome> listOutcome, Consequence[] consequences) {
		List<ProbabilityOutcome> linkedList = new LinkedList<>(listOutcome);
		List<Match> listOfMatches = new ArrayList<>();
		for (ProbabilityOutcome teamA : listOutcome) {
			linkedList.remove(teamA);
			for (ProbabilityOutcome teamB : linkedList) {
				Match match = Match.createMatch(teamA, teamB, consequences);
				listOfMatches.add(match);
			}
		}
		return listOfMatches;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((team1 == null) ? 0 : team1.hashCode());
		result = prime * result + ((team2 == null) ? 0 : team2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (team1 == null) {
			if (other.team1 != null)
				return false;
		} else if (!team1.equals(other.team1))
			return false;
		if (team2 == null) {
			if (other.team2 != null)
				return false;
		} else if (!team2.equals(other.team2))
			return false;
		return true;
	}
}
