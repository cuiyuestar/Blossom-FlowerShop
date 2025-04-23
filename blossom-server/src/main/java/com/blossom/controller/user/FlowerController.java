package com.blossom.controller.user;

import com.blossom.constant.RedisConstants;
import com.blossom.constant.StatusConstant;
import com.blossom.dto.FlowerPageQueryDTO;
import com.blossom.entity.Flower;
import com.blossom.result.PageResult;
import com.blossom.result.Result;
import com.blossom.service.FlowerService;
import com.blossom.utils.RedisClient;
import com.blossom.vo.FlowerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userFlowerController")
@RequestMapping("/user/flower")
@Slf4j
@Api(tags = "C端-鲜花浏览接口")
public class FlowerController {
    @Autowired
    private FlowerService flowerService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisClient redisClient;

    /**
     * 根据分类id查询鲜花
     * @param categoryId
     * @return
     */
    @GetMapping("/getByCategoryId")
    @ApiOperation("根据分类id查询鲜花")
    public Result<List<FlowerVO>> getByCategoryId(Long categoryId) {
        List<FlowerVO> list =flowerService.getByCategoryId(categoryId);
        return Result.success(list);
    }


    /**
     * 分页查询鲜花
     * @param flowerPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询鲜花") //将鲜花名写入dto，然后传到后端，用mapper层的万能条件查询方法进行查询
    public Result<PageResult> pageQuery(FlowerPageQueryDTO flowerPageQueryDTO) {
        PageResult page =flowerService.pageQuery(flowerPageQueryDTO);
        return Result.success(page);
    }


    @GetMapping("/getById")
    @ApiOperation("根据id查询鲜花")
    public Result<FlowerVO> getById(Long flowerId){
        FlowerVO flowerVO =flowerService.getById(flowerId);
        return Result.success(flowerVO);
    }

}
