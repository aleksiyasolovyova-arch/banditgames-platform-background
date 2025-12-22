package be.kdg.team11.readmodel.service.lobby;

import be.kdg.team11.readmodel.models.LobbyModel;
import be.kdg.team11.readmodel.repository.LobbyModelRepository;
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
    public LobbyModelServiceImpl(LobbyModelRepository lobbyModelRepository) {
        this.lobbyModelRepository = lobbyModelRepository;
    }

    @Override
    public void project(LobbyCreatedEvent event) {
        LobbyModel lobby = new LobbyModel();
        lobby.setLobbyId(event.lobbyId());
        lobby.setGameId(event.gameId());
        lobby.setPlayer1Id(event.player1Id());
        lobby.setPlayer2Id(event.player2Id());
       // lobby.setPlayer1Username(event.player1Username());
       // lobby.setPlayer2Username(event.player2Username());
       // lobby.setPlayer1PictureUrl(event.player1PictureUrl());
       // lobby.setPlayer2PictureUrl(event.player2PictureUrl());
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
