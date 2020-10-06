import java.util.Random;

public class CuckooHashing {
    public static final int numOfEntries = 1000; 
    public static final int numOfHashFunction = 3; 

    public static int[] hashTable = new int[numOfEntries];
    public static int[] hashModNums = {743, 22, 347};
    public static int[] hashValues = new int[numOfHashFunction];

    public static int counterNotPlaced = 0;
    public static int tracker = 1;
    static int k = 0;

    public static Random rand;

    public static void main(String[] args) {

        

        initHashTable(hashTable);

        addInTable(hashTable, -1, 0);

        printHashTable(hashTable);

    }

    public static void initHashTable(int[] hashTable) {
        for(int i = 0; i < hashTable.length; i++) {
            hashTable[i] = -999999999; 
        }
    }

    public static void addInTable(int[] hashTable, int last, int count) {
        rand = new Random();
        for(int i = 0; i < numOfEntries; i++) {
            rand.setSeed(i);

            int element = rand.nextInt(1000000);

            if(count == 2) {
                tracker = 1;
                counterNotPlaced++;
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
