package com.blossom.service;

import com.blossom.dto.CommentDTO;
import com.blossom.dto.CommentPageQueryDTO;
import com.blossom.dto.FlowerDTO;
import com.blossom.dto.FlowerPageQueryDTO;
import com.blossom.entity.Flower;
import com.blossom.result.PageResult;
import com.blossom.vo.CommentVO;
import com.blossom.vo.FlowerVO;

import java.util.List;

public interface CommentService {

    /**
     * 添加评论
     * @param commentDTO
     */
    void addComment(CommentDTO commentDTO);


    /**
     * 删除评论
     * @param commentId
     */
    void deleteComment(Long commentId);


    /**
     * 根据鲜花id查询这种鲜花的评论
     * @param commentPageQueryDTO
     * @return
     */
    PageResult pageQuery(CommentPageQueryDTO commentPageQueryDTO);
}
