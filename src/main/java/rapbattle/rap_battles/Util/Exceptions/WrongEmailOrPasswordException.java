package rapbattles.rap_battles.Util.Exceptions;

public class WrongEmailOrPasswordException extends MainException {

    String message;

    public WrongEmailOrPasswordException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}