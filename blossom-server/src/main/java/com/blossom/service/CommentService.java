package com.blossom.service;

import com.blossom.dto.*;
import com.blossom.entity.Comment;
import com.blossom.result.PageResult;
import com.blossom.vo.CommentVO;

import java.util.List;

public interface CommentService {

    /**
     * 添加评论
     * @param commentDTO
     */
    void addComment(CommentDTO commentDTO);


    /**
     * 删除评论
     * @param commentDeleteDTO
     */
    void deleteComment(CommentDeleteDTO commentDeleteDTO);


    /**
     * 根据鲜花id查询这种鲜花的评论
     * @param commentPageQueryDTO
     * @return
     */
    PageResult pageQuery(CommentPageQueryDTO commentPageQueryDTO);

    /**
     * 根据点赞数查询评论
     * @param commentPageQueryDTO
     * @return
     */
    PageResult pageQueryByLikeCount(CommentPageQueryDTO commentPageQueryDTO);


    /**
     * 根据用户id查询用户自己的评论
     * @param userId
     * @return
     */
    List<Comment> listByUserId(Long userId);

    /**
     * 点赞评论
     */
    void like(Long commentId,Long userId);

    /**
     * 根据鲜花id查询评论
     * @param listCommentDTO
     * @return
     */
    List<CommentVO> listComment(ListCommentDTO listCommentDTO);

    /**
     * 判断用户是否点赞
     * @param id
     * @param currentId
     */
    Integer isLike(Long id, Long currentId);
}
