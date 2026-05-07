package org.example.auth.blueprint.security.service;

import lombok.RequiredArgsConstructor;
import org.example.auth.blueprint.common.model.DataMap;
import org.example.auth.blueprint.common.utils.CastUtils;
import org.example.auth.blueprint.security.model.Account;
import org.example.auth.blueprint.security.model.CustomUserDetails;
import org.example.auth.blueprint.security.mapper.AccountMapper;
import org.example.auth.blueprint.system.authority.mapper.AuthorityUserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountMapper accountMapper;
    private final AuthorityUserMapper authorityUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountMapper.selectByUserId(username);

        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        List<DataMap> authorityList = authorityUserMapper.selectAuthorityList(account.getUserId());
        Set<String> authorities = authorityList.stream().map(e -> CastUtils.obj2Str(e.get("authorityId"))).collect(Collectors.toSet());
        account.setAuthorities(authorities);

        return new CustomUserDetails(account);
    }
}
