package se.ju23.typespeeder.Spel;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "results")

public class GameResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "result_time", nullable = false)
    private LocalDateTime resultTime;

    public GameResults() {
        this.resultTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getResultTime() {
        return resultTime;
    }

    public void setResultTime(LocalDateTime resultTime) {
        this.resultTime = resultTime;
    }
}
