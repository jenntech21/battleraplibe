package rapbattles.rap_battles.DAOImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.DAO.CommentLikeDAO;
import rapbattles.rap_battles.Models.POJO.Like;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentLikeDAOImplem implements CommentLikeDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public Like getCommentLike(int comment_ID, int user_ID){
        try {
            String sql = "SELECT like_ID, target_ID, user_ID, reaction FROM comment_likes WHERE target_ID = ? AND user_ID = ?";
            return (Like) jdbc.queryForObject(sql, new Object[]{comment_ID, user_ID}, new LikeMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Like> getAllCommentLikes(int comment_ID){
        String sql = "SELECT like_ID, target_ID, user_ID, reaction FROM comment_likes WHERE target_ID = ?";
        return jdbc.query(sql, new Object[]{comment_ID}, (resultSet, i) -> mapRowLike(resultSet));
    }

    public void updateCommentLikeToLike(int like_ID){
        String sql = "UPDATE comment_likes SET reaction = 1 WHERE like_ID = ?";
        jdbc.update(sql, new Object[]{like_ID});
    }

    public void updateCommentLikeToDislike(int like_ID){
        String sql = "UPDATE comment_likes SET reaction = 0 WHERE like_ID = ?";
        jdbc.update(sql, new Object[]{like_ID});
    }

    public void likeComment(int comment_ID, int user_ID){
        String sql = "INSERT INTO comment_likes (target_ID, user_ID, reaction) VALUES (?,?,1)";
        jdbc.update(sql, new Object[]{comment_ID, user_ID});
    }

    public void dislikeComment(int comment_ID, int user_ID){
        String sql = "INSERT INTO comment_likes (target_ID, user_ID, reaction) VALUES (?,?,0)";
        jdbc.update(sql, new Object[]{comment_ID, user_ID});
    }

    public void deleteLike(int like_ID){
        String sql = "DELETE FROM comment_likes WHERE like_ID=?";
        jdbc.update(sql, new Object[]{like_ID});
    }

    private static final class LikeMapper implements RowMapper {
        public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
            Like commentLike = new Like();
            commentLike.setLike_ID(rs.getInt("like_ID"));
            commentLike.setTarget_ID(rs.getInt("target_ID"));
            commentLike.setUser_ID(rs.getInt("user_ID"));
            commentLike.setReaction(rs.getBoolean("reaction"));
            return commentLike;
        }
    }

    private Like mapRowLike(ResultSet rs) throws SQLException {
        return new Like(rs.getInt("like_ID"), rs.getInt("target_ID"),
                rs.getInt("user_ID"), rs.getBoolean("reaction"));
    }
}