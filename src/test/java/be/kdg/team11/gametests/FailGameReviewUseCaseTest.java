package be.kdg.team11.gametests;

import be.kdg.team11.content.core.FailGameReviewReviewUseCaseImpl;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.domain.game.exeptions.GameNotFoundException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameStateException;
import be.kdg.team11.content.port.in.FailGameReviewCommand;
import be.kdg.team11.content.port.out.LoadGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FailGameReviewUseCase Tests")
class FailGameReviewUseCaseTest {

    @Mock
    private LoadGamePort loadGamePort;

    @Mock
    private SaveGamePort saveGamePort;

    private FailGameReviewReviewUseCaseImpl useCase;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        gameId = UUID.randomUUID();
        useCase = new FailGameReviewReviewUseCaseImpl(loadGamePort, List.of(saveGamePort));
    }

    @Test
    @DisplayName("Should successfully fail a pending game")
    void testFailGame_Success() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        FailGameReviewCommand command = new FailGameReviewCommand(gameId);

        // Act
        Game result = useCase.failGameReview(command);

        // Assert
        assertThat(result).isNotNull();
        verify(mockGame, times(1)).fail();
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should throw GameReferenceNotFoundException when game doesn't exist")
    void testFailGame_NotFound() {
        // Arrange
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.empty());

        FailGameReviewCommand command = new FailGameReviewCommand(gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.failGameReview(command))
                .isInstanceOf(GameNotFoundException.class)
                .hasMessageContaining("Game not found");
    }

    @Test
    @DisplayName("Should persist failed game review to all save ports")
    void testFailGame_PersistsToAllPorts() {
        // Arrange
        SaveGamePort port1 = mock(SaveGamePort.class);
        SaveGamePort port2 = mock(SaveGamePort.class);
        useCase = new FailGameReviewReviewUseCaseImpl(loadGamePort, List.of(port1, port2));

        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        FailGameReviewCommand command = new FailGameReviewCommand(gameId);

        // Act
        useCase.failGameReview(command);

        // Assert
        verify(port1, times(1)).save(mockGame);
        verify(port2, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should throw InvalidGameStateException when game cannot be failed")
    void testFailGame_InvalidState() {
        // Arrange
        Game mockGame = mock(Game.class);
        doThrow(new InvalidGameStateException("Cannot fail game review: current state is FAILED_REVIEW, expected PENDING"))
                .when(mockGame).fail();
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        FailGameReviewCommand command = new FailGameReviewCommand(gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.failGameReview(command))
                .isInstanceOf(InvalidGameStateException.class)
                .hasMessageContaining("Cannot fail game review");
    }

    @Test
    @DisplayName("Should find game with load port and fail it")
    void testFailGame_LoadAndFail() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        FailGameReviewCommand command = new FailGameReviewCommand(gameId);

        // Act
        Game result = useCase.failGameReview(command);

        // Assert
        assertThat(result).isNotNull();
        verify(loadGamePort, times(1)).loadBy(any(GameId.class));
        verify(mockGame, times(1)).fail();
    }
}
