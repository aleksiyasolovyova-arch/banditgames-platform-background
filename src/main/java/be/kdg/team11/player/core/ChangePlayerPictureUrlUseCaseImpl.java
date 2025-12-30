package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.player.Player;
import be.kdg.team11.player.domain.player.PlayerId;
import be.kdg.team11.player.port.in.ChangePlayerPictureUrlCommand;
import be.kdg.team11.player.port.in.ChangePlayerPictureUrlPort;
import be.kdg.team11.player.port.out.LoadPlayerPort;
import be.kdg.team11.player.port.out.SavePlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ChangePlayerPictureUrlUseCaseImpl implements ChangePlayerPictureUrlPort {
    private final LoadPlayerPort loadPlayerPort;
    private final List<SavePlayerPort> savePlayerPorts;

    public ChangePlayerPictureUrlUseCaseImpl(
            LoadPlayerPort loadPlayerPort, List<SavePlayerPort> savePlayerPorts) {
        this.loadPlayerPort = loadPlayerPort;
        this.savePlayerPorts = savePlayerPorts;
    }

    @Override
    public Player changePictureUrl(ChangePlayerPictureUrlCommand command) {
        Player player = loadPlayerPort.loadBy(PlayerId.of(command.playerId())).orElseThrow(() -> PlayerId.notFound(command.playerId()));
        player.changePictureUrl(command.pictureUrl());
        savePlayerPorts.forEach(savePlayerPort -> {
            savePlayerPort.save(player);
        });
        return player;
    }
}
