package rapbattles.rap_battles.DAO;

import rapbattles.rap_battles.Models.POJO.Like;

import java.util.List;

public interface PostLikeDAO {

    List<Like> getAllPostLikes(int post_ID);

    Like getPostLike(int user_ID, int post_ID);

    void updatePostLikeToLike(int post_like_ID);

    void updatePostLikeToDislike(int post_like_ID);

    void likePost(int post_ID,int user_ID);

    void dislikePost(int post_ID,int user_ID);

    void deleteLike(int like_ID);
}