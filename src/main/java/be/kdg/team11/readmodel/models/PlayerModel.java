package be.kdg.team11.readmodel.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema="read_model_schema")
public class PlayerModel {
    @Id
    private UUID playerId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String pictureUrl;

    @Column(nullable = false)
    private LocalDate joinedDate;

    @Column
    private UUID favouriteGameId;

    //todo int
    @Column(nullable = false)
    private Integer totalGamesPlayed = 0;

    @Column(nullable = false)
    private Integer totalWins = 0;

    @Column(nullable = false)
    private Integer totalLosses = 0;

    @Column(nullable = false)
    private Integer totalDraws = 0;

    @Column(nullable = false)
    private Integer totalPlaytimeMinutes = 0;

    @Column
    private LocalDateTime lastActive;

    @Column(nullable = false)
    private Integer longestWinningStreak = 0;

    @Column(nullable = false)
    private Integer currentWinningStreak = 0;

    @Column(nullable = false)
    private Integer firstMoveWins = 0;

    @Column(nullable = false)
    private Integer firstMoveGames = 0;

    @Column(nullable = false)
    private Integer secondMoveWins = 0;

    @Column(nullable = false)
    private Integer secondMoveGames = 0;

    public PlayerModel() {}

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public UUID getFavouriteGameId() {
        return favouriteGameId;
    }

    public void setFavouriteGameId(UUID favouriteGameId) {
        this.favouriteGameId = favouriteGameId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public Integer getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(Integer totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public Integer getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(Integer totalWins) {
        this.totalWins = totalWins;
    }

    public Integer getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(Integer totalLosses) {
        this.totalLosses = totalLosses;
    }

    public Integer getTotalDraws() {
        return totalDraws;
    }

    public void setTotalDraws(Integer totalDraws) {
        this.totalDraws = totalDraws;
    }

    public Integer getTotalPlaytimeMinutes() {
        return totalPlaytimeMinutes;
    }

    public void setTotalPlaytimeMinutes(Integer totalPlaytimeMinutes) {
        this.totalPlaytimeMinutes = totalPlaytimeMinutes;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public Integer getLongestWinningStreak() {
        return longestWinningStreak;
    }

    public void setLongestWinningStreak(Integer longestWinningStreak) {
        this.longestWinningStreak = longestWinningStreak;
    }

    public Integer getCurrentWinningStreak() {
        return currentWinningStreak;
    }

    public void setCurrentWinningStreak(Integer currentWinningStreak) {
        this.currentWinningStreak = currentWinningStreak;
    }

    public Integer getFirstMoveWins() {
        return firstMoveWins;
    }

    public void setFirstMoveWins(Integer firstMoveWins) {
        this.firstMoveWins = firstMoveWins;
    }

    public Integer getFirstMoveGames() {
        return firstMoveGames;
    }

    public void setFirstMoveGames(Integer firstMoveGames) {
        this.firstMoveGames = firstMoveGames;
    }

    public Integer getSecondMoveWins() {
        return secondMoveWins;
    }

    public void setSecondMoveWins(Integer secondMoveWins) {
        this.secondMoveWins = secondMoveWins;
    }

    public Integer getSecondMoveGames() {
        return secondMoveGames;
    }

    public void setSecondMoveGames(Integer secondMoveGames) {
        this.secondMoveGames = secondMoveGames;
    }

}
