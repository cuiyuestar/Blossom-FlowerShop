package com.blossom.controller.user;


import com.blossom.dto.CommentDTO;
import com.blossom.dto.CommentPageQueryDTO;
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
    @PostMapping("/addcomment")
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
     * 根据鲜花id分页查询评论
     * @param commentPageQueryDTO
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据鲜花id查询这种花的评论")
    public Result<PageResult> list(CommentPageQueryDTO commentPageQueryDTO) {
        PageResult pageResult = commentService.pageQuery(commentPageQueryDTO);
        return Result.success(pageResult);
    }
}
