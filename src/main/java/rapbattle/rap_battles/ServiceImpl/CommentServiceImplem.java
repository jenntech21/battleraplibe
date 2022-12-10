package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.CommentDAOImplem;
import rapbattles.rap_battles.DAOImplementation.PostDAOImplem;
import rapbattles.rap_battles.Models.POJO.Comment;
import rapbattles.rap_battles.Service.CommentService;
import rapbattles.rap_battles.Util.Exceptions.ForbiddenException;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import rapbattles.rap_battles.Util.Exceptions.NotAuthorisedException;
import rapbattles.rap_battles.Util.Exceptions.NotFoundException;
import java.util.List;

@Service
public class CommentServiceImplem implements CommentService {

    @Autowired
    CommentDAOImplem commentDAO;

    @Autowired
    PostDAOImplem postDAO;

    public void writeComment(int user_ID, String content, int post_ID) throws MainException {
        if (postDAO.getPostByID(post_ID) == null){
            throw new NotFoundException("There is no post with this ID.");
        }
        commentDAO.writeComment(user_ID,content,post_ID);
    }

    public void replyToComment(int user_ID, String content, int reply_to_ID) throws MainException{
        Comment comment = getCommentByID(reply_to_ID);
        if (postDAO.getPostByID(comment.getPost_ID()) == null){
            throw new NotFoundException("There is no post with this ID.");
        }
        if (comment == null){
            throw new NotFoundException("There is no comment with this ID.");
        }
        commentDAO.replyToComment(user_ID,content,comment.getPost_ID() ,reply_to_ID);
    }

    public void editComment(int comment_ID, String content, int user_ID) throws MainException, ForbiddenException{
        if (commentDAO.getCommentByID(comment_ID)==null){
            throw new NotFoundException("There is no comment with this ID.");
        }
        if (commentDAO.getCommentByID(comment_ID).getUser_ID()!=user_ID){
            throw new NotAuthorisedException("This comment is not yours.");
        }
        commentDAO.editComment(comment_ID,content);
    }

    public List<Comment> getAllRepliesToComment(int reply_to_ID){
        return commentDAO.getAllRepliesToComment(reply_to_ID);
    }

    public Comment getCommentByID(int comment_ID) throws NotFoundException {
        Comment comment = commentDAO.getCommentByID(comment_ID);
        if (comment==null){
            throw new NotFoundException("There is no comment with this ID.");
        }
        return comment;
    }

    public List<Comment> getAllCommentsForPost(int post_ID){
        return commentDAO.getAllCommentsForPost(post_ID);
    }

    public List<Comment> getAllCommentsByUser(int user_ID){
        return commentDAO.getAllCommentsByUser(user_ID);
    }

    public void deleteComment(int comment_ID, int user_ID) throws ForbiddenException, MainException{
        if (commentDAO.getCommentByID(comment_ID)==null){
            throw new NotFoundException("There is no comment with this ID.");
        }
        if (commentDAO.getCommentByID(comment_ID).getUser_ID()!=user_ID){
            throw new NotAuthorisedException("This comment is not yours.");
        }
        commentDAO.deleteComment(comment_ID);
    }
}