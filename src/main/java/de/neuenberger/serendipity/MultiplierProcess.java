package de.neuenberger.serendipity;

import java.util.ArrayList;
import java.util.List;

public class MultiplierProcess implements ProbabilityProcess {
	
	private ProbabilityProcess process;
	private double factor;

	public MultiplierProcess(ProbabilityProcess process, double factor) {
		this.process = process;
		this.factor = factor;
	}
	
	@Override
	public List<Probability> getProbabilityOutput() {
		List<Probability> probabilityInput = process.getProbabilityOutput();
		List<Probability> probabilityOutput =new ArrayList<>(probabilityInput.size());
		
		for (Probability probability : probabilityInput) {
			probabilityOutput.add(probability.multiply(factor));
		}
		
		return probabilityOutput;
	}
	
}
