package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.POJO.Like;
import rapbattles.rap_battles.Util.Exceptions.MainException;

public interface CommentLikeService {

    Like getCommentLike(int comment_ID, int user_ID) throws MainException;

    void likeComment(int comment_ID,int user_ID) throws MainException;

    void dislikeComment(int comment_ID,int user_ID) throws MainException;
}