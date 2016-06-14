package de.neuenberger.serendipity.game;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Test;

import de.neuenberger.serendipity.ProbabilityOutcome;
import de.neuenberger.serendipity.SimpleProbabilityOutcome;

public class ResultTableTest {
	private final SimpleProbabilityOutcome t1 = new SimpleProbabilityOutcome("a");
	private final SimpleProbabilityOutcome t2 = new SimpleProbabilityOutcome("b");
	private final SimpleProbabilityOutcome t3 = new SimpleProbabilityOutcome("c");
	private final SimpleProbabilityOutcome t4 = new SimpleProbabilityOutcome("d");
	
	@Test
	public void testPairing() {
		List<Match> pairings = Match.createPairings(Arrays.asList(t1,t2,t3,t4), Consequence.values());
		
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
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalAccess() {
		ResultTable resultTable = createResultTable();
		resultTable.selectPosition(7);
	}
	
	@Test(expected=IllegalArgumentException.class)
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
}
