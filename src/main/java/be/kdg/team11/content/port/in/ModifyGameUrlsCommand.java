package be.kdg.team11.content.port.in;

import java.util.UUID;

public record ModifyGameUrlsCommand(
        UUID gameId,
        String pictureUrl,
        String gameUrl
) {
}
