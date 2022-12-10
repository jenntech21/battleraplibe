package rapbattles.rap_battles.DAOImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.DAO.DescriptionDAO;
import rapbattles.rap_battles.Models.POJO.Description;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DescriptionDAOImplem implements DescriptionDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public void updateDescription(String description, int user_ID){
        String sql = "UPDATE descriptions SET user_description = ? WHERE user_ID = ?";
        jdbc.update(sql, new Object[]{description, user_ID});
    }

    public void addDescription(String description, int user_ID){
        String sql = "INSERT INTO descriptions(user_ID, user_description) VALUES(?,?)";
        jdbc.update(sql, new Object[]{user_ID,description});
    }

    public Description findDescriptionById(int user_ID){
        try{
            String sql = "SELECT user_ID, user_description FROM descriptions WHERE user_ID = ?";
            return (Description) jdbc.queryForObject(sql, new Object[]{user_ID}, new DescriptionMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    private static final class DescriptionMapper implements RowMapper {

        public Description mapRow(ResultSet rs, int rowNum) throws SQLException {
            Description description = new Description();
            description.setUser_ID(rs.getInt("user_ID"));
            description.setUser_description(rs.getString("user_description"));
            return description;
        }
    }
}