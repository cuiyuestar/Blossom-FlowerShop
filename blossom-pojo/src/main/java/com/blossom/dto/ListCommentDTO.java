package com.blossom.dto;


import lombok.Data;
import java.io.Serializable;


@Data
public class ListCommentDTO implements Serializable {

    private Long userId;

    private Long flowerId;

}
