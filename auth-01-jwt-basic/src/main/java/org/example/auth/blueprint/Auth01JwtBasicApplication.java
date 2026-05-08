package org.example.auth.blueprint;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.example.auth.blueprint.common.model.DataMap;
import org.example.auth.blueprint.system.authority.mapper.AuthorityMapper;
import org.example.auth.blueprint.system.authority.mapper.AuthorityUserMapper;
import org.example.auth.blueprint.system.user.mapper.UserMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
@SpringBootApplication
public class Auth01JwtBasicApplication implements ApplicationRunner {

    private final UserMapper userMapper;
    private final AuthorityMapper authorityMapper;
    private final AuthorityUserMapper authorityUserMapper;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        System.setProperty("JWT_REFRESH_TOKEN_SECRET", dotenv.get("JWT_REFRESH_TOKEN_SECRET"));

        SpringApplication.run(Auth01JwtBasicApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initAuthority();
        initUser();
    }

    private void initAuthority() {
        DataMap map = new DataMap();
        map.put("authorityId", "ROLE_ADMIN");
        DataMap adminAuthorityInfo = authorityMapper.selectOne(map);

        if (adminAuthorityInfo == null) {
            map.put("authorityType", "SYS");
            map.put("authorityName", "관리자");
            map.put("description", "관리자");
            authorityMapper.insert(map);
        }

        map.put("authorityId", "ROLE_USER");
        adminAuthorityInfo = authorityMapper.selectOne(map);

        if (adminAuthorityInfo == null) {
            map.put("authorityType", "SYS");
            map.put("authorityName", "일반사용자");
            map.put("description", "일반사용자");
            authorityMapper.insert(map);
        }
    }

    private void initUser() {
        DataMap map = new DataMap();
        map.put("userId", "admin");
        DataMap adminUserInfo = userMapper.selectOne(map);

        if (adminUserInfo == null) {
            map.put("userPassword", passwordEncoder.encode("admin"));
            map.put("userName", "관리자");
            map.put("authorityId", "ROLE_ADMIN");
            userMapper.insert(map);
            authorityUserMapper.insert(map);

            map.put("authorityId", "ROLE_USER");
            authorityUserMapper.insert(map);
        }

        map.put("userId", "user");
        DataMap userInfo = userMapper.selectOne(map);

        if (userInfo == null) {
            map.put("userPassword", passwordEncoder.encode("user"));
            map.put("userName", "사용자1");
            map.put("authorityId", "ROLE_USER");
            userMapper.insert(map);
            authorityUserMapper.insert(map);
        }
    }
}
