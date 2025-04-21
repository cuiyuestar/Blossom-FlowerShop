package com.blossom.dto;


import lombok.Data;
import java.io.Serializable;


@Data
public class LikeCommentDTO implements Serializable {

    private Long userId;

    private Long commentId;


}
