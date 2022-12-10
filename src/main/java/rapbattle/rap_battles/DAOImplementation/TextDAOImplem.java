package rapbattles.rap_battles.DAOImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.DAO.TextDAO;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class TextDAOImplem implements TextDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public int writeText(String content){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO texts (content) VALUES (?)";
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, content);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void updateText(String content, int text_ID){
        String sql = "UPDATE texts SET content = ? WHERE text_ID = ?";
        jdbc.update(sql, new Object[]{content, text_ID});
    }

    public void deleteText(int text_ID){
        String sql = "DELETE FROM texts WHERE text_ID=?";
        jdbc.update(sql, new Object[]{text_ID});
    }
}