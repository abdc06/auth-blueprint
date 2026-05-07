package org.example.auth.blueprint.system.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.auth.blueprint.common.model.DataMap;

import java.util.List;

@Mapper
public interface UserMapper {

    List<DataMap> selectList(DataMap map);
    DataMap selectOne(DataMap map);
    int insert(DataMap map);
    int update(DataMap map);
    int delete(DataMap map);
}
