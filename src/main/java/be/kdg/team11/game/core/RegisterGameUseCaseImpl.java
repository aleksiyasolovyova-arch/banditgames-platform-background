package be.kdg.team11.game.core;

import be.kdg.team11.game.domain.Url;
import be.kdg.team11.game.domain.game.GameAchievement;
import be.kdg.team11.game.domain.game.Rule;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import be.kdg.team11.game.domain.game.Game;
import be.kdg.team11.game.port.in.RegisterGameCommand;
import be.kdg.team11.game.port.in.RegisterGamePort;
import be.kdg.team11.game.port.out.SaveGamePort;

import java.util.List;

@Service
@Transactional
public class RegisterGameUseCaseImpl implements RegisterGamePort {

    private final List<SaveGamePort> saveGamePorts;

    public RegisterGameUseCaseImpl(List<SaveGamePort> saveGamePorts) {
        this.saveGamePorts = saveGamePorts;
    }

    @Override
    public Game createGame(RegisterGameCommand command) {
        Url pictureUrl = new Url(command.pictureUrl());
        Url gameUrl = new Url(command.gameUrl());
        List<Rule> rules = command.rules().stream().map(Rule::new).toList();
        List<GameAchievement> achievements = command.achievements().stream().map(achievement -> new GameAchievement(achievement.code(), achievement.description())).toList();

        Game game = Game.register(
                command.name(),
                command.description(),
                command.price(),
                pictureUrl,
                gameUrl,
                command.gameCreatorName(),
                rules,
                achievements
        );

        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));

        return game;
    }

}
