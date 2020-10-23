import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class CountingMin {
    
    static List<String> value = new ArrayList<String>();
    static List<Long> IP = new ArrayList<Long>();
    static int numOfHashes = 3;
    static String numOfFlows = "";
    static int[] hashNums = new int[numOfHashes];
    static int w = 3000;
    static long[] c1 = new long[w];
    static long[] c2 = new long[w];
    static long[] c3 = new long[w];
    static Random rand;
    static long error = 0;
    static long diff = 0;
    
    public static void main(String[] args) throws IOException {
        readFromFile();
     //   System.out.println(IP.size() + " IP size");
     //   System.out.println(value.size() + " value size");
     //   int flowNums = Integer.parseInt(numOfFlows);
        genHashNum();
        insert();
        querying();
        System.out.println(" error: " + diff / 10000);
            
    }

    public static void readFromFile() throws FileNotFoundException {
        File file = new File("project3input.txt");
        Scanner input = new Scanner(file); 
        numOfFlows = input.next();
        
        int count = 1;
        while (input.hasNext()) {
            if(count % 2 != 0) {
             //   IP.add(input.next());
                String[] splitIP = input.next().split("\\.");
              //  System.out.println(Arrays.toString(splitIP));
             
               
                long x = (Integer.parseInt(splitIP[0])) * 255;
                long y = Integer.parseInt(splitIP[1]);
                long a = Integer.parseInt(splitIP[2]) ;
                long b = Integer.parseInt(splitIP[3]);
                long z = ((x + y) * 255 + a) * 255 + b ;
                IP.add(z);
                
            }
            else {
                value.add(input.next());
            }
        
        count++;
        }

     //   System.out.println(IP.toString());
    }

    public static void genHashNum() {
        for(int i = 0; i < numOfHashes; i++) {
            rand = new Random();
            hashNums[i] = rand.nextInt(100000000 - 0) + 0;
        }
    }

    public static void insert() {
        for(int i = 0; i < IP.size(); i++) {
            long hashValue1 = (IP.get(i) ^ hashNums[0]) % w;
            c1[(int) hashValue1] = c1[(int) hashValue1] + Integer.parseInt(value.get(i));
            long hashValue2 = (IP.get(i) ^ hashNums[1]) % w;
            c2[(int) hashValue2] = c2[(int) hashValue2] + Integer.parseInt(value.get(i));
            long hashValue3 = (IP.get(i) ^ hashNums[2]) % w;
            c3[(int) hashValue3] = c3[(int) hashValue3] + Integer.parseInt(value.get(i));
        }
            
    }

    public static void querying() {
        for(int i = 0; i < IP.size(); i++) {
            long hashValue1 = (IP.get(i) ^ hashNums[0]) % w;
            long hashValue2 = (IP.get(i) ^ hashNums[1]) % w;
            long hashValue3 = (IP.get(i) ^ hashNums[2]) % w;
            long minX = Math.min(Math.min(c1[(int) hashValue1], c2[(int) hashValue2]), c3[(int) hashValue3]);
            diff = diff + (minX - Integer.parseInt(value.get(i)));
            
        }
    }

}
