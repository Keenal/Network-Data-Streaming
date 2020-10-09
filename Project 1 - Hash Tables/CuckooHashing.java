import java.util.Random;

public class CuckooHashing {
    public static final int numOfEntries = 1000; 
    public static final int numOfHashFunction = 3; 

    public static int[] hashTable = new int[numOfEntries];
    public static int[] hashModNums = {743, 22, 347};
    public static int[] hashValues = new int[numOfHashFunction];
    
    public static int counter = 0;
    public static int rec = 0;
    public static int k = 0;
    public static int cuckooStepLimit = 2;
    

    public static Random rand;

    public static void main(String[] args) {


        initHashTable(hashTable);
        
        generateNums4Table(hashTable);

        printHashTable(hashTable);

    }

    public static void initHashTable(int[] hashTable) {
        for(int i = 0; i < hashTable.length; i++) {
            hashTable[i] = -999999999; 
        }
    }

    public static void generateNums4Table(int[] hashTable) {
        rand = new Random();
        for(int i = 0; i < numOfEntries; i++) {
            rand.setSeed(i);
            int element = rand.nextInt(1000000);
            cuckoo(element, hashTable, -1, 0);
        }
    }

    public static void cuckoo(int element, int[] hashTable, int idx, int cuckooSteps) {
        
        boolean checker = false;

            if(cuckooSteps == cuckooStepLimit) {
                rec = 0;
            }

            for(int j = 0; j < hashValues.length; j++) {
                hashValues[j] = (element ^ hashModNums[j]) % hashTable.length; 
                if(hashTable[hashValues[j]] == element) {
                    break;
                }
            }

            for(int j = 0; j < hashValues.length; j++) {
                
                if(hashTable[hashValues[j]] == -999999999) {
                    hashTable[hashValues[j]] = element;
                    checker = true;
                    rec = -1;
                    cuckooSteps++;
                    break;
                }
            }

            if(checker == false) {
                int flowPlaceHolder = -1;
                while(k < 3) {
                    if(hashValues[k] != idx) {
                        flowPlaceHolder = hashTable[hashValues[k]];
                        hashTable[hashValues[k]] = element;
                        counter++;
                        rec = -1;
                        cuckoo(flowPlaceHolder, hashTable, hashValues[k], cuckooSteps + 1);
                    }
                    k++;
                    if(rec == -1) {
                        break;
                    }
                    if(hashTable[hashValues[0]] == flowPlaceHolder || hashTable[hashValues[1]] == flowPlaceHolder || hashTable[hashValues[2]] == flowPlaceHolder) {
                        break;
                    }
                }
    }
        k = 0;

    }

    public static void printHashTable(int[] hashTable) {
        int count = 0;
        for(int i = 0; i < hashTable.length; i++) {
            if(hashTable[i] != -999999999) {
                count++;
            }
        }
        System.out.println("count " + count);
    }


}
