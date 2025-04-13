package com.blossom.controller.user;


import com.blossom.constant.MessageConstant;
import com.blossom.constant.RedisConstants;
import com.blossom.dto.ActivityPageQueryDTO;
import com.blossom.dto.ActivitySalePageQueryDTO;
import com.blossom.dto.ParticipateDTO;
import com.blossom.entity.Activity;
import com.blossom.entity.ActivitySale;
import com.blossom.entity.Flower;
import com.blossom.enumeration.ActivityChainMarkEnum;
import com.blossom.exception.ActivityException;
import com.blossom.framework.designPattern.designpattern.chain.AbstractChainContext;
import com.blossom.result.PageResult;
import com.blossom.result.Result;
import com.blossom.service.ActivityService;
import com.blossom.service.FlowerService;
import com.blossom.vo.ActivitySaleVO;
import com.blossom.vo.FlowerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private FlowerService flowerService;


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
    @GetMapping("/{id}")
    @ApiOperation("根据id查询活动")
    public Result<Activity> getById(@PathVariable Long id){
        Activity activity=activityService.getById(id);
        return Result.success(activity);
    }


    /**
     * 参与活动
     * @param participateDTO
     * @return
     */
    @PostMapping("/participate")
    @ApiOperation("参与活动")
    public Result participate(ParticipateDTO participateDTO){
        activityService.participate(participateDTO);
        return Result.success();
    }

    /**
     * 查询活动详情（查看活动下的促销商品）
     * @param activitySalePageQueryDTO
     * @return
     */
    @GetMapping("/sale")
    @ApiOperation("查询活动详情(促销商品)")
    public Result<List<ActivitySaleVO>> queryActivitySale(ActivitySalePageQueryDTO activitySalePageQueryDTO){
        PageResult pageResult=activityService.pageQuerySale(activitySalePageQueryDTO);
        // 类型转换获取完整对象列表
        List<ActivitySale> sales = (List<ActivitySale>) pageResult.getRecords().stream()
                .map(record -> (ActivitySale) record) // 强制类型转换
                .collect(Collectors.toList());

        List<ActivitySaleVO> activitySaleVOList=new ArrayList<>();
        for(ActivitySale activitySale:sales){
            Long flowerId=activitySale.getFlowerId();
            FlowerVO flowerVO=flowerService.getByIdWithFlavor(flowerId);

            ActivitySaleVO activitySaleVO=new ActivitySaleVO();
            activitySaleVO.setName(flowerVO.getName());
            activitySaleVO.setPrice(flowerVO.getPrice());
            activitySaleVO.setImage(flowerVO.getImage());
            activitySaleVO.setDescription(flowerVO.getDescription());
            activitySaleVO.setOriginalPrice(activitySale.getOriginalPrice());
            activitySaleVO.setDiscountPrice(activitySale.getDiscountPrice());

            activitySaleVOList.add(activitySaleVO);
        }
        return Result.success(activitySaleVOList);
    }
}
