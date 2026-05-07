package org.example.auth.blueprint.system.authority.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class AuthorityDTO {

    @Getter
    @Setter
    public static class Create {
        @NotBlank
        private String authorityId;
        @NotBlank
        private String authorityName;
        private String authorityType;
        private String description;
    }

    @Getter
    @Setter
    public static class Update {
        @NotBlank
        private String authorityId;
        @NotBlank
        private String authorityName;
        private String authorityType;
        private String description;
    }
}
