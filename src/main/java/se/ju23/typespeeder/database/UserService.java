package se.ju23.typespeeder.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean createUser (String username, String password, String inGameName) {
        if (userRepository.findByUsername(username) == null) {
            User user= new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setInGameName(inGameName);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Användarnamnet är upptaget.");
        }
    }

    public User updateUser(Long id, String newPassword, String newInGameName) {
        User user = userRepository.findById(id). orElse(null);
        if (user != null) {
            user.setPassword(newPassword);
            user.setInGameName(newInGameName);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Användaren finns inte");
        }
        return user;
    }
}
