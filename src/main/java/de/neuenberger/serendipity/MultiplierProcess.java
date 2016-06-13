package de.neuenberger.serendipity;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple multiplier process that multiplies all probabilities of the given
 * process with the factor
 * 
 * @author Michael Kirchmann
 *
 */
public class MultiplierProcess implements ProbabilityProcess {

	private ProbabilityProcess process;
	private double factor;

	/**
	 * Constructs a {@link MultiplierProcess}
	 * 
	 * @param process
	 *            given process
	 * @param factor
	 *            given factor
	 */
	public MultiplierProcess(ProbabilityProcess process, double factor) {
		this.process = process;
		this.factor = factor;
	}

	@Override
	public List<ProbabilityOutcome> getProbabilityOutput() {
		List<ProbabilityOutcome> probabilityInput = process.getProbabilityOutput();
		List<ProbabilityOutcome> probabilityOutput = new ArrayList<>(probabilityInput.size());

		for (ProbabilityOutcome probability : probabilityInput) {
			probabilityOutput.add(probability.multiply(factor));
		}

		return probabilityOutput;
	}

}
