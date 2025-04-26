package com.blossom.service;

import com.blossom.dto.CommentDTO;
import com.blossom.dto.CommentPageQueryDTO;
import com.blossom.entity.Comment;
import com.blossom.result.PageResult;
import com.blossom.result.Result;
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
     * @param commentId
     */
    void deleteComment(Long commentId);


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
     * 使用redis进行点赞评论
     * @param id
     */
    Result likeCommentRedis(Long id);

    /**
     * 点赞列表查询列表
     * @param id
     * @return
     */
    Result queryCommentLikes(Long id);

    /**
     * 点赞评论
     */
    void like(Long commentId);

    /**
     * 根据鲜花id查询评论
     * @param flowerId
     * @return
     */
    List<CommentVO> listByFlowerId(Long flowerId);

    /**
     * 判断用户是否点赞
     * @param id
     * @param currentId
     */
    Integer isLike(Long id, Long currentId);
}
