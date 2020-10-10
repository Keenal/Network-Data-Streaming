import java.util.Random;

public class DLeftHashing {
    public static final int numOfEntries = 1000; 
    public static final int numOfHashFunction = 4;
    public static final int numOfSegmentInterval = numOfEntries / 4; 

    public static int[] hashTable = new int[numOfEntries];
   // public static int[] hashModNums = {6163, 81, 51, 53};
   public static int[] hashModNums = new int[numOfHashFunction];

    private static Random rand;
    

    public static void main(String[] args) {

        initHashTable(hashTable);

        randomGen(hashTable);

        addInTable(hashTable);

        printHashTable(hashTable);

    }

    public static void randomGen(int[] hashTable) {
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

    public static void addInTable(int[] hashTable) {
        rand = new Random();
        for(int i = 0; i < numOfEntries; i++) {

            rand.setSeed(i);

            int element = rand.nextInt(100000);

            int[] hashValues = new int[numOfHashFunction];

            for(int j = 0; j < hashValues.length; j++) {
                hashValues[j] = ((element ^ hashModNums[j]) % numOfSegmentInterval) + (numOfSegmentInterval * j); 
                
                if(hashTable[hashValues[j]] == element) {
                    break;
                }
            }

            for(int j = 0; j < hashValues.length; j++) {
                
                if(hashTable[hashValues[j]] == -999999999) {
                    hashTable[hashValues[j]] = element;
                    break;
                }
            }
        }

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
