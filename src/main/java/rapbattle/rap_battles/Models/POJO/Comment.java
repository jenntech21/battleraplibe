package rapbattles.rap_battles.Models.POJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private int comment_ID;
    private int user_ID;
    private int reply_to_ID;
    private String content;
    private java.util.Date date_time_created;
    private int post_ID;
}