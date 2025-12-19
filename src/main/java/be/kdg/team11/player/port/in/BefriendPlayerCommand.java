package be.kdg.team11.player.port.in;

import java.util.UUID;

public record BefriendPlayerCommand(
        UUID friendshipId,
        UUID recipientId
) {}
