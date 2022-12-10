package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.POJO.Like;
import rapbattles.rap_battles.Util.Exceptions.MainException;

public interface PostLikeService {

    Like getPostLike(int post_ID, int user_ID) throws MainException;

    void likePost(int post_ID,int user_ID) throws MainException;

    void dislikePost(int post_ID,int user_ID) throws MainException;
}