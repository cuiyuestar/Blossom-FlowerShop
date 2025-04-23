package com.blossom.service.impl;

import com.blossom.context.BaseContext;
import com.blossom.dto.CommentDTO;
import com.blossom.dto.CommentDeleteDTO;
import com.blossom.dto.CommentPageQueryDTO;
import com.blossom.dto.ListCommentDTO;
import com.blossom.entity.Comment;
import com.blossom.entity.UserComment;
import com.blossom.enumeration.CommentChainMarkEnum;
import com.blossom.framework.designPattern.designpattern.chain.AbstractChainContext;
import com.blossom.mapper.CommentMapper;
import com.blossom.mapper.UserCommentMapper;
import com.blossom.result.PageResult;
import com.blossom.service.CommentService;
import com.blossom.vo.CommentVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;




@Service
@Slf4j
@RequiredArgsConstructor //允许对final修饰的对象进行注入
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserCommentMapper userCommentMapper;

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
     * @param commentDeleteDTO
     */
    public void deleteComment(CommentDeleteDTO commentDeleteDTO) {
        //Long userId = BaseContext.getCurrentId(); //获取用户id
        commentMapper.deleteByIdAndUserId(commentDeleteDTO.getCommentId(),commentDeleteDTO.getUserId());
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
     * 点赞评论/取消点赞
     */
    public void like(Long commentId,Long userId) {
        log.info("点赞评论请求参数: commentId={},userId={}", commentId,userId);
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
     * @param listCommentDTO
     * @return
     */
    public List<CommentVO> listComment(ListCommentDTO listCommentDTO) {
        List<Comment> commentList=  commentMapper.getById(listCommentDTO.getFlowerId());
        List<CommentVO> list=new ArrayList<>();
        for (Comment comment : commentList) {
            CommentVO commentVO=new CommentVO();
            commentVO.setId(comment.getId());
            commentVO.setContent(comment.getContent());
            commentVO.setCreateTime(comment.getCreateTime());
            commentVO.setLikeCount(comment.getLikeCount());
            commentVO.setRating(comment.getRating());
            commentVO.setReplyCount(comment.getReplyCount());
            Integer islike = isLike(comment.getId(),listCommentDTO.getUserId());
            commentVO.setIsLike(islike);
            list.add(commentVO);
        }
        return list;
    }

    /**
     * 判断用户是否点赞
     * @param commentId
     * @param userId
     * @return
     */
    public Integer isLike(Long commentId, Long userId) {
        UserComment userComment=userCommentMapper.getByCommentIdAndUserId(commentId,userId);
        log.info("用户是否点赞：{}",userComment);
        if(userComment!=null) return 1;
        else return 0;
    }


}
