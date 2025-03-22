package com.blossom.service.impl;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FlowerServiceImpl implements FlowerService {
    @Autowired
    private FlowerMapper flowerMapper;

    /**
     * 新增鲜花
     * @param flowerDTO
     */
    public void save(FlowerDTO flowerDTO) {

        //向菜品表插入一条数据（添加一个新菜品）
        Flower flower=new Flower();
        BeanUtils.copyProperties(flowerDTO,flower);
        flowerMapper.insert(flower);
    }


    /**
     * 菜品分页查询
     * @param flowerPageQueryDTO
     * @return
     */
    public PageResult pageQuery(FlowerPageQueryDTO flowerPageQueryDTO){
        //PageHelper.startPage是一个对mapper层进行动态干涉的方法
        //它本身并不返回结果,而是通过干涉mapper层,影响pageQuery方法的查询结果
        //它用于启动分页功能。它并不会直接返回分页结果，而是通过拦截 SQL 查询来实现分页，并将分页信息存储在当前线程的本地变量中（ThreadLocal）
        PageHelper.startPage(flowerPageQueryDTO.getPage(), flowerPageQueryDTO.getPageSize());
        Page<FlowerVO> page=flowerMapper.pageQuery(flowerPageQueryDTO);
        //将page对象的属性封装到pageResult对象中
        return new PageResult (page.getTotal(),page.getResult());
    }

    /**
     * 批量删除菜品
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //遍历待删除的菜品列表，如果存在正在启售的菜品，就不允许删除
        for(Long id:ids){
            Flower flower=flowerMapper.getById(id);
            if(flower.getStatus()== StatusConstant.ENABLE){
                //当前菜品处于启售状态，不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);//抛出不允许删除异常
            }
        }

        //删除菜品表中的菜品数据
        for(Long id:ids){
            flowerMapper.deleteById(id);
        }
    }

    //TODO：查询鲜花时顺便查询其评论
    /**
     * 根据id查询鲜花
     * @param id
     * @return
     */
    public FlowerVO getByIdWithFlavor(Long id) {
        //根据id查询菜品数据
        Flower flower =flowerMapper.getById(id);
        //将查询到的数据封装到dishVO中
        FlowerVO flowerVO=new FlowerVO();
        BeanUtils.copyProperties(flower,flowerVO);
        return flowerVO;
    }

    /**
     * 根据id修改鲜花的信息
     * @param flowerDTO
     */
    public void updateWithFlavor(FlowerDTO flowerDTO) {
        //修改菜品表基本信息
        Flower flower=new Flower();//由于dishDTO包含了口味数据，而我们这里只传输除口味以外的数据，所以需要
        //使用BeanUtils将dishDTO中的数据拷贝到dish中,dish里没有口味flavor属性,因此拷贝时不会将口味数据拷贝过去,
        //这就实现了传输除口味以外数据的目的
        BeanUtils.copyProperties(flowerDTO,flower);
        flowerMapper.update(flower);

    }

    /**
     * 条件查询鲜花
     * @param flower
     * @return
     */
    public List<FlowerVO> listWithFlavor(Flower flower) {
        List<Flower> dishList = flowerMapper.list(flower);

        List<FlowerVO> flowerVOList = new ArrayList<>();

        for (Flower f : dishList) {
            FlowerVO flowerVO = new FlowerVO();
            BeanUtils.copyProperties(f,flowerVO);

            flowerVOList.add(flowerVO);
        }

        return flowerVOList;
    }

    /**
     * 根据分类id查询菜品
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
