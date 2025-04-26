package com.blossom.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blossom.context.BaseContext;
import com.blossom.dto.CommentDTO;
import com.blossom.dto.CommentPageQueryDTO;
import com.blossom.dto.UserDTO;
import com.blossom.entity.Comment;
import com.blossom.entity.UserComment;
import com.blossom.enumeration.CommentChainMarkEnum;
import com.blossom.framework.designPattern.designpattern.chain.AbstractChainContext;
import com.blossom.mapper.CommentMapper;
import com.blossom.mapper.CommentsMapper;
import com.blossom.mapper.UserCommentMapper;
import com.blossom.result.PageResult;
import com.blossom.result.Result;
import com.blossom.service.CommentService;
import com.blossom.service.UserService;
import com.blossom.utils.UserHolder;
import com.blossom.vo.CommentVO;
import com.blossom.constant.RedisConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


;

@Service
@Slf4j
@RequiredArgsConstructor //允许对final修饰的对象进行注入
public class CommentServiceImpl extends ServiceImpl<CommentsMapper,Comment> implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserCommentMapper userCommentMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private UserService userService;

    private final AbstractChainContext<CommentPageQueryDTO> PageQueryabstractChainContext;
    private final AbstractChainContext<CommentDTO> AddCommentabstractChainContext;

    /**
     * 新增评论
     * @param commentDTO
     */
    public void addComment(CommentDTO commentDTO){
        log.info("新增评论请求参数: {}", commentDTO);
        AddCommentabstractChainContext.handler(CommentChainMarkEnum.COMMENT_ADD_FILTER.name(),commentDTO);
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        //comment.setUserId(BaseContext.getCurrentId());//从ThreadLocal获取用户id并填入（用户id不能通过dto传输，保证安全性）
        comment.setCreateTime(LocalDateTime.now());
        comment.setLikeCount(0);
        comment.setReplyCount(0);
        log.info("插入数据库");
        commentMapper.insert(comment);
    }

    /**
     * 删除评论
     * @param commentId
     */
    public void deleteComment(Long commentId) {
        Long userId = BaseContext.getCurrentId(); //获取用户id
        commentMapper.deleteByIdAndUserId(commentId,userId);
    }

    /**
     * 分页查询评论 (根据发布时间)
     * @param commentPageQueryDTO
     * @return
     */
    public PageResult pageQuery(CommentPageQueryDTO commentPageQueryDTO) {
        //责任链校验传入的分页查询数据
        PageQueryabstractChainContext.handler(CommentChainMarkEnum.COMMENT_PAGE_QUERY_FILTER.name(),commentPageQueryDTO);
        //开启自动分页
        PageHelper.startPage(commentPageQueryDTO.getPage(),commentPageQueryDTO.getPageSize());
        Page<Comment> page=commentMapper.pageQuery(commentPageQueryDTO);
        //转换VO列表
        List<CommentVO> voList = new ArrayList<>();
        Long currentUserId = BaseContext.getCurrentId();

        for (Comment comment : page.getResult()) {
            CommentVO vo = new CommentVO();
            BeanUtils.copyProperties(comment, vo);
            //设置点赞状态
            vo.setIsLike(isLike(comment.getId(), currentUserId));
            voList.add(vo);
        }
        return new PageResult(page.getTotal(),voList);
    }


    /**
     * 分页查询评论 (根据点赞数)
     * @param commentPageQueryDTO
     * @return
     */
    public PageResult pageQueryByLikeCount(CommentPageQueryDTO commentPageQueryDTO) {
        //责任链校验传入的分页查询数据
        PageQueryabstractChainContext.handler(CommentChainMarkEnum.COMMENT_PAGE_QUERY_FILTER.name(),commentPageQueryDTO);
        //开启自动分页
        PageHelper.startPage(commentPageQueryDTO.getPage(),commentPageQueryDTO.getPageSize());
        Page<Comment> page=commentMapper.pageQueryByLikeCount(commentPageQueryDTO);
        //转换VO列表
        List<CommentVO> voList = new ArrayList<>();
        Long currentUserId = BaseContext.getCurrentId();

        for (Comment comment : page.getResult()) {
            CommentVO vo = new CommentVO();
            BeanUtils.copyProperties(comment, vo);
            //设置点赞状态
            vo.setIsLike(isLike(comment.getId(), currentUserId));
            voList.add(vo);
        }
        return new PageResult(page.getTotal(),voList);
    }




    /**
     * 根据用户id查询评论
     * @param userId
     * @return
     */
    public List<Comment> listByUserId(Long userId) {
        List<Comment> commentList=commentMapper.list(userId);
        return commentList;
    }

    /**
     * 点赞笔记功能实现（每个用户只能点赞一次）
     * @param id
     * @return
     */
    @Override
    public Result likeCommentRedis(Long id) {
        //1获取登录用户
        Long userId = UserHolder.getUser().getId();
        //2.判断当前用户是否已经点赞
        String key="blog:liked:"+id;
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        if (score==null) {
            //3，如果未点赞，可以点赞
            //3.1数据库点赞数+1
            boolean isSuccess = update().setSql("like_count=like_count+1").eq("id", id).update();
            //3.2保存用户到redis的set集合中（sortSet）
            if (isSuccess){
                stringRedisTemplate.opsForZSet().add(key,userId.toString(),System.currentTimeMillis());
            }
        }else{
            //4已经点赞，取消点赞
            //4.1数据库点赞数-1
            boolean isSuccess = update().setSql("like_count=like_count-1").eq("id", id).update();
            //4.2把用户从redis的set集合移除
            if (isSuccess){
                stringRedisTemplate.opsForZSet().remove(key,userId.toString());
            }
        }


        return Result.success();
    }

    /**
     * 点赞列表查询列表
     * 显示最早点赞的用户（前五）
     * @param id
     * @return
     */
    @Override
    public Result queryCommentLikes(Long id) {
        String key= RedisConstants.COMMENT_LIKED_KEY +id;
        //1.查询top5的点赞用户zrange key 0 4
        Set<String> top5 = stringRedisTemplate.opsForZSet().range(key, 0, 4);
        if (top5==null||top5.isEmpty()){
            return Result.success(Collections.emptyList());
        }
        //2.解析出其中的用户id
        List<Long> ids = top5.stream().map(Long::valueOf).collect(Collectors.toList());
        //3.根据用户id查询用户 where id in(5,1) order by field(id,5,1)
        String idStr = StrUtil.join(",", ids);
        List<UserDTO> userDTOS = userService.query()
                .in("id",ids).last("ORDER BY FIELD(id,"+idStr+")").list()
                .stream()
                .map(user -> BeanUtil.copyProperties(user, UserDTO.class))
                .collect(Collectors.toList());

        //4返回
        return Result.success(userDTOS);
    }

    /**
     * 点赞评论/取消点赞
     */
    public void like(Long commentId) {
        Long userId=BaseContext.getCurrentId();
        UserComment history=userCommentMapper.getByCommentIdAndUserId(commentId,userId);
        //如果已经点赞，则取消点赞，删除点赞数据
        if(history!=null){
            userCommentMapper.delete(commentId,userId);
            commentMapper.decrease(commentId);
        }else{
            //插入点赞数据
            UserComment userComment=new UserComment();
            userComment.setCommentId(commentId);
            userComment.setUserId(userId);
            userCommentMapper.insert(userComment);
            commentMapper.increase(commentId); //更新评论的点赞数
        }

    }

    /**
     * 根据鲜花id查询评论
     * @param flowerId
     * @return
     */
    public List<CommentVO> listByFlowerId(Long flowerId) {
        List<Comment> commentList= (List<Comment>) commentMapper.getById(flowerId);
        List<CommentVO> list=new ArrayList<>();
        for (Comment comment : commentList) {
            CommentVO commentVO=new CommentVO();
            commentVO.setContent(comment.getContent());
            commentVO.setCreateTime(comment.getCreateTime());
            commentVO.setLikeCount(comment.getLikeCount());
            commentVO.setRating(comment.getRating());
            commentVO.setReplyCount(comment.getReplyCount());
            Integer islike = isLike(comment.getId(),BaseContext.getCurrentId());
            commentVO.setIsLike(islike);
        }
        return list;
    }

    /**
     * 判断用户是否点赞（redis优化）
     * 提取方法，可使用
     * @param comment
     */
    private void isCommentLiked(Comment comment) {
        // 1.获取登录用户
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            // 用户未登录，无需查询是否点赞
            return;
        }
        Long userId = user.getId();
        // 2.判断当前登录用户是否已经点赞
        String key = "blog:liked:" + comment.getId();
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        comment.setIsLike(score != null);
    }

    /**
     * 判断用户是否点赞
     * @param commentId
     * @param userId
     * @return
     */
    public Integer isLike(Long commentId, Long userId) {
        UserComment userComment=userCommentMapper.getByCommentIdAndUserId(commentId,userId);
        if(userComment!=null) return 1;
        else return 0;
    }


}
