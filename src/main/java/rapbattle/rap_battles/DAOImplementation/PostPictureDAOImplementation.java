package rapbattles.rap_battles.DAOImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.DAO.PostPictureDAO;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class PostPictureDAOImplementation implements PostPictureDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public int uploadPostPicture(String path){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO post_pictures (path) VALUES (?)";
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, path);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void deletePostPicture(int picture_ID){
        String sql = "DELETE FROM post_pictures WHERE picture_ID=?";
        jdbc.update(sql, new Object[]{picture_ID});
    }
}