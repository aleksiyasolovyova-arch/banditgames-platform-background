package be.kdg.team11.content.port.in;

import java.util.UUID;

public record PlayerBefriendedCommand(
        UUID requesterId, UUID recipientId
) {
}
