import java.math.BigInteger;
import java.util.Random;

public class ActiveCounter {

    static int cnMax = 16;
    static int ceMax = 4;
    static long cn = 0;
    static long ce = 0;
    static long  cnA = 0;
    static long ceA = 0;
    static Random rand;

    public static void main(String[] args) {
        
        decimalToBinary();

        System.out.println("Final Value of Active Counter: " + Math.pow(2, ce) * cn);
    }

    private static void decimalToBinary() {
        for(int i = 0; i < 1000000; i++) {
            rand = new Random();

          // long x = decimalToBinary2(cn);
           

            

            double prob = 1 / Math.pow(2,ce);
            float r = rand.nextFloat();
            if(r < prob) {
                cn = addBinary(cn);
                cnA=decimalToBinary2(cn);
            }
            

            if(String.valueOf(cnA).length() > cnMax) {
                long y = cn;
                y = y >> 1;
                cn = y;
                cnA = decimalToBinary2(y);
                ce = addBinary(ce);
                ceA = decimalToBinary2(ce);
            }
           // cnA =  x;
           
            
        }
        
    }

    static long decimalToBinary2(long N)  
{  
  
    // To store the binary number  
    long B_Number = 0;  
    long cnt = 0;  
    while (N != 0) 
    {  
        long rem = N % 2;  
        double c = Math.pow(10, cnt);  
        B_Number += rem * c;  
        N /= 2;  
  
        // Count used to store exponent value  
        cnt++;  
    }  
  
    return B_Number;  
} 

    public static long addBinary(long x) {
        long m = 1; 
        while( (int)(x & m) >= 1) { // shifting
            x = x ^ m; 
            m <<= 1; 
        } 
        x = x ^ m; // flipping

        return x;
        
    }
    
}
