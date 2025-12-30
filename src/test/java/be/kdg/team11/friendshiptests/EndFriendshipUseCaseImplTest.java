package be.kdg.team11.friendshiptests;


import be.kdg.team11.player.core.EndFriendshipUseCaseImpl;
import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.domain.friendship.FriendshipId;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.EndFriendshipCommand;
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
@DisplayName("EndFriendshipUseCase Tests")
class EndFriendshipUseCaseImplTest {

    @Mock
    private LoadFriendshipPort loadFriendshipPort;

    @Mock
    private SaveFriendshipPort saveFriendshipPort;

    private EndFriendshipUseCaseImpl useCase;
    private UUID friendshipId;
    private UUID initiatedById;

    @BeforeEach
    void setUp() {
        // Explicitly open mocks in case @ExtendWith didn't work
        MockitoAnnotations.openMocks(this);

        friendshipId = UUID.randomUUID();
        initiatedById = UUID.randomUUID();
        // Constructor: LoadFriendshipPort FIRST, List<SaveFriendshipPort> SECOND
        List<SaveFriendshipPort> ports = new ArrayList<>();
        ports.add(saveFriendshipPort);
        useCase = new EndFriendshipUseCaseImpl(loadFriendshipPort, ports);
    }

    @Test
    @DisplayName("Should successfully end a friendship")
    void testEndFriendship_Success() {
        // Arrange
        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        EndFriendshipCommand command = new EndFriendshipCommand(friendshipId, initiatedById);

        // Act
        Friendship result = useCase.endFriendship(command);

        // Assert
        assertThat(result).isNotNull();
        verify(mockFriendship, times(1)).end(any(PlayerId.class));
        verify(saveFriendshipPort, times(1)).save(mockFriendship);
    }

    @Test
    @DisplayName("Should throw exception when friendship doesn't exist")
    void testEndFriendship_NotFound() {
        // Arrange
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.empty());

        EndFriendshipCommand command = new EndFriendshipCommand(friendshipId, initiatedById);

        // Act & Assert
        assertThatThrownBy(() -> useCase.endFriendship(command))
                .isNotNull();
        verify(saveFriendshipPort, never()).save(any());
    }

    @Test
    @DisplayName("Should persist ended friendship to all save ports")
    void testEndFriendship_PersistsToAllPorts() {
        // Arrange
        SaveFriendshipPort port1 = mock(SaveFriendshipPort.class);
        SaveFriendshipPort port2 = mock(SaveFriendshipPort.class);
        List<SaveFriendshipPort> ports = new ArrayList<>();
        ports.add(port1);
        ports.add(port2);
        useCase = new EndFriendshipUseCaseImpl(loadFriendshipPort, ports);

        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        EndFriendshipCommand command = new EndFriendshipCommand(friendshipId, initiatedById);

        // Act
        useCase.endFriendship(command);

        // Assert
        verify(port1, times(1)).save(mockFriendship);
        verify(port2, times(1)).save(mockFriendship);
    }

    @Test
    @DisplayName("Should call end() with initiatedBy PlayerId before saving")
    void testEndFriendship_CallsEndWithPlayerId() {
        // Arrange
        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        EndFriendshipCommand command = new EndFriendshipCommand(friendshipId, initiatedById);

        // Act
        useCase.endFriendship(command);

        // Assert
        verify(mockFriendship, times(1)).end(any(PlayerId.class));
        verify(saveFriendshipPort, times(1)).save(mockFriendship);
    }

    @Test
    @DisplayName("Should return the ended friendship after saving")
    void testEndFriendship_ReturnEndedFriendship() {
        // Arrange
        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        EndFriendshipCommand command = new EndFriendshipCommand(friendshipId, initiatedById);

        // Act
        Friendship result = useCase.endFriendship(command);

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(mockFriendship);
    }

    @Test
    @DisplayName("Should load friendship by correct FriendshipId and end with correct initiatedBy")
    void testEndFriendship_LoadsAndEndsWithCorrectIds() {
        // Arrange
        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        EndFriendshipCommand command = new EndFriendshipCommand(friendshipId, initiatedById);

        // Act
        useCase.endFriendship(command);

        // Assert
        verify(loadFriendshipPort, times(1)).loadBy(any(FriendshipId.class));
        verify(mockFriendship, times(1)).end(any(PlayerId.class));
        verify(saveFriendshipPort, times(1)).save(mockFriendship);
    }
}
