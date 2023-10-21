import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Evaluation {

    public static void main(String[] args) throws IOException {
        Map<String, List<String>> encodingInputFiles = new HashMap<>();
        encodingInputFiles.put("bm1.txt", new ArrayList<>());
        encodingInputFiles.put("bm2.txt", new ArrayList<>());
        encodingInputFiles.put("bm3.txt", new ArrayList<>());

        encodingInputFiles.get("bm1.txt").add("eval1_1.txt");
        encodingInputFiles.get("bm1.txt").add("eval1_2.txt");
        encodingInputFiles.get("bm1.txt").add("eval1_3.txt");
        encodingInputFiles.get("bm1.txt").add("eval1_4.txt");

        encodingInputFiles.get("bm2.txt").add("eval2_1.txt");
        encodingInputFiles.get("bm2.txt").add("eval2_2.txt");
        encodingInputFiles.get("bm2.txt").add("eval2_3.txt");
        encodingInputFiles.get("bm2.txt").add("eval2_4.txt");

        encodingInputFiles.get("bm3.txt").add("eval3_1.txt");
        encodingInputFiles.get("bm3.txt").add("eval3_2.txt");
        encodingInputFiles.get("bm3.txt").add("eval3_3.txt");
        encodingInputFiles.get("bm3.txt").add("eval3_4.txt");

        System.out.printf("%n%nTime Indicated below are in Milliseconds%n%n");

        String folderPath = args[0];
        for (String fileName: encodingInputFiles.keySet()){
            File evalFile = new File(String.join("/",folderPath, fileName));

            System.out.println("---------------------------------------------------------------------------------");
            System.out.printf("%-30s%-25s%n","",fileName);

            System.out.printf("%-20s%-10s%-10s%-15s%n", "Input File", "NFA", "DFA", "DFAMinimized");
            System.out.println("---------------------------------------------------------------------------------");

            NFA nfa = new NFA(evalFile);
            DFA dfa = new DFA(nfa);
            DFAMinimized dfaMinimized = new DFAMinimized(dfa);

            List<Integer> nfaStatesTransitions = new ArrayList<>();
            nfaStatesTransitions.add(nfa.getNfaStates().size());
            nfaStatesTransitions.add(nfa.countNumTransitions());

            List<Integer> dfaStatesTransitions = new ArrayList<>();
            dfaStatesTransitions.add(dfa.getDfaStates().size());
            dfaStatesTransitions.add(dfa.countNumTransitions());

            List<Integer> dfaMinStatesTransitions = new ArrayList<>();
            dfaMinStatesTransitions.add(dfaMinimized.countStates());
            dfaMinStatesTransitions.add(dfaMinimized.countTransitions());

            System.out.printf("%-20s%-10s%-10s%-15s%n", "Size", nfaStatesTransitions, dfaStatesTransitions, dfaMinStatesTransitions);


            for (String inputFile: encodingInputFiles.get(fileName)){
                BufferedReader reader = new BufferedReader(new FileReader(String.join("/",folderPath, inputFile)));
                String inputString = reader.readLine();
                reader.close();

                long startTime = System.nanoTime();
                nfa.trace(inputString);
                long nfaTime = (System.nanoTime() - startTime)/1000000;

                startTime = System.nanoTime();
                dfa.trace(inputString);
                long dfaTime = (System.nanoTime() - startTime)/1000000;

                startTime = System.nanoTime();
                dfaMinimized.trace(inputString);
                long dfaMinimizedTime = (System.nanoTime() - startTime)/1000000;
                System.out.printf("%-20s%-10d%-10d%-15d%n",inputFile,nfaTime,dfaTime,dfaMinimizedTime);
            }
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("\n");
        }



    }

}
