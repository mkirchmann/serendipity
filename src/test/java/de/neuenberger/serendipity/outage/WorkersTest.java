package de.neuenberger.serendipity.outage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import de.neuenberger.serendipity.ProbabilityOutcome;
import de.neuenberger.serendipity.SimpleProbabilityOutcome;

public class WorkersTest {
	@Test
	public void testWorkersAvailable() {
		List<SimpleProbabilityOutcome> players = new ArrayList<>();
		players.add(new SimpleProbabilityOutcome("Peter K", 0.5));
		players.add(new SimpleProbabilityOutcome("Christian S", 0.75));
		players.add(new SimpleProbabilityOutcome("Patrick W", 0.5));
		players.add(new SimpleProbabilityOutcome("Jonas R", 0.9));
		players.add(new SimpleProbabilityOutcome("Ingo R", 0.1));
		players.add(new SimpleProbabilityOutcome("Klaus S", 0.75));
		players.add(new SimpleProbabilityOutcome("Michael K", 0.5));
		
		List<ProbabilityOutcome> result = new PermutateOutages(players).simplifiedProcess();
		List<ProbabilityOutcome> sorted=result.stream().sorted(Comparator.comparing(ProbabilityOutcome::getTitle)).collect(Collectors.toList());
		for (ProbabilityOutcome probabilityOutcome : sorted) {
			System.out.println(probabilityOutcome);
		}
		
	}
}
