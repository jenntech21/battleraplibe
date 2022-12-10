package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.Util.Exceptions.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface UserService {

    UserDTO addUser(User user, HttpSession session) throws MainException;

    UserDTO login(User user, HttpSession session) throws MainException;

    int removeUser(HttpSession session) throws NotLoggedException;

    byte[] viewUserAvatarByID(int user_ID) throws NotFoundException, IOException;
}