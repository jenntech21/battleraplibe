package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.PostPictureDAOImplementation;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Service.PostPictureService;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class PostPictureServiceImplem extends UploadService implements PostPictureService {

    @Autowired
    PostPictureDAOImplementation ppDAO;

    public static final String IMAGE_URL = "C:\\Users\\Konstantin\\Desktop\\ProjectImages\\Posts\\";

    public int uploadPostImage(String fileStr, UserDTO userDTO) throws IOException, MainException{
        return ppDAO.uploadPostPicture(uploadFile(fileStr,userDTO,IMAGE_URL,".png"));
    }

    public byte[] downloadImage(String imageName)throws IOException{
        File newImage = new File(IMAGE_URL +imageName+".png");
        FileInputStream fis = new FileInputStream(newImage);
        return fis.readAllBytes();
    }
}