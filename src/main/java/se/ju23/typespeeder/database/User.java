package se.ju23.typespeeder.database;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, unique = true)
    private String username;

    @Column (length = 45)
    private String password;

    @Column(name = "ingamename", length = 45)
    private String inGameName;

    @Column(name = "score")
    private int score;

    @Column (name = "total_score")
    private Integer totalScore = 0;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInGameName() {
        return inGameName;
    }

    public void setInGameName(String inGameName) {
        this.inGameName = inGameName;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getTotalScore() {
        return totalScore!= null ? totalScore: 0;
    }
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }
    public void addScore(int additionalScore) {
        if (this.totalScore == null) {
            this.totalScore = 0;
        }
        this.totalScore += additionalScore;
    }

}
