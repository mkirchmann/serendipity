package de.neuenberger.serendipity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Michael Kirchmann
 *
 */
public interface ProbabilityProcess {
	List<ProbabilityOutcome> getProbabilityOutput();
	
	static ProbabilityOutcome[] toProbabilities(ProbabilityProcess process) {
		List<ProbabilityOutcome> probabilityOutput = process.getProbabilityOutput();
		return toProbabilities(probabilityOutput);
	}

	static ProbabilityOutcome[] toProbabilities(List<ProbabilityOutcome> probabilityOutput) {
		return probabilityOutput.toArray(new ProbabilityOutcome[]{});
	}
	
	static ProbabilityOutcome[] toProbabilities(ProbabilityProcess... p) {
		List<ProbabilityOutcome> probabilityList = new ArrayList<>();
		for (ProbabilityProcess probabilityProcess : p) {
			probabilityList.addAll(probabilityProcess.getProbabilityOutput());
		}
		ProbabilityOutcome[] probabilityArray = toProbabilities(probabilityList);
		return probabilityArray;
	}
}
