package be.kdg.team11.readmodel.service.lobby;

import be.kdg.team11.readmodel.models.LobbyModel;
import be.kdg.team11.readmodel.models.PlayerModel;
import be.kdg.team11.readmodel.repository.GameModelRepository;
import be.kdg.team11.readmodel.repository.LobbyModelRepository;
import be.kdg.team11.readmodel.repository.PlayerModelRepository;
import be.kdg.team11.sharedkernel.events.lobby.LobbyCreatedEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithDrawEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyEndedWithWinnerEvent;
import be.kdg.team11.sharedkernel.events.lobby.LobbyStartedEvent;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LobbyModelServiceImpl implements LobbyModelService{
    private final LobbyModelRepository lobbyModelRepository;
    private final PlayerModelRepository playerModelRepository;
    private final GameModelRepository gameModelRepository;
    public LobbyModelServiceImpl(LobbyModelRepository lobbyModelRepository,
                                 PlayerModelRepository playerModelRepository,
                                 GameModelRepository gameModelRepository) {
        this.lobbyModelRepository = lobbyModelRepository;
        this.playerModelRepository = playerModelRepository;
        this.gameModelRepository = gameModelRepository;
    }

    @Override
    public void project(LobbyCreatedEvent event) {
        LobbyModel lobby = new LobbyModel();
        lobby.setLobbyId(event.lobbyId());
        lobby.setGameId(event.gameId());
        gameModelRepository.findById(event.gameId()).ifPresent(game -> {
            lobby.setGameName(game.getName());
            lobby.setGamePictureUrl(game.getPictureUrl());
        });
        lobby.setPlayer1Id(event.player1Id());
        lobby.setPlayer2Id(event.player2Id());
        PlayerModel player1 = playerModelRepository.findById(event.player1Id()).orElse(null);
        if (player1 != null) {
            lobby.setPlayer1Username(player1.getUsername());
            lobby.setPlayer1PictureUrl(player1.getPictureUrl());
        }
        PlayerModel player2 = playerModelRepository.findById(event.player2Id()).orElse(null);
        if (player2 != null) {
            lobby.setPlayer2Username(player2.getUsername());
            lobby.setPlayer2PictureUrl(player2.getPictureUrl());
        }
        lobby.setPlayer1Status(event.player1Status());
        lobby.setPlayer2Status(event.player2Status());
        lobby.setLobbyType(event.lobbyType());
        lobby.setResult("DID_NOT_START");
        lobby.setCreatedAt(event.eventPit());

        lobbyModelRepository.save(lobby);
    }

    @Override
    public void project(LobbyEndedWithDrawEvent event) {
        lobbyModelRepository.findById(event.lobbyId())
                .ifPresent(lobby -> {
                    lobby.setResult(event.newStatus());
                    lobby.setFinishedAt(event.eventPit());
                    lobbyModelRepository.save(lobby);
                });

    }

    @Override
    public void project(LobbyEndedWithWinnerEvent event) {
        lobbyModelRepository.findById(event.lobbyId())
                .ifPresent(lobby -> {
                    lobby.setResult(event.newStatus());
                    lobby.setWinnerId(event.winnerId());
                    lobby.setFinishedAt(event.eventPit());
                    lobbyModelRepository.save(lobby);
                });
    }

    @Override
    public void project(LobbyStartedEvent event) {
        lobbyModelRepository.findById(event.lobbyId())
                .ifPresent(lobby -> {
                    lobby.setStartedAt(event.eventPit());
                    lobbyModelRepository.save(lobby);
                });
    }
}
