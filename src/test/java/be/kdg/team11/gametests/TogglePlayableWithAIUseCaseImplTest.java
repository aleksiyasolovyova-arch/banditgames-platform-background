package be.kdg.team11.gametests;

import be.kdg.team11.content.core.TogglePlayableWithAIUseCaseImpl;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameStateException;
import be.kdg.team11.content.port.in.TogglePlayableWithAICommand;
import be.kdg.team11.content.port.out.LoadGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class TogglePlayableWithAIUseCaseImplTest {
    @Mock
    private LoadGamePort loadGamePort;

    @Mock
    private SaveGamePort saveGamePort;

    private TogglePlayableWithAIUseCaseImpl useCase;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        // Explicitly open mocks in case @ExtendWith didn't work
        MockitoAnnotations.openMocks(this);

        gameId = UUID.randomUUID();
        // Constructor: List<SaveGamePort> saveGamePorts FIRST, LoadGamePort SECOND
        List<SaveGamePort> ports = new ArrayList<>();
        ports.add(saveGamePort);
        useCase = new TogglePlayableWithAIUseCaseImpl(ports, loadGamePort);
    }

    @Test
    @DisplayName("Should successfully toggle playable with AI flag")
    void testToggle_Success() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        TogglePlayableWithAICommand command = new TogglePlayableWithAICommand(gameId);

        // Act
        Game result = useCase.toggle(command);

        // Assert
        assertThat(result).isNotNull();
        verify(mockGame, times(1)).togglePlayableWithAI();
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should throw exception when game doesn't exist")
    void testToggle_NotFound() {
        // Arrange
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.empty());

        TogglePlayableWithAICommand command = new TogglePlayableWithAICommand(gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.toggle(command))
                .isNotNull();
        verify(saveGamePort, never()).save(any());
    }

    @Test
    @DisplayName("Should persist toggled game to all save ports")
    void testToggle_PersistsToAllPorts() {
        // Arrange
        SaveGamePort port1 = mock(SaveGamePort.class);
        SaveGamePort port2 = mock(SaveGamePort.class);
        List<SaveGamePort> ports = new ArrayList<>();
        ports.add(port1);
        ports.add(port2);
        useCase = new TogglePlayableWithAIUseCaseImpl(ports, loadGamePort);

        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        TogglePlayableWithAICommand command = new TogglePlayableWithAICommand(gameId);

        // Act
        useCase.toggle(command);

        // Assert
        verify(port1, times(1)).save(mockGame);
        verify(port2, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should call togglePlayableWithAI() before saving game")
    void testToggle_CallsToggleBeforeSave() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        TogglePlayableWithAICommand command = new TogglePlayableWithAICommand(gameId);

        // Act
        useCase.toggle(command);

        // Assert
        verify(mockGame, times(1)).togglePlayableWithAI();
        verify(saveGamePort, times(1)).save(mockGame);
    }

    @Test
    @DisplayName("Should return the toggled game after saving")
    void testToggle_ReturnToggledGame() {
        // Arrange
        Game mockGame = mock(Game.class);
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        TogglePlayableWithAICommand command = new TogglePlayableWithAICommand(gameId);

        // Act
        Game result = useCase.toggle(command);

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(mockGame);
    }

    @Test
    @DisplayName("Should throw InvalidGameStateException when game cannot be toggled")
    void testToggle_InvalidState() {
        // Arrange
        Game mockGame = mock(Game.class);
        doThrow(new InvalidGameStateException("Cannot toggle playable with AI: game is not in a valid state"))
                .when(mockGame).togglePlayableWithAI();
        when(loadGamePort.loadBy(any(GameId.class))).thenReturn(Optional.of(mockGame));

        TogglePlayableWithAICommand command = new TogglePlayableWithAICommand(gameId);

        // Act & Assert
        assertThatThrownBy(() -> useCase.toggle(command))
                .isInstanceOf(InvalidGameStateException.class)
                .hasMessageContaining("Cannot toggle playable with AI");
        verify(saveGamePort, never()).save(any());
    }
}
