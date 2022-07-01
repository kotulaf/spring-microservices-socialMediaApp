package User;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)       // without this we would get an internal server error, which is not true
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message)
    {
        super(message);             // here we send our message to the super class, which is RuntimeException
    }
}
