package rapbattles.rap_battles.Util.Exceptions;

public class NotAuthorisedException extends ForbiddenException {

    private String message;

    public NotAuthorisedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}