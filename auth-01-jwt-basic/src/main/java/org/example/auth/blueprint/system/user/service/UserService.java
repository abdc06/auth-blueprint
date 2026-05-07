package org.example.auth.blueprint.system.user.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.example.auth.blueprint.common.model.DataMap;
import org.example.auth.blueprint.common.utils.CastUtils;
import org.example.auth.blueprint.common.utils.MapUtils;
import org.example.auth.blueprint.system.authority.mapper.AuthorityUserMapper;
import org.example.auth.blueprint.system.user.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserMapper userMapper;
    private final AuthorityUserMapper authorityUserMapper;
    private final PasswordEncoder passwordEncoder;

    public List<DataMap> selectList(DataMap map) {
        return userMapper.selectList(map);
    }

    public DataMap selectOne(DataMap map) {
        return userMapper.selectOne(map);
    }

    public int insert(DataMap map) {
        int result = 0;
        map.put("userPassword", passwordEncoder.encode(CastUtils.obj2Str(map.get("userId"))));
        result += userMapper.insert(map);

        List<DataMap> list = MapUtils.convert(map.get("list"), new TypeReference<>() {});

        if (list != null && !list.isEmpty()) {
            list.forEach(e -> e.put("userId", map.get("userId")));
            result += authorityUserMapper.deleteByUserId(map.getString("userId"));
            result += authorityUserMapper.insertList(list);
        }

        return result;
    }

    public int update(DataMap map) {
        int result = 0;
        result += userMapper.update(map);

        List<DataMap> list = MapUtils.convert(map.get("list"), new TypeReference<>() {});

        if (list != null && !list.isEmpty()) {
            list.forEach(e -> e.put("userId", map.get("userId")));
            result += authorityUserMapper.deleteByUserId(map.getString("userId"));
            result += authorityUserMapper.insertList(list);
        }

        return result;
    }

    public int delete(DataMap map) {
        return userMapper.delete(map);
    }
}
