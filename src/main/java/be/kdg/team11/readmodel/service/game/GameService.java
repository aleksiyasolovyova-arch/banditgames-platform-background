package be.kdg.team11.readmodel.service.game;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface GameService {
    void project(
      UUID gameId,
      String name,
      String description,
      String pictureUrl,
      String gameUrl,
      String gameCreatorName,
      List<String> rules,
      boolean playableWithAI
    );
}
