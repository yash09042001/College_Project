package main;

import codec.Base64;
import codec.CorruptedCodeException;
import de.flexiprovider.api.keys.PrivateKey;
import de.flexiprovider.api.keys.SecretKeySpec;
import de.flexiprovider.core.FlexiCoreProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author eiosys
 */
public class SecretKeyDecoder {

    int id;
    String name;

    public SecretKeyDecoder(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static int getNumeric_Count(String sentence) {
        int Numericcount = 0;
        String str = sentence;
        System.out.println("str:" + str);
        Pattern pattern = Pattern.compile("\\w+([0-9]+)\\w+([0-9]+)");
        Matcher matcher = pattern.matcher(str);
        int groupCount = matcher.groupCount();
        System.out.println("groupCount:" + groupCount);
        System.out.println("");
        while (matcher.find()) {
            System.out.println("find:" + matcher.find());
            System.out.println(matcher.group());
            Numericcount++;
        }

        return Numericcount;
    }

    public static void getFloatcount(String ip) {
        String input = "abc_12.23;dcf_23.99;dfa_12.99;";
        String regex = "([a-zA-Z]+)_(\\d+(\\.\\d+)?);?";
        List<String> strings = new ArrayList<String>();
        List<Double> floats = new ArrayList<Double>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            System.out.println(" float  macth :" + matcher.find());
//            strings.add(matcher.group(1));
//            floats.add(Double.valueOf(matcher.group(2)));
        }
        System.out.println(strings); // prints [abc, dcf, dfa]
        System.out.println(floats); // prints [12.23, 23.99, 12.99]
    }

    public static void main(String args[]) {
        
         SecretKey decodekey = SecretKeyDecoder.decodekey("1N9+LP98gV+qK/Yfx4Df5A==");
       decryptfile("C:\\Users\\eiosys\\Documents\\NetBeansProjects\\Seedblock_web\\output.txt", decodekey,"txt"); 

       String algorithm = "DSA"; // or RSA, DH, etc.

    // Generate a 1024-bit Digital Signature Algorithm (DSA) key pair
   
    // The orginal and new keys are the same
   
       
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static SecretKey decodekey(String key_string) {
        SecretKey originalKey=null;
        try {
            byte[] decode = Base64.decode(key_string);
            byte[] bytes = key_string.getBytes();
            byte[] decode1 = Base64.decode(bytes);

         
             originalKey = new SecretKeySpec(decode1, 0, decode1.length, "AES");
            System.out.println("key  after :" + originalKey.toString());

            byte[] encoded = originalKey.getEncoded();
            String encode = Base64.encode(encoded);
            System.out.println("encoded String  after :" + encode);

        } catch (CorruptedCodeException ex) {
            Logger.getLogger(SecretKeyDecoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return originalKey;
    }

    
    public static String encryptfile(String filepath) {
        HashMap<SecretKey, String> encryptdata = new HashMap();
        SecretKey encryptionKey = null;
        String absolutePath = "";
        try {
            int index = filepath.lastIndexOf(".");
            String extenstion = filepath.substring(index, filepath.length());
            System.out.println("extenstion:" + extenstion);

            Security.addProvider(new FlexiCoreProvider());
            Cipher cipher = Cipher.getInstance("AES");
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecretKey secKey = keyGen.generateKey();
            System.out.println("secKey: before" + secKey.toString());


            byte[] encoded = secKey.getEncoded();


            String key_string = Base64.encode(encoded);
            System.out.println("encoded String  :" + key_string);

//            decodekey(encode);



            byte[] decode = Base64.decode(key_string);
            byte[] bytes = key_string.getBytes();
            byte[] decode1 = Base64.decode(bytes);

            SecretKey originalKey = new SecretKeySpec(decode1, 0, decode1.length, "AES");
            System.out.println("key  after :" + originalKey.toString());

            byte[] encoded1 = originalKey.getEncoded();
            String encode = Base64.encode(encoded1);
            System.out.println("encoded String  after :" + encode);






            cipher.init(Cipher.ENCRYPT_MODE, secKey);
            encryptionKey = secKey;
            String cleartextFile = filepath;
            String ciphertextFile = "encryptedfile" + extenstion + "";

            FileInputStream fis = new FileInputStream(cleartextFile);
            FileOutputStream fos = new FileOutputStream(ciphertextFile);
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);


            byte[] block = new byte[8];
            int i;
            while ((i = fis.read(block)) != -1) {
                cos.write(block, 0, i);
            }

            encryptdata.put(encryptionKey, ciphertextFile);
            cos.close();
            System.out.println("encrypton done ....");
            File f = new File(ciphertextFile);
            absolutePath = f.getAbsolutePath();
            System.out.println(" file  path :");

            SecretKeyDecoder.decryptfile(absolutePath, originalKey, "pdf");
        } catch (Exception ee) {
            System.out.println("Exception:" + ee);
            ee.printStackTrace();
        }
        return absolutePath;
    }

    public static void decryptfile(String path, SecretKey secKey, String extension) {
        //  byte[] decryptfileop = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            String ciphertextFile = path;
            System.out.println("ciphertextFile:" + ciphertextFile);
            String cleartextAgainFile = "C:\\Users\\eiosys\\Documents\\NetBeansProjects\\Seedblock_web\\opfile." + extension + "";
            cipher.init(Cipher.DECRYPT_MODE, secKey);

            FileInputStream fis = new FileInputStream(ciphertextFile);
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            FileOutputStream fos = new FileOutputStream(cleartextAgainFile);


            byte[] block = new byte[8];
            int i;
            while ((i = cis.read(block)) != -1) {
                fos.write(block, 0, i);
            }
            fos.close();
        } catch (Exception ee) {
            System.out.println("Exception:" + ee);
            ee.printStackTrace();
        }
    }
}
