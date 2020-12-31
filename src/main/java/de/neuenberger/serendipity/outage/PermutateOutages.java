package de.neuenberger.serendipity.outage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.neuenberger.serendipity.ProbabilityOutcome;
import de.neuenberger.serendipity.SimpleProbabilityOutcome;

public class PermutateOutages {
	private final List<ProbabilityOutcome> inputWorkingProbabilities;

	public PermutateOutages(List<? extends ProbabilityOutcome> inputWorkingProbabilities) {
		this.inputWorkingProbabilities = new ArrayList<>(inputWorkingProbabilities);
	}
	
	List<List<EntityOutageResult>> process() {
		if (inputWorkingProbabilities.isEmpty()) {
			return Collections.emptyList();
		}
		List<List<EntityOutageResult>> permutated=new ArrayList<>((int) Math.pow(2,inputWorkingProbabilities.size()));
		
		
		
		List<EntityOutageResult> subList=new ArrayList<>();
		permutated.add(subList);
		buildSubChain(permutated, subList,inputWorkingProbabilities);
		
		
		return permutated;
		
	}
	
	List<ProbabilityOutcome> simplifiedProcess() {
		List<ProbabilityOutcome> intermedResult = process().stream().map(subList->{
			int sum = subList.stream().mapToInt(EntityOutageResult::getScore).sum();
			double prob=subList.stream().mapToDouble(EntityOutageResult::getProbability).reduce(1, (a,b)->a*b);
			return new SimpleProbabilityOutcome(sum+"-points", prob);
		}).collect(Collectors.toList());
		Map<String, List<ProbabilityOutcome>> grouped = intermedResult.stream().collect(Collectors.groupingBy(ProbabilityOutcome::getTitle));
		return grouped.entrySet().stream().map(entry->{
			double summedProbability = entry.getValue().stream().mapToDouble(ProbabilityOutcome::getProbability).sum();
			String title = entry.getKey();
			return new SimpleProbabilityOutcome(title, summedProbability);
		}).collect(Collectors.toList());
	}

	private void buildSubChain(List<List<EntityOutageResult>> permutated, List<EntityOutageResult> subList, List<ProbabilityOutcome> selectables) {
		if (selectables.isEmpty()) {
			return;
		}
		ProbabilityOutcome probabilityOutcome = selectables.get(0);
		
		boolean first=true;
		List<EntityOutageResult> conservatedSubList=new ArrayList<>(subList);
		for (EventResult result : EventResult.values()) {
			List<EntityOutageResult> innerSubList;
			if (!first) {
				 innerSubList = new ArrayList<>(conservatedSubList);
				 permutated.add(innerSubList);
			} else {
				innerSubList = subList;
			}
			innerSubList.add(new EntityOutageResult(result, probabilityOutcome));
			List<ProbabilityOutcome> nextSelectables = selectables.stream().skip(1).collect(Collectors.toList());
			buildSubChain(permutated, innerSubList, nextSelectables);
			first=false;
		}
	}
}
