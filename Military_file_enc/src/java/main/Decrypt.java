package main;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Decrypt {

    //RsaSample obj = new RsaSample(1024);
    private static SecureRandom random = new SecureRandom();

    public static String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

    public synchronized static BigInteger decrypt(BigInteger message, BigInteger d, BigInteger n) {
        System.out.println("private key==" + message.modPow(d, n));
        return message.modPow(d, n);
    }
}
