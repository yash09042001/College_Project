package main;

import de.flexiprovider.core.FlexiCoreProvider;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;




public class Cryptography {
    public static String otp;
    public static String id;

//    public static void main(String[] args) throws Exception {
//        String Path = "E:\\image16 17.jpg";
//        encryptfile(Path);
//
//        SecretKey secretKey = null;
//        int fileid = 0;
//        fileid = getnewfileid();
//        System.out.println("id" + fileid);
//        System.out.println("-------------");
//
//        Random rn = new Random();
//        int rn_no = rn.nextInt(100);
//        int seedid = 36;
//        byte[] encryptedbyte = null;
//        String username = null, password = null, email = null;
//        String Path = "dataset.jpg";
//        System.out.println("1");
//        String[] ex = {"docx", "pdf"};
//        ArrayList<Integer> uploadedfid = getuploadedfid(ex, seedid);
//        
//        HashMap<SecretKey, String> encryptfile = encryptfile(Path);
//        System.out.println("2");
//
//        System.exit(0);
//
//
//        // String Path = "";
//
//        AESCrypt.insert_userdata(rn_no, username, password, email);//seed id calcaulted inside this fun// save userdetails to database
//
//        AESCrypt.getownerid(seedid);
////        AESCrypt.insert_filedetails(seedid, fileid, username, secretKey);
//
//        // File uploading to main  database 
//
//        File file = new File(Path);
//        String absolutePath = file.getAbsolutePath();
//        String fname = file.getName();
//        int index = fname.lastIndexOf(".");
//        String ftype = fname.substring(index + 1);
//        System.out.println("type:" + ftype);
//
//        File f = null;
//
//        encryptfile(absolutePath); //encrypt file using AES
//        Set<Entry<SecretKey, String>> entrySet = encryptfile.entrySet();
//        for (Entry<SecretKey, String> entry : encryptfile.entrySet()) {
//            secretKey = entry.getKey();  //mail secret key to user with whome file is shared ....
//            String getfilename = entry.getValue();
//            f = new File(getfilename);
//        }
//
//
//
//        encryptedbyte = readbytes(f);
//
//        if (encryptedbyte == null) {
//            System.out.println("encryptedbyte null:");
//        }
//
//        int sid_users = getsid_users(username, password); //2nd  seed id calcaulted inside this fun
//
//        fileid = getnewfileid();
//        insert_filedata(sid_users, fileid, fname, ftype, encryptedbyte); // file details insertion to main  table
//
//        // EXOR operation of encrypted byte with seed id 
//
//        byte[] XORop = AESCrypt.performXOR(encryptedbyte, sid_users);
//
//        boolean backup = insert_backup(fileid, XORop);// insert details to backup server
//        System.out.println("backup inserted :" + backup);
//        System.out.println("------------------------");
//    }
    public static String getsecretkey(int file_id) {

        SecretKey sec = null;
        String bytes = null;
        int newfileid = 0;
        String key = "";
        try {
            System.out.println(" get id:" + file_id);
            Statement stmt = dbcon.connectDB();
            String query = "select secretkey from backupdata where file_id=" + file_id + "";
            System.out.println("sql:" + query);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                bytes = rs.getString("secretkey");
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        System.out.println("byets:" + bytes);
        return bytes;
    }

    public static HashMap<Integer, String> getfiledetails(ArrayList<Integer> arr) {
        HashMap<Integer, String> hash = new HashMap<Integer, String>();
        try {
            Statement stmt = dbcon.connectDB();
            ResultSet rs = null;
            String query = "";
            for (int i = 0; i < arr.size(); i++) {
                int fid = arr.get(i);
                query = "select file_id,file_name from  filedata where file_id=" + fid + "";
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int fileid = rs.getInt("file_id");
                    String filename = rs.getString("file_name");
                    hash.put(fileid, filename);
                }
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }

        return hash;
    }

    public static boolean userauthentication(String username, String password) {
        boolean validuser = false;
        try {
            Statement stmt = dbcon.connectDB();
            String query = "select name,password from users";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String unm = rs.getString("name");
                String pw = rs.getString("password");
                if (unm.equals(username) && pw.equals(password)) {
                    System.out.println("valid user ");
                    validuser = true;
                } else {
                    validuser = false;
                }
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return validuser;
    }

    public static ArrayList<Integer> getuploadedfid(String[] extensions, int sid) {
        System.out.println("getuploadedfid ");
        ArrayList<Integer> op = new ArrayList<Integer>();
        ArrayList<String> file_exten = new ArrayList<String>();
        for (int i = 0; i < extensions.length; i++) {
            System.out.println("hiii");
            file_exten.add(extensions[i]);
        }
        int fileid;
        String filename = "";
        try {
            System.out.println("enter in try");
            Statement stmt = dbcon.connectDB();
//            JOptionPane.showMessageDialog(null,"sid:"+sid);
            System.out.println("sid" + sid);
            String query = "select file_id,file_name from filedetailbackup where owner_id=" + sid + "";
            System.out.println("query:" + query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fileid = rs.getInt("file_id");
                filename = rs.getString("file_name");
                System.out.println("fileid:" + fileid + "filename:" + filename);
                String extension = filename.substring(filename.indexOf(".") + 1, filename.length());
                System.out.println("extension" + extension);
                System.out.println("arraylist ex:" + file_exten);
                if (file_exten.contains(extension)) {
                    op.add(fileid);
                    System.out.println("extension:" + extension);
                }
            }
            System.out.println("hash size:" + file_exten.size());
            System.out.println("size of hm " + op.size() + ":" + op);

        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return op;

    }

    public static int getnewfileid() throws ClassNotFoundException, SQLException {
        int newfileid = 0;
        Statement stmt = dbcon.connectDB();
        String query = "select file_id from  filedata";
        ResultSet rs = stmt.executeQuery(query);
        if (rs.last()) {
            newfileid = rs.getInt("file_id");
            System.out.println("newfileid" + newfileid);
            newfileid = newfileid + 1;
        }
        return newfileid;

    }

    public static boolean userauth(String username, String password) {
        boolean validuser = false;
        try {
            Statement stmt = dbcon.connectDB();
            String query = "select name,password,id from users";
           
            System.out.println("User name : " +id+ username + password);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
//                System.out.println("DB val : "+rs.getString(1)+rs.getString(2));
                id = rs.getString("id");
                String unm = rs.getString(1);
                String pw = rs.getString(2);
                if (unm.equals(username) && pw.equals(password)) {
                    System.out.println("valid user ");
                    validuser = true;
                    break;
                } else {
                    validuser = false;
                }
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return validuser;
    }

    public static boolean cauth(String username, String password) {
        boolean validuser = false;
        try {
            Statement stmt = dbcon.connectDB();
            String query = "select name,password from cloud_admin";
            System.out.println("User name : " + username + password);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
//                System.out.println("DB val : "+rs.getString(1)+rs.getString(2));
                String unm = rs.getString(1);
                String pw = rs.getString(2);
                if (unm.equals(username) && pw.equals(password)) {
                    System.out.println("valid user ");
                    validuser = true;
                    break;
                } else {
                    validuser = false;
                }
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return validuser;
    }

    public static HashMap<SecretKey, String> encryptfile(String filepath) {
        HashMap<SecretKey, String> encryptdata = new HashMap();
        SecretKey encryptionKey = null;
        try {

            int index = filepath.lastIndexOf(".");
            String extenstion = filepath.substring(index, filepath.length());
            System.out.println("extenstion:" + extenstion);

            Security.addProvider(new FlexiCoreProvider());
            Cipher cipher = Cipher.getInstance("AES");
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecretKey secKey = keyGen.generateKey();
            System.out.println("secKey: before" + secKey.toString());


            cipher.init(Cipher.ENCRYPT_MODE, secKey);
//            encryptionKey = secKey;
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
            encryptdata.put(secKey, ciphertextFile);
            cos.close();
            System.out.println("encrypton done ....");
        } catch (Exception ee) {
            System.out.println("Exception:" + ee);
            ee.printStackTrace();
        }
        return encryptdata;

    }

    public static HashMap<PrivateKey, byte[]> encryptRSA(String filepath, PublicKey pubKey, PrivateKey privKey) {
        HashMap<PrivateKey, byte[]> encryptdata = new HashMap();
        
        try {

            int index = filepath.lastIndexOf(".");
            String extenstion = filepath.substring(index, filepath.length());
            System.out.println("extenstion:" + extenstion);

//            Security.addProvider(new FlexiCoreProvider());
            System.out.println("filepath "+filepath);
            byte[] cipherBytes = EncDec.encryptBlocks(filepath, pubKey);
//            encryptionKey = secKey;
            encryptdata.put(privKey, cipherBytes);
            
            
            System.out.println("encrypton done ....");
        } catch (Exception ee) {
            System.out.println("Exception:" + ee);
            ee.printStackTrace();
        }
        return encryptdata;

    }
    
    public static String KeyToString(PrivateKey secKey) {
        byte[] encoded = secKey.getEncoded();
        System.out.println("KeyToString 1");
        String key_string = Base64.encodeBase64String(encoded);
        System.out.println("KeyToString 2 "+key_string);
        return key_string;
    }

    public static byte[] readbytes(File file) throws IOException {

        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        } finally {
            try {
                if (ous != null) {
                    ous.close();
                }
            } catch (IOException e) {
            }

            try {
                if (ios != null) {
                    ios.close();
                }
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }

    public static int seedid_byte(int seed_id) {
        int noofbits = 8;
        int length, bitdiff, counter = 0;
        String bitvalue = "0";

        String seedbits = Integer.toBinaryString(seed_id);
        System.out.println("seedid" + seedbits + "len:" + seedbits.length());
        if (seedbits.length() < noofbits) {
            // System.out.println("ss if before:" + ss);
            length = seedbits.length();
            bitdiff = noofbits - length;

            while (counter != bitdiff) {

                seedbits = seedbits + bitvalue;
                counter++;
            }
        }
        System.out.println("seedid after" + seedbits);
        int s_id = Integer.parseInt(seedbits);
        return s_id;

    }

    public static byte[] performXOR(byte[] encrypted, int seed_id) { //Connection connection
        if (encrypted == null) {
            System.out.println("input byte array is null.");
        }
        System.out.println("seed_id:" + seed_id);
        // int seedid_byte = seed_id;
        int seedid_byte = seedid_byte(seed_id);
        System.out.println("seed_id of byte:" + seedid_byte);
        int sbid;
        sbid = seedid_byte;

        byte xorop;
        byte seedbyte = (byte) sbid;
        System.out.println("seedbyte " + seedbyte);
//        FileInputStream fileInputStream = null;
        byte[] bFile = encrypted;
        byte[] op_bFile = new byte[encrypted.length];
//        byte[] org = new byte[encrypted.length];


        try {
            for (int i = 0; i < bFile.length; i++) {
                //System.out.print("chrdata:" + (char) bFile[i]);

                xorop = (byte) (seedbyte ^ bFile[i]);
                //System.out.print("chrdata xor:" + (char) xorop);
                op_bFile[i] = xorop;
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
            e.printStackTrace();
        }
        return op_bFile;

    }

    public static BufferedImage createImageFromBytes(byte[] imageData) {
        BufferedImage bImageFromConvert = null;
        try {
//            byte[] imageInByte;
            InputStream in = new ByteArrayInputStream(imageData);
            bImageFromConvert = ImageIO.read(in);
//            ImageIO.write(bImageFromConvert, "jpg", new File(
//       
        } catch (Exception ee) {
            System.out.println("Exception:" + ee);
        }
        return bImageFromConvert;
    }

    public static int generateseedblockid(int cid, int rno) {

        int sbid;
        sbid = cid ^ rno;
        return sbid;


    }

    public boolean valiadate_eid(String email_id) {
        String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        String eid = "user@domain.com";
        Boolean b = eid.matches(EMAIL_REGEX);
        System.out.println("is e-mail: " + eid + " :Valid = " + b);
        String email2 = "user^domain.co.in";
        b = email2.matches(EMAIL_REGEX);
        System.out.println("is e-mail: " + email2
                + " :Valid = " + b);
        return b;
    }

    public static boolean insert_userdata(int rn_no, String user_nm, String password, String email_id) throws ClassNotFoundException, SQLException {
        boolean success = false;
        try {
            Statement st = dbcon.connectDB();
            if (st == null) {
                System.out.println("st is null");
            }

            int seedblockid;
            int uid;
//        if (stmt == null) {
//            System.out.println("stmt is null...");
//        }

            String sql = "insert into users(name,password,email) values('" + user_nm + "','" + password + "','" + email_id + "')";
            System.out.println("sql query :" + sql);

            int executeUpdate = st.executeUpdate(sql);
            if (executeUpdate > 0) {
                System.out.println("inserted..");
            } else {

                System.out.println("db issue ...");

            }
            System.out.println("---------------------");

            String select_query = "select id from users";
            ResultSet rs = st.executeQuery(select_query);

            if (rs.last()) {

                uid = rs.getInt("id");
                System.out.println("uid:" + uid);

                seedblockid = Cryptography.generateseedblockid(uid, rn_no);
                String toBinaryString = Integer.toBinaryString(rn_no);
                System.out.println("toBinaryString:" + toBinaryString.length());
                String query = "update  users set seed_id ='" + seedblockid + "' where id='" + uid + "'";//
                int executequery = st.executeUpdate(query);
                if (executequery > 0) {
                    System.out.println(" seed_id inserted..");
                } else {

                    System.out.println("db issue ...");

                }
            }
            success = true;
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return false;

    }

    public static int getfile_id(Statement stmt, int seed_id, String fileid) {
        int f_id = 0;

        return 0;
    }

    public static int getsid_users(String unm, String pw) throws SQLException, ClassNotFoundException {
        Statement stmt = dbcon.connectDB();
        int sid = 0;
        String query = "select seed_id from users where name='" + unm + "'and password='" + pw + "' ";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            sid = rs.getInt("seed_id");
        }
        return sid;
    }

    public static HashMap<Integer, String> getuser_id_eid() throws ClassNotFoundException {

        HashMap<Integer, String> userdata = new HashMap<Integer, String>();
        try {
            Statement stmt = dbcon.connectDB();
            String query = "select id ,email from  users";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String eid = rs.getString("email");
                int id = rs.getInt("id");
                userdata.put(id, eid);
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        System.out.println("getuser_id_eid hm :" + userdata.size());
        return userdata;


    }

    public static String getuser_eid(String unm, String pass) throws ClassNotFoundException {
        String eid = "";
        try {
            Statement stmt = dbcon.connectDB();
            String query = "select email from  users where name	='" + unm + "'and password='" + pass + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                eid = rs.getString("email");

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return eid;


    }

    public static ArrayList<String> email_to(ArrayList<Integer> selected_userid, HashMap<Integer, String> userdata) {
        ArrayList<String> emailid = new ArrayList<String>();
        for (Entry<Integer, String> entry : userdata.entrySet()) {
            Integer integer = entry.getKey();
            String eid = entry.getValue();
            boolean contains = selected_userid.contains(integer);
            if (contains) {
                emailid.add(eid);
                System.out.println("selected user id" + integer);
            }
        }
        return emailid;

    }

    public static int insert_filedata(int sid, Integer file_id, String file_name, 
            String file_type, byte[] bytes) throws SQLException, ClassNotFoundException {
        Connection connection = dbcon.connectDB_con();
        boolean isinserted = false;
        try {
            String query = "INSERT INTO filedata (file_id,file_name,file_type,data_file)"
                    + " VALUES (null,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(query
                    , Statement.RETURN_GENERATED_KEYS);
//            pstmt.setInt(1, file_id);
            pstmt.setString(1, file_name);
            pstmt.setString(2, file_type);
            pstmt.setBytes(3, bytes);
            pstmt.execute();
            System.out.println("1 insertion  Done");
            isinserted = true;
            ResultSet ids = pstmt.getGeneratedKeys();
            if (ids.next()) {
                return ids.getInt(1);
            }
        } catch (Exception e) {
            isinserted = false;
            e.printStackTrace();
        }
        return -1;//isinserted;
    }

    public static int getownerid(int seed_id) {
        int getoid = 0;
        try {
            Statement stmt = dbcon.connectDB();
            String query = "select id from users where seed_id=" + seed_id + "";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                getoid = rs.getInt("id");
                System.out.println("id" + getoid);
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return getoid;
    }

    public static boolean insert_filedetails(int oid, int file_id, String file_nm, 
            PrivateKey secretKey, String d, String n) {
        boolean save = false;
        
//        String encoded = Base64.encode(secretKey.getEncoded());
        String encoded = Cryptography.KeyToString(secretKey);
//         String encoded = Base64.encode(secretKey.getEncoded());
        try {
            Statement stmt = dbcon.connectDB();
            String query = "INSERT INTO  filedetailbackup (owner_id,file_id,file_name,"
                    + " secret_key) VALUES ('" + oid + "','" + file_id + "','" + file_nm + "',"
                    + "'" + encoded + "')";
            System.out.println("query "+query);
            int rs = stmt.executeUpdate(query);
            
            Statement stmt1 = dbcon.connectDB();
            String otp = Decrypt.nextSessionId();
            
            String query1 = "INSERT INTO privatekey (d,n,otp,file_id) " +
                      "VALUES ('"+d+"','"+n+"','"+otp+"','"+file_id+"')";
            System.out.println("query1 "+query1);
            int rs1 = stmt1.executeUpdate(query1);
            
            if (rs > 0) {
                save = true;
            } else {
                save = false;
            }
            Cryptography.otp = otp;
        } catch (Exception ee) {
            ee.printStackTrace();
            System.out.println("Exception:" + ee);
        }
        return save;


    }

    public static boolean insert_backup(int file_id, byte[] bytes, int uid, String file_nm, 
            PrivateKey secretKey) throws ClassNotFoundException, SQLException {
        Connection connection = dbcon.connectDB_con();
        boolean isinserted = false;
        try {
            String query = "INSERT INTO backupdata (file_id,backupfile,user_id,file_name,secretkey) VALUES (?,?,?,?,?)";
            String key = Cryptography.KeyToString(secretKey);
            System.out.println("secret key===" + key);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, file_id);
            pstmt.setBytes(2, bytes);
            pstmt.setInt(3, uid);
            pstmt.setString(4, file_nm);
            pstmt.setString(5, key);

            System.out.println("uid===" + uid);
            pstmt.execute();
            System.out.println(" data stored on backup server");
            isinserted = true;
        } catch (Exception e) {
            System.out.println("Exception:" + e);
            e.printStackTrace();
            isinserted = false;
        }

        return isinserted;

    }
//    public static void bytearraytofiles(byte[] array, String Extension) {
//        File file = null;
//        BufferedImage image = null;
//        if (Extension.equals("jpg") || Extension.equals("png")) {
//            image = createImageFromBytes(array);
//        } else if (Extension.equals("pdf") || Extension.equals("docx") || Extension.equals("docx")) {
//            file = bytearraytofile(array, Extension);
//        }
//
//    }
}
