package de.neuenberger.serendipity;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

public class ProbabilityOutcomeTest {
	@Test
	public void testGetByTitle_positiveCase() {
		String title = "someTitle";
		
		ProbabilityOutcome mock1 = Mockito.mock(ProbabilityOutcome.class);
		ProbabilityOutcome mock2 = Mockito.mock(ProbabilityOutcome.class);
		ProbabilityOutcome mock3 = Mockito.mock(ProbabilityOutcome.class);
		List<ProbabilityOutcome> list = Arrays.asList(mock1,mock2,mock3);
		
		Mockito.when(mock2.getTitle()).thenReturn(title);
		
		ProbabilityOutcome byTitle = ProbabilityOutcome.getByTitle(list, title);
	
		Assertions.assertThat(byTitle).isSameAs(mock2);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetByTitle_notFoundCase() {
		String title = "someTitle";
		
		ProbabilityOutcome mock1 = Mockito.mock(ProbabilityOutcome.class);
		ProbabilityOutcome mock2 = Mockito.mock(ProbabilityOutcome.class);
		ProbabilityOutcome mock3 = Mockito.mock(ProbabilityOutcome.class);
		List<ProbabilityOutcome> list = Arrays.asList(mock1,mock2,mock3);
		
		ProbabilityOutcome byTitle = ProbabilityOutcome.getByTitle(list, title);
	
		Assertions.assertThat(byTitle).isSameAs(mock2);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetByTitle_emptyCase() {
		String title = "someTitle";
		
		List<ProbabilityOutcome> list = Arrays.asList();
		
		ProbabilityOutcome byTitle = ProbabilityOutcome.getByTitle(list, title);
	}

}
