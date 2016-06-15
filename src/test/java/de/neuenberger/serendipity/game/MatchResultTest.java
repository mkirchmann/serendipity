package de.neuenberger.serendipity.game;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

public class MatchResultTest {
	@Test(expected=IllegalStateException.class)
	public void testIllegalInit() {
		Match match = Mockito.mock(Match.class);
		MatchResult result = new MatchResult(Consequence.DRAW);
		result.init(match);
		result.init(match);
	}
	
	@Test
	public void testInit() {
		Match match = Mockito.mock(Match.class);
		MatchResult result = new MatchResult(Consequence.DRAW);
		result.init(match);
		
		Assertions.assertThat(result.getMatch()).isSameAs(match);
	}
	
	@Test
	public void testToString() throws Exception {
		final String title = "skjhsdgjfk";
		Consequence consequence = Consequence.WIN1;
		MatchResult result = new MatchResult(consequence);
		Match match = new Match(null, null, result) {

			@Override
			public String toString() {
				return title;
			}
		};
		String toString = result.toString();
		
		Assertions.assertThat(toString).contains(title).contains(""+consequence);
	}
}
