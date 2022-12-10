package rapbattles.rap_battles.Models.POJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    private int like_ID;
    private int target_ID;
    private int user_ID;
    private boolean reaction;
}