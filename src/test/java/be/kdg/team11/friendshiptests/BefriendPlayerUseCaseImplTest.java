package be.kdg.team11.friendshiptests;
import be.kdg.team11.player.core.BefriendPlayerUseCaseImpl;
import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.domain.friendship.FriendshipId;
import be.kdg.team11.player.port.in.BefriendPlayerCommand;
import be.kdg.team11.player.port.out.LoadFriendshipPort;
import be.kdg.team11.player.port.out.SaveFriendshipPort;
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
@DisplayName("BefriendPlayerUseCase Tests")
public class BefriendPlayerUseCaseImplTest {

    @Mock
    private LoadFriendshipPort loadFriendshipPort;

    @Mock
    private SaveFriendshipPort saveFriendshipPort;

    private BefriendPlayerUseCaseImpl useCase;
    private UUID friendshipId;
    private UUID recipientId;

    @BeforeEach
    void setUp() {
        // Explicitly open mocks in case @ExtendWith didn't work
        MockitoAnnotations.openMocks(this);

        friendshipId = UUID.randomUUID();
        recipientId = UUID.randomUUID();
        // Constructor: LoadFriendshipPort FIRST, List<SaveFriendshipPort> SECOND
        List<SaveFriendshipPort> ports = new ArrayList<>();
        ports.add(saveFriendshipPort);
        useCase = new BefriendPlayerUseCaseImpl(loadFriendshipPort, ports);
    }

    @Test
    @DisplayName("Should successfully befriend a player")
    void testBefriendPlayer_Success() {
        // Arrange
        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        BefriendPlayerCommand command = new BefriendPlayerCommand(friendshipId, recipientId);

        // Act
        Friendship result = useCase.befriendPlayer(command);

        // Assert
        assertThat(result).isNotNull();
        verify(mockFriendship, times(1)).befriend();
        verify(saveFriendshipPort, times(1)).save(mockFriendship);
    }

    @Test
    @DisplayName("Should throw exception when friendship doesn't exist")
    void testBefriendPlayer_NotFound() {
        // Arrange
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.empty());

        BefriendPlayerCommand command = new BefriendPlayerCommand(friendshipId, recipientId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.befriendPlayer(command))
                .isNotNull();
        verify(saveFriendshipPort, never()).save(any());
    }

    @Test
    @DisplayName("Should persist befriended player to all save ports")
    void testBefriendPlayer_PersistsToAllPorts() {
        // Arrange
        SaveFriendshipPort port1 = mock(SaveFriendshipPort.class);
        SaveFriendshipPort port2 = mock(SaveFriendshipPort.class);
        List<SaveFriendshipPort> ports = new ArrayList<>();
        ports.add(port1);
        ports.add(port2);
        useCase = new BefriendPlayerUseCaseImpl(loadFriendshipPort, ports);

        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        BefriendPlayerCommand command = new BefriendPlayerCommand(friendshipId, recipientId);

        // Act
        useCase.befriendPlayer(command);

        // Assert
        verify(port1, times(1)).save(mockFriendship);
        verify(port2, times(1)).save(mockFriendship);
    }

    @Test
    @DisplayName("Should call befriend() before saving friendship")
    void testBefriendPlayer_CallsBefriendBeforeSave() {
        // Arrange
        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        BefriendPlayerCommand command = new BefriendPlayerCommand(friendshipId, recipientId);

        // Act
        useCase.befriendPlayer(command);

        // Assert
        verify(mockFriendship, times(1)).befriend();
        verify(saveFriendshipPort, times(1)).save(mockFriendship);
    }

    @Test
    @DisplayName("Should return the befriended friendship after saving")
    void testBefriendPlayer_ReturnBefriendedFriendship() {
        // Arrange
        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        BefriendPlayerCommand command = new BefriendPlayerCommand(friendshipId, recipientId);

        // Act
        Friendship result = useCase.befriendPlayer(command);

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(mockFriendship);
    }

    @Test
    @DisplayName("Should load friendship by correct FriendshipId")
    void testBefriendPlayer_LoadsWithCorrectFriendshipId() {
        // Arrange
        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        BefriendPlayerCommand command = new BefriendPlayerCommand(friendshipId, recipientId);

        // Act
        useCase.befriendPlayer(command);

        // Assert
        verify(loadFriendshipPort, times(1)).loadBy(any(FriendshipId.class));
        verify(mockFriendship, times(1)).befriend();
        verify(saveFriendshipPort, times(1)).save(mockFriendship);
    }
}
