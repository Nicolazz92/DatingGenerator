package com.velikokhatko.exceptions;

import com.velikokhatko.model.Match;
import com.velikokhatko.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityExistsException;

@ControllerAdvice
public class ExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(Match.class);
    private final UserService userService;

    public ExceptionHandler(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception exception) {
        logger.error(exception.getMessage());
        return getErrorModelAndView(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @org.springframework.web.bind.annotation.ExceptionHandler(UnsupportedOperationException.class)
    public ModelAndView handleUnsupportedOperation(Exception exception) {
        logger.error(exception.getMessage());
        return getErrorModelAndView(exception, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(EntityExistsException.class)
    public ModelAndView handlePageNotFound(Exception exception) {
        logger.error(exception.getMessage());
        return getErrorModelAndView(exception, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNPE(Exception exception) {
        logger.error(exception.getMessage());
        return getErrorModelAndView(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ModelAndView getErrorModelAndView(Exception exception, HttpStatus httpStatus) {
        ModelAndView mav = new ModelAndView("errors/errorView");
        mav.addObject("httpStatus", httpStatus.toString());
        mav.addObject("devMessage", exception.getMessage());
        mav.addObject("hasLoggedUser", userService.getAuthorizedUser().isPresent());
        return mav;
    }
}
