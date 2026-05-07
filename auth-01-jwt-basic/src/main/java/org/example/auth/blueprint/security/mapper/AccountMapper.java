package org.example.auth.blueprint.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.auth.blueprint.security.model.Account;

import java.util.Optional;

@Mapper
public interface AccountMapper {

    Account selectByUserId(@Param("userId") String userId);
}
