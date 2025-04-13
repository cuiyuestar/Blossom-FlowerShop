package com.blossom.mapper;


import com.blossom.entity.UserComment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserCommentMapper {

     /**
      * 添加点赞记录
      * @param userComment
      */
     void insert(UserComment userComment);

     /**
      * 根据评论id和用户id查询用户是否点赞
      * @param commentId
      * @param userId
      * @return
      */
     @Select("select * from user_comment where comment_id=#{commentId} and user_id=#{userId}")
     UserComment getByCommentIdAndUserId(Long commentId, Long userId);


     /**
      * 根据评论id和用户id删除点赞记录
      * @param commentId
      * @param userId
      */
     @Delete("delete from user_comment where comment_id=#{commentId} and user_id=#{userId}")
     void delete(Long commentId, Long userId);
}
