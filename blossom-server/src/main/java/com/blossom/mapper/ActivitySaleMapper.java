package com.blossom.mapper;


import com.blossom.annotation.AutoFill;
import com.blossom.dto.ActivitySaleDTO;
import com.blossom.entity.ActivitySale;
import com.blossom.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ActivitySaleMapper {


    /**
     * 添加活动鲜花
     * @param activitySale
     */
    @AutoFill(value= OperationType.INSERT)
    void insert(ActivitySale activitySale);

    /**
     * 根据活动id和鲜花id查询活动促销鲜花
     * @param activitySaleDTO
     * @return
     */
    @Select("select * from activity_sale where activity_id=#{activityId} and flower_id=#{flowerId}")
    ActivitySale getByActivityIdAndFlowerId(ActivitySaleDTO activitySaleDTO);

    /**
     * 批量更新库存
     * @param sales
     */
    @AutoFill(value= OperationType.UPDATE)
    void batchUpdateWithVersion(List<ActivitySale> sales);

    /**
     * 根据活动id和鲜花id查询活动促销鲜花（适配库存持久化方法）
     * @param activityId
     * @param flowerId
     * @return
     */
    @Select("select * from activity_sale where activity_id=#{activityId} and flower_id=#{flowerId}")
    ActivitySale getByActivityIdAndFlowerId(Long activityId, Long flowerId);
}
