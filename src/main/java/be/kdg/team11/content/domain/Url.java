package be.kdg.team11.content.domain;


public record Url(
        String value
) {
    public static Url of(String value) {
        return new Url(value);
    }


}

