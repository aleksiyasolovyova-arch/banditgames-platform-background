package team11.platform_backend.gamelobby.domain.projections;

import java.util.UUID;

public record PlayerId(
        UUID playerId
) {
    private static final String AI_ID = "00000000-0000-0000-0000-000000a1face";

    public static PlayerId ai(){
        return new PlayerId(UUID.fromString(AI_ID));
    }

    public boolean isAI(){
        return playerId.equals(UUID.fromString(AI_ID));
    }
}
