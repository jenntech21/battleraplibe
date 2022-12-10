package rapbattles.rap_battles.Util.Exceptions;

public class NotLoggedException extends ForbiddenException {

    private String message;

    public NotLoggedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}