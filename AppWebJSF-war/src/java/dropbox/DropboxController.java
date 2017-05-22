/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.WriteMode;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adrián Cardenas, Adrián Garcia, Daniel Lopez, David Luque, John Purihin
 */
public class DropboxController {
    private static final String ACCESS_TOKEN = "HBbsfy0JFUAAAAAAAAAABz7s0NLAl2ezwBDZS-_zgakBYCRA1IIWl0ENg1Vl1_lZ";
    
    private static DbxRequestConfig config = new DbxRequestConfig("appTecWeb");
    private static DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
    
    public static void uploadFile(String fileName, InputStream fileContent ) throws DropboxControllerException  {
        try {
            client.files().upload("/" + fileName).uploadAndFinish(fileContent);
        } catch (DbxException | IOException ex) {
            throw new DropboxControllerException(ex.getMessage());
        } 
    }
    
    public static byte[] downloadFile(String fileName) throws DropboxControllerException{
        byte[] res = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            client.files().download(fileName).download(out);
            res = out.toByteArray();

        } catch (IllegalArgumentException | DbxException | IOException ex) {
            if(ex instanceof IllegalArgumentException)
                System.err.println("Nombre no valido: " + fileName);
            throw new DropboxControllerException(ex.getMessage());
        }
        return res;
    }
    
    public static String getUrl(String userFoto)  {
        String fileName = "/" + userFoto;
        String url = "";
        try{
            byte[] bytes = DropboxController.downloadFile(fileName);
            Encoder encoder = Base64.getEncoder();
            byte[] encodeBase64 = encoder.encode(bytes);
            String base64Encoded = new String(encodeBase64, "UTF-8");
            url = "data:image/png;base64," + base64Encoded;
            
        } catch (DropboxControllerException | UnsupportedEncodingException ex){
            System.err.println(ex.toString());
        }
        return url;
    }
    
    public static void overwriteFile(String fileName, InputStream fileContent){
        try {
            client.files().uploadBuilder("/" + fileName).withMode(WriteMode.OVERWRITE).uploadAndFinish(fileContent);
        } catch (DbxException | IOException ex) {
            Logger.getLogger(DropboxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
