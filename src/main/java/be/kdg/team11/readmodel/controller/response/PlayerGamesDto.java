package be.kdg.team11.readmodel.controller.response;

import java.util.List;
import java.util.UUID;

public record PlayerGamesDto(
        UUID gameId,
        String name,
        String description,
        String pictureUrl,
        String gameUrl,
        String gameCreatorName,
        List<String> rules,
        boolean isFavourite,
        boolean playableWithAi
) {}
