package com.blossom.controller.admin;

import com.blossom.dto.FlowerDTO;
import com.blossom.dto.FlowerPageQueryDTO;
import com.blossom.entity.Flower;
import com.blossom.result.PageResult;
import com.blossom.result.Result;
import com.blossom.service.FlowerService;
import com.blossom.vo.FlowerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 鲜花管理类
 */
@RestController
@RequestMapping("/admin/flower")
@Api(tags="鲜花管理")
@Slf4j
public class FlowerController {

    @Autowired
    private FlowerService flowerService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增鲜花
     * @param flowerDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增鲜花")

    public Result save(@RequestBody FlowerDTO flowerDTO){
        log.info("新增鲜花:{}",flowerDTO);
        flowerService.save(flowerDTO);
        //新增鲜花后，数据库数据发生变化，需要删除缓存中的旧数据
        //构造key
        /*由于新增鲜花后发生变化的是分类（比如我在‘玫瑰’分类中新增一种花，原本的‘玫瑰’分类缓存里是没有这种新花的数据的，所以要清空分类缓存
        防止缓存不一致）
        因此要用分类id作为key*/
        String key="flower_"+flowerDTO.getCategoryId();
        cleanCache(key);
        return Result.success("新增成功");
    }

    /**
     * 鲜花分页查询
     * @param flowerPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("鲜花分页查询")
    public Result<PageResult> page(FlowerPageQueryDTO flowerPageQueryDTO){
        log.info("鲜花分页查询:{}", flowerPageQueryDTO);
        PageResult pageResult=flowerService.pageQuery(flowerPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除鲜花
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除鲜花")
    public Result delete(@RequestParam List<Long> ids){
        log.info("批量删除鲜花:{}",ids);
        flowerService.deleteBatch(ids);
        //清除所有被删除的鲜花的缓存
        cleanCache("flower_*");
        return Result.success("删除成功");
    }

    /**
     * 根据id查询鲜花
     * 方法作用：可以显示鲜花的信息，比如点击修改鲜花的时候，可以看到鲜花的原本信息，比如鲜花的名称、图片、分类等
     * @param id
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询鲜花")
    public Result<FlowerVO> getById(@PathVariable  Long id){
        log.info("根据id查询鲜花:{}",id);
        FlowerVO flowerVO=flowerService.getById(id);
        return Result.success(flowerVO);
    }

    /**
     * 修改鲜花
     * @param flowerDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改鲜花")
    public Result update(@RequestBody FlowerDTO flowerDTO){
        log.info("修改鲜花信息:{}",flowerDTO);
        flowerService.update(flowerDTO);
        //将所有鲜花的缓存清空
        cleanCache("flower_*");

        return Result.success("修改成功");
    }


    /**
     * 根据条件查询鲜花数据
     * @param categoryId
     * @return
     */
    //@GetMapping("/list")
    //@ApiOperation("根据条件查询鲜花数据")
    public Result<List<Flower>> list(Long categoryId){
        List<Flower> list = flowerService.list(categoryId);
        return Result.success(list);
    }



    /**
     * 清理缓存
     * @param pattern
     */
    private void cleanCache(String pattern){
        Set keys=redisTemplate.keys(pattern);//获取所有以flower_开头的key，放入keys集合中
        redisTemplate.delete(keys);
    }

}
