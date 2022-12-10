package rapbattles.rap_battles.Controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import rapbattles.rap_battles.Util.ErrorMessage;
import rapbattles.rap_battles.Util.Exceptions.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

public abstract class BaseController {

    static Logger log = Logger.getLogger(UserController.class.getName());

    public static final String LOGGED = "logged";

    @ExceptionHandler({MainException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage exceptionHandlerBadRequest(Exception e) {
        log.error(e.getMessage());
        return new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
    }

    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage exceptionHandlerForbidden(Exception e) {
        log.error(e.getMessage());
        return new ErrorMessage(e.getMessage(), HttpStatus.FORBIDDEN.value(), LocalDateTime.now());
    }

    @ExceptionHandler({IOException.class,NotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage exceptionHandlerIOE(Exception e) {
        log.error(e.getMessage());
        return new ErrorMessage(e.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
    }

    protected void validateLogged(HttpSession session) throws NotLoggedException {
        if (session.getAttribute(LOGGED) == null) {
            throw new NotLoggedException("You are not logged in.");
        }
    }
}