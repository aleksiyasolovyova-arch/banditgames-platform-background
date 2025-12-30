package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.domain.player.Username;
import be.kdg.team11.player.port.in.CreatePlayerCommand;
import be.kdg.team11.player.port.in.CreatePlayerPort;
import be.kdg.team11.player.port.out.LoadGameReferencePort;
import be.kdg.team11.player.port.out.SavePlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CreatePlayerUseCaseImpl implements CreatePlayerPort {
    private final List<SavePlayerPort> savePlayerPorts;

    public CreatePlayerUseCaseImpl(List<SavePlayerPort> savePlayerPorts) {
        this.savePlayerPorts = savePlayerPorts;
    }

    @Override
    public Player create(CreatePlayerCommand command) {
        PlayerId playerId = PlayerId.of(command.playerId());
        Username username = Username.of(command.username());
        Player player = Player.create(
                playerId,
                username
        );

        // Save player through all ports
        savePlayerPorts.forEach(savePlayerPort -> savePlayerPort.save(player));

        return player;
    }
}
