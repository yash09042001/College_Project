package main;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import de.flexiprovider.core.FlexiCoreProvider;
import de.flexiprovider.core.rsa.RSAKeyFactory;
import de.flexiprovider.core.rsa.RSAPrivateCrtKey;
import de.flexiprovider.core.rsa.RSAPrivateKeySpec;
import de.flexiprovider.core.rsa.RSAPublicKeySpec;
import de.flexiprovider.core.rsa.interfaces.RSAPublicKey;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncDec {

    /**
     * 117 bytes upper limit for encryption
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Security.addProvider(new FlexiCoreProvider());
        HashMap<String, BigInteger> generateKeys = generateKeys();  
        BigInteger n = generateKeys.get("RSA_N");
        BigInteger d = generateKeys.get("RSA_D");
        BigInteger e = generateKeys.get("RSA_E");
//        PublicKey pubKey =
//                new RSAKeyFactory().generatePublic(new RSAPublicKeySpec(n, e));
//        ObjectInputStream ois = new ObjectInputStream(
//                new FileInputStream(generateKeys.get("PrivateKey")));
//        PrivateKey privKey = (PrivateKey) ois.readObject();

//        ois = new ObjectInputStream(
//                new FileInputStream(generateKeys.get("PublicKey")));
//        PublicKey pubKey = (PublicKey) ois.readObject();
        // Encrypt
        String extension = "txt";
        String cleartextFile = "hello." + extension;
//	String ciphertextFile = "ciphertextRSA";
//        byte[] cipherBytes = EncDec.encryptBlocks(cleartextFile, pubKey);

        // Decrypt
//        String sql = "select backupfile from backupdata where file_id=1";
//        BigInteger n = new BigInteger("93751800306650013747526141356987112891175125078890233075190471222291168589275842682740967727492380230391524368563934844421429257508990480700065634025059180552232595763381814185302431457206840692222100885880782328051227111538614378471212253679829299627232714551178556221333327568786442518500334614415273619113");
//        BigInteger d = new BigInteger("103533772629487268530990323129376770031998385243832322942939723860397612534086627380550927659561346754725239405890548452251195707294072106051734795805886001260223188111032851971588703096016245137492451416948560986932157403337570532719711529904277871978725826802083370678122486318787622332026627386575200121871"); 
        byte[] cipherBytes = null;
        String filepath = "F:/DS/code 2015/Seed Block Algorithm for Backup/RSA-seed/Seedblock_web/hello.txt";
        System.out.println("n "+n);
        System.out.println("e "+e);
        System.out.println("filepath "+filepath);
        
        PublicKey pubKey =
                        new RSAKeyFactory().generatePublic(new RSAPublicKeySpec(n, e));
        cipherBytes = EncDec.encryptBlocks(filepath, pubKey);
                
//        byte[] orgbytes = new byte[fis.available()];
//        fis.read(orgbytes);
//        fis.close();
//        ResultSet rs = dbcon.connectDB().executeQuery(sql);
//        if(rs.next()){
//            cipherBytes = rs.getBytes(1);
//            System.out.println("cipher "+new String(cipherBytes));
//        }
        
        String cleartextAgainFile = "cleartextAgainRSA1." + extension;
        int seed_id = Cryptography.getsid_users("sid", "123");
        cipherBytes = Cryptography.performXOR(cipherBytes, seed_id);
        cipherBytes = Cryptography.performXOR(cipherBytes, seed_id);
        PrivateKey privKey =
                new RSAKeyFactory().generatePrivate(new RSAPrivateKeySpec(n, d));
        System.out.println("privKey ===============\n"+privKey);
        EncDec.decryptBlocks(cipherBytes, privKey, cleartextAgainFile);

    }

    public static byte[] encryptBlocks(String cleartextFile, PublicKey pubKey) {
        FileInputStream fis = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA", "FlexiCore");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            fis = new FileInputStream(cleartextFile);
            ByteArrayOutputStream baos_final = new ByteArrayOutputStream();
            byte[] block = new byte[32];
            int plain_block_size = 100;
            int i, total_size = 0;
            int input_data_size = fis.available();
            byte[] input = new byte[input_data_size];
            fis.read(input);
            int no_of_blocks = (input_data_size / plain_block_size);
            System.out.println("no_of_blocks " + no_of_blocks);
            for (int j = 0; j < no_of_blocks; j++) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();//cipherLength);//ciphertextFile);
                CipherOutputStream cos = new CipherOutputStream(baos, cipher);
                byte[] plain_block = new byte[plain_block_size];
                System.arraycopy(input, j * plain_block_size, plain_block, 0, plain_block_size);
                ByteArrayInputStream bais = new ByteArrayInputStream(plain_block);
                while ((i = bais.read(block)) != -1) {
//                    System.out.println("1 block size " + i);
                    cos.write(block, 0, i);
                    total_size += i;
                }
                cos.flush();
                System.out.println("1 total_size " + total_size);
//                System.out.println("total_size " + total_size);
                cos.close();
                baos_final.write(baos.toByteArray());
            }
            if (input_data_size % plain_block_size != 0) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();//cipherLength);//ciphertextFile);
                CipherOutputStream cos = new CipherOutputStream(baos, cipher);
                int last_block_size = input_data_size % plain_block_size;
                byte[] plain_block = new byte[last_block_size];
                System.arraycopy(input, no_of_blocks * plain_block_size, plain_block, 0, last_block_size);
                ByteArrayInputStream bais = new ByteArrayInputStream(plain_block);
                while ((i = bais.read(block)) != -1) {
//                    System.out.println("2 block size " + i);
                    cos.write(block, 0, i);
                    total_size += i;
                }
                cos.flush();
                System.out.println("2 total_size " + total_size);
//                System.out.println("total_size " + total_size);
                cos.close();
                byte[] blockCipher = baos.toByteArray();
                System.out.println("blockCipher len " + blockCipher.length);
                baos_final.write(blockCipher);
            }
            byte[] cipherBytes = baos_final.toByteArray();
            System.out.println("cipher total_size " + cipherBytes.length);
            return cipherBytes;
        } catch (Exception ex) {
            Logger.getLogger(EncDec.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(EncDec.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static void decryptBlocks(byte[] cipherBytes, PrivateKey privKey, String cleartextAgainFile) {
        try {
            int cipher_block_size = 128;
            Cipher cipher = Cipher.getInstance("RSA", "FlexiCore");
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            FileOutputStream fos = new FileOutputStream(cleartextAgainFile);

            int no_of_cipher_blocks = (cipherBytes.length / cipher_block_size);
            System.out.println("no_of_cipher_blocks " + no_of_cipher_blocks);
            int total_size = 0;
            byte[] block = new byte[32];
            for (int j = 0; j < no_of_cipher_blocks; j++) {
                byte[] cipher_block = new byte[cipher_block_size];
                System.arraycopy(cipherBytes, j * cipher_block_size, cipher_block, 0, cipher_block_size);

                ByteArrayInputStream bais = new ByteArrayInputStream(cipher_block); //ciphertextFile);
                CipherInputStream cis = new CipherInputStream(bais, cipher);


                System.out.println("DECRYPT_MODE");
                int i;

                while ((i = cis.read(block)) != -1) {
//                    System.out.println("block size " + i);
                    fos.write(block, 0, i);
                    total_size += i;
                }
                System.out.println("total_size " + total_size);
                fos.flush();
            }
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(EncDec.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static HashMap<String, BigInteger> generateKeys() {
        try {
//            Security.addProvider(new FlexiCoreProvider());
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "FlexiCore");
            kpg.initialize(1024);
            KeyPair keyPair = kpg.generateKeyPair();
            PrivateKey privKey = keyPair.getPrivate();
            PublicKey pubKey = keyPair.getPublic();
//            System.out.println("privkey "+privKey);
            RSAPrivateCrtKey rsap = (RSAPrivateCrtKey) privKey;
            BigInteger d = rsap.getD().bigInt;
            BigInteger n = rsap.getN().bigInt;
            RSAPublicKey rsapub = (RSAPublicKey) pubKey;
            BigInteger e = rsapub.getE().bigInt;

            HashMap<String, BigInteger> hm = new HashMap<String, BigInteger>();
            hm.put("RSA_N", n);//"PrivateKey.key");
            hm.put("RSA_D", d);//"PublicKey.key");
            hm.put("RSA_E", e);
            return hm;
        } catch (Exception ex) {
            Logger.getLogger(EncDec.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
