package com.blossom.controller.user;


import com.blossom.context.BaseContext;
import com.blossom.dto.CommentDTO;
import com.blossom.dto.CommentPageQueryDTO;
import com.blossom.entity.Comment;
import com.blossom.result.PageResult;
import com.blossom.result.Result;
import com.blossom.service.CommentService;
import com.blossom.service.FlowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController("userCommentController")
@RequestMapping("/user/comment")
@Slf4j
@Api(tags = "C端-评论接口")
public class CommentController {
    @Autowired
    private FlowerService flowerService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CommentService commentService;


    /**
     * 发表评论
     * @param commentDTO
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("发表评论")
    public Result addComment(@RequestBody CommentDTO commentDTO) {
        commentService.addComment(commentDTO);
        return Result.success();
    }

    /**
     * 删除评论
     * @param commentId
     * @return
     */
    @DeleteMapping("/{commentId}")
    @ApiOperation("删除评论")
    public Result deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return Result.success();
    }

    /**
     * 根据鲜花id分页查询评论 (鲜花的评论数量可能较大，因此采用分页查询减少查询压力)
     * @param commentPageQueryDTO
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据鲜花id查询这种花的评论")
    public Result<PageResult> list(CommentPageQueryDTO commentPageQueryDTO) {
        PageResult pageResult = commentService.pageQuery(commentPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 查询用户自己的评论 （用户自身评论数较少，可直接返回评论集合list）
     * @return
     */
    @GetMapping("/list-common")
    @ApiOperation("查询用户自己的评论")
    public Result<List<Comment>> listByUserId() {
        Long userId=BaseContext.getCurrentId();
        List<Comment> commentList= commentService.listByUserId(userId);
        return Result.success(commentList);
    }

    /**
     * 点赞评论/取消点赞
     * @param commentId
     * @return
     */
    @PostMapping("/{commentId}")
    @ApiOperation("点赞评论/取消点赞")
    public Result likeComment(@PathVariable Long commentId){
        commentService.like(commentId);
        return Result.success();
    }
}
