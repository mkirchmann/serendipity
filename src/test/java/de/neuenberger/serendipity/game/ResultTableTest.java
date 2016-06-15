package de.neuenberger.serendipity.game;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Test;

import de.neuenberger.serendipity.ProbabilityOutcome;
import de.neuenberger.serendipity.SimpleProbabilityOutcome;

public class ResultTableTest {
	private static final Offset<Double> OFFSET = Offset.offset(0.0000000001);
	private static final String TEAM_A = "a";
	private static final String TEAM_B = "b";
	private static final String TEAM_C = "c";
	private static final String TEAM_D = "d";
	private final SimpleProbabilityOutcome t1 = new SimpleProbabilityOutcome(TEAM_A);
	private final SimpleProbabilityOutcome t2 = new SimpleProbabilityOutcome(TEAM_B);
	private final SimpleProbabilityOutcome t3 = new SimpleProbabilityOutcome(TEAM_C);
	private final SimpleProbabilityOutcome t4 = new SimpleProbabilityOutcome(TEAM_D);

	@Test
	public void testPairing() {
		List<Match> pairings = Match.createPairings(Arrays.asList(t1, t2, t3, t4), Consequence.values());

		Assertions.assertThat(pairings).hasSize(6);
		ResultTable resultTable = new ResultTable(pairings);

		List<ProbabilityOutcome> probabilityToBecomeFirst = resultTable.selectPosition(0);
		Assertions.assertThat(probabilityToBecomeFirst).hasSize(4);
		double probabilities = ProbabilityOutcome.sumAllProbabilities(probabilityToBecomeFirst);
		Assertions.assertThat(probabilities).isCloseTo(1.0, Offset.offset(0.000000001));
		for (ProbabilityOutcome probabilityOutcome : probabilityToBecomeFirst) {
			System.out.println(probabilityOutcome);
			Assertions.assertThat(probabilityOutcome.getProbability()).isCloseTo(0.25, Offset.offset(0.00000001));
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalAccess() {
		ResultTable resultTable = createResultTable();
		resultTable.selectPosition(7);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalAccess2() {
		ResultTable resultTable = createResultTable();
		resultTable.selectPosition(-1);
	}

	@Test
	public void testSimpleResultTable() {
		ResultTable resultTable = createResultTable(t1, t2);
		List<ProbabilityOutcome> selectPosition = resultTable.selectPosition(0);
		double probabilities = ProbabilityOutcome.sumAllProbabilities(selectPosition);
		Assertions.assertThat(probabilities).isCloseTo(1.0, Offset.offset(0.000000001));
		for (ProbabilityOutcome probabilityOutcome : selectPosition) {
			Assertions.assertThat(probabilityOutcome.getProbability()).isCloseTo(0.5, Offset.offset(0.000000001));
		}
	}

	private ResultTable createResultTable() {
		return this.createResultTable(t1, t2, t3, t4);
	}

	private ResultTable createResultTable(ProbabilityOutcome... outcomes) {
		List<Match> pairings = Match.createPairings(Arrays.asList(outcomes), Consequence.values());
		ResultTable resultTable = new ResultTable(pairings);
		return resultTable;
	}

	@Test
	public void testResultsWithFixedResult() {
		List<ProbabilityOutcome> listOutcome = Arrays.asList(t1, t2, t3, t4);
		List<Match> pairings = Match.createPairings(listOutcome, Consequence.values());
		Match match = new Match(t1, t2, new MatchResult(Consequence.WIN1));
		pairings.remove(match);
		pairings.add(match);
		ResultTable resultTable = new ResultTable(pairings);

		System.out.println("--------------");
		List<ProbabilityOutcome> list = resultTable.selectPosition(0);
		for (ProbabilityOutcome probabilityOutcome : list) {
			System.out.println(probabilityOutcome);
		}
	}

	@Test
	public void testAllHaveSamePoints() {

		Match match1 = new Match(t1, t2, new MatchResult(Consequence.DRAW));
		Match match2 = new Match(t3, t4, new MatchResult(Consequence.DRAW));

		ResultTable resultTable = new ResultTable(Arrays.asList(match1, match2));
		for (int i = 0; i < 4; i++) {
			List<ProbabilityOutcome> list = resultTable.selectPosition(i);
			double sumAllProbabilities = ProbabilityOutcome.sumAllProbabilities(list);
			Assertions.assertThat(sumAllProbabilities).isCloseTo(1, OFFSET);
			for (ProbabilityOutcome probabilityOutcome : list) {
				Assertions.assertThat(probabilityOutcome.getProbability()).isCloseTo(0.25, OFFSET);
			}
		}
	}

	@Test
	public void testClearWinner() {

		Match match1 = new Match(t1, t2, new MatchResult(Consequence.WIN1));
		Match match2 = new Match(t3, t4, new MatchResult(Consequence.DRAW));

		ResultTable resultTable = new ResultTable(Arrays.asList(match1, match2));

		for (int i = 0; i < 4; i++) {
			List<ProbabilityOutcome> list = resultTable.selectPosition(i);
			double sumAllProbabilities = ProbabilityOutcome.sumAllProbabilities(list);
			Assertions.assertThat(sumAllProbabilities).isCloseTo(1, OFFSET);
		}

		{
			List<ProbabilityOutcome> selectPosition1 = resultTable.selectPosition(0);
			Assertions.assertThat(selectPosition1).hasSize(1);
			ProbabilityOutcome outcomeTeamA = ProbabilityOutcome.getByTitle(selectPosition1, TEAM_A);
			Assertions.assertThat(outcomeTeamA.getProbability()).isCloseTo(1.0, OFFSET); // sure
																							// event;
		}

		{
			List<ProbabilityOutcome> selectPosition4 = resultTable.selectPosition(3);
			Assertions.assertThat(selectPosition4).hasSize(1);
			ProbabilityOutcome outcomeTeamB = ProbabilityOutcome.getByTitle(selectPosition4, TEAM_B);
			Assertions.assertThat(outcomeTeamB.getProbability()).isCloseTo(1.0, OFFSET); // sure
																							// event;
		}

		{
			List<ProbabilityOutcome> selectPosition2 = resultTable.selectPosition(1);
			Assertions.assertThat(selectPosition2).hasSize(2);
			ProbabilityOutcome outcomeTeamC2 = ProbabilityOutcome.getByTitle(selectPosition2, TEAM_C);
			Assertions.assertThat(outcomeTeamC2.getProbability()).isCloseTo(0.5, OFFSET); // fifty-fifty-event;
			ProbabilityOutcome outcomeTeamD2 = ProbabilityOutcome.getByTitle(selectPosition2, TEAM_D);
			Assertions.assertThat(outcomeTeamD2.getProbability()).isCloseTo(0.5, OFFSET); // fifty-fifty-event;
		}

		{
			List<ProbabilityOutcome> selectPosition3 = resultTable.selectPosition(2);
			Assertions.assertThat(selectPosition3).hasSize(2);
			ProbabilityOutcome outcomeTeamC2 = ProbabilityOutcome.getByTitle(selectPosition3, TEAM_C);
			Assertions.assertThat(outcomeTeamC2.getProbability()).isCloseTo(0.5, OFFSET); // fifty-fifty-event;
			ProbabilityOutcome outcomeTeamD2 = ProbabilityOutcome.getByTitle(selectPosition3, TEAM_D);
			Assertions.assertThat(outcomeTeamD2.getProbability()).isCloseTo(0.5, OFFSET); // fifty-fifty-event;
		}
	}
}
