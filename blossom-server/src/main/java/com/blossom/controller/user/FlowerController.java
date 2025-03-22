package com.blossom.controller.user;

import com.blossom.constant.StatusConstant;
import com.blossom.entity.Flower;
import com.blossom.result.Result;
import com.blossom.service.FlowerService;
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
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-鲜花浏览接口")
public class FlowerController {
    @Autowired
    private FlowerService flowerService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询鲜花
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询鲜花")
    public Result<List<FlowerVO>> list(Long categoryId) {
        //构造key,规则:flower_分类id
        String key="flower_"+categoryId;
        //查询redis中是否存在鲜花
        List<FlowerVO> list= (List<FlowerVO>) redisTemplate.opsForValue().get(key);
        //如果存在，则直接返回缓存数据
        if(list!=null&&list.size()>0){
            return Result.success(list);
        }
        //如果不存在，则查询数据库，并将查询到的菜品数据缓存到redis中
        Flower flower = new Flower();
        flower.setCategoryId(categoryId);
        flower.setStatus(StatusConstant.ENABLE);//查询起售中的鲜花
        list = flowerService.listWithFlavor(flower);
        redisTemplate.opsForValue().set(key,list);//将数据存入缓存
        return Result.success(list);
    }


}
