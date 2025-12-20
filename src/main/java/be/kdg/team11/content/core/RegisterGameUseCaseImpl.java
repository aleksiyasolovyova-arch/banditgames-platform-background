package be.kdg.team11.content.core;

import be.kdg.team11.content.domain.game.Game;
import be.kdg.team11.content.domain.game.GameAchievement;
import be.kdg.team11.content.domain.game.Rule;
import be.kdg.team11.content.port.in.RegisterGameCommand;
import be.kdg.team11.content.port.in.RegisterGamePort;
import be.kdg.team11.content.port.out.SaveGamePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RegisterGameUseCaseImpl implements RegisterGamePort {

    private final List<SaveGamePort> saveGamePorts;

    public RegisterGameUseCaseImpl(List<SaveGamePort> saveGamePorts) {
        this.saveGamePorts = saveGamePorts;
    }

    @Override
    public Game register(RegisterGameCommand command) {
        List<Rule> rules = command.rules().stream()
                .map(Rule::of)
                .toList();
        List<GameAchievement> achievements = command.achievements().stream()
                .map(achievement -> new GameAchievement(achievement.code(), achievement.description()))
                .toList();
        Game game = Game.register(
                command.name(),
                command.description(),
                command.price(),
                command.pictureUrl(),
                command.gameUrl(),
                command.gameCreatorName(),
                rules,
                achievements,
                command.playableWithAI()
        );

        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));

        return game;
    }

}
