package de.neuenberger.serendipity.em;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.neuenberger.serendipity.ListSelection;
import de.neuenberger.serendipity.Probability;
import de.neuenberger.serendipity.ProbabilityProcess;
import de.neuenberger.serendipity.SimpleProbability;
import de.neuenberger.serendipity.em.Team.TeamProbabilityFactory;

public class Gruppe {

	private final List<Team> teams;
	
	public Gruppe(Team ...teams) {
		this.teams = Collections.unmodifiableList(new ArrayList<>(Arrays.asList(teams)));
	}
	
	private List<Probability> createProbability(List<Team> t, TeamProbabilityFactory factory) {
		List<Probability> p=new ArrayList<>();
		for (Team team : t) {
			p.add(factory.create(team));
		}
		return Collections.unmodifiableList(p);
	}

	public List<Team> getTeams() {
		return teams;
	}
	
	public ProbabilityProcess toProbabilityProcess(TeamProbabilityFactory factory) {
		final List<Probability> probabilities = createProbability(teams, factory);
		ProbabilityProcess probabilityProcess = new ProbabilityProcess() {
			
			@Override
			public List<Probability> getProbabilityOutput() {
				return probabilities;
			}
		};
		return probabilityProcess;
	}
	
}
