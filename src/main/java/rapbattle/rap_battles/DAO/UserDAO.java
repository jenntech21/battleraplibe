package rapbattles.rap_battles.DAO;

import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;

public interface UserDAO {

    void registerUser(User user);

    UserDTO findUserByEmailDTO(String email);

    User findUserByEmail(String email);

    UserDTO findUserByID(int user_ID);

    UserDTO findUserByUsernameDTO(String username);

    void deleteUserByID(int user_ID);

}