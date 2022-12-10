package rapbattles.rap_battles.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.Comment;
import rapbattles.rap_battles.ServiceImpl.CommentServiceImplem;
import rapbattles.rap_battles.Util.Exceptions.ForbiddenException;
import rapbattles.rap_battles.Util.Exceptions.MainException;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/comments")
public class CommentController extends BaseController{

    @Autowired
    CommentServiceImplem csi;

    @PostMapping("/comment_post/{post_id}")
    public String writeComment(@PathVariable(value="post_id") int post_ID, @RequestBody Comment comment, HttpSession session) throws ForbiddenException, MainException{
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        csi.writeComment(userDTO.getUser_ID(),comment.getContent(),post_ID);
        return "Comment successfully written.";
    }

    @PostMapping("/reply_comment/{comment_id}")
    public String replyToComment(@PathVariable(value="comment_id") int comment_ID, @RequestBody Comment comment, HttpSession session) throws ForbiddenException, MainException{
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        csi.replyToComment(userDTO.getUser_ID(),comment.getContent(),comment_ID);
        return "Comment successfully replied";
    }

    @PostMapping("/edit_comment/{comment_id}")
    public String editComment(@PathVariable(value="comment_id") int comment_ID, @RequestBody Comment comment, HttpSession session) throws MainException, ForbiddenException{
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        csi.editComment(comment_ID, comment.getContent(),userDTO.getUser_ID());
        return "Comment edited successfully.";
    }

    @GetMapping("/get_all_replies/{comment_id}")
    public List<Comment> getAllRepliesToComment(@PathVariable(value="comment_id") int comment_ID){
        return csi.getAllRepliesToComment(comment_ID);
    }

    @GetMapping("/get/{comment_id}")
    public Comment getCommentByID(@PathVariable(value="comment_id") int comment_ID) throws MainException {
        return csi.getCommentByID(comment_ID);
    }

    @GetMapping("/get_for_post/{post_id}")
    public List<Comment> getAllCommentsForPost(@PathVariable(value="post_id") int post_ID){
        return csi.getAllCommentsForPost(post_ID);
    }

    @GetMapping("/get_for_user/{user_id}")
    public List<Comment> getAllCommentsByUser(@PathVariable(value="user_id") int user_ID){
        return csi.getAllCommentsByUser(user_ID);
    }

    @DeleteMapping("/delete/{comment_id}")
    public String deleteComment(@PathVariable(value="comment_id")int comment_ID, HttpSession session) throws ForbiddenException, MainException {
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        csi.deleteComment(comment_ID,userDTO.getUser_ID());
        return "Comment deleted successfully.";
    }
}