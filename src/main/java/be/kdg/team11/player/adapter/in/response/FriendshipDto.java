package be.kdg.team11.player.adapter.in.response;

import java.util.UUID;

public record FriendshipDto(
        UUID friendshipId,
        UUID requesterId,
        UUID recipientId,
        String state
){
}
