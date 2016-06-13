package de.neuenberger.serendipity.em;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.neuenberger.serendipity.ListSelection;
import de.neuenberger.serendipity.MultiplierProcess;
import de.neuenberger.serendipity.ProbabilityOutcome;
import de.neuenberger.serendipity.ProbabilityProcess;
import de.neuenberger.serendipity.SimpleProbabilityOutcome;
import de.neuenberger.serendipity.em.Team.TeamProbabilityFactory;

public class TestEM2016 {
	private Gruppe gruppeA;
	private Gruppe gruppeB;
	private Gruppe gruppeC;
	private Gruppe gruppeD;
	private Gruppe gruppeE;
	private Gruppe gruppeF;


	@Before
	public void create() {
		
		Team team01 = new Team("Frankreich", "Nike");
		Team team02 = new Team("Schweiz", "Puma");
		Team team03 = new Team("Rumänien", "Joma");
		Team team04 = new Team("Albanien", "Macron");
		gruppeA = createGroup(team01,team02,team03,team04);
		
		
		Team team05 = new Team("Russland", "Adidas");
		Team team06 = new Team("Wales", "Adidas");
		Team team07 = new Team("England", "Nike");
		Team team08 = new Team("Slowakei", "Puma");
		gruppeB = createGroup(team05,team06,team07,team08);
		
		Team team09 = new Team("Deutschland", "Adidas");
		Team team10 = new Team("Ukraine", "Adidas");
		Team team11 = new Team("Nordirland", "Adidas");
		Team team12 = new Team("Polen", "Nike");
		gruppeC = createGroup(team09, team10, team11, team12);
		
		
		Team team13 = new Team("Spanien", "Adidas");
		Team team14 = new Team("Kroatien", "Nike");
		Team team15 = new Team("Türkey", "Nike");
		Team team16 = new Team("Tschechien", "Puma");
		gruppeD = createGroup(team13,team14,team15,team16);
		
		Team team17 = new Team("Belgien", "Adidas");
		Team team18 = new Team("Schweden", "Adidas");
		Team team19 = new Team("Italien", "Puma");
		Team team20 = new Team("Irland", "Umbro");
		gruppeE = createGroup(team17,team18,team19,team20);
		
		Team team21 = new Team("Ungarn", "Adidas");
		Team team22 = new Team("Portugal", "Nike");
		Team team23 = new Team("Österreich", "Puma");
		Team team24 = new Team("Island", "Erreà");
		gruppeF = createGroup(team21,team22,team23,team24);
	}
	private ProbabilityProcess createFinal(TeamProbabilityFactory factory) {	
		ProbabilityProcess bestOfEight1 = createKoMatch(secondOf(factory, gruppeA),secondOf(factory, gruppeC));
		ProbabilityProcess bestOfEight2 = createKoMatch(selectBestOf(factory, gruppeB),thirdOf(factory, gruppeA,gruppeC,gruppeD));
		ProbabilityProcess bestOfEight3 = createKoMatch(selectBestOf(factory, gruppeD),thirdOf(factory, gruppeB,gruppeE,gruppeF));
		ProbabilityProcess bestOfEight4 = createKoMatch(selectBestOf(factory, gruppeA),thirdOf(factory, gruppeC,gruppeD,gruppeE));
		ProbabilityProcess bestOfEight5 = createKoMatch(selectBestOf(factory, gruppeC),thirdOf(factory, gruppeA,gruppeB,gruppeF));
		ProbabilityProcess bestOfEight6 = createKoMatch(selectBestOf(factory, gruppeF),secondOf(factory, gruppeE));
		ProbabilityProcess bestOfEight7 = createKoMatch(selectBestOf(factory, gruppeE),secondOf(factory, gruppeD));
		ProbabilityProcess bestOfEight8 = createKoMatch(secondOf(factory, gruppeB),secondOf(factory, gruppeF));
		
		ProbabilityProcess quarterFinal1 = createKoMatch(bestOfEight1, bestOfEight3);
		ProbabilityProcess quarterFinal2 = createKoMatch(bestOfEight2, bestOfEight6);
		ProbabilityProcess quarterFinal3 = createKoMatch(bestOfEight5, bestOfEight7);
		ProbabilityProcess quarterFinal4 = createKoMatch(bestOfEight4, bestOfEight8);
		
		ProbabilityProcess semiFinal1 = createKoMatch(quarterFinal1, quarterFinal2);
		ProbabilityProcess semiFinal2 = createKoMatch(quarterFinal3, quarterFinal4);
		
		return createKoMatch(semiFinal1, semiFinal2);
	}
	
