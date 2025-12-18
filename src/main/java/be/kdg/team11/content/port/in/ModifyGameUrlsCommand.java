package be.kdg.team11.content.port.in;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameDataException;
import be.kdg.team11.content.domain.game.exeptions.InvalidGameUrlException;

import java.util.UUID;

public record ModifyGameUrlsCommand(
        UUID gameId,
        String pictureUrl,
        String gameUrl
) {}
