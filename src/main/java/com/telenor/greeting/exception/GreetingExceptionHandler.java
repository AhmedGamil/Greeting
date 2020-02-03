package com.telenor.greeting.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GreetingExceptionHandler {

    /**
     * spring validation return 500-InternalServerError which is not the correct error code so overriding to be 400-badRequest
     *
     * @see <a href="https://github.com/spring-projects/spring-boot/issues/10471</a>
     */

    @ExceptionHandler(value = {UnsatisfiedServletRequestParameterException.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GreetingErrorResponse handleConstraintViolationException(Exception e) {
        log.error(e.getMessage(), e);
        return new GreetingErrorResponse("ERROR.01", e.getMessage());
    }

    //display proper message instead of java exception trace
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GreetingErrorResponse handleConstraintViolationException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        return new GreetingErrorResponse("ERROR.02", "invalid parameter " +e.getName() + " with value " +e.getValue());
    }


    //handel not yet implemented exception
    @ExceptionHandler(value = {UnsupportedOperationException.class})
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public GreetingErrorResponse handleUnsupportedOperationException(UnsupportedOperationException e) {
        log.error(e.getMessage(), e);
        return new GreetingErrorResponse("ERROR.03", "not yet implemented");
    }

    //handel any other exception
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GreetingErrorResponse handleGenericExceptions(Exception e) {
        log.error(e.getMessage(), e);
        return new GreetingErrorResponse("ERROR.00", "something went wrong, please, try again later");
    }

}
