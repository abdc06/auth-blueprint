package org.example.auth.blueprint.system.authority.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.auth.blueprint.common.model.DataMap;

import java.util.List;

@Mapper
public interface AuthorityUserMapper {

    List<DataMap> selectAuthorityList(@Param("userId") String userId);
    List<DataMap> selectUserList(@Param("authorityId") String authorityId);
    int insert(DataMap map);
    int insertList(List<DataMap> list);

    int delete(DataMap map);
    int deleteByUserId(@Param("userId") String userId);
    int deleteByAuthorityId(@Param("authorityId") String authorityId);

    boolean existsAuthorization(DataMap map);
}
