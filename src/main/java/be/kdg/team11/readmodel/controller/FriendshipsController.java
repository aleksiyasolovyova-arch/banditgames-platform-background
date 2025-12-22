package be.kdg.team11.readmodel.controller;

import be.kdg.team11.readmodel.controller.dto.FriendDto;
import be.kdg.team11.readmodel.service.friendship.FriendshipModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/friends")
public class FriendshipsController {
    private final FriendshipModelService friendshipModelService;

    public FriendshipsController(FriendshipModelService friendshipModelService) {
        this.friendshipModelService = friendshipModelService;
    }

    @GetMapping
    public ResponseEntity<List<FriendDto>> getFriends(@AuthenticationPrincipal Jwt token) {
        UUID playerId = UUID.fromString(token.getSubject());
        List<FriendDto> friends = friendshipModelService.getPlayerFriends(playerId);
        return ResponseEntity.ok(friends);
    }


}
