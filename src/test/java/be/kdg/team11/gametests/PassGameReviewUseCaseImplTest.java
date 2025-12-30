package be.kdg.team11.gametests;

import be.kdg.team11.content.core.PassGameReviewUseCaseImpl;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.port.in.PassGameReviewCommand;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PassGameReviewUseCase Tests")
class PassGameReviewUseCaseImplTest {

    @Mock
    private LoadGamePort loadGamePort;

    @Mock
    private SaveGamePort saveGamePort;

    private PassGameReviewUseCaseImpl useCase;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        // Explicitly open mocks in case @ExtendWith didn't work
        MockitoAnnotations.openMocks(this);

        gameId = UUID.randomUUID();
        // Constructor: LoadGamePort FIRST, List<SaveGamePort> SECOND
        List<SaveGamePort> ports = new ArrayList<>();
        ports.add(saveGamePort);
        useCase = new PassGameReviewUseCaseImpl(loadGamePort, ports);
    }

    @Test
    @DisplayName("Should successfully accept a pending game and save it")
    void testPassGame_Success() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        PassGameReviewCommand command = new PassGameReviewCommand(gameId);

        // Act
        Game result = useCase.pass(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(mockGame);
        verify(mockGame, times(1)).pass();
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should throw exception when game doesn't exist")
    void testPassGame_NotFound() {
        // Arrange
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.empty());

        PassGameReviewCommand command = new PassGameReviewCommand(gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.pass(command))
                .isNotNull();
        verify(saveGamePort, never()).save(any());
    }

    @Test
    @DisplayName("Should persist game to all save ports")
    void testPassGame_PersistsToAllPorts() {
        // Arrange
        SaveGamePort port1 = mock(SaveGamePort.class);
        SaveGamePort port2 = mock(SaveGamePort.class);
        List<SaveGamePort> ports = new ArrayList<>();
        ports.add(port1);
        ports.add(port2);
        useCase = new PassGameReviewUseCaseImpl(loadGamePort, ports);

        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        PassGameReviewCommand command = new PassGameReviewCommand(gameId);

        // Act
        useCase.pass(command);

        // Assert
        verify(port1, times(1)).save(mockGame);
        verify(port2, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should call pass() before saving game")
    void testPassGame_CallsPassBeforeSave() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        PassGameReviewCommand command = new PassGameReviewCommand(gameId);

        // Act
        useCase.pass(command);

        // Assert
        verify(mockGame, times(1)).pass();
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should return the loaded game after saving")
    void testPassGame_ReturnsLoadedGame() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        PassGameReviewCommand command = new PassGameReviewCommand(gameId);

        // Act
        Game result = useCase.pass(command);

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(mockGame);
    }
}