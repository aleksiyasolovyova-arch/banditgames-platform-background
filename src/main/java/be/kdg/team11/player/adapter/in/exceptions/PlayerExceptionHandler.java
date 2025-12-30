package be.kdg.team11.player.adapter.in.exceptions;

import be.kdg.team11.player.domain.friendship.exceptions.FriendRequestAlreadyExistsException;
import be.kdg.team11.player.domain.friendship.exceptions.FriendshipNotFoundException;
import be.kdg.team11.player.domain.friendship.exceptions.InvalidFriendshipException;
import be.kdg.team11.player.domain.friendship.exceptions.InvalidFriendshipStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlayerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((e1, e2) -> e1 + ", " + e2)
                .orElse("Validation failed");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("VALIDATION_ERROR", errors));
    }

    @ExceptionHandler(FriendshipNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFriendshipNotFound(FriendshipNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("FRIENDSHIP_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(InvalidFriendshipException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFriendship(InvalidFriendshipException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_FRIENDSHIP_DATA", ex.getMessage()));
    }

    @ExceptionHandler(InvalidFriendshipStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFriendshipState(InvalidFriendshipStateException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_FRIENDSHIP_STATE", ex.getMessage()));
    }

    @ExceptionHandler(FriendRequestAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleFriendshipAlreadyExists(
            FriendRequestAlreadyExistsException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("FRIENDSHIP_ALREADY_EXISTS", ex.getMessage()));
    }

}
