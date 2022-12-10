package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.Description;

public interface DescriptionService {

    Description updateUserDescription(String description, UserDTO userDTO);
}