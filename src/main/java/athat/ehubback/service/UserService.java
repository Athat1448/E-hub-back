package athat.ehubback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import athat.ehubback.model.User;
import athat.ehubback.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired(required=true)
    private BCryptPasswordEncoder passwordEncoder;

    public String login(String username, String password) throws ResponseStatusException {
        User user = userRepository.findByUsername(username);
        
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return "success";
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public User register(String username, String password) throws ResponseStatusException {
        
        if (userRepository.findByUsername(username) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        String encodedPassword = this.passwordEncoder.encode(password);
        User user = new User(username, encodedPassword);
        userRepository.save(user);
        return user;
    }

}
