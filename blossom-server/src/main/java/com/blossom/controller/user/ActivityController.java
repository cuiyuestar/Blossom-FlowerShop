package com.blossom.controller.user;


import com.blossom.constant.MessageConstant;
import com.blossom.constant.RedisConstants;
import com.blossom.dto.ActivityPageQueryDTO;
import com.blossom.dto.ParticipateDTO;
import com.blossom.entity.Activity;
import com.blossom.enumeration.ActivityChainMarkEnum;
import com.blossom.exception.ActivityException;
import com.blossom.framework.designPattern.designpattern.chain.AbstractChainContext;
import com.blossom.result.PageResult;
import com.blossom.result.Result;
import com.blossom.service.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userActivityController")
@RequestMapping("user/activity")
@Slf4j
@RequiredArgsConstructor
@Api(tags= "热门活动")
public class ActivityController {
    @Autowired
     private RedisTemplate redisTemplate;
    @Autowired
    private ActivityService activityService;


    /**
     * 分页查询活动
     * @param activityPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> PageQuery(ActivityPageQueryDTO activityPageQueryDTO){
        PageResult pageResult=activityService.pageQuery(activityPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据id查询活动
     * @param id
     * @return
     */
    @GetMapping("{/id}")
    @ApiOperation("根据id查询活动")
    public Result<Activity> getById(@PathVariable Long id){
        Activity activity=activityService.getById(id);
        return Result.success(activity);
    }



    public Result participate(ParticipateDTO participateDTO){
        activityService.participate(participateDTO);
        return Result.success();
    }
}
