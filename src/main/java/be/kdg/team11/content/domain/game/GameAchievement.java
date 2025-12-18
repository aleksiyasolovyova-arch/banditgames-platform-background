package be.kdg.team11.content.domain.game;

public record GameAchievement(
        String code,
        String description
) {

    public static GameAchievement of(String code, String description) {
        return new GameAchievement(code, description);
    }

}
