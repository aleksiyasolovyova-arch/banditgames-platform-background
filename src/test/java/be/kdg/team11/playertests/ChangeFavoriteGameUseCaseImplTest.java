package be.kdg.team11.playertests;
import be.kdg.team11.player.core.ChangeFavoriteGameUseCaseImpl;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.in.ChangeFavoriteGameCommand;
import be.kdg.team11.player.port.out.LoadGameReferencePort;
import be.kdg.team11.player.port.out.LoadPlayerPort;
import be.kdg.team11.player.port.out.SavePlayerPort;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ChangeFavoriteGameUseCase Tests")
public class ChangeFavoriteGameUseCaseImplTest {
    @Mock
    private LoadPlayerPort loadPlayerPort;

    @Mock
    private LoadGameReferencePort loadGameReferencePort;

    @Mock
    private SavePlayerPort savePlayerPort;

    private ChangeFavoriteGameUseCaseImpl useCase;
    private UUID playerId;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        // Explicitly open mocks in case @ExtendWith didn't work
        MockitoAnnotations.openMocks(this);

        playerId = UUID.randomUUID();
        gameId = UUID.randomUUID();

        // Constructor: LoadPlayerPort, List<SavePlayerPort>, LoadGameReferencePort
        List<SavePlayerPort> ports = new ArrayList<>();
        ports.add(savePlayerPort);
        useCase = new ChangeFavoriteGameUseCaseImpl(loadPlayerPort, ports, loadGameReferencePort);
    }

    @Test
    @DisplayName("Should successfully change favorite game")
    void testFavoriteGame_Success() {
        // Arrange
        GameReference mockGameReference = mock(GameReference.class);
        when(mockGameReference.gameId()).thenReturn(gameId);
        when(mockGameReference.gameUrl()).thenReturn("https://example.com/game");

        Player mockPlayer = mock(Player.class);

        when(loadGameReferencePort.loadBy(gameId)).thenReturn(Optional.of(mockGameReference));
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        ChangeFavoriteGameCommand command = new ChangeFavoriteGameCommand(playerId, gameId);

        // Act
        Player result = useCase.favoriteGame(command);

        // Assert
        assertThat(result).isNotNull();
        verify(loadGameReferencePort, times(1)).loadBy(gameId);
        verify(loadPlayerPort, times(1)).loadBy(any(PlayerId.class));
        verify(mockPlayer, times(1)).changeFavoriteGame(any(GameReference.class));
        verify(savePlayerPort, times(1)).save(mockPlayer);
    }

    @Test
    @DisplayName("Should throw exception when game doesn't exist")
    void testFavoriteGame_GameNotFound() {
        // Arrange
        when(loadGameReferencePort.loadBy(gameId)).thenReturn(Optional.empty());

        ChangeFavoriteGameCommand command = new ChangeFavoriteGameCommand(playerId, gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.favoriteGame(command))
                .isNotNull();
        verify(savePlayerPort, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when player doesn't exist")
    void testFavoriteGame_PlayerNotFound() {
        // Arrange
        GameReference mockGameReference = mock(GameReference.class);
        when(mockGameReference.gameId()).thenReturn(gameId);
        when(mockGameReference.gameUrl()).thenReturn("https://example.com/game");

        when(loadGameReferencePort.loadBy(gameId)).thenReturn(Optional.of(mockGameReference));
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.empty());

        ChangeFavoriteGameCommand command = new ChangeFavoriteGameCommand(playerId, gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.favoriteGame(command))
                .isNotNull();
        verify(savePlayerPort, never()).save(any());
    }

    @Test
    @DisplayName("Should persist modified player to all save ports")
    void testFavoriteGame_PersistsToAllPorts() {
        // Arrange
        SavePlayerPort port1 = mock(SavePlayerPort.class);
        SavePlayerPort port2 = mock(SavePlayerPort.class);
        List<SavePlayerPort> ports = new ArrayList<>();
        ports.add(port1);
        ports.add(port2);
        useCase = new ChangeFavoriteGameUseCaseImpl(loadPlayerPort, ports, loadGameReferencePort);

        GameReference mockGameReference = mock(GameReference.class);
        when(mockGameReference.gameId()).thenReturn(gameId);
        when(mockGameReference.gameUrl()).thenReturn("https://example.com/game");

        Player mockPlayer = mock(Player.class);

        when(loadGameReferencePort.loadBy(gameId)).thenReturn(Optional.of(mockGameReference));
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        ChangeFavoriteGameCommand command = new ChangeFavoriteGameCommand(playerId, gameId);

        // Act
        useCase.favoriteGame(command);

        // Assert
        verify(port1, times(1)).save(mockPlayer);
        verify(port2, times(1)).save(mockPlayer);
    }

    @Test
    @DisplayName("Should call changeFavoriteGame with GameReference")
    void testFavoriteGame_CallsChangeFavoriteGameWithCorrectRef() {
        // Arrange
        GameReference mockGameReference = mock(GameReference.class);
        when(mockGameReference.gameId()).thenReturn(gameId);
        when(mockGameReference.gameUrl()).thenReturn("https://example.com/game");

        Player mockPlayer = mock(Player.class);

        when(loadGameReferencePort.loadBy(gameId)).thenReturn(Optional.of(mockGameReference));
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        ChangeFavoriteGameCommand command = new ChangeFavoriteGameCommand(playerId, gameId);

        // Act
        useCase.favoriteGame(command);

        // Assert
        verify(mockPlayer, times(1)).changeFavoriteGame(any(GameReference.class));
        verify(savePlayerPort, times(1)).save(mockPlayer);
    }

    @Test
    @DisplayName("Should return the modified player after saving")
    void testFavoriteGame_ReturnModifiedPlayer() {
        // Arrange
        GameReference mockGameReference = mock(GameReference.class);
        when(mockGameReference.gameId()).thenReturn(gameId);
        when(mockGameReference.gameUrl()).thenReturn("https://example.com/game");

        Player mockPlayer = mock(Player.class);

        when(loadGameReferencePort.loadBy(gameId)).thenReturn(Optional.of(mockGameReference));
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        ChangeFavoriteGameCommand command = new ChangeFavoriteGameCommand(playerId, gameId);

        // Act
        Player result = useCase.favoriteGame(command);

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(mockPlayer);
    }

    @Test
    @DisplayName("Should load game reference before loading player")
    void testFavoriteGame_LoadGameBeforePlayer() {
        // Arrange
        GameReference mockGameReference = mock(GameReference.class);
        when(mockGameReference.gameId()).thenReturn(gameId);
        when(mockGameReference.gameUrl()).thenReturn("https://example.com/game");

        Player mockPlayer = mock(Player.class);

        when(loadGameReferencePort.loadBy(gameId)).thenReturn(Optional.of(mockGameReference));
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        ChangeFavoriteGameCommand command = new ChangeFavoriteGameCommand(playerId, gameId);

        // Act
        useCase.favoriteGame(command);

        // Assert
        verify(loadGameReferencePort, times(1)).loadBy(gameId);
        verify(loadPlayerPort, times(1)).loadBy(any(PlayerId.class));
        verify(mockPlayer, times(1)).changeFavoriteGame(any(GameReference.class));
        verify(savePlayerPort, times(1)).save(mockPlayer);
    }
}
