package athat.ehubback.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import athat.ehubback.model.User;
import athat.ehubback.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserAuthenticationService implements UserDetailsService {

  private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) {

    User user = repository.findByUsername(username);

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .roles(user.getRole().toString())
        .build();
  }

}
