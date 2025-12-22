package be.kdg.team11.readmodel.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class GameModelAchievementEmbeddable {

    @Column(nullable = false, length = 100)
    private String code;

    @Column(nullable = false, length = 255)
    private String description;

    public GameModelAchievementEmbeddable() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
