package com.discount.support;

import com.discount.dtos.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

/**
 * The type Controller exception handler.
 */
@ControllerAdvice(basePackages = "com.discount.controller")
public class ControllerExceptionHandler {


    /**
     * Not found exception handler error message.
     *
     * @param ex the ex
     * @return the error message
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ErrorMessage notFoundExceptionHandler(EntityNotFoundException ex)
    {
        return new ErrorMessage(String.valueOf(HttpStatus.NOT_FOUND.value())
                ,HttpStatus.NOT_FOUND.getReasonPhrase()
                ,ex.getMessage());
    }
}
