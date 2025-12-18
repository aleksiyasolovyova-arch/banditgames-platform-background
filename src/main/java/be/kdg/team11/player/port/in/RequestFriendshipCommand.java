package be.kdg.team11.player.port.in;

import be.kdg.team11.player.domain.friendship.exceptions.InvalidFriendshipException;

import java.util.UUID;

public record RequestFriendshipCommand(
        UUID requesterId,
        UUID recipientId
) {}
