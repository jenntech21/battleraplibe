package rapbattles.rap_battles.Util.Exceptions;

public class NotFoundException extends MainException{

    private String message;

    public NotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}