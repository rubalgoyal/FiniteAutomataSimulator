import java.util.*;

public class DFAMinimized {
    private Map<String, Set<Set<String>>> finalStates = new HashMap<>();
    private Map<String, Set<Set<String>>>  nonFinalStates = new HashMap<>();
    private Set<String> startingStates;
    private String startingGroup;
    private DFA dfa;
    private Map<String, Map<String, String>> transitionMatrix = new HashMap<>();

    public DFAMinimized(DFA dfa){
        this.dfa = dfa;
        this.startingStates = new HashSet<>(dfa.getStartingState());
        minimizeStates();
    }

    public boolean trace(String inputString){
        // CurrentStates the given character is in
        String currentStates = this.startingGroup;

        // Trace to store the path
        List<String> tracePath = new ArrayList<>();

        for(char inputChar : inputString.toCharArray()){
            if(!this.dfa.getNfa().getInputSymbols().contains(String.valueOf(inputChar)))
                return  false;

            Map<String, String> transitions = this.transitionMatrix.get(currentStates);

            if(transitions != null && transitions.containsKey(String.valueOf(inputChar))){
                String nextState = transitions.get(String.valueOf(inputChar));
                currentStates = nextState;
            }
            else
                return false;
            tracePath.add(String.join(",", currentStates));
        }


        if(this.finalStates.containsKey(currentStates))
            return true;
        return false;
    }

    private void minimizeStates(){
        Set<Set<String>> nonFinalStates = new HashSet<>();
        Set<Set<String>> finalStates = new HashSet<>();
        nonFinalStates.add(this.startingStates);
        for(Set<String> stateSet: this.dfa.getDfaStates()){
            if(this.dfa.getAcceptingStates().contains(stateSet))
                finalStates.add(stateSet);
            else {
                nonFinalStates.add(stateSet);
            }
        }
        // Now we need to partition the states into groups
        this.finalStates = partitionAcceptingStates(finalStates, true);
        this.nonFinalStates = partitionAcceptingStates(nonFinalStates, false);
        createTransitionMatrix();
        this.startingGroup = getStatesGroup(this.startingStates);
    }

    private Map<String, Set<Set<String>>> partitionAcceptingStates(Set<Set<String>> inputStates, boolean isFinalState){
        int currentGroup = 0;
        String prefix = isFinalState == true ? "F":"NF";
        Queue<Set<String>> stateQueue = new LinkedList<>(inputStates);
        Map<String, Set<Set<String>>> partitionMap = new HashMap<>();

        while (!stateQueue.isEmpty()){
            Set<String> currentState = stateQueue.poll();
            if(partitionMap.size() == 0){
                Set<Set<String>> map = new HashSet<>();
                map.add(currentState);
                partitionMap.put(prefix+currentGroup, map);
            }
            else{
                boolean sameGroup = false;
                String whichGroup = null;
                for(String group: partitionMap.keySet()){
                    if(isSameGroup(currentState, partitionMap, group)){
                        sameGroup = true;
                        whichGroup = group;
                        break;
                    }
                }
                if(sameGroup){
                    Set<Set<String>> map = new HashSet<>();
                    map.add(currentState);
                    partitionMap.put(whichGroup, map);
                }
                else {
                    currentGroup = currentGroup + 1;
                    Set<Set<String>> map = new HashSet<>();
                    map.add(currentState);
                    partitionMap.put(prefix + currentGroup, map);
                }
            }

        }

        return partitionMap;
    }

    private boolean isSameGroup(Set<String> currentState, Map<String, Set<Set<String>>> partitionMap, String group){
        boolean sameGroup = true;
        Map<String, Set<String>> transitions = this.dfa.getDfaTransitionTable().get(currentState);
        for(String inputSymbol : transitions.keySet()){
            Set<String> nextStates = transitions.get(inputSymbol);
            if(!partitionMap.get(group).contains(nextStates)){
                sameGroup = false;
                break;
            }
        }

        return sameGroup;
    }

    private void createTransitionMatrix(){
        HashMap<Set<String>, Map<String, Set<String>>> dfaTransition = this.dfa.getDfaTransitionTable();
        for(Set<String> fromState: dfaTransition.keySet()){
            if(fromState != null){
                String fromStateGroup = getStatesGroup(fromState);
                if(fromStateGroup != null){
                    for(String input : dfaTransition.get(fromState).keySet()){
                        Set<String> toState = dfaTransition.get(fromState).get(input);
                        if(toState != null){
                            String toStateGroup = getStatesGroup(toState);
                            if(toStateGroup != null){
                                if(this.transitionMatrix.containsKey(fromStateGroup)){
                                    if(this.transitionMatrix.get(fromStateGroup).containsKey(input)){
                                        this.transitionMatrix.get(fromStateGroup).put(input, toStateGroup);
                                    }
                                    else {
                                        Map<String, String> map = this.transitionMatrix.get(fromStateGroup);
                                        map.put(input, toStateGroup);
                                        this.transitionMatrix.put(fromStateGroup, map);
                                    }
                                }
                                else{
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put(input, toStateGroup);
                                    this.transitionMatrix.put(fromStateGroup, map);
                                }
                            }
                        }
                    }
                }
            }
            }
    }

    private String getStatesGroup(Set<String> state){
        String  currentGroup = null;
        boolean notFound = true;
        for(String group : this.finalStates.keySet()){
            if(this.finalStates.get(group).contains(state)){
                currentGroup = group;
                notFound = false;
                break;
            }
        }

        if(notFound){
            for(String group : this.nonFinalStates.keySet()){
                if(this.nonFinalStates.get(group) != null){
                    if(this.nonFinalStates.get(group).contains(state)){
                        currentGroup = group;
                        notFound = true;
                        break;
                    }
                }
            }
        }

        return currentGroup;
    }

}
