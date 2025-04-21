package com.blossom.mapper;


import com.blossom.annotation.AutoFill;
import com.blossom.dto.CommentPageQueryDTO;
import com.blossom.entity.Comment;
import com.blossom.entity.Flower;
import com.blossom.enumeration.OperationType;
import com.blossom.vo.CommentVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CommentMapper {


    /**
     * 新增评论
     * @param comment
     */
    void insert(Comment comment);


    /**
     * 根据鲜花id查询评论
     * @param flowerId
     * @return
     */
    @Select("select * from comment where flower_id=#{flowerId}")
    List<Comment> getById(Long flowerId);



    /**
     * 根据评论id和用户id删除评论
     * @param commentId
     * @param userId
     */
    @Delete("delete from comment where id=#{commentId} and user_id=#{userId}")
    void deleteByIdAndUserId(Long commentId, Long userId);

    /**
     * 分页查询评论
     * @param commentPageQueryDTO
     * @return
     */
    Page<Comment> pageQuery(CommentPageQueryDTO commentPageQueryDTO);

    /**
     * 分页查询热评 (根据点赞数排序)
     * @param commentPageQueryDTO
     * @return
     */
    Page<Comment> pageQueryByLikeCount(CommentPageQueryDTO commentPageQueryDTO);


    /**
     * 条件查询评论
     * @param commentDTO
     * @return
     */
    List<Comment> list(Long commentDTO);

    /**
     * 增加评论点赞数
     * @param commentId
     */
    @Update("update comment set like_count=like_count+1 where id=#{commentId}")
    void increase(Long commentId);

    /**
     * 减少评论点赞数
     * @param commentId
     */
    @Update("update comment set like_count=like_count-1 where id=#{commentId}")
    void decrease(Long commentId);


}
