package team11.platform_backend.game.domain.achievement;


import team11.platform_backend.sharedkernel.events.GameCompletedEvent;

public sealed interface Threshold permits CountThreshold, TimeThreshold {
    boolean isMetBy(GameCompletedEvent e);
}