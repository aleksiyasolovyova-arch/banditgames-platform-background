package team11.platform_backend.game.port.in;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record UpdateGameCommand(
        UUID gameId,
        String gameName,
        String gameDescription,
        BigDecimal gamePrice,
        List<String> pictureUrls,
        String gameUrl,
        String aiPlayerUrl
) {
    public UpdateGameCommand {
        // Game ID
        if (gameId == null) {
            throw new IllegalArgumentException("Game ID cannot be null");
        }

        // Game Name
        if (gameName == null || gameName.trim().isEmpty()) {
            throw new IllegalArgumentException("Game name cannot be empty");
        }

        gameName = gameName.trim();
        if (gameName.length() > 100) {
            throw new IllegalArgumentException("Game name cannot exceed 100 characters");
        }

        // Game Description
        if (gameDescription == null || gameDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Game description cannot be empty");
        }

        if (gameDescription.length() > 500) {
            throw new IllegalArgumentException("Game description cannot exceed 500 characters");
        }

        // Game Price
        if (gamePrice == null) {
            throw new IllegalArgumentException("Game price cannot be null");
        }

        if (gamePrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Game price cannot be negative");
        }

        // Picture URLs
        if (pictureUrls == null || pictureUrls.isEmpty()) {
            throw new IllegalArgumentException("At least one picture URL must be provided");
        }

        for (String url : pictureUrls) {
            if (url == null || url.trim().isEmpty()) {
                throw new IllegalArgumentException("Picture URL cannot be empty");
            }
        }

        // Game URL
        if (gameUrl == null || gameUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("Game URL cannot be empty");
        }

    }
}
