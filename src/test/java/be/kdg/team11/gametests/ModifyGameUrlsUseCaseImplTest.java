package be.kdg.team11.gametests;
import be.kdg.team11.content.core.ModifyGameUrlsUseCaseImpl;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.port.in.ModifyGameUrlsCommand;
import be.kdg.team11.content.port.out.LoadGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ModifyGameUrlsUseCase Tests")
public class ModifyGameUrlsUseCaseImplTest {
    @Mock
    private LoadGamePort loadGamePort;

    @Mock
    private SaveGamePort saveGamePort;

    private ModifyGameUrlsUseCaseImpl useCase;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        // Explicitly open mocks in case @ExtendWith didn't work
        MockitoAnnotations.openMocks(this);

        gameId = UUID.randomUUID();
        // Constructor: LoadGamePort FIRST, List<SaveGamePort> SECOND
        List<SaveGamePort> ports = new ArrayList<>();
        ports.add(saveGamePort);
        useCase = new ModifyGameUrlsUseCaseImpl(loadGamePort, ports);
    }

    @Test
    @DisplayName("Should successfully modify game URLs")
    void testModify_Success() {
        // Arrange
        String newPictureUrl = "https://example.com/new-picture.jpg";
        String newGameUrl = "https://example.com/new-game";

        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        ModifyGameUrlsCommand command = new ModifyGameUrlsCommand(gameId, newPictureUrl, newGameUrl);

        // Act
        Game result = useCase.modify(command);

        // Assert
        assertThat(result).isNotNull();
        verify(mockGame, times(1)).modifyUrls(newPictureUrl, newGameUrl);
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should throw exception when game doesn't exist")
    void testModify_NotFound() {
        // Arrange
        String newPictureUrl = "https://example.com/new-picture.jpg";
        String newGameUrl = "https://example.com/new-game";

        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.empty());

        ModifyGameUrlsCommand command = new ModifyGameUrlsCommand(gameId, newPictureUrl, newGameUrl);

        // Act & Assert
        assertThatThrownBy(() -> useCase.modify(command))
                .isNotNull();
        verify(saveGamePort, never()).save(any());
    }

    @Test
    @DisplayName("Should persist modified game to all save ports")
    void testModify_PersistsToAllPorts() {
        // Arrange
        String newPictureUrl = "https://example.com/new-picture.jpg";
        String newGameUrl = "https://example.com/new-game";

        SaveGamePort port1 = mock(SaveGamePort.class);
        SaveGamePort port2 = mock(SaveGamePort.class);
        List<SaveGamePort> ports = new ArrayList<>();
        ports.add(port1);
        ports.add(port2);
        useCase = new ModifyGameUrlsUseCaseImpl(loadGamePort, ports);

        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        ModifyGameUrlsCommand command = new ModifyGameUrlsCommand(gameId, newPictureUrl, newGameUrl);

        // Act
        useCase.modify(command);

        // Assert
        verify(port1, times(1)).save(mockGame);
        verify(port2, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should call modifyUrls with correct parameters")
    void testModify_CallsModifyUrlsWithCorrectParams() {
        // Arrange
        String newPictureUrl = "https://example.com/picture.png";
        String newGameUrl = "https://example.com/game";

        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        ModifyGameUrlsCommand command = new ModifyGameUrlsCommand(gameId, newPictureUrl, newGameUrl);

        // Act
        useCase.modify(command);

        // Assert
        verify(mockGame, times(1)).modifyUrls(eq(newPictureUrl), eq(newGameUrl));
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should return the modified game after saving")
    void testModify_ReturnModifiedGame() {
        // Arrange
        String newPictureUrl = "https://example.com/new-picture.jpg";
        String newGameUrl = "https://example.com/new-game";

        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        ModifyGameUrlsCommand command = new ModifyGameUrlsCommand(gameId, newPictureUrl, newGameUrl);

        // Act
        Game result = useCase.modify(command);

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(mockGame);
    }

    @Test
    @DisplayName("Should load game by correct GameId")
    void testModify_LoadsWithCorrectGameId() {
        // Arrange
        String newPictureUrl = "https://example.com/new-picture.jpg";
        String newGameUrl = "https://example.com/new-game";

        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        ModifyGameUrlsCommand command = new ModifyGameUrlsCommand(gameId, newPictureUrl, newGameUrl);

        // Act
        useCase.modify(command);

        // Assert
        verify(loadGamePort, times(1)).loadBy(any(GameId.class));
        verify(mockGame, times(1)).modifyUrls(newPictureUrl, newGameUrl);
        verify(saveGamePort, times(1)).save(mockGame);
    }
}
