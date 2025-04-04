package com.blossom.mapper;


import com.blossom.annotation.AutoFill;
import com.blossom.dto.ActivityPageQueryDTO;
import com.blossom.dto.CommentPageQueryDTO;
import com.blossom.entity.Activity;
import com.blossom.entity.Comment;
import com.blossom.entity.Flower;
import com.blossom.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ActivityMapper {


    /**
     * 分页查询活动
     * @param activityPageQueryDTO
     * @return
     */
    Page<Activity> pageQuery(ActivityPageQueryDTO activityPageQueryDTO);

    /**
     * 根据id查询活动
     * @param id
     * @return
     */
    @Select("select * from activity where id=#{id}")
    Activity getById(Long id);

    /**
     * 创建活动
     * @param activity
     */
    @AutoFill(value= OperationType.INSERT)
    void insert(Activity activity);


    /**
     * 批量设置活动过期
     * @return
     */
    int batchExpireActivities();
}
