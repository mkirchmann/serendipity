package de.neuenberger.serendipity.game;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import de.neuenberger.serendipity.BaseTest;
import de.neuenberger.serendipity.SimpleProbabilityOutcome;

public class MatchTest {
	private final String title1 = "sope809wt";
	private final String title2 = "fhlgdfk";
	@Test
	public void test() {
		SimpleProbabilityOutcome pa = new SimpleProbabilityOutcome(title1);
		SimpleProbabilityOutcome pb = new SimpleProbabilityOutcome(title2);
		Match match = new Match(pa,pb);
		String string = match.toString();
		Assertions.assertThat(string).contains(title1).contains(title2);
	}

	@Test
	public void testGetFromHashMap() {
		SimpleProbabilityOutcome pa = new SimpleProbabilityOutcome(title1);
		SimpleProbabilityOutcome pb = new SimpleProbabilityOutcome(title2);
		Match match = new Match(pa, pb);
		Map<Match, Integer> map = new HashMap<>();
		Integer expected = 99393;
		map.put(match, expected);
		Integer result = map.get(match);

		Assertions.assertThat(result).isSameAs(expected);
	}

	@Test
	public void testGetFromHashMapWithNull() {
		Match match = new Match(null, null);
		Map<Match, Integer> map = new HashMap<>();
		Integer expected = 654654;
		map.put(match, expected);
		Integer result = map.get(match);

		Assertions.assertThat(result).isSameAs(expected);
	}

	@Test
	public void coverEquals() {
		BaseTest.testEquals(new Match(null, null));
	}
}
