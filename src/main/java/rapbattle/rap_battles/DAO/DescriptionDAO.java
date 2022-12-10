package rapbattles.rap_battles.DAO;
import rapbattles.rap_battles.Models.POJO.Description;

public interface DescriptionDAO {

    void updateDescription(String description, int user_ID);

    void addDescription(String description, int user_ID);

    Description findDescriptionById(int user_ID);
}