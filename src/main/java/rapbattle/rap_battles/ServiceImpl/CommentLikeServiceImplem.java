package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.CommentDAOImplem;
import rapbattles.rap_battles.DAOImplementation.CommentLikeDAOImplem;
import rapbattles.rap_battles.Models.POJO.Like;
import rapbattles.rap_battles.Service.CommentLikeService;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import rapbattles.rap_battles.Util.Exceptions.NotFoundException;

@Service
public class CommentLikeServiceImplem implements CommentLikeService {

    @Autowired
    CommentLikeDAOImplem clDAO;

    @Autowired
    CommentDAOImplem commentDAO;

    public Like getCommentLike(int comment_ID, int user_ID) throws MainException {
        if (commentDAO.getCommentByID(comment_ID) == null){
            throw new NotFoundException("There is no comment with this ID.");
        }
        return clDAO.getCommentLike(comment_ID,user_ID);
    }

    public void likeComment(int comment_ID,int user_ID) throws MainException {
        Like like = getCommentLike(comment_ID, user_ID);
        if (like ==null) {
            clDAO.likeComment(comment_ID, user_ID);
            commentDAO.incrementNumberOfLikes(comment_ID);
        }
        else{
            if (like.isReaction()==false){
                clDAO.updateCommentLikeToLike(like.getLike_ID());
                commentDAO.incrementNumberOfLikes(comment_ID);
            }
        }
    }

    public void dislikeComment(int comment_ID,int user_ID) throws MainException {
        Like like = getCommentLike(comment_ID, user_ID);
        if (like ==null) {
            clDAO.dislikeComment(comment_ID, user_ID);
            commentDAO.decrementNumberOfLikes(comment_ID);
        }
        else{
            if (like.isReaction()==true){
                clDAO.updateCommentLikeToDislike(like.getLike_ID());
                commentDAO.decrementNumberOfLikes(comment_ID);
            }
        }
    }
}