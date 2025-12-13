package be.kdg.team11.content.port.in;

import org.springframework.util.Assert;

import java.util.UUID;

public record ModifyGameUrlsCommand(
        UUID gameId,
        String pictureUrl,
        String gameUrl
) {
    public ModifyGameUrlsCommand {
        Assert.notNull(gameId, "Game ID cannot be null");
        Assert.hasText(pictureUrl, "Picture URL cannot be empty");
        Assert.hasText(gameUrl, "Game URL cannot be empty");
    }
}
