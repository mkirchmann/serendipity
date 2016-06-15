package de.neuenberger.serendipity.em;

import java.util.ArrayList;
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

	public ComplexFinalFactory(TeamProbabilityFactory factory) {
		super(factory);
	}

	@Override
	protected Gruppe createGruppeA() {
		Gruppe gruppe = super.createGruppeA();

		List<Match> matches = new ArrayList<>();
		matches.add(createMatch(team01France, team02Switzerland, Consequence.values()));
		matches.add(createMatch(team01France, team03Romania, Consequence.WIN1));
		matches.add(createMatch(team01France, team04Albania, Consequence.values()));
		matches.add(createMatch(team02Switzerland, team03Romania, Consequence.values()));
		matches.add(createMatch(team02Switzerland, team04Albania, Consequence.WIN1));
		matches.add(createMatch(team03Romania, team04Albania, Consequence.values()));

		ResultTable resultTable = new ResultTable(matches);
		getGruppeToResult().put(gruppe, resultTable);

		return gruppe;
	}

	@Override
	protected Gruppe createGruppeB() {
		Gruppe gruppe = super.createGruppeB();
		List<Match> matches = new ArrayList<>();

		matches.add(createMatch(team05Russia, team06Wales, Consequence.values()));
		matches.add(createMatch(team05Russia, team07England, Consequence.DRAW));
		matches.add(createMatch(team05Russia, team08Slovakia, Consequence.values()));
		matches.add(createMatch(team06Wales, team07England, Consequence.values()));
		matches.add(createMatch(team06Wales, team08Slovakia, Consequence.WIN1));
		matches.add(createMatch(team07England, team08Slovakia, Consequence.values()));

		ResultTable resultTable = new ResultTable(matches);
		getGruppeToResult().put(gruppe, resultTable);
		return gruppe;
	}

	protected Gruppe createGruppeC() {
		Gruppe gruppe = super.createGruppeC();

		List<Match> matches = new ArrayList<>();
		matches.add(createMatch(team09Germany, team10Ukraine, Consequence.WIN1));
		matches.add(createMatch(team09Germany, team12Poland, Consequence.values()));
		matches.add(createMatch(team09Germany, team11NorthernIreland, Consequence.values()));
		matches.add(createMatch(team10Ukraine, team11NorthernIreland, Consequence.values()));
		matches.add(createMatch(team10Ukraine, team12Poland, Consequence.values()));
		matches.add(createMatch(team11NorthernIreland, team12Poland, Consequence.WIN2));

		ResultTable resultTable = new ResultTable(matches);
		getGruppeToResult().put(gruppe, resultTable);
		return gruppe;
	};

	protected Gruppe createGruppeD() {
		Gruppe gruppe = super.createGruppeD();

		List<Match> matches = new ArrayList<>();
		matches.add(createMatch(team13Spain, team14Croatia, Consequence.values()));
		matches.add(createMatch(team13Spain, team15Turkey, Consequence.values()));
		matches.add(createMatch(team13Spain, team16CzechRepublic, Consequence.WIN1));
		matches.add(createMatch(team14Croatia, team15Turkey, Consequence.WIN1));
		matches.add(createMatch(team14Croatia, team16CzechRepublic, Consequence.values()));
		matches.add(createMatch(team15Turkey, team16CzechRepublic, Consequence.values()));

		ResultTable resultTable = new ResultTable(matches);
		getGruppeToResult().put(gruppe, resultTable);
		return gruppe;
	};

	protected Gruppe createGruppeE() {
		Gruppe gruppe = super.createGruppeE();

		List<Match> matches = new ArrayList<>();
		matches.add(createMatch(team17Belgium, team18Sweden, Consequence.values()));
		matches.add(createMatch(team17Belgium, team19Italy, Consequence.WIN2));
		matches.add(createMatch(team17Belgium, team20Ireland, Consequence.values()));
		matches.add(createMatch(team18Sweden, team19Italy, Consequence.values()));
		matches.add(createMatch(team18Sweden, team20Ireland, Consequence.DRAW));
		matches.add(createMatch(team19Italy, team20Ireland, Consequence.values()));

		ResultTable resultTable = new ResultTable(matches);
		getGruppeToResult().put(gruppe, resultTable);
		return gruppe;
	};

	protected Gruppe createGruppeF() {
		Gruppe gruppe = super.createGruppeF();

		List<Match> matches = new ArrayList<>();
		matches.add(createMatch(team21Hungary, team22Portugal, Consequence.values()));
		matches.add(createMatch(team21Hungary, team23Austria, Consequence.WIN1));
		matches.add(createMatch(team21Hungary, team24Iceland, Consequence.values()));
		matches.add(createMatch(team22Portugal, team23Austria, Consequence.values()));
		matches.add(createMatch(team22Portugal, team24Iceland, Consequence.DRAW));
		matches.add(createMatch(team23Austria, team24Iceland, Consequence.values()));

		ResultTable resultTable = new ResultTable(matches);
		getGruppeToResult().put(gruppe, resultTable);
		return gruppe;
	};

	Match createMatch(Team teamA, Team teamB, Consequence... consequence) {
		return Match.createMatch(getFactory().create(teamA), getFactory().create(teamB), consequence);
	}

	private Map<Gruppe, ResultTable> getGruppeToResult() {
		if (gruppeToResult == null) {
			gruppeToResult = new HashMap<>();
		}
		return gruppeToResult;
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
		return new ListSelection(1, list.toArray(new ProbabilityOutcome[] {}));
	}
}
