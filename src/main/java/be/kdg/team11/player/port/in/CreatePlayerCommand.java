package be.kdg.team11.player.port.in;

public record CreatePlayerCommand(
        String username,
        String pictureUrl
) {
}
