import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Statement;
import java.util.*;

public class NFA {
    private File nfaEncoding;
    private Set<String> nfaStates = new HashSet<>();
    private String startingState;
    private Set<String> acceptingStates = new HashSet<>();;
    private HashMap<String, HashMap<String, Set<String>>> transitionTable = new HashMap<>();
    private HashMap<String, Set<String>> epsilonMoveStates = new HashMap<>();

    public Set<String> getNfaStates() {
        return this.nfaStates;
    }

    public String getStartingState() {
        return this.startingState;
    }

    public NFA(File nfaEncoding) throws IOException{
        this.nfaEncoding = nfaEncoding;
        parseEncoding();
    }

    private void parseEncoding() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nfaEncoding) );
        // 1st line
        String[] acceptingStates =  reader.readLine().split(" ") ;
        parseAcceptingStates(acceptingStates);
        // 2nd line
        String[] startingStates  = reader.readLine().split(" ");
        if(startingStates.length > 1)
            throw new RuntimeException("More than 1 accepting state is not acceptable in NFA");
        this.startingState = startingStates[0];
        this.nfaStates.add(startingStates[0]);
        // 3rd line
        String[] remainingStates =  reader.readLine().split(" ") ;
        parseRemainingStates(remainingStates);
        // 4th line
        String[] transitionFunction =  reader.readLine().split(" ") ;
        parseTransitionFunction(transitionFunction);
        reader.close();

        parseEpsilonTransitionStates();
        System.out.println(this.nfaStates);
    }

    private void parseAcceptingStates(String[] acceptingStates){
        if (acceptingStates.length == 0)
            return;

        this.nfaStates.add(acceptingStates[0]);
        this.acceptingStates.add(acceptingStates[0]);
        if (acceptingStates.length == 1)
            return;

        String[] newAcceptingStates = new String[acceptingStates.length - 1];
        System.arraycopy(acceptingStates, 1, newAcceptingStates, 0, acceptingStates.length - 1);

        parseAcceptingStates(newAcceptingStates);
    }

    private void parseRemainingStates(String[] remainingStates){
        if (remainingStates.length == 0)
            return;

        this.nfaStates.add(remainingStates[0]);
        if (remainingStates.length == 1)
            return;

        String[] newRemainingStates = new String[remainingStates.length - 1];
        System.arraycopy(remainingStates, 1, newRemainingStates, 0, remainingStates.length - 1);

        parseRemainingStates(newRemainingStates);
    }
    private void parseTransitionFunction(String[] transitionFunctions){
        if (transitionFunctions.length == 0)
            return;

        String transitionFunction = transitionFunctions[0];
        String[] fromStateStringToState = transitionFunction.split("");


        if(this.transitionTable.containsKey(fromStateStringToState[0])){
            if(this.transitionTable.get(fromStateStringToState[0]).containsKey(fromStateStringToState[1])){
                this.transitionTable.get(fromStateStringToState[0]).get(fromStateStringToState[1]).add(fromStateStringToState[2]);
            }
            else {

                Set<String> destStates = new HashSet<>();
                destStates.add(fromStateStringToState[2]);
                this.transitionTable.get(fromStateStringToState[0]).put(fromStateStringToState[1], destStates);
            }

        }else {
            HashMap<String, Set<String>> value = new HashMap<>();
            Set<String> destStates = new HashSet<>();
            destStates.add(fromStateStringToState[2]);
            value.put(fromStateStringToState[1], destStates);

            this.transitionTable.put(fromStateStringToState[0], value);
        }


        if (transitionFunctions.length == 1)
            return;

        String[] newTransitionFunctions = new String[transitionFunctions.length - 1];
        System.arraycopy(transitionFunctions, 1, newTransitionFunctions, 0, transitionFunctions.length - 1);

        parseTransitionFunction(newTransitionFunctions);
    }

    private void parseEpsilonTransitionStates(){
        for(String currentState : this.nfaStates){
            if( this.transitionTable.containsKey(currentState) && this.transitionTable.get(currentState).containsKey("e") ){
                // First add the next transition states to epsilon states.
                // Then find if we can travel from these states to next epsilon state.
                this.epsilonMoveStates.put(currentState, new HashSet<>());
                this.epsilonMoveStates.get(currentState).addAll(this.transitionTable.get(currentState).get("e"));
            }
        }

    }

    //TODO: Yet to implement
    public boolean trace(String inputString){
        // CurrentStates the given character is in
        Set<String> currentStates = new HashSet<>();
        currentStates.add(this.startingState);

        for(char inputChar : inputString.toCharArray()){
            //The next states the character can travel to
            Set<String> nextStates = new HashSet<>();

            for(String state : currentStates){
                // check if the given current state exists in the transition table & find next state
                if(
                        this.transitionTable.containsKey(state) &&
                                this.transitionTable.get(state).containsKey(String.valueOf(inputChar))
                )
                    nextStates.addAll( this.transitionTable.get(state).get(String.valueOf(inputChar)) );
            }
        }

        return false;
    }

}
