import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

class CountingMinSampling {
    
    static List<String> value = new ArrayList<String>();
    static List<Long> IP = new ArrayList<Long>();
    static int numOfHashes = 3;
    static String numOfFlows = "";
    static int[] hashNums = new int[numOfHashes];
    static int w = 3000;
    static long[] c1 = new long[w];
    static long[] c2 = new long[w];
    static long[] c3 = new long[w];
    static ArrayList<String> alist = new ArrayList<String>();
    static TreeMap<Double, String> diffFlowID = new TreeMap<Double, String>(Collections.reverseOrder());
    static TreeMap<Double, Long> diffTrueSize = new TreeMap<Double, Long>(Collections.reverseOrder());
    static Random rand;
    static double error = 0.0;
    static double diff = 0.0;
    static double p = 0.3;
    static int maxRand = Integer.MAX_VALUE;
    
    public static void main(String[] args) throws IOException {
        readFromFile();
        genHashNum();
        insert();
        querying();
      
        System.out.println("P:" + p + " Average Error: " + diff / 10000);

        Set set = diffTrueSize.entrySet(); 
        Set set2 = diffFlowID.entrySet(); 
        Iterator k = set2.iterator();
        Iterator j = set.iterator(); 
        int counter = 0;
        // Traverse map and print elements 
        while (j.hasNext() && counter < 100 && k.hasNext()) { 
            Map.Entry me = (Map.Entry)j.next(); 
            Map.Entry me2 = (Map.Entry)k.next(); 
        //    System.out.print("Flow ID: " + me2.getValue() + "      "); 
        //    System.out.print("Estimated Size: " + me.getKey() + "      "); 
        //    System.out.println("True Size: " + me.getValue()); 
            
            counter++;
        } 
            
    }

    public static void readFromFile() throws FileNotFoundException {
        File file = new File("project3input.txt");
        Scanner input = new Scanner(file); 
        numOfFlows = input.next();
        
        int count = 1;
        while (input.hasNext()) {
            if(count % 2 != 0) {
                String temp = input.next();
                alist.add(temp);
                String[] splitIP = temp.split("\\.");
               
                long x = (Integer.parseInt(splitIP[0])) * 255;
                long y = Integer.parseInt(splitIP[1]);
                long a = Integer.parseInt(splitIP[2]) ;
                long b = Integer.parseInt(splitIP[3]);
                long z = ((x + y) * 255 + a) * 255 + b;

                IP.add(z);
                
            }
            else {
                value.add(input.next());
            }
        
        count++;
        }

     
    }

    public static void genHashNum() {
        for(int i = 0; i < numOfHashes; i++) {
            rand = new Random();
            hashNums[i] = rand.nextInt(100000000 - 0) + 0;
        }
    }

    public static void insert() {
        for(int i = 0; i < IP.size(); i++) {
            int x = Integer.parseInt(value.get(i));
            
            for(int j = 0; j < x; j++) {
                if(p * maxRand > rand.nextInt(Integer.MAX_VALUE)) {
                    long hashValue1 = (IP.get(i) ^ hashNums[0]) % w;
                    long hashValue2 = (IP.get(i) ^ hashNums[1]) % w;
                    long hashValue3 = (IP.get(i) ^ hashNums[2]) % w;
                    c1[(int) hashValue1] = c1[(int) hashValue1] + 1;
                    c2[(int) hashValue2] = c2[(int) hashValue2] + 1;
                    c3[(int) hashValue3] = c3[(int) hashValue3] + 1;
                }
            }
            
            
        }
            
    }

    public static void querying() {
        for(int i = 0; i < IP.size(); i++) {
            long hashValue1 = (IP.get(i) ^ hashNums[0]) % w;
            long hashValue2 = (IP.get(i) ^ hashNums[1]) % w;
            long hashValue3 = (IP.get(i) ^ hashNums[2]) % w;
            double minX = Math.min(Math.min(c1[(int) hashValue1], c2[(int) hashValue2]), c3[(int) hashValue3]) / p;
            diff = diff + (minX - Integer.parseInt(value.get(i)));
            diffFlowID.put(minX, alist.get(i));
            diffTrueSize.put(minX, Long.parseLong(value.get(i)));
            
        }
    }

}
