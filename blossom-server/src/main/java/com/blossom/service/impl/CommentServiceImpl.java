package com.blossom.service.impl;

import com.blossom.constant.MessageConstant;
import com.blossom.constant.StatusConstant;
import com.blossom.context.BaseContext;
import com.blossom.dto.CommentDTO;
import com.blossom.dto.CommentPageQueryDTO;
import com.blossom.dto.FlowerDTO;
import com.blossom.dto.FlowerPageQueryDTO;
import com.blossom.entity.Comment;
import com.blossom.entity.Flower;
import com.blossom.exception.DeletionNotAllowedException;
import com.blossom.mapper.CommentMapper;
import com.blossom.mapper.FlowerMapper;
import com.blossom.result.PageResult;
import com.blossom.service.CommentService;
import com.blossom.service.FlowerService;
import com.blossom.vo.CommentVO;
import com.blossom.vo.FlowerVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 新增评论
     * @param commentDTO
     */
    public void addComment(CommentDTO commentDTO){
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        comment.setUserId(BaseContext.getCurrentId());//从ThreadLocal获取用户id并填入
        comment.setCreateTime(LocalDateTime.now()); //填入创建时间
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
     * 分页查询评论
     * @param commentPageQueryDTO
     * @return
     */
    public PageResult pageQuery(CommentPageQueryDTO commentPageQueryDTO) {
        //开启自动分页
        PageHelper.startPage(commentPageQueryDTO.getPage(),commentPageQueryDTO.getPageSize());
        Page<Comment> page=commentMapper.pageQuery(commentPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }


}
