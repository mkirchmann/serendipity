package de.neuenberger.serendipity;

import java.util.List;

/**
 * An interface working as an input for probability processes.
 * 
 * @author Michael Kirchmann
 *
 */
public interface Probability {
	double getProbability();

	String getTitle();

	Probability multiply(double multiPlier);

	boolean isCombinable(Probability p);

	Probability combineWith(Probability p);

	static double sumAllProbabilities(Probability... probabilities) {
		double result = 0.0;
		for (Probability p : probabilities) {
			result += p.getProbability();
		}
		return result;
	}

	static double sumAllProbabilities(List<Probability> probabilities) {
		double result = 0.0;
		for (Probability p : probabilities) {
			result += p.getProbability();
		}
		return result;
	}

	/**
	 * Goes through the given list and checks if the given title is equal to the
	 * title. If so it returns that found one. If no title is matching, an
	 * exception is thrown.
	 * 
	 * @param probabilities
	 *            given list of {@link Probability}
	 * @param title
	 *            given title
	 * @return returns the first matching {@link Probability}, throws an
	 *         exception if none is found.
	 */
	static Probability getByTitle(List<Probability> probabilities, String title) {
		Probability result = null;
		for (Probability probability : probabilities) {
			if (title.equals(probability.getTitle())) {
				result = probability;
				break;
			}
		}
		if (result == null) {
			throw new IllegalArgumentException("Probability with title '" + title + "' not found");
		}
		return result;
	}
}
