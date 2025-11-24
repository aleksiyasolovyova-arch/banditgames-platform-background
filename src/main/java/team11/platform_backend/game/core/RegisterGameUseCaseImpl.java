package team11.platform_backend.game.core;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import team11.platform_backend.game.domain.game.Game;
import team11.platform_backend.game.domain.game.Rule;
import team11.platform_backend.game.domain.game.RuleCategory;
import team11.platform_backend.game.port.in.RegisterGameCommand;
import team11.platform_backend.game.port.in.RegisterGamePort;
import team11.platform_backend.game.port.out.SaveGamePort;
import team11.platform_backend.sharedkernel.valueobjects.Url;

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
        // 1. Convert pictureUrls to Url value objects
        List<Url> pictureUrls = command.pictureUrls().stream()
                .map(Url::new)
                .toList();

        // 2. Convert RuleCommand to Rule value objects
        List<Rule> rules = command.rules().stream()
                .map(ruleCmd -> new Rule(
                        ruleCmd.ruleName(),
                        ruleCmd.ruleDescription(),
                        ruleCmd.ruleCategories().stream()
                                .map(RuleCategory::valueOf)
                                .toList()
                ))
                .toList();

        // 3. Create Game aggregate
        Game game = new Game(
                command.gameName(),
                command.gameDescription(),
                command.gamePrice(),
                pictureUrls,
                command.gameCreatorName(),
                new Url(command.gameUrl()),
                rules
        );

        // 4. Save to persistence (follows Restaurant pattern)
        saveGamePorts.forEach(saveGamePort -> saveGamePort.save(game));

        return game;
    }

}
