package rapbattles.rap_battles.Util.Exceptions;

public class InvalidUsernameOrEmailException extends MainException {

        String message;

        public InvalidUsernameOrEmailException(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }