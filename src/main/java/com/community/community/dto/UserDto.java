package com.community.community.dto;

import lombok.Data;

@Data
public class UserDto {

    private Integer id;

    private String name;

    private String token;

    //头像缩略图
    private String avatarUrl;
}
