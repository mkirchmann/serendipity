package de.neuenberger.serendipity.em;

import de.neuenberger.serendipity.ProbabilityOutcome;
import de.neuenberger.serendipity.SimpleProbabilityOutcome;

/**
 * 
 * @author Michael Kirchmann
 *
 */
public class Team {
	private final String name;
	private final String supplier;
	
	public Team(String name, String supplier) {
		this.name = name;
		this.supplier = supplier;
	}

	public String getName() {
		return name;
	}

	public String getSupplier() {
		return supplier;
	}
	
	public interface TeamProbabilityFactory {
		ProbabilityOutcome create(Team t);
	}
	
	static class TeamByNameProbabilityFactory implements TeamProbabilityFactory{

		@Override
		public ProbabilityOutcome create(Team team) {
			return new SimpleProbabilityOutcome("Team:\t"+team.getName());
		}
		
	}
	
	static class TeamBySupplierProbabilityFactory implements TeamProbabilityFactory {
		@Override
		public ProbabilityOutcome create(Team team) {
			return new SimpleProbabilityOutcome("Supplier:\t"+team.getSupplier());
		}
	}
	
	public static TeamProbabilityFactory getTeamProbabilityFactoryName() {
		return new TeamByNameProbabilityFactory();
	}
	
	public static TeamProbabilityFactory getTeamProbabilityFactorySupplier() {
		return new TeamBySupplierProbabilityFactory();
	}
}
