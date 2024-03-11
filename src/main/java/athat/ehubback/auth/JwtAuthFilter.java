package athat.ehubback.auth;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import athat.ehubback.repository.UserRepository;
import athat.ehubback.service.JwtService;
import athat.ehubback.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private JwtService jwtService;
  private UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      String authHeader = request.getHeader("Authorization");
      String token = null;
      String username = null;
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
        username = jwtService.extractUsername(token);
      }

      if (token == null) {
        filterChain.doFilter(request, response);
        return;
      }

      User user = userRepository.findByUsername(username);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        if (jwtService.validateToken(token)) {
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }

      if(!request.getRequestURI().contains("/store/create") && user.getRole() == null && !request.getRequestURI().contains("/user/validate")){
          throw new Exception("Authorization failed: user has no roles.");
      }

      filterChain.doFilter(request, response);
      
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
      response.getWriter().write(e.getMessage());
    }

  }

}
