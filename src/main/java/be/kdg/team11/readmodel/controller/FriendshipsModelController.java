package be.kdg.team11.readmodel.controller;

import be.kdg.team11.readmodel.controller.dto.FriendShipModelDto;
import be.kdg.team11.readmodel.service.friendship.FriendshipModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/friendships")
public class FriendshipsModelController {
    private final FriendshipModelService friendshipModelService;

    public FriendshipsModelController(FriendshipModelService friendshipModelService) {
        this.friendshipModelService = friendshipModelService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
        public ResponseEntity<List<FriendShipModelDto>> getFriendships(@AuthenticationPrincipal Jwt token) {
        UUID playerId = UUID.fromString(token.getSubject());
        List<FriendShipModelDto> friendships = friendshipModelService.getPlayerFriendships(playerId);
        return ResponseEntity.ok(friendships);
    }
}
