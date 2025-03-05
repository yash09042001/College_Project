package main;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.sql.Statement;
public class RsaSample
{
  private BigInteger n, d, e;

  private int bitlen = 1024;

  /** Create an instance that can encrypt using someone else's public key. */
  public RsaSample(BigInteger newn, BigInteger newe) {
    n = newn;
    e = newe;
  }

  /** Create an instance that can both encrypt and decrypt. */
  public RsaSample(int bits,int file_id) throws ClassNotFoundException, SQLException {
    Statement stmt = dbcon.connectDB();
      
    bitlen = bits;
    SecureRandom r = new SecureRandom();
    BigInteger p = new BigInteger(bitlen / 2, 100, r);
    System.out.println("p="+p);
    BigInteger q = new BigInteger(bitlen / 2, 100, r);
    System.out.println("q="+q);
    n = p.multiply(q);
    System.out.println("n="+n);
    BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
        .subtract(BigInteger.ONE));
    System.out.println("m="+m);
    e = new BigInteger("3");
   
    while (m.gcd(e).intValue() > 1) {
      e = e.add(new BigInteger("2"));
    }
     System.out.println("e="+e);
    d = e.modInverse(m);
     System.out.println("d="+d);
     Decrypt obj=new Decrypt();
     String otp=obj.nextSessionId();
      System.out.println("OTP:"+otp);
      System.out.println("file_idddddddddddddddddddddddd=="+file_id);
     String insert="INSERT INTO privatekey (d,n,otp,file_id)\n" +
                      "VALUES ('"+d+"','"+n+"','"+otp+"','"+file_id+"')";
     stmt.executeUpdate(insert);
  }
  
  public RsaSample(int bits) throws ClassNotFoundException, SQLException {
    Statement stmt = dbcon.connectDB();
      
    bitlen = bits;
    SecureRandom r = new SecureRandom();
    BigInteger p = new BigInteger(bitlen / 2, 100, r);
    System.out.println("p="+p);
    BigInteger q = new BigInteger(bitlen / 2, 100, r);
    System.out.println("q="+q);
    n = p.multiply(q);
    System.out.println("n="+n);
    BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
        .subtract(BigInteger.ONE));
    System.out.println("m="+m);
    e = new BigInteger("3");
   
    while (m.gcd(e).intValue() > 1) {
      e = e.add(new BigInteger("2"));
    }
     System.out.println("e="+e);
    d = e.modInverse(m);
     System.out.println("d="+d);
    
     }

  /** Encrypt the given plaintext message. */
  public synchronized String encrypt(String message) {
 System.out.println("hey:"+new BigInteger(message.getBytes()).modPow(e, n).toString());
      return (new BigInteger(message.getBytes())).modPow(e, n).toString();
      
  }

  /** Encrypt the given plaintext message. */
  public synchronized BigInteger encrypt(BigInteger message) {
      System.out.println("pubk="+message.modPow(e, n)); 
    return message.modPow(e, n);
     }

  
  /** Decrypt the given ciphertext message. */
  public synchronized String decrypt(String message) {
      System.out.println("hii"+new String((new BigInteger(message)).modPow(d, n).toByteArray()));
    return new String((new BigInteger(message)).modPow(d, n).toByteArray());
  }

  /** Decrypt the given ciphertext message. */
  public synchronized BigInteger decrypt(BigInteger message) {
      System.out.println("private key=="+message.modPow(d, n));
    return message.modPow(d, n);
  }

  /** Generate a new public and private key set. */
  public synchronized void generateKeys() {
    SecureRandom r = new SecureRandom();
    BigInteger p = new BigInteger(bitlen / 2, 100, r);
      System.out.println("p="+p);
    BigInteger q = new BigInteger(bitlen / 2, 100, r);
     System.out.println("q="+q);
    n = p.multiply(q);
     System.out.println("n="+n);
    BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
        .subtract(BigInteger.ONE));
     System.out.println("m="+m);
    e = new BigInteger("3");
     System.out.println("e="+e);
    while (m.gcd(e).intValue() > 1) {
      e = e.add(new BigInteger("2"));
    }
    d = e.modInverse(m);
  }

  /** Return the modulus. */
  public synchronized BigInteger getN() {
    return n;
  }

  /** Return the public key. */
  public synchronized BigInteger getE() {
    return e;
  }

  /** Trivial test program. */
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    RsaSample rsa = new RsaSample(1024);

    String text1 = "Yellow and Black Border Collies";
   // rsa.encrypt(text1);
    System.out.println("Plaintext: " + text1);
    BigInteger plaintext = new BigInteger(text1.getBytes());
    BigInteger ciphertext = rsa.encrypt(plaintext);
   // System.out.println("public key="+plaintext);
    System.out.println("Ciphertext: " + ciphertext);
    plaintext = rsa.decrypt(ciphertext);
    System.out.println("decrypt="+plaintext);
    String text2 = new String(plaintext.toByteArray());
    System.out.println("Plaintext: " + text2);
    
  }
}