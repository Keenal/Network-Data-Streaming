
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class CodedBloomFilter {
    static int numOfSets = 7;
    static int numOfElements = 1000;
    static int numOfFilters = 30000;
    static int numOfHashes = 7;
    static int countA = 0;  
    static int countB = 0;
    static int countC = 0;
    static int finalCount =  0;
    static int[] hashNums = new int[numOfHashes];
    static List<Integer> arrElements = new ArrayList<>();
    static int[] bloom1 = new int[numOfFilters];
    static int[] bloom2 = new int[numOfFilters];
    static int[] bloom3 = new int[numOfFilters];
    static int[][] codes= {{0,0,1},{0,1,0},{0,1,1},{1,0,0},{1,0,1},{1,1,0},{1,1,1}};

    static Random rand;
    public static void main(String[] args) {

        genHashNum();
        addElementsInSets();
        lookUp();

        System.out.println("Correct Number of Elements: " + finalCount);
    }

    public static void genHashNum() {
        for(int i = 0; i < numOfHashes; i++) {
            rand = new Random();
            hashNums[i] = rand.nextInt(100000000 - 0) + 0;
        }
    }

    public static void addElementsInSets() {
        int j = 1;
        while(j != 8) {
            HashSet<Integer> hs = new HashSet<>();
            int[] setArr = new int[numOfElements];
            for(int i = 0; i < numOfElements; i++) {
                rand = new Random();
                int element = rand.nextInt(100000000 - 0) + 0;
                if(!hs.contains(element)) {
                    hs.add(element);
                    arrElements.add(element);
                    setArr[i] = element;
                }
                for(int k = 0; k < numOfHashes; k++) {
                    int hashValue = (element ^ hashNums[k]) % numOfFilters;
                    switch(j) {
                        case 1:
                            if(bloom3[hashValue] == 0) {
                                bloom3[hashValue] = 1;
                            }
                            break;
                        
                        case 2:
                            if(bloom2[hashValue] == 0) {
                                bloom2[hashValue] = 1;
                            }
                            break;
                        
                        case 3: 
                            if(bloom2[hashValue] == 0) {
                                bloom2[hashValue] = 1;
                            }
                            if(bloom3[hashValue] == 0) {
                                bloom3[hashValue] = 1;
                            }
                            break;
                        
                        case 4: 
                            if(bloom1[hashValue] == 0) {
                                bloom1[hashValue] = 1;
                            }
                            break;
                            
                        case 5:
                            if(bloom1[hashValue] == 0) {
                                bloom1[hashValue] = 1;
                            }
                            if(bloom3[hashValue] == 0) {
                                bloom3[hashValue] = 1;
                            }
                            break;

                        case 6: 
                            if(bloom1[hashValue] == 0) {
                                bloom1[hashValue] = 1;
                            }
                            if(bloom2[hashValue] == 0) {
                                bloom2[hashValue] = 1;
                            }
                            break;

                        case 7:
                            if(bloom1[hashValue] == 0) {
                                bloom1[hashValue] = 1;
                            }
                            if(bloom2[hashValue] == 0) {
                                bloom2[hashValue] = 1;
                            }
                            if(bloom3[hashValue] == 0) {
                                bloom3[hashValue] = 1;
                            }
                            break;
                        default:
                            System.out.println("Shouldn't go here");
                    }

                }
            }
            j++;
        }
        
        
    }
    static int setCounter=0;
    public static void lookUp() {
        
        for(int i = 0; i < 7000; i++) {
            List<Integer> code = new ArrayList<>(); 
            int check=0;
            for(int k = 0; k < numOfHashes; k++) {
                int hashValue = (arrElements.get(i) ^ hashNums[k]) % numOfFilters;
                if(bloom1[hashValue] == 1) {
                    countA++;
                }
                if(bloom2[hashValue] == 1) {
                    countB++;
                }
                if(bloom3[hashValue] == 1) {
                    countC++;
                }
            }

            if(countA == 7 ){
                code.add(1);
            }
            else{
                code.add(0);
            }
            if(countB == 7 ){
                code.add(1);
            }
            else{
                code.add(0);
            }if(countC == 7 ){
                code.add(1);
            }
            else{
                code.add(0);
            }
            for(int j=0; j<code.size();j++){
                if(code.get(j)!=codes[setCounter][j]){
                    check=1;
                }
            }
            if(check==0){
                finalCount++;
            }

            countA = 0;
            countB = 0;
            countC = 0;

            if(i%1000==0 && i!=0){
                setCounter++;
            }
        }
    }


}
