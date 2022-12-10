package rapbattles.rap_battles.Util.Exceptions;

public class WrongActivationCodeException extends MainException {

    private String message;

    public WrongActivationCodeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}