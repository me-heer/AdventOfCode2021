package com.aoc;

import java.lang.reflect.Array;
import java.util.*;

public class Day14 extends AdventOfCode {
    Map<String, String> insertionRules = new HashMap<>();
    StringBuilder polymerTemplate = new StringBuilder("");

    @Override
    public void solve() {
        Scanner in = input;
        polymerTemplate = new StringBuilder(in.nextLine());

        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] rules = line.split(" -> ");
            if (rules.length == 2) {
                insertionRules.put(rules[0], rules[1]);
            }
        }

        for (int i = 0; i < 40; i++) {
            System.out.print("STEP: " + i);
            executeStep();
        }
        findMostAndLeastCommonElement(polymerTemplate);
    }

    private void findMostAndLeastCommonElement(StringBuilder polymerTemplate) {
        Map<Character, Long> elementWithOccurrences = new HashMap<>();
        for (int i = 0; i < polymerTemplate.length(); i++) {
            Character character = polymerTemplate.charAt(i);
            Long previousOccurrences = elementWithOccurrences.getOrDefault(character, 0l);
            Long updatedOccurrences = previousOccurrences + 1;
            elementWithOccurrences.put(character, updatedOccurrences);
        }
        elementWithOccurrences.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);
    }

    private void executeStep() {
        StringBuilder currentPolymerState = new StringBuilder(polymerTemplate);
        Map<String, String> applicableRules = findApplicableRules(insertionRules, currentPolymerState);
        Map<String, ArrayList<Integer>> rulesToIndex = getRulesToIndexMapping(applicableRules, currentPolymerState);
        for (Map.Entry<String, String> applicableRule: applicableRules.entrySet()) {
            String ruleCondition = applicableRule.getKey();
            String insertionCharacter = applicableRule.getValue();

            int[] currentIndexes = rulesToIndex.get(ruleCondition).stream().mapToInt(i -> i).toArray();

            for (int i = 0; i < currentIndexes.length; i++) {
                Integer insertIndex = currentIndexes[i];
                StringBuilder updatedPolymerState =
                        new StringBuilder(currentPolymerState.substring(0, insertIndex + 1) + insertionCharacter + currentPolymerState.substring(insertIndex + 1));
                currentPolymerState = updatedPolymerState;
                rulesToIndex = updateIndexes(ruleCondition, insertIndex, rulesToIndex);
                //update remaining
                for (int j = i; j < currentIndexes.length; j++) {
                   currentIndexes[j]++;
                }
            }

//            for (Integer insertIndex: rulesToIndex.get(ruleCondition)) {
//                String updatedPolymerState =
//                        currentPolymerState.substring(0, insertIndex + 1) + insertionCharacter + currentPolymerState.substring(insertIndex + 1);
//                currentPolymerState = updatedPolymerState;
//                rulesToIndex = updateIndexes(ruleCondition, insertIndex, rulesToIndex);
//            }
        }
        polymerTemplate = currentPolymerState;
    }

    private Map<String, ArrayList<Integer>> updateIndexes(String currentRuleCondition, Integer currentRuleIndex,
                                               Map<String, ArrayList<Integer>> rulesToIndexMap) {
        Map<String, ArrayList<Integer>> updatedRulesToIndexMap = new HashMap<>();
        for (Map.Entry<String, ArrayList<Integer>> ruleToIndexEntry: rulesToIndexMap.entrySet()) {
            String ruleEntry = ruleToIndexEntry.getKey();
            if (!ruleEntry.equals(currentRuleCondition)) {
                ArrayList<Integer> currentIndexes = ruleToIndexEntry.getValue();
                ArrayList<Integer> updatedCurrentIndexes = new ArrayList<>();
                for (Integer currentIndex: currentIndexes) {
                    if (currentIndex > currentRuleIndex)
                        currentIndex++;
                    updatedCurrentIndexes.add(currentIndex);
                }
                updatedRulesToIndexMap.put(ruleEntry, updatedCurrentIndexes);
            }
        }
        return updatedRulesToIndexMap;
    }

    private Map<String, ArrayList<Integer>> getRulesToIndexMapping(Map<String, String> applicableRules,
                                                              StringBuilder currentPolymerState) {
        Map<String, ArrayList<Integer>> rulesToIndexMapping = new HashMap<>();
        for (Map.Entry<String, String> applicableRule: applicableRules.entrySet()) {
            String ruleCondition = applicableRule.getKey();
            for (int i = 0; i < currentPolymerState.length();i++) {
                int upperBound = currentPolymerState.length() > (i + 2) ? (i + 2): currentPolymerState.length();
                String subStr = currentPolymerState.substring(i, upperBound);
                if (subStr.contains(ruleCondition)) {
                    ArrayList<Integer> occurrenceIndexes = rulesToIndexMapping.getOrDefault(ruleCondition,
                            new ArrayList<Integer>());
                    occurrenceIndexes.add(i);
                    rulesToIndexMapping.put(ruleCondition, occurrenceIndexes);
                }
            }
        }
        return rulesToIndexMapping;
    }

    private Map<String, String> findApplicableRules(Map<String, String> allRules, StringBuilder currentPolymerState) {
        Map<String, String> applicableRules = new HashMap<>();
        for (Map.Entry<String, String> rule: allRules.entrySet()) {
            String ruleCondition = rule.getKey();
            if (currentPolymerState.indexOf(ruleCondition) != -1)
                    applicableRules.put(rule.getKey(), rule.getValue());
        }
        return applicableRules;
    }

}
