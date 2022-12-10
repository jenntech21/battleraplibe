package rapbattles.rap_battles.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.ServiceImpl.UserServiceImplem;
import rapbattles.rap_battles.Util.Exceptions.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController{

    @Autowired
    UserServiceImplem usi;

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody User user, HttpSession session) throws MainException {

        return usi.addUser(user, session);
    }

    @PostMapping(value = "/login")
    public UserDTO loginUser(@RequestBody User user, HttpSession session) throws MainException {
        return usi.login(user,session);
    }

    @GetMapping("/activate/{activation_code}")
    public String activateAccount(@PathVariable(value = "activation_code") String activation_code) throws MainException {
        usi.activateAccountService(activation_code);
        return "<h1>Your account has been activated.</h1>";
    }

    @DeleteMapping("/deleteAccount")
    public int deleteUser(HttpSession session) throws ForbiddenException {
        validateLogged(session);
        return usi.removeUser(session);
    }

    @GetMapping(value = "user_avatar/{user_ID}", produces = "image/png")
    public byte[] viewAvatar(@PathVariable("user_ID") int user_ID) throws IOException, NotFoundException {
        return usi.viewUserAvatarByID(user_ID);
    }

    @PostMapping("/change_password")
    public String changePassword(@RequestBody User user, HttpSession session) throws ForbiddenException,MainException{
        validateLogged(session);
        usi.changePassword(user,session);
        return "Password changed successfully";
    }


}