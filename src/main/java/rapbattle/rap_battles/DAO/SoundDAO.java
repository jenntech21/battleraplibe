package rapbattles.rap_battles.DAO;

public interface SoundDAO {

    int uploadSound(String path);

    void deleteSound(int sound_ID);
}