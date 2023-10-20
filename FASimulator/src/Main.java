import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
//    private
    public static void main(String[] args) throws IOException {
        /*
        1 -> NFA
        2 -> DFA
        3 -> DFA_min
        4 -> DFA_dist
         */
        NFA nfa;
        if (args.length == 3){
            int simulationType = Integer.parseInt(args[0]);
            if(simulationType > 5){
                System.out.println("Simulation type needs to be between 1-4");
                System.exit(1);
            }
            File nfaEncoder = new File(args[1]);
            BufferedReader inputStringReader = new BufferedReader(new FileReader(args[2]));
            String inputString = inputStringReader.readLine();
            inputStringReader.close();

            nfa = new NFA(nfaEncoder);

            if(simulationType == 1){
                if(nfa.trace(inputString))
                    System.out.println("yes");
                else
                    System.out.println("no");
            }
            else {
                DFA dfa = new DFA(nfa);
                if(simulationType == 2){
                    if(dfa.trace(inputString))
                        System.out.println("yes");
                    else
                        System.out.println("no");
                }
                else {
                    DFAMinimized dfaMinimized = new DFAMinimized(dfa);
                    if(simulationType == 3){
                        if(dfaMinimized.trace(inputString))
                            System.out.println("yes");
                        else
                            System.out.println("no");
                    } else if (simulationType == 4) {
                        System.out.println("Not implemented");
                    }
                }
            }
        }
        else{
            System.out.println("Please enter exactly 3 arguments (simulation type,NFA encoding file, input string file)");
            System.exit(1);
        }
    }
}