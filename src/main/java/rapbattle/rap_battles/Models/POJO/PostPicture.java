package rapbattles.rap_battles.Models.POJO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostPicture {

    private int picture_ID;
    private String path;

    public PostPicture(String path) {
        this.path = path;
    }
}