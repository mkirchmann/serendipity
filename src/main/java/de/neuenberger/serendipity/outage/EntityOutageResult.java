package de.neuenberger.serendipity.outage;

import java.util.Objects;

import de.neuenberger.serendipity.ProbabilityOutcome;

public class EntityOutageResult {
	private final EventResult result;
	private final ProbabilityOutcome outcome;

	public EntityOutageResult(EventResult result, ProbabilityOutcome outcome) {
		Objects.requireNonNull(result);
		Objects.requireNonNull(outcome);
		this.result = result;
		this.outcome = outcome;
	}
	
	public ProbabilityOutcome getOutcome() {
		return outcome;
	}
	
	public EventResult getResult() {
		return result;
	}
	
	public int getScore() {
		return result.getScore();
	}
	
	public double getProbability() {
		return result.getProbability(outcome.getProbability());
	}
	
	
}
