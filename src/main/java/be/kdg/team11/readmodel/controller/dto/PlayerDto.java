package be.kdg.team11.readmodel.controller.dto;

import java.util.UUID;

public record PlayerDto(
        boolean friend,
        UUID friendId,
        String friendUsername,
        String friendPictureUrl
) {
}
