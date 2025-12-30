package be.kdg.team11.gametests;

import be.kdg.team11.content.core.FailGameReviewUseCaseImpl;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FailGameReviewUseCase Tests")
public class FailGameReviewUseCaseImplTest {

    @Mock
    private LoadGamePort loadGamePort;

    @Mock
    private SaveGamePort saveGamePort;

    private FailGameReviewUseCaseImpl useCase;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        gameId = UUID.randomUUID();
        useCase = new FailGameReviewUseCaseImpl(loadGamePort, List.of(saveGamePort));
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
    @DisplayName("Should throw exception when game doesn't exist")
    void testFailGame_NotFound() {
        // Arrange
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.empty());

        FailGameReviewCommand command = new FailGameReviewCommand(gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.failGameReview(command))
                .isNotNull();
        verify(saveGamePort, never()).save(any());
    }

    @Test
    @DisplayName("Should persist failed game to all save ports")
    void testFailGame_PersistsToAllPorts() {
        // Arrange
        SaveGamePort port1 = mock(SaveGamePort.class);
        SaveGamePort port2 = mock(SaveGamePort.class);
        useCase = new FailGameReviewUseCaseImpl(loadGamePort, List.of(port1, port2));

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
    @DisplayName("Should call fail() before saving game")
    void testFailGame_CallsFailBeforeSave() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        FailGameReviewCommand command = new FailGameReviewCommand(gameId);

        // Act
        useCase.failGameReview(command);

        // Assert
        verify(mockGame, times(1)).fail();
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should return the failed game after saving")
    void testFailGame_ReturnsFailedGame() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        FailGameReviewCommand command = new FailGameReviewCommand(gameId);

        // Act
        Game result = useCase.failGameReview(command);

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(mockGame);
    }

    @Test
    @DisplayName("Should throw InvalidGameStateException when game cannot be failed")
    void testFailGame_InvalidState() {
        // Arrange
        Game mockGame = mock(Game.class);
        doThrow(new InvalidGameStateException("Cannot fail game review: current state is FAILED, expected PENDING"))
                .when(mockGame).fail();
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        FailGameReviewCommand command = new FailGameReviewCommand(gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.failGameReview(command))
                .isInstanceOf(InvalidGameStateException.class)
                .hasMessageContaining("Cannot fail game review");
        verify(saveGamePort, never()).save(any());
    }
}
