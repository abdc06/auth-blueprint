package org.example.auth.blueprint.security.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class AccountDTO {

    @Getter
    public static class Login {
        @NotNull
        @Size(min = 3, max = 50)
        private String userId;
        @NotNull
        @Size(min = 3, max = 100)
        private String userPassword;
    }

    @Getter
    public static class RefreshToken {
        @NotNull
        private String refreshToken;
    }
}
