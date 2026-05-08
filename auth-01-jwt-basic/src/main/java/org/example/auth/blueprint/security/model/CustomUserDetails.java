package org.example.auth.blueprint.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.auth.blueprint.common.model.DataMap;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {

    private final Account account;

    public DataMap getAccountInfo() {
        DataMap accountInfo = new DataMap();
        accountInfo.put("userId", account.getUserId());
        accountInfo.put("userName", account.getUserName());
        accountInfo.put("userEmail", account.getUserEmail());
        accountInfo.put("authorities", account.getAuthorities());
        return accountInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return account.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return account.getUserPassword();
    }

    @Override
    public String getUsername() {
        return account.getUserId();
    }
}

