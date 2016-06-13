package de.neuenberger.serendipity;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Test;
import org.mockito.Mockito;

public class SimpleProbabilityTest {
	private static final String TITLE = "title";

	@Test
	public void testCombinableTrue() {
		SimpleProbabilityOutcome probabilityA = new SimpleProbabilityOutcome(TITLE);
		SimpleProbabilityOutcome probabilityB = new SimpleProbabilityOutcome(TITLE);
		
		boolean result = probabilityA.isCombinable(probabilityB);
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void testCombinableFalse() {
		SimpleProbabilityOutcome probabilityA = new SimpleProbabilityOutcome(TITLE);
		SimpleProbabilityOutcome probabilityB = new SimpleProbabilityOutcome("kjsdf");
		
		boolean result = probabilityA.isCombinable(probabilityB);
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void testCombine() {
		SimpleProbabilityOutcome probabilityA = new SimpleProbabilityOutcome(TITLE,0.25);
		SimpleProbabilityOutcome probabilityB = new SimpleProbabilityOutcome(TITLE,0.3);
		
		ProbabilityOutcome result = probabilityA.combineWith(probabilityB);
		
		Assertions.assertThat(result.getTitle()).isSameAs(TITLE);
		Assertions.assertThat(result.getProbability()).isCloseTo(0.55, Offset.offset(0.000000001));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructViolatedToLarge() {
		new SimpleProbabilityOutcome("bla",1.1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructViolated() {
		new SimpleProbabilityOutcome("bla",-0.1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCombinationWithNonCombinable() {
		ProbabilityOutcome outcome = Mockito.mock(ProbabilityOutcome.class);
		SimpleProbabilityOutcome probability = new SimpleProbabilityOutcome("kjdfs");
		probability.combineWith(outcome);
	}
	
	@Test
	public void testNotCombinableWithSelf() {
		SimpleProbabilityOutcome probability = new SimpleProbabilityOutcome("kjdfs");
		boolean result = probability.isCombinable(probability);
		
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void testNotCombinableWithNull() {
		SimpleProbabilityOutcome probability = new SimpleProbabilityOutcome("kjdfs");
		boolean result = probability.isCombinable(null);
		
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void testIsCombinableWithNull() {
		SimpleProbabilityOutcome probability1 = new SimpleProbabilityOutcome(null);
		SimpleProbabilityOutcome probability2 = new SimpleProbabilityOutcome(null);
		boolean result = probability1.isCombinable(probability2);
		
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void testIsNotCombinableWithNullAndNonNull() {
		SimpleProbabilityOutcome probability1 = new SimpleProbabilityOutcome(null);
		SimpleProbabilityOutcome probability2 = new SimpleProbabilityOutcome("dfslkj");
		boolean result = probability1.isCombinable(probability2);
		
		Assertions.assertThat(result).isFalse();
	}
}
