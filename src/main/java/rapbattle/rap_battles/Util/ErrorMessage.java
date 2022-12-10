package rapbattles.rap_battles.Util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorMessage {

        private String msg;
        private int status;
        private LocalDateTime time;
}