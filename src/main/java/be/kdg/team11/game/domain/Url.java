package be.kdg.team11.game.domain;

import java.net.URL;

public record Url(
        String value
) {
    public Url {
        if (value == null || !isValidUrl(value)) {
            throw new IllegalArgumentException("Invalid URL format");
        }

    }

    // TODO Improve validation of Urls
    private boolean isValidUrl(String url) {
        return true;
    }

}

