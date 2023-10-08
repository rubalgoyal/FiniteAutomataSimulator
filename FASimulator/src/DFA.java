import java.util.*;

public class DFA {
    private NFA nfa;
    private Set<Set<String>> dfaStates = new HashSet<>();

    private Set<String> startingState = new HashSet<>();

    private String nfaStartingState;
    private HashMap<String, Set<String>> nfaEpsilonMoveStates = new HashMap<>();

    private HashMap<Set<String>, Map<String, Set<String>>> dfaTransitionTable = new HashMap<>();

    public DFA(NFA nfa){
        this.nfa = nfa;
        this.nfaStartingState = nfa.getStartingState();
        this.nfaEpsilonMoveStates = nfa.getEpsilonMoveStates();
        convertToDfa(false);
    }

    public DFA(NFA nfa, boolean printConversion){
        this.nfa = nfa;
        this.nfaStartingState = nfa.getStartingState();
        this.nfaEpsilonMoveStates = nfa.getEpsilonMoveStates();
        convertToDfa(printConversion);
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

    private void convertToDfa(boolean printStates){
        // Initialize an empty queue to get Epsilon move states of NFA
        Queue<Set<String>> stateQueue = new LinkedList<>();

        // First insert the NFA start state as the DFA start state
        this.startingState.add( this.nfaStartingState );

        // Then calculate the NFA starting state closure and add it as the DFA starting state
        if( this.nfaEpsilonMoveStates.containsKey(this.nfaStartingState) )
            this.startingState.addAll(this.nfaEpsilonMoveStates.get(this.nfaStartingState));

        stateQueue.offer(this.startingState);

        while (!stateQueue.isEmpty()){
            Set<String> currentState = stateQueue.poll();
            Map<String, Set<String>> transition = new HashMap<>();

            for(String inputSymbol : this.nfa.getInputSymbols()){
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
                    epsilonClosure.addAll( this.nfaEpsilonMoveStates.getOrDefault(nextState, Collections.emptySet()) );
                }

                if(!epsilonClosure.isEmpty()){
                    transition.put(inputSymbol, epsilonClosure);
                    if(!this.dfaStates.contains(epsilonClosure)){
                        this.dfaStates.add(epsilonClosure);
                        stateQueue.offer(epsilonClosure);
                    }
                }

                this.dfaTransitionTable.put(currentState, transition);
            }
        }

        if(printStates){
            // For debugging purpose
            System.out.println("DFA States:");
            for(Set<String> state: this.dfaStates){
                System.out.println(state);
            }

            System.out.println("DFA Transition");
            for(Map.Entry<Set<String>, Map<String, Set<String>>> entry: this.dfaTransitionTable.entrySet() ){
                Set<String> fromState = entry.getKey();
                Map<String, Set<String>> transitions = entry.getValue();
                for(Map.Entry<String, Set<String>> transEntry : transitions.entrySet()){
                    String input = transEntry.getKey();
                    Set<String> toState = transEntry.getValue();
                    System.out.println(fromState + " -> " + input + " -> " + toState);
                }
            }
        }
    }
}
