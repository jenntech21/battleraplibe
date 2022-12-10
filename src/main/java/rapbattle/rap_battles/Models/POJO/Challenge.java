package rapbattles.rap_battles.Models.POJO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Challange {

    private int challenge_ID;
    private int challenger_ID;
    private int challengee_ID;
    private java.sql.Date date_time_created;
    private int winner_ID;
    private int loser_ID;
    private boolean accepted;
}