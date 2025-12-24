package be.kdg.team11.gametests;
import be.kdg.team11.content.core.PassGameReviewUseCaseImpl;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.domain.game.exeptions.GameNotFoundException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameStateException;
import be.kdg.team11.content.port.in.PassGameReviewCommand;
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
@DisplayName("PassGameReviewUseCase Tests")
class PassGameReviewUseCaseTest {

    @Mock
    private LoadGamePort loadGamePort;

    @Mock
    private SaveGamePort saveGamePort;

    private PassGameReviewUseCaseImpl useCase;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        gameId = UUID.randomUUID();
        useCase = new PassGameReviewUseCaseImpl(loadGamePort, List.of(saveGamePort));
    }

    @Test
    @DisplayName("Should successfully accept a pending game")
    void testPassGame_Success() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        PassGameReviewCommand command = new PassGameReviewCommand(gameId);

        // Act
        Game result = useCase.passGameReview(command);

        // Assert
        assertThat(result).isNotNull();
        verify(mockGame, times(1)).pass();
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should throw GameReferenceNotFoundException when game doesn't exist")
    void testPassGame_NotFound() {
        // Arrange
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.empty());

        PassGameReviewCommand command = new PassGameReviewCommand(gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.passGameReview(command))
                .isInstanceOf(GameNotFoundException.class)
                .hasMessageContaining("Game not found");
    }

    @Test
    @DisplayName("Should persist accepted game to all save ports")
    void testPassGame_PersistsToAllPorts() {
        // Arrange
        SaveGamePort port1 = mock(SaveGamePort.class);
        SaveGamePort port2 = mock(SaveGamePort.class);
        useCase = new PassGameReviewUseCaseImpl(loadGamePort, List.of(port1, port2));

        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        PassGameReviewCommand command = new PassGameReviewCommand(gameId);

        // Act
        useCase.passGameReview(command);

        // Assert
        verify(port1, times(1)).save(mockGame);
        verify(port2, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should accept game with multiple load ports")
    void testPassGame_MultipleLoadPorts() {
        // Arrange
        LoadGamePort port = mock(LoadGamePort.class);
        Game mockGame = mock(Game.class);

        when(port.loadBy(any(GameId.class))).thenReturn(Optional.empty());

        useCase = new PassGameReviewUseCaseImpl(port, List.of(saveGamePort));

        PassGameReviewCommand command = new PassGameReviewCommand(gameId);

        // Act
        Game result = useCase.passGameReview(command);

        // Assert
        assertThat(result).isNotNull();
        verify(mockGame, times(1)).pass();
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should throw InvalidGameStateException when game cannot be accepted")
    void testPassGame_InvalidState() {
        // Arrange
        Game mockGame = mock(Game.class);
        doThrow(new InvalidGameStateException("Cannot pass game review: current state is ACCEPTED, expected PENDING"))
                .when(mockGame).pass();
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        PassGameReviewCommand command = new PassGameReviewCommand(gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.passGameReview(command))
                .isInstanceOf(InvalidGameStateException.class)
                .hasMessageContaining("Cannot pass game review");
    }
}
