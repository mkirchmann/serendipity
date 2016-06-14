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

	private ResultTable createResultTable() {
		List<Match> pairings = Match.createPairings(Arrays.asList(t1,t2,t3,t4), Consequence.values());
		ResultTable resultTable = new ResultTable(pairings);
		return resultTable;
	}
}
