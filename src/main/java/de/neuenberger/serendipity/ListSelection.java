package de.neuenberger.serendipity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A process that selects a number of outcomes from all given outcomes.
 * 
 * @author Michael Kirchmann
 *
 */
public class ListSelection implements ProbabilityProcess {
	private final Integer selectionLimit;

	private final List<ProbabilityOutcome> probabilityInput;

	private List<ProbabilityOutcome> outputs;

	private final double sumAllProbabilities;

	/**
	 * Constructs an instance of {@link ListSelection}
	 * 
	 * @param selectionLimit
	 *            given selection Limit
	 * @param inputs
	 *            all inputs
	 */
	public ListSelection(Integer selectionLimit, ProbabilityOutcome... inputs) {
		this.selectionLimit = selectionLimit;
		Objects.requireNonNull(inputs);
		if (inputs.length == 0) {
			throw new IllegalArgumentException("Inputs may not be empty");
		}
		if (selectionLimit < 0) {
			throw new IllegalArgumentException("Negative numbers are not allowed for selection");
		}
		probabilityInput = new ArrayList<>(inputs.length);
		probabilityInput.addAll(Arrays.asList(inputs));
		sumAllProbabilities = ProbabilityOutcome.sumAllProbabilities(probabilityInput);
		if (sumAllProbabilities <= 0) {
			throw new IllegalArgumentException("Probability is out of range: " + sumAllProbabilities);
		}
	}

	@Override
	public List<ProbabilityOutcome> getProbabilityOutput() {
		if (outputs == null) {
			List<ProbabilityOutcome> tempOutputs = new ArrayList<>(probabilityInput.size());
			double multiPlier = getOutputProbability();
			for (ProbabilityOutcome probabilityInput : probabilityInput) {
				ProbabilityOutcome multiply = probabilityInput.multiply(multiPlier);
				double newProbability = multiply.getProbability();
				if (newProbability != 0) {
					addOrCombine(tempOutputs, multiply);
				}
			}
			Collections.sort(tempOutputs);
			this.outputs = Collections.unmodifiableList(tempOutputs);
		}
		return outputs;
	}

	/**
	 * 
	 * @return Returns a normalized probability
	 */
	protected double getOutputProbability() {
		return (double) Math.min(selectionLimit, probabilityInput.size()) / (double) sumAllProbabilities;
	}

	/**
	 * Checks whether an outcome exists in the given list out outputs and tries
	 * it to combine, otherwise it is added. The order of tempOutputs may be changed.
	 * 
	 * @param tempOutputs
	 *            given list out outputs
	 * @param outcomeToAdd given output to be added.
	 */
	private void addOrCombine(List<ProbabilityOutcome> tempOutputs, ProbabilityOutcome outcomeToAdd) {
		ProbabilityOutcome probabilityToBeReplaced = null;
		for (ProbabilityOutcome probability : tempOutputs) {
			if (probability.isCombinable(outcomeToAdd)) {
				probabilityToBeReplaced = probability;
				break;
			}
		}
		if (probabilityToBeReplaced != null) {
			tempOutputs.remove(probabilityToBeReplaced);
			tempOutputs.add(probabilityToBeReplaced.combineWith(outcomeToAdd));
		} else {
			tempOutputs.add(outcomeToAdd);
		}
	}
}
