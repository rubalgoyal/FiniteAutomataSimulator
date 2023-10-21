import java.util.*;

public class DFA {
    private NFA nfa;
    public NFA getNfa() {
        return nfa;
    }

    private Set<Set<String>> dfaStates = new HashSet<>();
    public Set<Set<String>> getDfaStates() {
        return dfaStates;
    }

    private Set<String> startingState = new HashSet<>();
    public Set<String> getStartingState() {
        return startingState;
    }

    private Set<Set<String>> acceptingStates = new HashSet<>();
    public Set<Set<String>> getAcceptingStates() {
        return acceptingStates;
    }

    private HashMap<Set<String>, Map<String, Set<String>>> dfaTransitionTable = new HashMap<>();
    public HashMap<Set<String>, Map<String, Set<String>>> getDfaTransitionTable() {
        return dfaTransitionTable;
    }


    public DFA(NFA nfa){
        this.nfa = nfa;
        getDfaStartState();
        convertToDfa();
    }

    public boolean trace(String inputString){
        // CurrentStates the given character is in
        Set<String> currentStates = this.startingState;

        // Trace to store the path
        List<String> tracePath = new ArrayList<>();
        currentStates.add(String.join(",", currentStates));

        for(char inputChar : inputString.toCharArray()){
            if(!this.nfa.getInputSymbols().contains(String.valueOf(inputChar)))
                return  false;
            Map<String, Set<String>> transitions = this.dfaTransitionTable.get(currentStates);

            if(transitions != null && transitions.containsKey(String.valueOf(inputChar))){
                Set<String> nextState = transitions.get(String.valueOf(inputChar));
                currentStates = nextState;
            }
            else
                return false;
            tracePath.add(String.join(",", currentStates));
        }

        for(String state: currentStates){
            if(this.nfa.getAcceptingStates().contains(state))
                return true;
        }
        return false;
    }

    private void convertToDfa(){
        // Initialize an empty queue to get Epsilon move states of NFA
        Queue<Set<String>> stateQueue = new LinkedList<>();

        stateQueue.offer(this.startingState);

        while (!stateQueue.isEmpty()){
            Set<String> currentState = stateQueue.poll();
            Map<String, Set<String>> transition = new HashMap<>();

            for(String inputSymbol : this.nfa.getInputSymbols()){
                if(inputSymbol.equals("e"))
                    continue;
                Set<String> nextStates = new HashSet<>();

                // First find all the next move state for that symbol
                for(String nfaState: currentState){
                    if(this.nfa.getTransitionTable().containsKey(nfaState) &&
                            this.nfa.getTransitionTable().get(nfaState).containsKey(inputSymbol)
                    )
                        nextStates.addAll(this.nfa.getTransitionTable().get(nfaState).get(inputSymbol) );
                }

                // Calculate epsilon closure of next states
                Set<String> epsilonClosure = new HashSet<>();
                for(String nextState: nextStates){
                    epsilonClosure.add(nextState);
                    epsilonClosure.addAll( this.nfa.getEpsilonMoveStates().getOrDefault(nextState, Collections.emptySet()) );
                }

                if(!epsilonClosure.isEmpty()){
                    transition.put(inputSymbol, epsilonClosure);
                    if(!this.dfaStates.contains(epsilonClosure) & transition.size() > 0){
                        this.dfaStates.add(epsilonClosure);
                        stateQueue.offer(epsilonClosure);
                    }

                }

                if(transition.size() > 0){
                    this.dfaTransitionTable.put(currentState, transition);

                    for(String finalState: this.nfa.getAcceptingStates()){
                        // If NFA's accepting state is contained in epsilon closure
                        if(epsilonClosure.contains(finalState))
                            this.acceptingStates.addAll(Collections.singleton(epsilonClosure));
//                        if(currentState.contains(finalState))
//                            this.acceptingStates.add(currentState);
//                        if(nextStates.contains(finalState))
//                            this.acceptingStates.add(nextStates);
                    }
                }
            }
        }
    }

    private void getDfaStartState(){
        //First Add the nfa starting state as the DFA starting state
        this.startingState.add(this.nfa.getStartingState());
        if(this.nfa.getEpsilonMoveStates().containsKey(this.nfa.getStartingState())){

            Set<String> nextStates = this.nfa.getEpsilonMoveStates().get(this.nfa.getStartingState());
            this.startingState.addAll(nextStates);

            while (!nextStates.isEmpty()){
                String next = nextStates.iterator().next();
                nextStates.remove(next);
                if(this.nfa.getEpsilonMoveStates().get(next) != null){
                    this.startingState.addAll(this.nfa.getEpsilonMoveStates().get(next));
                    nextStates.addAll(this.nfa.getEpsilonMoveStates().get(next));
                }
            }
        }
        this.dfaStates.addAll(Collections.singleton(this.startingState));
    }


    public int countNumTransitions(){
        int count = 0;
        for(Set<String> fromState: this.dfaTransitionTable.keySet()){
            for(String character: this.dfaTransitionTable.get(fromState).keySet())
                count = count + 1;
        }
        return count;
    }
}
