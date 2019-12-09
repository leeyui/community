package com.community.community.mapper;

import com.community.community.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("UPDATE user SET token=#{token} where account_id = #{accountId}")
    void updateTokenByAccountId(@Param("token") String token, @Param("accountId") String accountId);

    @Select("select * from user where id = #{creator}")
    User findById(@Param("creator") Integer creator);
}
