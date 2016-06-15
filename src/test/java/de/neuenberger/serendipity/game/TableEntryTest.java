package de.neuenberger.serendipity.game;

import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import de.neuenberger.serendipity.SimpleProbabilityOutcome;

public class TableEntryTest {
	@Test
	public void testToString() throws Exception {
		int points = 2839;
		String title = "lkgfhjgf";
		TableEntry tableEntry = new TableEntry(new SimpleProbabilityOutcome(title), points);
		Assertions.assertThat(tableEntry.toString()).contains(""+points).contains(title);
	}
}
