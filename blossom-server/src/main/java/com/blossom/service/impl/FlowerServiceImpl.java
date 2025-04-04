package com.blossom.service.impl;

import com.blossom.constant.RedisConstants;
import com.blossom.enumeration.FlowerChainMarkEnum;
import com.blossom.framework.designPattern.designpattern.chain.AbstractChainContext;
import com.blossom.utils.RedisClient;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.blossom.constant.MessageConstant;
import com.blossom.constant.StatusConstant;
import com.blossom.dto.FlowerDTO;
import com.blossom.dto.FlowerPageQueryDTO;
import com.blossom.entity.Flower;;
import com.blossom.exception.DeletionNotAllowedException;
import com.blossom.mapper.FlowerMapper;
import com.blossom.result.PageResult;
import com.blossom.service.FlowerService;
import com.blossom.vo.FlowerVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlowerServiceImpl implements FlowerService {
    // 所有依赖字段统一改为final
    @Autowired
    private FlowerMapper flowerMapper;
    @Autowired
    private RedisClient redisClient;

    private final AbstractChainContext<FlowerPageQueryDTO> abstractChainContext;




    /**
     * 新增鲜花
     * @param flowerDTO
     */
    public void save(FlowerDTO flowerDTO) {
        Flower flower=new Flower();
        BeanUtils.copyProperties(flowerDTO,flower);
        flowerMapper.insert(flower);
    }


    /**
     * 鲜花分页查询
     * @param flowerPageQueryDTO
     * @return
     */
    public PageResult pageQuery(FlowerPageQueryDTO flowerPageQueryDTO){
        //通过责任链校验传入的flowerPageQueryDTO
        abstractChainContext.handler(FlowerChainMarkEnum.FLOWER_QUERY_FILTER.name(),flowerPageQueryDTO);

        //PageHelper.startPage是一个对mapper层进行动态干涉的方法
        //它本身并不返回结果,而是通过干涉mapper层（拼接limit语句）,影响pageQuery方法的查询结果（只查询部分数据，减少性能开销）
        //它用于启动分页功能。它并不会直接返回分页结果，而是通过拦截 SQL 查询来实现分页，并将分页信息存储在当前线程的本地变量中（ThreadLocal）
        PageHelper.startPage(flowerPageQueryDTO.getPage(), flowerPageQueryDTO.getPageSize());
        Page<FlowerVO> page=flowerMapper.pageQuery(flowerPageQueryDTO);
        //将page对象的属性封装到pageResult对象中
        return new PageResult (page.getTotal(),page.getResult());
    }

    /**
     * 批量删除鲜花
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //遍历待删除的鲜花列表，如果存在正在启售的鲜花，就不允许删除
        for(Long id:ids){
            Flower flower=flowerMapper.getById(id);
            if(flower.getStatus()== StatusConstant.ENABLE){
                //当前鲜花处于启售状态，不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);//抛出不允许删除异常
            }
        }

        for(Long id:ids){
            flowerMapper.deleteById(id);
        }
    }


    /**
     * 根据id查询鲜花
     * @param id
     * @return
     */
    public FlowerVO getByIdWithFlavor(Long id) {
        //根据id查询鲜花数据
        Flower flower =flowerMapper.getById(id);
        //将查询到的数据封装到FlowerVO中
        FlowerVO flowerVO=new FlowerVO();
        BeanUtils.copyProperties(flower,flowerVO);
        return flowerVO;
    }

    /**
     * 根据id修改鲜花的信息
     * @param flowerDTO
     */
    public void updateWithFlavor(FlowerDTO flowerDTO) {
        Flower flower=new Flower();
        BeanUtils.copyProperties(flowerDTO,flower);
        flowerMapper.update(flower);

    }

    /**
     * 条件查询鲜花
     * @param flower
     * @return
     */
    public List<FlowerVO> query(Flower flower) {
        List<Flower> flowerList = flowerMapper.list(flower);
        List<FlowerVO> flowerVOList = new ArrayList<>();
        for (Flower f : flowerList) {
            FlowerVO flowerVO = new FlowerVO();
            BeanUtils.copyProperties(f,flowerVO);
            flowerVOList.add(flowerVO);
        }
        return flowerVOList;
    }


    /**
     * 根据分类id查询鲜花（新方法，缓存安全，提供给用户端）
     * @param categoryId
     * @return
     */
    @Override
    public List<FlowerVO> getByCategoryId(Long categoryId) {
        Flower flower = Flower.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE) //查询启售中的鲜花
                .build();
        //构造key前缀,规则:商铺缓存前缀+分类id
        String keyPrefix= RedisConstants.CACHE_SHOP_KEY ;
        //缓存穿透保护
        List<Flower> flowerList = redisClient.queryWithPassThrough(keyPrefix,categoryId,List.class,
                id->flowerMapper.list(Flower.builder() //传入的函数list需要Flower类型的参数，而我传入的是Long类型的categoryId
                        .categoryId(id)    //因此需要通过Lambda表达式将categoryId从Long类型转化为Flower类型
                        .status(StatusConstant.ENABLE)
                        .build()),
                RedisConstants.CACHE_SHOP_TTL, TimeUnit.MINUTES);

        List<FlowerVO> flowerVOList = new ArrayList<>();
        for (Flower f : flowerList) {
            FlowerVO flowerVO = new FlowerVO();
            BeanUtils.copyProperties(f,flowerVO);
            flowerVOList.add(flowerVO);
        }
        return flowerVOList;
    }


    /**
     * 根据分类id查询鲜花（缓存不安全，只提供给管理端使用，其复杂程度较低，可提高管理端查询效率）
     * @param categoryId
     * @return
     */
    public List<Flower> list(Long categoryId) {
        Flower flower = Flower.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return flowerMapper.list(flower);
    }


}
