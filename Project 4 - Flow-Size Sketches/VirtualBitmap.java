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
    static int BCount = 0;
    static double[] estimateArr = new double[8507];

    public static void main(String[] args) throws FileNotFoundException {
        readFromFile();
        genRandomR8507();
        genRandomR500();
        record();
        countB();
        calculate();

        System.out.println("BCount: " + BCount);
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
            rArr8507[i] = rand.nextInt() & Integer.MAX_VALUE;

        }
    }

    public static void genRandomR500() {
        for(int i = 0; i < L; i++) {
            rand = new Random();
            rArr500[i] = rand.nextInt() & Integer.MAX_VALUE;

        }
    }

    public static void record() {
        for(int i = 0; i < IPInteger.size(); i++) {
            int Flen = Integer.parseInt(numOfElements.get(i));
            int[] VBf = new int[L];
            for(int j = 0; j < Flen; j++) {
                rand = new Random();
                int element = rand.nextInt() & Integer.MAX_VALUE;
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
                long B =  (IPInteger.get(i) ^ rArr500[j]) % BArr.length;
                int Bb = (int) B;
                BArr[Bb] = VBf[j];
            }

            VBfCountArr[i] = VBfCount;
        }
    }

    public static void countB() {
        for(int i = 0; i < BArr.length; i++) {
            if(BArr[i] == 0) {
                BCount++;
            }
        }
    }

    public static void calculate() {
        float Vb = (float) BCount / 500000; 
        for(int i = 0; i < IPInteger.size(); i++) {
            double Vf = (float) VBfCountArr[i] / L; 
            double estimate = L * (Math.log(Vb) - Math.log(Vf));
            estimateArr[i] = estimate;
        }
    }
    
}
