package be.kdg.team11.player.domain.player;

import be.kdg.team11.player.domain.player.exceptions.PlayerNotFoundException;

public record Username(String username) {
    public static Username of (String username) {return new Username(username);}
    public static PlayerNotFoundException notFound (String username){
        return new PlayerNotFoundException(
                String.format("Player not found with username: %s", username)
        );
    }
}
