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
  - `Main.java` : This is the main class for executing the FA. It accepts the 3 inputs ```simulationType, pathToNfaEncodingFile, pathToInputStringFile```. 
  - `NFA.java` :
  - `DFA.java` :
  - `DFAMinimized.java` :
