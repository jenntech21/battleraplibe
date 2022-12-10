package rapbattles.rap_battles.DAOImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import rapbattles.rap_battles.DAO.ActivationCodeDAO;
import rapbattles.rap_battles.Models.POJO.UserActivationCode;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ActivationCodeDAOImplem implements ActivationCodeDAO {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void uploadActivationCode(int user_ID, String activation_code) {
        String sql = "INSERT INTO user_activation_code(user_ID,activation_code) VALUES(?,?)";

        jdbc.update(sql, new Object[]{user_ID, activation_code});
    }

    public UserActivationCode findUserIdByActivationCode(String activation_code){
        try {
        String sql = "SELECT user_ID, activation_code FROM user_activation_code WHERE activation_code = ?";
        UserActivationCode userActivationCode = jdbc.queryForObject(sql, new Object[]{activation_code}, (resultSet, i) -> mapRow(resultSet));
        return userActivationCode;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String findActivationCodeByUserID(int user_ID){
        try {
            String sql = "SELECT user_ID, activation_code FROM user_activation_code WHERE user_ID = ?";
            UserActivationCode userActivationCode = jdbc.queryForObject(sql, new Object[]{user_ID}, (resultSet, i) -> mapRow(resultSet));
            return userActivationCode.getActivation_code();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private UserActivationCode mapRow(ResultSet rs) throws SQLException {
        return new UserActivationCode(rs.getInt("user_ID"), rs.getString("activation_code"));
    }

}
