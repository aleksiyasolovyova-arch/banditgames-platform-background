package be.kdg.team11.achievementtests;

import be.kdg.team11.content.core.CreatePlatformAchievementUseCaseImpl;
import be.kdg.team11.content.domain.platformachievement.PlatformAchievement;
import be.kdg.team11.content.port.in.CreatePlatformAchievementCommand;
import be.kdg.team11.content.port.out.SavePlatformAchievementPort;
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
class CreatePlatformAchievementUseCaseTest {

    @Mock
    private SavePlatformAchievementPort savePlatformAchievementPort;

    private CreatePlatformAchievementUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new CreatePlatformAchievementUseCaseImpl(List.of(savePlatformAchievementPort));
    }

    @Test
    @DisplayName("Should successfully create a new achievement")
    void testCreateAchievement_Success() {
        // Arrange
        CreatePlatformAchievementCommand command = new CreatePlatformAchievementCommand(
                "First Victory",
                "Achieve your first victory in the game",
                "https://example.com/badges/first-victory.png",
                "WIN_COUNT",
                1
        );

        // Act
        PlatformAchievement result = useCase.create(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("First Victory");
        assertThat(result.getDescription()).isEqualTo("Achieve your first victory in the game");
        assertThat(result.getRequiredValue()).isEqualTo(1);
        assertThat(result.getAchievementId()).isNotNull();

        verify(savePlatformAchievementPort, times(1)).save(any(PlatformAchievement.class));
    }

    @Test
    @DisplayName("Should persist achievement to all save ports")
    void testCreateAchievement_PersistsToAllPorts() {
        // Arrange
        SavePlatformAchievementPort port1 = mock(SavePlatformAchievementPort.class);
        SavePlatformAchievementPort port2 = mock(SavePlatformAchievementPort.class);
        useCase = new CreatePlatformAchievementUseCaseImpl(List.of(port1, port2));

        CreatePlatformAchievementCommand command = new CreatePlatformAchievementCommand(
                "Master Player",
                "Achieve 100 victories",
                "https://example.com/badges/master.png",
                "WIN_COUNT",
                100
        );

        // Act
        useCase.create(command);

        // Assert
        verify(port1, times(1)).save(any(PlatformAchievement.class));
        verify(port2, times(1)).save(any(PlatformAchievement.class));
    }

    @Test
    @DisplayName("Should create achievement with minimum required value")
    void testCreateAchievement_MinimumRequiredValue() {
        // Arrange
        CreatePlatformAchievementCommand command = new CreatePlatformAchievementCommand(
                "Welcome",
                "Just for joining",
                "https://example.com/badges/welcome.png",
                "WIN_COUNT",
                0
        );

        // Act
        PlatformAchievement result = useCase.create(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRequiredValue()).isEqualTo(0);
        verify(savePlatformAchievementPort, times(1)).save(any(PlatformAchievement.class));
    }

    @Test
    @DisplayName("Should create achievement with maximum required value")
    void testCreateAchievement_MaximumRequiredValue() {
        // Arrange
        CreatePlatformAchievementCommand command = new CreatePlatformAchievementCommand(
                "Perfect Player",
                "Achieve 100 victories",
                "https://example.com/badges/perfect.png",
                "WIN_COUNT",
                100
        );

        // Act
        PlatformAchievement result = useCase.create(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRequiredValue()).isEqualTo(100);
        verify(savePlatformAchievementPort, times(1)).save(any(PlatformAchievement.class));
    }

    @Test
    @DisplayName("Should create achievement with valid achievement type")
    void testCreateAchievement_ValidAchievementType() {
        // Arrange
        CreatePlatformAchievementCommand command = new CreatePlatformAchievementCommand(
                "Speed Runner",
                "Complete game in under 5 minutes",
                "https://example.com/badges/speedrun.png",
                "WIN_COUNT",
                5
        );

        // Act
        PlatformAchievement result = useCase.create(command);

        // Assert
        assertThat(result).isNotNull();
        verify(savePlatformAchievementPort, times(1)).save(any(PlatformAchievement.class));
    }

    @Test
    @DisplayName("Should create achievement with all valid data types")
    void testCreateAchievement_AllValidDataTypes() {
        // Arrange
        CreatePlatformAchievementCommand command = new CreatePlatformAchievementCommand(
                "Legendary Player",
                "Reach legendary status by winning 50 games with perfect records",
                "https://cdn.example.com/badges/legendary.png",
                "WIN_COUNT",
                50
        );

        // Act
        PlatformAchievement result = useCase.create(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).hasSize(16);
        assertThat(result.getDescription()).hasSize(63);
        verify(savePlatformAchievementPort, times(1)).save(any(PlatformAchievement.class));
    }

    @Test
    @DisplayName("Should create multiple gameAchievements independently")
    void testCreateAchievement_MultipleAchievements() {
        // Arrange
        CreatePlatformAchievementCommand command1 = new CreatePlatformAchievementCommand(
                "Achievement 1",
                "Description 1",
                "https://example.com/badge1.png",
                "WIN_COUNT",
                1
        );

        CreatePlatformAchievementCommand command2 = new CreatePlatformAchievementCommand(
                "Achievement 2",
                "Description 2",
                "https://example.com/badge2.png",
                "WIN_COUNT",
                10
        );

        // Act
        PlatformAchievement result1 = useCase.create(command1);
        PlatformAchievement result2 = useCase.create(command2);

        // Assert
        assertThat(result1.getAchievementId()).isNotEqualTo(result2.getAchievementId());
        assertThat(result1.getName()).isNotEqualTo(result2.getName());
        verify(savePlatformAchievementPort, times(2)).save(any(PlatformAchievement.class));
    }
}
