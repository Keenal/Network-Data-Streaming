import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class VirtualBitmap {

    static String numOfFlows = "";
    static List<String> numOfElements = new ArrayList<String>();
    static List<Long> IPInteger = new ArrayList<Long>();
    static int[] rArr8507 = new int[8507];
    static int[] rArr500 = new int[500];
    static Random rand; 
    static int L = 500;
    static int[] VBfCountArr = new int[8507];
    static int[] BArr = new int[500000];

    public static void main(String[] args) throws FileNotFoundException {
        readFromFile();
        genRandomR8507();
        genRandomR500();
        record();
    }

    public static void readFromFile() throws FileNotFoundException {
        File file = new File("project4input.txt");
        Scanner input = new Scanner(file); 
        numOfFlows = input.next();
        
        int count = 1;
        while (input.hasNext()) {
            if(count % 2 != 0) {
                String temp = input.next();
                String[] splitIP = temp.split("\\.");
               
                long x = (Integer.parseInt(splitIP[0])) * 255;
                long y = Integer.parseInt(splitIP[1]);
                long a = Integer.parseInt(splitIP[2]) ;
                long b = Integer.parseInt(splitIP[3]);
                long z = ((x + y) * 255 + a) * 255 + b;

                IPInteger.add(z);
                
            }
            else {
                numOfElements.add(input.next());
            }
        
        count++;

        }
    }

    public static void genRandomR8507() {
        for(int i = 0; i < IPInteger.size(); i++) {
            rand = new Random();
            rArr8507[i] = rand.nextInt(100000000 - 0) + 0;

        }
    }

    public static void genRandomR500() {
        for(int i = 0; i < L; i++) {
            rand = new Random();
            rArr500[i] = rand.nextInt(100000000 - 0) + 0;

        }
    }

    public static void record() {
        for(int i = 0; i < IPInteger.size(); i++) {
            int Flen = Integer.parseInt(numOfElements.get(i));
            int[] VBf = new int[L];
            for(int j = 0; j < Flen; j++) {
                rand = new Random();
                int element = rand.nextInt(100000000 - 0) + 0;
                int f = (element ^ rArr8507[i]) % L;
                VBf[f] = 1;
            }
            int VBfCount = 0;
            for(int j = 0; j < VBf.length; j++) {
                if(VBf[j] == 0) {
                    VBfCount++;
                }
            }
            for(int j = 0; j < L; j++) {
                int B = (int) (IPInteger.get(i) ^ rArr500[j]) % BArr.length;
                BArr[B] = VBf[j];
            }

            VBfCountArr[i] = VBfCount;
        }
    }

    public static void countB() {
        
    }
    
}
