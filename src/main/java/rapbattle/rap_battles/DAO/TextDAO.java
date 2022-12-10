package rapbattles.rap_battles.DAO;

public interface TextDAO {

    int writeText(String content);

    void updateText(String content, int text_ID);

    void deleteText(int text_ID);
}