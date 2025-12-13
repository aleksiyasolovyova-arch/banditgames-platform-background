package be.kdg.team11.content.core;

import be.kdg.team11.content.port.out.DeleteGamePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameId;
import be.kdg.team11.content.port.in.RejectGameCommand;
import be.kdg.team11.content.port.in.RejectGamePort;
import be.kdg.team11.content.port.out.LoadGamePort;

import java.util.Optional;

@Service
@Transactional
public class RejectGameUseCaseImpl implements RejectGamePort{

    private final LoadGamePort loadGamePort;
    private final DeleteGamePort deleteGamePort;

    public RejectGameUseCaseImpl(LoadGamePort loadGamePort,
                                 DeleteGamePort deleteGamePort) {
        this.loadGamePort = loadGamePort;
        this.deleteGamePort = deleteGamePort;
    }

    @Override
    public Game rejectGame(RejectGameCommand command) {
        GameId gameId = GameId.of(command.gameId());
        Game game = loadGamePort.loadBy(gameId)
                .orElseThrow(() -> GameId.notFound(gameId));

        //TODO Ask for feedback about deleting when rejecting
        deleteGamePort.delete(game);
        return game;
    }
}
