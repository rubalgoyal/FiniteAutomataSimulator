# FiniteAutomataSimulator
In this porject, I have implemented a Finite Automata Simulator. For any given NFA Encoding text file, I first decode that to an instance of NFA class and then subsequently create instances of DFA, DFA Minimized. Then for any given input string, I trace the string and check if the string would be accepted for the given encoding for not. The application would print in the console:
- If input string is accepted $\rightarrow$ "yes"
- If input string is rejected $\rightarrow$ "no"

## Repository Structure
  - `EncodingTestCase`: Contains two subdirectorys _tests/_ and _evals/_.
      * _tests/_  conatins the test 3 NFA encoding files _tc{0,1,2}.txt_ and for each NFA encoding contains the 4 input string files _in{0,1,2}\_{0,1,2,3}.txt_ and corresponding answer files _answ{0,1,2}.txt_
      * _evals/_ similar to _tests/_ it also contains 3 NFA encoding _bm{1,2,3}.txt_ and corresponding 4 input string  files _eval{1,2,3}\_{1,2,3,4}.txt_ but these are used for benchmarking.

  - `FASimulator`: This folder conatins the Java code implementation for decoding the encoding into NFA and then depending upon which FA to call, will create instances of DFA or DFAMinimized.

## Code Implementation:
Along with implmenting the code, I have also tried to automate the **testing** by implementing unit testing for _tests/_ encoding and input strings.
  - `Main.java` : This is the main class for executing the FA. It requires 3 inputs ```simulationType, pathToNfaEncodingFile, pathToInputStringFile```.<br/>
     Depending on the simulation type (**simulationType = 1 $\rightarrow$ NFA, simulationType = 2 $\rightarrow$ DFA, simulationType = 3 $\rightarrow$ DFAMinimized**), main class creates the instances of NFA/DFA/DFAMinimized as necessary and then traces the string. The result is printed as "yes" or "no" as explained above.
  - `NFA.java` : The class implements the methods to parse the encoding into nfaStates, startingState, acceptingStates, inputSymbols, transitionFunction. and epsilonMoveStates in the constructor `NFA(File nfaEncoding)`. The the method `trace(String inputString)` can be invoked on the instance of the class to trace the string.
  - `DFA.java` : This class converts the `NFA nfa` instance into DFA parsing all the 5 tuples similar to `NFA.java`
  - `DFAMinimized.java` : This class converts the `DFA dfa` instance into DFAMinimized parsing all the 5 tuples similar to `DFA.java`
  - `Evaluation.java` : This is an evaluation class to evaluate the run time for each of the benchmark input strings.
    
### Test Suite
I have created an automated test suit for testing **NFA,DFA,DFAMinimized** for files available in _tests/_
  - `NFATest.java`: Tests for NFA encoding and string inputs
  - `DFATest.java`: Tests for DFA and input strings.
  - `DFAMinimizedTest.java` for DFAMinimized and input strings.
![Screenshot 2023-10-20 at 7 13 26 PM](https://github.com/rubalgoyal/FiniteAutomataSimulator/assets/105172154/8b1b4b53-dbc8-474f-b191-ff6f3f2f3166)
![Screenshot 2023-10-20 at 7 13 15 PM](https://github.com/rubalgoyal/FiniteAutomataSimulator/assets/105172154/9a915120-3849-4b57-8e35-a1a76c383ff2)
![Screenshot 2023-10-20 at 7 13 05 PM](https://github.com/rubalgoyal/FiniteAutomataSimulator/assets/105172154/80548f36-f80d-44e4-8aad-535ab13695a8)


## Benchmarking:
The following snippets show the benchmarking by running on Onyx. The time is the average of 5 runs.
![Screenshot 2023-10-20 at 7 25 24 PM](https://github.com/rubalgoyal/FiniteAutomataSimulator/assets/105172154/dc0ed789-aa63-47ce-a5fd-cc1ee5e0d8de)

### Observations
**bm1.txt:**
  - In this category, the NFA, DFA, and DFAMinimized have low sizes (states and transitions) for their respective input files.
  - The runtime for NFA, DFA, and DFAMinimized is generally low, and for many files, the runtime is zero. This suggests that the string processing for these input files is very fast, likely due to their simplicity.

**bm2.txt:**
  - The size of files is slightly larger compared to bm1.txt, but still relatively small.
  - For some input files, both DFA and DFAMinimized have non-zero runtimes, but they are still quite low. NFA runtime is often zero.
  - Compared to bm1.txt, the processing times are slightly higher, but still relatively fast.

**bm3.txt:**
  - In this category, the input files have larger sizes compared to bm1.txt and bm2.txt.
  - The NFA has non-zero runtimes for all input files, with the runtime increasing as the input file size grows.
  - The DFA and DFAMinimized have non-zero runtimes for some input files, and for the largest input file (eval3_4.txt), the DFA and DFAMinimized have significantly higher runtimes compared to NFA.

It's noticeable that the runtime for DFAMinimized is generally lower than the DFA, especially for larger input files. This indicates the benefit of minimizing the DFA for efficient string processing. The runtime varies significantly depending on the specific input file and its size. Larger input files tend to have longer runtimes, which is expected as there is more data to process.


## Instructions to run

1. Change the working directory to folder `FASimulator/src/`. Then execute the following command to compile the program
   ```
   javac Main.java
   ```
3. To run the NFA
   ```
   java Main 1 tests/tc0.txt tests/in0_1.txt
   ```
5. To run the DFA
   ```
   java Main 2 tests/tc0.txt tests/in0_1.txt
   ```
7. To run the DFAMinimized
   ```
   java Main 3 tests/tc0.txt tests/in0_1.txt 
   ```

To run the evaluations, do the following:
```
javac Evaluation.java
java Evaluation evals
```
