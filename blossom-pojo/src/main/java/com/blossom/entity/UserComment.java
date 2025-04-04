package com.blossom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户点赞评论记录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserComment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long commentId;

    private Long userId;

}
