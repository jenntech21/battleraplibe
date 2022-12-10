package rapbattles.rap_battles.Models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private int post_ID;
    private String username;
    private String title;
    private String content;
    private String picPath;
    private String sound_path;
    private java.util.Date date_time_created;
    private String fileStr;
    private String soundFile;
    private int number_of_likes;

    public PostDTO(int post_ID, String username, String title, String content, String picPath,String sound_path, Date date_time_created, int number_of_likes) {
        this.post_ID = post_ID;
        this.username = username;
        this.title = title;
        this.content = content;
        this.picPath = picPath;
        this.date_time_created = date_time_created;
        this.number_of_likes = number_of_likes;
        this.sound_path = sound_path;
    }
}