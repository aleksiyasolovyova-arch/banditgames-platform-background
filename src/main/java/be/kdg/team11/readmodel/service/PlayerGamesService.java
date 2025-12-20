package be.kdg.team11.readmodel.service;

import be.kdg.team11.readmodel.controller.response.PlayerGamesDto;

import java.util.List;
import java.util.UUID;

public interface PlayerGamesService {
    List<PlayerGamesDto> getAllForPlayerId(UUID playerID);
}
