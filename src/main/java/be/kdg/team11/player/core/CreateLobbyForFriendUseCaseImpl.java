package be.kdg.team11.player.core;

import be.kdg.team11.player.domain.friendship.FriendshipId;
import be.kdg.team11.player.domain.lobby.Lobby;
import be.kdg.team11.player.domain.projections.GameReference;
import be.kdg.team11.player.port.in.CreateLobbyForFriendCommand;
import be.kdg.team11.player.port.in.CreateLobbyForFriendPort;
import be.kdg.team11.player.port.out.FriendshipExistsPort;
import be.kdg.team11.player.port.out.LoadGameReferencePort;
import be.kdg.team11.player.port.out.SaveLobbyPort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CreateLobbyForFriendUseCaseImpl implements CreateLobbyForFriendPort {
    private final FriendshipExistsPort friendshipExistsPort;
    private final LoadGameReferencePort loadGameReferencePort;
    private final List<SaveLobbyPort> saveGameLobbyPorts;

    public CreateLobbyForFriendUseCaseImpl(FriendshipExistsPort friendshipExistsPort, LoadGameReferencePort loadGameReferencePort, List<SaveLobbyPort> saveGameLobbyPorts) {
        this.friendshipExistsPort = friendshipExistsPort;
        this.loadGameReferencePort = loadGameReferencePort;
        this.saveGameLobbyPorts = saveGameLobbyPorts;
    }

    @Override
    public Lobby create(CreateLobbyForFriendCommand command) {
        if (!friendshipExistsPort.exists(Pair.of(command.playerId(), command.friendId()))) {
            throw FriendshipId.notFound();
        }
        GameReference gameReference = loadGameReferencePort.loadBy(command.gameId()).orElseThrow(() -> GameReference.notFound(command.gameId()));
        Lobby lobby = Lobby.createForFriends(
                gameReference,
                Pair.of(command.playerId(), command.friendId())
        );
        saveGameLobbyPorts.forEach(saveLobbyPort -> saveLobbyPort.save(lobby));
        return lobby;
    }
}
