package be.kdg.team11.content.domain;

import be.kdg.team11.content.domain.game.exeptions.InvalidGameUrlException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public record Url(
        String value
) {
    public Url {
        if (value == null || value.isBlank()) {
            throw new InvalidGameUrlException("URL cannot be null or empty");
        }

        if (!isValidUrl(value)) {
            throw new InvalidGameUrlException(
                    "URL format is invalid. Must be a properly formatted URL: " + value
            );
        }
    }

    private boolean isValidUrl(String value) {
        try {
            URL url = URI.create(value).toURL();
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

}

