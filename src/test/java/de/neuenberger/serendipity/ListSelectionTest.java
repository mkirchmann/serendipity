package de.neuenberger.serendipity;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

public class ListSelectionTest {
	@Test
	public void testOutput() {
		// given
		ProbabilityOutcome pa = createProbability(1.0);
		ProbabilityOutcome pb = createProbability(1.0);
		ProbabilityOutcome pc = createProbability(0.0);
		ListSelection selection = new ListSelection(1, pa,pb,pc);
		
		ListSelection spy = Mockito.spy(selection);
		double probabilityValue = 0.5;
		Mockito.doReturn(probabilityValue).when(spy).getOutputProbability();
		ProbabilityOutcome pam = createProbability(0.5);
		ProbabilityOutcome pbm = createProbability(0.5);
		ProbabilityOutcome pcm = createProbability(0.0);
		
		Mockito.when(pa.multiply(probabilityValue)).thenReturn(pam);
		Mockito.when(pb.multiply(probabilityValue)).thenReturn(pbm);
		Mockito.when(pc.multiply(probabilityValue)).thenReturn(pcm);
		
		// when
		List<ProbabilityOutcome> probabilityOutput = spy.getProbabilityOutput();
		
		// then
		Assertions.assertThat(probabilityOutput).containsOnly(pam,pbm);
	}
	
	@Test
	public void testMultiplier() {
		ProbabilityOutcome pa = createProbability(1.0);
		ProbabilityOutcome pb = createProbability(1.0);
		ProbabilityOutcome pc = createProbability(1.0);
		ProbabilityOutcome pd = createProbability(1.0);
		ListSelection selection = new ListSelection(1, pa,pb,pc,pd);
		double probability = selection.getOutputProbability();
		Assertions.assertThat(probability).isCloseTo(0.25, Offset.offset(0.00001));
	}
	
	@Test
	public void testSumOfAllProbabilities() {
		ProbabilityOutcome pa=new SimpleProbabilityOutcome("a",1);
		ProbabilityOutcome pb=new SimpleProbabilityOutcome("b",1);
		ProbabilityOutcome pc=new SimpleProbabilityOutcome("c",1);
		ProbabilityOutcome pd=new SimpleProbabilityOutcome("d",1);
		
		ListSelection selection = new ListSelection(1, pa,pb,pc,pd);
		List<ProbabilityOutcome> probabilityOutput = selection.getProbabilityOutput();
		double sumAll=0.0;
		for (ProbabilityOutcome probability : probabilityOutput) {
			sumAll+=probability.getProbability();
		}
		Assertions.assertThat(probabilityOutput).hasSize(4);
		Assertions.assertThat(sumAll).isCloseTo(1.0, Offset.offset(0.0000000001));
		
		List<ProbabilityOutcome> probabilityOutput2 = selection.getProbabilityOutput();
		Assertions.assertThat(probabilityOutput2).isSameAs(probabilityOutput);
	}
	
	@Test
	public void testCombination() {
		
	}

	private ProbabilityOutcome createProbability(double pValue) {
		ProbabilityOutcome pa = Mockito.mock(ProbabilityOutcome.class);
		Mockito.when(pa.getProbability()).thenReturn(pValue);
		return pa;
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testListSelectionCreationFailureNoInputs() {
		new ListSelection(1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testListSelectionCreationFailureNegativeProbability() {
		ProbabilityOutcome mock = Mockito.mock(ProbabilityOutcome.class);
		Mockito.when(mock.getProbability()).thenReturn(-1.0);
		new ListSelection(1,mock);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testListSelectionCreationFailureNegativeSelection() {
		ProbabilityOutcome mock = Mockito.mock(ProbabilityOutcome.class);
		new ListSelection(-1,mock);
	}
}
