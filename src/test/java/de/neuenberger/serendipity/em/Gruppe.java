package de.neuenberger.serendipity.em;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.neuenberger.serendipity.ProbabilityOutcome;
import de.neuenberger.serendipity.ProbabilityProcess;
import de.neuenberger.serendipity.em.Team.TeamProbabilityFactory;

public class Gruppe {

	private final List<Team> teams;
	
	public Gruppe(Team ...teams) {
		this.teams = Collections.unmodifiableList(new ArrayList<>(Arrays.asList(teams)));
	}
	
	private List<ProbabilityOutcome> createProbability(List<Team> t, TeamProbabilityFactory factory) {
		List<ProbabilityOutcome> p=new ArrayList<>();
		for (Team team : t) {
			p.add(factory.create(team));
		}
		return Collections.unmodifiableList(p);
	}

	public List<Team> getTeams() {
		return teams;
	}
	
	public ProbabilityProcess toProbabilityProcess(TeamProbabilityFactory factory) {
		final List<ProbabilityOutcome> probabilities = createProbability(teams, factory);
		ProbabilityProcess probabilityProcess = new ProbabilityProcess() {
			
			@Override
			public List<ProbabilityOutcome> getProbabilityOutput() {
				return probabilities;
			}
		};
		return probabilityProcess;
	}
	
}
