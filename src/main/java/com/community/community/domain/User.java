package com.community.community.domain;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String name;

    //区别不同授权用户
    private String accountId;

    private String token;

    private Long gmtCreate;

    private Long gmtModified;

    //头像缩略图
    private String avatarUrl;
}
