package org.example.auth.blueprint.system.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.auth.blueprint.system.authority.dto.AuthorityDTO;

import java.util.List;

public class UserDTO {

    @Data
    public static class Register {
        @NotBlank
        private String userId;
        @NotBlank
        private String userPassword;
        @NotBlank
        private String userName;
        @Email
        private String userEmail;
    }

    @Data
    public static class Login {

        @NotBlank
        private String userId;
        @NotBlank
        private String userPassword;
    }

    @Data
    public static class Create {
        @NotBlank
        private String userId;
        @NotBlank
        private String userName;
        @Email
        private String userEmail;
        @NotBlank
        private String deptNo;

        @Valid
        private List<AuthorityDTO.Create> list;
    }

    @Data
    public static class Update {
        @NotBlank
        private String userId;
        @NotBlank
        private String userName;
        @Email
        private String userEmail;
        @NotBlank
        private String deptNo;

        @Valid
        private List<AuthorityDTO.Create> list;
    }
}
