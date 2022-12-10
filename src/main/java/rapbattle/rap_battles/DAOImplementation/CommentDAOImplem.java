package rapbattles.rap_battles.DAOImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.DAO.CommentDAO;
import rapbattles.rap_battles.Models.POJO.Comment;
import rapbattles.rap_battles.Models.POJO.Like;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class CommentDAOImplem implements CommentDAO {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    CommentLikeDAOImplem clDAO;

    public void writeComment(int user_ID, String content, int post_ID) {
        java.util.Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String sql = "INSERT INTO comments (user_ID, content, date_time_created, post_ID) VALUES (?,?,?,?)";
        jdbc.update(sql, new Object[]{user_ID, content, timestamp, post_ID});
    }

    public void incrementNumberOfLikes(int comment_ID){
        String sql = "UPDATE comments SET number_of_likes = number_of_likes+1 WHERE comment_ID = ?";
        jdbc.update(sql, new Object[]{comment_ID});
    }

    public void decrementNumberOfLikes(int comment_ID){
        String sql = "UPDATE comments SET number_of_likes = number_of_likes-1 WHERE comment_ID = ?";
        jdbc.update(sql, new Object[]{comment_ID});
    }

    public void replyToComment(int user_ID, String content, int post_ID, int reply_to_ID) {
        java.util.Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String sql = "INSERT INTO comments (user_ID, content, date_time_created, post_ID, reply_to_ID) VALUES (?,?,?,?,?)";
        jdbc.update(sql, new Object[]{user_ID, content, timestamp, post_ID, reply_to_ID});
    }

    public List<Comment> getAllRepliesToComment(int reply_to_ID) {
        String sql = "SELECT comment_ID, user_ID, reply_to_ID, content, date_time_created, post_ID FROM comments WHERE reply_to_ID = ?";
        return jdbc.query(sql, new Object[]{reply_to_ID}, (resultSet, i) -> mapRowComment(resultSet));
    }

    public void editComment(int comment_ID, String content){
        String sql = "UPDATE comments SET content = ? WHERE comment_ID = ?";
        jdbc.update(sql, new Object[]{content, comment_ID});
    }

    public Comment getCommentByID(int comment_ID) {
        try {
            String sql = "SELECT comment_ID, user_ID, reply_to_ID, content, date_time_created, post_ID FROM comments WHERE comment_ID = ?";
            return (Comment) jdbc.queryForObject(sql, new Object[]{comment_ID}, new CommentMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Comment> getAllCommentsForPost(int post_ID) {
        String sql = "SELECT comment_ID, user_ID, reply_to_ID, content, date_time_created, post_ID FROM comments WHERE post_ID = ?";
        return jdbc.query(sql, new Object[]{post_ID}, (resultSet, i) -> mapRowComment(resultSet));
    }

    public List<Comment> getAllCommentsByUser(int user_ID) {
        String sql = "SELECT comment_ID, user_ID, reply_to_ID, content, date_time_created, post_ID FROM comments WHERE user_ID = ?";
        return jdbc.query(sql, new Object[]{user_ID}, (resultSet, i) -> mapRowComment(resultSet));
    }

    public void deleteComment(int comment_ID) {
        List<Like> commentLikes = clDAO.getAllCommentLikes(comment_ID);
        for (Like commentLike: commentLikes){
            clDAO.deleteLike(commentLike.getLike_ID());
        }
        String sql = "DELETE FROM comments WHERE comment_ID=?";
        jdbc.update(sql, new Object[]{comment_ID});
        sql = "DELETE FROM comments WHERE reply_to_ID=?";
        jdbc.update(sql, new Object[]{comment_ID});
    }

    private static final class CommentMapper implements RowMapper {
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Comment comment = new Comment();
            comment.setComment_ID(rs.getInt("comment_ID"));
            comment.setUser_ID(rs.getInt("user_ID"));
            comment.setReply_to_ID(rs.getInt("reply_to_ID"));
            comment.setContent(rs.getString("content"));
            comment.setDate_time_created(rs.getTimestamp("date_time_created"));
            comment.setPost_ID(rs.getInt("post_ID"));
            return comment;
        }
    }

    private Comment mapRowComment(ResultSet rs) throws SQLException {
        return new Comment(rs.getInt("comment_ID"), rs.getInt("user_ID"),
                rs.getInt("reply_to_ID"), rs.getString("content"),
                rs.getTimestamp("date_time_created"), rs.getInt("post_ID"));
    }
}