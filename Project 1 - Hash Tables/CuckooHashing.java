public class CuckooHashing {
    public static void main(String[] args) {

        int numOfEntries = 10;
        int numOfFlows = 10; 
        int numOfHashes = 3;
        int kHashFunc = 3;
        int hashF1 = 0, hashF2 = 0, hashF3 = 0;

        int[] inputElements = {20, 50, 53, 75, 100, 67, 105, 3, 36, 39}; //randomly generate this
        int[] hashTable = new int[numOfEntries];
        int[] arrayS = {8, 7, 4}; //randomly generate this
        
        initHashTable(hashTable);

        for(int i = 0; i < inputElements.length; i++) {
            hashF1 = inputElements[i] % 11;
            hashF2 = (inputElements[i] / 11) % 11;
            hashF3 = inputElements[i] % 10;

            
            if(hashTable[hashF1] == Integer.MIN_VALUE) {
                hashTable[hashF1] = inputElements[i];
            }


            /*
            hashF1 = inputElements[i] ^ arrayS[0];
            hashF2 = inputElements[i] ^ arrayS[1];
            hashF3 = inputElements[i] ^ arrayS[2];
            */

        //    System.out.println("i: " + inputElements[i] + " h1: " + 
        //                        hashF1 + " h2: " + hashF2 + " h3: " + hashF3);

        }

        

    }

    public static void initHashTable(int[] hashTable) {
        for(int i = 0; i < hashTable.length; i++) {
            hashTable[i] = Integer.MIN_VALUE;
        }
    }
}
