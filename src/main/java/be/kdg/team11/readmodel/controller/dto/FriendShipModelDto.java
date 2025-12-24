package be.kdg.team11.readmodel.controller.dto;

import java.util.UUID;

public record FriendShipModelDto(
        UUID friendShipId,
        boolean befriended,
        UUID playerId,
        String username,
        String pictureUrl
) {
}
