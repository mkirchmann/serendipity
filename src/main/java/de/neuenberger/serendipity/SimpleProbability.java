package de.neuenberger.serendipity;

public class SimpleProbability implements Probability {
	private final String title;
	private final double probability;
	
	public SimpleProbability(String title, double probability) {
		this.title = title;
		this.probability = probability;
		if (probability<0 || probability>1) {
			throw new IllegalArgumentException("Probability needs to be between 0...1");
		}
	}

	public SimpleProbability(String title) {
		this(title,1.0);
	}

	public String getTitle() {
		return title;
	}

	public double getProbability() {
		return probability;
	}

	@Override
	public Probability multiply(double multiPlier) {
		return new SimpleProbability(title, probability*multiPlier);
	}
	
	@Override
	public String toString() {
		return title+"\t"+probability;
	}

	@Override
	public boolean isCombinable(Probability obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleProbability other = (SimpleProbability) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public Probability combineWith(Probability p) {
		if (!isCombinable(p)) {
			throw new IllegalArgumentException(this+" not combinable with "+p);
		}
		SimpleProbability sp = (SimpleProbability) p;
		return new SimpleProbability(title, probability+sp.getProbability());
	}

	
}
