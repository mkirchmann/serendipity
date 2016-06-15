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
	
	protected final Team team01France = new Team("Frankreich", "Nike");
	protected final Team team02Switzerland = new Team("Schweiz", "Puma");
	protected final Team team03Romania = new Team("Rumänien", "Joma");
	protected final Team team04Albania = new Team("Albanien", "Macron");
	protected final Team team05Russia = new Team("Russland", "Adidas");
	protected final Team team06Wales = new Team("Wales", "Adidas");
	protected final Team team07England = new Team("England", "Nike");
	protected final Team team08Slovakia = new Team("Slowakei", "Puma");
	protected final Team team09Germany = new Team("Deutschland", "Adidas");
	protected final Team team10Ukraine = new Team("Ukraine", "Adidas");
	protected final Team team11NorthernIreland = new Team("Nordirland", "Adidas");
	protected final Team team12Poland = new Team("Polen", "Nike");
	protected final Team team13Spain = new Team("Spanien", "Adidas");
	protected final Team team14Croatia = new Team("Kroatien", "Nike");
	protected final Team team15Turkey = new Team("Türkey", "Nike");
	protected final Team team16CzechRepublic = new Team("Tschechien", "Puma");
	protected final Team team17Belgium = new Team("Belgien", "Adidas");
	protected final Team team18Sweden = new Team("Schweden", "Adidas");
	protected final Team team19Italy = new Team("Italien", "Puma");
	protected final Team team20Ireland = new Team("Irland", "Umbro");
	protected final Team team21Hungary = new Team("Ungarn", "Adidas");
	protected final Team team22Portugal = new Team("Portugal", "Nike");
	protected final Team team23Austria = new Team("Österreich", "Puma");
	protected final Team team24Iceland = new Team("Island", "Erreà");

	public SimpleFinalFactory(TeamProbabilityFactory factory) {
		this.factory = factory;
		gruppeA = createGruppeA();
		gruppeB = createGruppeB();
		gruppeC = createGruppeC();
		gruppeD = createGruppeD();
		gruppeE = createGruppeE();
		gruppeF = createGruppeF();
	}

	protected Gruppe createGruppeF() {
		Gruppe gruppe = createGroup(team21Hungary,team22Portugal,team23Austria,team24Iceland);
		return gruppe;
	}

	protected Gruppe createGruppeE() {
		Gruppe gruppe = createGroup(team17Belgium,team18Sweden,team19Italy,team20Ireland);
		return gruppe;
	}

	protected Gruppe createGruppeD() {
		Gruppe gruppe = createGroup(team13Spain,team14Croatia,team15Turkey,team16CzechRepublic);
		return gruppe;
	}

	protected Gruppe createGruppeC() {
		 Gruppe gruppe = createGroup(team09Germany, team10Ukraine, team11NorthernIreland, team12Poland);
		return gruppe;
	}

	protected Gruppe createGruppeB() {
		Gruppe gruppe =  createGroup(team05Russia,team06Wales,team07England,team08Slovakia);
		return gruppe;
	}

	protected Gruppe createGruppeA() {
		Gruppe gruppe = createGroup(team01France,team02Switzerland,team03Romania,team04Albania);
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
