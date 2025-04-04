package com.blossom.controller.admin;


import com.blossom.dto.ActivityDTO;
import com.blossom.dto.ActivitySaleDetailDTO;
import com.blossom.result.Result;
import com.blossom.service.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminActivityController")
@RequestMapping("admin/activity")
@Slf4j
@Api(tags= "热门活动")
public class ActivityController {
    @Autowired
     private ActivityService activityService;



    /**
     * 创建活动
     * @param activityDTO
     * @return
     */
        @PostMapping("/create")
        @ApiOperation("创建活动")
        public Result create(ActivityDTO activityDTO){
            activityService.create(activityDTO);
            return Result.success();
        }

    /**
     * 设置活动的促销鲜花表
     * @param activitySaleDetailDTO
     * @return
     */
    @PostMapping("/set")
        @ApiOperation("设置活动的促销鲜花表")
        public Result setActivitySale(ActivitySaleDetailDTO activitySaleDetailDTO){
            activityService.setActivitySale(activitySaleDetailDTO);
            return Result.success();
        }


}
