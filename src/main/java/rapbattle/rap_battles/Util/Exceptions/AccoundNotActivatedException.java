package rapbattles.rap_battles.Util.Exceptions;

public class AccountNotActivatedException extends MainException {

    String message;

    public AccountNotActivatedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}