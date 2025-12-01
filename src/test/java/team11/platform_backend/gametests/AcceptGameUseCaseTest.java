package team11.platform_backend.gametests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team11.platform_backend.game.core.AcceptGameUseCaseImpl;
import team11.platform_backend.game.domain.game.Game;
import team11.platform_backend.game.domain.game.GameId;
import team11.platform_backend.game.domain.game.exeptions.InvalidGameStateException;
import team11.platform_backend.game.port.in.AcceptGameCommand;
import team11.platform_backend.game.port.out.LoadGamePort;
import team11.platform_backend.game.port.out.SaveGamePort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcceptGameUseCaseTest {

    @Mock
    private LoadGamePort loadGamePort;

    @Mock
    private SaveGamePort saveGamePort;

    @Mock
    private Game game;

    private AcceptGameUseCaseImpl acceptGameUseCase;

    @BeforeEach
    void setUp() {
        // The UseCase takes lists of ports, so we wrap our mocks in lists
        acceptGameUseCase = new AcceptGameUseCaseImpl(List.of(loadGamePort), List.of(saveGamePort));
    }

    @Test
    void shouldAcceptPendingGame() {
        // Given
        UUID gameUuid = UUID.randomUUID();
        AcceptGameCommand command = new AcceptGameCommand(gameUuid);
        GameId gameId = new GameId(gameUuid);

        when(loadGamePort.findById(gameId)).thenReturn(Optional.of(game));

        // When
        acceptGameUseCase.acceptGame(command);

        // Then
        verify(loadGamePort).findById(gameId);
        verify(game).acceptGame();
        verify(saveGamePort).save(game);
    }

    @Test
    void shouldThrowException_WhenGameNotFound() {
        // Given
        UUID gameUuid = UUID.randomUUID();
        AcceptGameCommand command = new AcceptGameCommand(gameUuid);
        GameId gameId = new GameId(gameUuid);

        when(loadGamePort.findById(gameId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> acceptGameUseCase.acceptGame(command));
        verify(game, never()).acceptGame();
        verify(saveGamePort, never()).save(any());
    }

    @Test
    void shouldPropagateException_WhenGameIsNotPending() {
        // Given
        UUID gameUuid = UUID.randomUUID();
        AcceptGameCommand command = new AcceptGameCommand(gameUuid);
        GameId gameId = new GameId(gameUuid);

        when(loadGamePort.findById(gameId)).thenReturn(Optional.of(game));

        // Simulate the domain logic throwing an exception (e.g., if state is not PENDING)
        doThrow(new InvalidGameStateException("Cannot accept game: current state is ACCEPTED, expected PENDING"))
                .when(game).acceptGame();

        // When & Then
        assertThrows(InvalidGameStateException.class, () -> acceptGameUseCase.acceptGame(command));
        verify(saveGamePort, never()).save(any());
    }
}