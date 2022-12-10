package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.PostDAOImplem;
import rapbattles.rap_battles.DAOImplementation.PostLikeDAOImplem;
import rapbattles.rap_battles.Models.POJO.Like;
import rapbattles.rap_battles.Service.PostLikeService;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import rapbattles.rap_battles.Util.Exceptions.NotFoundException;

@Service
public class PostLikeServiceImplem implements PostLikeService {

    @Autowired
    PostLikeDAOImplem plDAO;

    @Autowired
    PostDAOImplem postDAO;

    public Like getPostLike(int post_ID, int user_ID) throws MainException{
        if (postDAO.getPostByID(post_ID) == null){
            throw new NotFoundException("There is no post with this ID.");
        }
        return plDAO.getPostLike(post_ID,user_ID);
    }

    public void likePost(int post_ID,int user_ID) throws MainException {
        Like like = getPostLike(post_ID, user_ID);
        if (like ==null) {
            plDAO.likePost(post_ID, user_ID);
            postDAO.incrementNumberOfLikes(post_ID);
        }
        else{
            if (like.isReaction()==false){
                plDAO.updatePostLikeToLike(like.getLike_ID());
                postDAO.incrementNumberOfLikes(post_ID);
            }
        }
    }

    public void dislikePost(int post_ID,int user_ID) throws MainException {
        Like like = getPostLike(post_ID, user_ID);
        if (like ==null) {
            plDAO.dislikePost(post_ID, user_ID);
            postDAO.decrementNumberOfLikes(post_ID);
        }
        else{
            if (like.isReaction()==true){
                plDAO.updatePostLikeToDislike(like.getLike_ID());
                postDAO.decrementNumberOfLikes(post_ID);
            }
        }
    }
}