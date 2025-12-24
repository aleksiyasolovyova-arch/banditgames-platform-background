package be.kdg.team11.content.adapter.in.exceptions;

import be.kdg.team11.content.domain.achievement.exeptions.AchievementNotFoundException;
import be.kdg.team11.content.domain.achievement.exeptions.InvalidAchievementException;
import be.kdg.team11.content.domain.achievement.exeptions.InvalidAchievementTypeException;
import be.kdg.team11.content.domain.game.exeptions.GameNotFoundException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameDataException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameStateException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContentExceptionHandler {

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


    /**
     * Handles game not found errors
     * Returns 404 Not Found
     */
    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGameNotFound(GameNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("GAME_NOT_FOUND", ex.getMessage()));
    }

    /**
     * Handles invalid game data (null/empty fields, length violations)
     * Returns 400 Bad Request
     */
    @ExceptionHandler(InvalidGameDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidGameData(InvalidGameDataException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_GAME_DATA", ex.getMessage()));
    }

    /**
     * Handles invalid game URLs
     * Returns 400 Bad Request
     */
    @ExceptionHandler(InvalidGameUrlException.class)
    public ResponseEntity<ErrorResponse> handleInvalidGameUrl(InvalidGameUrlException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_GAME_URL", ex.getMessage()));
    }

    /**
     * Handles invalid game state transitions (e.g., accept already accepted game)
     * Returns 400 Bad Request
     */
    @ExceptionHandler(InvalidGameStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidGameState(InvalidGameStateException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_GAME_STATE", ex.getMessage()));
    }

    /**
     * Handles achievement not found errors
     * Returns 404 Not Found
     */
    @ExceptionHandler(AchievementNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAchievementNotFound(AchievementNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("ACHIEVEMENT_NOT_FOUND", ex.getMessage()));
    }

    /**
     * Handles invalid achievement data (null/empty fields, length violations)
     * Returns 400 Bad Request
     */
    @ExceptionHandler(InvalidAchievementException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAchievement(InvalidAchievementException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_ACHIEVEMENT_DATA", ex.getMessage()));
    }

    /**
     * Handles invalid achievement type
     * Returns 400 Bad Request
     */
    @ExceptionHandler(InvalidAchievementTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAchievementType(InvalidAchievementTypeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_ACHIEVEMENT_TYPE", ex.getMessage()));
    }


}
