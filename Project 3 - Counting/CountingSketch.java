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

class CountingSketch {
    
    static List<String> value = new ArrayList<String>();
    static List<Long> IP = new ArrayList<Long>();
    static int numOfHashes = 3;
    static String numOfFlows = "";
    static int w = 3000;
    static int[] hashNums = new int[numOfHashes];
    static long[] c1 = new long[w];
    static long[] c2 = new long[w];
    static long[] c3 = new long[w];
    static long[] median = new long[3];
   // static long[] largestDiff = new long[IP.size()];
   static List<Long> largestDiff = new ArrayList<Long>();
    static Random rand;
    static long error = 0;
    static long diff = 0;
    
    public static void main(String[] args) throws IOException {
        readFromFile();
     //   System.out.println(IP.size() + " IP size");
     //   System.out.println(value.size() + " value size");
     //   int flowNums = Integer.parseInt(numOfFlows);
     //   genHashNum();
        insert();
    //    querying();
    //    System.out.println(largestDiff.size() + " " + IP.size());
        System.out.println(" error: " + diff / 10000);
        /*
        for(int i = 0; i < 100; i++) {
            System.out.println("flow id# " + i + " " + IP.get(i) + " true size: " + value.get(i) 
                                + " estimated size: " + largestDiff.get(i));
        }
        */
            
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
                long a = Integer.parseInt(splitIP[2]);
                long b = Integer.parseInt(splitIP[3]);
                long z = ((x + y) * 255 + a) * 255 + b ;
                z = z % 10;
                if(z >= 5) {
                    z = z * 1;
                }
                else {
                    z = z * -1;
                }

                IP.add(z);
                
            }
            else {
                value.add(input.next());
            }
        
        count++;
        }

     //   System.out.println(IP.toString());
    }

    /*
    public static void genHashNum() {
        
        for(int i = 0; i < numOfHashes; i++) {
            rand = new Random();
            hashNums[i] = rand.nextInt(100000000 + 100000000) - 100000000; // (upperbound - lowerbound) + upperbound
        }
    }
    */

    public static void insert() {
        for(int i = 0; i < IP.size(); i++) {
            
            for(int j = 0; j < numOfHashes; j++) {
                rand = new Random();
                hashNums[j] = rand.nextInt(100000000 + 100000000) - 100000000; // (upperbound - lowerbound) + upperbound
            }
            long hashValue1 = (Math.abs(IP.get(i)) ^ Math.abs(hashNums[0])) % w;
            long hashValue1New = (IP.get(i) ^ hashNums[0]) % w;
            if(hashValue1New < 0) {
                c1[(int) hashValue1] = c1[(int) hashValue1] + Integer.parseInt(value.get(i));
            }
            else {
                c1[(int) hashValue1] = c1[(int) hashValue1] - Integer.parseInt(value.get(i));
            }

            long hashValue2 = (Math.abs(IP.get(i)) ^ Math.abs(hashNums[1])) % w;
            long hashValue2New = (IP.get(i) ^ hashNums[1]) % w;
            if(hashValue2New < 0) {
                c2[(int) hashValue2] = c2[(int) hashValue2] + Integer.parseInt(value.get(i));
            }
            else {
                c2[(int) hashValue2] = c2[(int) hashValue2] - Integer.parseInt(value.get(i));
            }
            
            long hashValue3 = (Math.abs(IP.get(i)) ^ Math.abs(hashNums[2])) % w;
            long hashValue3New = (IP.get(i) ^ hashNums[2]) % w;
            if(hashValue3New < 0) {
                c3[(int) hashValue3] = c3[(int) hashValue3] + Integer.parseInt(value.get(i));
            }
            else {
                c3[(int) hashValue3] = c3[(int) hashValue3] - Integer.parseInt(value.get(i));
            }

        //    int i1 =(int) ((IP.get(i) ^ hashNums[0]) % w);
        //    int i2 = (int) ((IP.get(i) ^ hashNums[1]) % w);
        //    int i3 = (int) ((IP.get(i) ^ hashNums[2]) % w);

            median[0]= c1[(int) hashValue1New];
            median[1]= c2[(int) hashValue2New];
            median[2]= c3[(int) hashValue3New];
            Arrays.sort(median);
            long med = median[1];
            diff = diff + Math.abs(med - Integer.parseInt(value.get(i)));
            
        }
            
    }

    /*
    public static void querying() {
        for(int i = 0; i < IP.size(); i++) {
            int i1 =(int) ((IP.get(i) ^ hashNums[0]) % w);
            int i2 = (int) ((IP.get(i) ^ hashNums[1]) % w);
            int i3 = (int) ((IP.get(i) ^ hashNums[2]) % w);
            median[0]= c1[i1];
            median[1]= c2[i2];
            median[2]= c3[i3];
            Arrays.sort(median);
            long med = median[1];
            diff = diff + Math.abs(med - Integer.parseInt(value.get(i)));
        //    largestDiff.add(minX);
            
        }
    }
    */

}
