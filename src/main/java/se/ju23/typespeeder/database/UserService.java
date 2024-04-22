package se.ju23.typespeeder.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsersOrderByTotalScoreDesc() {
        return userRepository.findAllByOrderByTotalScoreDesc();
    }

    public User authenticate (String username, String password){
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)){
            return user;
        } else {
            return null;
        }
    }
    public boolean createUser (String username, String password, String inGameName) {
        if (userRepository.findByUsername(username) == null) {
            User user= new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setInGameName(inGameName);
            try {
                userRepository.save(user);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            throw new RuntimeException("Användarnamnet är upptaget.");
        }
    }

    public User updateUser(Long id, String newPassword, String newInGameName) {
        User user = userRepository.findById(id). orElse(null);
        if (user != null) {
            if (!newPassword.isEmpty()) {
                user.setPassword(newPassword);
            }
            if (!newInGameName.isEmpty()) {
                user.setInGameName(newInGameName);
            }
            userRepository.save(user);
        } else {
            throw new RuntimeException("Användaren finns inte");
        }
        return user;
    }
    public void addScoreToUser(User user, int score) {
        user.addScore(score);
        userRepository.save(user);
    }

}
