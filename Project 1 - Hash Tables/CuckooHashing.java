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
    public static int maxCount=-1;
    public static Random rand;
    public static int b=0;
    public static void main(String[] args) {


        initHashTable(hashTable);

        generateNums4ModNums(hashTable);

        generateNums4Table(hashTable);

        printHashTable(hashTable);


    }

    public static void generateNums4ModNums(int[] hashTable) {
        rand = new Random();
        int i = 0;
        while(i < numOfHashFunction) {
            hashModNums[i] = rand.nextInt(1000);
            i++;
        }
    }

    public static void initHashTable(int[] hashTable) {
        int i = 0; 
        while(i < hashTable.length) {
            hashTable[i] = -999999999; 
            i++;
        }
    }

    public static void generateNums4Table(int[] hashTable) {
        rand = new Random();
        int i = 0;
        while(i < numOfEntries) {
            rand.setSeed(i);
            int element = rand.nextInt(100000);
            cuckoo(element, hashTable, 0, 0);
            i++;
        }
    }

    public static void cuckoo(int element, int[] hashTable, int k, int cuckooSteps) {
        
        boolean checker = false;
        
        int[] hashValues = new int[numOfHashFunction];

        if(cuckooSteps == cuckooStepLimit) {
            rec = 0;
            return;
        }
            
            // calculates 3 hash values per element
            int j = 0;
            while(j < hashValues.length) {
                hashValues[j] = (element ^ hashModNums[j]) % 1000; 
                if(hashTable[hashValues[j]] == element && cuckooSteps==0) {
                    return;
                }
                j++;
            }

            // if a spot is empty in the array, it places the element there
            int m = 0;
            while(m < hashValues.length) {
                if(hashTable[hashValues[m]] == -999999999) {
                    hashTable[hashValues[m]] = element;
                    checker = true;
                    rec = -1;
                    prevSuccess=true;
                    return;
                }
                m++;
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
        int count = 0;
        System.out.println("Number of flows: " + numOfEntries);
        System.out.println("---------------");
        for(int i = 0; i < hashTable.length; i++) {
            if(hashTable[i] != -999999999) {
                System.out.println("Flow ID#" + (i+1) + " = " + hashTable[i]);
                count++;
            }
            else {
                System.out.println("Flow ID#" + (i+1) + " = " + 0);
            }
        }
        System.out.println("---------------");
        System.out.println("Number of flows: " + numOfEntries);
        System.out.println("count " + count);
    }


}
