package org.example.auth.blueprint.system.authority.service;

import lombok.RequiredArgsConstructor;
import org.example.auth.blueprint.system.authority.mapper.AuthorityMapper;
import org.example.auth.blueprint.common.model.DataMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorityService {

    private final AuthorityMapper authorityMapper;

    public List<DataMap> selectList(DataMap map) {
        return authorityMapper.selectList(map);
    }

    public DataMap selectOne(DataMap map) {
        return authorityMapper.selectOne(map);
    }

    public int insert(DataMap map) {
        return authorityMapper.insert(map);
    }

    public int update(DataMap map) {
        return authorityMapper.update(map);
    }

    public int delete(DataMap map) {
        return authorityMapper.delete(map);
    }
}
