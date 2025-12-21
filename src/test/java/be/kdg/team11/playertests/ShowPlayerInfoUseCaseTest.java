package be.kdg.team11.playertests;

import be.kdg.team11.player.core.ShowPlayerInfoUseCaseImpl;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.ShowPlayerInfoCommand;
import be.kdg.team11.player.port.out.LoadPlayerPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ShowPlayerInfoUseCase Tests")
class ShowPlayerInfoUseCaseTest {

    @Mock
    private LoadPlayerPort loadPlayerPort;

    private ShowPlayerInfoUseCaseImpl useCase;

    private UUID playerId;

    @BeforeEach
    void setUp() {
        playerId = UUID.randomUUID();
        useCase = new ShowPlayerInfoUseCaseImpl(loadPlayerPort);
    }

    @Test
    @DisplayName("Should successfully retrieve player info when player exists")
    void testShowPlayerInfo_Success() {
        // Arrange
        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));
        ShowPlayerInfoCommand command = new ShowPlayerInfoCommand(playerId);

        // Act
        Player result = useCase.showInfo(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(mockPlayer);
        verify(loadPlayerPort, times(1)).loadBy(any(PlayerId.class));
    }

    @Test
    @DisplayName("Should throw PlayerNotFoundException when player doesn't exist")
    void testShowPlayerInfo_NotFound() {
        // Arrange
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.empty());
        ShowPlayerInfoCommand command = new ShowPlayerInfoCommand(playerId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.showInfo(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("not found");
    }

    @Test
    @DisplayName("Should load player with correct PlayerId")
    void testShowPlayerInfo_LoadsWithCorrectId() {
        // Arrange
        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));
        ShowPlayerInfoCommand command = new ShowPlayerInfoCommand(playerId);

        // Act
        useCase.showInfo(command);

        // Assert
        verify(loadPlayerPort, times(1)).loadBy(any(PlayerId.class));
    }

    @Test
    @DisplayName("Should return the loaded player object")
    void testShowPlayerInfo_ReturnsLoadedPlayer() {
        // Arrange
        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));
        ShowPlayerInfoCommand command = new ShowPlayerInfoCommand(playerId);

        // Act
        Player result = useCase.showInfo(command);

        // Assert
        assertThat(result).isSameAs(mockPlayer);
    }

    @Test
    @DisplayName("Should handle empty Optional from load port")
    void testShowPlayerInfo_EmptyOptional() {
        // Arrange
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.empty());
        ShowPlayerInfoCommand command = new ShowPlayerInfoCommand(playerId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.showInfo(command))
                .isInstanceOf(RuntimeException.class);
    }
}

