package de.neuenberger.serendipity.em;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.neuenberger.serendipity.ListSelection;
import de.neuenberger.serendipity.ProbabilityOutcome;
import de.neuenberger.serendipity.ProbabilityProcess;
import de.neuenberger.serendipity.em.Team.TeamProbabilityFactory;
import de.neuenberger.serendipity.game.Consequence;
import de.neuenberger.serendipity.game.Match;
import de.neuenberger.serendipity.game.ResultTable;

public class ComplexFinalFactory extends SimpleFinalFactory {

	public ComplexFinalFactory(TeamProbabilityFactory factory) {
		super(factory);
	}
	
	Map<Gruppe, ResultTable> gruppeToResult;
	
	@Override
	public Gruppe createGroup(Team... teams) {
		List<ProbabilityOutcome> listOutcome = new ArrayList<>();
		for (Team team : teams) {
			ProbabilityOutcome outcome = getFactory().create(team);
			listOutcome.add(outcome);
		}
		
		Consequence[] consequences = Consequence.values();
		List<Match> listOfMatches = Match.createPairings(listOutcome, consequences);
		Gruppe group = super.createGroup(teams);
		if (gruppeToResult==null) {
			gruppeToResult = new HashMap<>();
		}
		gruppeToResult.put(group, new ResultTable(listOfMatches));
		return group;
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
