public class DLeftHashing {
    public static final int numOfEntries = 1000; 
    public static final int numOfHashFunction = 3;
    public static final int numOfSegmentInterval = numOfEntries / 4; 

    public static int[] hashTable = new int[numOfEntries];
    public static int[] hashModNums = {737, 222, 845};

    public static void main(String[] args) {

        initHashTable(hashTable);

        addInTable(hashTable);

        printHashTable(hashTable);

    }

    public static void initHashTable(int[] hashTable) {
        for(int i = 0; i < hashTable.length; i++) {
            hashTable[i] = -999999999; 
        }
    }

    public static void addInTable(int[] hashTable) {
        for(int i = 0; i < numOfEntries; i++) {
            int element = (int) (Math.random()*1000);
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
