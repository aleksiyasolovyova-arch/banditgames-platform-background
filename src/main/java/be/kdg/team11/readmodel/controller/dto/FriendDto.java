package be.kdg.team11.readmodel.controller.dto;

import java.util.UUID;

public record FriendDto(
        UUID playerId,
        String username,
        String pictureUrl
) {
}
