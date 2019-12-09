package com.community.community.dto;

import com.community.community.domain.User;
import lombok.Data;

@Data
public class QuestionDto {
    private Integer id;

    private String title;

    private String description;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer creator;

    private Integer commentCount;

    private Integer viewCount;

    private Integer likeCount;

    private String tag;

    private User user;
}
