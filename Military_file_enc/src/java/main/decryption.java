package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;

/**
 *
 * @author eiosys
 */
public class decryption {


    public static HashMap<String, byte[]> getorgfile(int file_id) {
        byte[] bytes = null;
        HashMap<String, byte[]> orgdetails = new HashMap<String, byte[]>();
        try {
            Statement stmt = dbcon.connectDB();

            String f_type = "";
            String Select = "select data_file,file_name from  filedata where file_id=" + file_id + "";
            System.out.println("query:" + Select);
            Statement st = dbcon.connectDB();
            ResultSet rs1 = st.executeQuery(Select);
            while (rs1.next()) {
                String file_name = rs1.getString("file_name");
                Blob blob = rs1.getBlob("data_file");
                bytes = blobtobytearr(blob);
                orgdetails.put(file_name, bytes);
            }
        } catch (Exception ee) {
        }
        return orgdetails;

    }

    public static byte[] recoverfile_data(int seed_id, int file_id) throws SQLException, ClassNotFoundException {
        // HashMap<String, byte[]> getfiledata = new HashMap<String, byte[]>();
        byte[] bytes = null;
        try {
            Statement stmt = dbcon.connectDB();

            String f_type = "";
            String Select = "select backupfile from backupdata where seed_id=" + seed_id + " and file_id=" + file_id + "";
            System.out.println("query:" + Select);
            Statement st = dbcon.connectDB();
            ResultSet rs1 = st.executeQuery(Select);
            while (rs1.next()) {
                Blob blob = rs1.getBlob("backupfile");
                bytes = blobtobytearr(blob);

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return bytes;

    }

    public static byte[] getoriginalfile_data(int seed_id, int file_id) throws SQLException, ClassNotFoundException {
        // HashMap<String, byte[]> getfiledata = new HashMap<String, byte[]>();
        byte[] bytes = null;
        try {
            String f_type = "";
            String Select = "select data_file from  filedata where file_id=" + file_id + "";
            System.out.println("query:" + Select);
            Statement st = dbcon.connectDB();
            ResultSet rs1 = st.executeQuery(Select);
            while (rs1.next()) {
                Blob blob = rs1.getBlob("backupfile");
                bytes = blobtobytearr(blob);

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return bytes;

    }

    public static String decryptfile(String path,String destpath, SecretKey secKey, String filname, String extension) {
        //  byte[] decryptfileop = null;
//          String absolutePath = request.getRealPath("/") + "temp";
        String cleartextAgainFile = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            String ciphertextFile = path;


            System.out.println("ciphertextFile:" + ciphertextFile);

            cleartextAgainFile = destpath+"\\" + filname;
            cipher.init(Cipher.DECRYPT_MODE, secKey);


//            

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
        }
        return cleartextAgainFile;
    }

    public static byte[] blobtobytearr(Blob blob) throws SQLException {
        int blobLength = (int) blob.length();
        byte[] blobAsBytes = blob.getBytes(1, blobLength);
        blob.free();
        return blobAsBytes;
    }

    public static HashMap<Integer, String> get_filebackup(int fileid) throws ClassNotFoundException, SQLException {
        HashMap<Integer, String> filedata = new HashMap<Integer, String>();
        Statement stmt = dbcon.connectDB();
        String query = "select file_name,seed_id from filedetailbackup where file_id='" + fileid + "'";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String fname = rs.getString("file_name");
            int sid = rs.getInt("seed_id");
            filedata.put(sid, fname);
        }
        return filedata;
    }

    public static void insert_filebackup(int sid, int fileid, String filename) throws ClassNotFoundException, SQLException {
        Statement stmt = dbcon.connectDB();
        String query = "INSERT INTO filedetailbackup(file_id,file_name,seed_id) VALUES ('" + fileid + "','" + filename + "','" + sid + "')";
        int rs = stmt.executeUpdate(query);
        if (rs > 0) {
            System.out.println("inserted into filedetailbackup ");
        }
    }

    public static boolean delete_file(int fileid) {
        boolean status = false;
        try {
            Statement stmt = dbcon.connectDB();

            String sql = "DELETE FROM  filedata "
                    + "WHERE file_id = '" + fileid + "'";
//"WHERE file_id = '" + fileid + "' and file_name='" + filename + "' ";
            System.out.println("delete sql : "+sql);
            int rs = stmt.executeUpdate(sql);
            sql = "DELETE FROM  filedetailbackup "
                    + "WHERE file_id = '" + fileid + "'";
//"WHERE file_id = '" + fileid + "' and file_name='" + filename + "' ";
            System.out.println("delete sql : "+sql);
            rs = stmt.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("file is deleted");
                status = true;
            }
        } catch (Exception ee) {
            status = false;
            ee.printStackTrace();
        }
        return status;

    }

    public static File bytearraytofile(byte[] bytedata, String extension,String  filepath) {

        
        FileInputStream fileInputStream = null;
        File file = new File(filepath+"\\output." + extension + "");
        System.out.println("file  path hello:"+file);
        try {
            FileOutputStream fileOuputStream =
                    new FileOutputStream(file);
            fileOuputStream.write(bytedata);
            fileOuputStream.close();

            System.out.println("Done" + file.getAbsolutePath());

        } catch (Exception ee) {
            System.out.println("exception :" + ee);
        }
        return file;

    }
}
