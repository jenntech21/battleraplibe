package rapbattles.rap_battles.DAO;

import rapbattles.rap_battles.Models.POJO.UserActivationCode;

public interface ActivationCodeDAO {

    void uploadActivationCode(int user_ID, String activation_code);

    UserActivationCode findUserIdByActivationCode(String activation_code);

    String findActivationCodeByUserID(int user_ID);
}