package be.kdg.team11.content.port.in;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameDataException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameUrlException;

import java.util.UUID;

public record ModifyGameUrlsCommand(
        UUID gameId,
        String pictureUrl,
        String gameUrl
) {
    public ModifyGameUrlsCommand {
        if (gameId == null) {
            throw new InvalidGameDataException("Game ID cannot be null");
        }
        if (pictureUrl == null || pictureUrl.isBlank()) {
            throw new InvalidGameUrlException("Picture URL cannot be empty");
        }
        if (gameUrl == null || gameUrl.isBlank()) {
            throw new InvalidGameUrlException("Game URL cannot be empty");
        }
    }
}
