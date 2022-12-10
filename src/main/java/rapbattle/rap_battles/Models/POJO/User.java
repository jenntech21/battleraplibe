package rapbattles.rap_battles.Models.POJO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {


    private int user_ID;
    private String username;
    private String email;
    private String password;
    private String second_password;
    private String salt;
    private boolean activated;
}