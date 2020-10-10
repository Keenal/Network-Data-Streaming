import java.util.Random;

public class CuckooHashing {
    public static final int numOfEntries = 1000; 
    public static final int numOfHashFunction = 3; 

    public static int[] hashTable = new int[numOfEntries];
    public static int[] hashModNums = {6136, 8281, 5906};
    public static int[] hashValues = new int[numOfHashFunction];
    
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


        for(int i=0;i<100000;i++){
            initHashTable(hashTable);
            randomGen(hashModNums);
            generateNums4Table(hashTable);

             printHashTable(hashTable);
             if(count>maxCount){
                 maxCount=count;
                 b=y;
             }
             //System.out.println("count=" + count);
             y++;
        }
        System.out.println("MaxCOunt="+maxCount + "y=" + b);


    }

    public static void randomGen(int[] hashModNums){
        rand = new Random();

        for(int i=0;i<3;i++){
            rand.setSeed(i+y);
            
            hashModNums[i]=rand.nextInt(10000);
            if(y==14271){
            System.out.print(hashModNums[i]+" ");
            }
        }
        //System.out.println();
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
            cuckoo(element, hashTable, -1, 0);
        }
    }

    public static void cuckoo(int element, int[] hashTable, int idx, int cuckooSteps) {
        
        boolean checker = false;

            
            if(cuckooSteps == cuckooStepLimit) {
                rec = 0;
            }

            // calculates 3 hash values per element
            for(int j = 0; j < hashValues.length; j++) {
                hashValues[j] = (element ^ hashModNums[j]) % hashTable.length; 
                if(hashTable[hashValues[j]] == element && cuckooSteps!=0) {
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
                    cuckooSteps++;
                    break;
                }




            }

            if(checker == false) {
                int flowPlaceHolder = -1;
                prevSuccess=false;
                while(k < 3) {
                    
                    if(hashValues[k] != idx) {
                        //System.out.println("HERE");
                        flowPlaceHolder = hashTable[hashValues[k]];
                        cuckoo(flowPlaceHolder, hashTable, hashValues[k], cuckooSteps + 1);
                        if(prevSuccess==true){
                            System.out.println("HERE");

                            hashTable[hashValues[k]] = element;
                            return;
                         
                        }
                        rec = -1;
             
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
        count = 0;
        for(int i = 0; i < hashTable.length; i++) {
            if(hashTable[i] != -999999999) {
                count++;
            }
        }
        //System.out.println("count " + count);
    }


}
