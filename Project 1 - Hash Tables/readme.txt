1. The folder contains a total of 6 files, one for the code and one for the output of each hashing algorithm, as shown below.

1.1 MultiHashing.java
1.2 multi_output.txt
1.3 DLeftHashing.java
1.4 left_output.txt
1.5 CuckooHashing.java
1.6 cuckoo_output.java

2. The input values have been hard coded in the program and will only run for the input values shown in the project file (aka, the demo values), 1000 table entries, 1000 flow, 3/4 hashes (3 for multi and cuckoo and 4 for dleft). 

3. The flow ID and the values used to calculate the hash function have been generated randomly, with a seed value specified, hence every time the program is run, a new value would be shown as output. 

4. After running the program multiple times, here's the *estimated* range of which the output for each algorithm will show up as: 

4.1 multiHashing: 800 - 830
4.2 dLeftHashing: 831 - 880
4.3 cuckooHashing: 881 - 930