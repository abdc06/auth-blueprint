package org.example.auth.blueprint.system.authority.service;

import org.example.auth.blueprint.common.model.DataMap;
import org.example.auth.blueprint.system.authority.mapper.AuthorityUserMapper;
import lombok.RequiredArgsConstructor;
import org.example.auth.blueprint.common.utils.CastUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorityUserService {

    private final AuthorityUserMapper authorityUserMapper;

    public List<DataMap> selectAuthorityList(String userId) {
        return authorityUserMapper.selectAuthorityList(userId);
    }

    public List<DataMap> selectUserList(String authorityId) {
        return authorityUserMapper.selectUserList(authorityId);
    }

    public int save(List<DataMap> mapList) {
        int result = 0;

        for (DataMap map : mapList) {
            String status = CastUtils.obj2Str(map.get("status"));

            if ("I".equals(status)) {
                result += authorityUserMapper.insert(map);
            } else if ("D".equals(status)) {
                result += authorityUserMapper.delete(map);
            }
        }

        return result;
    }

    public boolean existsAuthorization(DataMap map) {
        return authorityUserMapper.existsAuthorization(map);
    }
}
