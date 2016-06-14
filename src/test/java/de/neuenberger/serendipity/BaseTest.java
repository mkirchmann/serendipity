package de.neuenberger.serendipity;

import org.assertj.core.api.Assertions;

public class BaseTest {
	public static <T> void testEquals(T obj) {
		boolean equals = obj.equals(null);
		Assertions.assertThat(equals).isFalse();
		
		equals = obj.equals(new Object());
		Assertions.assertThat(equals).isFalse();
	}
}
