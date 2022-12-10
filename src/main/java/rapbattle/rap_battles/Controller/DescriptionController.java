package rapbattles.rap_battles.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.Description;
import rapbattles.rap_battles.ServiceImpl.DescriptionServiceImplem;
import rapbattles.rap_battles.Util.Exceptions.NotLoggedException;

import javax.servlet.http.HttpSession;

import static rapbattles.rap_battles.ServiceImpl.UserServiceImplem.LOGGED;

@RestController
public class DescriptionController extends BaseController {

    @Autowired
    DescriptionServiceImplem dsi;

    @PostMapping("/updateDescription")
    public Description updateUserDescription(@RequestBody Description description, HttpSession session) throws NotLoggedException {
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);

        return dsi.updateUserDescription(description.getUser_description(),userDTO);
    }
}