package org.example.auth.blueprint.security.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class Account {

    private String userId;
    private String userName;
    private String userPassword;
    private String userEmail;
    private Long tokenWeight;
    private Set<String> authorities;
}
