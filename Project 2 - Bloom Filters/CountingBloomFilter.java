import java.util.HashSet;
import java.util.Random;

class CountingBloomFilter {
    static int numOfBits = 10000;
    static int numOfElements = 1000;
    static int numOfHashes = 7;
    static int finalNumOfElementsA = 0;
    static int[] hashNums = new int[numOfHashes];
    static int[] bloomArr = new int[numOfBits];
    static int[] aSetArr = new int[numOfElements];
    static HashSet<Integer> setA = new HashSet<>();
    static Random rand; 
    public static void main(String[] args) {

        genHashNum();
        genSetA();
        delete500();
        add5000();
        lookUpA();

        System.out.println("Number of elements: " + finalNumOfElementsA);

    }

    public static void genHashNum() {
        for(int i = 0; i < numOfHashes; i++) {
            rand = new Random();
            hashNums[i] = rand.nextInt(100000000 - 0) + 0;
        }
    }

    public static void genSetA() {
        for(int i = 0; i < numOfElements; i++) {
            rand = new Random();
            int element = rand.nextInt(100000000 - 0) + 0;
            if(!setA.contains(element)) {
                setA.add(element);
                aSetArr[i] = element;
            }
            for(int j = 0; j < numOfHashes; j++) {
                int hashValue = (element ^ hashNums[j]) % numOfBits;
                if(bloomArr[hashValue] == 0) {
                    bloomArr[hashValue] = 1;
                }
                else if (bloomArr[hashValue] > 0) {
                    bloomArr[hashValue] = bloomArr[hashValue] + 1;
                }
            }
        }
    }

    public static void delete500() {
        for(int k = 0; k < 500; k++) {
            for(int j = 0; j < numOfHashes; j++) {
                int hashValue = (aSetArr[k] ^ hashNums[j]) % numOfBits;
                if(bloomArr[hashValue] > 0) {
                    bloomArr[hashValue] = bloomArr[hashValue] - 1;
                }
            }
        }
        
    }

    public static void add5000() {
        for(int k = 0; k < 500; k++) {
            rand = new Random();
            int element = rand.nextInt(100000000 - 0) + 0;
            for(int j = 0; j < numOfHashes; j++) {
                int hashValue = (element ^ hashNums[j]) % numOfBits;
                if(bloomArr[hashValue] == 0) {
                    bloomArr[hashValue] = 1;
                }
                else if (bloomArr[hashValue] > 0) {
                    bloomArr[hashValue] = bloomArr[hashValue] + 1;
                }
            }
        }
    }

    public static void lookUpA() {
        
        for(int i = 0; i < aSetArr.length; i++) {
            int count = 0;
            for(int j = 0; j < numOfHashes; j++) {
                int hashValue = (aSetArr[i] ^ hashNums[j]) % numOfBits; 
                if(bloomArr[hashValue] > 0) {
                    count++;
                }
            }
            if(count == 7) {
                finalNumOfElementsA++;
            }
        }
    }

}