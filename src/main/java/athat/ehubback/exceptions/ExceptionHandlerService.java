package athat.ehubback.exceptions;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandlerService {

  @ExceptionHandler
  public ResponseEntity<ProblemDetail> handleError(ResponseStatusException ex) {
    return ResponseEntity.status(ex.getStatusCode().value()).body(ex.getBody());
  }

  @ExceptionHandler
  public ResponseEntity<String> handleError(Exception ex) {
    return ResponseEntity.internalServerError().body(ex.getMessage());
  }


}
