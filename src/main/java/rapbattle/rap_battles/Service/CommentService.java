package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.POJO.Comment;
import rapbattles.rap_battles.Util.Exceptions.ForbiddenException;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import rapbattles.rap_battles.Util.Exceptions.NotFoundException;
import java.util.List;

public interface CommentService {

    void writeComment(int user_ID, String content, int post_ID) throws MainException;

    void replyToComment(int user_ID, String content, int reply_to_ID) throws MainException;

    void editComment(int comment_ID, String content, int user_ID) throws MainException, ForbiddenException;

    List<Comment> getAllRepliesToComment(int reply_to_ID);

    Comment getCommentByID(int comment_ID) throws NotFoundException;

    List<Comment> getAllCommentsForPost(int post_ID);

    List<Comment> getAllCommentsByUser(int user_ID);

    void deleteComment(int comment_ID, int user_ID) throws ForbiddenException, MainException;
}