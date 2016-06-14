package de.neuenberger.serendipity.em;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Assert;
import org.junit.Test;

import de.neuenberger.serendipity.ListSelection;
import de.neuenberger.serendipity.ProbabilityOutcome;
import de.neuenberger.serendipity.ProbabilityProcess;
import de.neuenberger.serendipity.SimpleProbabilityOutcome;

public class TestEM2016 {
	
	@Test
	public void testSimpleFinalTeam() {
		SimpleFinalFactory finalFactory = new SimpleFinalFactory(Team.getTeamProbabilityFactoryName());
		testFactory(finalFactory,24);
	}
	
	@Test
	public void testSimpleFinalSupplier() {
		SimpleFinalFactory finalFactory = new SimpleFinalFactory(Team.getTeamProbabilityFactorySupplier());
		testFactory(finalFactory,7);
	}
	
	@Test
	public void testComplexFinalTeam() {
		SimpleFinalFactory finalFactory = new ComplexFinalFactory(Team.getTeamProbabilityFactoryName());
		testFactory(finalFactory,24);
	}
	
	@Test
	public void testComplexFinalSupplier() {
		SimpleFinalFactory finalFactory = new ComplexFinalFactory(Team.getTeamProbabilityFactorySupplier());
		testFactory(finalFactory,7);
	}

	private void testFactory(SimpleFinalFactory finalFactory, int expectedSize) {
		ProbabilityProcess finalMatch = finalFactory.createFinal();
		List<ProbabilityOutcome> probabilityOutput = finalMatch.getProbabilityOutput();
		double sumAll = 0.0;
		printLine();
		for (ProbabilityOutcome probability : probabilityOutput) {
			System.out.println(probability);
			sumAll+=probability.getProbability();
		}
		Assertions.assertThat(probabilityOutput).hasSize(expectedSize);
		Assertions.assertThat(sumAll).isCloseTo(1.0, Offset.offset(0.00000001));
	}
	
	@Test
	public void testSimple() {
		String titleF = "f";
		String titleE = "e";
		String titleA = "a";
		ProbabilityOutcome pa=new SimpleProbabilityOutcome(titleA);
		String titleB = "b";
		ProbabilityOutcome pb=new SimpleProbabilityOutcome(titleB);
		ProbabilityOutcome pc=new SimpleProbabilityOutcome("c");
		ProbabilityOutcome pd=new SimpleProbabilityOutcome("d");
		ProbabilityOutcome pe=new SimpleProbabilityOutcome(titleE);
		ProbabilityOutcome pf=new SimpleProbabilityOutcome(titleF);
		
		ListSelection selectionAB = new ListSelection(1, pa,pb);
		ListSelection selectionCD = new ListSelection(1, pc,pd,pe,pf);
		ListSelection selectionF = new ListSelection(1, ProbabilityProcess.toProbabilities(selectionAB,selectionCD));
		
		List<ProbabilityOutcome> result = selectionF.getProbabilityOutput();
		
		double probabilities = ProbabilityOutcome.sumAllProbabilities(result);
		Assertions.assertThat(result).hasSize(6);
		Assertions.assertThat(probabilities).isCloseTo(1.0, Offset.offset(0.000000001));
		
		ProbabilityOutcome finalPa = ProbabilityOutcome.getByTitle(result, titleA);
		ProbabilityOutcome finalPb = ProbabilityOutcome.getByTitle(result, titleB);
		ProbabilityOutcome finalPe = ProbabilityOutcome.getByTitle(result, titleE);
		ProbabilityOutcome finalPf = ProbabilityOutcome.getByTitle(result, titleF);
		Assertions.assertThat(finalPf.getProbability()).isEqualTo(finalPe.getProbability());
		Assertions.assertThat(finalPa.getProbability()).isEqualTo(finalPb.getProbability());
		Assert.assertTrue(finalPf.getProbability()<finalPa.getProbability());
		printLine();
		for (ProbabilityOutcome probability : result) {
			System.out.println(probability);
		}
	}
	private void printLine() {
		System.out.println("-----------------");
	}

}
