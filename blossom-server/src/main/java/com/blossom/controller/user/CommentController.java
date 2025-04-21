package com.blossom.controller.user;


import com.blossom.context.BaseContext;
import com.blossom.dto.CommentDTO;
import com.blossom.dto.CommentPageQueryDTO;
import com.blossom.dto.LikeCommentDTO;
import com.blossom.entity.Comment;
import com.blossom.result.PageResult;
import com.blossom.result.Result;
import com.blossom.service.CommentService;
import com.blossom.service.FlowerService;
import com.blossom.vo.CommentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        return Result.success("评论成功");
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

    @GetMapping("/{flowerId}")
    @ApiOperation("根据鲜花id查询这种花的评论——展示给用户，包含是否点赞的状态，便于前端显示点赞键是否上色")
    public Result<List<CommentVO>> listComment(@PathVariable Long flowerId){
        List<CommentVO> commentList=commentService.listByFlowerId(flowerId);
        return Result.success(commentList);
    }




//    /**
//     * 查询用户自己的评论 （用户自身评论数较少，可直接返回评论集合list）
//     * @return
//     */
//    @GetMapping("/list-own-comment")
//    @ApiOperation("查询用户自己的评论")
//    @ApiImplicitParams({
//            @ApiImplicitParam(
//                    name = "token",
//                    value = "Bearer Token",
//                    required = true,
//                    paramType = "header"
//            )
//    })
//    public Result<List<Comment>> listByUserId(Long userId) {
//        log.info("查询用户评论");
//        List<Comment> commentList= commentService.listByUserId(userId);
//        return Result.success(commentList);
//    }

//    /**
//     * 点赞评论/取消点赞
//     * @param
//     * @return
//     */
//    @PostMapping("/{commentId}")
//    @ApiOperation("点赞评论/取消点赞")
//    @ApiImplicitParams({
//            @ApiImplicitParam(
//                    name = "token",
//                    value = "Bearer Token",
//                    required = true,
//                    paramType = "header"
//            )
//    })
//    public Result likeComment(Long commentId){
//        Long userId=BaseContext.getCurrentId();
//        commentService.like(commentId,userId);
//        return Result.success();
//    }

    /**
     * 根据点赞量分页查询评论
     * @param commentPageQueryDTO
     * @return
     */
    @GetMapping("/list-by-likecount")
    @ApiOperation("根据点赞量分页查询评论")
    public Result<PageResult> listByLikeCount(CommentPageQueryDTO commentPageQueryDTO){
        PageResult pageResult = commentService.pageQueryByLikeCount(commentPageQueryDTO);
        return Result.success(pageResult);
    }


}
