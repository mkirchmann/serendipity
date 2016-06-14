package de.neuenberger.serendipity.em;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.neuenberger.serendipity.ListSelection;
import de.neuenberger.serendipity.ProbabilityOutcome;
import de.neuenberger.serendipity.ProbabilityProcess;
import de.neuenberger.serendipity.em.Team.TeamProbabilityFactory;
import de.neuenberger.serendipity.game.Consequence;
import de.neuenberger.serendipity.game.Match;
import de.neuenberger.serendipity.game.MatchResult;
import de.neuenberger.serendipity.game.ResultTable;

public class ComplexFinalFactory extends SimpleFinalFactory {

	Map<Gruppe, ResultTable> gruppeToResult;

	private List<Match> knownResults;

	public ComplexFinalFactory(TeamProbabilityFactory factory) {
		super(factory);
	}
	
	
	@Override
	public Gruppe createGroup(Team... teams) {
		List<ProbabilityOutcome> listOutcome = new ArrayList<>();
		for (Team team : teams) {
			ProbabilityOutcome outcome = getFactory().create(team);
			listOutcome.add(outcome);
		}
		
		Consequence[] consequences = Consequence.values();
		List<Match> listOfMatches = Match.createPairings(listOutcome, consequences);

		List<Match> listOfMatchesWithReplaced = new ArrayList<>(listOfMatches.size());
		List<Match> copyOfKnownResults = new ArrayList<>(getKnownResults());
		for (Match match2 : listOfMatches) {
			Match replacement = null;
			for (Match match : copyOfKnownResults) {
				if (match2.equals(match)) {
					replacement = match;
					break;
				}
			}
			if (replacement != null) {
				copyOfKnownResults.remove(replacement);
				listOfMatchesWithReplaced.add(replacement);
				System.out.println(match2 + " replaced with " + replacement);
			} else {
				listOfMatchesWithReplaced.add(match2);
			}
		}


		Gruppe group = super.createGroup(teams);
		if (gruppeToResult==null) {
			gruppeToResult = new HashMap<>();
		}
		gruppeToResult.put(group, new ResultTable(listOfMatches));
		return group;
	}

	private List<Match> getKnownResults() {
		if (knownResults == null) {
			knownResults = Collections.unmodifiableList(createKnownResults());
		}
		return knownResults;
	}

	private List<Match> createKnownResults() {
		List<Match> knownMatches = new ArrayList<>();

		knownMatches.add(createMatch(team09, team10, Consequence.WIN1));

		return knownMatches;
	}

	private Match createMatch(Team teamA, Team teamB, Consequence consequence) {
		MatchResult result = new MatchResult(consequence);
		return new Match(getFactory().create(teamA), getFactory().create(teamB), result);
	}

	@Override
	protected ProbabilityProcess thirdOf(Gruppe gruppe1, Gruppe gruppe2, Gruppe gruppe3) {
		// TODO Auto-generated method stub
		return super.thirdOf(gruppe1, gruppe2, gruppe3);
	}
	
	@Override
	protected ProbabilityProcess secondOf(Gruppe gruppe) {
		return selectPosition(gruppe, 1);
	}

	@Override
	protected ProbabilityProcess selectBestOf(Gruppe gruppe) {
		return selectPosition(gruppe, 0);
	}

	private ProbabilityProcess selectPosition(Gruppe gruppe, int position) {
		ResultTable table = gruppeToResult.get(gruppe);
		List<ProbabilityOutcome> list = table.selectPosition(position);
		return new ListSelection(1, list.toArray(new ProbabilityOutcome[]{}));
	}
}
