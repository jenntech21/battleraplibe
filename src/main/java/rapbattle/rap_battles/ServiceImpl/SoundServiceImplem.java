package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.SoundDAOImplem;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Service.SoundService;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class SoundServiceImplem extends UploadService implements SoundService {

    @Autowired
    SoundDAOImplem soundDAO;

    public static final String SOUND_URL = "C:\\Users\\Konstantin\\Desktop\\ProjectImages\\Sounds\\";

    public int uploadSound(String soundFile, UserDTO userDTO) throws IOException, MainException{
        return soundDAO.uploadSound(uploadFile(soundFile,userDTO,SOUND_URL,".mp3"));
    }

    public byte[] downloadSound(String soundName)throws IOException{
        File sound = new File(SOUND_URL +soundName+".mp3");
        FileInputStream fis = new FileInputStream(sound);
        return fis.readAllBytes();
    }
}