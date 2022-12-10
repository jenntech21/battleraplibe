package rapbattles.rap_battles.DAO;

import rapbattles.rap_battles.Models.POJO.Like;

import java.util.List;

public interface CommentLikeDAO {

    Like getCommentLike(int post_ID, int user_ID);

    List<Like> getAllCommentLikes(int comment_ID);

    void updateCommentLikeToLike(int comment_like_ID);

    void updateCommentLikeToDislike(int comment_like_ID);

    void likeComment(int post_ID, int user_ID);

    void dislikeComment(int post_ID, int user_ID);

    void deleteLike(int like_ID);
}