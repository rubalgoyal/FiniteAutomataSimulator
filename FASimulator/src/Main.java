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
        int simulationType;
        String nfaEncoding;
        String inputString;
        if (args.length != 0){
            if (args.length == 3){
                simulationType = Integer.parseInt(args[0]);
                BufferedReader reader = new BufferedReader(new FileReader(args[1]));
                inputString = reader.readLine();
                reader.close();
            }
            else{
                System.out.println("Please enter exactly 3 arguments (simulation type,NFA encoding file, input string file)");
                System.exit(1);
            }
        }
        else {
            File nfaEncodingFile = new File("/Users/rubalgoyal/Desktop/561_TOC/FiniteAutomataSimulator/EncodingTestCase/tests/tc3.txt");
//            File nfaEncodingFile = new File("/Users/rubalgoyal/Desktop/561_TOC/FiniteAutomataSimulator/EncodingTestCase/evals/bm3.txt");
            File inputStringFile = new File("/Users/rubalgoyal/Desktop/561_TOC/FiniteAutomataSimulator/EncodingTestCase/tests/in3_1.txt");
//            File inputStringFile = new File("/Users/rubalgoyal/Desktop/561_TOC/FiniteAutomataSimulator/EncodingTestCase/evals/eval1_3.txt");
            BufferedReader inputStringReader = new BufferedReader( new FileReader(inputStringFile));
            inputString = inputStringReader.readLine();

            NFA nfa = new NFA(nfaEncodingFile);
            System.out.println(nfa.trace(inputString));
            System.out.println(nfa.getAcceptingStates());
//            if(nfa.trace(inputString))
//                System.out.println("yes");
//            else
//                System.out.println("no");
//            DFA dfa = new DFA(nfa, true);
//            if(dfa.trace(inputString))
//                System.out.println("yes");
//            else
//                System.out.println("no");
        }
    }
}