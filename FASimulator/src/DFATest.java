import org.junit.Assert;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class DFATest {

    // Test folder
    @org.junit.jupiter.api.Test
    void testInput1() throws IOException {
        String filePath = "/Users/rubalgoyal/Desktop/561_TOC/FiniteAutomataSimulator/EncodingTestCase/tests/tc0.txt";
        File nfaEncodingFile = new File(filePath);
        NFA nfa = new NFA(nfaEncodingFile);

        DFA dfa = new DFA(nfa);

        String inputString;
        String inputFilePath = "/Users/rubalgoyal/Desktop/561_TOC/FiniteAutomataSimulator/EncodingTestCase/tests/in0_1.txt";
        BufferedReader inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();

        // 1st test
        Assert.assertFalse("String 1 not accepted", dfa.trace(inputString));
        // 2nd test
        inputFilePath = inputFilePath.replace("in0_1", "in0_2");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertTrue("String 2 accepted", dfa.trace(inputString));

        // 3rd test
        inputFilePath = inputFilePath.replace("in0_2", "in0_3");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertFalse("String 3 not accepted", dfa.trace(inputString));

        // 4th test
        inputFilePath = inputFilePath.replace("in0_3", "in0_4");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertTrue("String 4 accepted", dfa.trace(inputString));

        // 5th test
        inputFilePath = inputFilePath.replace("in0_4", "in0_5");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertFalse("String 5 not accepted", dfa.trace(inputString));
    }
    @org.junit.jupiter.api.Test
    void testInput2() throws IOException {
        String filePath = "/Users/rubalgoyal/Desktop/561_TOC/FiniteAutomataSimulator/EncodingTestCase/tests/tc1.txt";
        File nfaEncodingFile = new File(filePath);
        NFA nfa = new NFA(nfaEncodingFile);

        DFA dfa = new DFA(nfa);

        String inputString;
        String inputFilePath = "/Users/rubalgoyal/Desktop/561_TOC/FiniteAutomataSimulator/EncodingTestCase/tests/in1_1.txt";
        BufferedReader inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();

        // 1st test
        Assert.assertTrue("String 1 accepted", dfa.trace(inputString));
        // 2nd test
        inputFilePath = inputFilePath.replace("in1_1", "in1_2");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertTrue("String 2 accepted", dfa.trace(inputString));

        // 3rd test
        inputFilePath = inputFilePath.replace("in1_2", "in1_3");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertTrue("String 3 accepted", dfa.trace(inputString));

        // 4th test
        inputFilePath = inputFilePath.replace("in1_3", "in1_4");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertFalse("String 4 not accepted", dfa.trace(inputString));

        // 5th test
        inputFilePath = inputFilePath.replace("in1_4", "in1_5");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertFalse("String 5 not accepted", dfa.trace(inputString));
    }

    @org.junit.jupiter.api.Test
    void testInput3() throws IOException {
        String filePath = "/Users/rubalgoyal/Desktop/561_TOC/FiniteAutomataSimulator/EncodingTestCase/tests/tc2.txt";
        File nfaEncodingFile = new File(filePath);
        NFA nfa = new NFA(nfaEncodingFile);

        DFA dfa = new DFA(nfa);

        String inputString;
        String inputFilePath = "/Users/rubalgoyal/Desktop/561_TOC/FiniteAutomataSimulator/EncodingTestCase/tests/in2_1.txt";
        BufferedReader inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();

        // 1st test
        Assert.assertFalse("String 1 for not accepted", dfa.trace(inputString));
        // 2nd test
        inputFilePath = inputFilePath.replace("in2_1", "in2_2");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertFalse("String 2 not accepted", dfa.trace(inputString));

        // 3rd test
        inputFilePath = inputFilePath.replace("in2_2", "in2_3");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertTrue("String 3 accepted", dfa.trace(inputString));

        // 4th test
        inputFilePath = inputFilePath.replace("in2_3", "in2_4");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertTrue("String 4 accepted", dfa.trace(inputString));

        // 5th test
        inputFilePath = inputFilePath.replace("in2_4", "in2_5");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertFalse("String 5 not accepted", dfa.trace(inputString));
    }
    @org.junit.jupiter.api.Test
    void testInput4() throws IOException {
        String filePath = "/Users/rubalgoyal/Desktop/561_TOC/FiniteAutomataSimulator/EncodingTestCase/tests/tc3.txt";
        File nfaEncodingFile = new File(filePath);
        NFA nfa = new NFA(nfaEncodingFile);

        DFA dfa = new DFA(nfa);

        String inputString;
        String inputFilePath = "/Users/rubalgoyal/Desktop/561_TOC/FiniteAutomataSimulator/EncodingTestCase/tests/in3_1.txt";
        BufferedReader inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();

        // 1st test
        Assert.assertTrue("String 1 accepted", dfa.trace(inputString));
        // 2nd test
        inputFilePath = inputFilePath.replace("in3_1", "in3_2");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertFalse("String 2 not accepted", dfa.trace(inputString));

        // 3rd test
        inputFilePath = inputFilePath.replace("in3_2", "in3_3");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertTrue("String 3 accepted", dfa.trace(inputString));

        // 4th test
        inputFilePath = inputFilePath.replace("in3_3", "in3_4");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertFalse("String 4 not accepted", dfa.trace(inputString));

        // 5th test
        inputFilePath = inputFilePath.replace("in3_4", "in3_5");
        inputReader = new BufferedReader(new FileReader(inputFilePath));
        inputString = inputReader.readLine();
        inputReader.close();
        Assert.assertTrue("String 5 accepted", dfa.trace(inputString));
    }

}