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

public class ResultTable {

	Multimap<Integer, ProbabilityOutcome> map = ArrayListMultimap.create();
	
	public ResultTable(List<Match> result) {
		List<List<MatchResult>> permutations = permutate(result, new ArrayList<>());
		for (List<MatchResult> results : permutations) {
			Map<ProbabilityOutcome,Integer> mapTeamToPoints = new HashMap<>();
			for (MatchResult matchResult : results) {
				Match match = matchResult.getMatch();
				match.getTeam2();
				Consequence consequence = matchResult.getConsequence();
				addPoints(mapTeamToPoints, match.getTeam1(), consequence.getTeamOnePoints());
				addPoints(mapTeamToPoints, match.getTeam2(), consequence.getTeamTwoPoints());
			}
			
			Set<Entry<ProbabilityOutcome,Integer>> entrySet = mapTeamToPoints.entrySet();
			List<TableEntry> list = new ArrayList<>(entrySet.size());
			for (Entry<ProbabilityOutcome,Integer> entry : entrySet) {
				list.add(new TableEntry(entry.getKey(), entry.getValue()));
			}
			Collections.sort(list);
			TableEntry oldEntry = null;
			int oldIdx = 0;
			for (int i=0; i<list.size(); i++) {
				TableEntry entry = list.get(i);
				int toIdx;
				if (oldEntry != null) {
					if (oldEntry.getPoints().equals(entry.getPoints())) {
						toIdx = oldIdx;
					} else {
						toIdx = i;
					}
				} else {
					toIdx = i;
				}
				map.put(toIdx, entry.getTeam());
				oldEntry = entry;
				oldIdx = toIdx;
			}
		}
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
