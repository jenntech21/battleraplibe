package rapbattles.rap_battles.DAO;

import rapbattles.rap_battles.Models.POJO.Avatar;

public interface UserAvatarDAO {

    void uploadImage(Avatar userAvatar);

    String findImageById(int user_ID);
}