package be.kdg.team11.content.domain.game;

public record Rule(
        String description
){
    public Rule {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        if (description.isEmpty() || description.length() > 255) {
            throw new IllegalArgumentException("Description must be between 1 and 255 characters");
        }
    }
}
