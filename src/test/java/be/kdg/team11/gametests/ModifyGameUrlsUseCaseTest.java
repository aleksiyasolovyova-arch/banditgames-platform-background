package be.kdg.team11.gametests;

import be.kdg.team11.content.core.ModifyGameUrlsUseCaseImpl;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.domain.game.exeptions.GameNotFoundException;
import be.kdg.team11.content.port.in.ModifyGameUrlsCommand;
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
@DisplayName("ModifyGameUrlsUseCase Tests")
class ModifyGameUrlsUseCaseTest {

    @Mock
    private LoadGamePort loadGamePort;

    @Mock
    private SaveGamePort saveGamePort;

    private ModifyGameUrlsUseCaseImpl useCase;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        gameId = UUID.randomUUID();
        useCase = new ModifyGameUrlsUseCaseImpl(loadGamePort, List.of(saveGamePort));
    }

    @Test
    @DisplayName("Should successfully modify game URLs")
    void testModifyGameUrls_Success() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        ModifyGameUrlsCommand command = new ModifyGameUrlsCommand(
                gameId,
                "https://example.com/new-picture.png",
                "https://example.com/new-game"
        );

        // Act
        Game result = useCase.modify(command);

        // Assert
        assertThat(result).isNotNull();
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should throw GameReferenceNotFoundException when game doesn't exist")
    void testModifyGameUrls_NotFound() {
        // Arrange
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.empty());

        ModifyGameUrlsCommand command = new ModifyGameUrlsCommand(
                gameId,
                "https://example.com/pic.png",
                "https://example.com/play"
        );

        // Act & Assert
        assertThatThrownBy(() -> useCase.modify(command))
                .isInstanceOf(GameNotFoundException.class)
                .hasMessageContaining("Game not found");
    }

    @Test
    @DisplayName("Should persist modified game to all save ports")
    void testModifyGameUrls_PersistsToAllPorts() {
        // Arrange
        SaveGamePort port1 = mock(SaveGamePort.class);
        SaveGamePort port2 = mock(SaveGamePort.class);
        useCase = new ModifyGameUrlsUseCaseImpl(loadGamePort, List.of(port1, port2));

        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        ModifyGameUrlsCommand command = new ModifyGameUrlsCommand(
                gameId,
                "https://example.com/pic.png",
                "https://example.com/play"
        );

        // Act
        useCase.modify(command);

        // Assert
        verify(port1, times(1)).save(mockGame);
        verify(port2, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should modify game with valid URLs")
    void testModifyGameUrls_ValidUrls() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        String newPicUrl = "https://cdn.example.com/updated-pic.png";
        String newGameUrl = "https://cdn.example.com/updated-game";

        ModifyGameUrlsCommand command = new ModifyGameUrlsCommand(
                gameId,
                newPicUrl,
                newGameUrl
        );

        // Act
        Game result = useCase.modify(command);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should load game using GameId")
    void testModifyGameUrls_LoadsGameById() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        ModifyGameUrlsCommand command = new ModifyGameUrlsCommand(
                gameId,
                "https://example.com/pic.png",
                "https://example.com/play"
        );

        // Act
        useCase.modify(command);

        // Assert
        verify(loadGamePort, times(1)).loadBy(any(GameId.class));
    }
}
