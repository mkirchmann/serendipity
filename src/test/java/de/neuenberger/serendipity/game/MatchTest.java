package de.neuenberger.serendipity.game;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import de.neuenberger.serendipity.SimpleProbabilityOutcome;

public class MatchTest {
	@Test
	public void test() {
		String title1 = "sope809wt";
		String title2 = "fhlgdfk";
		SimpleProbabilityOutcome pa = new SimpleProbabilityOutcome(title1);
		SimpleProbabilityOutcome pb = new SimpleProbabilityOutcome(title2);
		Match match = new Match(pa,pb);
		String string = match.toString();
		Assertions.assertThat(string).contains(title1).contains(title2);
	}
}
