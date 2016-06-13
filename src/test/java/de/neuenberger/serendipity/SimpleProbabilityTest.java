package de.neuenberger.serendipity;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Test;

public class SimpleProbabilityTest {
	private static final String TITLE = "title";

	@Test
	public void testCombinableTrue() {
		SimpleProbability probabilityA = new SimpleProbability(TITLE);
		SimpleProbability probabilityB = new SimpleProbability(TITLE);
		
		boolean result = probabilityA.isCombinable(probabilityB);
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void testCombinableFalse() {
		SimpleProbability probabilityA = new SimpleProbability(TITLE);
		SimpleProbability probabilityB = new SimpleProbability("kjsdf");
		
		boolean result = probabilityA.isCombinable(probabilityB);
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void testCombine() {
		SimpleProbability probabilityA = new SimpleProbability(TITLE,0.25);
		SimpleProbability probabilityB = new SimpleProbability(TITLE,0.3);
		
		Probability result = probabilityA.combineWith(probabilityB);
		
		Assertions.assertThat(result.getTitle()).isSameAs(TITLE);
		Assertions.assertThat(result.getProbability()).isCloseTo(0.55, Offset.offset(0.000000001));
	}
}
