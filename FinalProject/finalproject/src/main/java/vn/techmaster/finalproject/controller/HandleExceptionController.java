package vn.techmaster.finalproject.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vn.techmaster.finalproject.exception.AuthorizedException;
import vn.techmaster.finalproject.exception.NotFoundException;
import vn.techmaster.finalproject.exception.UserException;

@ControllerAdvice
public class HandleExceptionController {
    @ExceptionHandler(UserException.class)
    public String handleUserException(UserException ex, Model model) {
      model.addAttribute("errors", ex.getMessage());
      return "error";
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException ex, Model model) {
      model.addAttribute("errors", ex.getMessage());
      return "error";
    }

    @ExceptionHandler(AuthorizedException.class)
    public String handleAuthorizedException(AuthorizedException ex, Model model) {
      model.addAttribute("errors", ex.getMessage());
      return "error";
    }
}
