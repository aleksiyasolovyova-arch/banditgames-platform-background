package be.kdg.team11.gametests;

import be.kdg.team11.content.core.RegisterGameUseCaseImpl;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.port.in.RegisterGameCommand;
import be.kdg.team11.content.port.out.SaveGamePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RegisterGameUseCase Tests")
class RegisterGameUseCaseTest {

    @Mock
    private SaveGamePort saveGamePort;

    private RegisterGameUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new RegisterGameUseCaseImpl(List.of(saveGamePort));
    }

    @Test
    @DisplayName("Should successfully register a new game")
    void testRegisterGame_Success() {
        // Arrange
        RegisterGameCommand command = new RegisterGameCommand(
                "Chess Master",
                "A classic strategy board game",
                new BigDecimal("19.99"),
                "https://example.com/chess.png",
                "https://example.com/play/chess",
                "Chess Experts Inc.",
                List.of("Rule 1", "Rule 2"),
                List.of(
                        new RegisterGameCommand.GameAchievementCommand("FIRST_MOVE", "Make your first move"),
                        new RegisterGameCommand.GameAchievementCommand("CHECKMATE", "Deliver checkmate")
                ),
                true
        );

        // Act
        Game result = useCase.register(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Chess Master");
        assertThat(result.getDescription()).isEqualTo("A classic strategy board game");
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("19.99"));
        assertThat(result.getGameCreatorName()).isEqualTo("Chess Experts Inc.");
        assertThat(result.getRules()).hasSize(2);
        assertThat(result.getAchievements()).hasSize(2);
        assertThat(result.getGameId()).isNotNull();

        verify(saveGamePort, times(1)).save(any(Game.class));
    }

    @Test
    @DisplayName("Should persist game to all save ports")
    void testRegisterGame_PersistsToAllPorts() {
        // Arrange
        SaveGamePort port1 = mock(SaveGamePort.class);
        SaveGamePort port2 = mock(SaveGamePort.class);
        useCase = new RegisterGameUseCaseImpl(List.of(port1, port2));

        RegisterGameCommand command = new RegisterGameCommand(
                "Game Name",
                "Game Description",
                BigDecimal.TEN,
                "https://example.com/pic.png",
                "https://example.com/play",
                "Creator",
                List.of("Rule 1"),
                List.of(new RegisterGameCommand.GameAchievementCommand("ACH", "Description")),
                true
        );

        // Act
        useCase.register(command);

        // Assert
        verify(port1, times(1)).save(any(Game.class));
        verify(port2, times(1)).save(any(Game.class));
    }

    @Test
    @DisplayName("Should register game with minimum data")
    void testRegisterGame_MinimumData() {
        // Arrange
        RegisterGameCommand command = new RegisterGameCommand(
                "A",
                "D",
                BigDecimal.ZERO,
                "https://example.com/pic.png",
                "https://example.com/play",
                "C",
                List.of("Rule 1"),
                List.of(new RegisterGameCommand.GameAchievementCommand("A", "D")),
                true
        );

        // Act
        Game result = useCase.register(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("A");
        verify(saveGamePort, times(1)).save(any(Game.class));
    }

    @Test
    @DisplayName("Should register game with multiple rules")
    void testRegisterGame_MultipleRules() {
        // Arrange
        List<String> rules = List.of(
                "Rule 1: Players move clockwise",
                "Rule 2: Collect 4 pieces to win",
                "Rule 3: Cannot move diagonally"
        );

        RegisterGameCommand command = new RegisterGameCommand(
                "Board Game",
                "Description",
                new BigDecimal("29.99"),
                "https://example.com/pic.png",
                "https://example.com/play",
                "Creator",
                rules,
                List.of(new RegisterGameCommand.GameAchievementCommand("ACH", "Desc")),
                true
        );

        // Act
        Game result = useCase.register(command);

        // Assert
        assertThat(result.getRules()).hasSize(3);
        verify(saveGamePort, times(1)).save(any(Game.class));
    }

    @Test
    @DisplayName("Should register game with multiple achievements")
    void testRegisterGame_MultipleAchievements() {
        // Arrange
        List<RegisterGameCommand.GameAchievementCommand> achievements = List.of(
                new RegisterGameCommand.GameAchievementCommand("FIRST_WIN", "Win your first game"),
                new RegisterGameCommand.GameAchievementCommand("SPEED_RUN", "Win in under 5 minutes"),
                new RegisterGameCommand.GameAchievementCommand("PERFECT", "Win without losing a piece")
        );

        RegisterGameCommand command = new RegisterGameCommand(
                "Game",
                "Description",
                BigDecimal.TEN,
                "https://example.com/pic.png",
                "https://example.com/play",
                "Creator",
                List.of("Rule 1"),
                achievements,
                true
        );

        // Act
        Game result = useCase.register(command);

        // Assert
        assertThat(result.getAchievements()).hasSize(3);
        verify(saveGamePort, times(1)).save(any(Game.class));
    }
}
