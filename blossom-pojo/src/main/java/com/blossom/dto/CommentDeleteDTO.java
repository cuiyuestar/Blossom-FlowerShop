package com.blossom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentDeleteDTO implements Serializable {

    private Long userId;

    private Long commentId;

}
