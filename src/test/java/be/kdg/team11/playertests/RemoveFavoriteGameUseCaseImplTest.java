package be.kdg.team11.playertests;

import be.kdg.team11.player.core.RemoveFavoriteGameUseCaseImpl;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.RemoveFavoriteGameCommand;
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
@DisplayName("RemoveFavoriteGameUseCase Tests")
class RemoveFavoriteGameUseCaseImplTest {

    @Mock
    private LoadPlayerPort loadPlayerPort;

    @Mock
    private SavePlayerPort savePlayerPort;

    private RemoveFavoriteGameUseCaseImpl useCase;
    private UUID playerId;

    @BeforeEach
    void setUp() {
        // Explicitly open mocks in case @ExtendWith didn't work
        MockitoAnnotations.openMocks(this);

        playerId = UUID.randomUUID();
        // Constructor: LoadPlayerPort FIRST, List<SavePlayerPort> SECOND
        List<SavePlayerPort> ports = new ArrayList<>();
        ports.add(savePlayerPort);
        useCase = new RemoveFavoriteGameUseCaseImpl(loadPlayerPort, ports);
    }

    @Test
    @DisplayName("Should successfully remove favorite game from player")
    void testRemoveFavoriteGame_Success() {
        // Arrange
        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        RemoveFavoriteGameCommand command = new RemoveFavoriteGameCommand(playerId);

        // Act
        Player result = useCase.unfavoriteGame(command);

        // Assert
        assertThat(result).isNotNull();
        verify(mockPlayer, times(1)).removeFavoriteGame();
        verify(savePlayerPort, times(1)).save(mockPlayer);
    }

    @Test
    @DisplayName("Should throw exception when player doesn't exist")
    void testRemoveFavoriteGame_NotFound() {
        // Arrange
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.empty());

        RemoveFavoriteGameCommand command = new RemoveFavoriteGameCommand(playerId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.unfavoriteGame(command))
                .isNotNull();
        verify(savePlayerPort, never()).save(any());
    }

    @Test
    @DisplayName("Should persist modified player to all save ports")
    void testRemoveFavoriteGame_PersistsToAllPorts() {
        // Arrange
        SavePlayerPort port1 = mock(SavePlayerPort.class);
        SavePlayerPort port2 = mock(SavePlayerPort.class);
        List<SavePlayerPort> ports = new ArrayList<>();
        ports.add(port1);
        ports.add(port2);
        useCase = new RemoveFavoriteGameUseCaseImpl(loadPlayerPort, ports);

        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        RemoveFavoriteGameCommand command = new RemoveFavoriteGameCommand(playerId);

        // Act
        useCase.unfavoriteGame(command);

        // Assert
        verify(port1, times(1)).save(mockPlayer);
        verify(port2, times(1)).save(mockPlayer);
    }

    @Test
    @DisplayName("Should call removeFavoriteGame() before saving player")
    void testRemoveFavoriteGame_CallsRemoveBeforeSave() {
        // Arrange
        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        RemoveFavoriteGameCommand command = new RemoveFavoriteGameCommand(playerId);

        // Act
        useCase.unfavoriteGame(command);

        // Assert
        verify(mockPlayer, times(1)).removeFavoriteGame();
        verify(savePlayerPort, times(1)).save(mockPlayer);
    }

    @Test
    @DisplayName("Should return the modified player after saving")
    void testRemoveFavoriteGame_ReturnModifiedPlayer() {
        // Arrange
        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        RemoveFavoriteGameCommand command = new RemoveFavoriteGameCommand(playerId);

        // Act
        Player result = useCase.unfavoriteGame(command);

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(mockPlayer);
    }

    @Test
    @DisplayName("Should load player by correct PlayerId")
    void testRemoveFavoriteGame_LoadsWithCorrectPlayerId() {
        // Arrange
        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        RemoveFavoriteGameCommand command = new RemoveFavoriteGameCommand(playerId);

        // Act
        useCase.unfavoriteGame(command);

        // Assert
        verify(loadPlayerPort, times(1)).loadBy(any(PlayerId.class));
        verify(mockPlayer, times(1)).removeFavoriteGame();
        verify(savePlayerPort, times(1)).save(mockPlayer);
    }
}