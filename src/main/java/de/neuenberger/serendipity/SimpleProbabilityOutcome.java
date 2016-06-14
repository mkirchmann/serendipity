package de.neuenberger.serendipity;

/**
 * This class models a simple Probability Outcome
 * 
 * @author Michael Kirchmann
 *
 */
public class SimpleProbabilityOutcome implements ProbabilityOutcome {
	private final String title;
	private final double probability;

	/**
	 * Constructs a {@link SimpleProbabilityOutcome} with given title and given probability
	 * @param title given title
	 * @param probability given probability
	 */
	public SimpleProbabilityOutcome(String title, double probability) {
		this.title = title;
		this.probability = probability;
		if (probability < 0 || probability > 1) {
			throw new IllegalArgumentException("Probability needs to be between 0...1");
		}
	}

	/**
	 * Constructs a {@link SimpleProbabilityOutcome} with given title and 1 as probability
	 * @param title given title
	 */
	public SimpleProbabilityOutcome(String title) {
		this(title, 1.0);
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public double getProbability() {
		return probability;
	}

	@Override
	public ProbabilityOutcome multiply(double multiPlier) {
		return new SimpleProbabilityOutcome(title, probability * multiPlier);
	}

	@Override
	public String toString() {
		return title + "\t" + probability;
	}

	@Override
	public boolean isCombinable(ProbabilityOutcome obj) {
		if (this == obj)
			return false;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleProbabilityOutcome other = (SimpleProbabilityOutcome) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public ProbabilityOutcome combineWith(ProbabilityOutcome p) {
		if (!isCombinable(p)) {
			throw new IllegalArgumentException(this + " not combinable with " + p);
		}
		SimpleProbabilityOutcome sp = (SimpleProbabilityOutcome) p;
		return new SimpleProbabilityOutcome(title, probability + sp.getProbability());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleProbabilityOutcome other = (SimpleProbabilityOutcome) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
