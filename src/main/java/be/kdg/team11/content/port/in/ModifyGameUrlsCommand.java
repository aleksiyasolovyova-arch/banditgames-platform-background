package be.kdg.team11.content.port.in;

import java.util.UUID;

public record ModifyGameUrlsCommand(
        UUID gameId,
        String pictureUrl,
        String gameUrl
) {
    public ModifyGameUrlsCommand {
        // Game ID
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID cannot be null");
        }

        // Picture URLs
        if (pictureUrl == null || pictureUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Picture URL cannot be empty");
        }

        // Game URL
        if (gameUrl == null || gameUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Game URL cannot be empty");
        }
    }
}
