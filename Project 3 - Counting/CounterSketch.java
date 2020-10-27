import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

class CounterSketch {
    
    static List<String> value = new ArrayList<String>();
    static List<Long> IP = new ArrayList<Long>();
    static int numOfHashes = 3;
    static String numOfFlows = "";
    static int w = 3000;
    static long[] hashNums = new long[numOfHashes];
    static long[] c1 = new long[w];
    static long[] c2 = new long[w];
    static long[] c3 = new long[w];
    static long[] median = new long[3];
   static List<Long> largestDiff = new ArrayList<Long>();
   static ArrayList<String> alist = new ArrayList<String>();
   static TreeMap<Long, String> diffFlowID = new TreeMap<Long, String>(Collections.reverseOrder());
   static TreeMap<Long, Long> diffTrueSize = new TreeMap<Long, Long>(Collections.reverseOrder());
    static Random rand;
    static long error = 0;
    static long diff = 0;
    
    public static void main(String[] args) throws IOException {
        readFromFile();
        genHashNum();
        insert();
        querying();
        System.out.println("Average Error: " + diff / 10000);

        Set set = diffTrueSize.entrySet(); 
        Set set2 = diffFlowID.entrySet(); 
        Iterator k = set2.iterator();
        Iterator j = set.iterator(); 
        int counter = 0;
        // Traverse map and print elements 
        while (j.hasNext() && counter < 100 && k.hasNext()) { 
            Map.Entry me = (Map.Entry)j.next(); 
            Map.Entry me2 = (Map.Entry)k.next(); 
            System.out.print("Flow ID: " + me2.getValue() + "      "); 
            System.out.print("Estimated Size: " + me.getKey() + "      "); 
            System.out.println("True Size: " + me.getValue()); 
            
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
                long a = Integer.parseInt(splitIP[2]);
                long b = Integer.parseInt(splitIP[3]);
                long z = ((x + y) * 255 + a) * 255 + b + 10000000;

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
            hashNums[i] = Math.abs(rand.nextInt(1000000000 - 0) + 1000000000) ; // (upperbound - lowerbound) + upperbound
        }
    }
    

    public static void insert() {
        for(int i = 0; i < IP.size(); i++) {

                long hash1 = (IP.get(i) ^ hashNums[0]);
                String binary = complement(Long.toBinaryString(hash1));
                if(binary.charAt(1) == '1') {
                    long hashMod1 = (IP.get(i) ^ hashNums[0]) % w;
                    c1[(int) hashMod1] = c1[(int) hashMod1] + Integer.parseInt(value.get(i));
                }
                else {
                    long hashMod1 = (IP.get(i) ^ hashNums[0]) % w;
                    c1[(int) hashMod1] = c1[(int) hashMod1] - Integer.parseInt(value.get(i));
                }

                long hash2 = (IP.get(i) ^ hashNums[1]);
                binary = complement(Long.toBinaryString(hash2));
                if(binary.charAt(1) == '1') {
                    long hashMod2 = (IP.get(i) ^ hashNums[1]) % w;
                    c2[(int) hashMod2] = c2[(int) hashMod2] + Integer.parseInt(value.get(i));
                }
                else {
                    long hashMod2 = (IP.get(i) ^ hashNums[1]) % w;
                    c2[(int) hashMod2] = c2[(int) hashMod2] - Integer.parseInt(value.get(i));
                }

                long hash3 = (IP.get(i) ^ hashNums[2]);
                binary = complement(Long.toBinaryString(hash3));
                if(binary.charAt(1) == '1') {
                    long hashMod3 = (IP.get(i) ^ hashNums[2]) % w;
                    c3[(int) hashMod3] = c3[(int) hashMod3] + Integer.parseInt(value.get(i));
                }
                else {
                    long hashMod3 = (IP.get(i) ^ hashNums[2]) % w;
                    c3[(int) hashMod3] = c3[(int) hashMod3] - Integer.parseInt(value.get(i));
                }
            
        }
            
    }

    
    public static void querying() {
        for(int i = 0; i < IP.size(); i++) {
            int i1 =(int) ((IP.get(i) ^ hashNums[0]) % w);
            int i2 = (int) ((IP.get(i) ^ hashNums[1]) % w);
            int i3 = (int) ((IP.get(i) ^ hashNums[2]) % w);
            long hash1 = (IP.get(i) ^ hashNums[0]);
            String binary = complement(Long.toBinaryString(hash1));
            if(binary.charAt(1) == '0') {
                median[0]= Math.abs(c1[i1]);
            }
            else {
                median[0]= (c1[i1]);
            }

            long hash2 = (IP.get(i) ^ hashNums[1]);
            binary = complement(Long.toBinaryString(hash2));
            if(binary.charAt(1) == '0') {
                median[1]= Math.abs(c2[i2]);
            }
            else {
                median[1]= c2[i2];
            }

            long hash3 = (IP.get(i) ^ hashNums[2]);
            binary = complement(Long.toBinaryString(hash3));
            if(binary.charAt(1) == '0') {
                median[2]= Math.abs(c3[i3]);
            }
            else {
                median[2]= (c3[i3]);
            }
            

            Arrays.sort(median);
            long med = median[1];
            diff = diff + Math.abs(med - Integer.parseInt(value.get(i)));
            diffFlowID.put(med, alist.get(i));
            diffTrueSize.put(med, Long.parseLong(value.get(i)));
            
        }
    }

    public static String complement(String bin) 
    { 
        int n = bin.length(); 
        int i; 
  
        String ones = "", twos = ""; 
        ones = twos = ""; 
  
        // for ones complement flip every bit 
        for (i = 0; i < n; i++) 
        { 
            ones += flip(bin.charAt(i)); 
        } 
  
        // for two's complement go from right to left in 
        // ones complement and if we get 1 make, we make 
        // them 0 and keep going left when we get first 
        // 0, make that 1 and go out of loop 
        twos = ones; 
        for (i = n - 1; i >= 0; i--) 
        { 
            if (ones.charAt(i) == '1') 
            { 
                twos = twos.substring(0, i) + '0' + twos.substring(i + 1); 
            }  
            else
            { 
                twos = twos.substring(0, i) + '1' + twos.substring(i + 1); 
                break; 
            } 
        } 
  
        // If No break : all are 1 as in 111 or 11111; 
        // in such case, add extra 1 at beginning 
        if (i == -1) 
        { 
            twos = '1' + twos; 
        } 
  
        //System.out.println("1's complement: " + ones); 
      //  System.out.println("2's complement: " + twos); 

      return twos;
    } 

    public static char flip(char c) 
    { 
        return (c == '0') ? '1' : '0'; 
    } 
    
    

}
