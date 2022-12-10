package rapbattles.rap_battles.DAO;

import rapbattles.rap_battles.Models.POJO.Comment;
import java.util.List;

public interface CommentDAO {

    void writeComment(int user_ID, String content, int post_ID);

    void incrementNumberOfLikes(int comment_ID);

    void decrementNumberOfLikes(int comment_ID);

    void replyToComment(int user_ID, String content, int post_ID, int reply_to_ID);

    void editComment(int comment_ID, String content);

    List<Comment> getAllRepliesToComment(int reply_to_ID);

    Comment getCommentByID(int comment_ID);

    List<Comment> getAllCommentsForPost(int post_ID);

    List<Comment> getAllCommentsByUser(int user_ID);

    void deleteComment(int comment_ID);
}