package com.blossom.controller.user;


import com.blossom.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController") //给当前类的bean起别名，和admin的ShopController区分开来
@RequestMapping("user/shop")
@Slf4j
@Api(tags= "用户店铺状态查询")
public class ShopController {
    @Autowired
     private RedisTemplate redisTemplate;

    public static final String KEY="SHOP_STATUS";

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
