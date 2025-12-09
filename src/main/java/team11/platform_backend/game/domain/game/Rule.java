package team11.platform_backend.game.domain.game;

import java.util.ArrayList;
import java.util.List;

public record Rule(
        String description
){
    public Rule(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        } if (description.isEmpty() || description.length() > 255) {
            throw new IllegalArgumentException("Description must be between 1 and 255 characters");
        }
        this.description = description;
    }
}
