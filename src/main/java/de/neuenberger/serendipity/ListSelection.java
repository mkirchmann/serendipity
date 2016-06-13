package de.neuenberger.serendipity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 
 * @author Michael Kirchmann
 *
 */
public class ListSelection implements ProbabilityProcess {
	private final Integer selectionLimit;
	
	private final List<Probability> probabilityInput;

	private List<Probability> outputs;

	private final double sumAllProbabilities;
	
	public ListSelection(Integer selectionLimit, Probability ...inputs) {
		this.selectionLimit = selectionLimit;
		Objects.requireNonNull(inputs);
		if (inputs.length==0) {
			throw new IllegalArgumentException("Inputs may not be empty");
		}
		if (selectionLimit<0) {
			throw new IllegalArgumentException("Negative numbers are not allowed for selection");
		}
		probabilityInput = new ArrayList<>(inputs.length);
		probabilityInput.addAll(Arrays.asList(inputs));
		sumAllProbabilities = Probability.sumAllProbabilities(probabilityInput);
		if (sumAllProbabilities<=0) {
			throw new IllegalArgumentException("Probability is out of range: "+sumAllProbabilities);
		}
	}

	@Override
	public List<Probability> getProbabilityOutput() {
		if (outputs==null) {
			List<Probability> tempOutputs = new ArrayList<>(probabilityInput.size());
			double multiPlier = getOutputProbability();
			for (Probability probabilityInput : probabilityInput) {
				Probability multiply = probabilityInput.multiply(multiPlier);
				double newProbability = multiply.getProbability();
				if (newProbability!=0) {
					addOrCombine(tempOutputs,multiply);
				}
			}
			this.outputs=Collections.unmodifiableList(tempOutputs);
		}
		return outputs;
	}

	protected double getOutputProbability() {
		return (double)Math.min(selectionLimit, probabilityInput.size())/(double)sumAllProbabilities;
	}

	private void addOrCombine(List<Probability> tempOutputs, Probability multiply) {
		Probability probabilityToBeReplaced = null;
		for (Probability probability : tempOutputs) {
			if (probability.isCombinable(multiply)) {
				probabilityToBeReplaced = probability;
				break;
			}
		}
		if (probabilityToBeReplaced!=null) {
			tempOutputs.remove(probabilityToBeReplaced);
			tempOutputs.add(probabilityToBeReplaced.combineWith(multiply));
		} else {
			tempOutputs.add(multiply);
		}
	}
}
