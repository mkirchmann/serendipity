package de.neuenberger.serendipity.em;

import de.neuenberger.serendipity.ListSelection;
import de.neuenberger.serendipity.MultiplierProcess;
import de.neuenberger.serendipity.ProbabilityOutcome;
import de.neuenberger.serendipity.ProbabilityProcess;
import de.neuenberger.serendipity.em.Team.TeamProbabilityFactory;

public class SimpleFinalFactory {
	private final Gruppe gruppeA;
	private final Gruppe gruppeB;
	private final Gruppe gruppeC;
	private final Gruppe gruppeD;
	private final Gruppe gruppeE;
	private final Gruppe gruppeF;
	private final TeamProbabilityFactory factory;
	
	public SimpleFinalFactory(TeamProbabilityFactory factory) {
		this.factory = factory;
		gruppeA = createGruppeA();
		gruppeB = createGruppeB();
		gruppeC = createGruppeC();
		gruppeD = createGruppeD();
		gruppeE = createGruppeE();
		gruppeF = createGruppeF();
	}

	private Gruppe createGruppeF() {
		Team team21 = new Team("Ungarn", "Adidas");
		Team team22 = new Team("Portugal", "Nike");
		Team team23 = new Team("Österreich", "Puma");
		Team team24 = new Team("Island", "Erreà");
		Gruppe gruppe = createGroup(team21,team22,team23,team24);
		return gruppe;
	}

	private Gruppe createGruppeE() {
		Team team17 = new Team("Belgien", "Adidas");
		Team team18 = new Team("Schweden", "Adidas");
		Team team19 = new Team("Italien", "Puma");
		Team team20 = new Team("Irland", "Umbro");
		Gruppe gruppe = createGroup(team17,team18,team19,team20);
		return gruppe;
	}

	private Gruppe createGruppeD() {
		Team team13 = new Team("Spanien", "Adidas");
		Team team14 = new Team("Kroatien", "Nike");
		Team team15 = new Team("Türkey", "Nike");
		Team team16 = new Team("Tschechien", "Puma");
		Gruppe gruppe = createGroup(team13,team14,team15,team16);
		return gruppe;
	}

	private Gruppe createGruppeC() {
		Team team09 = new Team("Deutschland", "Adidas");
		Team team10 = new Team("Ukraine", "Adidas");
		Team team11 = new Team("Nordirland", "Adidas");
		Team team12 = new Team("Polen", "Nike");
		 Gruppe gruppe = createGroup(team09, team10, team11, team12);
		return gruppe;
	}

	private Gruppe createGruppeB() {
		Team team05 = new Team("Russland", "Adidas");
		Team team06 = new Team("Wales", "Adidas");
		Team team07 = new Team("England", "Nike");
		Team team08 = new Team("Slowakei", "Puma");
		Gruppe gruppe =  createGroup(team05,team06,team07,team08);
		return gruppe;
	}

	private Gruppe createGruppeA() {
		Team team01 = new Team("Frankreich", "Nike");
		Team team02 = new Team("Schweiz", "Puma");
		Team team03 = new Team("Rumänien", "Joma");
		Team team04 = new Team("Albanien", "Macron");
		Gruppe gruppe = createGroup(team01,team02,team03,team04);
		return gruppe;
	}
	
	ProbabilityProcess createKoMatch(ProbabilityProcess pa, ProbabilityProcess pb) {
		ProbabilityOutcome[] probabilityArray = ProbabilityProcess.toProbabilities(pa,pb);
		return new ListSelection(1, probabilityArray);
	}
	
	public Gruppe createGroup(Team ... teams) {
		return new Gruppe(teams);
	}
	
	public ProbabilityProcess createFinal() {	
		ProbabilityProcess bestOfEight1 = createKoMatch(secondOf(gruppeA),secondOf(gruppeC));
		ProbabilityProcess bestOfEight2 = createKoMatch(selectBestOf(gruppeB),thirdOf(gruppeA,gruppeC,gruppeD));
		ProbabilityProcess bestOfEight3 = createKoMatch(selectBestOf(gruppeD),thirdOf(gruppeB,gruppeE,gruppeF));
		ProbabilityProcess bestOfEight4 = createKoMatch(selectBestOf(gruppeA),thirdOf(gruppeC,gruppeD,gruppeE));
		ProbabilityProcess bestOfEight5 = createKoMatch(selectBestOf(gruppeC),thirdOf(gruppeA,gruppeB,gruppeF));
		ProbabilityProcess bestOfEight6 = createKoMatch(selectBestOf(gruppeF),secondOf(gruppeE));
		ProbabilityProcess bestOfEight7 = createKoMatch(selectBestOf(gruppeE),secondOf(gruppeD));
		ProbabilityProcess bestOfEight8 = createKoMatch(secondOf(gruppeB),secondOf(gruppeF));
		
		ProbabilityProcess quarterFinal1 = createKoMatch(bestOfEight1, bestOfEight3);
		ProbabilityProcess quarterFinal2 = createKoMatch(bestOfEight2, bestOfEight6);
		ProbabilityProcess quarterFinal3 = createKoMatch(bestOfEight5, bestOfEight7);
		ProbabilityProcess quarterFinal4 = createKoMatch(bestOfEight4, bestOfEight8);
		
		ProbabilityProcess semiFinal1 = createKoMatch(quarterFinal1, quarterFinal2);
		ProbabilityProcess semiFinal2 = createKoMatch(quarterFinal3, quarterFinal4);
		
		return createKoMatch(semiFinal1, semiFinal2);
	}
	
	protected ProbabilityProcess thirdOf(Gruppe gruppe1, Gruppe gruppe2, Gruppe gruppe3) {
		ListSelection selection = new ListSelection(1, ProbabilityProcess.toProbabilities(gruppe3.toProbabilityProcess(factory),gruppe2.toProbabilityProcess(factory),gruppe1.toProbabilityProcess(factory)));
		return new MultiplierProcess(selection, 0.9);
	}

	protected ProbabilityProcess selectBestOf(Gruppe gruppe) {
		return new ListSelection(1, ProbabilityProcess.toProbabilities(gruppe.toProbabilityProcess(factory)));
	}

	protected ProbabilityProcess secondOf(Gruppe gruppe) {
		ListSelection selection = new ListSelection(1, ProbabilityProcess.toProbabilities(gruppe.toProbabilityProcess(factory)));
		return new MultiplierProcess(selection, 0.95);
	}

	public TeamProbabilityFactory getFactory() {
		return factory;
	}
}
