package rapbattles.rap_battles.Models.POJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Post {

    private int post_ID;
    private int user_ID;
    private String title;
    private int text_ID;
    private int picture_ID;
    private java.util.Date date_time_created;
    private int number_of_likes;
    private int sound_ID;
}