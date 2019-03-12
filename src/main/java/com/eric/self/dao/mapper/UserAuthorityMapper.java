package com.eric.self.dao.mapper;

import mapper.UserAuthority;
import mapper.UserAuthorityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAuthorityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAuthority record);

    int insertSelective(UserAuthority record);

    List<UserAuthority> selectByExample(UserAuthorityExample example);

    UserAuthority selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserAuthority record, @Param("example") UserAuthorityExample example);

    int updateByExample(@Param("record") UserAuthority record, @Param("example") UserAuthorityExample example);

    int updateByPrimaryKeySelective(UserAuthority record);

    int updateByPrimaryKey(UserAuthority record);

    void batchInsert(@Param("list") List<Object> list);
}