package com.blossom.mapper;

import com.github.pagehelper.Page;
import com.blossom.annotation.AutoFill;
import com.blossom.dto.FlowerPageQueryDTO;
import com.blossom.entity.Flower;
import com.blossom.enumeration.OperationType;
import com.blossom.vo.FlowerVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FlowerMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from flower where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入菜品数据
     * @param dish
     */
    @AutoFill(value= OperationType.INSERT)
    void insert(Flower dish);


    /**
     * 分页查询菜品
     * @param flowerPageQueryDTO
     * @return
     */
    Page<FlowerVO> pageQuery(FlowerPageQueryDTO flowerPageQueryDTO);


    /**
     * 根据主键查询数据
     * @param id
     * @return
     */
    @Select("select * from flower where id=#{id}")
   Flower getById(Long id);


    /**
     * 删除鲜花
     * @param id
     */
    @Delete("delete from flower where id=#{id}")
    void deleteById(Long id);


    /**
     * 修改鲜花基本信息
     * @param flower
     */
    @AutoFill(value= OperationType.UPDATE)
    void update(Flower flower);

    /**
     * 动态条件查询鲜花
     * @param flower
     * @return
     */
    List<Flower> list(Flower flower);
}
