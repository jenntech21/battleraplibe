package rapbattles.rap_battles.ServiceImpl;

import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Util.Exceptions.InvalidURLException;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public abstract class UploadService {

    public String uploadFile(String fileStr, UserDTO user, String upload_url, String ending) throws IOException, MainException {
        String base64 = fileStr;
        if (base64.equals("")||base64==null){
            throw new InvalidURLException("URL is not valid or empty!");
        }
        byte[] bytes = Base64.getDecoder().decode(base64);
        String name = user.getUser_ID()+"_"+System.currentTimeMillis()+ending;
        File newImage = new File(upload_url +name);
        FileOutputStream fos = new FileOutputStream(newImage);
        fos.write(bytes);
        return name;
    }
}