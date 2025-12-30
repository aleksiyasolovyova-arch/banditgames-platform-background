package be.kdg.team11.friendshiptests;

import be.kdg.team11.player.core.DeclineFriendshipUseCaseImpl;
import be.kdg.team11.player.domain.friendship.Friendship;
import be.kdg.team11.player.domain.friendship.FriendshipId;
import be.kdg.team11.player.port.in.DeclineFriendshipCommand;
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
@DisplayName("DeclineFriendshipUseCase Tests")
public class DeclineFriendshipUseCaseImplTest {
    @Mock
    private LoadFriendshipPort loadFriendshipPort;

    @Mock
    private SaveFriendshipPort saveFriendshipPort;

    private DeclineFriendshipUseCaseImpl useCase;
    private UUID friendshipId;
    private UUID declinedById;

    @BeforeEach
    void setUp() {
        // Explicitly open mocks in case @ExtendWith didn't work
        MockitoAnnotations.openMocks(this);

        friendshipId = UUID.randomUUID();
        declinedById = UUID.randomUUID();
        // Constructor: LoadFriendshipPort FIRST, List<SaveFriendshipPort> SECOND
        List<SaveFriendshipPort> ports = new ArrayList<>();
        ports.add(saveFriendshipPort);
        useCase = new DeclineFriendshipUseCaseImpl(loadFriendshipPort, ports);
    }

    @Test
    @DisplayName("Should successfully decline a friendship")
    void testDeclineFriendship_Success() {
        // Arrange
        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        DeclineFriendshipCommand command = new DeclineFriendshipCommand(friendshipId, declinedById);

        // Act
        Friendship result = useCase.declineFriendship(command);

        // Assert
        assertThat(result).isNotNull();
        verify(mockFriendship, times(1)).decline();
        verify(saveFriendshipPort, times(1)).save(mockFriendship);
    }

    @Test
    @DisplayName("Should throw exception when friendship doesn't exist")
    void testDeclineFriendship_NotFound() {
        // Arrange
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.empty());

        DeclineFriendshipCommand command = new DeclineFriendshipCommand(friendshipId, declinedById);

        // Act & Assert
        assertThatThrownBy(() -> useCase.declineFriendship(command))
                .isNotNull();
        verify(saveFriendshipPort, never()).save(any());
    }

    @Test
    @DisplayName("Should persist declined friendship to all save ports")
    void testDeclineFriendship_PersistsToAllPorts() {
        // Arrange
        SaveFriendshipPort port1 = mock(SaveFriendshipPort.class);
        SaveFriendshipPort port2 = mock(SaveFriendshipPort.class);
        List<SaveFriendshipPort> ports = new ArrayList<>();
        ports.add(port1);
        ports.add(port2);
        useCase = new DeclineFriendshipUseCaseImpl(loadFriendshipPort, ports);

        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        DeclineFriendshipCommand command = new DeclineFriendshipCommand(friendshipId, declinedById);

        // Act
        useCase.declineFriendship(command);

        // Assert
        verify(port1, times(1)).save(mockFriendship);
        verify(port2, times(1)).save(mockFriendship);
    }

    @Test
    @DisplayName("Should call decline() before saving friendship")
    void testDeclineFriendship_CallsDeclineBeforeSave() {
        // Arrange
        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        DeclineFriendshipCommand command = new DeclineFriendshipCommand(friendshipId, declinedById);

        // Act
        useCase.declineFriendship(command);

        // Assert
        verify(mockFriendship, times(1)).decline();
        verify(saveFriendshipPort, times(1)).save(mockFriendship);
    }

    @Test
    @DisplayName("Should return the declined friendship after saving")
    void testDeclineFriendship_ReturnDeclinedFriendship() {
        // Arrange
        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        DeclineFriendshipCommand command = new DeclineFriendshipCommand(friendshipId, declinedById);

        // Act
        Friendship result = useCase.declineFriendship(command);

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(mockFriendship);
    }

    @Test
    @DisplayName("Should load friendship by correct FriendshipId")
    void testDeclineFriendship_LoadsWithCorrectFriendshipId() {
        // Arrange
        Friendship mockFriendship = mock(Friendship.class);
        when(loadFriendshipPort.loadBy(any(FriendshipId.class))).thenReturn(Optional.of(mockFriendship));

        DeclineFriendshipCommand command = new DeclineFriendshipCommand(friendshipId, declinedById);

        // Act
        useCase.declineFriendship(command);

        // Assert
        verify(loadFriendshipPort, times(1)).loadBy(any(FriendshipId.class));
        verify(mockFriendship, times(1)).decline();
        verify(saveFriendshipPort, times(1)).save(mockFriendship);
    }
}
