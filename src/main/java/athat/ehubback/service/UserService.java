package athat.ehubback.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import athat.ehubback.dto.LoginDto;
import athat.ehubback.model.User;
import athat.ehubback.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private JwtService jwtService;
    private BCryptPasswordEncoder passwordEncoder;

    public LoginDto login(String username, String password) throws ResponseStatusException {
        User user = userRepository.findByUsername(username);
        
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtService.generateToken(user);
            return new LoginDto(user.getUsername(), user.getRole(), token);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public String register(String username, String password) throws ResponseStatusException {
        if (userRepository.findByUsername(username) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        String encodedPassword = this.passwordEncoder.encode(password);
        User user = new User(username, encodedPassword);
        userRepository.save(user);
        return jwtService.generateToken(user);
    }

    public String validateUser(String token) {
        if (jwtService.validateToken(token)) {
            return jwtService.extractRole(token);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
