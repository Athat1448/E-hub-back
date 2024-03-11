package athat.ehubback.dto;

import athat.ehubback.model.Role;

public record LoginDto(
  String username,
  Role role,
  String token
) {}
