package de.neuenberger.serendipity;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Test;
import org.mockito.Mockito;

public class ListSelectionTest {
	@Test
	public void testOutput() {
		// given
		Probability pa = createProbability(1.0);
		Probability pb = createProbability(1.0);
		Probability pc = createProbability(0.0);
		ListSelection selection = new ListSelection(1, pa,pb,pc);
		
		ListSelection spy = Mockito.spy(selection);
		double probabilityValue = 0.5;
		Mockito.doReturn(probabilityValue).when(spy).getOutputProbability();
		Probability pam = createProbability(0.5);
		Probability pbm = createProbability(0.5);
		Probability pcm = createProbability(0.0);
		
		Mockito.when(pa.multiply(probabilityValue)).thenReturn(pam);
		Mockito.when(pb.multiply(probabilityValue)).thenReturn(pbm);
		Mockito.when(pc.multiply(probabilityValue)).thenReturn(pcm);
		
		// when
		List<Probability> probabilityOutput = spy.getProbabilityOutput();
		
		// then
		Assertions.assertThat(probabilityOutput).containsOnly(pam,pbm);
	}
	
	@Test
	public void testMultiplier() {
		Probability pa = createProbability(1.0);
		Probability pb = createProbability(1.0);
		Probability pc = createProbability(1.0);
		Probability pd = createProbability(1.0);
		ListSelection selection = new ListSelection(1, pa,pb,pc,pd);
		double probability = selection.getOutputProbability();
		Assertions.assertThat(probability).isCloseTo(0.25, Offset.offset(0.00001));
	}
	
	@Test
	public void testSumOfAllProbabilities() {
		Probability pa=new SimpleProbability("a",1);
		Probability pb=new SimpleProbability("b",1);
		Probability pc=new SimpleProbability("c",1);
		Probability pd=new SimpleProbability("d",1);
		
		ListSelection selection = new ListSelection(1, pa,pb,pc,pd);
		List<Probability> probabilityOutput = selection.getProbabilityOutput();
		double sumAll=0.0;
		for (Probability probability : probabilityOutput) {
			sumAll+=probability.getProbability();
		}
		Assertions.assertThat(probabilityOutput).hasSize(4);
		Assertions.assertThat(sumAll).isCloseTo(1.0, Offset.offset(0.0000000001));
	}
	
	@Test
	public void testCombination() {
		
	}

	private Probability createProbability(double pValue) {
		Probability pa = Mockito.mock(Probability.class);
		Mockito.when(pa.getProbability()).thenReturn(pValue);
		return pa;
	}
}
