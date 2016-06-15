package de.neuenberger.serendipity.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import de.neuenberger.serendipity.ListSelection;
import de.neuenberger.serendipity.ProbabilityOutcome;

/**
 * Table for calculating probabilites of reaching a certain position through matches. 
 * 
 * @author Michael Kirchmann
 *
 */
public class ResultTable {

	private Multimap<Integer, ProbabilityOutcome> map = ArrayListMultimap.create();
	
	public ResultTable(List<Match> result) {
		List<List<MatchResult>> permutations = permutate(result, new ArrayList<>());
		for (List<MatchResult> results : permutations) {
			Map<ProbabilityOutcome, Integer> mapTeamToPoints = createMapTeamToPoints(results);
			
			List<TableEntry> rankTable = createRankTable(mapTeamToPoints);
			Multimap<Integer, ProbabilityOutcome> intermediateRankToTeam = createRankToTeamMapping(rankTable);
			map.putAll(intermediateRankToTeam);
		}
	}


	private Multimap<Integer, ProbabilityOutcome> createRankToTeamMapping(List<TableEntry> rankTable) {
		TableEntry oldEntry = null;
		List<Integer> oldToIdxs = new ArrayList<>();
		List<TableEntry> entriesToPut = new ArrayList<>();
		Multimap<Integer, ProbabilityOutcome> intermediateMap = ArrayListMultimap.create();
		for (int i=0; i<rankTable.size(); i++) {
			TableEntry entry = rankTable.get(i);
			if (oldEntry != null) {
				if (!oldEntry.getPoints().equals(entry.getPoints())) {
					oldToIdxs.clear();
					entriesToPut.clear();
				}
			}
			oldToIdxs.add(i);
			entriesToPut.add(entry);
			for (Integer toIdx : oldToIdxs) {
				for (TableEntry tableEntry : entriesToPut) {
					Collection<ProbabilityOutcome> collection = intermediateMap.get(toIdx);
					if (!collection.contains(tableEntry.getTeam())) { // prevent duplicates at position.
						intermediateMap.put(toIdx, tableEntry.getTeam());
					}
				}
			}
			oldEntry = entry;
		}
		return intermediateMap;
	}


	private List<TableEntry> createRankTable(Map<ProbabilityOutcome, Integer> mapTeamToPoints) {
		Set<Entry<ProbabilityOutcome,Integer>> entrySet = mapTeamToPoints.entrySet();
		List<TableEntry> list = new ArrayList<>(entrySet.size());
		for (Entry<ProbabilityOutcome,Integer> entry : entrySet) {
			list.add(new TableEntry(entry.getKey(), entry.getValue()));
		}
		Collections.sort(list);
		return list;
	}


	private Map<ProbabilityOutcome, Integer> createMapTeamToPoints(List<MatchResult> results) {
		Map<ProbabilityOutcome,Integer> mapTeamToPoints = new HashMap<>();
		for (MatchResult matchResult : results) {
			Match match = matchResult.getMatch();
			match.getTeam2();
			Consequence consequence = matchResult.getConsequence();
			addPoints(mapTeamToPoints, match.getTeam1(), consequence.getTeamOnePoints());
			addPoints(mapTeamToPoints, match.getTeam2(), consequence.getTeamTwoPoints());
		}
		return mapTeamToPoints;
	}
	
	
	private void addPoints(Map<ProbabilityOutcome, Integer> mapTeamToPoints, ProbabilityOutcome team,
			int pointsToAdd) {
		Integer totalPoints = mapTeamToPoints.get(team);
		if (totalPoints==null) {
			totalPoints=0;
		}
		totalPoints+=pointsToAdd;
		mapTeamToPoints.put(team, totalPoints);
	}


	private List<List<MatchResult>> permutate(List<Match> matches, List<List<MatchResult>> results) {
		if (matches.isEmpty()) {
			return results;
		} else {
			List<Match> nextMatches = new LinkedList<>(matches);
			Match remove = nextMatches.remove(0);
			List<MatchResult> matchResults = remove.getMatchResults();
			List<List<MatchResult>> returnResult = new ArrayList<>(results.size()*matchResults.size());
			for (MatchResult matchResult : matchResults) {
				if (results.isEmpty()) {
					ArrayList<MatchResult> newList = new ArrayList<>();
					newList.add(matchResult);
					returnResult.add(newList);
				} else {
					for (List<MatchResult> list : results) {
						ArrayList<MatchResult> newList = new ArrayList<>(list);
						newList.add(matchResult);
						returnResult.add(newList);
					}
				}
			}
			return permutate(nextMatches, returnResult);
		}
		
	}


	public List<ProbabilityOutcome> selectPosition(int position) {
		if (position<0 || position>=map.size()) {
			throw new IllegalArgumentException("illegal position: "+position);
		}
		Collection<ProbabilityOutcome> collection = map.get(position);
		ProbabilityOutcome[] array = collection.toArray(new ProbabilityOutcome[]{});
		return new ListSelection(1, array).getProbabilityOutput();
	}
}
