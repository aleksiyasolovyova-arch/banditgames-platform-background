package be.kdg.team11.gametests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import be.kdg.team11.content.core.RegisterGameUseCaseImpl;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameRegistrationState;
import be.kdg.team11.content.port.in.RegisterGameCommand;
import be.kdg.team11.content.port.out.SaveGamePort;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Use MockitoExtension for JUnit 5 setup
@ExtendWith(MockitoExtension.class)
public class RegisterGameUseCaseTest {

    // The class under test (the primary port implementation)
    private RegisterGameUseCaseImpl registerGameUseCase;

    // Mock dependencies (secondary ports)
    @Mock
    private SaveGamePort persistenceAdapter;
    @Mock
    private SaveGamePort searchIndexAdapter;

    // Command object for testing
    private RegisterGameCommand validCommand;

    @BeforeEach
    void setUp() {
        // Initialize the Use Case with the list of mocked SaveGamePorts
        List<SaveGamePort> saveGamePorts = List.of(persistenceAdapter, searchIndexAdapter);
        registerGameUseCase = new RegisterGameUseCaseImpl(saveGamePorts);

        // Setup a valid command for reuse in tests
        RegisterGameCommand.RuleCommand ruleCommand = new RegisterGameCommand.RuleCommand(
                "Objective",
                "Be the first to score 10 points.",
                List.of(RuleCategory.WINNING.name())
        );

        validCommand = new RegisterGameCommand(
                "Chess Pro",
                "A classic strategy board game.",
                new BigDecimal("9.99"),
                List.of("http://example.com/img1.jpg", "http://example.com/img2.jpg"),
                "GameDev Studio",
                "http://example.com/play/chess",
                List.of(ruleCommand)
        );
    }

    @Test
    void createGame_shouldSuccessfullyCreateAndSaveGame_andCallAllPorts() {
        // Arrange
        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);

        // Stub the save method for both ports to return the captured game object
        when(persistenceAdapter.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(searchIndexAdapter.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Game createdGame = registerGameUseCase.registerGame(validCommand);

        // Assert

        // 1. Verify the core business logic (Creation and Aggregation)
        assertNotNull(createdGame.getGameId(), "Game ID should be generated.");
        assertEquals(validCommand.name(), createdGame.getName());
        assertEquals(validCommand.price(), createdGame.getPrice());
        assertEquals(validCommand.gameUrl(), createdGame.getGameUrl().value());
        assertEquals(GameRegistrationState.PENDING, createdGame.getGameState(), "New game should start in PENDING state.");

        // Check value object conversions
        assertEquals(2, createdGame.getPictureUrl().size());
        assertEquals("http://example.com/img1.jpg", createdGame.getPictureUrl().get(0).value());
        assertEquals(1, createdGame.getRules().size());
        assertEquals(validCommand.rules().get(0).ruleName(), createdGame.getRules().get(0).ruleName());
        assertEquals(RuleCategory.WINNING, createdGame.getRules().get(0).ruleCategories().get(0));

        // 2. Verify interactions with the secondary ports (Restaurant Pattern)

        // Verify that *each* SaveGamePort's save method was called exactly once
        verify(persistenceAdapter, times(1)).save(gameCaptor.capture());
        verify(searchIndexAdapter, times(1)).save(gameCaptor.capture());

        // We can optionally verify that the captured Game objects are identical (same instance)
        // Since the ports were called with 'game' in a forEach loop, they should receive the same instance.
        List<Game> capturedGames = gameCaptor.getAllValues();
        assertSame(capturedGames.get(0), createdGame, "Persistence adapter should save the created instance.");
        assertSame(capturedGames.get(1), createdGame, "Search index adapter should save the created instance.");
    }

    @Test
    void createGame_shouldThrowIllegalArgumentException_whenRuleCategoryIsInvalid() {
        // Arrange
        // Create a command with an invalid RuleCategory string
        RegisterGameCommand.RuleCommand invalidRuleCommand = new RegisterGameCommand.RuleCommand(
                "Bad Rule",
                "This will fail.",
                List.of("INVALID_CATEGORY_NAME")
        );

        RegisterGameCommand invalidCommand = new RegisterGameCommand(
                validCommand.name(),
                validCommand.description(),
                validCommand.price(),
                validCommand.pictureUrls(),
                validCommand.gameCreatorName(),
                validCommand.gameUrl(),
                List.of(invalidRuleCommand) // Use the invalid rule command
        );

        // Act & Assert
        // Expect an IllegalArgumentException when RuleCategory.valueOf() fails
        assertThrows(IllegalArgumentException.class, () -> {
            registerGameUseCase.registerGame(invalidCommand);
        }, "Should throw IllegalArgumentException when RuleCategory enum mapping fails.");

        // Verify that no SaveGamePort was called if the use case failed early
        verify(persistenceAdapter, never()).save(any());
        verify(searchIndexAdapter, never()).save(any());
    }

    // You would typically write more tests for the Game command validation (RegisterGameCommand),
    // but since the validation is in the record's constructor, those tests belong with the command itself.
    // However, we test the Rule value object construction here.
    @Test
    void createGame_shouldThrowIllegalArgumentException_whenRuleValueObjectValidationFails() {
        // Arrange
        // Create a command with an invalid Rule (e.g., empty rule name)
        RegisterGameCommand.RuleCommand invalidRuleCommand = new RegisterGameCommand.RuleCommand(
                "", // Invalid: empty string
                "A description.",
                List.of(RuleCategory.SETUP.name())
        );

        RegisterGameCommand invalidCommand = new RegisterGameCommand(
                validCommand.name(),
                validCommand.description(),
                validCommand.price(),
                validCommand.pictureUrls(),
                validCommand.gameCreatorName(),
                validCommand.gameUrl(),
                List.of(invalidRuleCommand) // Use the invalid rule command
        );

        // Act & Assert
        // The Rule constructor should throw an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            registerGameUseCase.registerGame(invalidCommand);
        }, "Should throw IllegalArgumentException when Rule value object validation fails.");

        // Verify that no SaveGamePort was called
        verify(persistenceAdapter, never()).save(any());
        verify(searchIndexAdapter, never()).save(any());
    }
}