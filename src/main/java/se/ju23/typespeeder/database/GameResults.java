package se.ju23.typespeeder.database;

import jakarta.persistence.*;
import se.ju23.typespeeder.database.User;

@Entity
@Table(name = "results")

public class GameResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playerid", referencedColumnName = "id")
    private User player;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "time")
    private long resultTime;

    @Column(name = "game_type")
    private int gameType;

    public GameResults() {
        this.resultTime = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getResultTime() {
        return resultTime;
    }

    public void setResultTime(long resultTime) {
        this.resultTime = resultTime;
    }
    public int getGameType() {
        return gameType;
    }
    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

}
