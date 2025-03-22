package com.blossom.controller.admin;


import com.blossom.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")//给当前类的bean起别名，和user的ShopController区分开来
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags= "店铺管理")
public class ShopController {
    @Autowired
     private RedisTemplate redisTemplate;

    public static final String KEY="SHOP_STATUS";

    /**
     * 设置店铺状态
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺状态")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置店铺状态为：{}",status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set("KEY",status);
        return Result.success();
    }

    /**
     * 获取店铺状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺状态")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get("KEY");
        log.info("获取店铺状态为：{}",status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
