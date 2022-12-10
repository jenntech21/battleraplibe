package rapbattles.rap_battles.Util.Exceptions;

public class InvalidURLException extends MainException {

    String message;

    public InvalidURLException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}