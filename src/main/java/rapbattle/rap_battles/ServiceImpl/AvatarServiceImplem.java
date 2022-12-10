package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.AvatarDAOImplem;
import rapbattles.rap_battles.Models.DTO.UploadDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.Avatar;
import rapbattles.rap_battles.Service.UserAvatarService;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class AvatarServiceImplem extends UploadService implements UserAvatarService {

    public static final String IMAGE_URL = "C:\\Users\\Konstantin\\Desktop\\ProjectImages\\Avatars\\";

    @Autowired
    AvatarDAOImplem uaDAO;

    public void uploadAvatarImage(UploadDTO dto, UserDTO user) throws IOException, MainException {
        Avatar avatar = new Avatar(user.getUser_ID(),uploadFile(dto.getFileStr(), user, IMAGE_URL,".png"));
        uaDAO.uploadImage(avatar);
    }

    public byte[] downloadImage(String imageName)throws IOException{
        File newImage = new File(IMAGE_URL +imageName+".png");
        FileInputStream fis = new FileInputStream(newImage);
        return fis.readAllBytes();
    }
}