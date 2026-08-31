package com.aman.intern.moneyTransfer.Models.DTO.User;

import com.aman.intern.moneyTransfer.Models.DTO.Auth.RegisterResponseDTO;
import com.aman.intern.moneyTransfer.Models.Entities.User;
import lombok.Getter;

/// Wraps the updated profile plus a freshly-issued JWT.
///
/// The JWT's subject is the user's email (see JwtService#generateToken), so
/// changing the email invalidates the token the caller is currently holding
/// — the next request would fail user lookup with the old email. `token` is
/// non-null exactly when the email changed, telling the frontend to
/// overwrite its stored token; it's null otherwise since the existing token
/// is still perfectly valid.
@Getter
public class ProfileUpdateResponseDTO {
    private final RegisterResponseDTO profile;
    private final String token;

    public ProfileUpdateResponseDTO(User user, String token) {
        this.profile = new RegisterResponseDTO(user);
        this.token = token;
    }
}
