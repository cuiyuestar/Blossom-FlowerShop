package com.blossom.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO implements Serializable {

    //评论id
    private Long id;

    //评价等级
    private Integer rating;

    //评论内容
    private String content;

    private LocalDateTime createTime;

    private int likeCount;

    private int replyCount;

    private int isLike; //0表示未点赞，1表示已点赞
}
