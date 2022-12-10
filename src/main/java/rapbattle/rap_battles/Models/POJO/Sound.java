package rapbattles.rap_battles.Models.POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Sound {

    private int sound_ID;
    private String path;

    public Sound(String path) {
        this.path = path;
    }
}