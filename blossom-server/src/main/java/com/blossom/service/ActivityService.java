package com.blossom.service;

import com.blossom.dto.*;
import com.blossom.entity.Activity;
import com.blossom.result.PageResult;
import com.blossom.vo.ParticipationVO;

public interface ActivityService {


    /**
     * 活动分页查询
     * @param activityPageQueryDTO
     * @return
     */
    PageResult pageQuery(ActivityPageQueryDTO activityPageQueryDTO);


    /**
     * 根据id查询活动
     * @param id
     * @return
     */
    Activity getById(Long id);

    /**
     * 创建新活动
     * @param activityDTO
     */
    void create(ActivityDTO activityDTO);

    /**
     * 设置活动商品
     * @param activitySaleDetailDTO
     */
    void setActivitySale(ActivitySaleDetailDTO activitySaleDetailDTO);

    /**
     * 参与活动
     * @param participateDTO
     */
    ParticipationVO participate(ParticipateDTO participateDTO);

    /**
     * 根据活动id查询活动促销商品
     * @param activitySaleDetailDTO
     * @return
     */
    PageResult pageQuerySale(ActivitySalePageQueryDTO activitySaleDetailDTO);
}
