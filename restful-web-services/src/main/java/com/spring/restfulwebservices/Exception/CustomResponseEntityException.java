package com.spring.restfulwebservices.Exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import User.UserNotFoundException;

@ControllerAdvice           // we make this app-wide, meaning that our exception will be seen by all controllers
@RestController             // this means it's going to be handled by Spring, we will get a response from this
public class CustomResponseEntityException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)      // this overrides the current exception class wide (hence the Exception class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) { // copied this from ResponseEntityExceptionHandler but since we can't override the original function, i renamed it
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        // this is the class that we created, you can see that we get messages from the exception, and description from requests
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        // then we return a new ResponseEntity and pass in our exceptionResponse and the Http Status, which is by default the internal server error at the moment
    }

    // now we do the same but for our UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    // looking at the UserNotFoundException, this might not make sense, but it's just that when UserResource calls the exception, this gets executed

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(      // here we are overriding the original method 
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

                ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation was unsuccessful", ex.getBindingResult().toString()); // we put string as the message since the original .getMessage() was too long, and we use getBindingResult to get details of
                return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST); // here we put bad request, since that's what it is when the validation fails, for example it wouldn't make sense to put not found
	}
}
