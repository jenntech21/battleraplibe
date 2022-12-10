package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.DescriptionDAOImplem;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.Description;
import rapbattles.rap_battles.Service.DescriptionService;

@Service
public class DescriptionServiceImplem implements DescriptionService {

    @Autowired
    DescriptionDAOImplem ddao;

    @Override
    public Description updateUserDescription(String description, UserDTO userDTO) {
        if(ddao.findDescriptionById(userDTO.getUser_ID())==null){
            ddao.addDescription(description,userDTO.getUser_ID());
        }
        else {
            ddao.updateDescription(description, userDTO.getUser_ID());
        }
        return ddao.findDescriptionById(userDTO.getUser_ID());
    }
}