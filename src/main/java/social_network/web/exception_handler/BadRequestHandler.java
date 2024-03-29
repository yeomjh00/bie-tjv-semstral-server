package social_network.web.exception_handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import social_network.web.exception_handler.exception.InvalidAccessException;

@Slf4j
@ControllerAdvice
public class BadRequestHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(NoHandlerFoundException e, Model model){
        log.info("404 error: {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "/error";
    }

    @ExceptionHandler(InvalidAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle400(InvalidAccessException e, Model model){
        log.info("400 error: {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "/error";
    }
}
