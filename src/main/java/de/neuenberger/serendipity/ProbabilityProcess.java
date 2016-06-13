package de.neuenberger.serendipity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Michael Kirchmann
 *
 */
public interface ProbabilityProcess {
	List<Probability> getProbabilityOutput();
	
	static Probability[] toProbabilities(ProbabilityProcess process) {
		List<Probability> probabilityOutput = process.getProbabilityOutput();
		return toProbabilities(probabilityOutput);
	}

	static Probability[] toProbabilities(List<Probability> probabilityOutput) {
		return probabilityOutput.toArray(new Probability[]{});
	}
	
	static Probability[] toProbabilities(ProbabilityProcess... p) {
		List<Probability> probabilityList = new ArrayList<>();
		for (ProbabilityProcess probabilityProcess : p) {
			probabilityList.addAll(probabilityProcess.getProbabilityOutput());
		}
		Probability[] probabilityArray = toProbabilities(probabilityList);
		return probabilityArray;
	}
}
