package be.kdg.team11.gametests;
import be.kdg.team11.content.core.AcceptGameUseCaseImpl;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.domain.game.exeptions.GameNotFoundException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameStateException;
import be.kdg.team11.content.port.in.AcceptGameCommand;
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
@DisplayName("AcceptGameUseCase Tests")
class AcceptGameUseCaseTest {

    @Mock
    private LoadGamePort loadGamePort;

    @Mock
    private SaveGamePort saveGamePort;

    private AcceptGameUseCaseImpl useCase;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        gameId = UUID.randomUUID();
        useCase = new AcceptGameUseCaseImpl(List.of(loadGamePort), List.of(saveGamePort));
    }

    @Test
    @DisplayName("Should successfully accept a pending game")
    void testAcceptGame_Success() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        AcceptGameCommand command = new AcceptGameCommand(gameId);

        // Act
        Game result = useCase.acceptGame(command);

        // Assert
        assertThat(result).isNotNull();
        verify(mockGame, times(1)).accept();
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should throw GameNotFoundException when game doesn't exist")
    void testAcceptGame_NotFound() {
        // Arrange
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.empty());

        AcceptGameCommand command = new AcceptGameCommand(gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.acceptGame(command))
                .isInstanceOf(GameNotFoundException.class)
                .hasMessageContaining("Game not found");
    }

    @Test
    @DisplayName("Should persist accepted game to all save ports")
    void testAcceptGame_PersistsToAllPorts() {
        // Arrange
        SaveGamePort port1 = mock(SaveGamePort.class);
        SaveGamePort port2 = mock(SaveGamePort.class);
        useCase = new AcceptGameUseCaseImpl(List.of(loadGamePort), List.of(port1, port2));

        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        AcceptGameCommand command = new AcceptGameCommand(gameId);

        // Act
        useCase.acceptGame(command);

        // Assert
        verify(port1, times(1)).save(mockGame);
        verify(port2, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should accept game with multiple load ports")
    void testAcceptGame_MultipleLoadPorts() {
        // Arrange
        LoadGamePort port1 = mock(LoadGamePort.class);
        LoadGamePort port2 = mock(LoadGamePort.class);
        Game mockGame = mock(Game.class);

        when(port1.loadBy(any(GameId.class))).thenReturn(Optional.empty());
        when(port2.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        useCase = new AcceptGameUseCaseImpl(List.of(port1, port2), List.of(saveGamePort));

        AcceptGameCommand command = new AcceptGameCommand(gameId);

        // Act
        Game result = useCase.acceptGame(command);

        // Assert
        assertThat(result).isNotNull();
        verify(mockGame, times(1)).accept();
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should throw InvalidGameStateException when game cannot be accepted")
    void testAcceptGame_InvalidState() {
        // Arrange
        Game mockGame = mock(Game.class);
        doThrow(new InvalidGameStateException("Cannot accept game: current state is ACCEPTED, expected PENDING"))
                .when(mockGame).accept();
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        AcceptGameCommand command = new AcceptGameCommand(gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.acceptGame(command))
                .isInstanceOf(InvalidGameStateException.class)
                .hasMessageContaining("Cannot accept game");
    }
}
