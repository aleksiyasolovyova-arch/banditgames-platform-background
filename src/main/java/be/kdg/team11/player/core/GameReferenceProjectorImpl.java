package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.in.GameReferenceCommand;
import be.kdg.team11.player.port.in.GameReferenceProjector;
import be.kdg.team11.player.port.out.GameReferenceExistsPort;
import be.kdg.team11.player.port.out.SaveGameReferencePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GameReferenceProjectorImpl implements GameReferenceProjector {
    private final SaveGameReferencePort saveGameReferencePort;
    private final GameReferenceExistsPort gameReferenceExistsPort;

    public GameReferenceProjectorImpl(SaveGameReferencePort saveGameReferencePort, GameReferenceExistsPort gameReferenceExistsPort) {
        this.saveGameReferencePort = saveGameReferencePort;
        this.gameReferenceExistsPort = gameReferenceExistsPort;
    }

    @Override
    public void project(GameReferenceCommand gameReferenceCommand) {
        GameReference gameReference = GameReference.of(gameReferenceCommand.gameId(), gameReferenceCommand.gameUrl());
        if (gameReferenceExistsPort.exists(gameReference)) {
            throw GameReference.alreadyExists(gameReference.gameId());
        }
        saveGameReferencePort.save(gameReference);
    }
}
