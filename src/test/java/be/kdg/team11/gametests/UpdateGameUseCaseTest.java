package be.kdg.team11.gametests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import be.kdg.team11.content.core.UpdateGameUseCaseImpl;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.domain.game.GameRegistrationState;
import be.kdg.team11.content.port.in.UpdateGameCommand;
import be.kdg.team11.content.port.out.LoadGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;
import be.kdg.team11.content.domain.Url;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateGameUseCaseTest {

    @Mock
    private LoadGamePort loadGamePort;

    @Mock
    private SaveGamePort saveGamePort;

    private UpdateGameUseCaseImpl updateGameUseCase;

    @BeforeEach
    void setUp() {
        updateGameUseCase = new UpdateGameUseCaseImpl(List.of(loadGamePort), List.of(saveGamePort));
    }

    @Test
    void shouldUpdateGameSuccessfully() {
        // Given
        UUID gameUuid = UUID.randomUUID();
        GameId gameId = new GameId(gameUuid);

        // Original game state
        Game existingGame = new Game(
                gameId,
                "Old Name",
                "Old Description",
                BigDecimal.TEN,
                List.of(new Url("http://old.url/pic1")),
                "Creator",
                new Url("http://old.url/game"),
                GameRegistrationState.PENDING,
                Collections.emptyList()
        );

        // Update command
        UpdateGameCommand command = new UpdateGameCommand(
                gameUuid,
                "New Name",
                "New Description",
                BigDecimal.valueOf(20.0),
                List.of("http://new.url/pic1", "http://new.url/pic2"),
                "http://new.url/game"
        );

        when(loadGamePort.loadBy(gameId)).thenReturn(Optional.of(existingGame));

        // When
        Game result = updateGameUseCase.updateGame(command);

        // Then
        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
        verify(saveGamePort).save(gameCaptor.capture());

        Game savedGame = gameCaptor.getValue();

        // Verify updated fields
        assertEquals("New Name", savedGame.getName());
        assertEquals("New Description", savedGame.getDescription());
        assertEquals(BigDecimal.valueOf(20.0), savedGame.getPrice());
        assertEquals("http://new.url/game", savedGame.getGameUrl().value());
        assertEquals(2, savedGame.getPictureUrl().size());
        assertEquals("http://new.url/pic1", savedGame.getPictureUrl().get(0).value());

        // Verify fields that should remain unchanged
        assertEquals(gameId, savedGame.getGameId());
        assertEquals("Creator", savedGame.getGameCreatorName());
        assertEquals(GameRegistrationState.PENDING, savedGame.getGameState());

        // Verify return value matches saved value
        assertEquals(savedGame, result); // Assuming Game equals/hashCode logic or identity if savedGame is returned by service (service returns the new object created)
    }

    @Test
    void shouldThrowException_WhenGameNotFound() {
        // Given
        UUID gameUuid = UUID.randomUUID();
        GameId gameId = new GameId(gameUuid);

        UpdateGameCommand command = new UpdateGameCommand(
                gameUuid,
                "Name",
                "Desc",
                BigDecimal.ONE,
                List.of("url"),
                "gameUrl"
        );

        when(loadGamePort.loadBy(gameId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> updateGameUseCase.updateGame(command));

        assertEquals("Game not found with ID: " + gameUuid, exception.getMessage());
        verify(saveGamePort, never()).save(any());
    }
}