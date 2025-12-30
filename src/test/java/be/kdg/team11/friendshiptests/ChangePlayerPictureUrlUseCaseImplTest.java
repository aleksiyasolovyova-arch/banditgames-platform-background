package be.kdg.team11.friendshiptests;
import be.kdg.team11.player.core.ChangePlayerPictureUrlUseCaseImpl;
import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.ChangePlayerPictureUrlCommand;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ChangePlayerPictureUrlUseCase Tests")
public class ChangePlayerPictureUrlUseCaseImplTest {
    @Mock
    private LoadPlayerPort loadPlayerPort;

    @Mock
    private SavePlayerPort savePlayerPort;

    private ChangePlayerPictureUrlUseCaseImpl useCase;
    private UUID playerId;

    @BeforeEach
    void setUp() {
        // Explicitly open mocks in case @ExtendWith didn't work
        MockitoAnnotations.openMocks(this);

        playerId = UUID.randomUUID();
        // Constructor: LoadPlayerPort FIRST, List<SavePlayerPort> SECOND
        List<SavePlayerPort> ports = new ArrayList<>();
        ports.add(savePlayerPort);
        useCase = new ChangePlayerPictureUrlUseCaseImpl(loadPlayerPort, ports);
    }

    @Test
    @DisplayName("Should successfully change player picture URL")
    void testChangePictureUrl_Success() {
        // Arrange
        String newPictureUrl = "https://example.com/new-picture.jpg";

        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        ChangePlayerPictureUrlCommand command = new ChangePlayerPictureUrlCommand(playerId, newPictureUrl);

        // Act
        Player result = useCase.changePictureUrl(command);

        // Assert
        assertThat(result).isNotNull();
        verify(mockPlayer, times(1)).changePictureUrl(newPictureUrl);
        verify(savePlayerPort, times(1)).save(mockPlayer);
    }

    @Test
    @DisplayName("Should throw exception when player doesn't exist")
    void testChangePictureUrl_NotFound() {
        // Arrange
        String newPictureUrl = "https://example.com/new-picture.jpg";

        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.empty());

        ChangePlayerPictureUrlCommand command = new ChangePlayerPictureUrlCommand(playerId, newPictureUrl);

        // Act & Assert
        assertThatThrownBy(() -> useCase.changePictureUrl(command))
                .isNotNull();
        verify(savePlayerPort, never()).save(any());
    }

    @Test
    @DisplayName("Should persist modified player to all save ports")
    void testChangePictureUrl_PersistsToAllPorts() {
        // Arrange
        String newPictureUrl = "https://example.com/new-picture.jpg";

        SavePlayerPort port1 = mock(SavePlayerPort.class);
        SavePlayerPort port2 = mock(SavePlayerPort.class);
        List<SavePlayerPort> ports = new ArrayList<>();
        ports.add(port1);
        ports.add(port2);
        useCase = new ChangePlayerPictureUrlUseCaseImpl(loadPlayerPort, ports);

        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        ChangePlayerPictureUrlCommand command = new ChangePlayerPictureUrlCommand(playerId, newPictureUrl);

        // Act
        useCase.changePictureUrl(command);

        // Assert
        verify(port1, times(1)).save(mockPlayer);
        verify(port2, times(1)).save(mockPlayer);
    }

    @Test
    @DisplayName("Should call changePictureUrl with correct URL")
    void testChangePictureUrl_CallsWithCorrectUrl() {
        // Arrange
        String newPictureUrl = "https://example.com/avatar.png";

        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        ChangePlayerPictureUrlCommand command = new ChangePlayerPictureUrlCommand(playerId, newPictureUrl);

        // Act
        useCase.changePictureUrl(command);

        // Assert
        verify(mockPlayer, times(1)).changePictureUrl(eq(newPictureUrl));
        verify(savePlayerPort, times(1)).save(mockPlayer);
    }

    @Test
    @DisplayName("Should return the modified player after saving")
    void testChangePictureUrl_ReturnModifiedPlayer() {
        // Arrange
        String newPictureUrl = "https://example.com/new-picture.jpg";

        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        ChangePlayerPictureUrlCommand command = new ChangePlayerPictureUrlCommand(playerId, newPictureUrl);

        // Act
        Player result = useCase.changePictureUrl(command);

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(mockPlayer);
    }

    @Test
    @DisplayName("Should load player by correct PlayerId")
    void testChangePictureUrl_LoadsWithCorrectPlayerId() {
        // Arrange
        String newPictureUrl = "https://example.com/new-picture.jpg";

        Player mockPlayer = mock(Player.class);
        when(loadPlayerPort.loadBy(any(PlayerId.class))).thenReturn(Optional.of(mockPlayer));

        ChangePlayerPictureUrlCommand command = new ChangePlayerPictureUrlCommand(playerId, newPictureUrl);

        // Act
        useCase.changePictureUrl(command);

        // Assert
        verify(loadPlayerPort, times(1)).loadBy(any(PlayerId.class));
        verify(mockPlayer, times(1)).changePictureUrl(newPictureUrl);
        verify(savePlayerPort, times(1)).save(mockPlayer);
    }
}
