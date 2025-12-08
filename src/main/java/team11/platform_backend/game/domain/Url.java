package team11.platform_backend.game.domain;

import java.net.URL;

public record Url(
        String value
) {
    public Url(String value) {
        if (value == null || !isValidUrl(value)) {
            throw new IllegalArgumentException("Invalid URL format");
        }

        this.value = value;
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

