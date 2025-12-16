package be.kdg.team11.achievementtests;

import be.kdg.team11.content.core.CreateAchievementUseCaseImpl;
import be.kdg.team11.content.domain.achievement.Achievement;
import be.kdg.team11.content.port.in.CreateAchievementCommand;
import be.kdg.team11.content.port.out.SaveAchievementPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateAchievementUseCase Tests")
class CreateAchievementUseCaseTest {

    @Mock
    private SaveAchievementPort saveAchievementPort;

    private CreateAchievementUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreateAchievementUseCaseImpl(List.of(saveAchievementPort));
    }

    @Test
    @DisplayName("Should successfully create a new achievement")
    void testCreateAchievement_Success() {
        // Arrange
        CreateAchievementCommand command = new CreateAchievementCommand(
                "First Victory",
                "Achieve your first victory in the game",
                "https://example.com/badges/first-victory.png",
                "WIN_COUNT",
                1
        );

        // Act
        Achievement result = useCase.createAchievement(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("First Victory");
        assertThat(result.getDescription()).isEqualTo("Achieve your first victory in the game");
        assertThat(result.getRequiredValue()).isEqualTo(1);
        assertThat(result.getAchievementId()).isNotNull();

        verify(saveAchievementPort, times(1)).save(any(Achievement.class));
    }

    @Test
    @DisplayName("Should persist achievement to all save ports")
    void testCreateAchievement_PersistsToAllPorts() {
        // Arrange
        SaveAchievementPort port1 = mock(SaveAchievementPort.class);
        SaveAchievementPort port2 = mock(SaveAchievementPort.class);
        useCase = new CreateAchievementUseCaseImpl(List.of(port1, port2));

        CreateAchievementCommand command = new CreateAchievementCommand(
                "Master Player",
                "Achieve 100 victories",
                "https://example.com/badges/master.png",
                "WIN_COUNT",
                100
        );

        // Act
        useCase.createAchievement(command);

        // Assert
        verify(port1, times(1)).save(any(Achievement.class));
        verify(port2, times(1)).save(any(Achievement.class));
    }

    @Test
    @DisplayName("Should create achievement with minimum required value")
    void testCreateAchievement_MinimumRequiredValue() {
        // Arrange
        CreateAchievementCommand command = new CreateAchievementCommand(
                "Welcome",
                "Just for joining",
                "https://example.com/badges/welcome.png",
                "WIN_COUNT",
                0
        );

        // Act
        Achievement result = useCase.createAchievement(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRequiredValue()).isEqualTo(0);
        verify(saveAchievementPort, times(1)).save(any(Achievement.class));
    }

    @Test
    @DisplayName("Should create achievement with maximum required value")
    void testCreateAchievement_MaximumRequiredValue() {
        // Arrange
        CreateAchievementCommand command = new CreateAchievementCommand(
                "Perfect Player",
                "Achieve 100 victories",
                "https://example.com/badges/perfect.png",
                "WIN_COUNT",
                100
        );

        // Act
        Achievement result = useCase.createAchievement(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRequiredValue()).isEqualTo(100);
        verify(saveAchievementPort, times(1)).save(any(Achievement.class));
    }

    @Test
    @DisplayName("Should create achievement with valid achievement type")
    void testCreateAchievement_ValidAchievementType() {
        // Arrange
        CreateAchievementCommand command = new CreateAchievementCommand(
                "Speed Runner",
                "Complete game in under 5 minutes",
                "https://example.com/badges/speedrun.png",
                "WIN_COUNT",
                5
        );

        // Act
        Achievement result = useCase.createAchievement(command);

        // Assert
        assertThat(result).isNotNull();
        verify(saveAchievementPort, times(1)).save(any(Achievement.class));
    }

    @Test
    @DisplayName("Should create achievement with all valid data types")
    void testCreateAchievement_AllValidDataTypes() {
        // Arrange
        CreateAchievementCommand command = new CreateAchievementCommand(
                "Legendary Player",
                "Reach legendary status by winning 50 games with perfect records",
                "https://cdn.example.com/badges/legendary.png",
                "WIN_COUNT",
                50
        );

        // Act
        Achievement result = useCase.createAchievement(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).hasSize(16);
        assertThat(result.getDescription()).hasSize(63);
        verify(saveAchievementPort, times(1)).save(any(Achievement.class));
    }

    @Test
    @DisplayName("Should create multiple achievements independently")
    void testCreateAchievement_MultipleAchievements() {
        // Arrange
        CreateAchievementCommand command1 = new CreateAchievementCommand(
                "Achievement 1",
                "Description 1",
                "https://example.com/badge1.png",
                "WIN_COUNT",
                1
        );

        CreateAchievementCommand command2 = new CreateAchievementCommand(
                "Achievement 2",
                "Description 2",
                "https://example.com/badge2.png",
                "WIN_COUNT",
                10
        );

        // Act
        Achievement result1 = useCase.createAchievement(command1);
        Achievement result2 = useCase.createAchievement(command2);

        // Assert
        assertThat(result1.getAchievementId()).isNotEqualTo(result2.getAchievementId());
        assertThat(result1.getName()).isNotEqualTo(result2.getName());
        verify(saveAchievementPort, times(2)).save(any(Achievement.class));
    }
}
