package rapbattles.rap_battles.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.ServiceImpl.CommentLikeServiceImplem;
import rapbattles.rap_battles.Util.Exceptions.ForbiddenException;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/comment_likes")
public class CommentLikeController extends BaseController{

    @Autowired
    CommentLikeServiceImplem cli;

    @GetMapping("/like/{comment_id}")
    public String likeComment(@PathVariable(value ="comment_id") int comment_ID, HttpSession session) throws ForbiddenException, MainException {
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        cli.likeComment(comment_ID,userDTO.getUser_ID());
        return "You have successfully liked the comment.";
    }

    @GetMapping("/dislike/{comment_id}")
    public String dislikeComment(@PathVariable(value ="comment_id") int comment_ID, HttpSession session) throws ForbiddenException, MainException {
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        cli.dislikeComment(comment_ID,userDTO.getUser_ID());
        return "You have successfully disliked the comment.";
    }
}