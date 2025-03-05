
import java.math.BigInteger;
import java.security.SecureRandom;
import main.RsaSample;


public class Decrypt {
    
   //RsaSample obj = new RsaSample(1024);
    private SecureRandom random = new SecureRandom();
    public String nextSessionId() 
    {
     return new BigInteger(130, random).toString(32);
    }
     public synchronized BigInteger decrypt(BigInteger message,BigInteger d,BigInteger n) {
     System.out.println("private key=="+message.modPow(d, n));
     return message.modPow(d, n);
  }
    
}
