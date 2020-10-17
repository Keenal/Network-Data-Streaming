import java.util.HashSet;
import java.util.Random;

class BloomFilter {
    static int numOfBits = 10000;
    static int numOfElements = 1000;
    static int numOfHashes = 7;
    static int finalNumOfElementsA = 0;
    static int finalNumOfElementsB = 0;
    static int[] hashNums = new int[numOfHashes];
    static int[] bloomArr = new int[numOfBits];
    static int[] aSetArr = new int[numOfElements];
    static int[] bSetArr = new int[numOfElements];
    static HashSet<Integer> setA = new HashSet<>();
    static HashSet<Integer> setB = new HashSet<>();
    static Random rand; 
    public static void main(String[] args) {

        genHashNum();
        genSetA();
        lookUpA();
        genSetB();
        lookUpB();
        

        System.out.println(finalNumOfElementsA + " final A");
        System.out.println(finalNumOfElementsB + " final B");

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
            }
        }
    }

    public static void lookUpA() {
        
        for(int i = 0; i < aSetArr.length; i++) {
            int count = 0;
            for(int j = 0; j < numOfHashes; j++) {
                int hashValue = (aSetArr[i] ^ hashNums[j]) % numOfBits; 
                if(bloomArr[hashValue] == 1) {
                    count++;
                }
            }
            if(count == 7) {
                finalNumOfElementsA++;
            }
        }
    }

    
    public static void genSetB() {
        for(int i = 0; i < numOfElements; i++) {
            rand = new Random();
            int element = rand.nextInt(100000000 - 0) + 0;
            if(!setB.contains(element)) {
                setB.add(element);
                bSetArr[i] = element;
            }

        }
    }

    public static void lookUpB() {
        for(int i = 0; i < bSetArr.length; i++) {
            int count = 0;
            for(int j = 0; j < numOfHashes; j++) {
                int hashValue = (bSetArr[i] ^ hashNums[j]) % numOfBits;
                if(bloomArr[hashValue] == 1) {
                    count++;
                }
            }
            if(count == 7) {
                finalNumOfElementsB++;
            }
        }
    }
    



}