	private ProbabilityProcess thirdOf(TeamProbabilityFactory factory, Gruppe gruppe1, Gruppe gruppe2, Gruppe gruppe3) {
		ListSelection selection = new ListSelection(1, ProbabilityProcess.toProbabilities(gruppe3.toProbabilityProcess(factory),gruppe2.toProbabilityProcess(factory),gruppe1.toProbabilityProcess(factory)));
		return new MultiplierProcess(selection, 0.9);
	}

	private ProbabilityProcess selectBestOf(TeamProbabilityFactory factory, Gruppe gruppe) {
		return new ListSelection(1, ProbabilityProcess.toProbabilities(gruppe.toProbabilityProcess(factory)));
	}

	ProbabilityProcess secondOf(TeamProbabilityFactory factory, Gruppe gruppe) {
		ListSelection selection = new ListSelection(1, ProbabilityProcess.toProbabilities(gruppe.toProbabilityProcess(factory)));
		return new MultiplierProcess(selection, 0.95);
	}

	ProbabilityProcess createKoMatch(ProbabilityProcess pa, ProbabilityProcess pb) {
		ProbabilityOutcome[] probabilityArray = ProbabilityProcess.toProbabilities(pa,pb);
		return new ListSelection(1, probabilityArray);
	}


	public Gruppe createGroup(Team ... teams) {
		return new Gruppe(teams);
	}
	
	@Test
	public void testFinalTeam() {
		ProbabilityProcess finalMatch = createFinal(Team.getTeamProbabilityFactoryName());
		List<ProbabilityOutcome> probabilityOutput = finalMatch.getProbabilityOutput();
		double sumAll = 0.0;
		for (ProbabilityOutcome probability : probabilityOutput) {
			System.out.println(probability);
			sumAll+=probability.getProbability();
		}
		Assertions.assertThat(probabilityOutput).hasSize(24);
		Assertions.assertThat(sumAll).isCloseTo(1.0, Offset.offset(0.00000001));
	}
	
	@Test
	public void testFinalSupplier() {
		ProbabilityProcess finalMatch = createFinal(Team.getTeamProbabilityFactorySupplier());
		List<ProbabilityOutcome> probabilityOutput = finalMatch.getProbabilityOutput();
		double sumAll = 0.0;
		printLine();
		for (ProbabilityOutcome probability : probabilityOutput) {
			System.out.println(probability);
			sumAll+=probability.getProbability();
		}
		Assertions.assertThat(probabilityOutput).hasSize(7);
		Assertions.assertThat(sumAll).isCloseTo(1.0, Offset.offset(0.00000001));
	}
	
	@Test
	public void testSimple() {
		String titleF = "f";
		String titleE = "e";
		String titleA = "a";
		ProbabilityOutcome pa=new SimpleProbabilityOutcome(titleA);
		String titleB = "b";
		ProbabilityOutcome pb=new SimpleProbabilityOutcome(titleB);
		ProbabilityOutcome pc=new SimpleProbabilityOutcome("c");
		ProbabilityOutcome pd=new SimpleProbabilityOutcome("d");
		ProbabilityOutcome pe=new SimpleProbabilityOutcome(titleE);
		ProbabilityOutcome pf=new SimpleProbabilityOutcome(titleF);
		
		ListSelection selectionAB = new ListSelection(1, pa,pb);
		ListSelection selectionCD = new ListSelection(1, pc,pd,pe,pf);
		ListSelection selectionF = new ListSelection(1, ProbabilityProcess.toProbabilities(selectionAB,selectionCD));
		
		List<ProbabilityOutcome> result = selectionF.getProbabilityOutput();
		
		double probabilities = ProbabilityOutcome.sumAllProbabilities(result);
		Assertions.assertThat(result).hasSize(6);
		Assertions.assertThat(probabilities).isCloseTo(1.0, Offset.offset(0.000000001));
		
		ProbabilityOutcome finalPa = ProbabilityOutcome.getByTitle(result, titleA);
		ProbabilityOutcome finalPb = ProbabilityOutcome.getByTitle(result, titleB);
		ProbabilityOutcome finalPe = ProbabilityOutcome.getByTitle(result, titleE);
		ProbabilityOutcome finalPf = ProbabilityOutcome.getByTitle(result, titleF);
		Assertions.assertThat(finalPf.getProbability()).isEqualTo(finalPe.getProbability());
		Assertions.assertThat(finalPa.getProbability()).isEqualTo(finalPb.getProbability());
		Assert.assertTrue(finalPf.getProbability()<finalPa.getProbability());
		printLine();
		for (ProbabilityOutcome probability : result) {
			System.out.println(probability);
		}
	}
	private void printLine() {
		System.out.println("-----------------");
	}

}
