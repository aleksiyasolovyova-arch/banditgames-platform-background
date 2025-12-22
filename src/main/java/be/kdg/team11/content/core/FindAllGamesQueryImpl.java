package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.port.in.FindAllGamesQueryPort;
import be.kdg.team11.content.port.out.LoadGamesPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FindAllGamesQueryImpl implements FindAllGamesQueryPort {
    private final LoadGamesPort loadGamesPort;

    public FindAllGamesQueryImpl(LoadGamesPort loadGamesPort) {
        this.loadGamesPort = loadGamesPort;
    }

    @Override
    public List<Game> showAll() {
        return loadGamesPort.loadAll();
    }
}
