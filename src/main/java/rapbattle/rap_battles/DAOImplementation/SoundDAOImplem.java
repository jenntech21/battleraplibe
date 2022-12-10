package rapbattles.rap_battles.DAOImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.DAO.SoundDAO;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class SoundDAOImplem implements SoundDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public int uploadSound(String path){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO sounds(sound_path) VALUES (?)";
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, path);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void deleteSound(int sound_ID){
        String sql = "DELETE FROM sounds WHERE sound_ID=?";
        jdbc.update(sql, new Object[]{sound_ID});
    }
}