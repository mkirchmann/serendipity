package de.neuenberger.serendipity;

import java.util.List;

/**
 * An interface working as an input for probability processes.
 * 
 * @author Michael Kirchmann
 *
 */
public interface ProbabilityOutcome {
	double getProbability();

	/**
	 * The title of this probability value
	 * 
	 * @return returns the title as String
	 */
	String getTitle();

	/**
	 * 
	 * @param multiPlier
	 * @return Returns a newly created instance of this
	 *         {@link ProbabilityOutcome} with the new probability value
	 */
	ProbabilityOutcome multiply(double multiPlier);

	/**
	 * Checks whether this instance is combinable with the given
	 * {@link ProbabilityOutcome}.
	 * 
	 * @param p
	 *            the given {@link ProbabilityOutcome}
	 * @return Returns true if combinable, false otherwise.
	 */
	boolean isCombinable(ProbabilityOutcome p);

	/**
	 * Combines this instance with the given {@ProbabilityOutcome} in OR
	 * fashion, i.e. both probabilies are added. The two probabilites should
	 * summed be not more than 1.
	 * 
	 * @param p
	 *            given {@ProbabilityOutcome}
	 * @return returns a newly created instance of {@ProbabilityOutcome}.
	 */
	ProbabilityOutcome combineWith(ProbabilityOutcome p);

	/**
	 * Sum all probabilities from the given list of probabilities
	 * 
	 * @param probabilities
	 *            given list of {@link ProbabilityOutcome}s
	 * @return returns the sum of probabilities as double.
	 */
	static double sumAllProbabilities(List<ProbabilityOutcome> probabilities) {
		double result = 0.0;
		for (ProbabilityOutcome p : probabilities) {
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
	 *            given list of {@link ProbabilityOutcome}
	 * @param title
	 *            given title
	 * @return returns the first matching {@link ProbabilityOutcome}, throws an
	 *         exception if none is found.
	 */
	static ProbabilityOutcome getByTitle(List<ProbabilityOutcome> probabilities, String title) {
		ProbabilityOutcome result = null;
		for (ProbabilityOutcome probability : probabilities) {
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
