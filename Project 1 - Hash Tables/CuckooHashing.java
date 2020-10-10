import java.util.Random;

public class CuckooHashing {
    public static final int numOfEntries = 1000; 
    public static final int numOfHashFunction = 3; 

    public static int[] hashTable = new int[numOfEntries];
    public static int[] hashModNums = new int[numOfHashFunction];
    
    public static int counter = 0;
    public static int rec = 0;
    public static int k = 0;
    public static int cuckooStepLimit = 2;
    public static boolean prevSuccess=false;
    public static int y=1;
    public static int count=0;
    static int maxCount=-1;
    public static Random rand;
    static int b=0;
    public static void main(String[] args) {


        initHashTable(hashTable);

        generateNums4ModNums(hashTable);

        generateNums4Table(hashTable);

        printHashTable(hashTable);


    }

    public static void generateNums4ModNums(int[] hashTable) {
        rand = new Random();
        for(int i = 0; i < numOfHashFunction; i++) {
            hashModNums[i] = rand.nextInt(1000);
        }
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
            int element = rand.nextInt(100000);
            cuckoo(element, hashTable, 0, 0);
        }
    }

    public static void cuckoo(int element, int[] hashTable, int k, int cuckooSteps) {
        
        boolean checker = false;

        
        int[] hashValues = new int[numOfHashFunction];

        if(cuckooSteps == cuckooStepLimit) {
            return;
        }
            

            // calculates 3 hash values per element
            for(int j = 0; j < hashValues.length; j++) {
                hashValues[j] = (element ^ hashModNums[j]) % 1000; 
                if(hashTable[hashValues[j]] == element && cuckooSteps==0) {
                    return;
                }
            }

            // if a spot is empty in the array, it places the element there
            for(int j = 0; j < hashValues.length; j++) {
                
                if(hashTable[hashValues[j]] == -999999999) {
                    hashTable[hashValues[j]] = element;
                    checker = true;
                    rec = -1;
                    prevSuccess=true;
                //    cuckooSteps++;
                    return;
                }




            }
            

            if(checker == false) {
                int flowPlaceHolder = -1;
                prevSuccess=false;
                while(k < 3) {
                        flowPlaceHolder = hashTable[hashValues[k]];
                        cuckoo(flowPlaceHolder, hashTable, k, cuckooSteps + 1);
                        if(prevSuccess==true){
                            hashTable[hashValues[k]] = element;
                            break;
                         
                        }
                        rec = -1;
             
                    
                    k++;
                }
            }

    }

    public static void printHashTable(int[] hashTable) {
        count = 0;
        for(int i = 0; i < hashTable.length; i++) {
            if(hashTable[i] != -999999999) {
                count++;
            }
        }
        System.out.println("count " + count);
    }


}
