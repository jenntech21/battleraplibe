package rapbattles.rap_battles.DAOImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.DAO.PostLikeDAO;
import rapbattles.rap_battles.Models.POJO.Like;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostLikeDAOImplem implements PostLikeDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public Like getPostLike(int post_ID, int user_ID){
        try {
            String sql = "SELECT like_ID, target_ID, user_ID, reaction FROM post_likes WHERE target_ID = ? AND user_ID = ?";
            return (Like) jdbc.queryForObject(sql, new Object[]{post_ID, user_ID}, new LikeMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Like> getAllPostLikes(int post_ID){
        String sql = "SELECT like_ID, target_ID, user_ID, reaction FROM post_likes WHERE target_ID = ?";
        return jdbc.query(sql, new Object[]{post_ID}, (resultSet, i) -> mapRowLike(resultSet));
    }

    public void updatePostLikeToLike(int like_ID){
        String sql = "UPDATE post_likes SET reaction = 1 WHERE like_ID = ?";
        jdbc.update(sql, new Object[]{like_ID});
    }

    public void updatePostLikeToDislike(int like_ID){
        String sql = "UPDATE post_likes SET reaction = 0 WHERE like_ID = ?";
        jdbc.update(sql, new Object[]{like_ID});
    }

    public void likePost(int post_ID, int user_ID){
        String sql = "INSERT INTO post_likes (target_ID, user_ID, reaction) VALUES (?,?,1)";
        jdbc.update(sql, new Object[]{post_ID, user_ID});
    }

    public void dislikePost(int post_ID, int user_ID){
        String sql = "INSERT INTO post_likes (target_ID, user_ID, reaction) VALUES (?,?,0)";
        jdbc.update(sql, new Object[]{post_ID, user_ID});
    }

    public void deleteLike(int like_ID){
        String sql = "DELETE FROM post_likes WHERE like_ID=?";
        jdbc.update(sql, new Object[]{like_ID});
    }

    private static final class LikeMapper implements RowMapper {
        public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
            Like like = new Like();
            like.setLike_ID(rs.getInt("like_ID"));
            like.setTarget_ID(rs.getInt("target_ID"));
            like.setUser_ID(rs.getInt("user_ID"));
            like.setReaction(rs.getBoolean("reaction"));
            return like;
        }
    }

    private Like mapRowLike(ResultSet rs) throws SQLException {
        return new Like(rs.getInt("like_ID"), rs.getInt("target_ID"),
                rs.getInt("user_ID"), rs.getBoolean("reaction"));
    }

}